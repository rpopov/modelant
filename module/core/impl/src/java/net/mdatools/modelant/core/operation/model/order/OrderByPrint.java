/*
 * Copyright (c) i:FAO AG 2014. All Rights Reserved.
 *
 * This SOURCE CODE FILE, which has been provided by i:FAO AG as part
 * of a product of i:FAO AG for use ONLY by licensed users of the product,
 * includes CONFIDENTIAL and PROPRIETARY information.
 *
 * Created on 30.03.2014
 */
package net.mdatools.modelant.core.operation.model.order;

import java.util.IdentityHashMap;
import java.util.Map;

import javax.jmi.reflect.RefBaseObject;

import net.mdatools.modelant.core.api.Order;
import net.mdatools.modelant.core.operation.element.PrintModelElement;

/**
 * Compare model elements by their restricted print representations.
 *
 * Do not reuse the instances - this would cause memory leaks.
 *
 * The profiling identified this OrderBy clause to be the slowest one, therefore: <ul>
 * <li> introduced internal caching of the printouts
 * <li> introduced internal restriction of the elements to print
 * </ul>
 */
public class OrderByPrint implements Order {

  private static final PrintModelElement PRINT_MODEL_ELEMENT = new PrintModelElement();

  private final Map<RefBaseObject, String> elementToPrintOutMap = new IdentityHashMap<>();

  public int compare(RefBaseObject o1, RefBaseObject o2) {
    String rep1;
    String rep2;

    rep1 = getPrintOut( o1 );
    rep2 = getPrintOut( o2 );

    return rep1.compareTo( rep2 );
  }

  /**
   * The profiling identified this OrderBy clause to be the slowest one, therefore: <ul>
   * <li> introduced internal caching of the printouts
   * <li> introduced internal restriction of the elements to print
   * </ul>
   * @param key not null
   * @return string representation
   */
  private String getPrintOut(RefBaseObject key) {
    String result;

    result = elementToPrintOutMap.get( key );
    if ( result == null ) {
      result = PRINT_MODEL_ELEMENT.execute( key ).toString();
      elementToPrintOutMap.put( key, result );
    }
    return result;
  }
}