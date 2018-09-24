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

import net.mdatools.modelant.core.api.Function;
import net.mdatools.modelant.core.util.Navigator;

/**
 * Instantiate a model element of a metamodel.
 * @author Rusi Popov (popovr@mdatools.net)
 */
public class Create implements Function<String, RefObject> {

  /**
   * Extent where the model is loaded
   */
  private final RefPackage extent;

  public Create(RefPackage extent) {
    if ( extent == null ) { // no extent provided
      throw new IllegalArgumentException( "Expected a non-null extent provided to process");
    }
    this.extent = extent;
  }


  /**
   * The task's execution method. This method filters the model classes associated with metaclass and
   * executes the nested &lt;template&gt; tasks for the filtered classes collection. This
   * methods invokes the internal formatting tasks with the metaclass itself.
   * @param metaclass Path in the metamodel to the metaclass whose instances (in the model/extent) are to be
   * processed. Format of metaclass attribute: {&lt;package&gt;::} &lt;meta class&gt;
   */
  public RefObject execute(String metaclass) throws IllegalArgumentException {
    RefClass refClass;

    RefObject result;

    if ( metaclass == null ) {
      throw new IllegalArgumentException( "Expected a non-null metaclass provided");
    }

    refClass = Navigator.getMetaClass( extent, metaclass ); // non-null
    result = refClass.refCreateInstance( null );

    return result;
  }
}