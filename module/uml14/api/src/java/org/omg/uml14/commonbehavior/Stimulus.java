/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml14.commonbehavior;

/**
 * Stimulus object instance interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface Stimulus extends org.omg.uml14.core.ModelElement {
    /**
     * Returns the value of reference argument.
     * @return Value of reference argument. Element type: {@link org.omg.uml14.commonbehavior.Instance}
     */
    public java.util.List/*<org.omg.uml14.commonbehavior.Instance>*/ getArgument();
    /**
     * Returns the value of reference sender.
     * @return Value of reference sender.
     */
    public org.omg.uml14.commonbehavior.Instance getSender();
    /**
     * Sets the value of reference sender. See {@link #getSender} for description 
     * on the reference.
     * @param newValue New value to be set.
     */
    public void setSender(org.omg.uml14.commonbehavior.Instance newValue);
    /**
     * Returns the value of reference receiver.
     * @return Value of reference receiver.
     */
    public org.omg.uml14.commonbehavior.Instance getReceiver();
    /**
     * Sets the value of reference receiver. See {@link #getReceiver} for description 
     * on the reference.
     * @param newValue New value to be set.
     */
    public void setReceiver(org.omg.uml14.commonbehavior.Instance newValue);
    /**
     * Returns the value of reference communicationLink.
     * @return Value of reference communicationLink.
     */
    public org.omg.uml14.commonbehavior.Link getCommunicationLink();
    /**
     * Sets the value of reference communicationLink. See {@link #getCommunicationLink} 
     * for description on the reference.
     * @param newValue New value to be set.
     */
    public void setCommunicationLink(org.omg.uml14.commonbehavior.Link newValue);
    /**
     * Returns the value of reference dispatchAction.
     * @return Value of reference dispatchAction.
     */
    public org.omg.uml14.commonbehavior.Action getDispatchAction();
    /**
     * Sets the value of reference dispatchAction. See {@link #getDispatchAction} 
     * for description on the reference.
     * @param newValue New value to be set.
     */
    public void setDispatchAction(org.omg.uml14.commonbehavior.Action newValue);
}
