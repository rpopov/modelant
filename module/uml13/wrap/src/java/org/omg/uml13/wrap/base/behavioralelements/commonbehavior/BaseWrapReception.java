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
import org.omg.uml13.behavioralelements.commonbehavior.Reception;
import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.core.wrap.Factories;

/**
 * This is a wrapper of org.omg.uml13.behavioralelements.commonbehavior.Reception that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public abstract class BaseWrapReception extends org.omg.uml13.wrap.foundation.core.WrapBehavioralFeature implements org.omg.uml13.behavioralelements.commonbehavior.Reception {

  /**
   * Wraps an already existing object into a new wrapper instance
   */
  protected BaseWrapReception(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  /**
   * Constructs a new wrapped object
   */
  public BaseWrapReception(RefPackage extent) {
    this( getClassProxy( extent ).refCreateInstance( null ),
          Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ));
    Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ).bind( this );
  }

  /**
   * This method retrieves the factory for org.omg.uml13.behavioralelements.commonbehavior.Reception (also known as "class proxy")
   * model class.
   */
  public static RefClass getClassProxy(RefPackage extent) {
    return ((org.omg.uml13.Uml13Package) extent).getBehavioralElements().getCommonBehavior().getReception();
  }


  /**
   * Overrides the super implementation with the proper wrapped class
   * @see net.mdatools.modelant.core.wrap.Wrapper#getWrapped()
   */
  public Reception getWrapped() {
    return (Reception) super.getWrapped();
  }

  /**
   * @see net.mdatools.modelant.core.wrap.Wrapper#getDelegate()
   */
  protected Reception getDelegate() {
    return (Reception) super.getDelegate();
  }


  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.Reception#isAbstract()
   */
  public boolean isAbstract() {
    return getDelegate().isAbstract();
  }

  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.Reception#isLeaf()
   */
  public boolean isLeaf() {
    return getDelegate().isLeaf();
  }

  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.Reception#isRoot()
   */
  public boolean isRoot() {
    return getDelegate().isRoot();
  }

  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.Reception#getSpecification()
   */
  public java.lang.String getSpecification() {
    return getDelegate().getSpecification();
  }

  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.Reception#getSignal()
   */
  public org.omg.uml13.behavioralelements.commonbehavior.Signal getSignal() {
    return getDelegate().getSignal();
  }

  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.Reception#setAbstract(boolean)
   */
  public void setAbstract(boolean arg0) {
    getDelegate().setAbstract(arg0);
  }

  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.Reception#setLeaf(boolean)
   */
  public void setLeaf(boolean arg0) {
    getDelegate().setLeaf(arg0);
  }

  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.Reception#setRoot(boolean)
   */
  public void setRoot(boolean arg0) {
    getDelegate().setRoot(arg0);
  }

  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.Reception#setSignal(org.omg.uml13.behavioralelements.commonbehavior.Signal)
   */
  public void setSignal(org.omg.uml13.behavioralelements.commonbehavior.Signal arg0) {
    getDelegate().setSignal(arg0);
  }

  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.Reception#setSpecification(java.lang.String)
   */
  public void setSpecification(java.lang.String arg0) {
    getDelegate().setSpecification(arg0);
  }
}