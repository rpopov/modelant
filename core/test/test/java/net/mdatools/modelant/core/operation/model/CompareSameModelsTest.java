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
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jmi.reflect.RefPackage;
import javax.jmi.xmi.MalformedXMIException;

import junit.framework.TestCase;
import net.mdatools.modelant.core.api.diff.ModelComparisonResult;
import net.mdatools.modelant.core.api.match.MatchingCriteria;
import net.mdatools.modelant.repository.api.ModelFactory;
import net.mdatools.modelant.repository.api.ModelRepository;
import net.mdatools.modelant.repository.api.ModelRepositoryFactory;

public class CompareSameModelsTest extends TestCase {

  public static final Logger LOGGER = Logger.getLogger(CompareSameModelsTest.class.getName());
  
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
   * Copy the model into target and compare the results NOT USINHG the correspondence between the original
   * and copied elements for model comparison.
   * @throws IOException
   * @throws MalformedXMIException
   */
  public void testCopmarison() throws IOException, MalformedXMIException {
    CompareModels compare;
    ModelComparisonResult comparisonResult;

    repository.readIntoExtent( sourceModel, "demo.xml", Thread.currentThread().getContextClassLoader() );
    repository.readIntoExtent( targetModel, "demo.xml", Thread.currentThread().getContextClassLoader() );

    // compare the models - without using the correspondence - they must be equal!
    compare = new CompareModels( MatchingCriteria.NAME_MATCH,
                                 MatchingCriteria.NONE,
                                 new ArrayList<>(), sourceModel );
    comparisonResult = compare.execute( targetModel );

    LOGGER.log(Level.FINE, "Result of comparison: {0}", comparisonResult );

    assertTrue("Expected no differences", comparisonResult.isExactMatch() );
  }

  protected void tearDown() {
    repository.shutdown();
  }
}
