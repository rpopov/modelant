/*
 * Copyright (c) i:FAO AG 2018. All Rights Reserved.
 *
 * This SOURCE CODE FILE, which has been provided by i:FAO AG as part
 * of a product of i:FAO AG for use ONLY by licensed users of the product,
 * includes CONFIDENTIAL and PROPRIETARY information.
 *
 * Created on 22.04.2018 г.
 */
package net.mdatools.modelant.core.filter;

import java.util.ArrayList;
import java.util.Collection;

import javax.jmi.reflect.RefObject;

import net.mdatools.modelant.core.api.Filter;

/**
 * Filter the collection based on the contents of "name" field, if any
 * @author Rusi Popov
 */
public class FilterByName<T extends RefObject> implements Filter<T> {

  private final String target;

  /**
   * @param target not null name value to search for
   */
  public FilterByName(String target) {
    this.target = target;
  }

  public Collection<T> execute(Collection<T> collection) throws RuntimeException, IllegalArgumentException {
    Collection<T> result;
    Object value;

    result = new ArrayList<>();
    for (T element:collection) {
      value = element.refGetValue( "name" );

      if ( target.equals(value) ) {
        result.add(element);
      }
    }
    return result;
  }

}
