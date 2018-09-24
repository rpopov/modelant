/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.operation.element;

import javax.jmi.reflect.RefClass;
import javax.jmi.reflect.RefObject;
import javax.jmi.reflect.RefPackage;

import net.mdatools.modelant.core.api.Operation;
import net.mdatools.modelant.core.util.Navigator;

/**
 * Instantiate a the model class, which is the class of a specific model element,
 * in the the wrapped extent.
 * @author Rusi Popov (popovr@mdatools.net)
 */
public class ConstructSameInExtent implements Operation<RefObject> {

  /**
   * Extent where the model is loaded
   */
  private final RefPackage extent;

  /**
   * @param extent not null extent where to construct in
   */
  public ConstructSameInExtent(RefPackage extent) {
    if (extent == null) {
      throw new IllegalArgumentException("Expected a non-null model extent");
    }
    this.extent = extent;
  }

  /**
   * @param source Navigate from this element, down the path
   */
  public RefObject execute(RefObject source) throws IllegalArgumentException {
    RefObject result;
    RefClass refClass;
    String metaclass;

    // fond the source object
    if ( source == null ) {
      throw new IllegalArgumentException("Expected a non-null model element");
    }

    metaclass = Navigator.getMetaClassName( source );
    refClass = Navigator.getMetaClass( extent, metaclass ); // non-null

    result = refClass.refCreateInstance( null );
    return result;
  }
}