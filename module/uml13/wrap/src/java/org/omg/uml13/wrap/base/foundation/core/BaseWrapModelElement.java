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
package org.omg.uml13.wrap.base.foundation.core;

import org.omg.uml13.foundation.core.ModelElement;
import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.core.wrap.Factories;

/**
 * This is a wrapper of org.omg.uml13.foundation.core.ModelElement that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public abstract class BaseWrapModelElement extends org.omg.uml13.wrap.foundation.core.WrapElement implements org.omg.uml13.foundation.core.ModelElement {

  /**
   * Wraps an already existing object into a new wrapper instance
   */
  protected BaseWrapModelElement(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }


  /**
   * Overrides the super implementation with the proper wrapped class
   * @see net.mdatools.modelant.core.wrap.Wrapper#getWrapped()
   */
  public ModelElement getWrapped() {
    return (ModelElement) super.getWrapped();
  }

  /**
   * @see net.mdatools.modelant.core.wrap.Wrapper#getDelegate()
   */
  protected ModelElement getDelegate() {
    return (ModelElement) super.getDelegate();
  }


  /**
   * @see org.omg.uml13.foundation.core.ModelElement#isSpecification()
   */
  public boolean isSpecification() {
    return getDelegate().isSpecification();
  }

  /**
   * @see org.omg.uml13.foundation.core.ModelElement#getName()
   */
  public java.lang.String getName() {
    return getDelegate().getName();
  }

  /**
   * @see org.omg.uml13.foundation.core.ModelElement#getClientDependency()
   */
  public java.util.Collection getClientDependency() {
    return getDelegate().getClientDependency();
  }

  /**
   * @see org.omg.uml13.foundation.core.ModelElement#getComment()
   */
  public java.util.Collection getComment() {
    return getDelegate().getComment();
  }

  /**
   * @see org.omg.uml13.foundation.core.ModelElement#getConstraint()
   */
  public java.util.Collection getConstraint() {
    return getDelegate().getConstraint();
  }

  /**
   * @see org.omg.uml13.foundation.core.ModelElement#getElementResidence()
   */
  public java.util.Collection getElementResidence() {
    return getDelegate().getElementResidence();
  }

  /**
   * @see org.omg.uml13.foundation.core.ModelElement#getPresentation()
   */
  public java.util.Collection getPresentation() {
    return getDelegate().getPresentation();
  }

  /**
   * @see org.omg.uml13.foundation.core.ModelElement#getSourceFlow()
   */
  public java.util.Collection getSourceFlow() {
    return getDelegate().getSourceFlow();
  }

  /**
   * @see org.omg.uml13.foundation.core.ModelElement#getSupplierDependency()
   */
  public java.util.Collection getSupplierDependency() {
    return getDelegate().getSupplierDependency();
  }

  /**
   * @see org.omg.uml13.foundation.core.ModelElement#getTargetFlow()
   */
  public java.util.Collection getTargetFlow() {
    return getDelegate().getTargetFlow();
  }

  /**
   * @see org.omg.uml13.foundation.core.ModelElement#getTemplateParameter2()
   */
  public java.util.Collection getTemplateParameter2() {
    return getDelegate().getTemplateParameter2();
  }

  /**
   * @see org.omg.uml13.foundation.core.ModelElement#getTemplateParameter3()
   */
  public java.util.Collection getTemplateParameter3() {
    return getDelegate().getTemplateParameter3();
  }

  /**
   * @see org.omg.uml13.foundation.core.ModelElement#getTemplateParameter()
   */
  public java.util.List getTemplateParameter() {
    return getDelegate().getTemplateParameter();
  }

  /**
   * @see org.omg.uml13.foundation.core.ModelElement#getBinding()
   */
  public org.omg.uml13.foundation.core.Binding getBinding() {
    return getDelegate().getBinding();
  }

  /**
   * @see org.omg.uml13.foundation.core.ModelElement#getNamespace()
   */
  public org.omg.uml13.foundation.core.Namespace getNamespace() {
    return getDelegate().getNamespace();
  }

  /**
   * @see org.omg.uml13.foundation.core.ModelElement#getVisibility()
   */
  public org.omg.uml13.foundation.datatypes.VisibilityKind getVisibility() {
    return getDelegate().getVisibility();
  }

  /**
   * @see org.omg.uml13.foundation.core.ModelElement#setBinding(org.omg.uml13.foundation.core.Binding)
   */
  public void setBinding(org.omg.uml13.foundation.core.Binding arg0) {
    getDelegate().setBinding(arg0);
  }

  /**
   * @see org.omg.uml13.foundation.core.ModelElement#setName(java.lang.String)
   */
  public void setName(java.lang.String arg0) {
    getDelegate().setName(arg0);
  }

  /**
   * @see org.omg.uml13.foundation.core.ModelElement#setNamespace(org.omg.uml13.foundation.core.Namespace)
   */
  public void setNamespace(org.omg.uml13.foundation.core.Namespace arg0) {
    getDelegate().setNamespace(arg0);
  }

  /**
   * @see org.omg.uml13.foundation.core.ModelElement#setSpecification(boolean)
   */
  public void setSpecification(boolean arg0) {
    getDelegate().setSpecification(arg0);
  }

  /**
   * @see org.omg.uml13.foundation.core.ModelElement#setVisibility(org.omg.uml13.foundation.datatypes.VisibilityKind)
   */
  public void setVisibility(org.omg.uml13.foundation.datatypes.VisibilityKind arg0) {
    getDelegate().setVisibility(arg0);
  }
}