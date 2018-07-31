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

import javax.jmi.reflect.RefClass;
import javax.jmi.reflect.RefPackage;
import org.omg.uml13.foundation.core.Attribute;
import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.core.wrap.Factories;

/**
 * This is a wrapper of org.omg.uml13.foundation.core.Attribute that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public abstract class BaseWrapAttribute extends org.omg.uml13.wrap.foundation.core.WrapStructuralFeature implements org.omg.uml13.foundation.core.Attribute {

  /**
   * Wraps an already existing object into a new wrapper instance
   */
  protected BaseWrapAttribute(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  /**
   * Constructs a new wrapped object
   */
  public BaseWrapAttribute(RefPackage extent) {
    this( getClassProxy( extent ).refCreateInstance( null ),
          Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ));
    Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ).bind( this );
  }

  /**
   * This method retrieves the factory for org.omg.uml13.foundation.core.Attribute (also known as "class proxy")
   * model class.
   */
  public static RefClass getClassProxy(RefPackage extent) {
    return ((org.omg.uml13.Uml13Package) extent).getFoundation().getCore().getAttribute();
  }


  /**
   * Overrides the super implementation with the proper wrapped class
   * @see net.mdatools.modelant.core.wrap.Wrapper#getWrapped()
   */
  public Attribute getWrapped() {
    return (Attribute) super.getWrapped();
  }

  /**
   * @see net.mdatools.modelant.core.wrap.Wrapper#getDelegate()
   */
  protected Attribute getDelegate() {
    return (Attribute) super.getDelegate();
  }


  /**
   * @see org.omg.uml13.foundation.core.Attribute#getAssociationEnd()
   */
  public org.omg.uml13.foundation.core.AssociationEnd getAssociationEnd() {
    return getDelegate().getAssociationEnd();
  }

  /**
   * @see org.omg.uml13.foundation.core.Attribute#getInitialValue()
   */
  public org.omg.uml13.foundation.datatypes.Expression getInitialValue() {
    return getDelegate().getInitialValue();
  }

  /**
   * @see org.omg.uml13.foundation.core.Attribute#setAssociationEnd(org.omg.uml13.foundation.core.AssociationEnd)
   */
  public void setAssociationEnd(org.omg.uml13.foundation.core.AssociationEnd arg0) {
    getDelegate().setAssociationEnd(arg0);
  }

  /**
   * @see org.omg.uml13.foundation.core.Attribute#setInitialValue(org.omg.uml13.foundation.datatypes.Expression)
   */
  public void setInitialValue(org.omg.uml13.foundation.datatypes.Expression arg0) {
    getDelegate().setInitialValue(arg0);
  }
}