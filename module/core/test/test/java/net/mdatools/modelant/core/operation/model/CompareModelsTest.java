/*
 * Copyright (c) 2017 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Created on 03.12.2017
 */
package net.mdatools.modelant.core.operation.model;

import java.util.Map;

import javax.jmi.reflect.RefObject;
import javax.jmi.reflect.RefPackage;

import net.mdatools.modelant.core.api.diff.ModelComparisonResult;

public class CompareModelsTest extends CompareChangedModelsBase {

  /**
   * @see net.mdatools.modelant.core.operation.model.CompareChangedModelsBase#verifyChanges(javax.jmi.reflect.RefPackage, javax.jmi.reflect.RefPackage, java.util.Map, net.mdatools.modelant.core.api.diff.ModelComparisonResult)
   */
  protected void verifyChanges(RefPackage sourceModel, RefPackage targetModel, Map<RefObject, RefObject> correspondence,
                               ModelComparisonResult comparisonResult) {
    assertTrue("Expected no differences", comparisonResult.isExactMatch() );

    assertEquals("Expected that all model elements are found as corresponding",
                 correspondence.size(),
                 comparisonResult.getExactlyMatched().size());
  }

  /**
   * @see net.mdatools.modelant.core.operation.model.CompareChangedModelsBase#change(javax.jmi.reflect.RefPackage, javax.jmi.reflect.RefPackage, java.util.Map)
   */
  protected void change(RefPackage sourceModel, RefPackage targetModel, Map<RefObject, RefObject> correspondence) {
    // no change in the copy
  }
}
