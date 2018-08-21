/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml14.core;

/**
 * Feature object instance interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface Feature extends org.omg.uml14.core.ModelElement {
    /**
     * Returns the value of attribute ownerScope.
     * @return Value of attribute ownerScope.
     */
    public org.omg.uml14.datatypes.ScopeKind getOwnerScope();
    /**
     * Sets the value of ownerScope attribute. See {@link #getOwnerScope} for 
     * description on the attribute.
     * @param newValue New value to be set.
     */
    public void setOwnerScope(org.omg.uml14.datatypes.ScopeKind newValue);
    /**
     * Returns the value of reference owner.
     * @return Value of reference owner.
     */
    public org.omg.uml14.core.Classifier getOwner();
    /**
     * Sets the value of reference owner. See {@link #getOwner} for description 
     * on the reference.
     * @param newValue New value to be set.
     */
    public void setOwner(org.omg.uml14.core.Classifier newValue);
}
