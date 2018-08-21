/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml13.foundation.core;

/**
 * TemplateParameter object instance interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface TemplateParameter extends javax.jmi.reflect.RefObject {
    /**
     * Returns the value of reference defaultElement.
     * @return Value of reference defaultElement.
     */
    public org.omg.uml13.foundation.core.ModelElement getDefaultElement();
    /**
     * Sets the value of reference defaultElement. See {@link #getDefaultElement} 
     * for description on the reference.
     * @param newValue New value to be set.
     */
    public void setDefaultElement(org.omg.uml13.foundation.core.ModelElement newValue);
    /**
     * Returns the value of reference modelElement.
     * @return Value of reference modelElement.
     */
    public org.omg.uml13.foundation.core.ModelElement getModelElement();
    /**
     * Sets the value of reference modelElement. See {@link #getModelElement} 
     * for description on the reference.
     * @param newValue New value to be set.
     */
    public void setModelElement(org.omg.uml13.foundation.core.ModelElement newValue);
    /**
     * Returns the value of reference modelElement2.
     * @return Value of reference modelElement2.
     */
    public org.omg.uml13.foundation.core.ModelElement getModelElement2();
    /**
     * Sets the value of reference modelElement2. See {@link #getModelElement2} 
     * for description on the reference.
     * @param newValue New value to be set.
     */
    public void setModelElement2(org.omg.uml13.foundation.core.ModelElement newValue);
}
