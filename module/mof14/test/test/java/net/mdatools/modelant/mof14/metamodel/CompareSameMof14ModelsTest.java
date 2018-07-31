/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.mof14.metamodel;

import java.util.logging.Level;

import net.mdatools.modelant.core.api.diff.ModelComparisonResult;

/**
 * Compare same model, loaded in 2 separate extents
 * @author Rusi Popov
 */
public class CompareSameMof14ModelsTest extends CompareMof14ModelsBase {

  public CompareSameMof14ModelsTest() {
    super( "01-12-02_Diff_modelant.xml",
           "01-12-02_Diff_modelant.xml" );
  }

  public void testSameModels() {
    ModelComparisonResult result;
    CompareMof14Models operation;

    operation = new CompareMof14Models( sourceModel );
    result = operation.execute( targetModel );

    LOGGER.log( Level.INFO, "Differences: {0}", result);

    assertTrue("Expected no differences", result.isExactMatch());
  }
}
