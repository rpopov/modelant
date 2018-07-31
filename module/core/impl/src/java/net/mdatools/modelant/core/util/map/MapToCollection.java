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

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Map each key to a collection of values
 * @param <K> the class of the key
 * @param <V> the class of the elements held in the collection
 */
public abstract class MapToCollection<K, V> implements Serializable {

  /**
   * The internal map representation.
   * INVARIANT:
   *  it maps keys to Collections of values
   */
  private final Map<K, Collection<V>> map = new HashMap<K, Collection<V>>();

  /**
   * @see java.util.Map#clear()
   */
  public final void clear() {
    map.clear();
  }


  /**
   * @see java.util.Map#containsKey(java.lang.Object)
   */
  public final boolean containsKey(K key) {
    return map.containsKey( key );
  }


  /**
   * @param key
   * @return null or a a non-empty list of values
   * @see java.util.Map#get(java.lang.Object)
   */
  public Collection<V> get(K key) {
    Collection<V> result;

    result = map.get( key );

    return result;
  }


  /**
   * @see java.util.Map#isEmpty()
   */
  public final boolean isEmpty() {
    return map.isEmpty();
  }


  /**
   * @see java.util.Map#keySet()
   */
  public final Set<K> keySet() {
    return map.keySet();
  }

  /**
   * @see java.util.Map#keySet()
   */
  public final Set<Map.Entry<K, Collection<V>>> entrySet() {
    return map.entrySet();
  }



  /**
   * @return the list of all actual mappings bound to this key.
   *         NOTE: this is different than the the inherited specification, where
   *               the previous value bound to the key is required to be returned
   * @see java.util.Map#put(java.lang.Object, java.lang.Object)
   */
  public final Collection<V> put(K key, V value) {
    Collection<V> result = get( key );

    if ( result == null ) {
      result = createValuesCollection();
      map.put( key, result );
    }
    result.add( value );
    return result;
  }


  public final Collection<V> put(K key, Collection<V> value) {
    Collection<V> result = get(key);

    if (result == null) {
      result = createValuesCollection();
      result.addAll(value);
      map.put(key, result);
    }
    return result;
  }


  /**
   * @see java.util.Map#putAll(java.util.Map)
   */
  public final void putAll(MapToCollection<K, V> t) {
    for (Map.Entry<K, Collection<V>> entry : t.entrySet()) {
      put( entry.getKey(), entry.getValue() );
    }
  }


  /**
   * Removes the whole collection bound to the key
   * @return the original values collection bound to that key
   * @see java.util.Map#remove(java.lang.Object)
   */
  public final Collection<V> remove(K key) {
    return map.remove( key );
  }

  /**
   * Removes the value from the collection for that key
   * @return true if actually removed the value
   * @see java.util.Map#remove(java.lang.Object)
   */
  public final boolean remove(K key, V value) {
    boolean result;
    Collection<V> values;

    values = get( key );
    if ( values != null ) {
      result = values.remove( value );
      if ( values.isEmpty() ) {
        map.remove( key );
      }
    } else {
      result = false;
    }
    return result;
  }

  /**
   * @return a collection of all values in this multi-value map
   * @see java.util.Map#values()
   */
  public final Collection<V> values() {
    Collection<V> result;
    Collection<V> values;

    result = createValuesCollection();

    for(Map.Entry<K, Collection<V>> entry : map.entrySet() ) {
      values = entry.getValue();

      if ( values != null ) {
        result.addAll( values );
      }
    }
    return result;
  }


  /**
   * This is a factory method for nested collections
   */
  protected abstract Collection<V> createValuesCollection();


  /**
   * @see java.lang.Object#toString()
   */
  public String toString() {
    StringBuilder result = new StringBuilder( 2048 );

    result//.append( getClass().getSimpleName() )
          .append( " {" );

    for(Map.Entry<K, Collection<V>> entry : map.entrySet() ) {
      result.append( "\r\n" )
            .append( entry.getKey() )
            .append( " = ")
            .append( entry.getValue() );
    }
    if (!map.isEmpty()) {
      result.append( "\r\n" );
    }
    result.append("}");
    return result.toString();
  }

  /**
   * @return convert this to a read-only map to collections
   */
  public final Map<K, Collection<V>> toMap() {
    return Collections.unmodifiableMap( map );
  }
}