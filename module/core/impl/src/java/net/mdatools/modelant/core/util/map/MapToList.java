/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.util.map;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Map each key to a list of values
 * @param <K> the class of the key
 * @param <V> the class of the elements held in the list
 * @author Rusi Popov
 */
public class MapToList<K, V> extends MapToCollection<K, V> {

  /**
   * @see net.mdatools.modelant.core.util.map.MapToCollection#createValuesCollection()
   */
  protected Collection<V> createValuesCollection() {
    return new ArrayList<V>();
  }
}
