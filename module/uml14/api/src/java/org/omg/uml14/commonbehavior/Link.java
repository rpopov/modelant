/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml14.commonbehavior;

/**
 * Link object instance interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface Link extends org.omg.uml14.core.ModelElement {
    /**
     * Returns the value of reference association.
     * @return Value of reference association.
     */
    public org.omg.uml14.core.UmlAssociation getAssociation();
    /**
     * Sets the value of reference association. See {@link #getAssociation} for 
     * description on the reference.
     * @param newValue New value to be set.
     */
    public void setAssociation(org.omg.uml14.core.UmlAssociation newValue);
    /**
     * Returns the value of reference connection.
     * @return Value of reference connection. Element type: {@link org.omg.uml14.commonbehavior.LinkEnd}
     */
    public java.util.Collection/*<org.omg.uml14.commonbehavior.LinkEnd>*/ getConnection();
}
