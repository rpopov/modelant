/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml13.wrap.base.foundation.core;

import javax.jmi.reflect.RefClass;
import javax.jmi.reflect.RefPackage;
import org.omg.uml13.foundation.core.Parameter;
import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.core.wrap.Factories;

/**
 * This is a wrapper of org.omg.uml13.foundation.core.Parameter that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public abstract class BaseWrapParameter extends org.omg.uml13.wrap.foundation.core.WrapModelElement implements org.omg.uml13.foundation.core.Parameter {

  /**
   * Wraps an already existing object into a new wrapper instance
   */
  protected BaseWrapParameter(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  /**
   * Constructs a new wrapped object
   */
  public BaseWrapParameter(RefPackage extent) {
    this( getClassProxy( extent ).refCreateInstance( null ),
          Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ));
    Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ).bind( this );
  }

  /**
   * This method retrieves the factory for org.omg.uml13.foundation.core.Parameter (also known as "class proxy")
   * model class.
   */
  public static RefClass getClassProxy(RefPackage extent) {
    return ((org.omg.uml13.Uml13Package) extent).getFoundation().getCore().getParameter();
  }


  /**
   * Overrides the super implementation with the proper wrapped class
   * @see net.mdatools.modelant.core.wrap.Wrapper#getWrapped()
   */
  public Parameter getWrapped() {
    return (Parameter) super.getWrapped();
  }

  /**
   * @see net.mdatools.modelant.core.wrap.Wrapper#getDelegate()
   */
  protected Parameter getDelegate() {
    return (Parameter) super.getDelegate();
  }


  /**
   * @see org.omg.uml13.foundation.core.Parameter#getBehavioralFeature()
   */
  public org.omg.uml13.foundation.core.BehavioralFeature getBehavioralFeature() {
    return getDelegate().getBehavioralFeature();
  }

  /**
   * @see org.omg.uml13.foundation.core.Parameter#getType()
   */
  public org.omg.uml13.foundation.core.Classifier getType() {
    return getDelegate().getType();
  }

  /**
   * @see org.omg.uml13.foundation.core.Parameter#getDefaultValue()
   */
  public org.omg.uml13.foundation.datatypes.Expression getDefaultValue() {
    return getDelegate().getDefaultValue();
  }

  /**
   * @see org.omg.uml13.foundation.core.Parameter#getKind()
   */
  public org.omg.uml13.foundation.datatypes.ParameterDirectionKind getKind() {
    return getDelegate().getKind();
  }

  /**
   * @see org.omg.uml13.foundation.core.Parameter#setBehavioralFeature(org.omg.uml13.foundation.core.BehavioralFeature)
   */
  public void setBehavioralFeature(org.omg.uml13.foundation.core.BehavioralFeature arg0) {
    getDelegate().setBehavioralFeature(arg0);
  }

  /**
   * @see org.omg.uml13.foundation.core.Parameter#setDefaultValue(org.omg.uml13.foundation.datatypes.Expression)
   */
  public void setDefaultValue(org.omg.uml13.foundation.datatypes.Expression arg0) {
    getDelegate().setDefaultValue(arg0);
  }

  /**
   * @see org.omg.uml13.foundation.core.Parameter#setKind(org.omg.uml13.foundation.datatypes.ParameterDirectionKind)
   */
  public void setKind(org.omg.uml13.foundation.datatypes.ParameterDirectionKind arg0) {
    getDelegate().setKind(arg0);
  }

  /**
   * @see org.omg.uml13.foundation.core.Parameter#setType(org.omg.uml13.foundation.core.Classifier)
   */
  public void setType(org.omg.uml13.foundation.core.Classifier arg0) {
    getDelegate().setType(arg0);
  }
}