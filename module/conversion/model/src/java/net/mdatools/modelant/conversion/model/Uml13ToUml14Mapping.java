/*
 * Copyright (c) i:FAO AG 2018. All Rights Reserved.
 *
 * This SOURCE CODE FILE, which has been provided by i:FAO AG as part
 * of a product of i:FAO AG for use ONLY by licensed users of the product,
 * includes CONFIDENTIAL and PROPRIETARY information.
 *
 * Created on 22.04.2018 г.
 */
package net.mdatools.modelant.conversion.model;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import javax.jmi.reflect.RefClass;
import javax.jmi.reflect.RefObject;
import javax.jmi.reflect.RefPackage;

import net.mdatools.modelant.core.api.Procedure;
import net.mdatools.modelant.core.api.model.ConstructProcedure;
import net.mdatools.modelant.core.api.model.NameMapping;
import net.mdatools.modelant.core.api.name.ClassName;
import net.mdatools.modelant.core.api.name.PackageName;
import net.mdatools.modelant.core.name.AssociationNameImpl;
import net.mdatools.modelant.core.name.ClassNameImpl;
import net.mdatools.modelant.core.name.EnumValueNameImpl;
import net.mdatools.modelant.core.name.FieldNameImpl;
import net.mdatools.modelant.core.name.PackageNameImpl;
import net.mdatools.modelant.core.operation.model.transform.RenamingMapping;

/**
 * The mapping rules to convert a UML 1.3 model to a UML 1.4 model.
 *
 * @author Rusi Popov
 */
public final class Uml13ToUml14Mapping extends RenamingMapping {

  static final ClassName UML14_TAG_DEFINITION = new ClassNameImpl( new PackageNameImpl("Core"),
                                                                   "TagDefinition");

  {
    final PackageName foundation;
    final PackageName core;
    final PackageName behavioralElements;
    final PackageName modelManagement;
    final PackageName collaborations;
    final PackageName statemachines;
    final PackageName useCases;
    final PackageName dataTypes;
    final PackageName extensionMechanisms;
    final PackageName commonBehavior;
    final PackageName activityGraphs;
    final PackageName collaborations14;
    final PackageName statemachines14;
    final PackageName useCases14;
    final PackageName core14;
    final PackageName modelManagement14;
    final PackageName commonBehavior14;
    final PackageNameImpl dataTypes14;

    final ClassNameImpl taggedValue;
    final ClassNameImpl pseudoStateKind;
    final ClassNameImpl pseudoStateKind14;

    foundation = new PackageNameImpl("Foundation");
    core = new PackageNameImpl(foundation, "Core");
    extensionMechanisms = new PackageNameImpl(foundation, "Extension_Mechanisms");

    behavioralElements = new PackageNameImpl("Behavioral_Elements");
    commonBehavior = new PackageNameImpl(behavioralElements, "Common_Behavior");
    collaborations = new PackageNameImpl(behavioralElements, "Collaborations");
    useCases = new PackageNameImpl(behavioralElements, "Use_Cases");
    activityGraphs = new PackageNameImpl(behavioralElements, "Activity_Graphs");
    statemachines = new PackageNameImpl(behavioralElements, "State_Machines");

    modelManagement = new PackageNameImpl("Model_Management");
    modelManagement14 = new PackageNameImpl("Model_Management");
    set(modelManagement, modelManagement14);

    dataTypes = new PackageNameImpl(foundation, "Data_Types");
    dataTypes14 = new PackageNameImpl("Data_Types");
    set(dataTypes, dataTypes14);

    core14 = new PackageNameImpl("Core");
    set(core, core14);
    set(extensionMechanisms, core14);

    commonBehavior14 = new PackageNameImpl("Common_Behavior");
    set(commonBehavior, commonBehavior14);

    useCases14 = new PackageNameImpl("Use_Cases");
    set(useCases, useCases14);

    statemachines14 = new PackageNameImpl("State_Machines");
    set(statemachines,  statemachines14);

    collaborations14 = new PackageNameImpl("Collaborations");
    set(collaborations, collaborations14);
    set(activityGraphs, new PackageNameImpl("Activity_Graphs"));

    set(new AssociationNameImpl(modelManagement, "A_modelElement_elementImport"),
        new AssociationNameImpl(modelManagement14, "A_importedElement_elementImport"));

    setBackward(new AssociationNameImpl(collaborations, "A_collaboration_representedClassifier"),
                new AssociationNameImpl(collaborations14, "A_representedClassifier_collaboration"));

    set(new AssociationNameImpl(collaborations, "A_message2_sender"),
        new AssociationNameImpl(collaborations14, "A_message_sender"));

    set(new AssociationNameImpl(collaborations, "A_message4_activator"),
        new AssociationNameImpl(collaborations14, "A_message_activator"));

    set(new AssociationNameImpl(collaborations, "A_receiver_message1"),
        new AssociationNameImpl(collaborations14, "A_receiver_message"));

    set(new AssociationNameImpl(commonBehavior, "A_argument_stimulus1"),
        new AssociationNameImpl(commonBehavior14, "A_argument_stimulus"));

    set(new AssociationNameImpl(commonBehavior, "A_receiver_stimulus2"),
        new AssociationNameImpl(commonBehavior14, "A_receiver_stimulus"));

    set(new AssociationNameImpl(commonBehavior, "A_stimulus3_sender"),
        new AssociationNameImpl(commonBehavior14, "A_stimulus_sender"));

    set(new AssociationNameImpl(collaborations, "A_predecessor_message3"),
        new AssociationNameImpl(collaborations14, "A_predecessor_successor"));

    set(new AssociationNameImpl(statemachines, "A_state1_entry"),
        new AssociationNameImpl(statemachines14, "A_state_entry"));

    set(new AssociationNameImpl(statemachines, "A_state2_exit"),
        new AssociationNameImpl(statemachines14, "A_state_exit"));

    set(new AssociationNameImpl(statemachines, "A_state3_doActivity"),
        new AssociationNameImpl(statemachines14, "A_state_doActivity"));

    set(new AssociationNameImpl(statemachines, "A_subMachineState_submachine"),
        new AssociationNameImpl(statemachines14, "A_submachineState_submachine"));

    set(new AssociationNameImpl(useCases, "A_base_extend2"),
        new AssociationNameImpl(useCases14, "A_base_extender"));

    set(new AssociationNameImpl(useCases, "A_include2_base"),
        new AssociationNameImpl(useCases14, "A_include_base"));

    set(new AssociationNameImpl(useCases, "A_include_addition"),
        new AssociationNameImpl(useCases14, "A_includer_addition"));

    set(new AssociationNameImpl(core, "A_modelElement_templateParameter"),
        new AssociationNameImpl(core14, "A_template_templateParameter"));

    setBackward(new AssociationNameImpl(core, "A_modelElement2_templateParameter2"),
                new AssociationNameImpl(core14, "A_template_templateParameter"));

    set(new AssociationNameImpl(core, "A_defaultElement_templateParameter3"),
        new AssociationNameImpl(core14, "A_template_templateParameter"));

    set(new AssociationNameImpl(core, "A_implementationLocation_residentElement"), //OK
        new AssociationNameImpl(core14, "A_container_residentElement"));

    set(new AssociationNameImpl(core, "A_deploymentLocation_resident"),
        new AssociationNameImpl(core14, "A_deploymentLocation_deployedComponent"));

    set(new AssociationNameImpl(core, "A_parameter_type"),
        new AssociationNameImpl(core14, "A_typedParameter_type"));

    set(new AssociationNameImpl(core, "A_structuralFeature_type"),
        new AssociationNameImpl(core14, "A_typedFeature_type"));

    set(new AssociationNameImpl(extensionMechanisms, "A_constrainedElement2_stereotypeConstraint"),
        new AssociationNameImpl(core14, "A_constrainedStereotype_stereotypeConstraint"));

    set(new AssociationNameImpl(core, "A_participant_specification"),
        new AssociationNameImpl(core14, "A_specifiedEnd_specification"));

    set(new AssociationNameImpl(core, "A_type_associationEnd"),
        new AssociationNameImpl(core14, "A_participant_association"));

    unset(new AssociationNameImpl(extensionMechanisms, "A_requiredTag_stereotype"));

    // renaming references (associations at the side of the classes)
    set(new FieldNameImpl(new ClassNameImpl(modelManagement,"ElementImport"),"modelElement"),
        new FieldNameImpl("importedElement"));

    // deleted/not handled feature names, because they are handled through associations
    // Actually these mappings are not needed, as they are not accessible as attributes.
    // Provided here for completeness and testability.
    unset(new FieldNameImpl(new ClassNameImpl(collaborations,"ClassifierRole"),"message1"));
    unset(new FieldNameImpl(new ClassNameImpl(collaborations,"ClassifierRole"),"message2"));
    unset(new FieldNameImpl(new ClassNameImpl(collaborations,"Collaboration"),"representedClassifier"));
    unset(new FieldNameImpl(new ClassNameImpl(collaborations,"Message"),"activator"));
    unset(new FieldNameImpl(new ClassNameImpl(collaborations,"Message"),"message3"));
    unset(new FieldNameImpl(new ClassNameImpl(collaborations,"Message"),"message4"));
    unset(new FieldNameImpl(new ClassNameImpl(collaborations,"Message"),"predecessor"));
    unset(new FieldNameImpl(new ClassNameImpl(collaborations,"Message"),"receiver"));
    unset(new FieldNameImpl(new ClassNameImpl(collaborations,"Message"),"sender"));
    unset(new FieldNameImpl(new ClassNameImpl(commonBehavior,"Action"),"stimulus"));
    unset(new FieldNameImpl(new ClassNameImpl(commonBehavior,"Instance"),"attributeLink"));
    unset(new FieldNameImpl(new ClassNameImpl(commonBehavior,"Link"),"stimulus"));
    unset(new FieldNameImpl(new ClassNameImpl(commonBehavior,"Signal"),"context"));
    unset(new FieldNameImpl(new ClassNameImpl(commonBehavior,"Signal"),"context"));
    unset(new FieldNameImpl(new ClassNameImpl(commonBehavior,"Signal"),"reception"));
    unset(new FieldNameImpl(new ClassNameImpl(commonBehavior,"Signal"),"reception"));
    unset(new FieldNameImpl(new ClassNameImpl(commonBehavior,"Signal"),"sendAction"));
    unset(new FieldNameImpl(new ClassNameImpl(commonBehavior,"Signal"),"sendAction"));
    unset(new FieldNameImpl(new ClassNameImpl(commonBehavior,"Stimulus"),"argument"));
    unset(new FieldNameImpl(new ClassNameImpl(commonBehavior,"Stimulus"),"receiver"));
    unset(new FieldNameImpl(new ClassNameImpl(commonBehavior,"Stimulus"),"sender"));
    unset(new FieldNameImpl(new ClassNameImpl(core,"AssociationEnd"),"specification"));
    unset(new FieldNameImpl(new ClassNameImpl(core,"AssociationEnd"),"type"));
    unset(new FieldNameImpl(new ClassNameImpl(core,"Classifier"),"participant"));
    unset(new FieldNameImpl(new ClassNameImpl(core,"Component"),"deploymentLocation"));
    unset(new FieldNameImpl(new ClassNameImpl(core,"Component"),"residentElement"));
    unset(new FieldNameImpl(new ClassNameImpl(core,"ElementResidence"),"implementationLocation"));
    unset(new FieldNameImpl(new ClassNameImpl(core,"GeneralizableElement"),"specialization"));
    unset(new FieldNameImpl(new ClassNameImpl(core,"ModelElement"),"binding"));
    unset(new FieldNameImpl(new ClassNameImpl(core,"ModelElement"),"binding"));
    unset(new FieldNameImpl(new ClassNameImpl(core,"ModelElement"),"elementResidence"));
    unset(new FieldNameImpl(new ClassNameImpl(core,"ModelElement"),"elementResidence"));
    unset(new FieldNameImpl(new ClassNameImpl(core,"ModelElement"),"presentation"));
    unset(new FieldNameImpl(new ClassNameImpl(core,"ModelElement"),"presentation"));
    unset(new FieldNameImpl(new ClassNameImpl(core,"ModelElement"),"supplierDependency"));
    unset(new FieldNameImpl(new ClassNameImpl(core,"ModelElement"),"supplierDependency"));
    unset(new FieldNameImpl(new ClassNameImpl(core,"ModelElement"),"templateParameter"));
    unset(new FieldNameImpl(new ClassNameImpl(core,"ModelElement"),"templateParameter2"));
    unset(new FieldNameImpl(new ClassNameImpl(core,"ModelElement"),"templateParameter3"));
    unset(new FieldNameImpl(new ClassNameImpl(core,"Node"),"resident"));
    unset(new FieldNameImpl(new ClassNameImpl(core,"Operation"),"method"));
    unset(new FieldNameImpl(new ClassNameImpl(core,"Operation"),"method"));
    unset(new FieldNameImpl(new ClassNameImpl(core,"Parameter"),"type"));
    unset(new FieldNameImpl(new ClassNameImpl(core,"StructuralFeature"),"type"));
    unset(new FieldNameImpl(new ClassNameImpl(core,"TemplateParameter"),"modelElement"));
    unset(new FieldNameImpl(new ClassNameImpl(core,"TemplateParameter"),"defaultElement"));
    unset(new FieldNameImpl(new ClassNameImpl(core,"TemplateParameter"),"modelElement2"));
    unset(new FieldNameImpl(new ClassNameImpl(extensionMechanisms,"Stereotype"),"extendedElement"));
    unset(new FieldNameImpl(new ClassNameImpl(extensionMechanisms,"Stereotype"),"extendedElement"));
    unset(new FieldNameImpl(new ClassNameImpl(extensionMechanisms,"Stereotype"),"requiredTag"));
    unset(new FieldNameImpl(new ClassNameImpl(extensionMechanisms,"Stereotype"),"stereotypeConstraint"));
    unset(new FieldNameImpl(new ClassNameImpl(statemachines,"Event"),"state"));
    unset(new FieldNameImpl(new ClassNameImpl(statemachines,"Event"),"transition"));
    unset(new FieldNameImpl(new ClassNameImpl(statemachines,"State"),"doActivity"));
    unset(new FieldNameImpl(new ClassNameImpl(statemachines,"State"),"entry"));
    unset(new FieldNameImpl(new ClassNameImpl(statemachines,"State"),"exit"));
    unset(new FieldNameImpl(new ClassNameImpl(statemachines,"StateMachine"),"subMachineState"));
    unset(new FieldNameImpl(new ClassNameImpl(statemachines,"SubmachineState"),"submachine"));
    unset(new FieldNameImpl(new ClassNameImpl(statemachines,"Transition"),"state"));
    unset(new FieldNameImpl(new ClassNameImpl(useCases,"Extend"),"base"));
    unset(new FieldNameImpl(new ClassNameImpl(useCases,"ExtensionPoint"),"extend"));
    unset(new FieldNameImpl(new ClassNameImpl(useCases,"Include"),"addition"));
    unset(new FieldNameImpl(new ClassNameImpl(useCases,"Include"),"base"));
    unset(new FieldNameImpl(new ClassNameImpl(useCases,"UseCase"),"extend2"));
    unset(new FieldNameImpl(new ClassNameImpl(useCases,"UseCase"),"include"));
    unset(new FieldNameImpl(new ClassNameImpl(useCases,"UseCase"),"include2"));

    // enumerations map
    pseudoStateKind = new ClassNameImpl(dataTypes,"PseudostateKind");
    pseudoStateKind14 = new ClassNameImpl(dataTypes14,"PseudostateKind");
    set(new EnumValueNameImpl(pseudoStateKind,"pk_branch"), new EnumValueNameImpl(pseudoStateKind14,"pk_choice"));
    set(new EnumValueNameImpl(pseudoStateKind,"pk_final"), new EnumValueNameImpl(pseudoStateKind14,"pk_join"));

    set(new EnumValueNameImpl(new ClassNameImpl(dataTypes,"OrderingKind"),"ok_sorted"),
        new EnumValueNameImpl(new ClassNameImpl(dataTypes14,"OrderingKind"),"ok_ordered"));

    // construct TaggedValues with the corresponding TagDefinitions of String type with the same name
    taggedValue = new ClassNameImpl(extensionMechanisms,"TaggedValue");
    set(taggedValue,
        new ConstructProcedure<RefObject>() {
          public Procedure<RefObject> construct(RefPackage sourceExtent, RefPackage targetExtent, Map<RefObject, RefObject> objectsMap, NameMapping valueMapping) {
            RefClass targetTaggedValueRefClass;
            RefClass targetTagDefinitionRefClass;

            targetTaggedValueRefClass = new ClassNameImpl(core14,"TaggedValue").getMetaClass( targetExtent);
            targetTagDefinitionRefClass = UML14_TAG_DEFINITION.getMetaClass( targetExtent );

            return new Procedure<RefObject>() {
              public void execute(RefObject source) {
                RefObject uml14TagDefinition;
                String tagName;
                RefObject target;
                Object value;
                Collection<Object> targetValueList;

                target = targetTaggedValueRefClass.refCreateInstance( null );

                // lookup the tag definition with source.tag as name
                tagName = (String) source.refGetValue( "tag" );

                uml14TagDefinition = lookupTagDefinition( tagName );

                target.refSetValue( "name", tagName);
                target.refSetValue( "type", uml14TagDefinition );
                target.refSetValue( "name", tagName );

                // copy the value too, if any
                value = source.refGetValue( "value" );
                if ( value != null ) {
                  targetValueList = (Collection<Object>) target.refGetValue( "dataValue" );

                  assert targetValueList != null: "Expected a collection value, where to add the new value";
                  targetValueList.add(value);
                }

                objectsMap.put( source, target );
              }

              private RefObject lookupTagDefinition(String tagName) {
                RefObject result;
                Iterator<RefObject> definitionIterator;
                RefObject definition;

                definitionIterator = targetTagDefinitionRefClass.refAllOfClass().iterator();

                result = null;
                while ( result == null && definitionIterator.hasNext() ) {
                  definition = definitionIterator.next();

                  if (tagName.equals(definition.refGetValue("name"))) {
                    result = definition;
                  }
                }

                if (result==null) { // not created yet, construct it
                  result = targetTagDefinitionRefClass.refCreateInstance( null );
                  result.refSetValue( "tagType", tagName );
                  result.refSetValue( "name", tagName );
                }
                return result;
              }
            };
          }
        });
    // do not copy the attributes of TaggedValue as they are already copied
    unset(new FieldNameImpl(taggedValue,"tag"));
    unset(new FieldNameImpl(taggedValue,"value"));
  }
}