/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml14.core;

/**
 * TagDefinition object instance interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface TagDefinition extends org.omg.uml14.core.ModelElement {
    /**
     * Returns the value of attribute tagType.
     * @return Value of attribute tagType.
     */
    public java.lang.String getTagType();
    /**
     * Sets the value of tagType attribute. See {@link #getTagType} for description 
     * on the attribute.
     * @param newValue New value to be set.
     */
    public void setTagType(java.lang.String newValue);
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
     * Returns the value of reference owner.
     * @return Value of reference owner.
     */
    public org.omg.uml14.core.Stereotype getOwner();
    /**
     * Sets the value of reference owner. See {@link #getOwner} for description 
     * on the reference.
     * @param newValue New value to be set.
     */
    public void setOwner(org.omg.uml14.core.Stereotype newValue);
}
