/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.operation.model;

import java.util.Map;
import java.util.function.Predicate;

import javax.jmi.reflect.RefObject;
import javax.jmi.reflect.RefPackage;

import org.omg.uml13.foundation.core.UmlClass;

import net.mdatools.modelant.core.api.diff.ModelComparisonResult;

/**
 * Introduce attribute changes (value differences) and verify they are detected
 * @author Rusi Popov
 */
public class CompareChangedAttributesTest extends CompareChangedModelsBase {

  private UmlClass changedClass;

  /**
   * @see net.mdatools.modelant.core.operation.model.CompareChangedModelsBase#verifyChanges(javax.jmi.reflect.RefPackage, javax.jmi.reflect.RefPackage, java.util.Map, net.mdatools.modelant.core.api.diff.ModelComparisonResult)
   */
  protected void verifyChanges(RefPackage sourceModel,
                               RefPackage targetModel,
                               Map<RefObject, RefObject> correspondence,
                               ModelComparisonResult comparisonResult) {
    assertEquals("Expected 1 change detected",
                 1,
                 comparisonResult.getChanged().size());
    assertSame("Expcetd the changed element detected is changedClass",
               changedClass,
               comparisonResult.getChanged().get(0).getYObject());
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
                                         return t instanceof UmlClass;
                                       }
                                      })
                              .findFirst()
                              .get();
    changedClass.setAbstract( !changedClass.isAbstract());
  }
}
