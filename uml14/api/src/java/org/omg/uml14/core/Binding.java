/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml14.core;

/**
 * Binding object instance interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface Binding extends org.omg.uml14.core.Dependency {
    /**
     * Returns the value of reference argument.
     * @return Value of reference argument. Element type: {@link org.omg.uml14.core.TemplateArgument}
     */
    public java.util.List/*<org.omg.uml14.core.TemplateArgument>*/ getArgument();
}
