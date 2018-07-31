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
import org.omg.uml13.behavioralelements.commonbehavior.Argument;
import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.core.wrap.Factories;

/**
 * This is a wrapper of org.omg.uml13.behavioralelements.commonbehavior.Argument that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public abstract class BaseWrapArgument extends org.omg.uml13.wrap.foundation.core.WrapModelElement implements org.omg.uml13.behavioralelements.commonbehavior.Argument {

  /**
   * Wraps an already existing object into a new wrapper instance
   */
  protected BaseWrapArgument(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  /**
   * Constructs a new wrapped object
   */
  public BaseWrapArgument(RefPackage extent) {
    this( getClassProxy( extent ).refCreateInstance( null ),
          Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ));
    Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ).bind( this );
  }

  /**
   * This method retrieves the factory for org.omg.uml13.behavioralelements.commonbehavior.Argument (also known as "class proxy")
   * model class.
   */
  public static RefClass getClassProxy(RefPackage extent) {
    return ((org.omg.uml13.Uml13Package) extent).getBehavioralElements().getCommonBehavior().getArgument();
  }


  /**
   * Overrides the super implementation with the proper wrapped class
   * @see net.mdatools.modelant.core.wrap.Wrapper#getWrapped()
   */
  public Argument getWrapped() {
    return (Argument) super.getWrapped();
  }

  /**
   * @see net.mdatools.modelant.core.wrap.Wrapper#getDelegate()
   */
  protected Argument getDelegate() {
    return (Argument) super.getDelegate();
  }


  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.Argument#getAction()
   */
  public org.omg.uml13.behavioralelements.commonbehavior.Action getAction() {
    return getDelegate().getAction();
  }

  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.Argument#getValue()
   */
  public org.omg.uml13.foundation.datatypes.Expression getValue() {
    return getDelegate().getValue();
  }

  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.Argument#setAction(org.omg.uml13.behavioralelements.commonbehavior.Action)
   */
  public void setAction(org.omg.uml13.behavioralelements.commonbehavior.Action arg0) {
    getDelegate().setAction(arg0);
  }

  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.Argument#setValue(org.omg.uml13.foundation.datatypes.Expression)
   */
  public void setValue(org.omg.uml13.foundation.datatypes.Expression arg0) {
    getDelegate().setValue(arg0);
  }
}