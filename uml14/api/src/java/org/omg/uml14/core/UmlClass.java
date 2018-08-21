/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml14.core;

/**
 * Class object instance interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface UmlClass extends org.omg.uml14.core.Classifier {
    /**
     * Returns the value of attribute isActive.
     * @return Value of attribute isActive.
     */
    public boolean isActive();
    /**
     * Sets the value of isActive attribute. See {@link #isActive} for description 
     * on the attribute.
     * @param newValue New value to be set.
     */
    public void setActive(boolean newValue);
}
