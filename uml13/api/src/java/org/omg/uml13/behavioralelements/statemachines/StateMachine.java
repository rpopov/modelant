/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml13.behavioralelements.statemachines;

/**
 * StateMachine object instance interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface StateMachine extends org.omg.uml13.foundation.core.ModelElement {
    /**
     * Returns the value of reference context.
     * @return Value of reference context.
     */
    public org.omg.uml13.foundation.core.ModelElement getContext();
    /**
     * Sets the value of reference context. See {@link #getContext} for description 
     * on the reference.
     * @param newValue New value to be set.
     */
    public void setContext(org.omg.uml13.foundation.core.ModelElement newValue);
    /**
     * Returns the value of reference top.
     * @return Value of reference top.
     */
    public org.omg.uml13.behavioralelements.statemachines.State getTop();
    /**
     * Sets the value of reference top. See {@link #getTop} for description on 
     * the reference.
     * @param newValue New value to be set.
     */
    public void setTop(org.omg.uml13.behavioralelements.statemachines.State newValue);
    /**
     * Returns the value of reference transitions.
     * @return Value of reference transitions. Element type: {@link org.omg.uml13.behavioralelements.statemachines.Transition}
     */
    public java.util.Collection/*<org.omg.uml13.behavioralelements.statemachines.Transition>*/ getTransitions();
    /**
     * Returns the value of reference subMachineState.
     * @return Value of reference subMachineState. Element type: {@link org.omg.uml13.behavioralelements.statemachines.SubmachineState}
     */
    public java.util.Collection/*<org.omg.uml13.behavioralelements.statemachines.SubmachineState>*/ getSubMachineState();
}
