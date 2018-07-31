/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.operation.model.order;

import java.util.ArrayList;
import java.util.List;

import javax.jmi.reflect.RefBaseObject;

import net.mdatools.modelant.core.api.Order;

/**
 * A composite comparator to order the model elements defined by the order of the nested comparators.
 * This way any objects that are equal according to comparator i will be additionally compared with comparator i+1
 * <pre>
 *   Example:
 *     &lt;oderBy&gt;
 *       &lt;orderByClass&gt;...&lt;/orderByClass&gt;
 *       &lt;orderByQualifedName&gt;...&lt;/orderByQualifedName&gt;
 *     &lt;/orderBy&gt;
 * </pre>
 * @author Rusi Popov
 */
public class CompositeOrder implements Order {

  private final List<Order> nested = new ArrayList<Order>();

  /**
   * List the nested comparators. Their order is significant - the earlier listed comparators
   * will be applied first.
   * @param next
   */
  public void add(Order next) {
    nested.add( next );
  }

  public int compare(RefBaseObject o1, RefBaseObject o2) {
    int result;

    result = 0;
    for (int i =0; i < nested.size() && result == 0; i++) {
      result = nested.get( i ).compare( o1, o2 );
    }
    return result;
  }
}