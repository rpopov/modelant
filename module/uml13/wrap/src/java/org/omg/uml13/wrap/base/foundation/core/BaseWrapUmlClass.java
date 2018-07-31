/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml13.wrap.base.foundation.core;

import javax.jmi.reflect.RefClass;
import javax.jmi.reflect.RefPackage;
import org.omg.uml13.foundation.core.UmlClass;
import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.core.wrap.Factories;

/**
 * This is a wrapper of org.omg.uml13.foundation.core.UmlClass that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public abstract class BaseWrapUmlClass extends org.omg.uml13.wrap.foundation.core.WrapClassifier implements org.omg.uml13.foundation.core.UmlClass {

  /**
   * Wraps an already existing object into a new wrapper instance
   */
  protected BaseWrapUmlClass(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  /**
   * Constructs a new wrapped object
   */
  public BaseWrapUmlClass(RefPackage extent) {
    this( getClassProxy( extent ).refCreateInstance( null ),
          Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ));
    Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ).bind( this );
  }

  /**
   * This method retrieves the factory for org.omg.uml13.foundation.core.UmlClass (also known as "class proxy")
   * model class.
   */
  public static RefClass getClassProxy(RefPackage extent) {
    return ((org.omg.uml13.Uml13Package) extent).getFoundation().getCore().getUmlClass();
  }


  /**
   * Overrides the super implementation with the proper wrapped class
   * @see net.mdatools.modelant.core.wrap.Wrapper#getWrapped()
   */
  public UmlClass getWrapped() {
    return (UmlClass) super.getWrapped();
  }

  /**
   * @see net.mdatools.modelant.core.wrap.Wrapper#getDelegate()
   */
  protected UmlClass getDelegate() {
    return (UmlClass) super.getDelegate();
  }


  /**
   * @see org.omg.uml13.foundation.core.UmlClass#isActive()
   */
  public boolean isActive() {
    return getDelegate().isActive();
  }

  /**
   * @see org.omg.uml13.foundation.core.UmlClass#setActive(boolean)
   */
  public void setActive(boolean arg0) {
    getDelegate().setActive(arg0);
  }
}