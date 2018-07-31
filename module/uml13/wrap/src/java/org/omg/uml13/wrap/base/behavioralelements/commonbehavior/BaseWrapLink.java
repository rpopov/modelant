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
import org.omg.uml13.behavioralelements.commonbehavior.Link;
import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.core.wrap.Factories;

/**
 * This is a wrapper of org.omg.uml13.behavioralelements.commonbehavior.Link that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public abstract class BaseWrapLink extends org.omg.uml13.wrap.foundation.core.WrapModelElement implements org.omg.uml13.behavioralelements.commonbehavior.Link {

  /**
   * Wraps an already existing object into a new wrapper instance
   */
  protected BaseWrapLink(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  /**
   * Constructs a new wrapped object
   */
  public BaseWrapLink(RefPackage extent) {
    this( getClassProxy( extent ).refCreateInstance( null ),
          Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ));
    Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ).bind( this );
  }

  /**
   * This method retrieves the factory for org.omg.uml13.behavioralelements.commonbehavior.Link (also known as "class proxy")
   * model class.
   */
  public static RefClass getClassProxy(RefPackage extent) {
    return ((org.omg.uml13.Uml13Package) extent).getBehavioralElements().getCommonBehavior().getLink();
  }


  /**
   * Overrides the super implementation with the proper wrapped class
   * @see net.mdatools.modelant.core.wrap.Wrapper#getWrapped()
   */
  public Link getWrapped() {
    return (Link) super.getWrapped();
  }

  /**
   * @see net.mdatools.modelant.core.wrap.Wrapper#getDelegate()
   */
  protected Link getDelegate() {
    return (Link) super.getDelegate();
  }


  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.Link#getConnection()
   */
  public java.util.Collection getConnection() {
    return getDelegate().getConnection();
  }

  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.Link#getStimulus()
   */
  public java.util.Collection getStimulus() {
    return getDelegate().getStimulus();
  }

  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.Link#getAssociation()
   */
  public org.omg.uml13.foundation.core.UmlAssociation getAssociation() {
    return getDelegate().getAssociation();
  }

  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.Link#setAssociation(org.omg.uml13.foundation.core.UmlAssociation)
   */
  public void setAssociation(org.omg.uml13.foundation.core.UmlAssociation arg0) {
    getDelegate().setAssociation(arg0);
  }
}