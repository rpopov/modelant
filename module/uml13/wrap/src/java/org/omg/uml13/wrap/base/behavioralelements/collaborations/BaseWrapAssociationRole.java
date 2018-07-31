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
import org.omg.uml13.behavioralelements.collaborations.AssociationRole;
import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.core.wrap.Factories;

/**
 * This is a wrapper of org.omg.uml13.behavioralelements.collaborations.AssociationRole that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public abstract class BaseWrapAssociationRole extends org.omg.uml13.wrap.foundation.core.WrapUmlAssociation implements org.omg.uml13.behavioralelements.collaborations.AssociationRole {

  /**
   * Wraps an already existing object into a new wrapper instance
   */
  protected BaseWrapAssociationRole(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  /**
   * Constructs a new wrapped object
   */
  public BaseWrapAssociationRole(RefPackage extent) {
    this( getClassProxy( extent ).refCreateInstance( null ),
          Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ));
    Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ).bind( this );
  }

  /**
   * This method retrieves the factory for org.omg.uml13.behavioralelements.collaborations.AssociationRole (also known as "class proxy")
   * model class.
   */
  public static RefClass getClassProxy(RefPackage extent) {
    return ((org.omg.uml13.Uml13Package) extent).getBehavioralElements().getCollaborations().getAssociationRole();
  }


  /**
   * Overrides the super implementation with the proper wrapped class
   * @see net.mdatools.modelant.core.wrap.Wrapper#getWrapped()
   */
  public AssociationRole getWrapped() {
    return (AssociationRole) super.getWrapped();
  }

  /**
   * @see net.mdatools.modelant.core.wrap.Wrapper#getDelegate()
   */
  protected AssociationRole getDelegate() {
    return (AssociationRole) super.getDelegate();
  }


  /**
   * @see org.omg.uml13.behavioralelements.collaborations.AssociationRole#getMessage()
   */
  public java.util.Collection getMessage() {
    return getDelegate().getMessage();
  }

  /**
   * @see org.omg.uml13.behavioralelements.collaborations.AssociationRole#getBase()
   */
  public org.omg.uml13.foundation.core.UmlAssociation getBase() {
    return getDelegate().getBase();
  }

  /**
   * @see org.omg.uml13.behavioralelements.collaborations.AssociationRole#getMultiplicity()
   */
  public org.omg.uml13.foundation.datatypes.Multiplicity getMultiplicity() {
    return getDelegate().getMultiplicity();
  }

  /**
   * @see org.omg.uml13.behavioralelements.collaborations.AssociationRole#setBase(org.omg.uml13.foundation.core.UmlAssociation)
   */
  public void setBase(org.omg.uml13.foundation.core.UmlAssociation arg0) {
    getDelegate().setBase(arg0);
  }

  /**
   * @see org.omg.uml13.behavioralelements.collaborations.AssociationRole#setMultiplicity(org.omg.uml13.foundation.datatypes.Multiplicity)
   */
  public void setMultiplicity(org.omg.uml13.foundation.datatypes.Multiplicity arg0) {
    getDelegate().setMultiplicity(arg0);
  }
}