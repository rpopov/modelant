/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml14.core;

/**
 * Stereotype object instance interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface Stereotype extends org.omg.uml14.core.GeneralizableElement {
    /**
     * Returns the value of attribute icon.
     * @return Value of attribute icon.
     */
    public java.lang.String getIcon();
    /**
     * Sets the value of icon attribute. See {@link #getIcon} for description 
     * on the attribute.
     * @param newValue New value to be set.
     */
    public void setIcon(java.lang.String newValue);
    /**
     * Returns the value of attribute baseClass.
     * @return Value of baseClass attribute. Element type: {@link java.lang.String}
     */
    public java.util.Collection/*<java.lang.String>*/ getBaseClass();
    /**
     * Returns the value of reference definedTag.
     * @return Value of reference definedTag. Element type: {@link org.omg.uml14.core.TagDefinition}
     */
    public java.util.Collection/*<org.omg.uml14.core.TagDefinition>*/ getDefinedTag();
    /**
     * Returns the value of reference stereotypeConstraint.
     * @return Value of reference stereotypeConstraint. Element type: {@link org.omg.uml14.core.Constraint}
     */
    public java.util.Collection/*<org.omg.uml14.core.Constraint>*/ getStereotypeConstraint();
}
