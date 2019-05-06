/*
 * Copyright (c) Rusi Popov, MDA Tools.net 2019
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Created on Apr 6, 2019
 */
package net.mdatools.modelant.mof14.maven.generator.select;

import java.util.Collection;

import net.mdatools.modelant.core.api.Filter;
import net.mdatools.modelant.core.api.Select;

/**
 * Compose a filter and selector
 * @author Rusi Popov
 */
public class FilteredSelector<A, T> implements Select<A, T> {

  private final Filter<T> filter;

  private final Select<A, T> selector;

  public FilteredSelector(Filter<T> filter, Select<A, T> selector) {
    assert filter != null : "Expected a non-null filter";
    assert selector != null : "Expected a non-null selector";

    this.filter = filter;
    this.selector = selector;
  }

  public Collection<T> execute(A argument) throws RuntimeException, IllegalArgumentException {
    return filter.execute( selector.execute( argument ) );
  }
}
