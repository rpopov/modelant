/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.api.operation;

import net.mdatools.modelant.core.api.Operation;

/**
 * Identity Operation. Applies Null Object pattern.
 * @author Rusi Popov (popovr@mdatools.net)
 */
public final class Identity<T> implements Operation<T> {

  /**
   * @param arg0 not null
   * @return arg0 not null
   * @see net.mdatools.modelant.core.api.Function#execute(java.lang.Object)
   */
  public T execute(T arg0) {
    if (arg0 == null) {
      throw new IllegalArgumentException("Expected a non-null argument");
    }
    return arg0;
  }
}
