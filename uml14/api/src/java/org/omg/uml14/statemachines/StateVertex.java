/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml14.statemachines;

/**
 * StateVertex object instance interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface StateVertex extends org.omg.uml14.core.ModelElement {
    /**
     * Returns the value of reference container.
     * @return Value of reference container.
     */
    public org.omg.uml14.statemachines.CompositeState getContainer();
    /**
     * Sets the value of reference container. See {@link #getContainer} for description 
     * on the reference.
     * @param newValue New value to be set.
     */
    public void setContainer(org.omg.uml14.statemachines.CompositeState newValue);
    /**
     * Returns the value of reference outgoing.
     * @return Value of reference outgoing. Element type: {@link org.omg.uml14.statemachines.Transition}
     */
    public java.util.Collection/*<org.omg.uml14.statemachines.Transition>*/ getOutgoing();
    /**
     * Returns the value of reference incoming.
     * @return Value of reference incoming. Element type: {@link org.omg.uml14.statemachines.Transition}
     */
    public java.util.Collection/*<org.omg.uml14.statemachines.Transition>*/ getIncoming();
}
