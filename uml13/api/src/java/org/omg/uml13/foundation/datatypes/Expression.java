/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml13.foundation.datatypes;

/**
 * Expression object instance interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface Expression extends javax.jmi.reflect.RefObject {
    /**
     * Returns the value of attribute language.
     * @return Value of attribute language.
     */
    public java.lang.String getLanguage();
    /**
     * Sets the value of language attribute. See {@link #getLanguage} for description 
     * on the attribute.
     * @param newValue New value to be set.
     */
    public void setLanguage(java.lang.String newValue);
    /**
     * Returns the value of attribute body.
     * @return Value of attribute body.
     */
    public java.lang.String getBody();
    /**
     * Sets the value of body attribute. See {@link #getBody} for description 
     * on the attribute.
     * @param newValue New value to be set.
     */
    public void setBody(java.lang.String newValue);
}
