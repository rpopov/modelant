/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.mof14.metamodel;

import java.io.File;
import java.io.IOException;

import javax.jmi.reflect.RefPackage;
import javax.jmi.xmi.MalformedXMIException;

import junit.framework.TestCase;
import net.mdatools.modelant.core.api.diff.ModelComparisonResult;
import net.mdatools.modelant.core.operation.model.CompareModels;
import net.mdatools.modelant.repository.api.ModelRepository;
import net.mdatools.modelant.repository.api.ModelRepositoryFactory;

/**
 * A common mechanism to test metamodels comparison
 * @author Rusi Popov
 */
abstract class Mof14CompareMetamodelsBase extends TestCase {

  private ModelRepository repository;
  private RefPackage sourceModel;
  private RefPackage targetModel;

  protected void setUp() throws Exception {
    repository = ModelRepositoryFactory.construct(new File("target"));

    // cleanup the previously instantiated extents
    repository.deleteExtent( "SOURCE" );
    repository.deleteExtent( "TARGET" );

    sourceModel = repository.constructMetamodelExtent( "SOURCE" );
    targetModel = repository.constructMetamodelExtent( "TARGET" );
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

    repository.readIntoExtent( sourceModel, getSourceMetamodelName(), Thread.currentThread().getContextClassLoader() );
    repository.readIntoExtent( targetModel, getTargetMetamodelName(), Thread.currentThread().getContextClassLoader() );

    compare = new CompareMof14Models( sourceModel );
    comparisonResult = compare.execute( targetModel );

    verify(comparisonResult);
  }

  /**
   * @return non-null file name of the source metamodel in XMI
   */
  protected abstract String getSourceMetamodelName();

  /**
   * @return non-null file name of the target metamodel in XMI
   */
  protected abstract String getTargetMetamodelName();

  /**
   * Test the results found
   * @param comparisonResult not null
   */
  protected abstract void verify(ModelComparisonResult comparisonResult);

  protected void tearDown() {
    repository.shutdown();
  }
}
