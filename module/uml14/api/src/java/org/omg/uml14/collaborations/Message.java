package org.omg.uml14.collaborations;

/**
 * Message object instance interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface Message extends org.omg.uml14.core.ModelElement {
    /**
     * Returns the value of reference interaction.
     * @return Value of reference interaction.
     */
    public org.omg.uml14.collaborations.Interaction getInteraction();
    /**
     * Sets the value of reference interaction. See {@link #getInteraction} for 
     * description on the reference.
     * @param newValue New value to be set.
     */
    public void setInteraction(org.omg.uml14.collaborations.Interaction newValue);
    /**
     * Returns the value of reference activator.
     * @return Value of reference activator.
     */
    public org.omg.uml14.collaborations.Message getActivator();
    /**
     * Sets the value of reference activator. See {@link #getActivator} for description 
     * on the reference.
     * @param newValue New value to be set.
     */
    public void setActivator(org.omg.uml14.collaborations.Message newValue);
    /**
     * Returns the value of reference sender.
     * @return Value of reference sender.
     */
    public org.omg.uml14.collaborations.ClassifierRole getSender();
    /**
     * Sets the value of reference sender. See {@link #getSender} for description 
     * on the reference.
     * @param newValue New value to be set.
     */
    public void setSender(org.omg.uml14.collaborations.ClassifierRole newValue);
    /**
     * Returns the value of reference receiver.
     * @return Value of reference receiver.
     */
    public org.omg.uml14.collaborations.ClassifierRole getReceiver();
    /**
     * Sets the value of reference receiver. See {@link #getReceiver} for description 
     * on the reference.
     * @param newValue New value to be set.
     */
    public void setReceiver(org.omg.uml14.collaborations.ClassifierRole newValue);
    /**
     * Returns the value of reference predecessor.
     * @return Value of reference predecessor. Element type: {@link org.omg.uml14.collaborations.Message}
     */
    public java.util.Collection/*<org.omg.uml14.collaborations.Message>*/ getPredecessor();
    /**
     * Returns the value of reference communicationConnection.
     * @return Value of reference communicationConnection.
     */
    public org.omg.uml14.collaborations.AssociationRole getCommunicationConnection();
    /**
     * Sets the value of reference communicationConnection. See {@link #getCommunicationConnection} 
     * for description on the reference.
     * @param newValue New value to be set.
     */
    public void setCommunicationConnection(org.omg.uml14.collaborations.AssociationRole newValue);
    /**
     * Returns the value of reference action.
     * @return Value of reference action.
     */
    public org.omg.uml14.commonbehavior.Action getAction();
    /**
     * Sets the value of reference action. See {@link #getAction} for description 
     * on the reference.
     * @param newValue New value to be set.
     */
    public void setAction(org.omg.uml14.commonbehavior.Action newValue);
    /**
     * Returns the value of reference conformingStimulus.
     * @return Value of reference conformingStimulus. Element type: {@link org.omg.uml14.commonbehavior.Stimulus}
     */
    public java.util.Collection/*<org.omg.uml14.commonbehavior.Stimulus>*/ getConformingStimulus();
}
