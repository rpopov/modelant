/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.uml14.reverse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.jmi.reflect.RefObject;
import javax.jmi.reflect.RefPackage;

import org.omg.uml14.core.AssociationEnd;
import org.omg.uml14.core.Attribute;
import org.omg.uml14.core.Classifier;
import org.omg.uml14.core.DataType;
import org.omg.uml14.core.Dependency;
import org.omg.uml14.core.Generalization;
import org.omg.uml14.core.Interface;
import org.omg.uml14.core.ModelElement;
import org.omg.uml14.core.Namespace;
import org.omg.uml14.core.Operation;
import org.omg.uml14.core.Parameter;
import org.omg.uml14.core.Stereotype;
import org.omg.uml14.core.TaggedValue;
import org.omg.uml14.core.UmlAssociation;
import org.omg.uml14.core.UmlClass;
import org.omg.uml14.datatypes.AggregationKindEnum;
import org.omg.uml14.datatypes.Expression;
import org.omg.uml14.datatypes.Multiplicity;
import org.omg.uml14.datatypes.MultiplicityRange;
import org.omg.uml14.datatypes.ScopeKindEnum;
import org.omg.uml14.datatypes.VisibilityKindEnum;
import org.omg.uml14.modelmanagement.Model;
import org.omg.uml14.modelmanagement.ModelClass;
import org.omg.uml14.modelmanagement.UmlPackage;

import net.mdatools.modelant.core.api.name.ClassName;
import net.mdatools.modelant.core.api.name.Name;
import net.mdatools.modelant.core.api.name.PackageName;
import net.mdatools.modelant.core.name.ClassNameImpl;
import net.mdatools.modelant.core.name.NameImpl;
import net.mdatools.modelant.core.name.PackageNameImpl;

public class Uml14ModelFactory {

  /**
   * The stereotype that indicates the ELEMENT declarations in the XSD
   */
  public static final String STEREOTYPE_ELEMENT = "element";

  private static final PackageName foundation;
  private static final PackageName core;
  private static final PackageName modelManagement;
  private static final PackageName dataTypes;
  private static final PackageName extensionMechanisms;
  private static final PackageName bebehavioralElements;
  private static final PackageName commonBehavior;
  private static final ClassName taggedValue;
  private static final ClassName attribute;
  private static final ClassName parameter;
  private static final ClassName className;
  private static final ClassName interfaceName;
  private static final ClassName dependencyName;
  private static final ClassName exceptionName;
  private static final ClassName expression;
  private static final ClassName umlPackage;
  private static final ClassName stereotype;
  private static final ClassName modelName;
  private static final ClassName generalization;
  private static final ClassName operationName;
  private static final ClassName association;
  private static final ClassName associationEnd;
  private static final ClassName multiplicity;
  private static final ClassName multiplicityRange;
  private static final ClassName dataType;

  static {
    foundation = new PackageNameImpl("Foundation");
    core = new PackageNameImpl(foundation, "Core");
    dataTypes = new PackageNameImpl(foundation, "Data_Types");
    extensionMechanisms = new PackageNameImpl(foundation, "Extension_Mechanisms");

    modelManagement = new PackageNameImpl("Model_Management");

    bebehavioralElements = new PackageNameImpl("Behavioral_Elements");
    commonBehavior = new PackageNameImpl(bebehavioralElements,"Common_Behavior");

    taggedValue = new ClassNameImpl(extensionMechanisms, "TaggedValue");
    attribute = new ClassNameImpl(core, "Attribute");
    parameter = new ClassNameImpl(core, "Parameter");

    className = new ClassNameImpl(core, "Class");
    interfaceName = new ClassNameImpl(core, "Interface");
    exceptionName = new ClassNameImpl(commonBehavior, "Exception");
    operationName = new ClassNameImpl(core, "Operation");
    dependencyName= new ClassNameImpl(core, "Dependency");

    association = new ClassNameImpl(core, "Association");
    associationEnd = new ClassNameImpl(core, "AssociationEnd");
    dataType = new ClassNameImpl(core, "DataType");
    expression = new ClassNameImpl(dataTypes, "Expression");
    umlPackage = new ClassNameImpl(modelManagement, "Package");
    modelName = new ClassNameImpl(modelManagement, "Model");
    stereotype = new ClassNameImpl(extensionMechanisms, "Stereotype");
    generalization = new ClassNameImpl(core, "Generalization");

    multiplicity = new ClassNameImpl(dataTypes, "Multiplicity");
    multiplicityRange = new ClassNameImpl(dataTypes, "MultiplicityRange");
  }

  private final RefPackage extent;
  private final Model model;

  public Uml14ModelFactory(RefPackage extent) {
    this.extent = extent;
    this.model = constructModel(extent);
  }

  private Model constructModel(RefPackage extent) {
    Model result;
    ModelClass metaClass;
    Collection<Model> allModels;

    metaClass = (ModelClass) modelName.getMetaClass( extent );
    allModels = metaClass.refAllOfClass();
    if ( allModels.isEmpty() ) {
      result = (Model) metaClass.refCreateInstance( null );
    } else {
      result = allModels.iterator().next();
    }
    return result;
  }

  public void setModelName(String modelName) {
    model.setName( modelName );
  }

  public UmlAssociation constructAssociation(Classifier thisClass,
                                             String thisRole,
                                             int thisEndUpper,
                                             boolean isComposition,
                                             boolean isThisNavigable,
                                             Classifier otherClass,
                                             String otherRole,
                                             int otherEndUpper,
                                             Namespace namespace,
                                             String documentation) {
    UmlAssociation result;
    AssociationEnd thisEnd;

    result = (UmlAssociation) association.getMetaClass( extent ).refCreateInstance( null );
    result.setNamespace( namespace );
    result.setName( "" );
    result.setVisibility( VisibilityKindEnum.VK_PUBLIC );

    // this end (the class with the associative attribute)
    thisEnd = constructAssociationEnd( thisClass, thisRole, thisEndUpper, result );

    if ( isComposition ) {
      thisEnd.setAggregation( AggregationKindEnum.AK_COMPOSITE );
    }

    constructAssociationEnd( otherClass, otherRole, otherEndUpper, result );

    return result;
  }

  private AssociationEnd constructAssociationEnd(Classifier thisClass,
                                                 String thisRole,
                                                 int thisEndUpper,
                                                 UmlAssociation result) {
    AssociationEnd thisEnd;

    thisEnd = (AssociationEnd) associationEnd.getMetaClass( extent ).refCreateInstance( null );
    thisEnd.setName( thisRole );
    thisEnd.setParticipant( thisClass );
    thisEnd.setVisibility( VisibilityKindEnum.VK_PUBLIC );
    thisEnd.setAssociation( result );
    thisEnd.setMultiplicity( constructMultiplicity( thisEndUpper ) );

    return thisEnd;
  }

  public Multiplicity constructMultiplicity(int otherEndUpper) {
    Multiplicity result;
    MultiplicityRange range;

    result = (Multiplicity) multiplicity.getMetaClass( extent ).refCreateInstance( null );

    range = (MultiplicityRange) multiplicityRange.getMetaClass( extent ).refCreateInstance( null );
    range.setUpper( otherEndUpper );
    result.getRange().add(range);

    return result;
  }

  public UmlClass constructClass(String simpleTypeName) {
    return constructClass( model, simpleTypeName );
  }

  public UmlClass constructClass(Namespace namespace, String simpleTypeName) {
    UmlClass result;
    Object lookedUp;

    lookedUp = locateLocalModelElement( namespace, simpleTypeName );
    if ( lookedUp == null ) { // none found - build it
      result = (UmlClass) className.getMetaClass( extent ).refCreateInstance( null );

      result.setName( simpleTypeName );
      result.setVisibility( VisibilityKindEnum.VK_PUBLIC );
      result.setNamespace( namespace );

    } else if ( lookedUp instanceof UmlClass ) {
      result = (UmlClass) lookedUp;

    } else {
      throw new IllegalArgumentException("Expected to lookup a UmlClass instance for the name: "+namespace.getName()+"."+simpleTypeName+" isntead of "+lookedUp);
    }
    return result;
  }

  /**
   * Construct the data type with the name
   * @param dataTypeName is the non-null type name
   * @return the data type identified or class identified
   */
  public DataType constructDataType(String dataTypeName) {
    DataType result;

    try {
      result = (DataType) locateModelElement(dataTypeName);

    } catch (IllegalArgumentException ex) { // data type not found
      result = (DataType) dataType.getMetaClass( extent ).refCreateInstance( null );
      result.setName( dataTypeName );
      result.setNamespace( model );
      result.setVisibility( VisibilityKindEnum.VK_PUBLIC );

    }
    return result;
  }

  public Attribute constructAttribute(String name) {
    Attribute result;

    result = (Attribute) attribute.getMetaClass( extent ).refCreateInstance( null );

    result.setName( name );
    result.setVisibility( VisibilityKindEnum.VK_PUBLIC );
    result.setOwnerScope( ScopeKindEnum.SK_INSTANCE );

    return result;
  }

  public Parameter constructParameter(String name) {
    Parameter result;

    result = (Parameter) parameter.getMetaClass( extent ).refCreateInstance( null );

    result.setName( name );
    result.setVisibility( VisibilityKindEnum.VK_PUBLIC );

    return result;
  }

  public Expression constructExpression(String body) {
    Expression result;

    result = (Expression) expression.getMetaClass( extent ).refCreateInstance( null );
    result.setBody( body );

    return result;
  }

  /**
   * This method creates the public interface in the package provided
   *
   * @param umlPackage the containing package
   * @param name is the name of the interface to create
   * @return the interface built
   */
  public Interface constructInterface(Namespace umlPackage, String name) {
    Interface result = (Interface) locateLocalModelElement( umlPackage, name );

    if ( result == null ) { // none found - build it
      result = (Interface) interfaceName.getMetaClass( extent ).refCreateInstance(null);
      result.setName( name );
      result.setVisibility( VisibilityKindEnum.VK_PUBLIC );
      result.setNamespace( umlPackage );
    }
    return result;
  }

  /**
   * Instantiate a public exception with the qualified name provided, but the Exceptions might be messed with the regular classes so they
   * are registered as DataTypes at the model level instead of UnlExceprion-s when they are also
   * reverse engineered.
   * @param qualifiedName of the exception to be created
   * @return the newly created public exception
   */
  public Classifier constructException(String qualifiedName) {
    Classifier result = (Classifier) locateLocalModelElement( model, qualifiedName );

    if ( result == null ) { // still not created, thus it is not included
      result = (Classifier) exceptionName.getMetaClass( extent ).refCreateInstance( null );
      result.setName( qualifiedName );
      result.setNamespace( model );
      result.setVisibility( VisibilityKindEnum.VK_PUBLIC );
    }
    return result;
  }

  public Operation constructOperation(String name) {
    Operation result;

    result = (Operation) operationName.getMetaClass( extent ).refCreateInstance( null );
    result.setName( name );
    result.setVisibility( VisibilityKindEnum.VK_PUBLIC );
    result.setOwnerScope( ScopeKindEnum.SK_INSTANCE );

    return result;
  }

  public void constructGeneralization(Classifier subClass, Classifier superClass) {
    Generalization result;

    result = (Generalization) generalization.getMetaClass( extent ).refCreateInstance( null );

    result.setNamespace( superClass.getNamespace() );
    result.setParent( superClass );
    result.setChild( subClass );

  }

  public Dependency constructDependency(ModelElement client, ModelElement supplier, String name) {
    Dependency result;

    result = (Dependency) dependencyName.getMetaClass( extent ).refCreateInstance( null );
    result.getSupplier().add( supplier );
    result.getClient().add( client );
    result.setNamespace( model );
    result.setSpecification( false );
    result.setName( name );
    result.setVisibility( VisibilityKindEnum.VK_PUBLIC );

    return result;
  }

  public UmlPackage constructPackage(String name) {
    return constructPackage( NameImpl.parseQualifiedName(name) );
  }

  /**
   * @param name not null name of the UML package to lookup/construct
   * @return not null UML package with the name provided from the current model
   */
  public UmlPackage constructPackage(Name name) {
    UmlPackage result;
    UmlPackage namespace;

    if ( name.getOwner() == null ) {
    	namespace = model;
    } else {
    	namespace = constructPackage( name.getOwner() );
    }

    result = (UmlPackage) locateLocalModelElement( namespace, name.getName() );
    if ( result == null ) { // still not created, thus it is not included
      result = (UmlPackage) umlPackage.getMetaClass( extent ).refCreateInstance( null );
      result.setName( name.getName() );
      result.setNamespace( namespace );
    }
    return result;
  }

  public void constructStereotypeElement(ModelElement extendedElement) {
    constructStereotype( extendedElement, STEREOTYPE_ELEMENT );
  }

  public void constructStereotype(ModelElement extendedElement, String name) {
    Stereotype result;

    result = (Stereotype) locateLocalModelElement( model, name );
    if ( result == null ) { // still not created, thus it is not included
      result = (Stereotype) stereotype.getMetaClass( extent ).refCreateInstance( null );

      result.setName( name );
      result.setNamespace( model );
      result.setVisibility( VisibilityKindEnum.VK_PUBLIC );
    }
    extendedElement.getStereotype().add(result);
  }

  /**
	 * @param tagName a non-null tag name
	 * @return the tag with the name bound to this model element
	 */
	public TaggedValue getTaggedValue(ModelElement modelElement, String tagName) {
	  TaggedValue result = null;
	  TaggedValue tag;
	  Iterator<TaggedValue> sourceIterator;

	  sourceIterator = allTaggedValues(modelElement).iterator();
	  while ( result == null && sourceIterator.hasNext() ) {
	    tag = sourceIterator.next();

	    if ( tagName.equals( tag.getName() ) ) {
	      result = tag;
	    }
	  }
	  return result;
	}

	public void constructTagDocumentation(ModelElement otherClass, String contents) {
    TaggedValue documentation;

    documentation = getTaggedValue( otherClass, net.mdatools.modelant.uml14.metamodel.Convention.TAG_VALUE_DOCUMENTATION);
    if ( documentation == null ) {
      constructTag( otherClass, net.mdatools.modelant.uml14.metamodel.Convention.TAG_VALUE_DOCUMENTATION, contents );

    } else if ( documentation.getDataValue() == null ) {
      documentation.getDataValue().add(contents);

    } else if (!documentation.getDataValue().contains( contents )) {
      documentation.getDataValue().add(documentation.getDataValue()+"\n\r"+contents);
    }
  }

  /**
   * @param modelElement is a non-null model element.
   * @return a non-null collection of tagged values bound to that model element
   */
  private Collection<TaggedValue> allTaggedValues(ModelElement modelElement) {
    Collection<TaggedValue> result;
    TaggedValue tag;
    Iterator sourceIterator;

    result = new ArrayList<TaggedValue>();

    sourceIterator = taggedValue.getMetaClass( extent ).refAllOfClass().iterator();
    while ( sourceIterator.hasNext() ) {
      tag = (TaggedValue) sourceIterator.next();

      if ( tag.getModelElement() == modelElement ) {
        result.add( tag );
      }
    }
    return result;
  }

  public void constructTag(ModelElement modelElement, String name, String value) {
    TaggedValue result;

    result = (TaggedValue) taggedValue.getMetaClass( extent ).refCreateInstance( null );

    result.setName( name );
    result.getDataValue().add( value );
    result.setModelElement( modelElement );
  }

  public void constructTagFieldPrecision(ModelElement intoClass, int precision) {
    constructTag(intoClass,
                 net.mdatools.modelant.uml14.metamodel.Convention.TAG_VALUE_DATA_TYPE_PRECISION,
                 "" + precision );
  }

  public void constructTagSize(ModelElement intoClass, int parseInt) {
    constructTag(intoClass,
                 net.mdatools.modelant.uml14.metamodel.Convention.TAG_VALUE_DATA_LENGTH,
                 "" + parseInt );
  }

  public void constructTagPersistent(UmlClass intoClass) {
    constructTag(intoClass,
                 net.mdatools.modelant.uml14.metamodel.Convention.TAG_VALUE_PERSISTENCE,
                 net.mdatools.modelant.uml14.metamodel.Convention.TAG_VALUE_PERSISTENCE_VALUE );
  }

  public void constructTagPrimaryKey(Attribute attribute, int order) {
    constructTag(attribute,
                 net.mdatools.modelant.uml14.metamodel.Convention.TAG_VALUE_PRIMARY_KEY,
                 Integer.toString( order ));
  }

  /**
   * Locate the model element using its qualified name in this model object. The empty
   * (string) name is recognized as the name of the default package - the model itself.
   *
   * @param qualifiedName is a non-empty string with the qualified name of a model element
   * @return the non-null model element with the name specified.
   */
  public RefObject locateModelElement(String qualifiedName) throws IllegalArgumentException {
    return locateModelElement( model, NameImpl.parseQualifiedName(qualifiedName));
  }

  /**
   * Locate the model element using its qualified Name in this model object.
   * @param name is a non-empty qualified name of a model element
   *
   * @return the model element with the name specified.
   */
  public RefObject locateModelElement(Name<?> name) throws IllegalArgumentException {
  	return locateModelElement(model, name);
  }


  /**
	 * Locate the model element using its Name in this model object.
	 * @param namespace not null namespace to look for the name
	 * @param name not null element name
	 * @return the non-null model element with the name specified.
	 * @throws IllegalArgumentException when not found
	 */
	public RefObject locateModelElement(Namespace namespace, Name<?> name) throws IllegalArgumentException {
	  RefObject result;
	  RefObject resultNamespace;

	  if ( name.getOwner() != null ) {
	    resultNamespace = locateModelElement(namespace, name.getOwner());
	  } else {
	    resultNamespace = namespace;
	  }

	  // TODO: Reconsider the locateRelativeModelElement to throw exception when not applicable or found nothing (N/A again)

	  if ( !(resultNamespace instanceof Namespace) ) {
	    throw new IllegalArgumentException("Looking up "+name.getOwner()+" reached a non-namespace element to lookup "+name.getName());
	  }
	  result = locateLocalModelElement((Namespace) resultNamespace, name.getName());

	  if (result == null ) {
	    throw new IllegalArgumentException("Not found "+name+" in "+name.getOwner()+" namespace");
	  }

	  return result;
	}

	/**
	 * This method locates the model element with name provided in <b>elementName </b> within the UML
	 * namespace <b>outerPackage </b>.
	 *
	 * @param elementName is a non-null name
	 * @return null if no package found, otherwise the package with the name specified
	 */
	public ModelElement locateLocalModelElement(Namespace namespace, String elementName) {
	  ModelElement result = null;
	  ModelElement ownedElement;
	  Iterator ownedElementsIterator;

	  ownedElementsIterator = namespace.getOwnedElement().iterator();
	  while ( result == null && ownedElementsIterator.hasNext() ) {
	    ownedElement = (ModelElement) ownedElementsIterator.next();

	    if ( elementName.equals( ownedElement.getName() ) ) {
	      result = ownedElement;
	    }
	  }
	  return result;
	}
}