/*
 * Copyright (c) 2001,2012 Rusi Popov, MDA Tools.net
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Rusi Popov (popovr@mdatools.net) - initial implementation
 */
package net.mdatools.modelant.core.util.map;

import java.util.Collection;
import java.util.HashSet;

/**
 * Map each key to a set of values
 * @param <Key> the class of the key
 * @param <Value> the class of the elements held in the set
 * @author Rusi Popov
 */
public class MapToSet<Key, Value> extends MapToCollection<Key, Value> {

  /**
   * @see net.mdatools.modelant.core.util.map.MapToCollection#createValuesCollection()
   */
  protected Collection<Value> createValuesCollection() {
    return new HashSet<Value>();
  }
}
