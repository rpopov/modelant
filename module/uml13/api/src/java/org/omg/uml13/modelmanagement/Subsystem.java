/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml13.modelmanagement;

/**
 * Subsystem object instance interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface Subsystem extends org.omg.uml13.modelmanagement.UmlPackage, org.omg.uml13.foundation.core.Classifier {
    /**
     * Returns the value of attribute isInstantiable.
     * @return Value of attribute isInstantiable.
     */
    public boolean isInstantiable();
    /**
     * Sets the value of isInstantiable attribute. See {@link #isInstantiable} 
     * for description on the attribute.
     * @param newValue New value to be set.
     */
    public void setInstantiable(boolean newValue);
}
