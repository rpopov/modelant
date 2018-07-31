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
import org.omg.uml13.behavioralelements.usecases.UseCase;
import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.core.wrap.Factories;

/**
 * This is a wrapper of org.omg.uml13.behavioralelements.usecases.UseCase that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public abstract class BaseWrapUseCase extends org.omg.uml13.wrap.foundation.core.WrapClassifier implements org.omg.uml13.behavioralelements.usecases.UseCase {

  /**
   * Wraps an already existing object into a new wrapper instance
   */
  protected BaseWrapUseCase(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  /**
   * Constructs a new wrapped object
   */
  public BaseWrapUseCase(RefPackage extent) {
    this( getClassProxy( extent ).refCreateInstance( null ),
          Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ));
    Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ).bind( this );
  }

  /**
   * This method retrieves the factory for org.omg.uml13.behavioralelements.usecases.UseCase (also known as "class proxy")
   * model class.
   */
  public static RefClass getClassProxy(RefPackage extent) {
    return ((org.omg.uml13.Uml13Package) extent).getBehavioralElements().getUseCases().getUseCase();
  }


  /**
   * Overrides the super implementation with the proper wrapped class
   * @see net.mdatools.modelant.core.wrap.Wrapper#getWrapped()
   */
  public UseCase getWrapped() {
    return (UseCase) super.getWrapped();
  }

  /**
   * @see net.mdatools.modelant.core.wrap.Wrapper#getDelegate()
   */
  protected UseCase getDelegate() {
    return (UseCase) super.getDelegate();
  }


  /**
   * @see org.omg.uml13.behavioralelements.usecases.UseCase#getExtend()
   */
  public java.util.Collection getExtend() {
    return getDelegate().getExtend();
  }

  /**
   * @see org.omg.uml13.behavioralelements.usecases.UseCase#getExtend2()
   */
  public java.util.Collection getExtend2() {
    return getDelegate().getExtend2();
  }

  /**
   * @see org.omg.uml13.behavioralelements.usecases.UseCase#getExtensionPoint()
   */
  public java.util.Collection getExtensionPoint() {
    return getDelegate().getExtensionPoint();
  }

  /**
   * @see org.omg.uml13.behavioralelements.usecases.UseCase#getInclude()
   */
  public java.util.Collection getInclude() {
    return getDelegate().getInclude();
  }

  /**
   * @see org.omg.uml13.behavioralelements.usecases.UseCase#getInclude2()
   */
  public java.util.Collection getInclude2() {
    return getDelegate().getInclude2();
  }
}