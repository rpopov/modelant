package org.omg.uml14;

/**
 * UML package interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface Uml14Package extends javax.jmi.reflect.RefPackage {
    public org.omg.uml14.datatypes.DataTypesPackage getDataTypes();
    public org.omg.uml14.core.CorePackage getCore();
    public org.omg.uml14.commonbehavior.CommonBehaviorPackage getCommonBehavior();
    public org.omg.uml14.usecases.UseCasesPackage getUseCases();
    public org.omg.uml14.statemachines.StateMachinesPackage getStateMachines();
    public org.omg.uml14.collaborations.CollaborationsPackage getCollaborations();
    public org.omg.uml14.activitygraphs.ActivityGraphsPackage getActivityGraphs();
    public org.omg.uml14.modelmanagement.ModelManagementPackage getModelManagement();
}
