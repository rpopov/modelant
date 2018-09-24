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
import java.util.ArrayList;
import java.util.HashMap;

import javax.jmi.reflect.RefPackage;
import javax.jmi.xmi.MalformedXMIException;

import junit.framework.TestCase;
import net.mdatools.modelant.core.api.diff.ModelComparisonResult;
import net.mdatools.modelant.core.api.match.ConsideredEqual;
import net.mdatools.modelant.core.api.match.MatchingCriteria;
import net.mdatools.modelant.repository.api.ModelFactory;
import net.mdatools.modelant.repository.api.ModelRepository;
import net.mdatools.modelant.repository.api.ModelRepositoryFactory;

/**
 * Compare two different exports of the same model. We know that Rose XMI Export plugin
 * always generate unique IDs and references.
 * @author Rusi Popov (popovr@mdatools.net)
 */
public class CompareDifferentExportsOfTheSameModelTest extends TestCase {

  private ModelRepository repository;
  private ModelFactory metamodelFactory;
  private RefPackage sourceModel;
  private RefPackage targetModel;

  protected void setUp() throws Exception {
    repository = ModelRepositoryFactory.construct(new File("target"));

    // cleanup the previously instantiated extents
    repository.deleteExtent( "SOURCE" );
    repository.deleteExtent( "TARGET" );
    repository.deleteExtent( "UML13" );

    metamodelFactory = repository.loadMetamodel("UML13");

    sourceModel = metamodelFactory.instantiate("SOURCE");
    targetModel = metamodelFactory.instantiate("TARGET");
  }


  /**
   * Compare the exports without any assumptions
   * @throws IOException
   * @throws MalformedXMIException
   */
  public void testCopmarison() throws IOException, MalformedXMIException {
    CompareModels compare;
    ModelComparisonResult comparisonResult;

    repository.readIntoExtent( sourceModel, "demo2.xml", Thread.currentThread().getContextClassLoader() );
    repository.readIntoExtent( targetModel, "demo3.xml", Thread.currentThread().getContextClassLoader() );

    // compare the models - without using the correspondence - they must be equal!
    compare = new CompareModels( MatchingCriteria.NAME_AND_NAMESPACE_MATCH,
                                 MatchingCriteria.NONE,
                                 new ArrayList<>(), sourceModel );
    comparisonResult = compare.execute( targetModel );

    assertTrue("Expected no differences", comparisonResult.isExactMatch() );
  }

  protected void tearDown() {
    repository.shutdown();
  }
}
