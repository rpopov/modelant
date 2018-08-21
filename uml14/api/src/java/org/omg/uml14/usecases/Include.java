/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml14.usecases;

/**
 * Include object instance interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface Include extends org.omg.uml14.core.Relationship {
    /**
     * Returns the value of reference addition.
     * @return Value of reference addition.
     */
    public org.omg.uml14.usecases.UseCase getAddition();
    /**
     * Sets the value of reference addition. See {@link #getAddition} for description 
     * on the reference.
     * @param newValue New value to be set.
     */
    public void setAddition(org.omg.uml14.usecases.UseCase newValue);
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
}
