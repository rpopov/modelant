/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml13.foundation.datatypes;

/**
 * A_multiplicity_range association proxy interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface AMultiplicityRange extends javax.jmi.reflect.RefAssociation {
    /**
     * Queries whether a link currently exists between a given pair of instance 
     * objects in the associations link set.
     * @param multiplicity Value of the first association end.
     * @param range Value of the second association end.
     * @return Returns true if the queried link exists.
     */
    public boolean exists(org.omg.uml13.foundation.datatypes.Multiplicity multiplicity, org.omg.uml13.foundation.datatypes.MultiplicityRange range);
    /**
     * Queries the instance object that is related to a particular instance object 
     * by a link in the current associations link set.
     * @param range Required value of the second association end.
     * @return Related object or <code>null</code> if none exists.
     */
    public org.omg.uml13.foundation.datatypes.Multiplicity getMultiplicity(org.omg.uml13.foundation.datatypes.MultiplicityRange range);
    /**
     * Queries the instance objects that are related to a particular instance 
     * object by a link in the current associations link set.
     * @param multiplicity Required value of the first association end.
     * @return Collection of related objects.
     */
    public java.util.Collection getRange(org.omg.uml13.foundation.datatypes.Multiplicity multiplicity);
    /**
     * Creates a link between the pair of instance objects in the associations 
     * link set.
     * @param multiplicity Value of the first association end.
     * @param range Value of the second association end.
     */
    public boolean add(org.omg.uml13.foundation.datatypes.Multiplicity multiplicity, org.omg.uml13.foundation.datatypes.MultiplicityRange range);
    /**
     * Removes a link between a pair of instance objects in the current associations 
     * link set.
     * @param multiplicity Value of the first association end.
     * @param range Value of the second association end.
     */
    public boolean remove(org.omg.uml13.foundation.datatypes.Multiplicity multiplicity, org.omg.uml13.foundation.datatypes.MultiplicityRange range);
}
