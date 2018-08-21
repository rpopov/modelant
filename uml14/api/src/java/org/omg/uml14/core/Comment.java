/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml14.core;

/**
 * Comment object instance interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface Comment extends org.omg.uml14.core.ModelElement {
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
    /**
     * Returns the value of reference annotatedElement.
     * @return Value of reference annotatedElement. Element type: {@link org.omg.uml14.core.ModelElement}
     */
    public java.util.Collection/*<org.omg.uml14.core.ModelElement>*/ getAnnotatedElement();
}
