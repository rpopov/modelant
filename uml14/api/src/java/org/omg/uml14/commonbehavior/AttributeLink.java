/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml14.commonbehavior;

/**
 * AttributeLink object instance interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface AttributeLink extends org.omg.uml14.core.ModelElement {
    /**
     * Returns the value of reference attribute.
     * @return Value of reference attribute.
     */
    public org.omg.uml14.core.Attribute getAttribute();
    /**
     * Sets the value of reference attribute. See {@link #getAttribute} for description 
     * on the reference.
     * @param newValue New value to be set.
     */
    public void setAttribute(org.omg.uml14.core.Attribute newValue);
    /**
     * Returns the value of reference value.
     * @return Value of reference value.
     */
    public org.omg.uml14.commonbehavior.Instance getValue();
    /**
     * Sets the value of reference value. See {@link #getValue} for description 
     * on the reference.
     * @param newValue New value to be set.
     */
    public void setValue(org.omg.uml14.commonbehavior.Instance newValue);
    /**
     * Returns the value of reference instance.
     * @return Value of reference instance.
     */
    public org.omg.uml14.commonbehavior.Instance getInstance();
    /**
     * Sets the value of reference instance. See {@link #getInstance} for description 
     * on the reference.
     * @param newValue New value to be set.
     */
    public void setInstance(org.omg.uml14.commonbehavior.Instance newValue);
    /**
     * Returns the value of reference linkEnd.
     * @return Value of reference linkEnd.
     */
    public org.omg.uml14.commonbehavior.LinkEnd getLinkEnd();
    /**
     * Sets the value of reference linkEnd. See {@link #getLinkEnd} for description 
     * on the reference.
     * @param newValue New value to be set.
     */
    public void setLinkEnd(org.omg.uml14.commonbehavior.LinkEnd newValue);
}
