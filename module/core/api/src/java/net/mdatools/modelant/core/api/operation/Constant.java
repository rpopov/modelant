/*
 * Copyright (c) 2017 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Created on 29.10.2017
 */
package net.mdatools.modelant.core.api.operation;

import net.mdatools.modelant.core.api.Operation;

/**
 * This is a constant operation, returning one and the same constant value.
 * Used with decorators and composites.
 * @param <T> the type of the constant this operation represents
 * @author Rusi Popov
 */
public class Constant<T> implements Operation<T> {

  private final T constant;


  /**
   * @param constant not null
   */
  public Constant(T constant) {
    if (constant== null) {
      throw new IllegalArgumentException("Expected a non-null constant value");
    }
    this.constant = constant;
  }


  /**
   * @return constant
   * @see net.mdatools.modelant.core.api.Function#execute(java.lang.Object)
   */
  public T execute(T argument) {
    return constant;
  }
}
