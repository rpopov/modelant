/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.name;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jmi.model.Association;
import javax.jmi.reflect.JmiException;
import javax.jmi.reflect.RefAssociation;
import javax.jmi.reflect.RefAssociationLink;
import javax.jmi.reflect.RefObject;
import javax.jmi.reflect.RefPackage;

import net.mdatools.modelant.core.api.Procedure;
import net.mdatools.modelant.core.api.model.ConstructProcedure;
import net.mdatools.modelant.core.api.model.NameMapping;
import net.mdatools.modelant.core.api.name.AssociationName;
import net.mdatools.modelant.core.api.name.Name;
import net.mdatools.modelant.core.api.name.PackageName;

/**
 * A mechanism to locate a RefAssociation by its qualified name in the metamodel
 * @author Rusi Popov (popovr@mdatools.net)
 */
public class AssociationNameImpl extends NameImpl<PackageName> implements AssociationName {

  private static final Logger LOGGER = Logger.getLogger( AssociationNameImpl.class.getName() );

  public AssociationNameImpl(String packageName) {
    super(packageName);
  }

  public AssociationNameImpl(PackageName parent, String name) {
    super(parent, name);
  }

  public AssociationNameImpl(Association association) {
    super(new PackageNameImpl(association.getContainer()), association.getName());
  }

  /**
   * @param qualifiedName not null
   * @return the Association Name that represents the qualified name provided,
   * @throws IllegalArgumentException when qualifiedName is empty
   */
  public static AssociationName parseQualifiedClassName(String qualifiedName) throws IllegalArgumentException {
    AssociationName result;
    PackageName pack;
    String[] names;

    pack = null;
    names = qualifiedName.split( METAMODEL_PATH_SEPARATOR_PARSE );
    for (int i=0; i<names.length-1; i++) {
      pack = new PackageNameImpl( pack, names[i] );
    }
    if (names.length > 0) {
      result = new AssociationNameImpl(pack, names[names.length-1]);
    } else {
      result = null;
    }
    return result;
  }

  /**
   * @param rootPackage not null extent
   */
  public RefAssociation getMetaAssociation(RefPackage rootPackage) throws JmiException {
    RefAssociation result;
    RefPackage ownerPackage;

    assert rootPackage != null : "Expected a non-null package";

    if ( getOwner() == null ) {
      ownerPackage = rootPackage;
    } else {
      ownerPackage = getOwner().getMetaPackage( rootPackage );
    }

    try {
      result = ownerPackage.refAssociation( getName() );
    } catch (JmiException ex) {
      throw new IllegalArgumentException("Looking up the association "+ this
                                       + " reached " + PRINT_MODEL_ELEMENT.execute( ownerPackage )
                                       + " for which retrieving the nested class '"+getName()+"'"
                                       + " caused ",ex);
    }
    return result;
  }

  /**
   * @see Name#constructName(Name, String)
   */
  public Name<PackageName> constructName(PackageName parent, String name) {
    return new AssociationNameImpl(parent, name);
  }

  /**
   * @return non-null transformation of source model Links into target model links
   * @see net.mdatools.modelant.core.api.name.Name#constructTransfromation()
   */
  public ConstructProcedure<RefAssociationLink> constructTransfromation() {
    return newForwardLinkProduction();
  }

  /**
   * This represents a target model association
   * @return non-null producer of transformations of source model links to target model links in the same direction, e.g. the source of the produced
   *         link is the object that corresponds to the source of the original link, the same is for link targets
   */
  public ConstructProcedure<RefAssociationLink> newForwardLinkProduction() {
    return new ConstructProcedure<RefAssociationLink>() {

        public Procedure<RefAssociationLink> construct(RefPackage sourceExtent,
                                                       RefPackage targetExtent, Map<RefObject, RefObject> objectsMap, NameMapping valueMapping) throws RuntimeException, IllegalArgumentException {
          Procedure<RefAssociationLink> result;
          RefAssociation associationClass;
          Association association;

          associationClass = AssociationNameImpl.this.getMetaAssociation(targetExtent);
          association = (Association) associationClass.refMetaObject();

          if ( association.isDerived() ) { // the derived target associations are NOT copied
            result = Procedure.EMPTY;

          } else {
            result = new Procedure<RefAssociationLink>() {
              public void execute(RefAssociationLink link) throws RuntimeException, IllegalArgumentException {
                RefObject target;
                RefObject source;

                source = objectsMap.get(link.refFirstEnd());
                target = objectsMap.get(link.refSecondEnd());

                if ( source != null ) {
                  if ( target != null ) {
                    associationClass.refAddLink(source, target);
                  } else {
                    LOGGER.log( Level.INFO, "{0} mapped to null", PRINT_MODEL_ELEMENT.execute( link.refSecondEnd() ));
                  }
                } else {
                  LOGGER.log( Level.INFO, "{0} mapped to null", PRINT_MODEL_ELEMENT.execute( link.refFirstEnd() ));
                }
              }
            };
          }
          return result;
        }
   };
  }

  /**
   * This represents a target model association
   * @return non-null producer of transformations of source model links to target model links in the opposite direction, e.g. the source of the produced
   *         link is the object that corresponds to the target of the original link, the same is for link targets
   */
  public ConstructProcedure<RefAssociationLink> newBackwardLinkProduction() {
    return new ConstructProcedure<RefAssociationLink>() {

        public Procedure<RefAssociationLink> construct(RefPackage sourceExtent,
                                                       RefPackage targetExtent, Map<RefObject, RefObject> objectsMap, NameMapping valueMapping) throws RuntimeException, IllegalArgumentException {
          Procedure<RefAssociationLink> result;
          RefAssociation associationClass;
          Association association;

          associationClass = AssociationNameImpl.this.getMetaAssociation(targetExtent);
          association = (Association) associationClass.refMetaObject();

          if ( association.isDerived() ) { // the derived target associations are NOT copied
            result = Procedure.EMPTY;

          } else {
            result = new Procedure<RefAssociationLink>() {
              public void execute(RefAssociationLink link) throws RuntimeException, IllegalArgumentException {
                RefObject target;
                RefObject source;

                source = objectsMap.get(link.refFirstEnd());
                target = objectsMap.get(link.refSecondEnd());

                if ( source != null ) {
                  if ( target != null ) {
                    associationClass.refAddLink(target, source);
                  } else {
                    LOGGER.log( Level.INFO, "{0} mapped to null", PRINT_MODEL_ELEMENT.execute( link.refSecondEnd() ));
                  }
                } else {
                  LOGGER.log( Level.INFO, "{0} mapped to null", PRINT_MODEL_ELEMENT.execute( link.refFirstEnd() ));
                }
              }
            };
          }
          return result;
        }
   };
  }
}