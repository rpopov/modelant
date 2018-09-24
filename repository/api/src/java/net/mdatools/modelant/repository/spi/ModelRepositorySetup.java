/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.repository.spi;

import java.io.File;

import net.mdatools.modelant.repository.api.ModelRepository;
import net.mdatools.modelant.repository.api.ModelRepositoryFactory;

/**
 * Initialize the MetaData Repository to work in.
 * @author Rusi Popov (popovr@mdatools.net)
 */
public interface ModelRepositorySetup extends ModelRepository {

  /**
   * Used by the {@link ModelRepositoryFactory} only
   * On shutdown remove the storage files.
   * @param workDir possibly null directory name where to keep the repository files
   *        When null provided, the current directory is assumed. The file and needed directories
   *        will be created, when missing
   */
  void initialize(File workDir);

}