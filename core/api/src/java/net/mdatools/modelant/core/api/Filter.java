/*
 * Copyright (c) Rusi Popov, MDA Tools.net 2019
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Created on Apr 6, 2019
 */
package net.mdatools.modelant.core.api;

import java.util.Collection;
import java.util.Collections;

import javax.jmi.reflect.RefObject;

/**
 * A general mechanism to restrict the contents of collections
 * @author Rusi Popov
 */
public interface Filter<T> extends Operation<Collection<T>> {
  /**
   * @return type-safe identity filter
   */
  static <T extends RefObject> Filter<T> identity() {
    return new Filter<T>() {
      public Collection<T> execute(Collection<T> argument) {
        return argument;
      }
    };
  }

  /**
   * @return type-safe empty filter
   */
  static <T extends RefObject> Filter<T> empty() {
    return new Filter<T>() {
      public Collection<T> execute(Collection<T> argument) {
        return Collections.emptyList();
      }
    };
  }
}
