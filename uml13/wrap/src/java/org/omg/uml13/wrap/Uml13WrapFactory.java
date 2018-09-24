/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml13.wrap;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;

import javax.jmi.reflect.RefPackage;

import org.omg.uml13.foundation.core.AssociationEnd;
import org.omg.uml13.foundation.core.Classifier;
import org.omg.uml13.foundation.core.ModelElement;
import org.omg.uml13.foundation.extensionmechanisms.Stereotype;
import org.omg.uml13.foundation.extensionmechanisms.TaggedValue;
import org.omg.uml13.wrap.foundation.core.WrapAssociationEnd;
import org.omg.uml13.wrap.foundation.extensionmechanisms.WrapStereotype;
import org.omg.uml13.wrap.foundation.extensionmechanisms.WrapTaggedValue;

import net.mdatools.modelant.core.api.wrap.Wrapper;
import net.mdatools.modelant.core.wrap.BaseWrapperFactory;
import net.mdatools.modelant.uml13.metamodel.Convention;
import net.mdatools.modelant.uml13.wrap.AssociationEndsCache;
import net.mdatools.modelant.uml13.wrap.StereotypeCache;
import net.mdatools.modelant.uml13.wrap.TaggedValuesCache;

/**
 * This class maps Model Classes to corresponding wrapper classes.
 * Uses externally provided uml13.properties file for translation and type mapping purposes,
 * which is looked up in classpath.
 * @author Rusi Popov (popovr@mdatools.net)
 */
public class Uml13WrapFactory extends BaseWrapperFactory implements Convention {

  private static final Map<Class<?>, Class<? extends Wrapper>> mapModelToWrapper = new HashMap<Class<?>, Class<? extends Wrapper>>();
  static {
    mapModelToWrapper.put(org.omg.uml13.modelmanagement.ElementImport.class,org.omg.uml13.wrap.modelmanagement.WrapElementImport.class);
    mapModelToWrapper.put(org.omg.uml13.modelmanagement.Subsystem.class,org.omg.uml13.wrap.modelmanagement.WrapSubsystem.class);
    mapModelToWrapper.put(org.omg.uml13.modelmanagement.Model.class,org.omg.uml13.wrap.modelmanagement.WrapModel.class);
    mapModelToWrapper.put(org.omg.uml13.modelmanagement.UmlPackage.class,org.omg.uml13.wrap.modelmanagement.WrapUmlPackage.class);
    mapModelToWrapper.put(org.omg.uml13.behavioralelements.activitygraphs.ClassifierInState.class,org.omg.uml13.wrap.behavioralelements.activitygraphs.WrapClassifierInState.class);
    mapModelToWrapper.put(org.omg.uml13.behavioralelements.activitygraphs.ObjectFlowState.class,org.omg.uml13.wrap.behavioralelements.activitygraphs.WrapObjectFlowState.class);
    mapModelToWrapper.put(org.omg.uml13.behavioralelements.activitygraphs.CallState.class,org.omg.uml13.wrap.behavioralelements.activitygraphs.WrapCallState.class);
    mapModelToWrapper.put(org.omg.uml13.behavioralelements.activitygraphs.ActionState.class,org.omg.uml13.wrap.behavioralelements.activitygraphs.WrapActionState.class);
    mapModelToWrapper.put(org.omg.uml13.behavioralelements.activitygraphs.SubactivityState.class,org.omg.uml13.wrap.behavioralelements.activitygraphs.WrapSubactivityState.class);
    mapModelToWrapper.put(org.omg.uml13.behavioralelements.activitygraphs.Partition.class,org.omg.uml13.wrap.behavioralelements.activitygraphs.WrapPartition.class);
    mapModelToWrapper.put(org.omg.uml13.behavioralelements.activitygraphs.ActivityGraph.class,org.omg.uml13.wrap.behavioralelements.activitygraphs.WrapActivityGraph.class);
    mapModelToWrapper.put(org.omg.uml13.behavioralelements.collaborations.Interaction.class,org.omg.uml13.wrap.behavioralelements.collaborations.WrapInteraction.class);
    mapModelToWrapper.put(org.omg.uml13.behavioralelements.collaborations.Message.class,org.omg.uml13.wrap.behavioralelements.collaborations.WrapMessage.class);
    mapModelToWrapper.put(org.omg.uml13.behavioralelements.collaborations.AssociationEndRole.class,org.omg.uml13.wrap.behavioralelements.collaborations.WrapAssociationEndRole.class);
    mapModelToWrapper.put(org.omg.uml13.behavioralelements.collaborations.AssociationRole.class,org.omg.uml13.wrap.behavioralelements.collaborations.WrapAssociationRole.class);
    mapModelToWrapper.put(org.omg.uml13.behavioralelements.collaborations.ClassifierRole.class,org.omg.uml13.wrap.behavioralelements.collaborations.WrapClassifierRole.class);
    mapModelToWrapper.put(org.omg.uml13.behavioralelements.collaborations.Collaboration.class,org.omg.uml13.wrap.behavioralelements.collaborations.WrapCollaboration.class);
    mapModelToWrapper.put(org.omg.uml13.behavioralelements.statemachines.FinalState.class,org.omg.uml13.wrap.behavioralelements.statemachines.WrapFinalState.class);
    mapModelToWrapper.put(org.omg.uml13.behavioralelements.statemachines.StubState.class,org.omg.uml13.wrap.behavioralelements.statemachines.WrapStubState.class);
    mapModelToWrapper.put(org.omg.uml13.behavioralelements.statemachines.SynchState.class,org.omg.uml13.wrap.behavioralelements.statemachines.WrapSynchState.class);
    mapModelToWrapper.put(org.omg.uml13.behavioralelements.statemachines.SubmachineState.class,org.omg.uml13.wrap.behavioralelements.statemachines.WrapSubmachineState.class);
    mapModelToWrapper.put(org.omg.uml13.behavioralelements.statemachines.SimpleState.class,org.omg.uml13.wrap.behavioralelements.statemachines.WrapSimpleState.class);
    mapModelToWrapper.put(org.omg.uml13.behavioralelements.statemachines.Pseudostate.class,org.omg.uml13.wrap.behavioralelements.statemachines.WrapPseudostate.class);
    mapModelToWrapper.put(org.omg.uml13.behavioralelements.statemachines.Guard.class,org.omg.uml13.wrap.behavioralelements.statemachines.WrapGuard.class);
    mapModelToWrapper.put(org.omg.uml13.behavioralelements.statemachines.ChangeEvent.class,org.omg.uml13.wrap.behavioralelements.statemachines.WrapChangeEvent.class);
    mapModelToWrapper.put(org.omg.uml13.behavioralelements.statemachines.CompositeState.class,org.omg.uml13.wrap.behavioralelements.statemachines.WrapCompositeState.class);
    mapModelToWrapper.put(org.omg.uml13.behavioralelements.statemachines.Transition.class,org.omg.uml13.wrap.behavioralelements.statemachines.WrapTransition.class);
    mapModelToWrapper.put(org.omg.uml13.behavioralelements.statemachines.SignalEvent.class,org.omg.uml13.wrap.behavioralelements.statemachines.WrapSignalEvent.class);
    mapModelToWrapper.put(org.omg.uml13.behavioralelements.statemachines.CallEvent.class,org.omg.uml13.wrap.behavioralelements.statemachines.WrapCallEvent.class);
    mapModelToWrapper.put(org.omg.uml13.behavioralelements.statemachines.TimeEvent.class,org.omg.uml13.wrap.behavioralelements.statemachines.WrapTimeEvent.class);
    mapModelToWrapper.put(org.omg.uml13.behavioralelements.statemachines.State.class,org.omg.uml13.wrap.behavioralelements.statemachines.WrapState.class);
    mapModelToWrapper.put(org.omg.uml13.behavioralelements.statemachines.StateVertex.class,org.omg.uml13.wrap.behavioralelements.statemachines.WrapStateVertex.class);
    mapModelToWrapper.put(org.omg.uml13.behavioralelements.statemachines.Event.class,org.omg.uml13.wrap.behavioralelements.statemachines.WrapEvent.class);
    mapModelToWrapper.put(org.omg.uml13.behavioralelements.statemachines.StateMachine.class,org.omg.uml13.wrap.behavioralelements.statemachines.WrapStateMachine.class);
    mapModelToWrapper.put(org.omg.uml13.behavioralelements.usecases.ExtensionPoint.class,org.omg.uml13.wrap.behavioralelements.usecases.WrapExtensionPoint.class);
    mapModelToWrapper.put(org.omg.uml13.behavioralelements.usecases.Include.class,org.omg.uml13.wrap.behavioralelements.usecases.WrapInclude.class);
    mapModelToWrapper.put(org.omg.uml13.behavioralelements.usecases.Extend.class,org.omg.uml13.wrap.behavioralelements.usecases.WrapExtend.class);
    mapModelToWrapper.put(org.omg.uml13.behavioralelements.usecases.UseCaseInstance.class,org.omg.uml13.wrap.behavioralelements.usecases.WrapUseCaseInstance.class);
    mapModelToWrapper.put(org.omg.uml13.behavioralelements.usecases.Actor.class,org.omg.uml13.wrap.behavioralelements.usecases.WrapActor.class);
    mapModelToWrapper.put(org.omg.uml13.behavioralelements.usecases.UseCase.class,org.omg.uml13.wrap.behavioralelements.usecases.WrapUseCase.class);
    mapModelToWrapper.put(org.omg.uml13.behavioralelements.commonbehavior.NodeInstance.class,org.omg.uml13.wrap.behavioralelements.commonbehavior.WrapNodeInstance.class);
    mapModelToWrapper.put(org.omg.uml13.behavioralelements.commonbehavior.ComponentInstance.class,org.omg.uml13.wrap.behavioralelements.commonbehavior.WrapComponentInstance.class);
    mapModelToWrapper.put(org.omg.uml13.behavioralelements.commonbehavior.UmlException.class,org.omg.uml13.wrap.behavioralelements.commonbehavior.WrapUmlException.class);
    mapModelToWrapper.put(org.omg.uml13.behavioralelements.commonbehavior.Stimulus.class,org.omg.uml13.wrap.behavioralelements.commonbehavior.WrapStimulus.class);
    mapModelToWrapper.put(org.omg.uml13.behavioralelements.commonbehavior.TerminateAction.class,org.omg.uml13.wrap.behavioralelements.commonbehavior.WrapTerminateAction.class);
    mapModelToWrapper.put(org.omg.uml13.behavioralelements.commonbehavior.ReturnAction.class,org.omg.uml13.wrap.behavioralelements.commonbehavior.WrapReturnAction.class);
    mapModelToWrapper.put(org.omg.uml13.behavioralelements.commonbehavior.LinkEnd.class,org.omg.uml13.wrap.behavioralelements.commonbehavior.WrapLinkEnd.class);
    mapModelToWrapper.put(org.omg.uml13.behavioralelements.commonbehavior.Reception.class,org.omg.uml13.wrap.behavioralelements.commonbehavior.WrapReception.class);
    mapModelToWrapper.put(org.omg.uml13.behavioralelements.commonbehavior.Argument.class,org.omg.uml13.wrap.behavioralelements.commonbehavior.WrapArgument.class);
    mapModelToWrapper.put(org.omg.uml13.behavioralelements.commonbehavior.ActionSequence.class,org.omg.uml13.wrap.behavioralelements.commonbehavior.WrapActionSequence.class);
    mapModelToWrapper.put(org.omg.uml13.behavioralelements.commonbehavior.SendAction.class,org.omg.uml13.wrap.behavioralelements.commonbehavior.WrapSendAction.class);
    mapModelToWrapper.put(org.omg.uml13.behavioralelements.commonbehavior.CallAction.class,org.omg.uml13.wrap.behavioralelements.commonbehavior.WrapCallAction.class);
    mapModelToWrapper.put(org.omg.uml13.behavioralelements.commonbehavior.DataValue.class,org.omg.uml13.wrap.behavioralelements.commonbehavior.WrapDataValue.class);
    mapModelToWrapper.put(org.omg.uml13.behavioralelements.commonbehavior.LinkObject.class,org.omg.uml13.wrap.behavioralelements.commonbehavior.WrapLinkObject.class);
    mapModelToWrapper.put(org.omg.uml13.behavioralelements.commonbehavior.Link.class,org.omg.uml13.wrap.behavioralelements.commonbehavior.WrapLink.class);
    mapModelToWrapper.put(org.omg.uml13.behavioralelements.commonbehavior.Object.class,org.omg.uml13.wrap.behavioralelements.commonbehavior.WrapObject.class);
    mapModelToWrapper.put(org.omg.uml13.behavioralelements.commonbehavior.AttributeLink.class,org.omg.uml13.wrap.behavioralelements.commonbehavior.WrapAttributeLink.class);
    mapModelToWrapper.put(org.omg.uml13.behavioralelements.commonbehavior.UninterpretedAction.class,org.omg.uml13.wrap.behavioralelements.commonbehavior.WrapUninterpretedAction.class);
    mapModelToWrapper.put(org.omg.uml13.behavioralelements.commonbehavior.DestroyAction.class,org.omg.uml13.wrap.behavioralelements.commonbehavior.WrapDestroyAction.class);
    mapModelToWrapper.put(org.omg.uml13.behavioralelements.commonbehavior.CreateAction.class,org.omg.uml13.wrap.behavioralelements.commonbehavior.WrapCreateAction.class);
    mapModelToWrapper.put(org.omg.uml13.behavioralelements.commonbehavior.Action.class,org.omg.uml13.wrap.behavioralelements.commonbehavior.WrapAction.class);
    mapModelToWrapper.put(org.omg.uml13.behavioralelements.commonbehavior.Signal.class,org.omg.uml13.wrap.behavioralelements.commonbehavior.WrapSignal.class);
    mapModelToWrapper.put(org.omg.uml13.behavioralelements.commonbehavior.Instance.class,org.omg.uml13.wrap.behavioralelements.commonbehavior.WrapInstance.class);
    mapModelToWrapper.put(org.omg.uml13.foundation.extensionmechanisms.TaggedValue.class,org.omg.uml13.wrap.foundation.extensionmechanisms.WrapTaggedValue.class);
    mapModelToWrapper.put(org.omg.uml13.foundation.extensionmechanisms.Stereotype.class,org.omg.uml13.wrap.foundation.extensionmechanisms.WrapStereotype.class);
    mapModelToWrapper.put(org.omg.uml13.foundation.core.TemplateParameter.class,org.omg.uml13.wrap.foundation.core.WrapTemplateParameter.class);
    mapModelToWrapper.put(org.omg.uml13.foundation.core.ElementResidence.class,org.omg.uml13.wrap.foundation.core.WrapElementResidence.class);
    mapModelToWrapper.put(org.omg.uml13.foundation.core.Flow.class,org.omg.uml13.wrap.foundation.core.WrapFlow.class);
    mapModelToWrapper.put(org.omg.uml13.foundation.core.Comment.class,org.omg.uml13.wrap.foundation.core.WrapComment.class);
    mapModelToWrapper.put(org.omg.uml13.foundation.core.Permission.class,org.omg.uml13.wrap.foundation.core.WrapPermission.class);
    mapModelToWrapper.put(org.omg.uml13.foundation.core.Node.class,org.omg.uml13.wrap.foundation.core.WrapNode.class);
    mapModelToWrapper.put(org.omg.uml13.foundation.core.Component.class,org.omg.uml13.wrap.foundation.core.WrapComponent.class);
    mapModelToWrapper.put(org.omg.uml13.foundation.core.Binding.class,org.omg.uml13.wrap.foundation.core.WrapBinding.class);
    mapModelToWrapper.put(org.omg.uml13.foundation.core.Usage.class,org.omg.uml13.wrap.foundation.core.WrapUsage.class);
    mapModelToWrapper.put(org.omg.uml13.foundation.core.PresentationElement.class,org.omg.uml13.wrap.foundation.core.WrapPresentationElement.class);
    mapModelToWrapper.put(org.omg.uml13.foundation.core.Abstraction.class,org.omg.uml13.wrap.foundation.core.WrapAbstraction.class);
    mapModelToWrapper.put(org.omg.uml13.foundation.core.Dependency.class,org.omg.uml13.wrap.foundation.core.WrapDependency.class);
    mapModelToWrapper.put(org.omg.uml13.foundation.core.AssociationClass.class,org.omg.uml13.wrap.foundation.core.WrapAssociationClass.class);
    mapModelToWrapper.put(org.omg.uml13.foundation.core.Generalization.class,org.omg.uml13.wrap.foundation.core.WrapGeneralization.class);
    mapModelToWrapper.put(org.omg.uml13.foundation.core.Method.class,org.omg.uml13.wrap.foundation.core.WrapMethod.class);
    mapModelToWrapper.put(org.omg.uml13.foundation.core.Parameter.class,org.omg.uml13.wrap.foundation.core.WrapParameter.class);
    mapModelToWrapper.put(org.omg.uml13.foundation.core.Operation.class,org.omg.uml13.wrap.foundation.core.WrapOperation.class);
    mapModelToWrapper.put(org.omg.uml13.foundation.core.BehavioralFeature.class,org.omg.uml13.wrap.foundation.core.WrapBehavioralFeature.class);
    mapModelToWrapper.put(org.omg.uml13.foundation.core.Attribute.class,org.omg.uml13.wrap.foundation.core.WrapAttribute.class);
    mapModelToWrapper.put(org.omg.uml13.foundation.core.UmlAssociation.class,org.omg.uml13.wrap.foundation.core.WrapUmlAssociation.class);
    mapModelToWrapper.put(org.omg.uml13.foundation.core.Relationship.class,org.omg.uml13.wrap.foundation.core.WrapRelationship.class);
    mapModelToWrapper.put(org.omg.uml13.foundation.core.Constraint.class,org.omg.uml13.wrap.foundation.core.WrapConstraint.class);
    mapModelToWrapper.put(org.omg.uml13.foundation.core.Interface.class,org.omg.uml13.wrap.foundation.core.WrapInterface.class);
    mapModelToWrapper.put(org.omg.uml13.foundation.core.AssociationEnd.class,org.omg.uml13.wrap.foundation.core.WrapAssociationEnd.class);
    mapModelToWrapper.put(org.omg.uml13.foundation.core.StructuralFeature.class,org.omg.uml13.wrap.foundation.core.WrapStructuralFeature.class);
    mapModelToWrapper.put(org.omg.uml13.foundation.core.Feature.class,org.omg.uml13.wrap.foundation.core.WrapFeature.class);
    mapModelToWrapper.put(org.omg.uml13.foundation.core.DataType.class,org.omg.uml13.wrap.foundation.core.WrapDataType.class);
    mapModelToWrapper.put(org.omg.uml13.foundation.core.UmlClass.class,org.omg.uml13.wrap.foundation.core.WrapUmlClass.class);
    mapModelToWrapper.put(org.omg.uml13.foundation.core.Classifier.class,org.omg.uml13.wrap.foundation.core.WrapClassifier.class);
    mapModelToWrapper.put(org.omg.uml13.foundation.core.Namespace.class,org.omg.uml13.wrap.foundation.core.WrapNamespace.class);
    mapModelToWrapper.put(org.omg.uml13.foundation.core.GeneralizableElement.class,org.omg.uml13.wrap.foundation.core.WrapGeneralizableElement.class);
    mapModelToWrapper.put(org.omg.uml13.foundation.core.ModelElement.class,org.omg.uml13.wrap.foundation.core.WrapModelElement.class);
    mapModelToWrapper.put(org.omg.uml13.foundation.core.Element.class,org.omg.uml13.wrap.foundation.core.WrapElement.class);
    mapModelToWrapper.put(org.omg.uml13.foundation.datatypes.ProcedureExpression.class,org.omg.uml13.wrap.foundation.datatypes.WrapProcedureExpression.class);
    mapModelToWrapper.put(org.omg.uml13.foundation.datatypes.MappingExpression.class,org.omg.uml13.wrap.foundation.datatypes.WrapMappingExpression.class);
    mapModelToWrapper.put(org.omg.uml13.foundation.datatypes.ArgListsExpression.class,org.omg.uml13.wrap.foundation.datatypes.WrapArgListsExpression.class);
    mapModelToWrapper.put(org.omg.uml13.foundation.datatypes.TypeExpression.class,org.omg.uml13.wrap.foundation.datatypes.WrapTypeExpression.class);
    mapModelToWrapper.put(org.omg.uml13.foundation.datatypes.IterationExpression.class,org.omg.uml13.wrap.foundation.datatypes.WrapIterationExpression.class);
    mapModelToWrapper.put(org.omg.uml13.foundation.datatypes.ActionExpression.class,org.omg.uml13.wrap.foundation.datatypes.WrapActionExpression.class);
    mapModelToWrapper.put(org.omg.uml13.foundation.datatypes.BooleanExpression.class,org.omg.uml13.wrap.foundation.datatypes.WrapBooleanExpression.class);
    mapModelToWrapper.put(org.omg.uml13.foundation.datatypes.TimeExpression.class,org.omg.uml13.wrap.foundation.datatypes.WrapTimeExpression.class);
    mapModelToWrapper.put(org.omg.uml13.foundation.datatypes.ObjectSetExpression.class,org.omg.uml13.wrap.foundation.datatypes.WrapObjectSetExpression.class);
    mapModelToWrapper.put(org.omg.uml13.foundation.datatypes.Expression.class,org.omg.uml13.wrap.foundation.datatypes.WrapExpression.class);
    mapModelToWrapper.put(org.omg.uml13.foundation.datatypes.MultiplicityRange.class,org.omg.uml13.wrap.foundation.datatypes.WrapMultiplicityRange.class);
    mapModelToWrapper.put(org.omg.uml13.foundation.datatypes.Multiplicity.class,org.omg.uml13.wrap.foundation.datatypes.WrapMultiplicity.class);
    mapModelToWrapper.put(org.omg.uml13.foundation.datatypes.VisibilityKind.class,org.omg.uml13.wrap.foundation.datatypes.WrapVisibilityKind.class);
    mapModelToWrapper.put(org.omg.uml13.foundation.datatypes.ScopeKind.class,org.omg.uml13.wrap.foundation.datatypes.WrapScopeKind.class);
    mapModelToWrapper.put(org.omg.uml13.foundation.datatypes.PseudostateKind.class,org.omg.uml13.wrap.foundation.datatypes.WrapPseudostateKind.class);
    mapModelToWrapper.put(org.omg.uml13.foundation.datatypes.ParameterDirectionKind.class,org.omg.uml13.wrap.foundation.datatypes.WrapParameterDirectionKind.class);
    mapModelToWrapper.put(org.omg.uml13.foundation.datatypes.OrderingKind.class,org.omg.uml13.wrap.foundation.datatypes.WrapOrderingKind.class);
    mapModelToWrapper.put(org.omg.uml13.foundation.datatypes.MessageDirectionKind.class,org.omg.uml13.wrap.foundation.datatypes.WrapMessageDirectionKind.class);
    mapModelToWrapper.put(org.omg.uml13.foundation.datatypes.ChangeableKind.class,org.omg.uml13.wrap.foundation.datatypes.WrapChangeableKind.class);
    mapModelToWrapper.put(org.omg.uml13.foundation.datatypes.CallConcurrencyKind.class,org.omg.uml13.wrap.foundation.datatypes.WrapCallConcurrencyKind.class);
    mapModelToWrapper.put(org.omg.uml13.foundation.datatypes.AggregationKind.class,org.omg.uml13.wrap.foundation.datatypes.WrapAggregationKind.class);
  }

  /**
   * The name of the file with translations, mappings.
   * Find the properties file in the class path this way allowing it to be modified
   * independently of this code. This .jar itself could contain a default version.
   */
  private static final String PROPERTIES_FILE_NAME = "uml13.properties";

  private static final Properties PROPERTIES = new Properties();
  static {
    InputStream out;

    out = Uml13WrapFactory.class.getClassLoader().getResourceAsStream( PROPERTIES_FILE_NAME );
    if ( out != null ) {
      try {
        try {
          PROPERTIES.load( out );
        } finally {
          out.close();
        }
      } catch (Exception ex) {
        LOGGER.log(Level.SEVERE, "Loading "+PROPERTIES_FILE_NAME+" caused:", ex);
      }
    }
  }


  /**
   * Internal cache of tagged values relative to a model elements. This cache maps a model element to a list
   * of tagged values.
   */
  private TaggedValuesCache taggedValuesCache = new TaggedValuesCache();

  /**
   * Internal cache of tagged values relative to a model elements. This cache maps a model element to a list
   * of tagged values.
   */
  private AssociationEndsCache associationEndsCache = new AssociationEndsCache();

  /**
   * Internal cache of tagged values relative to a model elements. This cache maps a model element to a list
   * of tagged values.
   */
  private StereotypeCache stereotypeCache = new StereotypeCache();

  /**
   * This flag indicates if the current instance of the model manager is search intensive, so chaches (only)
   * are used for lookup.<p>
   * INVALRIANT: If the instance is search intensive, then all elements being serached are referred in the caches
   */
  private boolean isIntensive = false;

  /**
   * This constructor is used only by the Factories Singleton
   * @see net.mdatools.modelant.core.wrap.Factories#registerSingleton(Class)
   */
  protected Uml13WrapFactory() {
    super();
  }

  /**
   * @see net.mdatools.modelant.core.wrap.Factory#getMappedClass(java.lang.Class)
   */
  protected Class<? extends Wrapper> getMappedClass(Class<?> original) {
    Class<? extends Wrapper> result;

    result = mapModelToWrapper.get( original );

    return result;
  }

  /**
   * @see net.mdatools.modelant.core.wrap.Factory#getProperty(java.lang.String)
   */
  public String getProperty(String name) {
    return PROPERTIES.getProperty( name );
  }


  /**
   * This is a specific UML 1.3 method used to cache and simulate associations that are not supported
   * by the UML 1.3 standard, like the association from any model element to its tagged values. Obviously
   * such associations are wide used and it is not effective to search them in backward direction.
   * The lookup methods below work in both modes<ul>
   * <li><b>intensive</b> mode, i.e. there was a caching established by calling this method
   * <li><b>standard</b> model when they calculate these associations following the standard.
   * </ul>
   * @param targetExtent where the model was loaded
   * @see #locateAssociationEndsOf(Classifier)
   * @see #locateStereotype(ModelElement)
   * @see #locateTaggedValues(ModelElement)
   */
  public void beginIntensive( RefPackage targetExtent ) {
    isIntensive = true;

    taggedValuesCache.load( WrapTaggedValue.getClassProxy( targetExtent ));
    associationEndsCache.load( WrapAssociationEnd.getClassProxy( targetExtent ));
    stereotypeCache.load( WrapStereotype.getClassProxy( targetExtent ));
  }

  /**
   * @param tag is a non-null tag just created
   */
  public void createdTaggedValue(TaggedValue tag) {
    taggedValuesCache.register( tag );
  }

  /**
   * @param tag is a non-null stereotype just created
   */
  public void createdStereotype(Stereotype tag) {
    stereotypeCache.register( tag );
  }


  /**
   * @param tag is a non-null stereotype just created
   */
  public void createdAssociationEnd(AssociationEnd tag) {
    associationEndsCache.register( tag );
  }


  /**
   * @param modelElement is a non-null model element.
   * @return a non-null collection of tagged values bound to that model element
   */
  public Collection<TaggedValue> locateTaggedValues(ModelElement modelElement) {
    Collection<TaggedValue> result;
    TaggedValue tag;
    Iterator sourceIterator;

    if ( isIntensive ) {
      result = taggedValuesCache.get( modelElement );

      if ( result == null ) {
        result = new ArrayList<TaggedValue>();
      }
    } else {
      result = new ArrayList<TaggedValue>();

      sourceIterator = WrapTaggedValue.getClassProxy( modelElement.refOutermostPackage() ).refAllOfClass().iterator();
      while ( sourceIterator.hasNext() ) {
        tag = (TaggedValue) sourceIterator.next();

        if ( tag.getModelElement() == modelElement ) {
          result.add( tag );
        }
      }
    }
    return result;
  }

  /**
   * @param target is a non-null model element
   * @return the stereotype bound to target or null
   */
  public Stereotype locateStereotype(ModelElement target) {
    Stereotype result = null;
    Iterator<Stereotype> it;
    Stereotype stereotype;
    Collection<Stereotype> allStereotypes ;

    if ( isIntensive ) {
      allStereotypes = stereotypeCache.get( target );

      if ( allStereotypes != null ) {
        it = allStereotypes.iterator();
        if ( it.hasNext() ) {
          result = it.next();
        }
      }
    } else {
      it = WrapStereotype.getClassProxy( target.refOutermostPackage() ).refAllOfClass().iterator();
      while ( it.hasNext() ) {
        stereotype = it.next();

        if ( stereotype.getExtendedElement().contains( target )) {
          result = stereotype;
        }
      }
    }
    return result;
  }


  /**
   * @param target is a non-null Classifier
   * @return a non-null collection of AssociationEnds whose type is the target classifier, i.e. the "starts"
   *         of any associations from this class[ifier] to any other class[ifier]
   */
  public Collection<AssociationEnd> locateAssociationEndsOf(Classifier target) {
    ArrayList<AssociationEnd> result;
    Iterator sourceIterator;
    AssociationEnd end;

    if ( isIntensive ) {
      result = (ArrayList<AssociationEnd>) associationEndsCache.get( target );
      if ( result == null ) {
        result = new ArrayList<AssociationEnd>();
      }
    } else {
      result = new ArrayList<AssociationEnd>();

      sourceIterator = WrapAssociationEnd.getClassProxy( target.refOutermostPackage() ).refAllOfClass().iterator();
      while ( sourceIterator.hasNext() && result.size() < 2 ) {
        end = (AssociationEnd) sourceIterator.next();

        if ( target.equals( end.getType() ) ) {
          result.add( end );
        }
      }
    }
    return result;
  }

  /**
   * @see net.mdatools.modelant.core.wrap.Factory#getMappedRootClasses()
   */
  protected Class<?>[] getMappedRootClasses() {
    return new Class[] {org.omg.uml13.foundation.core.Element.class,
                        org.omg.uml13.foundation.extensionmechanisms.TaggedValue.class,
                        org.omg.uml13.foundation.core.ElementResidence.class,
                        org.omg.uml13.foundation.core.TemplateParameter.class,
                        org.omg.uml13.foundation.datatypes.Expression.class,
                        org.omg.uml13.foundation.datatypes.Multiplicity.class,
                        org.omg.uml13.foundation.datatypes.MultiplicityRange.class,
                        org.omg.uml13.modelmanagement.ElementImport.class};
  }
}