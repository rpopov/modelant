/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml13.wrap.base.behavioralelements.statemachines;

import org.omg.uml13.behavioralelements.statemachines.StateVertex;
import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.core.wrap.Factories;

/**
 * This is a wrapper of org.omg.uml13.behavioralelements.statemachines.StateVertex that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public abstract class BaseWrapStateVertex extends org.omg.uml13.wrap.foundation.core.WrapModelElement implements org.omg.uml13.behavioralelements.statemachines.StateVertex {

  /**
   * Wraps an already existing object into a new wrapper instance
   */
  protected BaseWrapStateVertex(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }


  /**
   * Overrides the super implementation with the proper wrapped class
   * @see net.mdatools.modelant.core.wrap.Wrapper#getWrapped()
   */
  public StateVertex getWrapped() {
    return (StateVertex) super.getWrapped();
  }

  /**
   * @see net.mdatools.modelant.core.wrap.Wrapper#getDelegate()
   */
  protected StateVertex getDelegate() {
    return (StateVertex) super.getDelegate();
  }


  /**
   * @see org.omg.uml13.behavioralelements.statemachines.StateVertex#getIncoming()
   */
  public java.util.Collection getIncoming() {
    return getDelegate().getIncoming();
  }

  /**
   * @see org.omg.uml13.behavioralelements.statemachines.StateVertex#getOutgoing()
   */
  public java.util.Collection getOutgoing() {
    return getDelegate().getOutgoing();
  }

  /**
   * @see org.omg.uml13.behavioralelements.statemachines.StateVertex#getContainer()
   */
  public org.omg.uml13.behavioralelements.statemachines.CompositeState getContainer() {
    return getDelegate().getContainer();
  }

  /**
   * @see org.omg.uml13.behavioralelements.statemachines.StateVertex#setContainer(org.omg.uml13.behavioralelements.statemachines.CompositeState)
   */
  public void setContainer(org.omg.uml13.behavioralelements.statemachines.CompositeState arg0) {
    getDelegate().setContainer(arg0);
  }
}