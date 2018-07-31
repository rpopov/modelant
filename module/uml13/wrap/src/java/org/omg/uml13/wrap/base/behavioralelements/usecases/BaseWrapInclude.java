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
package org.omg.uml13.wrap.base.behavioralelements.usecases;

import javax.jmi.reflect.RefClass;
import javax.jmi.reflect.RefPackage;
import org.omg.uml13.behavioralelements.usecases.Include;
import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.core.wrap.Factories;

/**
 * This is a wrapper of org.omg.uml13.behavioralelements.usecases.Include that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public abstract class BaseWrapInclude extends org.omg.uml13.wrap.foundation.core.WrapRelationship implements org.omg.uml13.behavioralelements.usecases.Include {

  /**
   * Wraps an already existing object into a new wrapper instance
   */
  protected BaseWrapInclude(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  /**
   * Constructs a new wrapped object
   */
  public BaseWrapInclude(RefPackage extent) {
    this( getClassProxy( extent ).refCreateInstance( null ),
          Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ));
    Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ).bind( this );
  }

  /**
   * This method retrieves the factory for org.omg.uml13.behavioralelements.usecases.Include (also known as "class proxy")
   * model class.
   */
  public static RefClass getClassProxy(RefPackage extent) {
    return ((org.omg.uml13.Uml13Package) extent).getBehavioralElements().getUseCases().getInclude();
  }


  /**
   * Overrides the super implementation with the proper wrapped class
   * @see net.mdatools.modelant.core.wrap.Wrapper#getWrapped()
   */
  public Include getWrapped() {
    return (Include) super.getWrapped();
  }

  /**
   * @see net.mdatools.modelant.core.wrap.Wrapper#getDelegate()
   */
  protected Include getDelegate() {
    return (Include) super.getDelegate();
  }


  /**
   * @see org.omg.uml13.behavioralelements.usecases.Include#getAddition()
   */
  public org.omg.uml13.behavioralelements.usecases.UseCase getAddition() {
    return getDelegate().getAddition();
  }

  /**
   * @see org.omg.uml13.behavioralelements.usecases.Include#getBase()
   */
  public org.omg.uml13.behavioralelements.usecases.UseCase getBase() {
    return getDelegate().getBase();
  }

  /**
   * @see org.omg.uml13.behavioralelements.usecases.Include#setAddition(org.omg.uml13.behavioralelements.usecases.UseCase)
   */
  public void setAddition(org.omg.uml13.behavioralelements.usecases.UseCase arg0) {
    getDelegate().setAddition(arg0);
  }

  /**
   * @see org.omg.uml13.behavioralelements.usecases.Include#setBase(org.omg.uml13.behavioralelements.usecases.UseCase)
   */
  public void setBase(org.omg.uml13.behavioralelements.usecases.UseCase arg0) {
    getDelegate().setBase(arg0);
  }
}