/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml13.wrap.base.behavioralelements.usecases;

import javax.jmi.reflect.RefClass;
import javax.jmi.reflect.RefPackage;
import org.omg.uml13.behavioralelements.usecases.Extend;
import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.core.wrap.Factories;

/**
 * This is a wrapper of org.omg.uml13.behavioralelements.usecases.Extend that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public abstract class BaseWrapExtend extends org.omg.uml13.wrap.foundation.core.WrapRelationship implements org.omg.uml13.behavioralelements.usecases.Extend {

  /**
   * Wraps an already existing object into a new wrapper instance
   */
  protected BaseWrapExtend(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  /**
   * Constructs a new wrapped object
   */
  public BaseWrapExtend(RefPackage extent) {
    this( getClassProxy( extent ).refCreateInstance( null ),
          Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ));
    Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ).bind( this );
  }

  /**
   * This method retrieves the factory for org.omg.uml13.behavioralelements.usecases.Extend (also known as "class proxy")
   * model class.
   */
  public static RefClass getClassProxy(RefPackage extent) {
    return ((org.omg.uml13.Uml13Package) extent).getBehavioralElements().getUseCases().getExtend();
  }


  /**
   * Overrides the super implementation with the proper wrapped class
   * @see net.mdatools.modelant.core.wrap.Wrapper#getWrapped()
   */
  public Extend getWrapped() {
    return (Extend) super.getWrapped();
  }

  /**
   * @see net.mdatools.modelant.core.wrap.Wrapper#getDelegate()
   */
  protected Extend getDelegate() {
    return (Extend) super.getDelegate();
  }


  /**
   * @see org.omg.uml13.behavioralelements.usecases.Extend#getExtensionPoint()
   */
  public java.util.List getExtensionPoint() {
    return getDelegate().getExtensionPoint();
  }

  /**
   * @see org.omg.uml13.behavioralelements.usecases.Extend#getBase()
   */
  public org.omg.uml13.behavioralelements.usecases.UseCase getBase() {
    return getDelegate().getBase();
  }

  /**
   * @see org.omg.uml13.behavioralelements.usecases.Extend#getExtension()
   */
  public org.omg.uml13.behavioralelements.usecases.UseCase getExtension() {
    return getDelegate().getExtension();
  }

  /**
   * @see org.omg.uml13.behavioralelements.usecases.Extend#getCondition()
   */
  public org.omg.uml13.foundation.datatypes.BooleanExpression getCondition() {
    return getDelegate().getCondition();
  }

  /**
   * @see org.omg.uml13.behavioralelements.usecases.Extend#setBase(org.omg.uml13.behavioralelements.usecases.UseCase)
   */
  public void setBase(org.omg.uml13.behavioralelements.usecases.UseCase arg0) {
    getDelegate().setBase(arg0);
  }

  /**
   * @see org.omg.uml13.behavioralelements.usecases.Extend#setCondition(org.omg.uml13.foundation.datatypes.BooleanExpression)
   */
  public void setCondition(org.omg.uml13.foundation.datatypes.BooleanExpression arg0) {
    getDelegate().setCondition(arg0);
  }

  /**
   * @see org.omg.uml13.behavioralelements.usecases.Extend#setExtension(org.omg.uml13.behavioralelements.usecases.UseCase)
   */
  public void setExtension(org.omg.uml13.behavioralelements.usecases.UseCase arg0) {
    getDelegate().setExtension(arg0);
  }
}