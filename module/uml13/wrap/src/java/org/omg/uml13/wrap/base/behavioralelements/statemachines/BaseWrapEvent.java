/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml13.wrap.base.behavioralelements.statemachines;

import org.omg.uml13.behavioralelements.statemachines.Event;
import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.core.wrap.Factories;

/**
 * This is a wrapper of org.omg.uml13.behavioralelements.statemachines.Event that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public abstract class BaseWrapEvent extends org.omg.uml13.wrap.foundation.core.WrapModelElement implements org.omg.uml13.behavioralelements.statemachines.Event {

  /**
   * Wraps an already existing object into a new wrapper instance
   */
  protected BaseWrapEvent(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }


  /**
   * Overrides the super implementation with the proper wrapped class
   * @see net.mdatools.modelant.core.wrap.Wrapper#getWrapped()
   */
  public Event getWrapped() {
    return (Event) super.getWrapped();
  }

  /**
   * @see net.mdatools.modelant.core.wrap.Wrapper#getDelegate()
   */
  protected Event getDelegate() {
    return (Event) super.getDelegate();
  }


  /**
   * @see org.omg.uml13.behavioralelements.statemachines.Event#getState()
   */
  public java.util.Collection getState() {
    return getDelegate().getState();
  }

  /**
   * @see org.omg.uml13.behavioralelements.statemachines.Event#getTransition()
   */
  public java.util.Collection getTransition() {
    return getDelegate().getTransition();
  }

  /**
   * @see org.omg.uml13.behavioralelements.statemachines.Event#getParameter()
   */
  public java.util.List getParameter() {
    return getDelegate().getParameter();
  }
}