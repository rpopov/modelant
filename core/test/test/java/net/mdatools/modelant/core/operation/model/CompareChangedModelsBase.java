/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.operation.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Logger;

import javax.jmi.reflect.RefObject;
import javax.jmi.reflect.RefPackage;

import junit.framework.TestCase;
import net.mdatools.modelant.core.api.diff.ModelComparisonResult;
import net.mdatools.modelant.core.api.match.MatchingCriteria;
import net.mdatools.modelant.core.operation.element.RetrieveAssociations;
import net.mdatools.modelant.core.operation.element.RetrieveAttributes;
import net.mdatools.modelant.repository.api.ModelFactory;
import net.mdatools.modelant.repository.api.ModelRepository;
import net.mdatools.modelant.repository.api.ModelRepositoryFactory;

abstract class CompareChangedModelsBase extends TestCase {

  public Logger LOGGER = Logger.getLogger( CompareChangedModelsBase.class.getName() );

  private ModelRepository repository;
  private ModelFactory metamodelFactory;
  private RefPackage sourceModel;
  private RefPackage targetModel;

  protected final RetrieveAttributes GET_ATTRIBUTES = new RetrieveAttributes();
  protected final RetrieveAssociations GET_ASSOCIATIONS = new RetrieveAssociations();

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
   * Copy the model into target, alter the model and detect the changes.
   */
  public void testCopyAndCopmarison() throws Exception {
    CopyModel copy;
    CompareModels compare;
    Map<RefObject, RefObject> correspondence;
    ModelComparisonResult comparisonResult;

    repository.readIntoExtent( sourceModel, "demo.xml", Thread.currentThread().getContextClassLoader() );

    copy = new CopyModel( sourceModel );
    correspondence = copy.execute( targetModel );

    change(sourceModel, targetModel, correspondence);

    // compare the models - without using the correspondence - they must be equal!
    compare = new CompareModels( MatchingCriteria.NAME_AND_NAMESPACE_MATCH,
                                 MatchingCriteria.NONE,
                                 new ArrayList<>(),
                                 sourceModel );
    comparisonResult = compare.execute( targetModel );

    verifyChanges(sourceModel, targetModel, correspondence, comparisonResult);
  }

  /**
   * Verify that the changes were detected
   * @param sourceModel
   * @param targetModel
   * @param correspondence
   * @param comparisonResult
   * @throws Exception
   */
  protected abstract void verifyChanges(RefPackage sourceModel,
                                        RefPackage targetModel,
                                        Map<RefObject, RefObject> correspondence,
                                        ModelComparisonResult comparisonResult) throws Exception;

  /**
   * Introduce changes in the targetModel
   * @param sourceModel
   * @param targetModel
   * @param correspondence
   */
  protected abstract void change(RefPackage sourceModel,
                                 RefPackage targetModel,
                                 Map<RefObject, RefObject> correspondence);


  protected void tearDown() {
    repository.shutdown();
  }


  protected final ModelRepository getRepository() {
    return repository;
  }
}
