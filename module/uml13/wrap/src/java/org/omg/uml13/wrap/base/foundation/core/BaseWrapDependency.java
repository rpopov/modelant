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
import org.omg.uml13.foundation.core.Dependency;
import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.core.wrap.Factories;

/**
 * This is a wrapper of org.omg.uml13.foundation.core.Dependency that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public abstract class BaseWrapDependency extends org.omg.uml13.wrap.foundation.core.WrapRelationship implements org.omg.uml13.foundation.core.Dependency {

  /**
   * Wraps an already existing object into a new wrapper instance
   */
  protected BaseWrapDependency(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  /**
   * Constructs a new wrapped object
   */
  public BaseWrapDependency(RefPackage extent) {
    this( getClassProxy( extent ).refCreateInstance( null ),
          Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ));
    Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ).bind( this );
  }

  /**
   * This method retrieves the factory for org.omg.uml13.foundation.core.Dependency (also known as "class proxy")
   * model class.
   */
  public static RefClass getClassProxy(RefPackage extent) {
    return ((org.omg.uml13.Uml13Package) extent).getFoundation().getCore().getDependency();
  }


  /**
   * Overrides the super implementation with the proper wrapped class
   * @see net.mdatools.modelant.core.wrap.Wrapper#getWrapped()
   */
  public Dependency getWrapped() {
    return (Dependency) super.getWrapped();
  }

  /**
   * @see net.mdatools.modelant.core.wrap.Wrapper#getDelegate()
   */
  protected Dependency getDelegate() {
    return (Dependency) super.getDelegate();
  }


  /**
   * @see org.omg.uml13.foundation.core.Dependency#getClient()
   */
  public java.util.Collection getClient() {
    return getDelegate().getClient();
  }

  /**
   * @see org.omg.uml13.foundation.core.Dependency#getSupplier()
   */
  public java.util.Collection getSupplier() {
    return getDelegate().getSupplier();
  }
}