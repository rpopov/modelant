/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml13.behavioralelements.statemachines;

/**
 * Event object instance interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface Event extends org.omg.uml13.foundation.core.ModelElement {
    /**
     * Returns the value of reference parameter.
     * @return Value of reference parameter. Element type: {@link org.omg.uml13.foundation.core.Parameter}
     */
    public java.util.List/*<org.omg.uml13.foundation.core.Parameter>*/ getParameter();
    /**
     * Returns the value of reference state.
     * @return Value of reference state. Element type: {@link org.omg.uml13.behavioralelements.statemachines.State}
     */
    public java.util.Collection/*<org.omg.uml13.behavioralelements.statemachines.State>*/ getState();
    /**
     * Returns the value of reference transition.
     * @return Value of reference transition. Element type: {@link org.omg.uml13.behavioralelements.statemachines.Transition}
     */
    public java.util.Collection/*<org.omg.uml13.behavioralelements.statemachines.Transition>*/ getTransition();
}
