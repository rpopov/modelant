/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.uml13.metamodel;

import net.mdatools.modelant.repository.spi.BaseModelFactory;

/**
 * Load the UML 1.3 metamodel.
 * Refer this factory with name: <b>UML13</b> <pre>
 *
 * repository = ModelRepositoryFactory.construct(workDirectory);
 * modelFactory = repository.loadMetamodel("UML13");
 * sourceExtent = modelFactory.instantiate("SOURCE");
 * repository.readIntoExtent( sourceExtent, sourceModel );
 *
 * </pre>
 * @author Rusi Popov
 */
public final class Uml13ModelFactory extends BaseModelFactory {

  /**
   * @see net.mdatools.modelant.repository.api.ModelFactory#getMetamodelName()
   */
  public String getMetamodelName() {
    return "UML13";
  }

  /**
   * @see net.mdatools.modelant.repository.spi.BaseModelFactory#getMetamodelPath()
   */
  protected String getMetamodelPath() {
    return "01-12-02_Diff_modelant.xml";
  }

  /**
   * @see net.mdatools.modelant.repository.spi.BaseModelFactory#getModelPackageName()
   */
  protected String getModelPackageName() {
    return "UML";
  }
}
