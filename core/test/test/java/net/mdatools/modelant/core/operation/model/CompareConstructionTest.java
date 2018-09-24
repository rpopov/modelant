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
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.logging.Level;

import javax.jmi.reflect.RefObject;
import javax.jmi.reflect.RefPackage;

import org.omg.uml13.foundation.core.Namespace;
import org.omg.uml13.foundation.core.UmlClass;

import net.mdatools.modelant.core.api.diff.InstanceDifference;
import net.mdatools.modelant.core.api.diff.ModelComparisonResult;
import net.mdatools.modelant.core.operation.element.Clear;
import net.mdatools.modelant.core.operation.element.ConstructSameInExtent;
import net.mdatools.modelant.core.operation.element.PrintModelElement;
import net.mdatools.modelant.core.operation.element.RetrieveAssociations;
import net.mdatools.modelant.core.operation.element.RetrieveAttributes;
import net.mdatools.modelant.core.operation.element.Set;

/**
 * Introduce attribute changes (value differences) and verify they are detected
 * @author Rusi Popov (popovr@mdatools.net)
 */
public class CompareConstructionTest extends CompareChangedModelsBase {

  private static final PrintModelElement PRINT_MODEL_ELEMENT = new PrintModelElement();
  private UmlClass sourceClass;
  private UmlClass targetClass;
  private Collection<String> attributes;
  private Collection<String> associations;
  private UmlClass newClass;
  private UmlClass[] allClasses;
  private Namespace sourceClassNamespace;
  private RefObject targetClassNamespace;

  /**
   * @see net.mdatools.modelant.core.operation.model.CompareChangedModelsBase#verifyChanges(javax.jmi.reflect.RefPackage, javax.jmi.reflect.RefPackage, java.util.Map, net.mdatools.modelant.core.api.diff.ModelComparisonResult)
   */
  protected void verifyChanges(RefPackage sourceModel,
                               RefPackage targetModel,
                               Map<RefObject, RefObject> correspondence,
                               ModelComparisonResult comparisonResult) {
    InstanceDifference diff;
    List<RefObject> addedElements;

    LOGGER.log(Level.FINE, "{0}", comparisonResult);

    assertEquals("Added 2 elements",
                 2,
                 comparisonResult.getAdded().size()); // the constructed and one with deleted namespace

    addedElements = new ArrayList<>();
    addedElements.add( comparisonResult.getAdded().get( 0 ).getElement() );
    addedElements.add( comparisonResult.getAdded().get( 1 ).getElement() );

    assertTrue("Added new class ",
               addedElements.contains(newClass));

    assertTrue("The target class with changed namespace is recognized as new ",
               addedElements.contains(targetClass));

    // The contents of the target class is not recognized as new, as it is consolidated in the added class

    assertEquals("Deleted element - the source class that lost its namespace",
                 1,
                 comparisonResult.getDeleted().size());

    assertEquals("Deleted element - the source class that lost its namespace",
                 sourceClass,
                 comparisonResult.getDeleted().get(0).getElement());

    // Deleted element - the contents of the source class lost its namespace, is, as it is consolidated in the deleted class

//    assertEquals("Changes in the source class's namespace are recognized as changes to ojects associated to the sourceClass' nested elements",
//                 1+sourceClass.getOwnedElement().size(),
//                 comparisonResult.getChanged().size());

    // Test the newly added class in the namespace of the targetClass
    diff = comparisonResult.findDiffX( sourceClassNamespace );
    assertNotNull("Expected changes in the source class namespace",diff);

    assertEquals("Changes namespace",
                 targetClassNamespace,
                 diff.getYObject());

    assertEquals("Changes only in one asssociation",
                 1,
                 diff.getAssociationDiffs().size());

    assertEquals("Changes in the owned elements",
                 "ownedElement",
                 diff.getAssociationDiffs().get( 0 ).getAssociationName());

    assertEquals("Added one element",
                 1,
                 diff.getAssociationDiffs().get( 0 ).getYMinusX().size());

    assertTrue("Deleted the source class",
               diff.getAssociationDiffs().get( 0 ).getXMinusY().contains(sourceClass));

  }


  /**
   * @see net.mdatools.modelant.core.operation.model.CompareChangedModelsBase#change(javax.jmi.reflect.RefPackage, javax.jmi.reflect.RefPackage, java.util.Map)
   */
  protected void change(RefPackage sourceModel,
                        RefPackage targetModel,
                        Map<RefObject, RefObject> correspondence) {
    String printed;

    // choose a UmlClass instance
    allClasses = correspondence
                .keySet()
                .stream()
                .filter( new Predicate<RefObject>() {
                           public boolean test(RefObject t) {
                             return (t instanceof UmlClass)
                                    && ((UmlClass) t).getName().equals("Bw Load External Data"); // getNamespace() != null
                           }
                         })
                .toArray(n -> new UmlClass[n]);
    sourceClass = allClasses[0];
    targetClass = (UmlClass) correspondence.get( sourceClass );

    attributes = new RetrieveAttributes().execute( sourceClass );
    assertFalse( "Expected a non-empty list of attributes", attributes.isEmpty() );

    associations = new RetrieveAssociations().execute( sourceClass );
    assertFalse( "Expected a non-empty list of associations", associations.isEmpty() );

    newClass = (UmlClass) new ConstructSameInExtent( targetModel ).execute( sourceClass );

    // newClass.setName( sourceClass.getName()+"Changed");
    printed = PRINT_MODEL_ELEMENT.execute( targetClass );

    // verify all attributes are printed, no matter of their value
    for (String attribute: attributes) {
      assertTrue("Expected attribute "+attribute+" was printed", printed.contains( attribute ));
    }

    // assign the newly constructed to the namespace of the target class
    new Set( "namespace", targetClass.getNamespace() ).execute( newClass );
    assertSame("Expected the namespace of the newly registered class was set",
               targetClass.getNamespace(),
               newClass.getNamespace());

    sourceClassNamespace = sourceClass.getNamespace();
    targetClassNamespace = correspondence.get( sourceClassNamespace );

    // clear the namespace of the target class, which will treat it as a new class together with its contents
    // The further consolidation of the changes as ModelDifferences will include them in the target class
    new Clear("namespace").execute( targetClass );
    assertSame("Expected the namespace of the newly registered class was not set",
               null,
               ((org.omg.uml13.foundation.core.ModelElement) targetClass).getNamespace());
  }
}
