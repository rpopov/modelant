/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml14.commonbehavior;

/**
 * ComponentInstance object instance interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface ComponentInstance extends org.omg.uml14.commonbehavior.Instance {
    /**
     * Returns the value of reference nodeInstance.
     * @return Value of reference nodeInstance.
     */
    public org.omg.uml14.commonbehavior.NodeInstance getNodeInstance();
    /**
     * Sets the value of reference nodeInstance. See {@link #getNodeInstance} 
     * for description on the reference.
     * @param newValue New value to be set.
     */
    public void setNodeInstance(org.omg.uml14.commonbehavior.NodeInstance newValue);
    /**
     * Returns the value of reference resident.
     * @return Value of reference resident. Element type: {@link org.omg.uml14.commonbehavior.Instance}
     */
    public java.util.Collection/*<org.omg.uml14.commonbehavior.Instance>*/ getResident();
}
