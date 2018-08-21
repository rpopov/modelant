/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml13.wrap.base.foundation.core;

import org.omg.uml13.foundation.core.Feature;
import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.core.wrap.Factories;

/**
 * This is a wrapper of org.omg.uml13.foundation.core.Feature that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public abstract class BaseWrapFeature extends org.omg.uml13.wrap.foundation.core.WrapModelElement implements org.omg.uml13.foundation.core.Feature {

  /**
   * Wraps an already existing object into a new wrapper instance
   */
  protected BaseWrapFeature(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }


  /**
   * Overrides the super implementation with the proper wrapped class
   * @see net.mdatools.modelant.core.wrap.Wrapper#getWrapped()
   */
  public Feature getWrapped() {
    return (Feature) super.getWrapped();
  }

  /**
   * @see net.mdatools.modelant.core.wrap.Wrapper#getDelegate()
   */
  protected Feature getDelegate() {
    return (Feature) super.getDelegate();
  }


  /**
   * @see org.omg.uml13.foundation.core.Feature#getOwner()
   */
  public org.omg.uml13.foundation.core.Classifier getOwner() {
    return getDelegate().getOwner();
  }

  /**
   * @see org.omg.uml13.foundation.core.Feature#getOwnerScope()
   */
  public org.omg.uml13.foundation.datatypes.ScopeKind getOwnerScope() {
    return getDelegate().getOwnerScope();
  }

  /**
   * @see org.omg.uml13.foundation.core.Feature#setOwner(org.omg.uml13.foundation.core.Classifier)
   */
  public void setOwner(org.omg.uml13.foundation.core.Classifier arg0) {
    getDelegate().setOwner(arg0);
  }

  /**
   * @see org.omg.uml13.foundation.core.Feature#setOwnerScope(org.omg.uml13.foundation.datatypes.ScopeKind)
   */
  public void setOwnerScope(org.omg.uml13.foundation.datatypes.ScopeKind arg0) {
    getDelegate().setOwnerScope(arg0);
  }
}