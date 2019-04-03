/*
 * Copyright (c) Rusi Popov, MDA Tools.net 2019
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Created on Apr 3, 2019
 */
package net.mdatools.modelant.core.condition;

import javax.jmi.reflect.RefObject;

import net.mdatools.modelant.core.api.Condition;

/**
 * Check that the argument's name attribute has a specific value. The name to search for could be null.
 * @author Rusi Popov
 */
public class HasFieldValue<T extends RefObject> implements Condition<T> {

  private final String name;
  private final Object value;

  /**
   * @param name not null, not empty filed name
   * @param value possibly null value to compare with
   */
  public HasFieldValue(String name, Object value) {
    assert name != null && !name.isEmpty() : "Expected a non-null, non-empty field name";

    this.name = name;
    this.value = value;
  }

  public boolean eval(RefObject argument) throws RuntimeException, IllegalArgumentException {
    boolean result;
    Object value;

    value = argument.refGetValue( name );

    result = value == null && value == null
             || value != null && value.equals( value );

    return result;
  }
}
