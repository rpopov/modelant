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
package org.omg.uml13.wrap.base.behavioralelements.commonbehavior;

import javax.jmi.reflect.RefClass;
import javax.jmi.reflect.RefPackage;
import org.omg.uml13.behavioralelements.commonbehavior.CallAction;
import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.core.wrap.Factories;

/**
 * This is a wrapper of org.omg.uml13.behavioralelements.commonbehavior.CallAction that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public abstract class BaseWrapCallAction extends org.omg.uml13.wrap.behavioralelements.commonbehavior.WrapAction implements org.omg.uml13.behavioralelements.commonbehavior.CallAction {

  /**
   * Wraps an already existing object into a new wrapper instance
   */
  protected BaseWrapCallAction(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  /**
   * Constructs a new wrapped object
   */
  public BaseWrapCallAction(RefPackage extent) {
    this( getClassProxy( extent ).refCreateInstance( null ),
          Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ));
    Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ).bind( this );
  }

  /**
   * This method retrieves the factory for org.omg.uml13.behavioralelements.commonbehavior.CallAction (also known as "class proxy")
   * model class.
   */
  public static RefClass getClassProxy(RefPackage extent) {
    return ((org.omg.uml13.Uml13Package) extent).getBehavioralElements().getCommonBehavior().getCallAction();
  }


  /**
   * Overrides the super implementation with the proper wrapped class
   * @see net.mdatools.modelant.core.wrap.Wrapper#getWrapped()
   */
  public CallAction getWrapped() {
    return (CallAction) super.getWrapped();
  }

  /**
   * @see net.mdatools.modelant.core.wrap.Wrapper#getDelegate()
   */
  protected CallAction getDelegate() {
    return (CallAction) super.getDelegate();
  }


  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.CallAction#getOperation()
   */
  public org.omg.uml13.foundation.core.Operation getOperation() {
    return getDelegate().getOperation();
  }

  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.CallAction#setOperation(org.omg.uml13.foundation.core.Operation)
   */
  public void setOperation(org.omg.uml13.foundation.core.Operation arg0) {
    getDelegate().setOperation(arg0);
  }
}