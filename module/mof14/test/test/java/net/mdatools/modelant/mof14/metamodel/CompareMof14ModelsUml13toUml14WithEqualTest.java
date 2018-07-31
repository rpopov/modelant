/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.mof14.metamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import net.mdatools.modelant.core.api.diff.ModelComparisonResult;
import net.mdatools.modelant.core.api.match.ConsideredEqual;
import net.mdatools.modelant.core.operation.model.match.Equal;

public class CompareMof14ModelsUml13toUml14WithEqualTest extends CompareMof14ModelsBase {

  public CompareMof14ModelsUml13toUml14WithEqualTest() {
    super("01-12-02_Diff_modelant.xml",
          "01-02-15_Diff_modelant.xml");
  }

  public void testUml13toUml14WithExplicitMatchModels() throws Exception {
    ModelComparisonResult result;
    List<ConsideredEqual> bindings;
    CompareMof14Models operation;

    // define equals
    bindings = new ArrayList<>();
    bindings.add(new Equal("Foundation::Data_Types", "Data_Types"));
    bindings.add(new Equal("Foundation::Core", "Core"));
    bindings.add(new Equal("Foundation::Extension_Mechanisms", "Core"));
    bindings.add(new Equal("Behavioral_Elements::Common_Behavior", "Common_Behavior"));
    bindings.add(new Equal("Behavioral_Elements::Use_Cases", "Use_Cases"));
    bindings.add(new Equal("Behavioral_Elements::State_Machines", "State_Machines"));
    bindings.add(new Equal("Behavioral_Elements::Collaborations", "Collaborations"));
    bindings.add(new Equal("Behavioral_Elements::Activity_Graphs", "Activity_Graphs"));
    bindings.add(new Equal("Model_Management", "Model_Management"));
    bindings.add(new Equal("PrimitiveTypes", "PrimitiveTypes"));

    operation = new CompareMof14Models( bindings, sourceModel );
    result = operation.execute( targetModel );

    LOGGER.log( Level.INFO, "Differences: {0}", result);

    assertEquals("Expected differences", 468, result.getExactlyMatched().size());
  }
}
