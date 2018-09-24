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
 * The implementation of the Decorator design pattern on Operations.
 * Let any decorator subclass it.
 * @author Rusi Popov (popovr@mdatools.net)
 */
public final class Compose<T> implements Operation<T> {

  private final Operation<T> decorator;
  private final Operation<T> decorated;

  /**
   * @param decorated non-null operation to decorate.
   * Note that the IdentityOperation can be used in Null Object pattern.
   */
  public Compose(Operation<T> decorator, Operation<T> decorated) {
    if (decorator == null) {
      throw new IllegalArgumentException("Expected a non-null decorator operation");
    }
    this.decorator = decorator;

    if (decorated == null) {
      throw new IllegalArgumentException("Expected a non-null decorated operation");
    }
    this.decorated = decorated;
  }


  /**
   * @see net.mdatools.modelant.core.api.Function#execute(java.lang.Object)
   */
  public final T execute(T argument) throws RuntimeException, IllegalArgumentException {
    return decorator.execute(decorated.execute(argument));
  }
}