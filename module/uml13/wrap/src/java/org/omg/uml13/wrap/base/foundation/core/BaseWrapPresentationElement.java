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

import org.omg.uml13.foundation.core.PresentationElement;
import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.core.wrap.Factories;

/**
 * This is a wrapper of org.omg.uml13.foundation.core.PresentationElement that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public abstract class BaseWrapPresentationElement extends org.omg.uml13.wrap.foundation.core.WrapElement implements org.omg.uml13.foundation.core.PresentationElement {

  /**
   * Wraps an already existing object into a new wrapper instance
   */
  protected BaseWrapPresentationElement(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }


  /**
   * Overrides the super implementation with the proper wrapped class
   * @see net.mdatools.modelant.core.wrap.Wrapper#getWrapped()
   */
  public PresentationElement getWrapped() {
    return (PresentationElement) super.getWrapped();
  }

  /**
   * @see net.mdatools.modelant.core.wrap.Wrapper#getDelegate()
   */
  protected PresentationElement getDelegate() {
    return (PresentationElement) super.getDelegate();
  }


  /**
   * @see org.omg.uml13.foundation.core.PresentationElement#getSubject()
   */
  public java.util.Collection getSubject() {
    return getDelegate().getSubject();
  }
}