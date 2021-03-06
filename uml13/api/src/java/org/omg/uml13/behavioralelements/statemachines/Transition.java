/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml13.behavioralelements.statemachines;

/**
 * Transition object instance interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface Transition extends org.omg.uml13.foundation.core.ModelElement {
    /**
     * Returns the value of reference guard.
     * @return Value of reference guard.
     */
    public org.omg.uml13.behavioralelements.statemachines.Guard getGuard();
    /**
     * Sets the value of reference guard. See {@link #getGuard} for description 
     * on the reference.
     * @param newValue New value to be set.
     */
    public void setGuard(org.omg.uml13.behavioralelements.statemachines.Guard newValue);
    /**
     * Returns the value of reference effect.
     * @return Value of reference effect.
     */
    public org.omg.uml13.behavioralelements.commonbehavior.Action getEffect();
    /**
     * Sets the value of reference effect. See {@link #getEffect} for description 
     * on the reference.
     * @param newValue New value to be set.
     */
    public void setEffect(org.omg.uml13.behavioralelements.commonbehavior.Action newValue);
    /**
     * Returns the value of reference state.
     * @return Value of reference state.
     */
    public org.omg.uml13.behavioralelements.statemachines.State getState();
    /**
     * Sets the value of reference state. See {@link #getState} for description 
     * on the reference.
     * @param newValue New value to be set.
     */
    public void setState(org.omg.uml13.behavioralelements.statemachines.State newValue);
    /**
     * Returns the value of reference trigger.
     * @return Value of reference trigger.
     */
    public org.omg.uml13.behavioralelements.statemachines.Event getTrigger();
    /**
     * Sets the value of reference trigger. See {@link #getTrigger} for description 
     * on the reference.
     * @param newValue New value to be set.
     */
    public void setTrigger(org.omg.uml13.behavioralelements.statemachines.Event newValue);
    /**
     * Returns the value of reference stateMachine.
     * @return Value of reference stateMachine.
     */
    public org.omg.uml13.behavioralelements.statemachines.StateMachine getStateMachine();
    /**
     * Sets the value of reference stateMachine. See {@link #getStateMachine} 
     * for description on the reference.
     * @param newValue New value to be set.
     */
    public void setStateMachine(org.omg.uml13.behavioralelements.statemachines.StateMachine newValue);
    /**
     * Returns the value of reference source.
     * @return Value of reference source.
     */
    public org.omg.uml13.behavioralelements.statemachines.StateVertex getSource();
    /**
     * Sets the value of reference source. See {@link #getSource} for description 
     * on the reference.
     * @param newValue New value to be set.
     */
    public void setSource(org.omg.uml13.behavioralelements.statemachines.StateVertex newValue);
    /**
     * Returns the value of reference target.
     * @return Value of reference target.
     */
    public org.omg.uml13.behavioralelements.statemachines.StateVertex getTarget();
    /**
     * Sets the value of reference target. See {@link #getTarget} for description 
     * on the reference.
     * @param newValue New value to be set.
     */
    public void setTarget(org.omg.uml13.behavioralelements.statemachines.StateVertex newValue);
}
