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
package org.omg.uml13.wrap.base.behavioralelements.collaborations;

import javax.jmi.reflect.RefClass;
import javax.jmi.reflect.RefPackage;
import org.omg.uml13.behavioralelements.collaborations.AssociationEndRole;
import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.core.wrap.Factories;

/**
 * This is a wrapper of org.omg.uml13.behavioralelements.collaborations.AssociationEndRole that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public abstract class BaseWrapAssociationEndRole extends org.omg.uml13.wrap.foundation.core.WrapAssociationEnd implements org.omg.uml13.behavioralelements.collaborations.AssociationEndRole {

  /**
   * Wraps an already existing object into a new wrapper instance
   */
  protected BaseWrapAssociationEndRole(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  /**
   * Constructs a new wrapped object
   */
  public BaseWrapAssociationEndRole(RefPackage extent) {
    this( getClassProxy( extent ).refCreateInstance( null ),
          Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ));
    Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ).bind( this );
  }

  /**
   * This method retrieves the factory for org.omg.uml13.behavioralelements.collaborations.AssociationEndRole (also known as "class proxy")
   * model class.
   */
  public static RefClass getClassProxy(RefPackage extent) {
    return ((org.omg.uml13.Uml13Package) extent).getBehavioralElements().getCollaborations().getAssociationEndRole();
  }


  /**
   * Overrides the super implementation with the proper wrapped class
   * @see net.mdatools.modelant.core.wrap.Wrapper#getWrapped()
   */
  public AssociationEndRole getWrapped() {
    return (AssociationEndRole) super.getWrapped();
  }

  /**
   * @see net.mdatools.modelant.core.wrap.Wrapper#getDelegate()
   */
  protected AssociationEndRole getDelegate() {
    return (AssociationEndRole) super.getDelegate();
  }


  /**
   * @see org.omg.uml13.behavioralelements.collaborations.AssociationEndRole#getAvailableQualifier()
   */
  public java.util.Collection getAvailableQualifier() {
    return getDelegate().getAvailableQualifier();
  }

  /**
   * @see org.omg.uml13.behavioralelements.collaborations.AssociationEndRole#getBase()
   */
  public org.omg.uml13.foundation.core.AssociationEnd getBase() {
    return getDelegate().getBase();
  }

  /**
   * @see org.omg.uml13.behavioralelements.collaborations.AssociationEndRole#getCollaborationMultiplicity()
   */
  public org.omg.uml13.foundation.datatypes.Multiplicity getCollaborationMultiplicity() {
    return getDelegate().getCollaborationMultiplicity();
  }

  /**
   * @see org.omg.uml13.behavioralelements.collaborations.AssociationEndRole#setBase(org.omg.uml13.foundation.core.AssociationEnd)
   */
  public void setBase(org.omg.uml13.foundation.core.AssociationEnd arg0) {
    getDelegate().setBase(arg0);
  }

  /**
   * @see org.omg.uml13.behavioralelements.collaborations.AssociationEndRole#setCollaborationMultiplicity(org.omg.uml13.foundation.datatypes.Multiplicity)
   */
  public void setCollaborationMultiplicity(org.omg.uml13.foundation.datatypes.Multiplicity arg0) {
    getDelegate().setCollaborationMultiplicity(arg0);
  }
}