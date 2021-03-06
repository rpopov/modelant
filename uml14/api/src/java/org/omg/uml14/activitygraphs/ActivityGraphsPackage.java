/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml14.activitygraphs;

/**
 * Activity_Graphs package interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface ActivityGraphsPackage extends javax.jmi.reflect.RefPackage {
    public org.omg.uml14.core.CorePackage getCore();
    public org.omg.uml14.datatypes.DataTypesPackage getDataTypes();
    public org.omg.uml14.statemachines.StateMachinesPackage getStateMachines();
    public org.omg.uml14.commonbehavior.CommonBehaviorPackage getCommonBehavior();
    /**
     * Returns ActivityGraph class proxy object.
     * @return ActivityGraph class proxy object.
     */
    public org.omg.uml14.activitygraphs.ActivityGraphClass getActivityGraph();
    /**
     * Returns Partition class proxy object.
     * @return Partition class proxy object.
     */
    public org.omg.uml14.activitygraphs.PartitionClass getPartition();
    /**
     * Returns SubactivityState class proxy object.
     * @return SubactivityState class proxy object.
     */
    public org.omg.uml14.activitygraphs.SubactivityStateClass getSubactivityState();
    /**
     * Returns ActionState class proxy object.
     * @return ActionState class proxy object.
     */
    public org.omg.uml14.activitygraphs.ActionStateClass getActionState();
    /**
     * Returns CallState class proxy object.
     * @return CallState class proxy object.
     */
    public org.omg.uml14.activitygraphs.CallStateClass getCallState();
    /**
     * Returns ObjectFlowState class proxy object.
     * @return ObjectFlowState class proxy object.
     */
    public org.omg.uml14.activitygraphs.ObjectFlowStateClass getObjectFlowState();
    /**
     * Returns ClassifierInState class proxy object.
     * @return ClassifierInState class proxy object.
     */
    public org.omg.uml14.activitygraphs.ClassifierInStateClass getClassifierInState();
    /**
     * Returns AParameterState association proxy object.
     * @return AParameterState association proxy object.
     */
    public org.omg.uml14.activitygraphs.AParameterState getAParameterState();
    /**
     * Returns ATypeClassifierInState association proxy object.
     * @return ATypeClassifierInState association proxy object.
     */
    public org.omg.uml14.activitygraphs.ATypeClassifierInState getATypeClassifierInState();
    /**
     * Returns AContentsPartition association proxy object.
     * @return AContentsPartition association proxy object.
     */
    public org.omg.uml14.activitygraphs.AContentsPartition getAContentsPartition();
    /**
     * Returns AActivityGraphPartition association proxy object.
     * @return AActivityGraphPartition association proxy object.
     */
    public org.omg.uml14.activitygraphs.AActivityGraphPartition getAActivityGraphPartition();
    /**
     * Returns ATypeObjectFlowState association proxy object.
     * @return ATypeObjectFlowState association proxy object.
     */
    public org.omg.uml14.activitygraphs.ATypeObjectFlowState getATypeObjectFlowState();
    /**
     * Returns AClassifierInStateInState association proxy object.
     * @return AClassifierInStateInState association proxy object.
     */
    public org.omg.uml14.activitygraphs.AClassifierInStateInState getAClassifierInStateInState();
}
