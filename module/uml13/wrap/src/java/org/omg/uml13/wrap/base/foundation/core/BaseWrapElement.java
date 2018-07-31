/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml13.wrap.base.foundation.core;

import org.omg.uml13.foundation.core.Element;
import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.core.wrap.Factories;

/**
 * This is a wrapper of org.omg.uml13.foundation.core.Element that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public abstract class BaseWrapElement extends net.mdatools.modelant.core.wrap.Wrapper implements org.omg.uml13.foundation.core.Element {

  /**
   * Wraps an already existing object into a new wrapper instance
   */
  protected BaseWrapElement(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }


   /**
    * The factory instance of this wrapper
    */
   protected final org.omg.uml13.wrap.Uml13WrapFactory getFactory() {
     return (org.omg.uml13.wrap.Uml13WrapFactory) super.getFactory();
   }

  /**
   * Overrides the super implementation with the proper wrapped class
   * @see net.mdatools.modelant.core.wrap.Wrapper#getWrapped()
   */
  public Element getWrapped() {
    return (Element) super.getWrapped();
  }

  /**
   * @see net.mdatools.modelant.core.wrap.Wrapper#getDelegate()
   */
  protected Element getDelegate() {
    return (Element) super.getDelegate();
  }


  /**
   * @see javax.jmi.reflect.RefBaseObject#equals(java.lang.Object)
   */
  public boolean equals(java.lang.Object arg0) {
    return getDelegate().equals(arg0);
  }

  /**
   * @see javax.jmi.reflect.RefObject#refIsInstanceOf(javax.jmi.reflect.RefObject, boolean)
   */
  public boolean refIsInstanceOf(javax.jmi.reflect.RefObject arg0, boolean arg1) {
    return getDelegate().refIsInstanceOf(arg0, arg1);
  }

  /**
   * @see javax.jmi.reflect.RefBaseObject#hashCode()
   */
  public int hashCode() {
    return getDelegate().hashCode();
  }

  /**
   * @see javax.jmi.reflect.RefFeatured#refGetValue(java.lang.String)
   */
  public java.lang.Object refGetValue(java.lang.String arg0) {
    return getDelegate().refGetValue(arg0);
  }

  /**
   * @see javax.jmi.reflect.RefFeatured#refGetValue(javax.jmi.reflect.RefObject)
   */
  public java.lang.Object refGetValue(javax.jmi.reflect.RefObject arg0) {
    return getDelegate().refGetValue(arg0);
  }

  /**
   * @see javax.jmi.reflect.RefFeatured#refInvokeOperation(java.lang.String, java.util.List)
   */
  public java.lang.Object refInvokeOperation(java.lang.String arg0, java.util.List arg1) throws javax.jmi.reflect.RefException {
    return getDelegate().refInvokeOperation(arg0, arg1);
  }

  /**
   * @see javax.jmi.reflect.RefFeatured#refInvokeOperation(javax.jmi.reflect.RefObject, java.util.List)
   */
  public java.lang.Object refInvokeOperation(javax.jmi.reflect.RefObject arg0, java.util.List arg1) throws javax.jmi.reflect.RefException {
    return getDelegate().refInvokeOperation(arg0, arg1);
  }

  /**
   * @see javax.jmi.reflect.RefBaseObject#refMofId()
   */
  public java.lang.String refMofId() {
    return getDelegate().refMofId();
  }

  /**
   * @see javax.jmi.reflect.RefBaseObject#refVerifyConstraints(boolean)
   */
  public java.util.Collection refVerifyConstraints(boolean arg0) {
    return getDelegate().refVerifyConstraints(arg0);
  }

  /**
   * @see javax.jmi.reflect.RefObject#refClass()
   */
  public javax.jmi.reflect.RefClass refClass() {
    return getDelegate().refClass();
  }

  /**
   * @see javax.jmi.reflect.RefObject#refImmediateComposite()
   */
  public javax.jmi.reflect.RefFeatured refImmediateComposite() {
    return getDelegate().refImmediateComposite();
  }

  /**
   * @see javax.jmi.reflect.RefObject#refOutermostComposite()
   */
  public javax.jmi.reflect.RefFeatured refOutermostComposite() {
    return getDelegate().refOutermostComposite();
  }

  /**
   * @see javax.jmi.reflect.RefBaseObject#refMetaObject()
   */
  public javax.jmi.reflect.RefObject refMetaObject() {
    return getDelegate().refMetaObject();
  }

  /**
   * @see javax.jmi.reflect.RefBaseObject#refImmediatePackage()
   */
  public javax.jmi.reflect.RefPackage refImmediatePackage() {
    return getDelegate().refImmediatePackage();
  }

  /**
   * @see javax.jmi.reflect.RefBaseObject#refOutermostPackage()
   */
  public javax.jmi.reflect.RefPackage refOutermostPackage() {
    return getDelegate().refOutermostPackage();
  }

  /**
   * @see javax.jmi.reflect.RefFeatured#refSetValue(java.lang.String, java.lang.Object)
   */
  public void refSetValue(java.lang.String arg0, java.lang.Object arg1) {
    getDelegate().refSetValue(arg0, arg1);
  }

  /**
   * @see javax.jmi.reflect.RefFeatured#refSetValue(javax.jmi.reflect.RefObject, java.lang.Object)
   */
  public void refSetValue(javax.jmi.reflect.RefObject arg0, java.lang.Object arg1) {
    getDelegate().refSetValue(arg0, arg1);
  }

  /**
   * @see javax.jmi.reflect.RefObject#refDelete()
   */
  public void refDelete() {
    getDelegate().refDelete();
  }
}