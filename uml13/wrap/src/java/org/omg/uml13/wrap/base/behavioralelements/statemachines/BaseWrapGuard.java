/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml13.wrap.base.behavioralelements.statemachines;

import javax.jmi.reflect.RefClass;
import javax.jmi.reflect.RefPackage;
import org.omg.uml13.behavioralelements.statemachines.Guard;
import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.core.wrap.Factories;

/**
 * This is a wrapper of org.omg.uml13.behavioralelements.statemachines.Guard that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public abstract class BaseWrapGuard extends org.omg.uml13.wrap.foundation.core.WrapModelElement implements org.omg.uml13.behavioralelements.statemachines.Guard {

  /**
   * Wraps an already existing object into a new wrapper instance
   */
  protected BaseWrapGuard(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  /**
   * Constructs a new wrapped object
   */
  public BaseWrapGuard(RefPackage extent) {
    this( getClassProxy( extent ).refCreateInstance( null ),
          Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ));
    Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ).bind( this );
  }

  /**
   * This method retrieves the factory for org.omg.uml13.behavioralelements.statemachines.Guard (also known as "class proxy")
   * model class.
   */
  public static RefClass getClassProxy(RefPackage extent) {
    return ((org.omg.uml13.Uml13Package) extent).getBehavioralElements().getStateMachines().getGuard();
  }


  /**
   * Overrides the super implementation with the proper wrapped class
   * @see net.mdatools.modelant.core.wrap.Wrapper#getWrapped()
   */
  public Guard getWrapped() {
    return (Guard) super.getWrapped();
  }

  /**
   * @see net.mdatools.modelant.core.wrap.Wrapper#getDelegate()
   */
  protected Guard getDelegate() {
    return (Guard) super.getDelegate();
  }


  /**
   * @see org.omg.uml13.behavioralelements.statemachines.Guard#getTransition()
   */
  public org.omg.uml13.behavioralelements.statemachines.Transition getTransition() {
    return getDelegate().getTransition();
  }

  /**
   * @see org.omg.uml13.behavioralelements.statemachines.Guard#getExpression()
   */
  public org.omg.uml13.foundation.datatypes.BooleanExpression getExpression() {
    return getDelegate().getExpression();
  }

  /**
   * @see org.omg.uml13.behavioralelements.statemachines.Guard#setExpression(org.omg.uml13.foundation.datatypes.BooleanExpression)
   */
  public void setExpression(org.omg.uml13.foundation.datatypes.BooleanExpression arg0) {
    getDelegate().setExpression(arg0);
  }

  /**
   * @see org.omg.uml13.behavioralelements.statemachines.Guard#setTransition(org.omg.uml13.behavioralelements.statemachines.Transition)
   */
  public void setTransition(org.omg.uml13.behavioralelements.statemachines.Transition arg0) {
    getDelegate().setTransition(arg0);
  }
}