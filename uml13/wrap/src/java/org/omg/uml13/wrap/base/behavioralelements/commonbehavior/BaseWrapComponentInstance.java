/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml13.wrap.base.behavioralelements.commonbehavior;

import javax.jmi.reflect.RefClass;
import javax.jmi.reflect.RefPackage;
import org.omg.uml13.behavioralelements.commonbehavior.ComponentInstance;
import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.core.wrap.Factories;

/**
 * This is a wrapper of org.omg.uml13.behavioralelements.commonbehavior.ComponentInstance that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public abstract class BaseWrapComponentInstance extends org.omg.uml13.wrap.behavioralelements.commonbehavior.WrapInstance implements org.omg.uml13.behavioralelements.commonbehavior.ComponentInstance {

  /**
   * Wraps an already existing object into a new wrapper instance
   */
  protected BaseWrapComponentInstance(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  /**
   * Constructs a new wrapped object
   */
  public BaseWrapComponentInstance(RefPackage extent) {
    this( getClassProxy( extent ).refCreateInstance( null ),
          Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ));
    Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ).bind( this );
  }

  /**
   * This method retrieves the factory for org.omg.uml13.behavioralelements.commonbehavior.ComponentInstance (also known as "class proxy")
   * model class.
   */
  public static RefClass getClassProxy(RefPackage extent) {
    return ((org.omg.uml13.Uml13Package) extent).getBehavioralElements().getCommonBehavior().getComponentInstance();
  }


  /**
   * Overrides the super implementation with the proper wrapped class
   * @see net.mdatools.modelant.core.wrap.Wrapper#getWrapped()
   */
  public ComponentInstance getWrapped() {
    return (ComponentInstance) super.getWrapped();
  }

  /**
   * @see net.mdatools.modelant.core.wrap.Wrapper#getDelegate()
   */
  protected ComponentInstance getDelegate() {
    return (ComponentInstance) super.getDelegate();
  }


  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.ComponentInstance#getResident()
   */
  public java.util.Collection getResident() {
    return getDelegate().getResident();
  }

  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.ComponentInstance#getNodeInstance()
   */
  public org.omg.uml13.behavioralelements.commonbehavior.NodeInstance getNodeInstance() {
    return getDelegate().getNodeInstance();
  }

  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.ComponentInstance#setNodeInstance(org.omg.uml13.behavioralelements.commonbehavior.NodeInstance)
   */
  public void setNodeInstance(org.omg.uml13.behavioralelements.commonbehavior.NodeInstance arg0) {
    getDelegate().setNodeInstance(arg0);
  }
}