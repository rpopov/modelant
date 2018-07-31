/*
 * Copyright (c) i:FAO AG 2018. All Rights Reserved.
 *
 * This SOURCE CODE FILE, which has been provided by i:FAO AG as part
 * of a product of i:FAO AG for use ONLY by licensed users of the product,
 * includes CONFIDENTIAL and PROPRIETARY information.
 *
 * Created on 5.03.2018 г.
 */
package net.mdatools.modelant.core.api.diff;

/**
 * A general mechanism to export a result of models comparison
 * @author Rusi Popov
 */
public interface Export {

  void export(ModelComparisonResult result);

  /**
   * Default implementation as of printing the default string represnetation
   */
  Export DEFAULT = new Export() {
    public void export(ModelComparisonResult result) {
      System.out.println(result);
    }
  };
}
