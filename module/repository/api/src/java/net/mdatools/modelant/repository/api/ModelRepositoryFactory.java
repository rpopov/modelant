/*
 * Copyright (c) 2017 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Created on 17.08.2017
 */
package net.mdatools.modelant.repository.api;

import java.io.File;
import java.util.ServiceLoader;

import net.mdatools.modelant.repository.spi.ModelRepositorySetup;

/**
 * A factory of Model Repositories
 * @author Rusi Popov
 */
public final class ModelRepositoryFactory {

  /**
   * @param workingDir the working directory, where to create the repository files.
   *        When null provided, the current directory is assumed.
   *        On shutting down the repository its files are deleted.
   * @return non-null repository, if available, otherwise an exception is thrown
   */
  public static ModelRepository construct(File workingDir) {
    ModelRepositorySetup result;

    result = ServiceLoader.load( ModelRepositorySetup.class ).iterator().next();
    result.initialize( workingDir );
    return result;
  }

  /**
   * @param workingDir the working directory, where to create the repository files.
   *        When null provided, the current directory is assumed.
   *        On shutting down the repository its files are deleted.
   * @param loader not null class loader to locate the implementation in
   * @return non-null repository, if available, otherwise an exception is thrown
   */
  public static ModelRepository construct(File workingDir, ClassLoader loader) {
    ModelRepositorySetup result;

    result = ServiceLoader.load( ModelRepositorySetup.class, loader ).iterator().next();
    result.initialize( workingDir );
    return result;
  }
}
