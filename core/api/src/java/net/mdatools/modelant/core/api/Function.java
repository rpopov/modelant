/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.api;

/**
 * A single-state (constant) operation, so once constructed, it could be used as many times as needed.
 * Any arguments of the operation are provided in its constructor - any implementation
 * of this interface <b>does not</b> use setters to receive its parameter values.
 * <pre>
 *  Usage:
 *    operation = construct the operation
 *
 *    result = operation.execute(argument)
 * </pre>
 * @param <A> argument type
 * @param <R> result type
 * @author Rusi Popov (popovr@mdatools.net)
 */
public interface Function<A,R> {

  /**
   * @param argument to apply this function (instance) on
   * @return the operation's result, if any
   * @throws RuntimeException
   * @throws IllegalArgumentException when the operation is not set up
   */
  R execute(A argument) throws RuntimeException, IllegalArgumentException;

  /**
   * @see Object#toString()
   */
  String toString();

  Function IDENTITY = new Function() {
    public Object execute(Object argument) throws RuntimeException, IllegalArgumentException {
      return argument;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
      return "Identity function";
    }
  };
}
