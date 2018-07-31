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

public class CompareMof14ModelsUml13toUml14Test extends CompareMof14ModelsBase {

  public CompareMof14ModelsUml13toUml14Test() {
    super("01-12-02_Diff_modelant.xml",
          "01-02-15_Diff_modelant.xml");
  }

  public void testUml13toUml14Models() throws Exception {
    ModelComparisonResult result;
    CompareMof14Models operation;

    operation = new CompareMof14Models( sourceModel );
    result = operation.execute( targetModel );

    LOGGER.log( Level.INFO, "Differences: {0}", result);

    assertFalse("Expected differences", result.isExactMatch());
    assertEquals("Expected no matches", 17, result.getExactlyMatched().size());
  }
}
