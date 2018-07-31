/*
 * Copyright (c) 2017 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Created on 10.12.2017
 */
package net.mdatools.modelant.repository.spi;

import net.mdatools.modelant.repository.api.ModelFactory;
import net.mdatools.modelant.repository.api.ModelRepository;

/**
 * The internal interface to initialize the factory and set it up to collaborate with the model repository.
 * Called only by the ModelRepository when looking it up.
 * @author Rusi Popov
 */
public interface MetamodelFactorySetup {

  /**
   * @return the non-null name of the metamodel this factory is for
   */
  String getMetamodelName();

  /**
   * Initialize the factory and load the metamodel into a unique extent
   * Called only by the ModelRepository when looking it up.
   * @param loader TODO
   * @return the non-null root package the metamodel is loaded
   * @throws IllegalArgumentException in case of metamodel load failed
   */
  ModelFactory construct(ModelRepository repository, ClassLoader loader);
}