/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml14.core;

/**
 * Enumeration object instance interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface Enumeration extends org.omg.uml14.core.DataType {
    /**
     * Returns the value of reference literal.
     * @return Value of reference literal. Element type: {@link org.omg.uml14.core.EnumerationLiteral}
     */
    public java.util.List/*<org.omg.uml14.core.EnumerationLiteral>*/ getLiteral();
}
