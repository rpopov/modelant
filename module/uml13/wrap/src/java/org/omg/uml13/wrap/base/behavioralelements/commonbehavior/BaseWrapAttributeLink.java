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
import org.omg.uml13.behavioralelements.commonbehavior.AttributeLink;
import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.core.wrap.Factories;

/**
 * This is a wrapper of org.omg.uml13.behavioralelements.commonbehavior.AttributeLink that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public abstract class BaseWrapAttributeLink extends org.omg.uml13.wrap.foundation.core.WrapModelElement implements org.omg.uml13.behavioralelements.commonbehavior.AttributeLink {

  /**
   * Wraps an already existing object into a new wrapper instance
   */
  protected BaseWrapAttributeLink(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  /**
   * Constructs a new wrapped object
   */
  public BaseWrapAttributeLink(RefPackage extent) {
    this( getClassProxy( extent ).refCreateInstance( null ),
          Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ));
    Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ).bind( this );
  }

  /**
   * This method retrieves the factory for org.omg.uml13.behavioralelements.commonbehavior.AttributeLink (also known as "class proxy")
   * model class.
   */
  public static RefClass getClassProxy(RefPackage extent) {
    return ((org.omg.uml13.Uml13Package) extent).getBehavioralElements().getCommonBehavior().getAttributeLink();
  }


  /**
   * Overrides the super implementation with the proper wrapped class
   * @see net.mdatools.modelant.core.wrap.Wrapper#getWrapped()
   */
  public AttributeLink getWrapped() {
    return (AttributeLink) super.getWrapped();
  }

  /**
   * @see net.mdatools.modelant.core.wrap.Wrapper#getDelegate()
   */
  protected AttributeLink getDelegate() {
    return (AttributeLink) super.getDelegate();
  }


  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.AttributeLink#getInstance()
   */
  public org.omg.uml13.behavioralelements.commonbehavior.Instance getInstance() {
    return getDelegate().getInstance();
  }

  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.AttributeLink#getValue()
   */
  public org.omg.uml13.behavioralelements.commonbehavior.Instance getValue() {
    return getDelegate().getValue();
  }

  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.AttributeLink#getLinkEnd()
   */
  public org.omg.uml13.behavioralelements.commonbehavior.LinkEnd getLinkEnd() {
    return getDelegate().getLinkEnd();
  }

  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.AttributeLink#getAttribute()
   */
  public org.omg.uml13.foundation.core.Attribute getAttribute() {
    return getDelegate().getAttribute();
  }

  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.AttributeLink#setAttribute(org.omg.uml13.foundation.core.Attribute)
   */
  public void setAttribute(org.omg.uml13.foundation.core.Attribute arg0) {
    getDelegate().setAttribute(arg0);
  }

  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.AttributeLink#setInstance(org.omg.uml13.behavioralelements.commonbehavior.Instance)
   */
  public void setInstance(org.omg.uml13.behavioralelements.commonbehavior.Instance arg0) {
    getDelegate().setInstance(arg0);
  }

  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.AttributeLink#setLinkEnd(org.omg.uml13.behavioralelements.commonbehavior.LinkEnd)
   */
  public void setLinkEnd(org.omg.uml13.behavioralelements.commonbehavior.LinkEnd arg0) {
    getDelegate().setLinkEnd(arg0);
  }

  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.AttributeLink#setValue(org.omg.uml13.behavioralelements.commonbehavior.Instance)
   */
  public void setValue(org.omg.uml13.behavioralelements.commonbehavior.Instance arg0) {
    getDelegate().setValue(arg0);
  }
}