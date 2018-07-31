/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml13.wrap.base.foundation.extensionmechanisms;

import javax.jmi.reflect.RefClass;
import javax.jmi.reflect.RefPackage;
import org.omg.uml13.foundation.extensionmechanisms.Stereotype;
import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.core.wrap.Factories;

/**
 * This is a wrapper of org.omg.uml13.foundation.extensionmechanisms.Stereotype that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public abstract class BaseWrapStereotype extends org.omg.uml13.wrap.foundation.core.WrapGeneralizableElement implements org.omg.uml13.foundation.extensionmechanisms.Stereotype {

  /**
   * Wraps an already existing object into a new wrapper instance
   */
  protected BaseWrapStereotype(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  /**
   * Constructs a new wrapped object
   */
  public BaseWrapStereotype(RefPackage extent) {
    this( getClassProxy( extent ).refCreateInstance( null ),
          Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ));
    Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ).bind( this );
  }

  /**
   * This method retrieves the factory for org.omg.uml13.foundation.extensionmechanisms.Stereotype (also known as "class proxy")
   * model class.
   */
  public static RefClass getClassProxy(RefPackage extent) {
    return ((org.omg.uml13.Uml13Package) extent).getFoundation().getExtensionMechanisms().getStereotype();
  }


  /**
   * Overrides the super implementation with the proper wrapped class
   * @see net.mdatools.modelant.core.wrap.Wrapper#getWrapped()
   */
  public Stereotype getWrapped() {
    return (Stereotype) super.getWrapped();
  }

  /**
   * @see net.mdatools.modelant.core.wrap.Wrapper#getDelegate()
   */
  protected Stereotype getDelegate() {
    return (Stereotype) super.getDelegate();
  }


  /**
   * @see org.omg.uml13.foundation.extensionmechanisms.Stereotype#getBaseClass()
   */
  public java.lang.String getBaseClass() {
    return getDelegate().getBaseClass();
  }

  /**
   * @see org.omg.uml13.foundation.extensionmechanisms.Stereotype#getIcon()
   */
  public java.lang.String getIcon() {
    return getDelegate().getIcon();
  }

  /**
   * @see org.omg.uml13.foundation.extensionmechanisms.Stereotype#getExtendedElement()
   */
  public java.util.Collection getExtendedElement() {
    return getDelegate().getExtendedElement();
  }

  /**
   * @see org.omg.uml13.foundation.extensionmechanisms.Stereotype#getRequiredTag()
   */
  public java.util.Collection getRequiredTag() {
    return getDelegate().getRequiredTag();
  }

  /**
   * @see org.omg.uml13.foundation.extensionmechanisms.Stereotype#getStereotypeConstraint()
   */
  public java.util.Collection getStereotypeConstraint() {
    return getDelegate().getStereotypeConstraint();
  }

  /**
   * @see org.omg.uml13.foundation.extensionmechanisms.Stereotype#setBaseClass(java.lang.String)
   */
  public void setBaseClass(java.lang.String arg0) {
    getDelegate().setBaseClass(arg0);
  }

  /**
   * @see org.omg.uml13.foundation.extensionmechanisms.Stereotype#setIcon(java.lang.String)
   */
  public void setIcon(java.lang.String arg0) {
    getDelegate().setIcon(arg0);
  }
}