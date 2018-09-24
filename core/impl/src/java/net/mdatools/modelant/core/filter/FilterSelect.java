/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.filter;

import java.util.Collection;

import net.mdatools.modelant.core.api.Filter;
import net.mdatools.modelant.core.api.Selector;

/**
 * Compose a filter on a selector
 * @author Rusi Popov (popovr@mdatools.net)
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
