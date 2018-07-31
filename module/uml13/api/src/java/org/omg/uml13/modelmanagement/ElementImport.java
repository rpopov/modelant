/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml13.modelmanagement;

/**
 * ElementImport object instance interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface ElementImport extends javax.jmi.reflect.RefObject {
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
     * Returns the value of attribute alias.
     * @return Value of attribute alias.
     */
    public java.lang.String getAlias();
    /**
     * Sets the value of alias attribute. See {@link #getAlias} for description 
     * on the attribute.
     * @param newValue New value to be set.
     */
    public void setAlias(java.lang.String newValue);
    /**
     * Returns the value of reference package.
     * @return Value of reference package.
     */
    public org.omg.uml13.modelmanagement.UmlPackage getUmlPackage();
    /**
     * Sets the value of reference package. See {@link #getUmlPackage} for description 
     * on the reference.
     * @param newValue New value to be set.
     */
    public void setUmlPackage(org.omg.uml13.modelmanagement.UmlPackage newValue);
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
}
