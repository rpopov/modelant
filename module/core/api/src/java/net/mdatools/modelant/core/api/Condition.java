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
 * Any condition can be evaluated as many times as needed on the same arguments
 * and the result must always be the same. Thus, the conditions have no side effects.
 * Any arguments of the condition are provided in its constructor - any implementation
 * of this interface <b>does not</b> use setters to receive its parameter values.
 * <pre>
 *  Usage:
 *    condition = construct the condition
 *
 *    condition.eval(argument) as many times as needed
 * </pre>
 * @param <A> the type of the argument to evaluate the condition on
 * @author Rusi Popov
 */
public interface Condition<A> {

  /**
   * @param argument
   * @return true if this condition set up represents a tautology and false otherwise.
   * @throws IllegalArgumentException in any case of misconfiguration
   * @throws RuntimeException in any case of failed processing
   */
  boolean eval(A argument) throws RuntimeException, IllegalArgumentException;

  Condition TRUE = new Condition() {
    public boolean eval(Object argument) throws RuntimeException, IllegalArgumentException {
      return true;
    }
  };

  Condition FALSE = new Condition() {
    public boolean eval(Object argument) throws RuntimeException, IllegalArgumentException {
      return false;
    }
  };
}
