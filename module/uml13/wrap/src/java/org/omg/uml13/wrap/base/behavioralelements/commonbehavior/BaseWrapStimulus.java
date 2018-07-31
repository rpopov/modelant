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
package org.omg.uml13.wrap.base.behavioralelements.commonbehavior;

import javax.jmi.reflect.RefClass;
import javax.jmi.reflect.RefPackage;
import org.omg.uml13.behavioralelements.commonbehavior.Stimulus;
import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.core.wrap.Factories;

/**
 * This is a wrapper of org.omg.uml13.behavioralelements.commonbehavior.Stimulus that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public abstract class BaseWrapStimulus extends org.omg.uml13.wrap.foundation.core.WrapModelElement implements org.omg.uml13.behavioralelements.commonbehavior.Stimulus {

  /**
   * Wraps an already existing object into a new wrapper instance
   */
  protected BaseWrapStimulus(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  /**
   * Constructs a new wrapped object
   */
  public BaseWrapStimulus(RefPackage extent) {
    this( getClassProxy( extent ).refCreateInstance( null ),
          Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ));
    Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ).bind( this );
  }

  /**
   * This method retrieves the factory for org.omg.uml13.behavioralelements.commonbehavior.Stimulus (also known as "class proxy")
   * model class.
   */
  public static RefClass getClassProxy(RefPackage extent) {
    return ((org.omg.uml13.Uml13Package) extent).getBehavioralElements().getCommonBehavior().getStimulus();
  }


  /**
   * Overrides the super implementation with the proper wrapped class
   * @see net.mdatools.modelant.core.wrap.Wrapper#getWrapped()
   */
  public Stimulus getWrapped() {
    return (Stimulus) super.getWrapped();
  }

  /**
   * @see net.mdatools.modelant.core.wrap.Wrapper#getDelegate()
   */
  protected Stimulus getDelegate() {
    return (Stimulus) super.getDelegate();
  }


  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.Stimulus#getArgument()
   */
  public java.util.Collection getArgument() {
    return getDelegate().getArgument();
  }

  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.Stimulus#getDispatchAction()
   */
  public org.omg.uml13.behavioralelements.commonbehavior.Action getDispatchAction() {
    return getDelegate().getDispatchAction();
  }

  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.Stimulus#getReceiver()
   */
  public org.omg.uml13.behavioralelements.commonbehavior.Instance getReceiver() {
    return getDelegate().getReceiver();
  }

  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.Stimulus#getSender()
   */
  public org.omg.uml13.behavioralelements.commonbehavior.Instance getSender() {
    return getDelegate().getSender();
  }

  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.Stimulus#getCommunicationLink()
   */
  public org.omg.uml13.behavioralelements.commonbehavior.Link getCommunicationLink() {
    return getDelegate().getCommunicationLink();
  }

  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.Stimulus#setCommunicationLink(org.omg.uml13.behavioralelements.commonbehavior.Link)
   */
  public void setCommunicationLink(org.omg.uml13.behavioralelements.commonbehavior.Link arg0) {
    getDelegate().setCommunicationLink(arg0);
  }

  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.Stimulus#setDispatchAction(org.omg.uml13.behavioralelements.commonbehavior.Action)
   */
  public void setDispatchAction(org.omg.uml13.behavioralelements.commonbehavior.Action arg0) {
    getDelegate().setDispatchAction(arg0);
  }

  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.Stimulus#setReceiver(org.omg.uml13.behavioralelements.commonbehavior.Instance)
   */
  public void setReceiver(org.omg.uml13.behavioralelements.commonbehavior.Instance arg0) {
    getDelegate().setReceiver(arg0);
  }

  /**
   * @see org.omg.uml13.behavioralelements.commonbehavior.Stimulus#setSender(org.omg.uml13.behavioralelements.commonbehavior.Instance)
   */
  public void setSender(org.omg.uml13.behavioralelements.commonbehavior.Instance arg0) {
    getDelegate().setSender(arg0);
  }
}