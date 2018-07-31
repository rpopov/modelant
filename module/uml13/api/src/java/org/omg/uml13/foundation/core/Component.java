package org.omg.uml13.foundation.core;

/**
 * Component object instance interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface Component extends org.omg.uml13.foundation.core.Classifier {
    /**
     * Returns the value of reference deploymentLocation.
     * @return Value of reference deploymentLocation. Element type: {@link org.omg.uml13.foundation.core.Node}
     */
    public java.util.Collection/*<org.omg.uml13.foundation.core.Node>*/ getDeploymentLocation();
    /**
     * Returns the value of reference residentElement.
     * @return Value of reference residentElement. Element type: {@link org.omg.uml13.foundation.core.ElementResidence}
     */
    public java.util.Collection/*<org.omg.uml13.foundation.core.ElementResidence>*/ getResidentElement();
}
