package org.omg.uml14.core;

/**
 * A_resident_elementResidence association proxy interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface AResidentElementResidence extends javax.jmi.reflect.RefAssociation {
    /**
     * Queries whether a link currently exists between a given pair of instance 
     * objects in the associations link set.
     * @param resident Value of the first association end.
     * @param elementResidence Value of the second association end.
     * @return Returns true if the queried link exists.
     */
    public boolean exists(org.omg.uml14.core.ModelElement resident, org.omg.uml14.core.ElementResidence elementResidence);
    /**
     * Queries the instance object that is related to a particular instance object 
     * by a link in the current associations link set.
     * @param elementResidence Required value of the second association end.
     * @return Related object or <code>null</code> if none exists.
     */
    public org.omg.uml14.core.ModelElement getResident(org.omg.uml14.core.ElementResidence elementResidence);
    /**
     * Queries the instance objects that are related to a particular instance 
     * object by a link in the current associations link set.
     * @param resident Required value of the first association end.
     * @return Collection of related objects.
     */
    public java.util.Collection getElementResidence(org.omg.uml14.core.ModelElement resident);
    /**
     * Creates a link between the pair of instance objects in the associations 
     * link set.
     * @param resident Value of the first association end.
     * @param elementResidence Value of the second association end.
     */
    public boolean add(org.omg.uml14.core.ModelElement resident, org.omg.uml14.core.ElementResidence elementResidence);
    /**
     * Removes a link between a pair of instance objects in the current associations 
     * link set.
     * @param resident Value of the first association end.
     * @param elementResidence Value of the second association end.
     */
    public boolean remove(org.omg.uml14.core.ModelElement resident, org.omg.uml14.core.ElementResidence elementResidence);
}
