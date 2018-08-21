/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml14.statemachines;

/**
 * State_Machines package interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface StateMachinesPackage extends javax.jmi.reflect.RefPackage {
    public org.omg.uml14.core.CorePackage getCore();
    public org.omg.uml14.commonbehavior.CommonBehaviorPackage getCommonBehavior();
    public org.omg.uml14.datatypes.DataTypesPackage getDataTypes();
    /**
     * Returns StateMachine class proxy object.
     * @return StateMachine class proxy object.
     */
    public org.omg.uml14.statemachines.StateMachineClass getStateMachine();
    /**
     * Returns Event class proxy object.
     * @return Event class proxy object.
     */
    public org.omg.uml14.statemachines.EventClass getEvent();
    /**
     * Returns StateVertex class proxy object.
     * @return StateVertex class proxy object.
     */
    public org.omg.uml14.statemachines.StateVertexClass getStateVertex();
    /**
     * Returns State class proxy object.
     * @return State class proxy object.
     */
    public org.omg.uml14.statemachines.StateClass getState();
    /**
     * Returns TimeEvent class proxy object.
     * @return TimeEvent class proxy object.
     */
    public org.omg.uml14.statemachines.TimeEventClass getTimeEvent();
    /**
     * Returns CallEvent class proxy object.
     * @return CallEvent class proxy object.
     */
    public org.omg.uml14.statemachines.CallEventClass getCallEvent();
    /**
     * Returns SignalEvent class proxy object.
     * @return SignalEvent class proxy object.
     */
    public org.omg.uml14.statemachines.SignalEventClass getSignalEvent();
    /**
     * Returns Transition class proxy object.
     * @return Transition class proxy object.
     */
    public org.omg.uml14.statemachines.TransitionClass getTransition();
    /**
     * Returns CompositeState class proxy object.
     * @return CompositeState class proxy object.
     */
    public org.omg.uml14.statemachines.CompositeStateClass getCompositeState();
    /**
     * Returns ChangeEvent class proxy object.
     * @return ChangeEvent class proxy object.
     */
    public org.omg.uml14.statemachines.ChangeEventClass getChangeEvent();
    /**
     * Returns Guard class proxy object.
     * @return Guard class proxy object.
     */
    public org.omg.uml14.statemachines.GuardClass getGuard();
    /**
     * Returns Pseudostate class proxy object.
     * @return Pseudostate class proxy object.
     */
    public org.omg.uml14.statemachines.PseudostateClass getPseudostate();
    /**
     * Returns SimpleState class proxy object.
     * @return SimpleState class proxy object.
     */
    public org.omg.uml14.statemachines.SimpleStateClass getSimpleState();
    /**
     * Returns SubmachineState class proxy object.
     * @return SubmachineState class proxy object.
     */
    public org.omg.uml14.statemachines.SubmachineStateClass getSubmachineState();
    /**
     * Returns SynchState class proxy object.
     * @return SynchState class proxy object.
     */
    public org.omg.uml14.statemachines.SynchStateClass getSynchState();
    /**
     * Returns StubState class proxy object.
     * @return StubState class proxy object.
     */
    public org.omg.uml14.statemachines.StubStateClass getStubState();
    /**
     * Returns FinalState class proxy object.
     * @return FinalState class proxy object.
     */
    public org.omg.uml14.statemachines.FinalStateClass getFinalState();
    /**
     * Returns AStateEntry association proxy object.
     * @return AStateEntry association proxy object.
     */
    public org.omg.uml14.statemachines.AStateEntry getAStateEntry();
    /**
     * Returns AStateExit association proxy object.
     * @return AStateExit association proxy object.
     */
    public org.omg.uml14.statemachines.AStateExit getAStateExit();
    /**
     * Returns AEventParameter association proxy object.
     * @return AEventParameter association proxy object.
     */
    public org.omg.uml14.statemachines.AEventParameter getAEventParameter();
    /**
     * Returns AGuardTransition association proxy object.
     * @return AGuardTransition association proxy object.
     */
    public org.omg.uml14.statemachines.AGuardTransition getAGuardTransition();
    /**
     * Returns ASignalOccurrence association proxy object.
     * @return ASignalOccurrence association proxy object.
     */
    public org.omg.uml14.statemachines.ASignalOccurrence getASignalOccurrence();
    /**
     * Returns ABehaviorContext association proxy object.
     * @return ABehaviorContext association proxy object.
     */
    public org.omg.uml14.statemachines.ABehaviorContext getABehaviorContext();
    /**
     * Returns ATopStateMachine association proxy object.
     * @return ATopStateMachine association proxy object.
     */
    public org.omg.uml14.statemachines.ATopStateMachine getATopStateMachine();
    /**
     * Returns AStateDeferrableEvent association proxy object.
     * @return AStateDeferrableEvent association proxy object.
     */
    public org.omg.uml14.statemachines.AStateDeferrableEvent getAStateDeferrableEvent();
    /**
     * Returns AOccurrenceOperation association proxy object.
     * @return AOccurrenceOperation association proxy object.
     */
    public org.omg.uml14.statemachines.AOccurrenceOperation getAOccurrenceOperation();
    /**
     * Returns AContainerSubvertex association proxy object.
     * @return AContainerSubvertex association proxy object.
     */
    public org.omg.uml14.statemachines.AContainerSubvertex getAContainerSubvertex();
    /**
     * Returns ATransitionEffect association proxy object.
     * @return ATransitionEffect association proxy object.
     */
    public org.omg.uml14.statemachines.ATransitionEffect getATransitionEffect();
    /**
     * Returns AStateInternalTransition association proxy object.
     * @return AStateInternalTransition association proxy object.
     */
    public org.omg.uml14.statemachines.AStateInternalTransition getAStateInternalTransition();
    /**
     * Returns ATransitionTrigger association proxy object.
     * @return ATransitionTrigger association proxy object.
     */
    public org.omg.uml14.statemachines.ATransitionTrigger getATransitionTrigger();
    /**
     * Returns AStateMachineTransitions association proxy object.
     * @return AStateMachineTransitions association proxy object.
     */
    public org.omg.uml14.statemachines.AStateMachineTransitions getAStateMachineTransitions();
    /**
     * Returns AOutgoingSource association proxy object.
     * @return AOutgoingSource association proxy object.
     */
    public org.omg.uml14.statemachines.AOutgoingSource getAOutgoingSource();
    /**
     * Returns AIncomingTarget association proxy object.
     * @return AIncomingTarget association proxy object.
     */
    public org.omg.uml14.statemachines.AIncomingTarget getAIncomingTarget();
    /**
     * Returns ASubmachineStateSubmachine association proxy object.
     * @return ASubmachineStateSubmachine association proxy object.
     */
    public org.omg.uml14.statemachines.ASubmachineStateSubmachine getASubmachineStateSubmachine();
    /**
     * Returns AStateDoActivity association proxy object.
     * @return AStateDoActivity association proxy object.
     */
    public org.omg.uml14.statemachines.AStateDoActivity getAStateDoActivity();
}
