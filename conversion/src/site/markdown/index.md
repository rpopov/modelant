Conversion 
==========

The '''conversion''' modules treat the specific model conversions between different metamodels, effectively translating the models in one modeling language to other modeling languages.

<!-- MACRO{toc} -->

Conversion of UML 1.3 models to UML 1.4 models 
==============================================

The implemented convesion mechanisms are:

* Conversion of UML 1.3 models to UML 1.4

Maven Plugin: Convert UML 1.3 to UML 1.4
----------------------------------------

The goal '''convertUml13ToUml14''' of the '''modelant.conversion.maven.plugin''' converts UML 1.3 models to UML 1.4 as XMI 1.1/1.2 files.

```
<build>
  <plugins>
    <plugin>
      <groupId>net.mdatools</groupId>
      <artifactId>modelant.conversion.maven.plugin</artifactId>
      <version>${revision}</version>
      <executions>
        <execution>
          <phase>compile</phase>
          <goals>
            <goal>convertUml13ToUml14</goal>
          </goals>
          <configuration>
            <sourceModel>...</sourceModel>
            <targetModel>...</targetModel>
          </configuration>
        </execution>
      </executions>
    </plugin>
  </plugins>
</build>
```
where:

  * **sourceModel** the path to the source UML 1.3 model in XMI 1.1/1.2 format
  * **targetModel** the path to the target UML 1.4 model in XMI 1.2 format to create
    
See also [Operation: Convert UML 1.3 to UML 1.4](modelant.conversion.model/index.html)

Mapping UML 1.3 to UML 1.4 models
---------------------------------

With the help of the [model comparison plugin](../modelant.core.maven.plugin/index.html) the following mapping of UML 1.3 to UML 1.4 models was identified. Please note that **there is no formal definition** of such transformation provided by [OMG](http://www.omg.org) or anyone else, but [the tracking changes in UML 1.4 formal definition](https://www.omg.org/cgi-bin/doc?formal/01-09-67), so it was identified using the tools provided bby modelant and proven in other projects.

  * the deleted in UML 1.4 Reference '''Foundation::Extension_Mechanisms::Stereotype::extendedElement''' is compensated by the added in UML 1.4 Reference '''Core::ModelElement::stereotype''' which actually reverses the navigability of the ModelElement-to-Stereotype association '''A_stereotype_extendedElement''' from Stereotype->ModelElement in UML 1.3 to ModelElement->Stereotype in UML 1.4
    * the deleted references will not be used in the conversion from UML 1.3 to UML 1.4, thus it is not mapped
    * the added reference will not be used in the converion
    * the ModelElement and Stereotype classes will be associated when converting the '''A_stereotype_extendedElement''' association and binding it to the associated instances
  * the added in UML 1.4 Reference '''Core::ModelElement::taggedValue''' actually makes the association '''A_modelElement_taggedValue''' ModelElement<-TaggedValue in UML 1.3 bi-directional in UML 1.4.
  * the deleted in UML 1.4 reference '''Foundation::Core::Operation::method''' converts the bi-directional association in UML 1.3 '''A_specification_method''' to uni-directional association Method->Operation.
  * the deleted in UML 1.4 reference '''Foundation::Core::ModelElement::supplierDependency''' makes the bi-directional in UML 1.3 association '''A_supplier_supplierDependency''' (ModelElement (as supplier)-> Dependency (as supplierDependency) to uni-directional Dependency -> ModelElement (as dependency).
  * the deleted in UML 1.4 reference '''Foundation::Core::ModelElement::presentation''' makes the bi-directional association '''A_presentation_subject''' from ModelElement (as subject) to PresentationElement (as presentaton) into uni-directional association PresentationElement -> ModelElement (as subject) 
  * the deleted in UML 1.4 reference '''Foundation::Core::ModelElement::elementResidence''' makes the bi-directional association '''A_resident_elementResidence''' from ModelElement (as resident) to ElementResidence (as elementResidence) unidirectional ElementResidence to ModelElement (as resident).
  * the deleted in UML 1.4 reference '''Foundation::Core::GeneralizableElement::specialization''' makes the bi-directional association '''A_parent_specialization''' from GeneralizableElement (as parent) to Generalization (as specialization) to unidirectional Generalization (as specialization) -> GerealizableElement (as parent).
  * the deleted in UML 1.4 reference '''Behavioral_Elements::Use_Cases::ExtensionPoint::extend''' makes the bi-directional association '''A_extensionPoint_extend''' from Extend (as extend) to ExtensionPoint (as extensionPoint) to uni-directional association Extend -> ExtensionPoint (as extensionPoint).
  * the deleted in UML 1.4 reference '''Behavioral_Elements::State_Machines::Transition::state''' makes the bi-directional association '''A_state_internalTransition''' State (state)-Transition(internalTransition) unidirectional  State -> Transition(internalTransition).
  *  the deleted in UML 1.4 reference '''Behavioral_Elements::State_Machines::Event::transition''' makes the bi-directional association '''A_transition_trigger''': Transition(transition)-Even(trigger) unidirectional Transition -> Even(trigger)
  *  the deleted in UML 1.4 reference '''Behavioral_Elements::State_Machines::Event::state''' makes the bi-directional association '''A_state_deferrableEvent''': State(state)-Event(deferrableEvent) unidirectional State->Event(deferrableEvent) 
  *  the deleted in UML 1.4 reference '''Behavioral_Elements::Common_Behavior::Signal::sendAction''' makes the bi-directional association '''A_signal_sendAction''': SendAction(sendAction)-Signal(signal) unidirectional SendAction->Signal(signal)
  *  the deleted in UML 1.4 reference '''Behavioral_Elements::Common_Behavior::Signal::reception''' makes the bi-directional association '''A_signal_reception''': Signal(signal)-Reception(reception) unidirectional Reception->Signal(signal)
  *  the deleted in UML 1.4 reference '''Behavioral_Elements::Common_Behavior::Signal::context''' makes the bi-directional association '''A_context_raisedSignal''': BehavioralFeature(context)-Signal(raisedSignal) unidirectional BehavioralFeature->Signal(raisedSignal)
  *  the deleted in UML 1.4 reference '''Behavioral_Elements::Common_Behavior::Link::stimulus''' makes the bi-directional association '''A_stimulus_communicationLink''' unidirectional 
  *  the deleted in UML 1.4 reference '''Behavioral_Elements::Common_Behavior::Instance::attributeLink''' makes the bi-directional association '''A_attributeLink_attribute''' unidirectional 
  *  the deleted in UML 1.4 reference '''Behavioral_Elements::Common_Behavior::Instance::attributeLink''' makes the bi-directional association '''A_attributeLink_value''': AttributeLink(attributeLink)-Instance(value) unidirectional AttributeLink->Instance(value)
  *  the deleted in UML 1.4 reference '''Behavioral_Elements::Common_Behavior::Action::stimulus''' makes the bi-directional association '''A_dispatchAction_stimulus''': Action(dispatchAction)-Stimulus(stimulus) unidirectional Stimulus->Action(dispatchAction)
  * The UML 1.3 association '''A_behavior_context''' becomes uni-directional 
  * The UML 1.3 association '''A_modelElement_elementImport''' is replaced with the UML 1.4 association '''A_importedElement_elementImport''' 
  * The UML 1.3 association '''A_collaboration_representedClassifier''' is replaced with the UML 1.4 association '''A_representedClassifier_collaboration''' 
  * The UML 1.3 association A_message2_sender is replaced with the UML 1.4 asociation '''A_message_sender''' 
  * The UML 1.3 association A_message4_activator is replaced with the UML 1.4 association '''A_message_activator''' 
  * The UML 1.3 association '''A_receiver_message'''1 is replaced with the UML 1.4 association '''A_receiver_message''' 
  * The UML 1.3 association '''A_argument_stimulus'''1 is replaced with the UML 1.4 association '''A_argument_stimulus''' as unidirectional
  * The UML 1.3 association '''A_receiver_stimulus'''2 is replaced with the UML 1.4 association '''A_receiver_stimulus''' 
  * The UML 1.3 association A_stimulus3_sender is replaced with the UML 1.4 association '''A_stimulus_sender''' 
  * The UML 1.3 association A_state1_entry is replaced with the UML 1.4 association '''A_state_entry''' 
  * The UML 1.3 association A_state2_exit is replaced with the UML 1.4 association '''A_state_exit''' 
  * The UML 1.3 association A_state3_doActivity is replaced with the UML 1.4 association '''A_state_doActivity''' 
  * The UML 1.3 association '''A_base_extend'''2 is replaced with the UML 1.4 association '''A_base_extender''' 
  * The UML 1.3 association A_include2_base is replaced with the UML 1.4 association '''A_include_base''' 
  * The UML 1.3 association '''A_subMachineState_submachine''' is replaced with the UML 1.4 association '''A_submachineState_submachine''' 
  * The UML 1.3 association '''A_include_addition''' is replaced with the UML 1.4 association '''A_includer_addition''' 
  * The UML 1.3 association '''A_modelElement_templateParameter''' is replaced with the UML 1.4 association '''A_template_templateParameter''' 
  * The UML 1.3 association A_modelElement2_templateParameter2 is replaced with the UML 1.4 association '''A_modelElement_templateParameter''' 
  * The UML 1.3 association '''A_defaultElement_templateParameter'''3 is replaced with the UML 1.4 association '''A_template_templateParameter''' 
  * The UML 1.3 association '''A_predecessor_message'''3 is replaced with the UML 1.4 association '''A_predecessor_successor''' 
  * The UML 1.3 association '''A_implementationLocation_residentElement''' is replaced with the UML 1.4 association '''A_container_residentElement''' 
  * The UML 1.3 association '''A_deploymentLocation_resident''' is replaced with the UML 1.4 association '''A_deploymentLocation_deployedComponent''' 
  * The UML 1.3 association '''A_parameter_type''' is replaced with the UML 1.4 association '''A_typedParameter_type''' 
  * The UML 1.3 association '''A_structuralFeature_type''' is replaced with the UML 1.4 association '''A_typedFeature_type''' 
  * The UML 1.3 association A_constrainedElement2_stereotypeConstraint is replaced with the UML 1.4 association '''A_constrainedStereotype_stereotypeConstraint''' 
  * The UML 1.3 association '''A_participant_specification''' is replaced with the UML 1.4 association '''A_specifiedEnd_specification''' 
  * The UML 1.3 association '''A_type_associationEnd''' is replaced with the UML 1.4 association '''A_participant_association''' 
  * The UML 1.3 association '''A_requiredTag_stereotype''' is replaced with the UML 1.4 association '''A_definedTag_owner''' + '''A_modelElement_taggedValue''' 
  * The UML 1.3 reference '''Foundation::Core::ModelElement::binding''' is moved to the UML 1.4 TemplateArgument class. Its handing in the conversion from UMl 1.3 to UML 1.4 is:
    * map the "binding" association of the ModelElement to null, thus suppressing its explicit assigning
    * leave the mapping of '''A_binding_argument''' association handle it
  * The UML 1.3 Reference '''Foundation::Core::Classifier::participant''' is removed.
  * The UML 1.3 TaggedValue.tag attribute is represented in UML 1.4 as ModelElement.name 
  * The UML 1.3 TaggedValue.value (uninterpreted) attribute is represented in UML 1.4 as TaggedValue.dataValue: String[]
  * The UML 1.3 TaggedValue object is represented in UML 1.4 TaggedValue, associated through '''A_type_typedValue''' to a TagDefinition instance.
  * '''The UML 1.3 references, no matter mapped or not, should not be mapped to UML 1.4 as they duplicate the functionality already provided by the associations. Thus, any UML 1.3 references will be mapped to null''':
    * Behavioral_Elements,Collaborations,ClassifierRole,message1
    * Behavioral_Elements,Collaborations,ClassifierRole,message2
    * Behavioral_Elements,Collaborations,Collaboration,representedClassifier
    * Behavioral_Elements,Collaborations,Message,activator
    * Behavioral_Elements,Collaborations,Message,sender
    * Behavioral_Elements,Collaborations,Message,receiver
    * Behavioral_Elements,Collaborations,Message,predecessor
    * Behavioral_Elements,Collaborations,Message,message3
    * Behavioral_Elements,Collaborations,Message,message4
    * Behavioral_Elements,Common_Behavior,Signal,reception
    * Behavioral_Elements,Common_Behavior,Signal,context
    * Behavioral_Elements,Common_Behavior,Signal,sendAction
    * Behavioral_Elements,Common_Behavior,Stimulus,argument
    * Behavioral_Elements,Common_Behavior,Stimulus,sender
    * Behavioral_Elements,Common_Behavior,Stimulus,receiver
    * Behavioral_Elements,State_Machines,StateMachine,subMachineState
    * Behavioral_Elements,State_Machines,State,doActivity
    * Behavioral_Elements,State_Machines,State,entry
    * Behavioral_Elements,State_Machines,State,exit
    * Behavioral_Elements,State_Machines,SubmachineState,submachine
    * Behavioral_Elements,Use_Cases,Extend,base
    * Behavioral_Elements,Use_Cases,Include,addition
    * Behavioral_Elements,Use_Cases,Include,base
    * Behavioral_Elements,Use_Cases,UseCase,extend2
    * Behavioral_Elements,Use_Cases,UseCase,include
    * Behavioral_Elements,Use_Cases,UseCase,include2
    * Foundation,Core,AssociationEnd,type
    * Foundation,Core,AssociationEnd,specification
    * Foundation,Core,Component,deploymentLocation
    * Foundation,Core,Component,residentElement
    * Foundation,Core,ElementResidence,implementationLocation
    * Foundation,Core,ModelElement,supplierDependency
    * Foundation,Core,ModelElement,presentation
    * Foundation,Core,ModelElement,templateParameter3
    * Foundation,Core,ModelElement,binding
    * Foundation,Core,ModelElement,elementResidence
    * Foundation,Core,ModelElement,templateParameter
    * Foundation,Core,ModelElement,templateParameter2
    * Foundation,Core,Node,resident
    * Foundation,Core,Operation,method
    * Foundation,Core,Parameter,type
    * Foundation,Core,StructuralFeature,type
    * Foundation,Core,TemplateParameter,defaultElement
    * Foundation,Core,TemplateParameter,modelElement2
    * Foundation,Extension_Mechanisms,Stereotype,requiredTag
    * Foundation,Extension_Mechanisms,Stereotype,extendedElement
    * Foundation,Extension_Mechanisms,Stereotype,stereotypeConstraint
  * PseudostateKind values mapping:
    * pk_branch -> pk_choice
    * pk_final -> pk_join
  * OrderingKind values mapping:
    * ok_sorted -> ok_ordered
  * VisibilityKind values mapping:
    * no UML 1.3 value for UML 1.4 vk_package

[See also](http://www.lysator.liu.se/xenofarm/argouml/work/argouml/www/documentation/umlsupport/uml_changes.html).    
    

Modules
-------

* [Conversion API](modelant.conversion.model/index.html)
* [Conversion Maven plubins](modelant.conversion.maven/index.html)
  