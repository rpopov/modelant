/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml14.commonbehavior;

/**
 * Instance object instance interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface Instance extends org.omg.uml14.core.ModelElement {
    /**
     * Returns the value of reference classifier.
     * @return Value of reference classifier. Element type: {@link org.omg.uml14.core.Classifier}
     */
    public java.util.Collection/*<org.omg.uml14.core.Classifier>*/ getClassifier();
    /**
     * Returns the value of reference linkEnd.
     * @return Value of reference linkEnd. Element type: {@link org.omg.uml14.commonbehavior.LinkEnd}
     */
    public java.util.Collection/*<org.omg.uml14.commonbehavior.LinkEnd>*/ getLinkEnd();
    /**
     * Returns the value of reference slot.
     * @return Value of reference slot. Element type: {@link org.omg.uml14.commonbehavior.AttributeLink}
     */
    public java.util.Collection/*<org.omg.uml14.commonbehavior.AttributeLink>*/ getSlot();
    /**
     * Returns the value of reference componentInstance.
     * @return Value of reference componentInstance.
     */
    public org.omg.uml14.commonbehavior.ComponentInstance getComponentInstance();
    /**
     * Sets the value of reference componentInstance. See {@link #getComponentInstance} 
     * for description on the reference.
     * @param newValue New value to be set.
     */
    public void setComponentInstance(org.omg.uml14.commonbehavior.ComponentInstance newValue);
    /**
     * Returns the value of reference ownedInstance.
     * @return Value of reference ownedInstance. Element type: {@link org.omg.uml14.commonbehavior.Instance}
     */
    public java.util.Collection/*<org.omg.uml14.commonbehavior.Instance>*/ getOwnedInstance();
    /**
     * Returns the value of reference ownedLink.
     * @return Value of reference ownedLink. Element type: {@link org.omg.uml14.commonbehavior.Link}
     */
    public java.util.Collection/*<org.omg.uml14.commonbehavior.Link>*/ getOwnedLink();
}
