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
import org.omg.uml13.behavioralelements.activitygraphs.Partition;
import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.core.wrap.Factories;

/**
 * This is a wrapper of org.omg.uml13.behavioralelements.activitygraphs.Partition that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public abstract class BaseWrapPartition extends org.omg.uml13.wrap.foundation.core.WrapModelElement implements org.omg.uml13.behavioralelements.activitygraphs.Partition {

  /**
   * Wraps an already existing object into a new wrapper instance
   */
  protected BaseWrapPartition(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  /**
   * Constructs a new wrapped object
   */
  public BaseWrapPartition(RefPackage extent) {
    this( getClassProxy( extent ).refCreateInstance( null ),
          Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ));
    Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ).bind( this );
  }

  /**
   * This method retrieves the factory for org.omg.uml13.behavioralelements.activitygraphs.Partition (also known as "class proxy")
   * model class.
   */
  public static RefClass getClassProxy(RefPackage extent) {
    return ((org.omg.uml13.Uml13Package) extent).getBehavioralElements().getActivityGraphs().getPartition();
  }


  /**
   * Overrides the super implementation with the proper wrapped class
   * @see net.mdatools.modelant.core.wrap.Wrapper#getWrapped()
   */
  public Partition getWrapped() {
    return (Partition) super.getWrapped();
  }

  /**
   * @see net.mdatools.modelant.core.wrap.Wrapper#getDelegate()
   */
  protected Partition getDelegate() {
    return (Partition) super.getDelegate();
  }


  /**
   * @see org.omg.uml13.behavioralelements.activitygraphs.Partition#getContents()
   */
  public java.util.Collection getContents() {
    return getDelegate().getContents();
  }

  /**
   * @see org.omg.uml13.behavioralelements.activitygraphs.Partition#getActivityGraph()
   */
  public org.omg.uml13.behavioralelements.activitygraphs.ActivityGraph getActivityGraph() {
    return getDelegate().getActivityGraph();
  }

  /**
   * @see org.omg.uml13.behavioralelements.activitygraphs.Partition#setActivityGraph(org.omg.uml13.behavioralelements.activitygraphs.ActivityGraph)
   */
  public void setActivityGraph(org.omg.uml13.behavioralelements.activitygraphs.ActivityGraph arg0) {
    getDelegate().setActivityGraph(arg0);
  }
}