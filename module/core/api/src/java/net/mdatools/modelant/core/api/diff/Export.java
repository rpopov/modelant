/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
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
