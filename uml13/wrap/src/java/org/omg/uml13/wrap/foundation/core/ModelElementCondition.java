/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml13.wrap.foundation.core;

import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.core.wrap.Wrapper;

import org.omg.uml13.foundation.core.ModelElement;

/**
 * This interface allows providing additional condition upon the Model Elements
 * to select. Note that it is always applied on 'thisEnd' association ends, bound to
 * the wrapped class.
 * @author rpopov
 * @param <T> is an UML 1.3 model interface 
 */
public abstract class ModelElementCondition<T extends ModelElement> {
  
  private final Factory factory;
  
  /**
   * @param factory the actual non-null factory to apply mapping T to W
   */
  protected ModelElementCondition(Factory factory) {
    assert factory != null : "Expected a non-null factory";
    this.factory = factory;
  }
  
  /**
   * Evaluates this condition for the model element provided
   * @param modelElement not null 
   * @return the value of this condition for the model element  
   */
  public abstract boolean eval(T modelElement);
  
  /**
   * Wraps any model element using the factory
   * @param modelElement
   * @return the corresponding wrapper or null when null provided.
   * NOTE: It is not possible to identify a single wrapper type to use it as a more concrete return type
   */
  protected final Wrapper wrap(Object modelElement) {
    return factory.wrap(modelElement);
  }
}