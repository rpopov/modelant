/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml13.foundation.core;

/**
 * A_modelElement2_templateParameter2 association proxy interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface AModelElement2TemplateParameter2 extends javax.jmi.reflect.RefAssociation {
    /**
     * Queries whether a link currently exists between a given pair of instance 
     * objects in the associations link set.
     * @param modelElement2 Value of the first association end.
     * @param templateParameter2 Value of the second association end.
     * @return Returns true if the queried link exists.
     */
    public boolean exists(org.omg.uml13.foundation.core.ModelElement modelElement2, org.omg.uml13.foundation.core.TemplateParameter templateParameter2);
    /**
     * Queries the instance object that is related to a particular instance object 
     * by a link in the current associations link set.
     * @param templateParameter2 Required value of the second association end.
     * @return Related object or <code>null</code> if none exists.
     */
    public org.omg.uml13.foundation.core.ModelElement getModelElement2(org.omg.uml13.foundation.core.TemplateParameter templateParameter2);
    /**
     * Queries the instance objects that are related to a particular instance 
     * object by a link in the current associations link set.
     * @param modelElement2 Required value of the first association end.
     * @return Collection of related objects.
     */
    public java.util.Collection getTemplateParameter2(org.omg.uml13.foundation.core.ModelElement modelElement2);
    /**
     * Creates a link between the pair of instance objects in the associations 
     * link set.
     * @param modelElement2 Value of the first association end.
     * @param templateParameter2 Value of the second association end.
     */
    public boolean add(org.omg.uml13.foundation.core.ModelElement modelElement2, org.omg.uml13.foundation.core.TemplateParameter templateParameter2);
    /**
     * Removes a link between a pair of instance objects in the current associations 
     * link set.
     * @param modelElement2 Value of the first association end.
     * @param templateParameter2 Value of the second association end.
     */
    public boolean remove(org.omg.uml13.foundation.core.ModelElement modelElement2, org.omg.uml13.foundation.core.TemplateParameter templateParameter2);
}
