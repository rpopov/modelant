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

import javax.jmi.reflect.RefClass;
import javax.jmi.reflect.RefPackage;
import org.omg.uml13.behavioralelements.statemachines.Transition;
import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.core.wrap.Factories;

/**
 * This is a wrapper of org.omg.uml13.behavioralelements.statemachines.Transition that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public abstract class BaseWrapTransition extends org.omg.uml13.wrap.foundation.core.WrapModelElement implements org.omg.uml13.behavioralelements.statemachines.Transition {

  /**
   * Wraps an already existing object into a new wrapper instance
   */
  protected BaseWrapTransition(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  /**
   * Constructs a new wrapped object
   */
  public BaseWrapTransition(RefPackage extent) {
    this( getClassProxy( extent ).refCreateInstance( null ),
          Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ));
    Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ).bind( this );
  }

  /**
   * This method retrieves the factory for org.omg.uml13.behavioralelements.statemachines.Transition (also known as "class proxy")
   * model class.
   */
  public static RefClass getClassProxy(RefPackage extent) {
    return ((org.omg.uml13.Uml13Package) extent).getBehavioralElements().getStateMachines().getTransition();
  }


  /**
   * Overrides the super implementation with the proper wrapped class
   * @see net.mdatools.modelant.core.wrap.Wrapper#getWrapped()
   */
  public Transition getWrapped() {
    return (Transition) super.getWrapped();
  }

  /**
   * @see net.mdatools.modelant.core.wrap.Wrapper#getDelegate()
   */
  protected Transition getDelegate() {
    return (Transition) super.getDelegate();
  }


  /**
   * @see org.omg.uml13.behavioralelements.statemachines.Transition#getEffect()
   */
  public org.omg.uml13.behavioralelements.commonbehavior.Action getEffect() {
    return getDelegate().getEffect();
  }

  /**
   * @see org.omg.uml13.behavioralelements.statemachines.Transition#getTrigger()
   */
  public org.omg.uml13.behavioralelements.statemachines.Event getTrigger() {
    return getDelegate().getTrigger();
  }

  /**
   * @see org.omg.uml13.behavioralelements.statemachines.Transition#getGuard()
   */
  public org.omg.uml13.behavioralelements.statemachines.Guard getGuard() {
    return getDelegate().getGuard();
  }

  /**
   * @see org.omg.uml13.behavioralelements.statemachines.Transition#getState()
   */
  public org.omg.uml13.behavioralelements.statemachines.State getState() {
    return getDelegate().getState();
  }

  /**
   * @see org.omg.uml13.behavioralelements.statemachines.Transition#getStateMachine()
   */
  public org.omg.uml13.behavioralelements.statemachines.StateMachine getStateMachine() {
    return getDelegate().getStateMachine();
  }

  /**
   * @see org.omg.uml13.behavioralelements.statemachines.Transition#getSource()
   */
  public org.omg.uml13.behavioralelements.statemachines.StateVertex getSource() {
    return getDelegate().getSource();
  }

  /**
   * @see org.omg.uml13.behavioralelements.statemachines.Transition#getTarget()
   */
  public org.omg.uml13.behavioralelements.statemachines.StateVertex getTarget() {
    return getDelegate().getTarget();
  }

  /**
   * @see org.omg.uml13.behavioralelements.statemachines.Transition#setEffect(org.omg.uml13.behavioralelements.commonbehavior.Action)
   */
  public void setEffect(org.omg.uml13.behavioralelements.commonbehavior.Action arg0) {
    getDelegate().setEffect(arg0);
  }

  /**
   * @see org.omg.uml13.behavioralelements.statemachines.Transition#setGuard(org.omg.uml13.behavioralelements.statemachines.Guard)
   */
  public void setGuard(org.omg.uml13.behavioralelements.statemachines.Guard arg0) {
    getDelegate().setGuard(arg0);
  }

  /**
   * @see org.omg.uml13.behavioralelements.statemachines.Transition#setSource(org.omg.uml13.behavioralelements.statemachines.StateVertex)
   */
  public void setSource(org.omg.uml13.behavioralelements.statemachines.StateVertex arg0) {
    getDelegate().setSource(arg0);
  }

  /**
   * @see org.omg.uml13.behavioralelements.statemachines.Transition#setState(org.omg.uml13.behavioralelements.statemachines.State)
   */
  public void setState(org.omg.uml13.behavioralelements.statemachines.State arg0) {
    getDelegate().setState(arg0);
  }

  /**
   * @see org.omg.uml13.behavioralelements.statemachines.Transition#setStateMachine(org.omg.uml13.behavioralelements.statemachines.StateMachine)
   */
  public void setStateMachine(org.omg.uml13.behavioralelements.statemachines.StateMachine arg0) {
    getDelegate().setStateMachine(arg0);
  }

  /**
   * @see org.omg.uml13.behavioralelements.statemachines.Transition#setTarget(org.omg.uml13.behavioralelements.statemachines.StateVertex)
   */
  public void setTarget(org.omg.uml13.behavioralelements.statemachines.StateVertex arg0) {
    getDelegate().setTarget(arg0);
  }

  /**
   * @see org.omg.uml13.behavioralelements.statemachines.Transition#setTrigger(org.omg.uml13.behavioralelements.statemachines.Event)
   */
  public void setTrigger(org.omg.uml13.behavioralelements.statemachines.Event arg0) {
    getDelegate().setTrigger(arg0);
  }
}