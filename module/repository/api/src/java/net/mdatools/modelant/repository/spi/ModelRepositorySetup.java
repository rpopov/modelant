/*
 * Copyright (c) i:FAO AG 2018. All Rights Reserved.
 *
 * This SOURCE CODE FILE, which has been provided by i:FAO AG as part
 * of a product of i:FAO AG for use ONLY by licensed users of the product,
 * includes CONFIDENTIAL and PROPRIETARY information.
 *
 * Created on 25.02.2018 г.
 */
package net.mdatools.modelant.repository.spi;

import java.io.File;

import net.mdatools.modelant.repository.api.ModelRepository;

/**
 * Initialize the MetaData Repository to work in.
 * @author Rusi Popov
 */
public interface ModelRepositorySetup extends ModelRepository {

  /**
   * Used by the {@link ModelRepositiryFactory} only
   * On shutdown remove the storage files.
   * @param workDir possibly null directory name where to keep the repository files
   *        When null provided, the current directory is assumed. The file and needed directories
   *        will be created, when missing
   */
  void initialize(File workDir);

}