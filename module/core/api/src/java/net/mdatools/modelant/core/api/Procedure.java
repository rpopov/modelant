/*
 * Copyright (c) 2017 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Created on 01.07.2017
 */
package net.mdatools.modelant.core.api;

/**
 * A single-state (constant) procedure, so once constructed, it could be used as many times as needed.
 * Any arguments of the operation are provided in its constructor - any implementation
 * of this interface <b>does not</b> use setters to receive its parameter values.
 * <pre>
 *  Usage:
 *    procedure = construct the procedure
 *
 *    procedure.execute(argument)
 * </pre>
 * Implement the {@link Object#toString()} method in order to describe the problem
 * @param <A> argument type
 * @author Rusi Popov
 */
public interface Procedure<A> {

  /**
   * @param argument
   * @throws RuntimeException
   * @throws IllegalArgumentException when the operation is not set up
   */
  void execute(A argument) throws RuntimeException, IllegalArgumentException;

  Procedure EMPTY = new Procedure() {
    public void execute(Object argument) throws RuntimeException, IllegalArgumentException {
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
      return "Empty procedure";
    }
  };

  /**
   * @see Object#toString()
   */
  String toString();
}
