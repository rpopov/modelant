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

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * A mechanism to map sets <b> 1:1 </b> to other sets.
 * <b>All sets do not intersect.</b>
 * The mapping is symmetric, allowing <pre>
 *   map(getRepresentative(a))=getRepresentative(b)
 *   map(getRepresentative(b))=getRepresentative(a)
 * </pre>
 * @param <V> the class of the elements in the mapped sets.
 * @author Rusi Popov
 */
public interface EquivalenceClassesMap<V> {

  /**
   * Register the X equivalence class mapped  to Y equivalence class,
   * Any existing mappings of the equivalence classes' elements are overridden.
   * @param xClass not null, not empty equivalence class at x side
   * @param yClass not null, not empty equivalence class at y side
   */
  void add(Collection<V> xClass, Collection<V> yClass);

  /**
   * This method guarantees that each x or y objects pertain to no more than a single set and there is
   * one-to-one mapping between these sets
   * If there is a set containing y and there is no set containing x, then {x} is put as the set that is mapped to y;
   * If there is a set containing x and there is no set containing y, then {y} is put as the set that is mapped to x;
   * If there is no set containing x and there is no set containing y, then a new set {x} is created and mapped to the new set {y}
   * If there is a set containing x A.K.A {x} and there is a set containing y A.K.A {y}, then if {x} not mapped to {y}, this method fails.
   * @param x not null, conditionally named key
   * @param y not null, conditionally named value
   * @throws IllegalArgumentException when mapping x,y that have already mapped representatives that do not match
   */
  void add(V x, V y) throws IllegalArgumentException;


  /**
   * @return a non-null collection the representatives for elements X add(X,y) was called with.
   */
  Collection<V> getXKeys();

  /**
   * @param element is non-null
   * @return a non-null collection of objects of the same equivalence class the element pertains to
   */
  Collection<V> getEquivalents(V element);

  /**
   * @param element
   * @return the element that represents the equivalence class this element pertains to
   */
  V getRepresentative(V element);

  /**
   * @param element
   * @return the element this element (any element of its equivalence class) is mapped to
   */
  V map(V element);


  /**
   * @param elements non null collection
   * @return a collection of their representatives or the objects themselves, if they have not been matched in
   *         the target model and therefore have no representatives
   */
  default Set<V> getRepresentativesOrSelf(Collection<V> elements) {
    Set<V> result;
    V mapped;

    result = new HashSet<>(elements.size()<<2);

    for(V element: elements) {
      mapped = getRepresentative( element );

      if ( mapped == null ) { // no representative - use the element itself
        result.add( element );
      } else {
        result.add( mapped );
      }
    }
    return result;
  }

  /**
   * @param elements not null
   * @return a non-null set of the elements are mapped on. Mapped nulls are skipped.
   */
  default Set<V> getMappedRepresentatives(Collection<V> elements) {
    Set<V> result;
    V mapped;

    result = new HashSet<V>(elements.size()<<1);

    // collect the matched model elements for these nodes
    for(V element :  elements ) {
      mapped = map( element );

      if ( mapped != null ) {
        result.add( mapped );
      }
    }
    return result;
  }

  /**
   * @param elements non null collection
   * @return a collection of their representatives. null representatives are skipped
   */
  default Set<V> getRepresentatives(Collection<V> elements) {
    Set<V> result;
    V mapped;

    result = new HashSet<>(elements.size()<<2);

    for(V element: elements) {
      mapped = getRepresentative( element );

      if ( mapped != null ) { // no representative - use the element itself
        result.add( mapped );
      }
    }
    return result;
  }
}