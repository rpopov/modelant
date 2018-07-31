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
import org.omg.uml13.foundation.core.Generalization;
import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.core.wrap.Factories;

/**
 * This is a wrapper of org.omg.uml13.foundation.core.Generalization that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public abstract class BaseWrapGeneralization extends org.omg.uml13.wrap.foundation.core.WrapRelationship implements org.omg.uml13.foundation.core.Generalization {

  /**
   * Wraps an already existing object into a new wrapper instance
   */
  protected BaseWrapGeneralization(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  /**
   * Constructs a new wrapped object
   */
  public BaseWrapGeneralization(RefPackage extent) {
    this( getClassProxy( extent ).refCreateInstance( null ),
          Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ));
    Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ).bind( this );
  }

  /**
   * This method retrieves the factory for org.omg.uml13.foundation.core.Generalization (also known as "class proxy")
   * model class.
   */
  public static RefClass getClassProxy(RefPackage extent) {
    return ((org.omg.uml13.Uml13Package) extent).getFoundation().getCore().getGeneralization();
  }


  /**
   * Overrides the super implementation with the proper wrapped class
   * @see net.mdatools.modelant.core.wrap.Wrapper#getWrapped()
   */
  public Generalization getWrapped() {
    return (Generalization) super.getWrapped();
  }

  /**
   * @see net.mdatools.modelant.core.wrap.Wrapper#getDelegate()
   */
  protected Generalization getDelegate() {
    return (Generalization) super.getDelegate();
  }


  /**
   * @see org.omg.uml13.foundation.core.Generalization#getDiscriminator()
   */
  public java.lang.String getDiscriminator() {
    return getDelegate().getDiscriminator();
  }

  /**
   * @see org.omg.uml13.foundation.core.Generalization#getPowertype()
   */
  public org.omg.uml13.foundation.core.Classifier getPowertype() {
    return getDelegate().getPowertype();
  }

  /**
   * @see org.omg.uml13.foundation.core.Generalization#getChild()
   */
  public org.omg.uml13.foundation.core.GeneralizableElement getChild() {
    return getDelegate().getChild();
  }

  /**
   * @see org.omg.uml13.foundation.core.Generalization#getParent()
   */
  public org.omg.uml13.foundation.core.GeneralizableElement getParent() {
    return getDelegate().getParent();
  }

  /**
   * @see org.omg.uml13.foundation.core.Generalization#setChild(org.omg.uml13.foundation.core.GeneralizableElement)
   */
  public void setChild(org.omg.uml13.foundation.core.GeneralizableElement arg0) {
    getDelegate().setChild(arg0);
  }

  /**
   * @see org.omg.uml13.foundation.core.Generalization#setDiscriminator(java.lang.String)
   */
  public void setDiscriminator(java.lang.String arg0) {
    getDelegate().setDiscriminator(arg0);
  }

  /**
   * @see org.omg.uml13.foundation.core.Generalization#setParent(org.omg.uml13.foundation.core.GeneralizableElement)
   */
  public void setParent(org.omg.uml13.foundation.core.GeneralizableElement arg0) {
    getDelegate().setParent(arg0);
  }

  /**
   * @see org.omg.uml13.foundation.core.Generalization#setPowertype(org.omg.uml13.foundation.core.Classifier)
   */
  public void setPowertype(org.omg.uml13.foundation.core.Classifier arg0) {
    getDelegate().setPowertype(arg0);
  }
}