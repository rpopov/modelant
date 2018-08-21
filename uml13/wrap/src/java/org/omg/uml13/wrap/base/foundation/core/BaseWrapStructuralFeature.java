/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml13.wrap.base.foundation.core;

import org.omg.uml13.foundation.core.StructuralFeature;
import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.core.wrap.Factories;

/**
 * This is a wrapper of org.omg.uml13.foundation.core.StructuralFeature that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public abstract class BaseWrapStructuralFeature extends org.omg.uml13.wrap.foundation.core.WrapFeature implements org.omg.uml13.foundation.core.StructuralFeature {

  /**
   * Wraps an already existing object into a new wrapper instance
   */
  protected BaseWrapStructuralFeature(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }


  /**
   * Overrides the super implementation with the proper wrapped class
   * @see net.mdatools.modelant.core.wrap.Wrapper#getWrapped()
   */
  public StructuralFeature getWrapped() {
    return (StructuralFeature) super.getWrapped();
  }

  /**
   * @see net.mdatools.modelant.core.wrap.Wrapper#getDelegate()
   */
  protected StructuralFeature getDelegate() {
    return (StructuralFeature) super.getDelegate();
  }


  /**
   * @see org.omg.uml13.foundation.core.StructuralFeature#getType()
   */
  public org.omg.uml13.foundation.core.Classifier getType() {
    return getDelegate().getType();
  }

  /**
   * @see org.omg.uml13.foundation.core.StructuralFeature#getChangeability()
   */
  public org.omg.uml13.foundation.datatypes.ChangeableKind getChangeability() {
    return getDelegate().getChangeability();
  }

  /**
   * @see org.omg.uml13.foundation.core.StructuralFeature#getMultiplicity()
   */
  public org.omg.uml13.foundation.datatypes.Multiplicity getMultiplicity() {
    return getDelegate().getMultiplicity();
  }

  /**
   * @see org.omg.uml13.foundation.core.StructuralFeature#getTargetScope()
   */
  public org.omg.uml13.foundation.datatypes.ScopeKind getTargetScope() {
    return getDelegate().getTargetScope();
  }

  /**
   * @see org.omg.uml13.foundation.core.StructuralFeature#setChangeability(org.omg.uml13.foundation.datatypes.ChangeableKind)
   */
  public void setChangeability(org.omg.uml13.foundation.datatypes.ChangeableKind arg0) {
    getDelegate().setChangeability(arg0);
  }

  /**
   * @see org.omg.uml13.foundation.core.StructuralFeature#setMultiplicity(org.omg.uml13.foundation.datatypes.Multiplicity)
   */
  public void setMultiplicity(org.omg.uml13.foundation.datatypes.Multiplicity arg0) {
    getDelegate().setMultiplicity(arg0);
  }

  /**
   * @see org.omg.uml13.foundation.core.StructuralFeature#setTargetScope(org.omg.uml13.foundation.datatypes.ScopeKind)
   */
  public void setTargetScope(org.omg.uml13.foundation.datatypes.ScopeKind arg0) {
    getDelegate().setTargetScope(arg0);
  }

  /**
   * @see org.omg.uml13.foundation.core.StructuralFeature#setType(org.omg.uml13.foundation.core.Classifier)
   */
  public void setType(org.omg.uml13.foundation.core.Classifier arg0) {
    getDelegate().setType(arg0);
  }
}