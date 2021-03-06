/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml14.core;

/**
 * Generalization object instance interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface Generalization extends org.omg.uml14.core.Relationship {
    /**
     * Returns the value of attribute discriminator.
     * @return Value of attribute discriminator.
     */
    public java.lang.String getDiscriminator();
    /**
     * Sets the value of discriminator attribute. See {@link #getDiscriminator} 
     * for description on the attribute.
     * @param newValue New value to be set.
     */
    public void setDiscriminator(java.lang.String newValue);
    /**
     * Returns the value of reference child.
     * @return Value of reference child.
     */
    public org.omg.uml14.core.GeneralizableElement getChild();
    /**
     * Sets the value of reference child. See {@link #getChild} for description 
     * on the reference.
     * @param newValue New value to be set.
     */
    public void setChild(org.omg.uml14.core.GeneralizableElement newValue);
    /**
     * Returns the value of reference parent.
     * @return Value of reference parent.
     */
    public org.omg.uml14.core.GeneralizableElement getParent();
    /**
     * Sets the value of reference parent. See {@link #getParent} for description 
     * on the reference.
     * @param newValue New value to be set.
     */
    public void setParent(org.omg.uml14.core.GeneralizableElement newValue);
    /**
     * Returns the value of reference powertype.
     * @return Value of reference powertype.
     */
    public org.omg.uml14.core.Classifier getPowertype();
    /**
     * Sets the value of reference powertype. See {@link #getPowertype} for description 
     * on the reference.
     * @param newValue New value to be set.
     */
    public void setPowertype(org.omg.uml14.core.Classifier newValue);
}
