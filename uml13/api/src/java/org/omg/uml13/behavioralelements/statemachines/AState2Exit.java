/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml13.behavioralelements.statemachines;

/**
 * A_state2_exit association proxy interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface AState2Exit extends javax.jmi.reflect.RefAssociation {
    /**
     * Queries whether a link currently exists between a given pair of instance 
     * objects in the associations link set.
     * @param state2 Value of the first association end.
     * @param exit Value of the second association end.
     * @return Returns true if the queried link exists.
     */
    public boolean exists(org.omg.uml13.behavioralelements.statemachines.State state2, org.omg.uml13.behavioralelements.commonbehavior.Action exit);
    /**
     * Queries the instance object that is related to a particular instance object 
     * by a link in the current associations link set.
     * @param exit Required value of the second association end.
     * @return Related object or <code>null</code> if none exists.
     */
    public org.omg.uml13.behavioralelements.statemachines.State getState2(org.omg.uml13.behavioralelements.commonbehavior.Action exit);
    /**
     * Queries the instance object that is related to a particular instance object 
     * by a link in the current associations link set.
     * @param state2 Required value of the first association end.
     * @return Related object or <code>null</code> if none exists.
     */
    public org.omg.uml13.behavioralelements.commonbehavior.Action getExit(org.omg.uml13.behavioralelements.statemachines.State state2);
    /**
     * Creates a link between the pair of instance objects in the associations 
     * link set.
     * @param state2 Value of the first association end.
     * @param exit Value of the second association end.
     */
    public boolean add(org.omg.uml13.behavioralelements.statemachines.State state2, org.omg.uml13.behavioralelements.commonbehavior.Action exit);
    /**
     * Removes a link between a pair of instance objects in the current associations 
     * link set.
     * @param state2 Value of the first association end.
     * @param exit Value of the second association end.
     */
    public boolean remove(org.omg.uml13.behavioralelements.statemachines.State state2, org.omg.uml13.behavioralelements.commonbehavior.Action exit);
}
