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
package org.omg.uml13.wrap.base.behavioralelements.commonbehavior;

import javax.jmi.reflect.RefClass;
import javax.jmi.reflect.RefPackage;
import org.omg.uml13.behavioralelements.commonbehavior.LinkEnd;
import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.core.wrap.Factories;

/**
 * This is a wrapper of org.omg.uml13.behavioralelements.commonbehavior.LinkEnd that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public abstract class BaseWrapLinkEnd extends org.omg.uml13.wrap.foundation.core.WrapModelElement implements org.omg.uml13.behavioralelements.commonbehavior.LinkEnd {

  /**
   * Wraps an already existing object into a new wrapper instance
   */
  protected BaseWrapLinkEnd(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  /**
   * Constructs a new wrapped object
   */
  public BaseWrapLinkEnd(RefPackage extent) {
    this( getClassProxy( extent ).refCreateInstance( null ),
          Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ));
    Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ).bind( this );
  }

  /**
   * This method retrieves the factory for org.omg.uml13.behavioralelements.commonbehavior.LinkEnd (also known as "class proxy")
   * model class.
   */
  public static RefClass getClassProxy(RefPackage extent) {
    return ((org.omg.uml13.Uml13Package) extent).getBehavioralElements().getCommonBehavior().getLinkEnd();
  }


  /**
   * Overrides the super implementation with the proper wrapped class
   * @see net.mdatools.modelant.core.wrap.Wrapper#getWrapped()
   */
  public LinkEnd getWrapped() {
    return (LinkEnd) super.getWrapped();
  }

  /**
   * @see net.mdatools.modelant.core.wrap.Wrapper#getDelegate()
   */
  protected LinkEnd getDelegate() {
    return (LinkEnd) super.getDelegate();
  }


  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.LinkEnd#getQualifiedValue()
   */
  public java.util.Collection getQualifiedValue() {
    return getDelegate().getQualifiedValue();
  }

  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.LinkEnd#getInstance()
   */
  public org.omg.uml13.behavioralelements.commonbehavior.Instance getInstance() {
    return getDelegate().getInstance();
  }

  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.LinkEnd#getLink()
   */
  public org.omg.uml13.behavioralelements.commonbehavior.Link getLink() {
    return getDelegate().getLink();
  }

  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.LinkEnd#getAssociationEnd()
   */
  public org.omg.uml13.foundation.core.AssociationEnd getAssociationEnd() {
    return getDelegate().getAssociationEnd();
  }

  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.LinkEnd#setAssociationEnd(org.omg.uml13.foundation.core.AssociationEnd)
   */
  public void setAssociationEnd(org.omg.uml13.foundation.core.AssociationEnd arg0) {
    getDelegate().setAssociationEnd(arg0);
  }

  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.LinkEnd#setInstance(org.omg.uml13.behavioralelements.commonbehavior.Instance)
   */
  public void setInstance(org.omg.uml13.behavioralelements.commonbehavior.Instance arg0) {
    getDelegate().setInstance(arg0);
  }

  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.LinkEnd#setLink(org.omg.uml13.behavioralelements.commonbehavior.Link)
   */
  public void setLink(org.omg.uml13.behavioralelements.commonbehavior.Link arg0) {
    getDelegate().setLink(arg0);
  }
}