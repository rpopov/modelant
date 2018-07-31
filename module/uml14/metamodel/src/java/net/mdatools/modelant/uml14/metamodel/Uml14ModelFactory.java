/*
 * Copyright (c) 2017 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Created on 10.12.2017
 */
package net.mdatools.modelant.uml14.metamodel;

import net.mdatools.modelant.repository.spi.BaseModelFactory;

/**
 * Load the UML 1.3 metamodel.
 * Refer this factory with name: <b>UML14</b> <pre>
 *
 *   repository = ModelRepositoryFactory.construct();
 *   repository.initialize( storage file );
 *   modelFactory = repository.initialize("UML14");
 *   model = modelFactory.instantiate("model extent name");
 *
 *   repository.readIntoExtent(model, "model file path");
 *    or
 *   modelFactory.readModel(model, "model file path");
 *
 * </pre>
 * @author Rusi Popov
 */
public final class Uml14ModelFactory extends BaseModelFactory {

  /**
   * @see net.mdatools.modelant.repository.api.ModelFactory#getMetamodelName()
   */
  public String getMetamodelName() {
    return "UML14";
  }

  /**
   * @see net.mdatools.modelant.repository.spi.BaseModelFactory#getMetamodelPath()
   */
  protected String getMetamodelPath() {
    return "01-02-15_Diff_modelant.xml";
  }

  /**
   * @see net.mdatools.modelant.repository.spi.BaseModelFactory#getModelPackageName()
   */
  protected String getModelPackageName() {
    return "UML";
  }
}
