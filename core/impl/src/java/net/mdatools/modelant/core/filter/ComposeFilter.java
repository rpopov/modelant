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
import net.mdatools.modelant.core.api.Function;

/**
 * Compose 2 filters
 * @author Rusi Popov
 */
public final class ComposeFilter<T> implements Filter<T> {

  private final Function<Collection<T>, Collection<T>> f;
  private final Function<Collection<T>, Collection<T>> s;

  public ComposeFilter(Function<Collection<T>, Collection<T>> f, Function<Collection<T>, Collection<T>> s) {
    this.f = f;
    this.s = s;
  }

  /**
   * @see net.mdatools.modelant.core.api.Function#execute(java.lang.Object)
   */
  public Collection<T> execute(Collection<T> argument) throws RuntimeException, IllegalArgumentException {
    return s.execute(f.execute(argument));
  }
}
