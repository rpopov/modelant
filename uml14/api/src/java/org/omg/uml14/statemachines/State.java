/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml14.statemachines;

/**
 * State object instance interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface State extends org.omg.uml14.statemachines.StateVertex {
    /**
     * Returns the value of reference entry.
     * @return Value of reference entry.
     */
    public org.omg.uml14.commonbehavior.Action getEntry();
    /**
     * Sets the value of reference entry. See {@link #getEntry} for description 
     * on the reference.
     * @param newValue New value to be set.
     */
    public void setEntry(org.omg.uml14.commonbehavior.Action newValue);
    /**
     * Returns the value of reference exit.
     * @return Value of reference exit.
     */
    public org.omg.uml14.commonbehavior.Action getExit();
    /**
     * Sets the value of reference exit. See {@link #getExit} for description 
     * on the reference.
     * @param newValue New value to be set.
     */
    public void setExit(org.omg.uml14.commonbehavior.Action newValue);
    /**
     * Returns the value of reference deferrableEvent.
     * @return Value of reference deferrableEvent. Element type: {@link org.omg.uml14.statemachines.Event}
     */
    public java.util.Collection/*<org.omg.uml14.statemachines.Event>*/ getDeferrableEvent();
    /**
     * Returns the value of reference internalTransition.
     * @return Value of reference internalTransition. Element type: {@link org.omg.uml14.statemachines.Transition}
     */
    public java.util.Collection/*<org.omg.uml14.statemachines.Transition>*/ getInternalTransition();
    /**
     * Returns the value of reference doActivity.
     * @return Value of reference doActivity.
     */
    public org.omg.uml14.commonbehavior.Action getDoActivity();
    /**
     * Sets the value of reference doActivity. See {@link #getDoActivity} for 
     * description on the reference.
     * @param newValue New value to be set.
     */
    public void setDoActivity(org.omg.uml14.commonbehavior.Action newValue);
    /**
     * Returns the value of reference stateMachine.
     * @return Value of reference stateMachine.
     */
    public org.omg.uml14.statemachines.StateMachine getStateMachine();
    /**
     * Sets the value of reference stateMachine. See {@link #getStateMachine} 
     * for description on the reference.
     * @param newValue New value to be set.
     */
    public void setStateMachine(org.omg.uml14.statemachines.StateMachine newValue);
}
