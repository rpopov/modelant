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
