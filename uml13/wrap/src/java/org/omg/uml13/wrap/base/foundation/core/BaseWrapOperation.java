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
import org.omg.uml13.foundation.core.Operation;
import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.core.wrap.Factories;

/**
 * This is a wrapper of org.omg.uml13.foundation.core.Operation that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public abstract class BaseWrapOperation extends org.omg.uml13.wrap.foundation.core.WrapBehavioralFeature implements org.omg.uml13.foundation.core.Operation {

  /**
   * Wraps an already existing object into a new wrapper instance
   */
  protected BaseWrapOperation(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  /**
   * Constructs a new wrapped object
   */
  public BaseWrapOperation(RefPackage extent) {
    this( getClassProxy( extent ).refCreateInstance( null ),
          Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ));
    Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ).bind( this );
  }

  /**
   * This method retrieves the factory for org.omg.uml13.foundation.core.Operation (also known as "class proxy")
   * model class.
   */
  public static RefClass getClassProxy(RefPackage extent) {
    return ((org.omg.uml13.Uml13Package) extent).getFoundation().getCore().getOperation();
  }


  /**
   * Overrides the super implementation with the proper wrapped class
   * @see net.mdatools.modelant.core.wrap.Wrapper#getWrapped()
   */
  public Operation getWrapped() {
    return (Operation) super.getWrapped();
  }

  /**
   * @see net.mdatools.modelant.core.wrap.Wrapper#getDelegate()
   */
  protected Operation getDelegate() {
    return (Operation) super.getDelegate();
  }


  /**
   * @see org.omg.uml13.foundation.core.Operation#isAbstract()
   */
  public boolean isAbstract() {
    return getDelegate().isAbstract();
  }

  /**
   * @see org.omg.uml13.foundation.core.Operation#isLeaf()
   */
  public boolean isLeaf() {
    return getDelegate().isLeaf();
  }

  /**
   * @see org.omg.uml13.foundation.core.Operation#isRoot()
   */
  public boolean isRoot() {
    return getDelegate().isRoot();
  }

  /**
   * @see org.omg.uml13.foundation.core.Operation#getSpecification()
   */
  public java.lang.String getSpecification() {
    return getDelegate().getSpecification();
  }

  /**
   * @see org.omg.uml13.foundation.core.Operation#getMethod()
   */
  public java.util.Collection getMethod() {
    return getDelegate().getMethod();
  }

  /**
   * @see org.omg.uml13.foundation.core.Operation#getConcurrency()
   */
  public org.omg.uml13.foundation.datatypes.CallConcurrencyKind getConcurrency() {
    return getDelegate().getConcurrency();
  }

  /**
   * @see org.omg.uml13.foundation.core.Operation#setAbstract(boolean)
   */
  public void setAbstract(boolean arg0) {
    getDelegate().setAbstract(arg0);
  }

  /**
   * @see org.omg.uml13.foundation.core.Operation#setConcurrency(org.omg.uml13.foundation.datatypes.CallConcurrencyKind)
   */
  public void setConcurrency(org.omg.uml13.foundation.datatypes.CallConcurrencyKind arg0) {
    getDelegate().setConcurrency(arg0);
  }

  /**
   * @see org.omg.uml13.foundation.core.Operation#setLeaf(boolean)
   */
  public void setLeaf(boolean arg0) {
    getDelegate().setLeaf(arg0);
  }

  /**
   * @see org.omg.uml13.foundation.core.Operation#setRoot(boolean)
   */
  public void setRoot(boolean arg0) {
    getDelegate().setRoot(arg0);
  }

  /**
   * @see org.omg.uml13.foundation.core.Operation#setSpecification(java.lang.String)
   */
  public void setSpecification(java.lang.String arg0) {
    getDelegate().setSpecification(arg0);
  }
}