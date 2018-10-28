/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.mof14.metamodel;

import net.mdatools.modelant.repository.spi.BaseModelFactory;

/**
 * Load the MOF 1.4 metamodel as a regular model.
 * Refer this factory with name: <b>UML13</b> <pre>
 *
 *   repository = ModelRepositoryFactory.construct();
 *   repository.initialize( storage file );
 *   metamodelFactory = repository.initialize("MOF14");
 *   model = metamodelFactory.instantiate("model extent name");
 *
 *   repository.readIntoExtent(model, "model file path");
 *    or
 *   metamodelFactory.readModel(model, "model file path");
 * </pre>
 * @author Rusi Popov (popovr@mdatools.net)
 */
public final class Mof14ModelFactory extends BaseModelFactory<javax.jmi.model.ModelPackage> {

  /**
   * @see net.mdatools.modelant.repository.spi.ModelFactorySetup#getMetamodelName()
   */
  public String getMetamodelName() {
    return "MOF14";
  }

  /**
   * @see net.mdatools.modelant.repository.spi.BaseModelFactory#getMetamodelPath()
   */
  protected String getMetamodelPath() {
    return "mof.xml";
  }

  /**
   * @see net.mdatools.modelant.repository.spi.BaseModelFactory#getModelPackageName()
   */
  protected String getModelPackageName() {
    return "MOF14";
  }
}
