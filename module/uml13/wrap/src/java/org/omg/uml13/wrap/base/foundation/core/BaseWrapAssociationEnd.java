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
package org.omg.uml13.wrap.base.foundation.core;

import javax.jmi.reflect.RefClass;
import javax.jmi.reflect.RefPackage;
import org.omg.uml13.foundation.core.AssociationEnd;
import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.core.wrap.Factories;

/**
 * This is a wrapper of org.omg.uml13.foundation.core.AssociationEnd that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public abstract class BaseWrapAssociationEnd extends org.omg.uml13.wrap.foundation.core.WrapModelElement implements org.omg.uml13.foundation.core.AssociationEnd {

  /**
   * Wraps an already existing object into a new wrapper instance
   */
  protected BaseWrapAssociationEnd(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  /**
   * Constructs a new wrapped object
   */
  public BaseWrapAssociationEnd(RefPackage extent) {
    this( getClassProxy( extent ).refCreateInstance( null ),
          Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ));
    Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ).bind( this );
  }

  /**
   * This method retrieves the factory for org.omg.uml13.foundation.core.AssociationEnd (also known as "class proxy")
   * model class.
   */
  public static RefClass getClassProxy(RefPackage extent) {
    return ((org.omg.uml13.Uml13Package) extent).getFoundation().getCore().getAssociationEnd();
  }


  /**
   * Overrides the super implementation with the proper wrapped class
   * @see net.mdatools.modelant.core.wrap.Wrapper#getWrapped()
   */
  public AssociationEnd getWrapped() {
    return (AssociationEnd) super.getWrapped();
  }

  /**
   * @see net.mdatools.modelant.core.wrap.Wrapper#getDelegate()
   */
  protected AssociationEnd getDelegate() {
    return (AssociationEnd) super.getDelegate();
  }


  /**
   * @see org.omg.uml13.foundation.core.AssociationEnd#isNavigable()
   */
  public boolean isNavigable() {
    return getDelegate().isNavigable();
  }

  /**
   * @see org.omg.uml13.foundation.core.AssociationEnd#getSpecification()
   */
  public java.util.Collection getSpecification() {
    return getDelegate().getSpecification();
  }

  /**
   * @see org.omg.uml13.foundation.core.AssociationEnd#getQualifier()
   */
  public java.util.List getQualifier() {
    return getDelegate().getQualifier();
  }

  /**
   * @see org.omg.uml13.foundation.core.AssociationEnd#getType()
   */
  public org.omg.uml13.foundation.core.Classifier getType() {
    return getDelegate().getType();
  }

  /**
   * @see org.omg.uml13.foundation.core.AssociationEnd#getAssociation()
   */
  public org.omg.uml13.foundation.core.UmlAssociation getAssociation() {
    return getDelegate().getAssociation();
  }

  /**
   * @see org.omg.uml13.foundation.core.AssociationEnd#getAggregation()
   */
  public org.omg.uml13.foundation.datatypes.AggregationKind getAggregation() {
    return getDelegate().getAggregation();
  }

  /**
   * @see org.omg.uml13.foundation.core.AssociationEnd#getChangeability()
   */
  public org.omg.uml13.foundation.datatypes.ChangeableKind getChangeability() {
    return getDelegate().getChangeability();
  }

  /**
   * @see org.omg.uml13.foundation.core.AssociationEnd#getMultiplicity()
   */
  public org.omg.uml13.foundation.datatypes.Multiplicity getMultiplicity() {
    return getDelegate().getMultiplicity();
  }

  /**
   * @see org.omg.uml13.foundation.core.AssociationEnd#getOrdering()
   */
  public org.omg.uml13.foundation.datatypes.OrderingKind getOrdering() {
    return getDelegate().getOrdering();
  }

  /**
   * @see org.omg.uml13.foundation.core.AssociationEnd#getTargetScope()
   */
  public org.omg.uml13.foundation.datatypes.ScopeKind getTargetScope() {
    return getDelegate().getTargetScope();
  }

  /**
   * @see org.omg.uml13.foundation.core.AssociationEnd#setAggregation(org.omg.uml13.foundation.datatypes.AggregationKind)
   */
  public void setAggregation(org.omg.uml13.foundation.datatypes.AggregationKind arg0) {
    getDelegate().setAggregation(arg0);
  }

  /**
   * @see org.omg.uml13.foundation.core.AssociationEnd#setAssociation(org.omg.uml13.foundation.core.UmlAssociation)
   */
  public void setAssociation(org.omg.uml13.foundation.core.UmlAssociation arg0) {
    getDelegate().setAssociation(arg0);
  }

  /**
   * @see org.omg.uml13.foundation.core.AssociationEnd#setChangeability(org.omg.uml13.foundation.datatypes.ChangeableKind)
   */
  public void setChangeability(org.omg.uml13.foundation.datatypes.ChangeableKind arg0) {
    getDelegate().setChangeability(arg0);
  }

  /**
   * @see org.omg.uml13.foundation.core.AssociationEnd#setMultiplicity(org.omg.uml13.foundation.datatypes.Multiplicity)
   */
  public void setMultiplicity(org.omg.uml13.foundation.datatypes.Multiplicity arg0) {
    getDelegate().setMultiplicity(arg0);
  }

  /**
   * @see org.omg.uml13.foundation.core.AssociationEnd#setNavigable(boolean)
   */
  public void setNavigable(boolean arg0) {
    getDelegate().setNavigable(arg0);
  }

  /**
   * @see org.omg.uml13.foundation.core.AssociationEnd#setOrdering(org.omg.uml13.foundation.datatypes.OrderingKind)
   */
  public void setOrdering(org.omg.uml13.foundation.datatypes.OrderingKind arg0) {
    getDelegate().setOrdering(arg0);
  }

  /**
   * @see org.omg.uml13.foundation.core.AssociationEnd#setTargetScope(org.omg.uml13.foundation.datatypes.ScopeKind)
   */
  public void setTargetScope(org.omg.uml13.foundation.datatypes.ScopeKind arg0) {
    getDelegate().setTargetScope(arg0);
  }

  /**
   * @see org.omg.uml13.foundation.core.AssociationEnd#setType(org.omg.uml13.foundation.core.Classifier)
   */
  public void setType(org.omg.uml13.foundation.core.Classifier arg0) {
    getDelegate().setType(arg0);
  }
}