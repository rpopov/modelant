/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.operation.element;

import java.util.Collection;

import javax.jmi.reflect.RefFeatured;

/**
 * Associate or change the attribute value of a model element and return that updated model element.
 * @author Rusi Popov (popovr@mdatools.net)
 */
public class Add extends NavigateObjectPath<RefFeatured> {

  private final Object value;


  /**
   * @see NavigateObjectPath
   */
  public Add(String path, Object value) {
    super( path );
    this.value = value;
  }


  /**
   * @see NavigateObjectPath
   */
  public Add(String[] path, Object value) {
    super( path );
    this.value = value;
  }

  /**
   * Nothing to do
   * @see net.mdatools.modelant.core.operation.element.NavigateObjectPath#processEmptyPath(javax.jmi.reflect.RefFeatured)
   */
  protected RefFeatured processEmptyPath(RefFeatured start) {
    return start;
  }


  /**
   * Clear an association *-to-one
   * @see net.mdatools.modelant.core.operation.element.NavigateObjectPath#processLast(javax.jmi.reflect.RefFeatured, javax.jmi.reflect.RefFeatured, java.lang.String, javax.jmi.reflect.RefFeatured)
   */
  protected RefFeatured processLast(RefFeatured start,
                                    RefFeatured current,
                                    String itemName,
                                    RefFeatured associated) {
    current.refSetValue( itemName, value );

    return current;
  }


  /**
   * @see net.mdatools.modelant.core.operation.element.NavigateObjectPath#processLast(javax.jmi.reflect.RefFeatured, javax.jmi.reflect.RefFeatured, java.lang.String, java.lang.Object)
   */
  protected RefFeatured processLast(RefFeatured start, RefFeatured current, String itemName, Object actualValue) {
    if ( actualValue instanceof Collection ) { // association to many
      if ( value instanceof Collection) { // associate to many
        ((Collection) actualValue).addAll( (Collection) value );

      } else { // associate to one
        ((Collection) actualValue).add( value );
      }
    } else { // set an attribute
      throw new IllegalArgumentException("Expected "+actualValue+" is a collection attribute value to add "+value+" to");
    }
    return current;
  }
}