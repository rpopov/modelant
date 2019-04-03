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

import net.mdatools.modelant.core.api.Condition;
import net.mdatools.modelant.core.api.Select;

/**
 * Filter the collection based on the contents of "name" field, if any
 * @author Rusi Popov (popovr@mdatools.net)
 */
public class Filter<T extends RefObject> implements Select<Collection<T>, T> {

  private final Condition<T> condition;

  /**
   * @param fieldName non-null field name to evaluate value if it has the value provided
   * @param target not null name value to search for
   */
  public Filter(Condition<T> condition) {
    this.condition = condition;
  }

  public final Collection<T> execute(Collection<T> collection) throws RuntimeException, IllegalArgumentException {
    Collection<T> result;

    result = new ArrayList<>();
    for (T element:collection) {
      if ( condition.eval( element )) {
        result.add(element);
      }
    }
    return result;
  }
}
