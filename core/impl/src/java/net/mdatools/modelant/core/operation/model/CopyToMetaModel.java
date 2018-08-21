/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.operation.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jmi.model.Association;
import javax.jmi.model.Attribute;
import javax.jmi.model.Classifier;
import javax.jmi.model.Feature;
import javax.jmi.model.ModelElement;
import javax.jmi.reflect.RefAssociation;
import javax.jmi.reflect.RefAssociationLink;
import javax.jmi.reflect.RefClass;
import javax.jmi.reflect.RefObject;
import javax.jmi.reflect.RefPackage;

import net.mdatools.modelant.core.api.Function;
import net.mdatools.modelant.core.api.Procedure;
import net.mdatools.modelant.core.api.model.NameMapping;
import net.mdatools.modelant.core.api.name.AssociationName;
import net.mdatools.modelant.core.api.name.ClassName;
import net.mdatools.modelant.core.name.AssociationNameImpl;
import net.mdatools.modelant.core.name.ClassNameImpl;
import net.mdatools.modelant.core.name.FieldNameImpl;
import net.mdatools.modelant.core.operation.element.PrintModelElement;
import net.mdatools.modelant.core.util.Navigator;

/**
 * Transform one mode into another one using a correspondence (mapping) between their metamodels.
 * Copy a model, represented in one metamodel, as the same model in another metamodel, according to
 * explicitly provided rules for metamodel mapping rules. For example, if appropriate metamodel mapping rules
 * are provided, it will copy a model from UML 1.3 to UML 1.4.
 * Stateless. Thread-safe.
 * As of JMI-1.0:<ul>
 * <li> the derived associations are not copied
 * <li> the non-changeable attributes are not copied
 * </ul>
 * @author Rusi Popov
 */
public class CopyToMetaModel implements Function<RefPackage, Map<RefObject, RefObject>> {
  /**
   * This is a common logger
   */
  private static final Logger LOGGER = Logger.getLogger( CopyToMetaModel.class.getPackage().getName() );

  private final NameMapping nameMapping;

  private final RefPackage sourceExtent;

  /**
   * @param sourceExtent not null extent of the source/old model to compare
   * @param mapping non-null strategy to map one metamodel to another
   */
  public CopyToMetaModel(RefPackage sourceExtent, NameMapping mapping) {
    this.nameMapping = mapping;

    if ( sourceExtent == null ) {
      throw new IllegalArgumentException("Expected a non-null source extent to transform");
    }
    this.sourceExtent = sourceExtent;
  }

  /**
   * Convert the model from fromExtent to a model in toExtent considering the metamodel mapping defined
   * @param targetExtent not null extent of the target/new model to compare
   * @return the non-null correspondence between the objects from the source to the target model
   */
  public Map<RefObject, RefObject> execute(RefPackage targetExtent) {
    Map<RefObject, RefObject> result;

    // validate the parameters
    if ( targetExtent == null ) {
      throw new IllegalArgumentException("Expected a non-null target extent to copy to");
    }

    result = new IdentityHashMap<RefObject, RefObject>();

    for (RefClass refClass : Navigator.getAllClasses(sourceExtent)) {
      copyObjects(targetExtent, refClass, result );
    }

    for (RefClass refClass : Navigator.getAllClasses(sourceExtent)) {
      copyAttributes(targetExtent, refClass, result );
    }

    for (RefAssociation refAssociation : Navigator.getAllAssociations(sourceExtent)) {
      copyLinks(targetExtent, refAssociation, result );
    }
    return result;
  }

  /**
   * Copy the instances of refClass to the targetExtent with the stated metamodel transformation,
   * collect the correspondence of original to copy objects in objectsMap
   *
   * @param targetExtent not null extent where to copy the instances of refClass
   * @param originalMetaClass not null source model class
   * @param objectsMap not null correspondence between an original and copy objects
   */
  private void copyObjects(RefPackage targetExtent, RefClass originalMetaClass, Map<RefObject, RefObject> objectsMap) {
    Procedure<RefObject> construct;
    ClassName className;

    className = new ClassNameImpl((Classifier) originalMetaClass.refMetaObject());
    construct = nameMapping.mapMetaClass( className, sourceExtent, targetExtent, objectsMap );

    for (RefObject source : (Collection<RefObject>) originalMetaClass.refAllOfClass()) {
      try {
        construct.execute(source); // objectsMap is updated to bind the source to the produced object(s) if any

      } catch (Exception ex) {
        LOGGER.log(Level.SEVERE, "Copy object "+new PrintModelElement().execute( source )+" caused: ",ex);
      }
    }
  }

  /**
   * Copy the values of the attributes of the instances of refClass as corresponding values of corresponding attributes in
   * the corresponding instances
   *
   * @param targetExtent not null extent where to copy the instances of refClass
   * @param originalMetaClass not null source model class
   * @param objectsMap not null correspondence between an original and copy objects
   */
  private void copyAttributes(RefPackage targetExtent, RefClass originalMetaClass, Map<RefObject, RefObject> objectsMap) {
    Collection<Procedure<RefObject>> copyAttributesOperations;

    copyAttributesOperations = collectCopyAttributeOperations(originalMetaClass, targetExtent, objectsMap);

    // TODO: Let A extends B, check if A.allOfClass() subclass of B.allOfClass()
    // TODO: If so, then DO NOT collect superclasses of A to collect their attributes, as this would
    // TODO: repeat copying the parent attributes for all its subclasses, which is slow

    for (RefObject source : (Collection<RefObject>) originalMetaClass.refAllOfClass()) {
      for (Procedure<RefObject> copy : copyAttributesOperations) {
        try {
          copy.execute(source);
        } catch (Exception ex) {
          LOGGER.log(Level.SEVERE, copy+" on "+new PrintModelElement().execute( source )+" caused: ", ex);
        }
      }
    }
  }


  /**
   * Collect only Attribute features from the instances of the source class (as sourceMetaObject) to copy to the
   * instances of the target class.
   * Skip the Reference Features, as they are copied through the associations.
   *
   * The produced operations lookup the model element that corresponds to the source object to copy the argument
   * from and update the correspondent. In case there is no correspondent, they do nothing.
   * This unifies the interface of the produced operations.
   *
   * @param originalModelClass not null source metamodel class
   * @param targetExtent not null
   * @param objectsMap not null mapping of the source model elements to the target model elements
   * @return non-null collection of operations to copy corresponding attribute values from the source to the target (copy) object
   */
  private Collection<Procedure<RefObject>> collectCopyAttributeOperations(RefClass originalModelClass,
                                                                          RefPackage targetExtent,
                                                                          Map<RefObject, RefObject> objectsMap) {
    Collection<Procedure<RefObject>> result;
    FieldNameImpl sourceFieldName;
    Classifier sourceMetaObject;
    Collection<Classifier> superMetaObjects;

    result = new ArrayList<>();

    sourceMetaObject = (Classifier) originalModelClass.refMetaObject();

    superMetaObjects = Navigator.getAllSuperMetaObejcts( sourceMetaObject );

    for (Classifier superclass : superMetaObjects) {
      for (ModelElement contents : (Collection<ModelElement>) superclass.getContents()) {

        if ( contents instanceof Attribute ) {
          sourceFieldName = new FieldNameImpl((Attribute) contents);

          result.add(nameMapping.mapMetaFieldName(sourceFieldName, sourceExtent, targetExtent, objectsMap));
        }
      }
    }
    return result;
  }

  /**
   * Copy the links from the sourceAssociation as links into the correspondent association
   * @param targetExtent
   * @param sourceAssociation
   * @param objectsMap
   */
  private void copyLinks(RefPackage targetExtent, RefAssociation sourceAssociation, Map<RefObject, RefObject> objectsMap) {
    Procedure<RefAssociationLink> copyLinkOperation;
    AssociationName className;

    className = new AssociationNameImpl((Association) sourceAssociation.refMetaObject());

    copyLinkOperation = nameMapping.mapMetaAssociation(className, sourceExtent, targetExtent, objectsMap);

    for (RefAssociationLink source : (Collection<RefAssociationLink>) sourceAssociation.refAllLinks()) {
      try {
        copyLinkOperation.execute(source);
      } catch (Exception ex) {
        LOGGER.log(Level.SEVERE, "Copying link "+new PrintModelElement().execute( source )+" caused: ", ex);
      }
    }
  }
}