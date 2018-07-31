package org.omg.uml14.commonbehavior;

/**
 * NodeInstance object instance interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface NodeInstance extends org.omg.uml14.commonbehavior.Instance {
    /**
     * Returns the value of reference resident.
     * @return Value of reference resident. Element type: {@link org.omg.uml14.commonbehavior.ComponentInstance}
     */
    public java.util.Collection/*<org.omg.uml14.commonbehavior.ComponentInstance>*/ getResident();
}
