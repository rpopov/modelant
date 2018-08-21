/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml13.wrap.base.foundation.datatypes;

import javax.jmi.reflect.RefClass;
import javax.jmi.reflect.RefPackage;
import org.omg.uml13.foundation.datatypes.VisibilityKind;
import org.omg.uml13.foundation.datatypes.VisibilityKindEnum;
import net.mdatools.modelant.core.wrap.Factory;

/**
 * This is a wrapper of org.omg.uml13.foundation.datatypes.VisibilityKind that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public abstract class BaseWrapVisibilityKind extends net.mdatools.modelant.core.wrap.Wrapper {

  protected BaseWrapVisibilityKind(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  protected BaseWrapVisibilityKind(String value, Factory factory) {
    this( org.omg.uml13.foundation.datatypes.VisibilityKindEnum.forName( value ), factory );
  }

  /**
   * Overrides the super implementation with the proper wrapped class
   * @see net.mdatools.modelant.core.wrap.Wrapper#getWrapped()
   */
  public VisibilityKind getWrapped() {
    return (VisibilityKind) super.getWrapped();
  }

  /**
   * @see net.mdatools.modelant.core.wrap.Wrapper#getDelegate()
   */
  protected VisibilityKind getDelegate() {
    return (VisibilityKind) super.getDelegate();
  }


  /**
   * @see javax.jmi.reflect.RefEnum#equals(java.lang.Object)
   */
  public boolean equals(java.lang.Object arg0) {
    return getDelegate().equals(arg0);
  }

  /**
   * @see javax.jmi.reflect.RefEnum#hashCode()
   */
  public int hashCode() {
    return getDelegate().hashCode();
  }

  /**
   * @see javax.jmi.reflect.RefEnum#toString()
   */
  public java.lang.String toString() {
    return getDelegate().toString();
  }

  /**
   * @see javax.jmi.reflect.RefEnum#refTypeName()
   */
  public java.util.List refTypeName() {
    return getDelegate().refTypeName();
  }

}