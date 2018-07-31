/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml13.wrap.base.behavioralelements.commonbehavior;

import javax.jmi.reflect.RefClass;
import javax.jmi.reflect.RefPackage;
import org.omg.uml13.behavioralelements.commonbehavior.Signal;
import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.core.wrap.Factories;

/**
 * This is a wrapper of org.omg.uml13.behavioralelements.commonbehavior.Signal that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public abstract class BaseWrapSignal extends org.omg.uml13.wrap.foundation.core.WrapClassifier implements org.omg.uml13.behavioralelements.commonbehavior.Signal {

  /**
   * Wraps an already existing object into a new wrapper instance
   */
  protected BaseWrapSignal(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  /**
   * Constructs a new wrapped object
   */
  public BaseWrapSignal(RefPackage extent) {
    this( getClassProxy( extent ).refCreateInstance( null ),
          Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ));
    Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ).bind( this );
  }

  /**
   * This method retrieves the factory for org.omg.uml13.behavioralelements.commonbehavior.Signal (also known as "class proxy")
   * model class.
   */
  public static RefClass getClassProxy(RefPackage extent) {
    return ((org.omg.uml13.Uml13Package) extent).getBehavioralElements().getCommonBehavior().getSignal();
  }


  /**
   * Overrides the super implementation with the proper wrapped class
   * @see net.mdatools.modelant.core.wrap.Wrapper#getWrapped()
   */
  public Signal getWrapped() {
    return (Signal) super.getWrapped();
  }

  /**
   * @see net.mdatools.modelant.core.wrap.Wrapper#getDelegate()
   */
  protected Signal getDelegate() {
    return (Signal) super.getDelegate();
  }


  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.Signal#getContext()
   */
  public java.util.Collection getContext() {
    return getDelegate().getContext();
  }

  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.Signal#getReception()
   */
  public java.util.Collection getReception() {
    return getDelegate().getReception();
  }

  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.Signal#getSendAction()
   */
  public java.util.Collection getSendAction() {
    return getDelegate().getSendAction();
  }
}