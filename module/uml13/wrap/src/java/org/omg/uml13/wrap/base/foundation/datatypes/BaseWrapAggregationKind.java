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
package org.omg.uml13.wrap.base.foundation.datatypes;

import javax.jmi.reflect.RefClass;
import javax.jmi.reflect.RefPackage;
import org.omg.uml13.foundation.datatypes.AggregationKind;
import org.omg.uml13.foundation.datatypes.AggregationKindEnum;
import net.mdatools.modelant.core.wrap.Factory;

/**
 * This is a wrapper of org.omg.uml13.foundation.datatypes.AggregationKind that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public abstract class BaseWrapAggregationKind extends net.mdatools.modelant.core.wrap.Wrapper {

  protected BaseWrapAggregationKind(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  protected BaseWrapAggregationKind(String value, Factory factory) {
    this( org.omg.uml13.foundation.datatypes.AggregationKindEnum.forName( value ), factory );
  }

  /**
   * Overrides the super implementation with the proper wrapped class
   * @see net.mdatools.modelant.core.wrap.Wrapper#getWrapped()
   */
  public AggregationKind getWrapped() {
    return (AggregationKind) super.getWrapped();
  }

  /**
   * @see net.mdatools.modelant.core.wrap.Wrapper#getDelegate()
   */
  protected AggregationKind getDelegate() {
    return (AggregationKind) super.getDelegate();
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