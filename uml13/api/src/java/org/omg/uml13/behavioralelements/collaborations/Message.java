/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml13.behavioralelements.collaborations;

/**
 * Message object instance interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface Message extends org.omg.uml13.foundation.core.ModelElement {
    /**
     * Returns the value of reference interaction.
     * @return Value of reference interaction.
     */
    public org.omg.uml13.behavioralelements.collaborations.Interaction getInteraction();
    /**
     * Sets the value of reference interaction. See {@link #getInteraction} for 
     * description on the reference.
     * @param newValue New value to be set.
     */
    public void setInteraction(org.omg.uml13.behavioralelements.collaborations.Interaction newValue);
    /**
     * Returns the value of reference activator.
     * @return Value of reference activator.
     */
    public org.omg.uml13.behavioralelements.collaborations.Message getActivator();
    /**
     * Sets the value of reference activator. See {@link #getActivator} for description 
     * on the reference.
     * @param newValue New value to be set.
     */
    public void setActivator(org.omg.uml13.behavioralelements.collaborations.Message newValue);
    /**
     * Returns the value of reference sender.
     * @return Value of reference sender.
     */
    public org.omg.uml13.behavioralelements.collaborations.ClassifierRole getSender();
    /**
     * Sets the value of reference sender. See {@link #getSender} for description 
     * on the reference.
     * @param newValue New value to be set.
     */
    public void setSender(org.omg.uml13.behavioralelements.collaborations.ClassifierRole newValue);
    /**
     * Returns the value of reference receiver.
     * @return Value of reference receiver.
     */
    public org.omg.uml13.behavioralelements.collaborations.ClassifierRole getReceiver();
    /**
     * Sets the value of reference receiver. See {@link #getReceiver} for description 
     * on the reference.
     * @param newValue New value to be set.
     */
    public void setReceiver(org.omg.uml13.behavioralelements.collaborations.ClassifierRole newValue);
    /**
     * Returns the value of reference predecessor.
     * @return Value of reference predecessor. Element type: {@link org.omg.uml13.behavioralelements.collaborations.Message}
     */
    public java.util.Collection/*<org.omg.uml13.behavioralelements.collaborations.Message>*/ getPredecessor();
    /**
     * Returns the value of reference communicationConnection.
     * @return Value of reference communicationConnection.
     */
    public org.omg.uml13.behavioralelements.collaborations.AssociationRole getCommunicationConnection();
    /**
     * Sets the value of reference communicationConnection. See {@link #getCommunicationConnection} 
     * for description on the reference.
     * @param newValue New value to be set.
     */
    public void setCommunicationConnection(org.omg.uml13.behavioralelements.collaborations.AssociationRole newValue);
    /**
     * Returns the value of reference action.
     * @return Value of reference action.
     */
    public org.omg.uml13.behavioralelements.commonbehavior.Action getAction();
    /**
     * Sets the value of reference action. See {@link #getAction} for description 
     * on the reference.
     * @param newValue New value to be set.
     */
    public void setAction(org.omg.uml13.behavioralelements.commonbehavior.Action newValue);
    /**
     * Returns the value of reference message3.
     * @return Value of reference message3. Element type: {@link org.omg.uml13.behavioralelements.collaborations.Message}
     */
    public java.util.Collection/*<org.omg.uml13.behavioralelements.collaborations.Message>*/ getMessage3();
    /**
     * Returns the value of reference message4.
     * @return Value of reference message4. Element type: {@link org.omg.uml13.behavioralelements.collaborations.Message}
     */
    public java.util.Collection/*<org.omg.uml13.behavioralelements.collaborations.Message>*/ getMessage4();
}
