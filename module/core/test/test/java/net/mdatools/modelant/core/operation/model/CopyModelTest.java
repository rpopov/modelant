/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.operation.model;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.jmi.reflect.RefObject;
import javax.jmi.reflect.RefPackage;
import javax.jmi.xmi.MalformedXMIException;

import junit.framework.TestCase;
import net.mdatools.modelant.core.api.diff.ModelComparisonResult;
import net.mdatools.modelant.core.api.match.ConsideredEqual;
import net.mdatools.modelant.core.api.match.MatchingCriteria;
import net.mdatools.modelant.repository.api.ModelFactory;
import net.mdatools.modelant.repository.api.ModelRepository;
import net.mdatools.modelant.repository.api.ModelRepositoryFactory;

public class CopyModelTest extends CompareChangedModelsBase {

  /**
   * Copy the model into target and use the correspondence between the original an copied elements for
   * model comparison.
   * @throws IOException
   */
  protected void verifyChanges(RefPackage sourceModel,
                               RefPackage targetModel,
                               Map<RefObject, RefObject> correspondence,
                               ModelComparisonResult comparisonResult) throws IOException {
    assertTrue("Expected no differences", comparisonResult.isExactMatch() );

    getRepository().writeExtent(targetModel, new File("target/copy.xml"), ModelRepository.DEFAULT_XMI_VERSION);
  }


  protected void change(RefPackage sourceModel, RefPackage targetModel, Map<RefObject, RefObject> correspondence) {
    // nothing to change
  }
}
