/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml14.collaborations;

/**
 * AssociationRole object instance interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface AssociationRole extends org.omg.uml14.core.UmlAssociation {
    /**
     * Returns the value of attribute multiplicity.
     * @return Value of attribute multiplicity.
     */
    public org.omg.uml14.datatypes.Multiplicity getMultiplicity();
    /**
     * Sets the value of multiplicity attribute. See {@link #getMultiplicity} 
     * for description on the attribute.
     * @param newValue New value to be set.
     */
    public void setMultiplicity(org.omg.uml14.datatypes.Multiplicity newValue);
    /**
     * Returns the value of reference base.
     * @return Value of reference base.
     */
    public org.omg.uml14.core.UmlAssociation getBase();
    /**
     * Sets the value of reference base. See {@link #getBase} for description 
     * on the reference.
     * @param newValue New value to be set.
     */
    public void setBase(org.omg.uml14.core.UmlAssociation newValue);
    /**
     * Returns the value of reference message.
     * @return Value of reference message. Element type: {@link org.omg.uml14.collaborations.Message}
     */
    public java.util.Collection/*<org.omg.uml14.collaborations.Message>*/ getMessage();
    /**
     * Returns the value of reference conformingLink.
     * @return Value of reference conformingLink. Element type: {@link org.omg.uml14.commonbehavior.Link}
     */
    public java.util.Collection/*<org.omg.uml14.commonbehavior.Link>*/ getConformingLink();
}
