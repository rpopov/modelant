/*
 * Copyright (c) i:FAO AG 2018. All Rights Reserved.
 *
 * This SOURCE CODE FILE, which has been provided by i:FAO AG as part
 * of a product of i:FAO AG for use ONLY by licensed users of the product,
 * includes CONFIDENTIAL and PROPRIETARY information.
 *
 * Created on 23.04.2018 г.
 */
package net.mdatools.modelant.core.filter;

import java.util.Collection;

import net.mdatools.modelant.core.api.Filter;
import net.mdatools.modelant.core.api.Selector;

/**
 * Compose a filter on a selector
 * @author Rusi Popov
 */
public final class FilterSelect<A,T> implements Selector<A,T> {

  private final Filter<T> f;
  private final Selector<A, T> s;

  public FilterSelect(Filter<T> f, Selector<A, T> s) {
    this.f = f;
    this.s = s;
  }

  /**
   * @see net.mdatools.modelant.core.api.Function#execute(java.lang.Object)
   */
  public Collection<T> execute(A argument) throws RuntimeException, IllegalArgumentException {
    return f.execute(s.execute(argument));
  }
}
