/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.repository.spi;

import net.mdatools.modelant.repository.api.ModelFactory;
import net.mdatools.modelant.repository.api.ModelRepository;

/**
 * The internal interface to initialize the factory and set it up to collaborate with the model repository.
 * Called only by the ModelRepository when looking it up.
 * @author Rusi Popov (popovr@mdatools.net)
 */
public interface MetamodelFactorySetup {

  /**
   * @return the non-null name of the metamodel this factory is for
   */
  String getMetamodelName();

  /**
   * Initialize the factory and load the metamodel into a unique extent
   * Called only by the ModelRepository when looking it up.
   * @param loader not null class loader to find the resource with the model to load
   * @param repository not null repository where to load the metamodel
   * @return the non-null root package the metamodel is loaded
   * @throws IllegalArgumentException in case of metamodel load failed
   */
  ModelFactory construct(ModelRepository repository, ClassLoader loader);
}