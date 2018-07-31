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
import org.omg.uml13.behavioralelements.collaborations.Message;
import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.core.wrap.Factories;

/**
 * This is a wrapper of org.omg.uml13.behavioralelements.collaborations.Message that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public abstract class BaseWrapMessage extends org.omg.uml13.wrap.foundation.core.WrapModelElement implements org.omg.uml13.behavioralelements.collaborations.Message {

  /**
   * Wraps an already existing object into a new wrapper instance
   */
  protected BaseWrapMessage(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  /**
   * Constructs a new wrapped object
   */
  public BaseWrapMessage(RefPackage extent) {
    this( getClassProxy( extent ).refCreateInstance( null ),
          Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ));
    Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ).bind( this );
  }

  /**
   * This method retrieves the factory for org.omg.uml13.behavioralelements.collaborations.Message (also known as "class proxy")
   * model class.
   */
  public static RefClass getClassProxy(RefPackage extent) {
    return ((org.omg.uml13.Uml13Package) extent).getBehavioralElements().getCollaborations().getMessage();
  }


  /**
   * Overrides the super implementation with the proper wrapped class
   * @see net.mdatools.modelant.core.wrap.Wrapper#getWrapped()
   */
  public Message getWrapped() {
    return (Message) super.getWrapped();
  }

  /**
   * @see net.mdatools.modelant.core.wrap.Wrapper#getDelegate()
   */
  protected Message getDelegate() {
    return (Message) super.getDelegate();
  }


  /**
   * @see org.omg.uml13.behavioralelements.collaborations.Message#getMessage3()
   */
  public java.util.Collection getMessage3() {
    return getDelegate().getMessage3();
  }

  /**
   * @see org.omg.uml13.behavioralelements.collaborations.Message#getMessage4()
   */
  public java.util.Collection getMessage4() {
    return getDelegate().getMessage4();
  }

  /**
   * @see org.omg.uml13.behavioralelements.collaborations.Message#getPredecessor()
   */
  public java.util.Collection getPredecessor() {
    return getDelegate().getPredecessor();
  }

  /**
   * @see org.omg.uml13.behavioralelements.collaborations.Message#getCommunicationConnection()
   */
  public org.omg.uml13.behavioralelements.collaborations.AssociationRole getCommunicationConnection() {
    return getDelegate().getCommunicationConnection();
  }

  /**
   * @see org.omg.uml13.behavioralelements.collaborations.Message#getReceiver()
   */
  public org.omg.uml13.behavioralelements.collaborations.ClassifierRole getReceiver() {
    return getDelegate().getReceiver();
  }

  /**
   * @see org.omg.uml13.behavioralelements.collaborations.Message#getSender()
   */
  public org.omg.uml13.behavioralelements.collaborations.ClassifierRole getSender() {
    return getDelegate().getSender();
  }

  /**
   * @see org.omg.uml13.behavioralelements.collaborations.Message#getInteraction()
   */
  public org.omg.uml13.behavioralelements.collaborations.Interaction getInteraction() {
    return getDelegate().getInteraction();
  }

  /**
   * @see org.omg.uml13.behavioralelements.collaborations.Message#getActivator()
   */
  public org.omg.uml13.behavioralelements.collaborations.Message getActivator() {
    return getDelegate().getActivator();
  }

  /**
   * @see org.omg.uml13.behavioralelements.collaborations.Message#getAction()
   */
  public org.omg.uml13.behavioralelements.commonbehavior.Action getAction() {
    return getDelegate().getAction();
  }

  /**
   * @see org.omg.uml13.behavioralelements.collaborations.Message#setAction(org.omg.uml13.behavioralelements.commonbehavior.Action)
   */
  public void setAction(org.omg.uml13.behavioralelements.commonbehavior.Action arg0) {
    getDelegate().setAction(arg0);
  }

  /**
   * @see org.omg.uml13.behavioralelements.collaborations.Message#setActivator(org.omg.uml13.behavioralelements.collaborations.Message)
   */
  public void setActivator(org.omg.uml13.behavioralelements.collaborations.Message arg0) {
    getDelegate().setActivator(arg0);
  }

  /**
   * @see org.omg.uml13.behavioralelements.collaborations.Message#setCommunicationConnection(org.omg.uml13.behavioralelements.collaborations.AssociationRole)
   */
  public void setCommunicationConnection(org.omg.uml13.behavioralelements.collaborations.AssociationRole arg0) {
    getDelegate().setCommunicationConnection(arg0);
  }

  /**
   * @see org.omg.uml13.behavioralelements.collaborations.Message#setInteraction(org.omg.uml13.behavioralelements.collaborations.Interaction)
   */
  public void setInteraction(org.omg.uml13.behavioralelements.collaborations.Interaction arg0) {
    getDelegate().setInteraction(arg0);
  }

  /**
   * @see org.omg.uml13.behavioralelements.collaborations.Message#setReceiver(org.omg.uml13.behavioralelements.collaborations.ClassifierRole)
   */
  public void setReceiver(org.omg.uml13.behavioralelements.collaborations.ClassifierRole arg0) {
    getDelegate().setReceiver(arg0);
  }

  /**
   * @see org.omg.uml13.behavioralelements.collaborations.Message#setSender(org.omg.uml13.behavioralelements.collaborations.ClassifierRole)
   */
  public void setSender(org.omg.uml13.behavioralelements.collaborations.ClassifierRole arg0) {
    getDelegate().setSender(arg0);
  }
}