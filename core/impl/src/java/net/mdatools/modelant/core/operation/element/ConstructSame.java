/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.operation.element;

import javax.jmi.reflect.RefObject;

import net.mdatools.modelant.core.api.Operation;

/**
 * Instantiate a the model element's class.
 * @author Rusi Popov (popovr@mdatools.net)
 */
public class ConstructSame implements Operation<RefObject> {

  /**
   * @param source Navigate from this element, down the path
   */
  public RefObject execute(RefObject source) throws IllegalArgumentException {
    RefObject result;

    // fond the source object
    if ( source == null ) {
      throw new IllegalArgumentException("Expected a non-null model element");
    }

    // find the target extent
    result = source.refClass().refCreateInstance( null );

    return result;
  }
}