/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml13.wrap.base.behavioralelements.collaborations;

import javax.jmi.reflect.RefClass;
import javax.jmi.reflect.RefPackage;
import org.omg.uml13.behavioralelements.collaborations.ClassifierRole;
import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.core.wrap.Factories;

/**
 * This is a wrapper of org.omg.uml13.behavioralelements.collaborations.ClassifierRole that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public abstract class BaseWrapClassifierRole extends org.omg.uml13.wrap.foundation.core.WrapClassifier implements org.omg.uml13.behavioralelements.collaborations.ClassifierRole {

  /**
   * Wraps an already existing object into a new wrapper instance
   */
  protected BaseWrapClassifierRole(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  /**
   * Constructs a new wrapped object
   */
  public BaseWrapClassifierRole(RefPackage extent) {
    this( getClassProxy( extent ).refCreateInstance( null ),
          Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ));
    Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ).bind( this );
  }

  /**
   * This method retrieves the factory for org.omg.uml13.behavioralelements.collaborations.ClassifierRole (also known as "class proxy")
   * model class.
   */
  public static RefClass getClassProxy(RefPackage extent) {
    return ((org.omg.uml13.Uml13Package) extent).getBehavioralElements().getCollaborations().getClassifierRole();
  }


  /**
   * Overrides the super implementation with the proper wrapped class
   * @see net.mdatools.modelant.core.wrap.Wrapper#getWrapped()
   */
  public ClassifierRole getWrapped() {
    return (ClassifierRole) super.getWrapped();
  }

  /**
   * @see net.mdatools.modelant.core.wrap.Wrapper#getDelegate()
   */
  protected ClassifierRole getDelegate() {
    return (ClassifierRole) super.getDelegate();
  }


  /**
   * @see org.omg.uml13.behavioralelements.collaborations.ClassifierRole#getAvailableContents()
   */
  public java.util.Collection getAvailableContents() {
    return getDelegate().getAvailableContents();
  }

  /**
   * @see org.omg.uml13.behavioralelements.collaborations.ClassifierRole#getAvailableFeature()
   */
  public java.util.Collection getAvailableFeature() {
    return getDelegate().getAvailableFeature();
  }

  /**
   * @see org.omg.uml13.behavioralelements.collaborations.ClassifierRole#getBase()
   */
  public java.util.Collection getBase() {
    return getDelegate().getBase();
  }

  /**
   * @see org.omg.uml13.behavioralelements.collaborations.ClassifierRole#getMessage1()
   */
  public java.util.Collection getMessage1() {
    return getDelegate().getMessage1();
  }

  /**
   * @see org.omg.uml13.behavioralelements.collaborations.ClassifierRole#getMessage2()
   */
  public java.util.Collection getMessage2() {
    return getDelegate().getMessage2();
  }

  /**
   * @see org.omg.uml13.behavioralelements.collaborations.ClassifierRole#getMultiplicity()
   */
  public org.omg.uml13.foundation.datatypes.Multiplicity getMultiplicity() {
    return getDelegate().getMultiplicity();
  }

  /**
   * @see org.omg.uml13.behavioralelements.collaborations.ClassifierRole#setMultiplicity(org.omg.uml13.foundation.datatypes.Multiplicity)
   */
  public void setMultiplicity(org.omg.uml13.foundation.datatypes.Multiplicity arg0) {
    getDelegate().setMultiplicity(arg0);
  }
}