/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml14.collaborations;

/**
 * ClassifierRole object instance interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface ClassifierRole extends org.omg.uml14.core.Classifier {
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
     * @return Value of reference base. Element type: {@link org.omg.uml14.core.Classifier}
     */
    public java.util.Collection/*<org.omg.uml14.core.Classifier>*/ getBase();
    /**
     * Returns the value of reference availableFeature.
     * @return Value of reference availableFeature. Element type: {@link org.omg.uml14.core.Feature}
     */
    public java.util.Collection/*<org.omg.uml14.core.Feature>*/ getAvailableFeature();
    /**
     * Returns the value of reference availableContents.
     * @return Value of reference availableContents. Element type: {@link org.omg.uml14.core.ModelElement}
     */
    public java.util.Collection/*<org.omg.uml14.core.ModelElement>*/ getAvailableContents();
    /**
     * Returns the value of reference conformingInstance.
     * @return Value of reference conformingInstance. Element type: {@link org.omg.uml14.commonbehavior.Instance}
     */
    public java.util.Collection/*<org.omg.uml14.commonbehavior.Instance>*/ getConformingInstance();
}
