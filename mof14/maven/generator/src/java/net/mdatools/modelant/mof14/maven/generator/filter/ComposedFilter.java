/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.mof14.maven.generator.filter;

import java.util.ArrayList;
import java.util.Collection;

import javax.jmi.reflect.RefObject;

import net.mdatools.modelant.core.api.Condition;
import net.mdatools.modelant.core.api.Filter;

/**
 * Filter the collection based on the contents of "name" field, if any
 * @author Rusi Popov (popovr@mdatools.net)
 * @param <T> the type of the
 */
public final class ComposedFilter<T extends RefObject> implements Filter<T> {

  /**
   * not null condition to choose result elements
   */
  private final Condition<T> condition;

  /**
   * not null composed filter to provide the initial selection
   */
  private final Filter<T> nestedFilter;

  /**
   * @param condition not null condition
   */
  public ComposedFilter(Condition<T> condition) {
    this(condition, Filter.identity());
  }

  /**
   * @param condition not null to select result elements
   * @param filter not null to provide initial selection
   */
  public ComposedFilter(Condition<T> condition, Filter<T> filter) {
    assert condition != null : "Expected a non-null condition";
    assert filter != null : "Expected a non-null filter";

    this.condition = condition;
    this.nestedFilter = filter;
  }

  public final Collection<T> execute(Collection<T> collection) throws RuntimeException, IllegalArgumentException {
    Collection<T> result;

    result = new ArrayList<>();
    for (T element: nestedFilter.execute( collection )) {
      if ( condition.eval( element )) {
        result.add(element);
      }
    }
    return result;
  }

  /**
   * A fluent API method to conjuct conditions in filters
   * @param condition
   * @return a composed filter applying this condition and the provided one
   */
  public final ComposedFilter<T> and(Condition<T> condition) {
    return new ComposedFilter<>(condition, this);
  }
}
