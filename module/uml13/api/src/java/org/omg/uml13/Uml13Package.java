package org.omg.uml13;

/**
 * UML package interface.
 * Helper package that clusters all other outermost packages in UML 1.3 metamodel.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface Uml13Package extends javax.jmi.reflect.RefPackage {
    public org.omg.uml13.foundation.FoundationPackage getFoundation();
    public org.omg.uml13.modelmanagement.ModelManagementPackage getModelManagement();
    public org.omg.uml13.behavioralelements.BehavioralElementsPackage getBehavioralElements();
}
