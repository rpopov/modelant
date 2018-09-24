/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.api;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Any means to select or describe a collection of any objects (T), starting navigation
 * from a specific object (A).
 * @param <A> the type of the object to start the selection from
 * @param <T> the type of the objects to retrieve
 * @author Rusi Popov (popovr@mdatools.net)
 */
public interface Selector<A, T> extends Function<A, Collection<T>> {

  Selector EMPTY = new Selector() {
    public Collection execute(Object argument) throws RuntimeException, IllegalArgumentException {
      return Collections.emptyList();
    }
  };

  /**
   * Select a predefined constant set of elements
   * @author Rusi Popov (popovr@mdatools.net)
   */
  public class SelectFixedElements<A,T> implements Selector<A, T> {

    private final List<T> collection;

    /**
     * @param elements a non-null array of non-null elements
     */
    public SelectFixedElements(T... elements) {
      this.collection =  Collections.unmodifiableList(Arrays.asList( elements ));
    }

    public Collection<T> execute(A argument) throws RuntimeException, IllegalArgumentException {
      return collection;
    }
  }
}
