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
import org.omg.uml13.behavioralelements.statemachines.SynchState;
import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.core.wrap.Factories;

/**
 * This is a wrapper of org.omg.uml13.behavioralelements.statemachines.SynchState that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public abstract class BaseWrapSynchState extends org.omg.uml13.wrap.behavioralelements.statemachines.WrapStateVertex implements org.omg.uml13.behavioralelements.statemachines.SynchState {

  /**
   * Wraps an already existing object into a new wrapper instance
   */
  protected BaseWrapSynchState(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  /**
   * Constructs a new wrapped object
   */
  public BaseWrapSynchState(RefPackage extent) {
    this( getClassProxy( extent ).refCreateInstance( null ),
          Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ));
    Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ).bind( this );
  }

  /**
   * This method retrieves the factory for org.omg.uml13.behavioralelements.statemachines.SynchState (also known as "class proxy")
   * model class.
   */
  public static RefClass getClassProxy(RefPackage extent) {
    return ((org.omg.uml13.Uml13Package) extent).getBehavioralElements().getStateMachines().getSynchState();
  }


  /**
   * Overrides the super implementation with the proper wrapped class
   * @see net.mdatools.modelant.core.wrap.Wrapper#getWrapped()
   */
  public SynchState getWrapped() {
    return (SynchState) super.getWrapped();
  }

  /**
   * @see net.mdatools.modelant.core.wrap.Wrapper#getDelegate()
   */
  protected SynchState getDelegate() {
    return (SynchState) super.getDelegate();
  }


  /**
   * @see org.omg.uml13.behavioralelements.statemachines.SynchState#getBound()
   */
  public int getBound() {
    return getDelegate().getBound();
  }

  /**
   * @see org.omg.uml13.behavioralelements.statemachines.SynchState#setBound(int)
   */
  public void setBound(int arg0) {
    getDelegate().setBound(arg0);
  }
}