/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml14.collaborations;

/**
 * A_participatingLink_collaborationInstanceSet association proxy interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface AParticipatingLinkCollaborationInstanceSet extends javax.jmi.reflect.RefAssociation {
    /**
     * Queries whether a link currently exists between a given pair of instance 
     * objects in the associations link set.
     * @param participatingLink Value of the first association end.
     * @param collaborationInstanceSet Value of the second association end.
     * @return Returns true if the queried link exists.
     */
    public boolean exists(org.omg.uml14.commonbehavior.Link participatingLink, org.omg.uml14.collaborations.CollaborationInstanceSet collaborationInstanceSet);
    /**
     * Queries the instance objects that are related to a particular instance 
     * object by a link in the current associations link set.
     * @param collaborationInstanceSet Required value of the second association 
     * end.
     * @return Collection of related objects.
     */
    public java.util.Collection getParticipatingLink(org.omg.uml14.collaborations.CollaborationInstanceSet collaborationInstanceSet);
    /**
     * Queries the instance objects that are related to a particular instance 
     * object by a link in the current associations link set.
     * @param participatingLink Required value of the first association end.
     * @return Collection of related objects.
     */
    public java.util.Collection getCollaborationInstanceSet(org.omg.uml14.commonbehavior.Link participatingLink);
    /**
     * Creates a link between the pair of instance objects in the associations 
     * link set.
     * @param participatingLink Value of the first association end.
     * @param collaborationInstanceSet Value of the second association end.
     */
    public boolean add(org.omg.uml14.commonbehavior.Link participatingLink, org.omg.uml14.collaborations.CollaborationInstanceSet collaborationInstanceSet);
    /**
     * Removes a link between a pair of instance objects in the current associations 
     * link set.
     * @param participatingLink Value of the first association end.
     * @param collaborationInstanceSet Value of the second association end.
     */
    public boolean remove(org.omg.uml14.commonbehavior.Link participatingLink, org.omg.uml14.collaborations.CollaborationInstanceSet collaborationInstanceSet);
}
