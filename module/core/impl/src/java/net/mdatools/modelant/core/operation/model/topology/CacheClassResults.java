/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.operation.model.topology;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.jmi.reflect.RefBaseObject;
import javax.jmi.reflect.RefObject;

import net.mdatools.modelant.core.api.Function;

/**
 * Cache the results of the wrapped operation based on the reflective type of the argument
 */
public class CacheClassResults implements Function<RefObject, Collection<String>> {

  /**
   * wrapped operation to cache results of
   */
  private final Function<RefObject, Collection<String>> wrapped;

  /**
   * cache of results
   */
  private final Map<RefBaseObject, Collection<String>> cache = new HashMap<>();

  /**
   * @param wrapped not null operation to wrap and whose results to cache
   */
  public CacheClassResults(Function<RefObject, Collection<String>> wrapped) {
    this.wrapped = wrapped;
  }

  public Collection<String> execute(RefObject arg0) throws RuntimeException, IllegalArgumentException {
    Collection<String> result;

    result = cache.get( arg0.refClass() );
    if ( result == null  ) {
      result = wrapped.execute( arg0 );

      cache.put( arg0.refClass(), result );
    }
    return result;
  }
}