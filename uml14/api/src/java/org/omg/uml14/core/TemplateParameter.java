/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml14.core;

/**
 * TemplateParameter object instance interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface TemplateParameter extends javax.jmi.reflect.RefObject {
    /**
     * Returns the value of reference template.
     * @return Value of reference template.
     */
    public org.omg.uml14.core.ModelElement getTemplate();
    /**
     * Sets the value of reference template. See {@link #getTemplate} for description 
     * on the reference.
     * @param newValue New value to be set.
     */
    public void setTemplate(org.omg.uml14.core.ModelElement newValue);
    /**
     * Returns the value of reference parameter.
     * @return Value of reference parameter.
     */
    public org.omg.uml14.core.ModelElement getParameter();
    /**
     * Sets the value of reference parameter. See {@link #getParameter} for description 
     * on the reference.
     * @param newValue New value to be set.
     */
    public void setParameter(org.omg.uml14.core.ModelElement newValue);
    /**
     * Returns the value of reference defaultElement.
     * @return Value of reference defaultElement.
     */
    public org.omg.uml14.core.ModelElement getDefaultElement();
    /**
     * Sets the value of reference defaultElement. See {@link #getDefaultElement} 
     * for description on the reference.
     * @param newValue New value to be set.
     */
    public void setDefaultElement(org.omg.uml14.core.ModelElement newValue);
}
