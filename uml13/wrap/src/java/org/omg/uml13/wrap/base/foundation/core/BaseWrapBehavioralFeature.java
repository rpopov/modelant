/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml13.wrap.base.foundation.core;

import org.omg.uml13.foundation.core.BehavioralFeature;
import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.core.wrap.Factories;

/**
 * This is a wrapper of org.omg.uml13.foundation.core.BehavioralFeature that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public abstract class BaseWrapBehavioralFeature extends org.omg.uml13.wrap.foundation.core.WrapFeature implements org.omg.uml13.foundation.core.BehavioralFeature {

  /**
   * Wraps an already existing object into a new wrapper instance
   */
  protected BaseWrapBehavioralFeature(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }


  /**
   * Overrides the super implementation with the proper wrapped class
   * @see net.mdatools.modelant.core.wrap.Wrapper#getWrapped()
   */
  public BehavioralFeature getWrapped() {
    return (BehavioralFeature) super.getWrapped();
  }

  /**
   * @see net.mdatools.modelant.core.wrap.Wrapper#getDelegate()
   */
  protected BehavioralFeature getDelegate() {
    return (BehavioralFeature) super.getDelegate();
  }


  /**
   * @see org.omg.uml13.foundation.core.BehavioralFeature#isQuery()
   */
  public boolean isQuery() {
    return getDelegate().isQuery();
  }

  /**
   * @see org.omg.uml13.foundation.core.BehavioralFeature#getParameter()
   */
  public java.util.List getParameter() {
    return getDelegate().getParameter();
  }

  /**
   * @see org.omg.uml13.foundation.core.BehavioralFeature#setQuery(boolean)
   */
  public void setQuery(boolean arg0) {
    getDelegate().setQuery(arg0);
  }
}