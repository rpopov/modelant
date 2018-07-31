/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.operation.model.order;

import javax.jmi.reflect.RefBaseObject;
import javax.jmi.reflect.RefObject;

import net.mdatools.modelant.core.api.Order;

/**
 * This comparator should be used for ordering of model elements alphabetically, ignoring case,
 * according to the printed value of the specific named field.
 * If there is no such field (or association) in the model element of at least of one of the two compared 
 * model objects, then they are considered equal.
 */
public class OrderByField implements Order {
  /**
   * The field to sort the elements upon
   */
  private final String fieldName;

  /**
   * @param fieldName is the non-null name to sort elements upon
   */
  public OrderByField(String fieldName) {
    if ( fieldName == null ) {
      throw new IllegalArgumentException("Expected a non-null field name provided");
    }
    this.fieldName = fieldName;
  }

  /**
   * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
   */
  public int compare(RefBaseObject o1, RefBaseObject o2) {
    int result;
    String left;
    String right;

    if ( o1 instanceof RefObject
         && o2 instanceof RefObject ) {
      try {
        left = ""+((RefObject) o1).refGetValue( fieldName );
        right= ""+((RefObject) o2).refGetValue( fieldName );
  
        result = left.trim().compareTo( right.trim() );
        
      } catch (Exception ex) { // wrong name 
        // suppress the exception
        result=0;
      }
    } else {
      result = 0;
    }
    return result;
  }
}