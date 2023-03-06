/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.uml14.reverse;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jmi.reflect.RefPackage;

import org.omg.uml14.core.Attribute;
import org.omg.uml14.core.Classifier;
import org.omg.uml14.core.ModelElement;
import org.omg.uml14.core.UmlAssociation;
import org.omg.uml14.datatypes.ChangeableKindEnum;
import org.omg.uml14.datatypes.ScopeKindEnum;
import org.omg.uml14.datatypes.VisibilityKindEnum;
import org.omg.uml14.modelmanagement.UmlPackage;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.sun.xml.xsom.XSAttContainer;
import com.sun.xml.xsom.XSAttGroupDecl;
import com.sun.xml.xsom.XSAttributeDecl;
import com.sun.xml.xsom.XSAttributeUse;
import com.sun.xml.xsom.XSComplexType;
import com.sun.xml.xsom.XSComponent;
import com.sun.xml.xsom.XSContentType;
import com.sun.xml.xsom.XSDeclaration;
import com.sun.xml.xsom.XSElementDecl;
import com.sun.xml.xsom.XSFacet;
import com.sun.xml.xsom.XSListSimpleType;
import com.sun.xml.xsom.XSModelGroup;
import com.sun.xml.xsom.XSParticle;
import com.sun.xml.xsom.XSRestrictionSimpleType;
import com.sun.xml.xsom.XSSchema;
import com.sun.xml.xsom.XSSchemaSet;
import com.sun.xml.xsom.XSSimpleType;
import com.sun.xml.xsom.XSTerm;
import com.sun.xml.xsom.XSType;
import com.sun.xml.xsom.XSUnionSimpleType;
import com.sun.xml.xsom.parser.AnnotationContext;
import com.sun.xml.xsom.parser.AnnotationParser;
import com.sun.xml.xsom.parser.AnnotationParserFactory;
import com.sun.xml.xsom.parser.XSOMParser;

import net.mdatools.modelant.core.api.Function;
import net.mdatools.modelant.core.util.key.GenerateUniqueName;
import net.mdatools.modelant.repository.api.ModelFactory;
import net.mdatools.modelant.repository.api.ModelRepository;

/**
 * Reverse engineer a XML schema and storing the results as UML
 * 1.3 objects. The model produced is in fact a Platform Specific Model, which might need additional
 * processing and tuning.
 * <p>
 * Conventions for the model produced: <ul>
 * <li> The model elements (classes, association names) that represent elements in the output XML
 *      are marked with &lt;&lt;element&gt;&gt; stereotype
 * <li> For representation purposes local types (UML classes) could be introduced for XSD groups, unions,
 *      local / inlined types. All of them are marked with &lt;&lt;local type&gt;&gt; stereotype
 * <li>The column types are converted to DataType instances named: &lt;type
 *     name&gt;[_&lt;column size&gt;[_&lt;column precision&gt;]]. Additionally as tagged values named
 *     TAG_VALUE_DATA_TYPE_SIZE and TAG_VALUE_DATA_TYPE_PRECISION these values are bound to the concrete
 *     data type.
 * <li>The TAG_VALUE_DATA_TYPE_PRECISION tagged value is optional. When not provided, the precision
 *     should be treated as 0
 * <li>Any comments found while reverse engineering the XSD are bound as 'documentation' tagged
 *     values. These tagged values are compatible with the Rose's approach to documentation. They are
 *     optional.
 * </ul>
 * @author Rusi Popov (popovr@mdatools.net)
 */
public class ReverseXsdOperation implements Function<File,RefPackage> {

  static final Logger LOGGER = Logger.getLogger( ReverseXsdOperation.class.getName() );

  /**
   * The <code>ANONYMOUS_CLASS_NAME_PREFIX</code> holds the common name
   * of the anonymous types.
   */
  private static final String ANONYMOUS_CLASS_NAME_PREFIX = "AnonymousType";

  /**
   * The type name suffix to use when inferring the name of an originally anonymous type
   */
  private static final String INFERRED_CLASS_NAME_SUFFIX = "Type";
  private static final String INFERRED_GROUP_NAME_SUFFIX = "Group";
  private static final String INFERRED_UNION_NAME_SUFFIX = "United";
  private static final String EMPTY_ENUM_NAME_PREFIX = "EMPTY";
  private static final String ENUM_NAME_PREFIX = "ENUM_";

  private final ModelRepository modelRepository;

  /**
   * Maps XSD types to corresponding UML classes
   */
  private final Map<XSComponent,Classifier> typeToClassMap = new IdentityHashMap<XSComponent, Classifier>();

  /**
   * Generates unique class names for anonymous XSD types
   */
  private final GenerateUniqueName uniqueNamesGenerator = new GenerateUniqueName();

  /**
   * The common type AnyType, string provided by the schema implementation
   */
  private XSType anyType;
  private XSType stringType;
  private Uml14ModelFactory factory;

  /**
   * @param modelRepository not null
   */
  public ReverseXsdOperation(ModelRepository modelRepository) {
    assert modelRepository != null : "Expected non-nulll model repository";

    this.modelRepository = modelRepository;
  }

  public RefPackage execute(File schemaFile) throws IllegalArgumentException {
    RefPackage result;
    Iterator<XSSchema> schemasIterator;
    XSOMParser parser;
    XSSchemaSet schemaSet;
    XSSchema schema;
    ModelFactory modelFactory;

    modelFactory = modelRepository.loadMetamodel("UML13");
    result = modelFactory.instantiate();

    factory = new Uml14ModelFactory( result);

    parser = constructSchemaParser();
    try {
      parser.parse( schemaFile );
      schemaSet = parser.getResult();

    } catch (SAXException|IOException ex) {
      throw new IllegalArgumentException(ex);
    }

    if ( schemaSet != null ) {
      anyType = schemaSet.getAnyType();
      stringType = schemaSet.getType( "http://www.w3.org/2001/XMLSchema", "string" );

      schemasIterator = schemaSet.iterateSchema();
      while ( schemasIterator.hasNext() ) {
        schema = schemasIterator.next();
        describeSchema( schema );
      }
    } else {
      throw new IllegalArgumentException( "No valid/complete schemas parsed");
    }
    return result;
  }

  private XSOMParser constructSchemaParser() {
    XSOMParser parser;

    parser = new XSOMParser();
    parser.setErrorHandler( new ParserErrorListerner() );
    parser.setEntityResolver( new SimpleEntityResolver() );
    parser.setAnnotationParser( new SimpleAnnotationParserFactory() );

    return parser;
  }

  /**
   * Describes the contents of the schema in UML
   *
   * @param schema
   */
  private void describeSchema(XSSchema schema) {
    XSType type;
    XSElementDecl elementDecl;
    Iterator<XSType> typeIterator;
    Iterator<XSElementDecl> elementIterator;

    declareTargetNamespacePackage( schema );

    // declare/register all GLOBAL primitive & complex types as classes
    typeIterator = schema.iterateTypes();
    while ( typeIterator.hasNext() ) {
      type = typeIterator.next();
      declareType( type );
    }

    // describe the contents and associations all GLOBAL primitive & complex types as classes
    typeIterator = schema.iterateTypes();
    while ( typeIterator.hasNext() ) {
      type = typeIterator.next();

      defineType( type, locateType( type ) );
    }

    // describe all top-level elements as objects
    elementIterator = schema.iterateElementDecls();
    while ( elementIterator.hasNext() ) {
      elementDecl = elementIterator.next();
      createElement( elementDecl );
    }
  }

  /**
   * Declare the target namespace as a package and collect the top-level comments in it
   * @param schema not null
   */
  private void declareTargetNamespacePackage(XSSchema schema) {
    UmlPackage namespace;

    namespace = factory.constructPackage( formatPackageName( schema.getTargetNamespace() ) );
    assignDocumentation( namespace, schema );
  }

  /**
   * @param type
   * @return the UML class declared for the type. Returns NULL if the type
   *         has not been declared.
   */
  private Classifier locateType(XSComponent type) {
    Classifier result;

    result = typeToClassMap.get( type );
    return result;
  }

  /**
   * Instantiate the UML class that represents the XSD type.
   * Bind it with the representation of its XSD type.
   * When representing a anonymous class, a unique name is generated.
   * PRE-CONDITION:<ul>
   * <li> the type has not been declared yet
   * </ul>
   * @param type is not null XSD type to represent
   */
  private Classifier declareType(XSDeclaration type) {
    Classifier result;
    UmlPackage namespace;
    String simpleTypeName;

    namespace = factory.constructPackage( formatPackageName( type.getTargetNamespace() ) );

    simpleTypeName = formatClassName( type.getName() );

    LOGGER.log( Level.FINE, "Declare type: {0}", simpleTypeName);

    result = factory.constructClass( namespace, simpleTypeName );
    bindType( type, result );

    return result;
  }

  /**
   * Declare the type as a local class within the class provided.
   * This method is meaningful when processing anonymous element types
   * @param type is a non-null class where locateType( type ) == null
   * @param defaultName is the non-null name of the type to create if the type has no name
   * @param umlClass is the class where the local class is declared in the XSD
   */
  private Classifier declareLocalType(XSType type, String defaultName, Classifier umlClass) {
    Classifier result;
    UmlPackage namespace;
    String simpleTypeName;

    if ( type.getName() != null && !type.getName().isEmpty() ) {
      defaultName = type.getName();
    }
    // NOTE: even though the class could be local, we create it as top-level one
    //       because Rose could not import its attributes otherwise.
    simpleTypeName = formatClassName( defaultName );

    LOGGER.log( Level.FINE, "Declare local type: {0}", simpleTypeName);

    namespace = factory.constructPackage( formatPackageName( type.getTargetNamespace() ));
    result = factory.constructClass( namespace, simpleTypeName );
    result.setAbstract( true );
    bindType( type, result );

    factory.constructTagDocumentation( result, "An anonymous type in the XSD" );

    return result;
  }

  /**
   * This method binds the XML type to the UML class provided. Note that
   * in some  cases of types declaration like
   * <complexType>
   *   <simpleContents>
   *     <restriction base="...">
   *     ...
   *     </...>
   *   </...>
   * </...>
   * The complex type and the simple contents (type) represent actually the
   * same UML class.
   * @param type is non-null type
   * @param result is non-null UML class to bind
   */
  private void bindType(XSComponent type, Classifier result) {
    typeToClassMap.put( type, result );

    assignDocumentation( result, type );
  }

  /**
   * Describe/convert to model the details of the XSD type provided
   * PRE-CONDITION:<ul>
   * <li> The type was declared, i.e. the corresponding UML class has
   *      been assigned to the class
   * </ul>
   * @param type
   * @param intoClass is the non-null UML class to describe the XSD type into
   */
  private Classifier defineType(XSType type, Classifier intoClass) {

    LOGGER.log( Level.FINE, "Define type: {0}", type);

    if ( type.isComplexType() ) {
      processComplexType( type.asComplexType(), intoClass );
    } else {
      processSimpleType( type.asSimpleType(), intoClass );
    }
    return intoClass;
  }

  /**
   * Describes a simple as a class
   *
   * @param type is the non-null simple type to describe
   * @param intoClass is not-null
   * @see com.sun.xml.xsom.visitor.XSContentTypeVisitor#simpleType(com.sun.xml.xsom.XSSimpleType)
   */
  private void processSimpleType(XSSimpleType type, Classifier intoClass) {

    if ( type.isRestriction() ) {
      assignSuperclass( type, intoClass );
    }

    // this splitting of the processing is needed because of a bug in XSOM,
    // that wrongly identifies the supertype when XSD type is just a simple
    // content of a complex type. Thus, we cannot leave processSimpleContent()
    // to define the superclass - see the calling methods
    processSimpleContent( type, intoClass );
  }

  /**
   * Process this as the contents of a simple class. Note that it does not bind
   * to the superclass. The reason is that when type is a simple contents of
   * a complex type defined the superclass is just wrongly identified. Therefore,
   * the processing of the simple class is split into two: binding to the superclass
   * (here it is defined correctly) and processing of its contents.
   * @param type
   * @param intoClass is the non-null UML class to desccribe this XSD type into
   */
  private void processSimpleContent(XSSimpleType type, Classifier intoClass) {
    Classifier superClass;
    Classifier unitedClass;

    Iterator<XSFacet> facetsItrator;
    Iterator<XSSimpleType> unitedTypesItrator;
    XSFacet facet;
    XSSimpleType unitedType;
    UmlAssociation assoc;

    // describe restrictions
    if ( type.isRestriction() ) {
      LOGGER.log( Level.FINE, "Restriction");

      // describe the restrictions as vegas known tagged values
      facetsItrator = ((XSRestrictionSimpleType) type).iterateDeclaredFacets();
      while ( facetsItrator.hasNext() ) {
        facet = facetsItrator.next();

        LOGGER.log( Level.FINE, "  Facet: {0}",facet);

        if ( facet.getName().equals( XSFacet.FACET_LENGTH ) || facet.getName().equals( XSFacet.FACET_MAXLENGTH )
             || facet.getName().equals( XSFacet.FACET_TOTALDIGITS ) ) {

          factory.constructTagSize( intoClass, Integer.parseInt( facet.getValue().toString() ) );

        } else if ( facet.getName().equals( XSFacet.FACET_FRACTIONDIGITS ) ) {
          factory.constructTagFieldPrecision( intoClass, Integer.parseInt( facet.getValue().toString() ) );

        } else if ( facet.getName().equals( XSFacet.FACET_ENUMERATION ) ) {
          // create a constant for the enumerated value instead of a tag value
          createConstant( facet, intoClass );

        } else {
          factory.constructTag( intoClass, facet.getName(), facet.getValue().toString() );
        }
      }
    } else if ( type.isList() ) { // a list of simple type instances
      LOGGER.log( Level.FINE, "List");

      // make it a subclass of the AnyType
      superClass = locateType( anyType );
      factory.constructGeneralization( intoClass, superClass );

      // bind this (List type) to the item class
      unitedType = ((XSListSimpleType) type).getItemType();
      unitedClass = locateType( unitedType );
      if ( unitedClass == null ) { // a list of local class
        unitedClass = declareLocalType( unitedType,
                                        intoClass.getName()+INFERRED_CLASS_NAME_SUFFIX,
                                        intoClass);
        defineType( unitedType, unitedClass );
      }
      assoc = factory.constructAssociation( intoClass, "", 1, true, false,
                                            unitedClass, "", net.mdatools.modelant.uml14.metamodel.Convention.UNLIMITED,
                                            intoClass.getNamespace(),
                                            "A list of "+ unitedType.getName() );
      assignDocumentation( assoc, unitedType );

      factory.constructTagDocumentation(intoClass, "This XSD type represents a list of " + unitedType.getName() );

    } else if ( type.isUnion() ) { // this is a UNION of simple types, so this type

      LOGGER.log( Level.FINE, "Union");

      // make it a subclass of the the types it unites, thus allowing to be
      // implementation of any of them

      // describe all listed XSD types as superclasses of this
      unitedTypesItrator = ((XSUnionSimpleType) type).iterator();
      while ( unitedTypesItrator.hasNext() ) {
        unitedType = unitedTypesItrator.next();

        unitedClass = locateType( unitedType );
        if ( unitedClass == null ) { // a list of local class
          unitedClass = declareLocalType( unitedType,
                                          type.getName()+INFERRED_UNION_NAME_SUFFIX,
                                          intoClass );
          defineType( unitedType, locateType( type ) );
        }
        factory.constructGeneralization( intoClass, unitedClass );
      }
      factory.constructTagDocumentation(intoClass, "This XSD type represents an XSD union of simple types inheriting from all of them" );

    } else { // impossible, provided just for readability
      LOGGER.log( Level.SEVERE, "Unkown type: {0}", type);
    }
  }

  /**
   * This method converts a complex XSD type to a UML class
   * @param type is non-null complex type definition
   * @param intoClass is non-null uml class to describe the XSD type into
   */
  private void processComplexType(XSComplexType type, Classifier intoClass) {
    XSContentType contentType;

    intoClass.setAbstract( type.isAbstract() );

    assignSuperclass( type, intoClass );

    // describe type as an XSAttContainer
    processAttributeContainer( type, intoClass );

    // describe the specific contents of this type
    // NOTE: type.getExplicitContent() should be used but it actually misses some specific definitions
    //       in in-lined types
    contentType = type.getExplicitContent();
    if ( contentType == null ) {
      contentType = type.getContentType();
    }
    if ( contentType instanceof XSSimpleType ) {
      // handle the restriction/extension as THIS type (the superclass identified here is the correct one)

      processSimpleContent( contentType.asSimpleType(), intoClass );

    } else if ( contentType instanceof XSParticle ) {

      processParticleAsComplexTypeContent( contentType.asParticle(), intoClass );

    }
  }

  /**
   * @param type
   * @param intoClass
   */
  private void assignSuperclass(XSType type, Classifier intoClass) {
    Classifier superclass;

    if ( type != anyType ) { // a real superclass exists
      superclass = locateType( type.getBaseType() );

      if ( superclass == null ) { // a subclass of a local class
        // complete the declaration of this with the declaration of the superclass,
        // no need of a separate superclass
        defineType( type.getBaseType(), intoClass );

      } else { // a public class - subclass it
        factory.constructGeneralization( intoClass, superclass );
      }
    }
  }

  /**
   * Describes type as a collection of attributes into umlClass
   * @param type
   * @param umlClass
   */
  private void processAttributeContainer(XSAttContainer type, Classifier umlClass) {
    Attribute attribute;
    Iterator<? extends XSAttributeUse> declaredAttributeUsesIterator;
    XSAttributeUse attributeUse;
    Iterator<? extends XSAttGroupDecl> groupsIterator;
    XSAttGroupDecl group;

    // process the directly declared attributes
    declaredAttributeUsesIterator = type.iterateDeclaredAttributeUses();
    while ( declaredAttributeUsesIterator.hasNext() ) {
      attributeUse = declaredAttributeUsesIterator.next();

      attribute = createAttribute( attributeUse.getDecl(), umlClass );

      // collect the comments for the type when it is an attributes group, because
      // it is in-lined
      if ( !(type instanceof XSComplexType) ) {
        assignDocumentation( attribute, type );
      }
    }

    // add the attributes used in referred groups
    groupsIterator = type.iterateAttGroups();
    while ( groupsIterator.hasNext() ) {
      group = groupsIterator.next();
      processAttributeContainer( group, umlClass );
    }
    // type.getAttributeWildcard();
  }

  /**
   * Processes a particle as a definition of elements of a complex type.
   * The associations/compositions without role name at the part side represent
   * model groups, the associations/compositions with a role represent
   * XML elements.
   * @param particle is the non-null particle that describes the class' contents
   * @param intoClass is not-null class to put the elements in
   */
  private void processParticleAsComplexTypeContent(XSParticle particle, Classifier intoClass) {
    Classifier otherClass;
    UmlAssociation assoc;
    UmlPackage namespace;
    int otherEndUpper;
    XSTerm term;
    XSElementDecl elementDecl;
    String modelGroupName;

    // calculate multiplicity of the other end
    if ( particle.isRepeated() ) {
      otherEndUpper = particle.getMaxOccurs().subtract(particle.getMinOccurs()).intValue();
    } else {
      otherEndUpper = 1;
    }
    term = particle.getTerm();
    if ( term.isElementDecl() ) { // just a single element's declaration. The element is represented as an association to the element's type
      elementDecl = (XSElementDecl)term;

      otherClass = locateType( elementDecl.getType() );
      if ( otherClass == null ) { // a local class still not defined
        otherClass = declareLocalType( elementDecl.getType(),
                                       elementDecl.getName()+INFERRED_CLASS_NAME_SUFFIX,
                                       intoClass );
        defineType( elementDecl.getType(), otherClass );

        factory.constructTagDocumentation( otherClass, "A type inlined in an <xsd:element> declaration" );
      }

      // instantiate a composition
      assoc = factory.constructAssociation( intoClass, "", 1, true, false,
                                            otherClass, elementDecl.getName(), otherEndUpper,
                                            intoClass.getNamespace(),
                                            "A declared element of the type" );
      assignDocumentation( assoc, elementDecl );
      factory.constructStereotypeElement( assoc );

    } else if ( term.isModelGroup() ) { // an ad-hoc model group
      if ( otherEndUpper == 1 ) {
        inlineModelGroup( term.asModelGroup(),
                          intoClass );

      } else { // represent the local anonymous model group as a class and associate it

        modelGroupName = formatClassName( intoClass.getName()+INFERRED_GROUP_NAME_SUFFIX );

        LOGGER.log( Level.FINE, "Create anonymous model group: {0}",modelGroupName);

        otherClass = factory.constructClass(modelGroupName);
        bindType( term, otherClass );

        // NOTE: even though the class could be local, we create it as top-level one
        //       because Rose could not import its attributes.
        otherClass.setNamespace( intoClass.getNamespace() );

        inlineModelGroup( term.asModelGroup(),
                          otherClass );
        // instantiate a composition
        assoc = factory.constructAssociation( intoClass, "", 1, true, false,
                                              otherClass, "",
                                              otherEndUpper,
                                              intoClass.getNamespace(),
                                              "A model element group - the elements are declared in its contents" );
        assignDocumentation( assoc, term );
        factory.constructTagDocumentation(otherClass, "A model group without any representation as <xsd:element>" );
      }
    } else if ( term.isModelGroupDecl() ) { // a model group declaration
      if ( otherEndUpper == 1 ) {
        inlineModelGroup( term.asModelGroupDecl().getModelGroup(),
                          intoClass );
      } else {
        otherClass = locateType( term.asModelGroupDecl() );

        if ( otherClass == null ) { // the group is still not declared
          modelGroupName = formatClassName( term.asModelGroupDecl().getName()+INFERRED_GROUP_NAME_SUFFIX );

          LOGGER.log( Level.FINE, "Create local model group: {0}",modelGroupName);

          namespace = factory.constructPackage( formatPackageName( term.asModelGroupDecl().getTargetNamespace() ));
          otherClass = factory.constructClass( namespace, modelGroupName );

          bindType( term.asModelGroupDecl(), otherClass );
          assignDocumentation( otherClass, term );

          // NOTE: The group declaration is already put in the proper namespace

          inlineModelGroup( term.asModelGroupDecl().getModelGroup(),
                            otherClass );
        }
        // use the model group
        assoc = factory.constructAssociation( intoClass, "", 1, true, false,
                                              otherClass, "",
                                              otherEndUpper,
                                              intoClass.getNamespace(),
                                              "An explicitly declared model element group - the elements are declared in its contents" );
        assignDocumentation( assoc, term );
        factory.constructTagDocumentation(otherClass, "A model group declaration without any representation as <xsd:element>" );
      }
    }
  }

  /**
   * Creates a new attribute as of its declaration. Note that obviously
   * the attribute use might redefine the attribute's default value and/or
   * fixed value. The similarity in the code is just because of the stupid
   * model where the methods are copied between interfaces instead of being
   * just inherited.
   * @param declaration
   * @param umlClass
   * @return a non-null declaration
   */
  private Attribute createAttribute(XSAttributeDecl declaration, Classifier umlClass) {
    Attribute result;
    Classifier attributeClass;

    LOGGER.log( Level.FINE, "  Attribute: {0}",declaration);

    result = factory.constructAttribute( declaration.getName() );
    assignDocumentation( result, declaration );

    attributeClass = locateType( declaration.getType() );
    if ( attributeClass == null ) { // the type is still not defined
      attributeClass = declareLocalType( declaration.getType(),
                                         declaration.getName()+INFERRED_CLASS_NAME_SUFFIX,
                                         umlClass );
      defineType( declaration.getType(), attributeClass );
    }
    result.setOwner( umlClass );
    result.setType( attributeClass );
    result.setVisibility( VisibilityKindEnum.VK_PUBLIC );

    // describe the rest of the attribute use
    if ( declaration.getFixedValue() != null
         && declaration.getFixedValue().toString() != null ) { // constant attribute

      // make it a constant
      result.setInitialValue( factory.constructExpression( declaration.getFixedValue().toString() ) );
      result.setChangeability( ChangeableKindEnum.CK_FROZEN );
      result.setTargetScope( ScopeKindEnum.SK_CLASSIFIER );

    } else { // a regular attribute

      if ( declaration.getDefaultValue() != null
          && declaration.getDefaultValue().toString() != null ) {

        result.setInitialValue( factory.constructExpression("\""+declaration.getDefaultValue()+"\"" ));
      }
      result.setChangeability( ChangeableKindEnum.CK_CHANGEABLE );
      result.setTargetScope( ScopeKindEnum.SK_INSTANCE );
    }
    return result;
  }

  /**
   * Creates a new constant attribute out of an ENUMERATION facet
   * just inherited.
   * @param declaration is an enumeration facet
   * @param umlClass is the owner class
   * @return a non-null declaration
   */
  private Attribute createConstant(XSFacet declaration, Classifier umlClass) {
    Attribute result;
    Classifier attributeClass;

    result = factory.constructAttribute(formatConstant( declaration.getValue().value ));
    assignDocumentation( result, declaration );

    LOGGER.log( Level.FINE, "  Enumeration: {0}",result.getName());

    attributeClass = locateType( stringType );
    result.setOwner( umlClass );
    result.setType( attributeClass );

    // make it a constant
    result.setInitialValue(factory.constructExpression(declaration.getValue().toString()));
    result.setChangeability( ChangeableKindEnum.CK_FROZEN );
    result.setTargetScope( ScopeKindEnum.SK_CLASSIFIER );
    result.setVisibility( VisibilityKindEnum.VK_PUBLIC );

    return result;
  }

  /**
   * @param modelGroup is the non-null model group to inline
   * @param result is the non-null UML class where to represent the group into
   */
  private void inlineModelGroup(XSModelGroup modelGroup, Classifier result) {
    Iterator<XSParticle> particleIterator;
    XSParticle particle;

    // parse the particles
    particleIterator = modelGroup.iterator();
    while ( particleIterator.hasNext() ) {
      particle = particleIterator.next();
      processParticleAsComplexTypeContent( particle, result );
    }
    assignDocumentation( result, modelGroup );
  }

  /**
   * Represents the element as a class with <<ELEMENT>> stereotype
   * @param elementDeclaration is a non-null element
   */
  private void createElement(XSElementDecl elementDeclaration) {
    Classifier umlType;
    Classifier element;

    element = declareType( elementDeclaration );

    umlType = locateType( elementDeclaration.getType() );
    if ( umlType == null ) { // a local type is referred
      // create the type as the 'Type of this' element
      umlType = declareLocalType( elementDeclaration.getType(),
                                  elementDeclaration.getName()+INFERRED_CLASS_NAME_SUFFIX,
                                  element );
      defineType( elementDeclaration.getType(), umlType );
    }
    factory.constructGeneralization( element, umlType );
    factory.constructStereotypeElement( element );
  }

  /**
   * Assigns documentation, corresponding to the annotation provided,
   * to the target model element.
   * @param modelElement is non-null UML model element
   * @param component is the XML schema component this model element represents
   */
  private void assignDocumentation(ModelElement modelElement, XSComponent component) {
    String comments;

    if ( component != null
         && component.getAnnotation() != null
         && component.getAnnotation().getAnnotation() instanceof String) {

      comments = ((String) component.getAnnotation().getAnnotation()).trim();

      factory.constructTagDocumentation(modelElement, comments );
    }
  }

  /**
   * @param defaultName is the suggested class name to generate. Might be null or empty
   * @return an unique qualified class name that represents the taget namespace + default name
   */
  private String formatClassName(String defaultName) {
    String result;
    StringBuilder path;

    path = new StringBuilder( 64 );

    if ( defaultName == null || defaultName.isEmpty() ) {
      path.append( ANONYMOUS_CLASS_NAME_PREFIX );
    } else {
      path.append( defaultName );
    }
    result = uniqueNamesGenerator.getUnique( path.toString() );
    return result;
  }

  /**
   * @param targetNamespace
   * @return the package name treating all alphanumeric words from the target namespace as sub-package names
   */
  private String formatPackageName(String targetNamespace) {
    StringBuilder result;
    URI namespace;
    StringTokenizer tokenizer;
    String item;

    result = new StringBuilder(256);

    // define the package to store in
    if ( targetNamespace != null && !targetNamespace.isEmpty() ) {
      try {
        namespace = new URI( targetNamespace );

        // format the package name using the path from the namespace URL
        if ( namespace.getPath() != null ) {
          tokenizer = new StringTokenizer( namespace.getPath(), "/.-" );
          while ( tokenizer.hasMoreElements() ) {
            item = (String) tokenizer.nextElement();

            if ( result.length() > 0 ) {
              result.append(".");
            }
            result.append( item );
          }
        }
      } catch (Exception ex) {
        // DO NOTHING
      }
    }
    return result.toString();
  }

  /**
     * @param defaultName is the suggested class name to generate. Might be null or empty
     * @return a java formatted constant name
     */
  private String formatConstant(String defaultName) {
    String result;

    if ( defaultName == null ) {
      result = EMPTY_ENUM_NAME_PREFIX;
    } else {
      // construct identifier out of the value
      result = defaultName.replaceAll( "[^A-Za-z0-9_$]+", "" );

      if ( result.isEmpty() ) {
        result = uniqueNamesGenerator.getUnique( EMPTY_ENUM_NAME_PREFIX );
      } else {
        if ( !Character.isJavaIdentifierStart( result.charAt( 0 ) ) ) { // not suitable as Java package name
          result = ENUM_NAME_PREFIX+result;
        }
      }
    }
    return result;
  }

  /**
     * The factory to provide the anntoation parser to the XSD parser
     * @see AnnotationParserFactory
     */
    protected static class SimpleAnnotationParserFactory implements AnnotationParserFactory {
      public AnnotationParser create() {
        return new AnnotationParserImpl();
      }
    }

  /**
     * This annotation acts as an XML content handler that collects all texts of the
     * elements it is fed. Then it returns the result annotation as a single string.
     */
    protected static class AnnotationParserImpl extends AnnotationParser implements ContentHandler {
      /**
       * The buffer where to collect the annotations
       */
      private final StringBuilder result = new StringBuilder(256);

      /**
       * @see com.sun.xml.xsom.parser.AnnotationParser#getContentHandler(com.sun.xml.xsom.parser.AnnotationContext, java.lang.String, org.xml.sax.ErrorHandler, org.xml.sax.EntityResolver)
       */
      public ContentHandler getContentHandler(AnnotationContext context, String parentElementName, ErrorHandler errorHandler, EntityResolver entityResolver) {
        result.setLength( 0 );
        return this;
      }

      /**
       * @see com.sun.xml.xsom.parser.AnnotationParser#getResult(java.lang.Object)
       */
      public Object getResult(Object existing) {
        return result.toString();
      }

      /**
       * @see org.xml.sax.ContentHandler#characters(char[], int, int)
       */
      public void characters(char[] ch, int start, int length) throws SAXException {
        result.append( new String( ch, start, length ).replaceAll( "[\t\r\n]", " "));
      }

      public void endDocument() throws SAXException {
      }

      public void endElement(String uri, String localName, String qName) throws SAXException {
      }

      public void endPrefixMapping(String prefix) throws SAXException {
      }

      public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
      }

      public void processingInstruction(String target, String data) throws SAXException {
      }

      public void setDocumentLocator(Locator locator) {
      }

      public void skippedEntity(String name) throws SAXException {
      }

      public void startDocument() throws SAXException {
      }

      public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
      }

      public void startPrefixMapping(String prefix, String uri) throws SAXException {
      }
    }

  /**
   * Reports any errors/warnings found during the schema parsing
   */
  final class ParserErrorListerner implements ErrorHandler {
    public void error(SAXParseException arg0) {
      LOGGER.log( Level.SEVERE, "",arg0);
    }


    public void fatalError(SAXParseException arg0) {
      LOGGER.log( Level.SEVERE, "",arg0);
    }


    public void warning(SAXParseException arg0) {
      LOGGER.log( Level.WARNING, "",arg0);
    }
  }

  /**
   * Resolves any entities just by returning their URL (when known)
   */
  final class SimpleEntityResolver implements EntityResolver {
    /**
     * The XSOM parser reads the <xsd:import namespace="...URI..." schemaLocation="...file..."> and
     * provides the namespace URI as public ID and the schemaLocation as system ID
     *
     * @return a non-null InputSource for the file in the namespace URI
     * @see org.xml.sax.EntityResolver#resolveEntity(java.lang.String, java.lang.String)
     */
    public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
      InputSource result;
      URL url;

      if ( systemId == null ) {
        if ( publicId != null ) {
          url = new URL( publicId );

          LOGGER.log(Level.INFO, "Resolving {0} remotely", url);
        } else {
          throw new IllegalArgumentException( "Received null system and null public ID of XSD to import");
        }
      } else { // prefer the system ID
        url = new URL( systemId );

        LOGGER.log(Level.INFO, "Resolving {0} locally", url);
      }
      result = new InputSource( url.toString() );

      return result;
    }
  }
}