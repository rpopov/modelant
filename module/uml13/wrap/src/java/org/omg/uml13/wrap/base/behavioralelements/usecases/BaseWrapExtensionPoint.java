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
import org.omg.uml13.behavioralelements.usecases.ExtensionPoint;
import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.core.wrap.Factories;

/**
 * This is a wrapper of org.omg.uml13.behavioralelements.usecases.ExtensionPoint that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public abstract class BaseWrapExtensionPoint extends org.omg.uml13.wrap.foundation.core.WrapModelElement implements org.omg.uml13.behavioralelements.usecases.ExtensionPoint {

  /**
   * Wraps an already existing object into a new wrapper instance
   */
  protected BaseWrapExtensionPoint(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  /**
   * Constructs a new wrapped object
   */
  public BaseWrapExtensionPoint(RefPackage extent) {
    this( getClassProxy( extent ).refCreateInstance( null ),
          Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ));
    Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ).bind( this );
  }

  /**
   * This method retrieves the factory for org.omg.uml13.behavioralelements.usecases.ExtensionPoint (also known as "class proxy")
   * model class.
   */
  public static RefClass getClassProxy(RefPackage extent) {
    return ((org.omg.uml13.Uml13Package) extent).getBehavioralElements().getUseCases().getExtensionPoint();
  }


  /**
   * Overrides the super implementation with the proper wrapped class
   * @see net.mdatools.modelant.core.wrap.Wrapper#getWrapped()
   */
  public ExtensionPoint getWrapped() {
    return (ExtensionPoint) super.getWrapped();
  }

  /**
   * @see net.mdatools.modelant.core.wrap.Wrapper#getDelegate()
   */
  protected ExtensionPoint getDelegate() {
    return (ExtensionPoint) super.getDelegate();
  }


  /**
   * @see org.omg.uml13.behavioralelements.usecases.ExtensionPoint#getLocation()
   */
  public java.lang.String getLocation() {
    return getDelegate().getLocation();
  }

  /**
   * @see org.omg.uml13.behavioralelements.usecases.ExtensionPoint#getExtend()
   */
  public java.util.Collection getExtend() {
    return getDelegate().getExtend();
  }

  /**
   * @see org.omg.uml13.behavioralelements.usecases.ExtensionPoint#getUseCase()
   */
  public org.omg.uml13.behavioralelements.usecases.UseCase getUseCase() {
    return getDelegate().getUseCase();
  }

  /**
   * @see org.omg.uml13.behavioralelements.usecases.ExtensionPoint#setLocation(java.lang.String)
   */
  public void setLocation(java.lang.String arg0) {
    getDelegate().setLocation(arg0);
  }

  /**
   * @see org.omg.uml13.behavioralelements.usecases.ExtensionPoint#setUseCase(org.omg.uml13.behavioralelements.usecases.UseCase)
   */
  public void setUseCase(org.omg.uml13.behavioralelements.usecases.UseCase arg0) {
    getDelegate().setUseCase(arg0);
  }
}