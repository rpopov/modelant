/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml13.foundation.core;

/**
 * Method object instance interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface Method extends org.omg.uml13.foundation.core.BehavioralFeature {
    /**
     * Returns the value of attribute body.
     * @return Value of attribute body.
     */
    public org.omg.uml13.foundation.datatypes.ProcedureExpression getBody();
    /**
     * Sets the value of body attribute. See {@link #getBody} for description 
     * on the attribute.
     * @param newValue New value to be set.
     */
    public void setBody(org.omg.uml13.foundation.datatypes.ProcedureExpression newValue);
    /**
     * Returns the value of reference specification.
     * @return Value of reference specification.
     */
    public org.omg.uml13.foundation.core.Operation getSpecification();
    /**
     * Sets the value of reference specification. See {@link #getSpecification} 
     * for description on the reference.
     * @param newValue New value to be set.
     */
    public void setSpecification(org.omg.uml13.foundation.core.Operation newValue);
}
