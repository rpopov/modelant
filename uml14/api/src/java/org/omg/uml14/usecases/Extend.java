/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml14.usecases;

/**
 * Extend object instance interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface Extend extends org.omg.uml14.core.Relationship {
    /**
     * Returns the value of attribute condition.
     * @return Value of attribute condition.
     */
    public org.omg.uml14.datatypes.BooleanExpression getCondition();
    /**
     * Sets the value of condition attribute. See {@link #getCondition} for description 
     * on the attribute.
     * @param newValue New value to be set.
     */
    public void setCondition(org.omg.uml14.datatypes.BooleanExpression newValue);
    /**
     * Returns the value of reference base.
     * @return Value of reference base.
     */
    public org.omg.uml14.usecases.UseCase getBase();
    /**
     * Sets the value of reference base. See {@link #getBase} for description 
     * on the reference.
     * @param newValue New value to be set.
     */
    public void setBase(org.omg.uml14.usecases.UseCase newValue);
    /**
     * Returns the value of reference extension.
     * @return Value of reference extension.
     */
    public org.omg.uml14.usecases.UseCase getExtension();
    /**
     * Sets the value of reference extension. See {@link #getExtension} for description 
     * on the reference.
     * @param newValue New value to be set.
     */
    public void setExtension(org.omg.uml14.usecases.UseCase newValue);
    /**
     * Returns the value of reference extensionPoint.
     * @return Value of reference extensionPoint. Element type: {@link org.omg.uml14.usecases.ExtensionPoint}
     */
    public java.util.List/*<org.omg.uml14.usecases.ExtensionPoint>*/ getExtensionPoint();
}
