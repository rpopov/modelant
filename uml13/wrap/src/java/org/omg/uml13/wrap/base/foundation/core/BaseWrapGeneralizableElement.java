/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml13.wrap.base.foundation.core;

import org.omg.uml13.foundation.core.GeneralizableElement;
import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.core.wrap.Factories;

/**
 * This is a wrapper of org.omg.uml13.foundation.core.GeneralizableElement that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public abstract class BaseWrapGeneralizableElement extends org.omg.uml13.wrap.foundation.core.WrapModelElement implements org.omg.uml13.foundation.core.GeneralizableElement {

  /**
   * Wraps an already existing object into a new wrapper instance
   */
  protected BaseWrapGeneralizableElement(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }


  /**
   * Overrides the super implementation with the proper wrapped class
   * @see net.mdatools.modelant.core.wrap.Wrapper#getWrapped()
   */
  public GeneralizableElement getWrapped() {
    return (GeneralizableElement) super.getWrapped();
  }

  /**
   * @see net.mdatools.modelant.core.wrap.Wrapper#getDelegate()
   */
  protected GeneralizableElement getDelegate() {
    return (GeneralizableElement) super.getDelegate();
  }


  /**
   * @see org.omg.uml13.foundation.core.GeneralizableElement#isAbstract()
   */
  public boolean isAbstract() {
    return getDelegate().isAbstract();
  }

  /**
   * @see org.omg.uml13.foundation.core.GeneralizableElement#isLeaf()
   */
  public boolean isLeaf() {
    return getDelegate().isLeaf();
  }

  /**
   * @see org.omg.uml13.foundation.core.GeneralizableElement#isRoot()
   */
  public boolean isRoot() {
    return getDelegate().isRoot();
  }

  /**
   * @see org.omg.uml13.foundation.core.GeneralizableElement#getGeneralization()
   */
  public java.util.Collection getGeneralization() {
    return getDelegate().getGeneralization();
  }

  /**
   * @see org.omg.uml13.foundation.core.GeneralizableElement#getSpecialization()
   */
  public java.util.Collection getSpecialization() {
    return getDelegate().getSpecialization();
  }

  /**
   * @see org.omg.uml13.foundation.core.GeneralizableElement#setAbstract(boolean)
   */
  public void setAbstract(boolean arg0) {
    getDelegate().setAbstract(arg0);
  }

  /**
   * @see org.omg.uml13.foundation.core.GeneralizableElement#setLeaf(boolean)
   */
  public void setLeaf(boolean arg0) {
    getDelegate().setLeaf(arg0);
  }

  /**
   * @see org.omg.uml13.foundation.core.GeneralizableElement#setRoot(boolean)
   */
  public void setRoot(boolean arg0) {
    getDelegate().setRoot(arg0);
  }
}