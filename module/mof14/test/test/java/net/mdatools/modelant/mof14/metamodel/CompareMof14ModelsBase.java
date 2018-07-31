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

import java.io.File;
import java.util.logging.Logger;

import javax.jmi.reflect.RefPackage;

import junit.framework.TestCase;
import net.mdatools.modelant.repository.api.ModelRepository;
import net.mdatools.modelant.repository.api.ModelRepositoryFactory;

public class CompareMof14ModelsBase extends TestCase {

  protected static Logger LOGGER = Logger.getLogger( CompareMof14ModelsBase.class.getName() );

  private ModelRepository repository;

  protected RefPackage sourceModel;
  protected RefPackage targetModel;

  private final String sourceModelName;
  private final String targetModelName;

  protected CompareMof14ModelsBase(String sourceModel, String targetModel) {
    this.sourceModelName = sourceModel;
    this.targetModelName = targetModel;
  }

  protected void setUp() throws Exception {
    repository = ModelRepositoryFactory.construct(new File("target"));

    sourceModel = repository.constructMetamodelExtent( "SOURCE" );
    repository.readIntoExtent( sourceModel, sourceModelName);

    targetModel = repository.constructMetamodelExtent( "TARGET" );
    repository.readIntoExtent( targetModel, targetModelName);
  }

  /**
   */
  protected void tearDown() throws Exception {
    repository.shutdown();
  }
}
