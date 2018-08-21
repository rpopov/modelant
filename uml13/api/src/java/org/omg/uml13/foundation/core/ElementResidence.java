/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml13.foundation.core;

/**
 * ElementResidence object instance interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface ElementResidence extends javax.jmi.reflect.RefObject {
    /**
     * Returns the value of attribute visibility.
     * @return Value of attribute visibility.
     */
    public org.omg.uml13.foundation.datatypes.VisibilityKind getVisibility();
    /**
     * Sets the value of visibility attribute. See {@link #getVisibility} for 
     * description on the attribute.
     * @param newValue New value to be set.
     */
    public void setVisibility(org.omg.uml13.foundation.datatypes.VisibilityKind newValue);
    /**
     * Returns the value of reference resident.
     * @return Value of reference resident.
     */
    public org.omg.uml13.foundation.core.ModelElement getResident();
    /**
     * Sets the value of reference resident. See {@link #getResident} for description 
     * on the reference.
     * @param newValue New value to be set.
     */
    public void setResident(org.omg.uml13.foundation.core.ModelElement newValue);
    /**
     * Returns the value of reference implementationLocation.
     * @return Value of reference implementationLocation.
     */
    public org.omg.uml13.foundation.core.Component getImplementationLocation();
    /**
     * Sets the value of reference implementationLocation. See {@link #getImplementationLocation} 
     * for description on the reference.
     * @param newValue New value to be set.
     */
    public void setImplementationLocation(org.omg.uml13.foundation.core.Component newValue);
}
