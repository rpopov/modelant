/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml13.foundation.core;

/**
 * Parameter object instance interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface Parameter extends org.omg.uml13.foundation.core.ModelElement {
    /**
     * Returns the value of attribute defaultValue.
     * @return Value of attribute defaultValue.
     */
    public org.omg.uml13.foundation.datatypes.Expression getDefaultValue();
    /**
     * Sets the value of defaultValue attribute. See {@link #getDefaultValue} 
     * for description on the attribute.
     * @param newValue New value to be set.
     */
    public void setDefaultValue(org.omg.uml13.foundation.datatypes.Expression newValue);
    /**
     * Returns the value of attribute kind.
     * @return Value of attribute kind.
     */
    public org.omg.uml13.foundation.datatypes.ParameterDirectionKind getKind();
    /**
     * Sets the value of kind attribute. See {@link #getKind} for description 
     * on the attribute.
     * @param newValue New value to be set.
     */
    public void setKind(org.omg.uml13.foundation.datatypes.ParameterDirectionKind newValue);
    /**
     * Returns the value of reference behavioralFeature.
     * @return Value of reference behavioralFeature.
     */
    public org.omg.uml13.foundation.core.BehavioralFeature getBehavioralFeature();
    /**
     * Sets the value of reference behavioralFeature. See {@link #getBehavioralFeature} 
     * for description on the reference.
     * @param newValue New value to be set.
     */
    public void setBehavioralFeature(org.omg.uml13.foundation.core.BehavioralFeature newValue);
    /**
     * Returns the value of reference type.
     * @return Value of reference type.
     */
    public org.omg.uml13.foundation.core.Classifier getType();
    /**
     * Sets the value of reference type. See {@link #getType} for description 
     * on the reference.
     * @param newValue New value to be set.
     */
    public void setType(org.omg.uml13.foundation.core.Classifier newValue);
}
