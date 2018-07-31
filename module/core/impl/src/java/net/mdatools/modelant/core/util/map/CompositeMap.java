/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.util.map;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * This class is a composite Map implementation, so that when creating a
 * new map form another, this implementation does not copy the original,
 * but any access is delegated to the wrapped one.
 * @author Rusi Popov
 */
public class CompositeMap<K,V> implements Map<K,V> {

  private final Map<K,V> wrapped;

  /**
   * Any local changes
   */
  private final Map<K,V> local = new HashMap<>();

  /**
   *
   */
  public CompositeMap(Map<K,V> wrapped) {
    this.wrapped = wrapped;
  }

  /**
   * @see java.util.Map#clear()
   */
  public void clear() {
    wrapped.clear();
    local.clear();
  }

  /**
   * @see java.util.Map#containsKey(java.lang.Object)
   */
  public boolean containsKey(Object key) {
    return local.containsKey( key ) || wrapped.containsKey( key );
  }

  /**
   * @see java.util.Map#containsValue(java.lang.Object)
   */
  public boolean containsValue(Object value) {
    return local.containsValue( value ) || wrapped.containsValue( value );
  }

  /**
   * @see java.util.Map#get(java.lang.Object)
   */
  public V get(Object key) {
    V result;

    if ( local.containsKey( key ) ) { // handles key-to-null local bindings
      result = local.get( key );
    } else {
      result = wrapped.get( key );
    }
    return result;
  }

  /**
   * @see java.util.Map#entrySet()
   */
  public Set entrySet() {
    Set result = new HashSet();
    result.addAll( wrapped.entrySet() );
    result.addAll( local.entrySet() );
    return result;
  }

  /**
   * @see java.util.Map#keySet()
   */
  public Set keySet() {
    Set result = new HashSet();
    result.addAll( wrapped.keySet() );
    result.addAll( local.keySet() );
    return result;
  }

  /**
   * @see java.util.Map#values()
   */
  public Collection values() {
    Set result = new HashSet();
    result.addAll( wrapped.values() );
    result.addAll( local.values() );
    return result;
  }

  /**
   * @see java.util.Map#isEmpty()
   */
  public boolean isEmpty() {
    return local.isEmpty() && wrapped.isEmpty();
  }

  /**
   * @see java.util.Map#put(java.lang.Object, java.lang.Object)
   */
  public V put(K key, V value) {
    return local.put( key, value );
  }

  /**
   * @see java.util.Map#putAll(java.util.Map)
   */
  public void putAll(Map m) {
    local.putAll( m );
  }

  /**
   * @see java.util.Map#remove(java.lang.Object)
   */
  public V remove(Object key) {
    V result;

    if (  local.containsKey( key )) {
      result = local.remove( key );
    } else {
      result = wrapped.remove( key );
    }
    return result;
  }

  /**
   * @see java.util.Map#size()
   */
  public int size() {
    return local.size() + wrapped.size();
  }

  /**
   * @see java.lang.Object#toString()
   */
  public String toString() {
    Map local;

    local = new HashMap(this);
    return local.toString();
  }
}