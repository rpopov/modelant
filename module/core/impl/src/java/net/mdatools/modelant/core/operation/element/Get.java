/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.operation.element;

import javax.jmi.reflect.RefFeatured;

/**
 * Navigate down the path and retrieve the referred attribute/association's value
 * @author Rusi Popov
 */
public class Get extends NavigateObjectPath<Object> {

  public Get(String path) {
    super( path );
  }

  public Get(String[] path) {
    super( path );
  }


  /**
   * @return start as there is no navigation path from it
   * @see net.mdatools.modelant.core.operation.element.NavigateObjectPath#processEmptyPath(javax.jmi.reflect.RefFeatured)
   */
  protected Object processEmptyPath(RefFeatured start) {
    return start;
  }

  /**
   * @return associated
   * @see net.mdatools.modelant.core.operation.element.NavigateObjectPath#processLast(javax.jmi.reflect.RefFeatured, javax.jmi.reflect.RefFeatured, java.lang.String, javax.jmi.reflect.RefFeatured)
   */
  protected Object processLast(RefFeatured start, RefFeatured current, String itemName, RefFeatured associated) {
    return associated;
  }

  /**
   * @return value
   * @see net.mdatools.modelant.core.operation.element.NavigateObjectPath#processLast(javax.jmi.reflect.RefFeatured, javax.jmi.reflect.RefFeatured, java.lang.String, java.lang.Object)
   */
  protected Object processLast(RefFeatured start, RefFeatured current, String itemName, Object value) {
    return value;
  }
}
