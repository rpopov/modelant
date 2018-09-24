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

import net.mdatools.modelant.core.util.Navigator;

/**
 * Clear the association or attribute value of a model element and return that updated model element.
 * @author Rusi Popov (popovr@mdatools.net)
 */
public class Clear extends NavigateObjectPath<RefFeatured> {

  /**
   * @see NavigateObjectPath
   */
  public Clear(String path) {
    super( path );
  }


  /**
   * @see NavigateObjectPath
   */
  public Clear(String[] path) {
    super( path );
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
    current.refSetValue( itemName, null );

    return current;
  }


  /**
   * @see net.mdatools.modelant.core.operation.element.NavigateObjectPath#processLast(javax.jmi.reflect.RefFeatured, javax.jmi.reflect.RefFeatured, java.lang.String, java.lang.Object)
   */
  protected RefFeatured processLast(RefFeatured start, RefFeatured current, String itemName, Object value) {
    if ( value instanceof Collection ) { // clear association to many
      ((Collection) value).clear();

    } else { // clearing an attribute
      current.refSetValue( itemName, null );
    }
    return current;
  }
}