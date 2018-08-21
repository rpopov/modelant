/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml13.behavioralelements.statemachines;

/**
 * SubmachineState object instance interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface SubmachineState extends org.omg.uml13.behavioralelements.statemachines.CompositeState {
    /**
     * Returns the value of reference submachine.
     * @return Value of reference submachine.
     */
    public org.omg.uml13.behavioralelements.statemachines.StateMachine getSubmachine();
    /**
     * Sets the value of reference submachine. See {@link #getSubmachine} for 
     * description on the reference.
     * @param newValue New value to be set.
     */
    public void setSubmachine(org.omg.uml13.behavioralelements.statemachines.StateMachine newValue);
}
