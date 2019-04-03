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

/**
 * Check that the argument's name attribute has a specific value. The name to search for could be null.
 * @author Rusi Popov
 */
public class IsNamed<T extends RefObject> extends HasFieldValue<T> {

  /**
   * @param name possibly null name to compare with
   */
  public IsNamed(String name) {
    super("name", name);
  }
}
