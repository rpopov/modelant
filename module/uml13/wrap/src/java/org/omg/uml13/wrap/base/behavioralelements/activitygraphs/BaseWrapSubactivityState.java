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
package org.omg.uml13.wrap.base.behavioralelements.activitygraphs;

import javax.jmi.reflect.RefClass;
import javax.jmi.reflect.RefPackage;
import org.omg.uml13.behavioralelements.activitygraphs.SubactivityState;
import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.core.wrap.Factories;

/**
 * This is a wrapper of org.omg.uml13.behavioralelements.activitygraphs.SubactivityState that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public abstract class BaseWrapSubactivityState extends org.omg.uml13.wrap.behavioralelements.statemachines.WrapSubmachineState implements org.omg.uml13.behavioralelements.activitygraphs.SubactivityState {

  /**
   * Wraps an already existing object into a new wrapper instance
   */
  protected BaseWrapSubactivityState(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  /**
   * Constructs a new wrapped object
   */
  public BaseWrapSubactivityState(RefPackage extent) {
    this( getClassProxy( extent ).refCreateInstance( null ),
          Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ));
    Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ).bind( this );
  }

  /**
   * This method retrieves the factory for org.omg.uml13.behavioralelements.activitygraphs.SubactivityState (also known as "class proxy")
   * model class.
   */
  public static RefClass getClassProxy(RefPackage extent) {
    return ((org.omg.uml13.Uml13Package) extent).getBehavioralElements().getActivityGraphs().getSubactivityState();
  }


  /**
   * Overrides the super implementation with the proper wrapped class
   * @see net.mdatools.modelant.core.wrap.Wrapper#getWrapped()
   */
  public SubactivityState getWrapped() {
    return (SubactivityState) super.getWrapped();
  }

  /**
   * @see net.mdatools.modelant.core.wrap.Wrapper#getDelegate()
   */
  protected SubactivityState getDelegate() {
    return (SubactivityState) super.getDelegate();
  }


  /**
   * @see org.omg.uml13.behavioralelements.activitygraphs.SubactivityState#isDynamic()
   */
  public boolean isDynamic() {
    return getDelegate().isDynamic();
  }

  /**
   * @see org.omg.uml13.behavioralelements.activitygraphs.SubactivityState#getDynamicArguments()
   */
  public org.omg.uml13.foundation.datatypes.ArgListsExpression getDynamicArguments() {
    return getDelegate().getDynamicArguments();
  }

  /**
   * @see org.omg.uml13.behavioralelements.activitygraphs.SubactivityState#getDynamicMultiplicity()
   */
  public org.omg.uml13.foundation.datatypes.Multiplicity getDynamicMultiplicity() {
    return getDelegate().getDynamicMultiplicity();
  }

  /**
   * @see org.omg.uml13.behavioralelements.activitygraphs.SubactivityState#setDynamic(boolean)
   */
  public void setDynamic(boolean arg0) {
    getDelegate().setDynamic(arg0);
  }

  /**
   * @see org.omg.uml13.behavioralelements.activitygraphs.SubactivityState#setDynamicArguments(org.omg.uml13.foundation.datatypes.ArgListsExpression)
   */
  public void setDynamicArguments(org.omg.uml13.foundation.datatypes.ArgListsExpression arg0) {
    getDelegate().setDynamicArguments(arg0);
  }

  /**
   * @see org.omg.uml13.behavioralelements.activitygraphs.SubactivityState#setDynamicMultiplicity(org.omg.uml13.foundation.datatypes.Multiplicity)
   */
  public void setDynamicMultiplicity(org.omg.uml13.foundation.datatypes.Multiplicity arg0) {
    getDelegate().setDynamicMultiplicity(arg0);
  }
}