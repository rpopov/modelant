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
package net.mdatools.modelant.core.operation.model.topology;

import java.util.ArrayList;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @param <V> is the class of the mapped elements
 * @author Rusi Popov
 */
public class EquivalenceClassesMapImpl<V> implements EquivalenceClassesMap<V> {

  /**
   * Maps each element to the representative of the set it pertains to
   */
  private final Map<V,V> elementToKey = new IdentityHashMap<V,V>();

  /**
   * Maps the key (set representative) to the representative of the other set
   */
  private final Map<V,V> keyToKey = new IdentityHashMap<V,V>();

  /**
   * The unique keys of X elements added here
   */
  private final List<V> xKeys = new ArrayList<>();

  /**
   * @see net.mdatools.modelant.core.operation.model.topology.EquivalenceClassesMap#add(java.util.Collection, java.util.Collection)
   */
  public void add(Collection<V> xClass, Collection<V> yClass) {
    Iterator<V> xIterator;
    Iterator<V> yIterator;
    V x;
    V y;

    assert xClass != null : "Expected not null xClass";
    assert !xClass.isEmpty() : "Expected not empty xClass";

    assert yClass != null : "Expected not null yClass";
    assert !yClass.isEmpty() : "Expected not empty yClass";

    xIterator = xClass.iterator();
    yIterator = yClass.iterator();

    x = xIterator.next();
    y = yIterator.next();

    add(x,y);
    while ( xIterator.hasNext() ) {
      add( xIterator.next(), y );
    }
    while ( yIterator.hasNext() ) {
      add( x, yIterator.next() );
    }
  }

  /**
   * @see net.mdatools.modelant.core.operation.model.topology.EquivalenceClassesMap#add(java.lang.Object, java.lang.Object)
   */
  public final void add(V x, V y) throws IllegalArgumentException {
    V xRep;
    V yRep;

    xRep = getRepresentative( x );
    yRep = getRepresentative( y );
    if ( xRep == null ) {
      if ( yRep == null ) { // x, y still not mapped
        // bind x,y as representatives == create a new equivalence set
        elementToKey.put( x, x );
        elementToKey.put( y, y );

        // make the mapping symmetric
        keyToKey.put(x, y);
        keyToKey.put(y, x);

        xKeys.add( x );
      } else { // y mapped to yRep, x not mapped
        elementToKey.put( x, keyToKey.get( yRep ));
      }
    } else { // x mapped to xRep
      if ( yRep == null ) { // y still not mapped
        elementToKey.put( y, keyToKey.get( xRep ));

      } else { // y mapped to yRep & x mapped
        if ( yRep != keyToKey.get( xRep ) ) { // xRep != keyToKey.get( yRep )
          throw new IllegalArgumentException("Expected "+x+ " with its representative "+xRep
                                             +" is mapped to be mapped to the representative of "+yRep+" of "+y);
        }
      }
    }
  }

  /**
   * @see net.mdatools.modelant.core.operation.model.topology.EquivalenceClassesMap#getXKeys()
   */
  public final Collection<V> getXKeys() {
    return xKeys;
  }

  /**
   * @see net.mdatools.modelant.core.operation.model.topology.EquivalenceClassesMap#getEquivalents(java.lang.Object)
   */
  public final Collection<V> getEquivalents(V element) {
    Collection<V> result;
    V representative;

    result = new ArrayList<V>();
    representative = elementToKey.get( element );
    for (Map.Entry<V, V> entry: elementToKey.entrySet()) {
      if ( entry.getValue() == representative ) {
        result.add( entry.getKey() );
      }
    }
    return result;
  }

  /**
   * @see net.mdatools.modelant.core.operation.model.topology.EquivalenceClassesMap#getRepresentative(java.lang.Object)
   */
  public final V getRepresentative(V element) {
    return elementToKey.get( element );
  }

  /**
   * @see net.mdatools.modelant.core.operation.model.topology.EquivalenceClassesMap#map(java.lang.Object)
   */
  public final V map(V element) {
    V result;

    result = keyToKey.get( getRepresentative( element ) );

    return result;
  }
}