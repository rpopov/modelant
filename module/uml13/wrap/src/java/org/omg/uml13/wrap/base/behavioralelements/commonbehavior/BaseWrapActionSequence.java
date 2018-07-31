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
import org.omg.uml13.behavioralelements.commonbehavior.ActionSequence;
import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.core.wrap.Factories;

/**
 * This is a wrapper of org.omg.uml13.behavioralelements.commonbehavior.ActionSequence that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public abstract class BaseWrapActionSequence extends org.omg.uml13.wrap.behavioralelements.commonbehavior.WrapAction implements org.omg.uml13.behavioralelements.commonbehavior.ActionSequence {

  /**
   * Wraps an already existing object into a new wrapper instance
   */
  protected BaseWrapActionSequence(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  /**
   * Constructs a new wrapped object
   */
  public BaseWrapActionSequence(RefPackage extent) {
    this( getClassProxy( extent ).refCreateInstance( null ),
          Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ));
    Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ).bind( this );
  }

  /**
   * This method retrieves the factory for org.omg.uml13.behavioralelements.commonbehavior.ActionSequence (also known as "class proxy")
   * model class.
   */
  public static RefClass getClassProxy(RefPackage extent) {
    return ((org.omg.uml13.Uml13Package) extent).getBehavioralElements().getCommonBehavior().getActionSequence();
  }


  /**
   * Overrides the super implementation with the proper wrapped class
   * @see net.mdatools.modelant.core.wrap.Wrapper#getWrapped()
   */
  public ActionSequence getWrapped() {
    return (ActionSequence) super.getWrapped();
  }

  /**
   * @see net.mdatools.modelant.core.wrap.Wrapper#getDelegate()
   */
  protected ActionSequence getDelegate() {
    return (ActionSequence) super.getDelegate();
  }


  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.ActionSequence#getAction()
   */
  public java.util.List getAction() {
    return getDelegate().getAction();
  }
}