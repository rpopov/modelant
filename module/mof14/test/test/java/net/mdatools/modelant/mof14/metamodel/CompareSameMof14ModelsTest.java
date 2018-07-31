/*
 * Copyright (c) i:FAO AG 2018. All Rights Reserved.
 *
 * This SOURCE CODE FILE, which has been provided by i:FAO AG as part
 * of a product of i:FAO AG for use ONLY by licensed users of the product,
 * includes CONFIDENTIAL and PROPRIETARY information.
 *
 * Created on 18.02.2018 г.
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
