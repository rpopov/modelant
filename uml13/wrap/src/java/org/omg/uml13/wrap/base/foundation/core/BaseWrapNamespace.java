/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml13.wrap.base.foundation.core;

import org.omg.uml13.foundation.core.Namespace;
import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.core.wrap.Factories;

/**
 * This is a wrapper of org.omg.uml13.foundation.core.Namespace that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public abstract class BaseWrapNamespace extends org.omg.uml13.wrap.foundation.core.WrapModelElement implements org.omg.uml13.foundation.core.Namespace {

  /**
   * Wraps an already existing object into a new wrapper instance
   */
  protected BaseWrapNamespace(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }


  /**
   * Overrides the super implementation with the proper wrapped class
   * @see net.mdatools.modelant.core.wrap.Wrapper#getWrapped()
   */
  public Namespace getWrapped() {
    return (Namespace) super.getWrapped();
  }

  /**
   * @see net.mdatools.modelant.core.wrap.Wrapper#getDelegate()
   */
  protected Namespace getDelegate() {
    return (Namespace) super.getDelegate();
  }


  /**
   * @see org.omg.uml13.foundation.core.Namespace#getOwnedElement()
   */
  public java.util.List getOwnedElement() {
    return getDelegate().getOwnedElement();
  }
}