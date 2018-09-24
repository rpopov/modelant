/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.filter;

import java.util.ArrayList;
import java.util.Collection;

import javax.jmi.reflect.RefObject;

import net.mdatools.modelant.core.api.Filter;

/**
 * Filter the collection based on the contents of "name" field, if any
 * @author Rusi Popov (popovr@mdatools.net)
 */
public class FilterByField<T extends RefObject> implements Filter<T> {

  private final String fieldName;
  private final String target;

  /**
   * @param fieldName non-null field name to evaluate value if it has the value provided
   * @param target not null name value to search for
   */
  public FilterByField(String fieldName, String target) {
    this.fieldName = fieldName;
    this.target = target;
  }

  public Collection<T> execute(Collection<T> collection) throws RuntimeException, IllegalArgumentException {
    Collection<T> result;
    Object value;

    result = new ArrayList<>();
    for (T element:collection) {
      value = element.refGetValue( fieldName );

      if ( target.equals(value) ) {
        result.add(element);
      }
    }
    return result;
  }

}
