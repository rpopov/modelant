/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.operation.model;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.Predicate;
import java.util.logging.Level;

import javax.jmi.reflect.RefObject;
import javax.jmi.reflect.RefPackage;

import org.omg.uml13.foundation.core.Generalization;
import org.omg.uml13.foundation.core.UmlClass;

import net.mdatools.modelant.core.api.diff.InstanceDifference;
import net.mdatools.modelant.core.api.diff.ModelComparisonResult;

/**
 * Introduce attribute changes (value differences) and verify they are detected
 * @author Rusi Popov
 */
public class CompareChangedNamesTest extends CompareChangedModelsBase {

  private UmlClass changedClass;

  private RefObject parentClass;

  /**
   * @see net.mdatools.modelant.core.operation.model.CompareChangedModelsBase#verifyChanges(javax.jmi.reflect.RefPackage, javax.jmi.reflect.RefPackage, java.util.Map, net.mdatools.modelant.core.api.diff.ModelComparisonResult)
   */
  protected void verifyChanges(RefPackage sourceModel,
                               RefPackage targetModel,
                               Map<RefObject, RefObject> correspondence,
                               ModelComparisonResult comparisonResult) {
    InstanceDifference diff;

    LOGGER.log(Level.FINE, "{0}", comparisonResult);

    assertEquals("Added element detected",
                 1,
                 comparisonResult.getAdded().size());

    assertEquals("Deleted element detected",
                 1,
                 comparisonResult.getDeleted().size());

    assertEquals("Changed elements detected",
                 2,
                 comparisonResult.getChanged().size()); // the generalization and the class' namespace

    diff = comparisonResult.findDiffY(parentClass);

    assertNotNull("Expected a change found on the the superclass", diff);

    assertSame("Expected the changed element is the superclass",
               1,
               diff.getAssociationDiffs().size());

    assertSame("Expected the found changes are in 1 element - the generalization of the changed class, which is found as added/deleted",
               1,
               diff.getAssociationDiffs().get(0).getXMinusY().size());

    assertSame("Expected the found changes are in 1 element - the generalization of the changed class, which is found as added/deleted",
               1,
               diff.getAssociationDiffs().get(0).getYMinusX().size());


    diff = comparisonResult.findDiffY(changedClass.getNamespace());

    assertNotNull("Expected the changed element is the owner package", diff);

    assertSame("Expected the changed element is the package of the changed class",
               1,
               diff.getAssociationDiffs().size());

    assertSame("Expected the found changes are in 1 element - the package of the deleted/source class",
               1,
               diff.getAssociationDiffs().get(0).getXMinusY().size());

    assertSame("Expected the found changes are in 1 element - the package of the added/target class",
               1,
               diff.getAssociationDiffs().get(0).getYMinusX().size());

  }


  /**
   * @see net.mdatools.modelant.core.operation.model.CompareChangedModelsBase#change(javax.jmi.reflect.RefPackage, javax.jmi.reflect.RefPackage, java.util.Map)
   */
  protected void change(RefPackage sourceModel,
                        RefPackage targetModel,
                        Map<RefObject, RefObject> correspondence) {
    // choose a UmlClass instance
    changedClass = (UmlClass) correspondence
                              .values()
                              .stream()
                              .filter( new Predicate<RefObject>() {
                                         public boolean test(RefObject t) {
                                           return t instanceof UmlClass
                                                  && ((UmlClass) t).getName().equals("Bw Load External Data"); // getNamespace() != null
                                         }
                                       })
                              .findFirst()
                              .get();
    changedClass.setName( changedClass.getName()+"Changed");

    assertEquals("There is a single superclass",1, changedClass.getGeneralization().size());

    parentClass = new ArrayList<Generalization>(changedClass.getGeneralization()).get(0).getParent();

    // affected are also the nested elements, whose namespace changes and thus, they will be revealed as
    // added/deleted (as the namespace association pertains to the comparison criteria) they will be reported,
    // but they will be collected in one ModelDifference - added and one ModelDifference deleted
  }
}
