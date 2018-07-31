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
import org.omg.uml13.behavioralelements.commonbehavior.Instance;
import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.core.wrap.Factories;

/**
 * This is a wrapper of org.omg.uml13.behavioralelements.commonbehavior.Instance that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public abstract class BaseWrapInstance extends org.omg.uml13.wrap.foundation.core.WrapModelElement implements org.omg.uml13.behavioralelements.commonbehavior.Instance {

  /**
   * Wraps an already existing object into a new wrapper instance
   */
  protected BaseWrapInstance(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  /**
   * Constructs a new wrapped object
   */
  public BaseWrapInstance(RefPackage extent) {
    this( getClassProxy( extent ).refCreateInstance( null ),
          Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ));
    Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ).bind( this );
  }

  /**
   * This method retrieves the factory for org.omg.uml13.behavioralelements.commonbehavior.Instance (also known as "class proxy")
   * model class.
   */
  public static RefClass getClassProxy(RefPackage extent) {
    return ((org.omg.uml13.Uml13Package) extent).getBehavioralElements().getCommonBehavior().getInstance();
  }


  /**
   * Overrides the super implementation with the proper wrapped class
   * @see net.mdatools.modelant.core.wrap.Wrapper#getWrapped()
   */
  public Instance getWrapped() {
    return (Instance) super.getWrapped();
  }

  /**
   * @see net.mdatools.modelant.core.wrap.Wrapper#getDelegate()
   */
  protected Instance getDelegate() {
    return (Instance) super.getDelegate();
  }


  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.Instance#getAttributeLink()
   */
  public java.util.Collection getAttributeLink() {
    return getDelegate().getAttributeLink();
  }

  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.Instance#getClassifier()
   */
  public java.util.Collection getClassifier() {
    return getDelegate().getClassifier();
  }

  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.Instance#getLinkEnd()
   */
  public java.util.Collection getLinkEnd() {
    return getDelegate().getLinkEnd();
  }

  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.Instance#getSlot()
   */
  public java.util.Collection getSlot() {
    return getDelegate().getSlot();
  }

  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.Instance#getStimulus1()
   */
  public java.util.Collection getStimulus1() {
    return getDelegate().getStimulus1();
  }

  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.Instance#getStimulus2()
   */
  public java.util.Collection getStimulus2() {
    return getDelegate().getStimulus2();
  }

  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.Instance#getStimulus3()
   */
  public java.util.Collection getStimulus3() {
    return getDelegate().getStimulus3();
  }

  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.Instance#getComponentInstance()
   */
  public org.omg.uml13.behavioralelements.commonbehavior.ComponentInstance getComponentInstance() {
    return getDelegate().getComponentInstance();
  }

  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.Instance#setComponentInstance(org.omg.uml13.behavioralelements.commonbehavior.ComponentInstance)
   */
  public void setComponentInstance(org.omg.uml13.behavioralelements.commonbehavior.ComponentInstance arg0) {
    getDelegate().setComponentInstance(arg0);
  }
}