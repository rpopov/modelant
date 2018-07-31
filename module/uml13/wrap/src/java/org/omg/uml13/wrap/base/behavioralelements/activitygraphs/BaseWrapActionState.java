/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml13.wrap.base.behavioralelements.activitygraphs;

import javax.jmi.reflect.RefClass;
import javax.jmi.reflect.RefPackage;
import org.omg.uml13.behavioralelements.activitygraphs.ActionState;
import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.core.wrap.Factories;

/**
 * This is a wrapper of org.omg.uml13.behavioralelements.activitygraphs.ActionState that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public abstract class BaseWrapActionState extends org.omg.uml13.wrap.behavioralelements.statemachines.WrapSimpleState implements org.omg.uml13.behavioralelements.activitygraphs.ActionState {

  /**
   * Wraps an already existing object into a new wrapper instance
   */
  protected BaseWrapActionState(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  /**
   * Constructs a new wrapped object
   */
  public BaseWrapActionState(RefPackage extent) {
    this( getClassProxy( extent ).refCreateInstance( null ),
          Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ));
    Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ).bind( this );
  }

  /**
   * This method retrieves the factory for org.omg.uml13.behavioralelements.activitygraphs.ActionState (also known as "class proxy")
   * model class.
   */
  public static RefClass getClassProxy(RefPackage extent) {
    return ((org.omg.uml13.Uml13Package) extent).getBehavioralElements().getActivityGraphs().getActionState();
  }


  /**
   * Overrides the super implementation with the proper wrapped class
   * @see net.mdatools.modelant.core.wrap.Wrapper#getWrapped()
   */
  public ActionState getWrapped() {
    return (ActionState) super.getWrapped();
  }

  /**
   * @see net.mdatools.modelant.core.wrap.Wrapper#getDelegate()
   */
  protected ActionState getDelegate() {
    return (ActionState) super.getDelegate();
  }


  /**
   * @see org.omg.uml13.behavioralelements.activitygraphs.ActionState#isDynamic()
   */
  public boolean isDynamic() {
    return getDelegate().isDynamic();
  }

  /**
   * @see org.omg.uml13.behavioralelements.activitygraphs.ActionState#getDynamicArguments()
   */
  public org.omg.uml13.foundation.datatypes.ArgListsExpression getDynamicArguments() {
    return getDelegate().getDynamicArguments();
  }

  /**
   * @see org.omg.uml13.behavioralelements.activitygraphs.ActionState#getDynamicMultiplicity()
   */
  public org.omg.uml13.foundation.datatypes.Multiplicity getDynamicMultiplicity() {
    return getDelegate().getDynamicMultiplicity();
  }

  /**
   * @see org.omg.uml13.behavioralelements.activitygraphs.ActionState#setDynamic(boolean)
   */
  public void setDynamic(boolean arg0) {
    getDelegate().setDynamic(arg0);
  }

  /**
   * @see org.omg.uml13.behavioralelements.activitygraphs.ActionState#setDynamicArguments(org.omg.uml13.foundation.datatypes.ArgListsExpression)
   */
  public void setDynamicArguments(org.omg.uml13.foundation.datatypes.ArgListsExpression arg0) {
    getDelegate().setDynamicArguments(arg0);
  }

  /**
   * @see org.omg.uml13.behavioralelements.activitygraphs.ActionState#setDynamicMultiplicity(org.omg.uml13.foundation.datatypes.Multiplicity)
   */
  public void setDynamicMultiplicity(org.omg.uml13.foundation.datatypes.Multiplicity arg0) {
    getDelegate().setDynamicMultiplicity(arg0);
  }
}