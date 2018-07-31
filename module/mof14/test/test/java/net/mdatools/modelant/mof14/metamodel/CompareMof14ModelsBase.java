/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
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
