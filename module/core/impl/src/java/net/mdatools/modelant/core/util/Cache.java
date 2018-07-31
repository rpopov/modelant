/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.util;

import java.util.Iterator;

import javax.jmi.reflect.RefClass;
import javax.jmi.reflect.RefObject;

import net.mdatools.modelant.core.util.map.MapToList;

/**
 * A cache of Key (1)-to-Value(Many) map of model elements, usually representing associations that are not supported directly in the model
 */
public abstract class Cache<Key extends RefObject,
                            Value extends RefObject> extends MapToList<Key, Value>{

  /**
   * This method registers in the cache all instances of that class
   * @param classProxy not null
   */
  public final void load(RefClass classProxy) {
    Iterator iterator;
    RefObject refObject;

    iterator = classProxy.refAllOfClass().iterator();
    while ( iterator.hasNext() ) {
      refObject = (RefObject) iterator.next();

      register(refObject);
    }
  }

  public abstract void register(RefObject refObject);
  public abstract void unregister(RefObject refObject);
}