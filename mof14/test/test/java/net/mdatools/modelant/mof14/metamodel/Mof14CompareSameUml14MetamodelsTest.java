/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.mof14.metamodel;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.mdatools.modelant.core.api.diff.ModelComparisonResult;

/**
 * Test the metamodels comparison by comparing UML 1.4 to itself. Prove the model comparison
 * algorithm works correctly by finding no differences.
 *
 * @author Rusi Popov (popovr@mdatools.net)
 */
public class Mof14CompareSameUml14MetamodelsTest extends Mof14CompareMetamodelsBase {

	public static final Logger LOGGER = Logger.getLogger(Mof14CompareSameUml14MetamodelsTest.class.getName());

  /**
   * @see net.mdatools.modelant.mof14.metamodel.Mof14CompareMetamodelsBase#getSourceMetamodelName()
   */
  protected String getSourceMetamodelName() {
    return "01-02-15_Diff_modelant.xml";
  }


  /**
   * @see net.mdatools.modelant.mof14.metamodel.Mof14CompareMetamodelsBase#getTargetMetamodelName()
   */
  protected String getTargetMetamodelName() {
    return getSourceMetamodelName();
  }


  /**
   * @see net.mdatools.modelant.mof14.metamodel.Mof14CompareMetamodelsBase#verify(net.mdatools.modelant.core.api.diff.ModelComparisonResult)
   */
  protected void verify(ModelComparisonResult comparisonResult) {
    LOGGER.log(Level.FINE, "Result of comparison: {0}", comparisonResult );

    assertTrue( "Expected no cahnges found", comparisonResult.isExactMatch() );
  }
}
