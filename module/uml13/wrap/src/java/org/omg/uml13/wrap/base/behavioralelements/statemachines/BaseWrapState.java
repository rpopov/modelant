/*
 * Copyright (c) 2001,2012 Rusi Popov, MDA Tools.net
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Rusi Popov (popovr@mdatools.net) - initial implementation
 */
package org.omg.uml13.wrap.base.behavioralelements.statemachines;

import org.omg.uml13.behavioralelements.statemachines.State;
import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.core.wrap.Factories;

/**
 * This is a wrapper of org.omg.uml13.behavioralelements.statemachines.State that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public abstract class BaseWrapState extends org.omg.uml13.wrap.behavioralelements.statemachines.WrapStateVertex implements org.omg.uml13.behavioralelements.statemachines.State {

  /**
   * Wraps an already existing object into a new wrapper instance
   */
  protected BaseWrapState(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }


  /**
   * Overrides the super implementation with the proper wrapped class
   * @see net.mdatools.modelant.core.wrap.Wrapper#getWrapped()
   */
  public State getWrapped() {
    return (State) super.getWrapped();
  }

  /**
   * @see net.mdatools.modelant.core.wrap.Wrapper#getDelegate()
   */
  protected State getDelegate() {
    return (State) super.getDelegate();
  }


  /**
   * @see org.omg.uml13.behavioralelements.statemachines.State#getDeferrableEvent()
   */
  public java.util.Collection getDeferrableEvent() {
    return getDelegate().getDeferrableEvent();
  }

  /**
   * @see org.omg.uml13.behavioralelements.statemachines.State#getInternalTransition()
   */
  public java.util.Collection getInternalTransition() {
    return getDelegate().getInternalTransition();
  }

  /**
   * @see org.omg.uml13.behavioralelements.statemachines.State#getDoActivity()
   */
  public org.omg.uml13.behavioralelements.commonbehavior.Action getDoActivity() {
    return getDelegate().getDoActivity();
  }

  /**
   * @see org.omg.uml13.behavioralelements.statemachines.State#getEntry()
   */
  public org.omg.uml13.behavioralelements.commonbehavior.Action getEntry() {
    return getDelegate().getEntry();
  }

  /**
   * @see org.omg.uml13.behavioralelements.statemachines.State#getExit()
   */
  public org.omg.uml13.behavioralelements.commonbehavior.Action getExit() {
    return getDelegate().getExit();
  }

  /**
   * @see org.omg.uml13.behavioralelements.statemachines.State#getStateMachine()
   */
  public org.omg.uml13.behavioralelements.statemachines.StateMachine getStateMachine() {
    return getDelegate().getStateMachine();
  }

  /**
   * @see org.omg.uml13.behavioralelements.statemachines.State#setDoActivity(org.omg.uml13.behavioralelements.commonbehavior.Action)
   */
  public void setDoActivity(org.omg.uml13.behavioralelements.commonbehavior.Action arg0) {
    getDelegate().setDoActivity(arg0);
  }

  /**
   * @see org.omg.uml13.behavioralelements.statemachines.State#setEntry(org.omg.uml13.behavioralelements.commonbehavior.Action)
   */
  public void setEntry(org.omg.uml13.behavioralelements.commonbehavior.Action arg0) {
    getDelegate().setEntry(arg0);
  }

  /**
   * @see org.omg.uml13.behavioralelements.statemachines.State#setExit(org.omg.uml13.behavioralelements.commonbehavior.Action)
   */
  public void setExit(org.omg.uml13.behavioralelements.commonbehavior.Action arg0) {
    getDelegate().setExit(arg0);
  }

  /**
   * @see org.omg.uml13.behavioralelements.statemachines.State#setStateMachine(org.omg.uml13.behavioralelements.statemachines.StateMachine)
   */
  public void setStateMachine(org.omg.uml13.behavioralelements.statemachines.StateMachine arg0) {
    getDelegate().setStateMachine(arg0);
  }
}