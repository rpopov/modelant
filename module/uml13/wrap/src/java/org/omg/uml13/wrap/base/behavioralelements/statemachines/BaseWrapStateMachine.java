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
import org.omg.uml13.behavioralelements.statemachines.StateMachine;
import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.core.wrap.Factories;

/**
 * This is a wrapper of org.omg.uml13.behavioralelements.statemachines.StateMachine that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public abstract class BaseWrapStateMachine extends org.omg.uml13.wrap.foundation.core.WrapModelElement implements org.omg.uml13.behavioralelements.statemachines.StateMachine {

  /**
   * Wraps an already existing object into a new wrapper instance
   */
  protected BaseWrapStateMachine(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  /**
   * Constructs a new wrapped object
   */
  public BaseWrapStateMachine(RefPackage extent) {
    this( getClassProxy( extent ).refCreateInstance( null ),
          Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ));
    Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ).bind( this );
  }

  /**
   * This method retrieves the factory for org.omg.uml13.behavioralelements.statemachines.StateMachine (also known as "class proxy")
   * model class.
   */
  public static RefClass getClassProxy(RefPackage extent) {
    return ((org.omg.uml13.Uml13Package) extent).getBehavioralElements().getStateMachines().getStateMachine();
  }


  /**
   * Overrides the super implementation with the proper wrapped class
   * @see net.mdatools.modelant.core.wrap.Wrapper#getWrapped()
   */
  public StateMachine getWrapped() {
    return (StateMachine) super.getWrapped();
  }

  /**
   * @see net.mdatools.modelant.core.wrap.Wrapper#getDelegate()
   */
  protected StateMachine getDelegate() {
    return (StateMachine) super.getDelegate();
  }


  /**
   * @see org.omg.uml13.behavioralelements.statemachines.StateMachine#getSubMachineState()
   */
  public java.util.Collection getSubMachineState() {
    return getDelegate().getSubMachineState();
  }

  /**
   * @see org.omg.uml13.behavioralelements.statemachines.StateMachine#getTransitions()
   */
  public java.util.Collection getTransitions() {
    return getDelegate().getTransitions();
  }

  /**
   * @see org.omg.uml13.behavioralelements.statemachines.StateMachine#getTop()
   */
  public org.omg.uml13.behavioralelements.statemachines.State getTop() {
    return getDelegate().getTop();
  }

  /**
   * @see org.omg.uml13.behavioralelements.statemachines.StateMachine#getContext()
   */
  public org.omg.uml13.foundation.core.ModelElement getContext() {
    return getDelegate().getContext();
  }

  /**
   * @see org.omg.uml13.behavioralelements.statemachines.StateMachine#setContext(org.omg.uml13.foundation.core.ModelElement)
   */
  public void setContext(org.omg.uml13.foundation.core.ModelElement arg0) {
    getDelegate().setContext(arg0);
  }

  /**
   * @see org.omg.uml13.behavioralelements.statemachines.StateMachine#setTop(org.omg.uml13.behavioralelements.statemachines.State)
   */
  public void setTop(org.omg.uml13.behavioralelements.statemachines.State arg0) {
    getDelegate().setTop(arg0);
  }
}