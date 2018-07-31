/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml14.core;

/**
 * Attribute object instance interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface Attribute extends org.omg.uml14.core.StructuralFeature {
    /**
     * Returns the value of attribute initialValue.
     * @return Value of attribute initialValue.
     */
    public org.omg.uml14.datatypes.Expression getInitialValue();
    /**
     * Sets the value of initialValue attribute. See {@link #getInitialValue} 
     * for description on the attribute.
     * @param newValue New value to be set.
     */
    public void setInitialValue(org.omg.uml14.datatypes.Expression newValue);
    /**
     * Returns the value of reference associationEnd.
     * @return Value of reference associationEnd.
     */
    public org.omg.uml14.core.AssociationEnd getAssociationEnd();
    /**
     * Sets the value of reference associationEnd. See {@link #getAssociationEnd} 
     * for description on the reference.
     * @param newValue New value to be set.
     */
    public void setAssociationEnd(org.omg.uml14.core.AssociationEnd newValue);
}
