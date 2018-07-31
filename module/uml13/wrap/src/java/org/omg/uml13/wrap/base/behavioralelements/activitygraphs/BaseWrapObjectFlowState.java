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
import org.omg.uml13.behavioralelements.activitygraphs.ObjectFlowState;
import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.core.wrap.Factories;

/**
 * This is a wrapper of org.omg.uml13.behavioralelements.activitygraphs.ObjectFlowState that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public abstract class BaseWrapObjectFlowState extends org.omg.uml13.wrap.behavioralelements.statemachines.WrapSimpleState implements org.omg.uml13.behavioralelements.activitygraphs.ObjectFlowState {

  /**
   * Wraps an already existing object into a new wrapper instance
   */
  protected BaseWrapObjectFlowState(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  /**
   * Constructs a new wrapped object
   */
  public BaseWrapObjectFlowState(RefPackage extent) {
    this( getClassProxy( extent ).refCreateInstance( null ),
          Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ));
    Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ).bind( this );
  }

  /**
   * This method retrieves the factory for org.omg.uml13.behavioralelements.activitygraphs.ObjectFlowState (also known as "class proxy")
   * model class.
   */
  public static RefClass getClassProxy(RefPackage extent) {
    return ((org.omg.uml13.Uml13Package) extent).getBehavioralElements().getActivityGraphs().getObjectFlowState();
  }


  /**
   * Overrides the super implementation with the proper wrapped class
   * @see net.mdatools.modelant.core.wrap.Wrapper#getWrapped()
   */
  public ObjectFlowState getWrapped() {
    return (ObjectFlowState) super.getWrapped();
  }

  /**
   * @see net.mdatools.modelant.core.wrap.Wrapper#getDelegate()
   */
  protected ObjectFlowState getDelegate() {
    return (ObjectFlowState) super.getDelegate();
  }


  /**
   * @see org.omg.uml13.behavioralelements.activitygraphs.ObjectFlowState#isSynch()
   */
  public boolean isSynch() {
    return getDelegate().isSynch();
  }

  /**
   * @see org.omg.uml13.behavioralelements.activitygraphs.ObjectFlowState#getParameter()
   */
  public java.util.Collection getParameter() {
    return getDelegate().getParameter();
  }

  /**
   * @see org.omg.uml13.behavioralelements.activitygraphs.ObjectFlowState#getType()
   */
  public org.omg.uml13.foundation.core.Classifier getType() {
    return getDelegate().getType();
  }

  /**
   * @see org.omg.uml13.behavioralelements.activitygraphs.ObjectFlowState#setSynch(boolean)
   */
  public void setSynch(boolean arg0) {
    getDelegate().setSynch(arg0);
  }

  /**
   * @see org.omg.uml13.behavioralelements.activitygraphs.ObjectFlowState#setType(org.omg.uml13.foundation.core.Classifier)
   */
  public void setType(org.omg.uml13.foundation.core.Classifier arg0) {
    getDelegate().setType(arg0);
  }
}