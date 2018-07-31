/*
 * Copyright (c) 2005,2012 Rusi Popov, MDA Tools.net
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Rusi Popov (popovr@mdatools.net) - initial implementation
 */
package net.mdatools.modelant.uml13.reverse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;

import javax.jmi.reflect.RefPackage;

import org.omg.uml13.foundation.core.Attribute;
import org.omg.uml13.foundation.core.Classifier;
import org.omg.uml13.foundation.core.Dependency;
import org.omg.uml13.foundation.core.Operation;
import org.omg.uml13.foundation.core.Parameter;
import org.omg.uml13.foundation.datatypes.CallConcurrencyKindEnum;
import org.omg.uml13.foundation.datatypes.ChangeableKindEnum;
import org.omg.uml13.foundation.datatypes.Expression;
import org.omg.uml13.foundation.datatypes.ParameterDirectionKindEnum;
import org.omg.uml13.foundation.datatypes.ScopeKindEnum;
import org.omg.uml13.foundation.datatypes.VisibilityKindEnum;
import org.omg.uml13.modelmanagement.UmlPackage;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.ConstructorDoc;
import com.sun.javadoc.DocErrorReporter;
import com.sun.javadoc.Doclet;
import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.LanguageVersion;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.PackageDoc;
import com.sun.javadoc.ParamTag;
import com.sun.javadoc.ParameterizedType;
import com.sun.javadoc.RootDoc;
import com.sun.javadoc.Tag;
import com.sun.javadoc.Type;

import net.mdatools.modelant.core.api.name.Name;
import net.mdatools.modelant.core.name.ClassNameImpl;
import net.mdatools.modelant.repository.api.ModelFactory;
import net.mdatools.modelant.repository.api.ModelRepository;
import net.mdatools.modelant.repository.api.ModelRepositoryFactory;
import net.mdatools.modelant.uml13.metamodel.Convention;

/**
 * Javadoc - to - UML reverse engineering. It implements a standard Doclet
 * functionality, uses the static resources of the outer class (as parameters) and generates UML
 * classes for the java classes (and interfaces) reviewed.
 * <p>
 * The model created presents correctly the real packages, classes, interfaces, methods and attributes:<ul>
 * <li> Nested classes and interfaces are presented as well, though ones that are marked as static in Java are not marked as
 * static in the model.
 * <li>The methods created are not bound to the exceptions they throw (due to some compatibility issues
 * with Rose XML Plugin), though the exceptions and the stereotypes are registered in the model.
 * <li>The classes that are not included for documenting but are still referenced by ones being documented are included
 * in the model as data types with the qualified class name. They are placed in the top-most package - the model itself.
 * <li> Only one-way X-to-many associations are identified and created. The two-way associations (navigable in both directions)
 * are not identified.
 * </ul>
 * Expected to find system properties:<ul>
 * <li>model - the name of the model to generate
 * <li>targetDir - the absolute path to the directory where to create the result file
 * <li>target - file name where to store the model. Assumed to be created in targetDir
 * <li>workDir - (optional) the absolute path to the directory where to create the temporary files
 * </ul>
 * @author Rusi Popov
 */
public class ReverseEngineerJavaDoclet extends Doclet {

  /**
   * Doclet command-line parameter to specify the absolute file name of the model to produce
   */
  public static final String PARAMETER_TARGET_FILE = "target";

  /**
   * Doclet command-line parameter to specify the directory of model file to produce
   */
  public static final String PARAMETER_WORK_DIR = "workDir";

  /**
   * Doclet command-line parameter with the name of the model to construct
   */
  public static final String PARAMETER_MODEL = "model";

  /**
   * The name of the model parameter that describes a method's result
   */
  private static final String RETURN_PARAMETER_NAME = "return";

  /**
   * The name of the javadoc tag that describes a method's result
   */
  private static final String JAVADOC_RETURN_TAG_NAME = "@return";

  /**
   * Model file name to generate
   */
  private final File outputModelFile;

  private final RefPackage extent;
  private final ModelRepository repository;
  private final ModelFactory modelFactory;
  private final Uml13ModelFactory factory;

  /**
   * This constructor initializes the doclet and instantiates the MDR repository, loads the UML 1.3
   * meta model and instantiates an extent where to create the uml classes idnetified.
   *
   * @throws Exception when creation failed
   */
  private ReverseEngineerJavaDoclet() {
    File workDir;
    String modelFile;
    String modelName;

    workDir = new File(System.getProperty(PARAMETER_WORK_DIR, System.getProperty( "java.io.tmpdir")));
    modelName = System.getProperty(PARAMETER_MODEL, "model");
    modelFile = System.getProperty(PARAMETER_TARGET_FILE, "model.xml");

    outputModelFile = new File(modelFile);

    repository = ModelRepositoryFactory.construct(workDir);
    modelFactory = repository.loadMetamodel("UML13");
    extent = modelFactory.instantiate();

    factory = new Uml13ModelFactory( extent );
    factory.setModelName(modelName);
  }


  /**
   * The doclet entry point as of Doclet interface specified.
   *
   * @param root
   * @return true if model extraction completed successfully
   * @see com.sun.javadoc.Doclet#start(com.sun.javadoc.RootDoc)
   */
  public static boolean start(RootDoc root) {
    ReverseEngineerJavaDoclet doclet;

    try {
      doclet = new ReverseEngineerJavaDoclet();
      try {
        doclet.generate( root );
        doclet.export();
      } finally {
        doclet.shutDown();
      }
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
    return true;
  }


  /**
   * This method is required by the doclet convention. It checks the option provided and returns the
   * number of parameters it requires + 1 (the option itself).
   *
   * @param option is the non-null, not empty option name to read parameters of
   * @return 0 as the number of options it requires including the option itself. 0 means that the
   *         option is not used, -1 - an error occurred
   * @see com.sun.javadoc.Doclet#optionLength(java.lang.String)
   */
  public static int optionLength(String option) {
    return 0;
  }


  /**
   * This method parses the option parameters and sets the proper parameters of this doclet
   *
   * @param options
   * @param reporter
   * @return true when correct options provided
   * @see com.sun.javadoc.Doclet#validOptions(java.lang.String[][],
   *      com.sun.javadoc.DocErrorReporter)
   */
  public static boolean validOptions(String[][] options, DocErrorReporter reporter) {
    return true;
  }

  /**
   * Indicates support of generics and annotations
   */
  public static LanguageVersion languageVersion() {
    return LanguageVersion.JAVA_1_5;
  }

  /**
   * This method exports the UML model created to the outputFile specified
   * @throws IOException
   * @throws FileNotFoundException
   */
  private void export() throws FileNotFoundException, IOException {
  	System.out.println("Writing "+outputModelFile+"...");

    modelFactory.writeExtent(extent, outputModelFile);
  }

  /**
   * This method releases all resources the doclet had allocated, so this instance cannot be reused
   * anymore.
   */
  private void shutDown() {
    repository.shutdown();
  }

  /**
   * The entry point of the model generation from the JavaDoc results
   *
   * @param root
   */
  private void generate(RootDoc root) {
    // generate classes & packages
    ClassDoc[] classes;
    ClassDoc classDoc;

    classes = root.classes();

    // create initially all classes/interfaces as empty ones
    for (int i = 0; i < classes.length; ++i) {
      generateType( classes[ i ] );
    }

    // add fields & methods to the created classes
    for (int i = 0; i < classes.length; ++i) {
      classDoc = classes[ i ];
      fillInType(classDoc);
    }
  }


  /**
   * Add attributes, methods, constructors to the class' model
   * @param classDoc
   */
	private void fillInType(ClassDoc classDoc) {
		FieldDoc[] fields;
		MethodDoc[] methods;
		ConstructorDoc[] constructors;
		Classifier umlClassifier;
		Attribute attribute;
		Operation constructor;
		Operation method;


		umlClassifier = (Classifier) factory.locateModelElement( classDoc.qualifiedName() ); // classDoc must be already existing

		fields = classDoc.fields( false ); // find all attributes regardless of the scope specified

		// bind the attributes
		for (int j = 0; j < fields.length; j++) {
		  attribute = createAttribute( fields[ j ] );
		  attribute.setOwner( umlClassifier );

		  // mark the attributes in the interfaces as constants (static final)
		  if ( classDoc.isInterface() ) {
		    attribute.setVisibility( VisibilityKindEnum.VK_PUBLIC );    // public
		    attribute.setOwnerScope( ScopeKindEnum.SK_CLASSIFIER );     // static
		    attribute.setChangeability( ChangeableKindEnum.CK_FROZEN ); // final
		  }
		}

		// bind the constructors
		constructors = classDoc.constructors( false ); // all declared constructors
		for (int j = 0; j < constructors.length; j++) {
		  constructor = createConstructor( constructors[ j ], umlClassifier );
		  constructor.setOwner( umlClassifier );
		}

		// bind the methods
		methods = classDoc.methods( false ); // all declared methods
		for (int j = 0; j < methods.length; j++) {
		  method = createMethod( methods[ j ] );
		  method.setOwner( umlClassifier );

		  // mark the methods in the interfaces as abstract
		  if ( classDoc.isInterface() ) {
		    method.setVisibility( VisibilityKindEnum.VK_PUBLIC );    // public
		    method.setAbstract( true );
		  }
		}
	}

  /**
   * This method instantiates the proper interface, class or data type depending on
   * the rules identified<ol>
   * <li> If the class/interface is not included in the documentation scope, it is
   * instantiated as a data type
   * <li> If the cocumentation is for interface that has no attributes, then interface is
   *  instantiated
   * <li> Otherwise (this is a class or an interface with attributes) then a class is instantiated.
   * If the class represents an interface, it is steretyped with &lt;&lt;Interface&gt;&gt;
   * </ol>
   * If the classifier has been already registered, then no change is done.
   * @param classDoc
   * @return the classifier instantiated. It might be Interface, UmlClass or DataType instance.
   */
  private Classifier generateType(ClassDoc classDoc) {
    Classifier result;

    try {
      result = (Classifier) factory.locateModelElement( classDoc.qualifiedName() );

    } catch (IllegalArgumentException ex)  { // still not created
      if ( classDoc.isIncluded() ) {
        if ( classDoc.isClass() ) {
          result = gerenateClass( classDoc );

        } else { // clasDoc describes an interface
          if ( classDoc.fields().length == 0 ) { // no attributes exist in this interface
            result = gerenateInterface( classDoc );

          } else { // there are attributes - instantiate a class instead
            result = gerenateClass( classDoc );

            // stereotype the class as an interface
            factory.constructStereotype( result, Convention.STEREOTYPE_INTERFACE);
          }
        }
      } else { // this class is not included for documenting
        // create a data type with the qualified class name

        result = factory.constructDataType( classDoc.qualifiedName() );
      }
    }
    return result;
  }


  /**
   * @param classDoc not null class representation in JavaDoc
   * @return non-null name representing the class
   */
  private Name parseQualifiedName(ClassDoc classDoc) {
    Name result;

//    if ( classDoc.containingClass() != null ) {
      result = new ClassNameImpl(classDoc.qualifiedName());
//    } else {
// TODO
//    }

    return result;
  }

  /**
   * This method instantiates the interface in the proper packages and contaning classifiers,
   * together with the extended interfaces assigned. Once the UML does not allow attributes in
   * interfaces, then if such exist, instead of Interface UmlClass is being instantiated with
   * "Interface" stereotype.
   * @param classDoc
   * @return the classifier created
   */
  private Classifier gerenateInterface(ClassDoc classDoc) {
    Classifier result;
    UmlPackage umlPackage;

    umlPackage = createPackage( classDoc.containingPackage() );
    Classifier containingClassifier = null;

    ClassDoc containingClassifierDoc;

    String interfaceName = classDoc.name();

    // define parent class
    containingClassifierDoc = classDoc.containingClass();
    if ( containingClassifierDoc != null ) {

      // Instantiate properly the parent - it migt be an interface as well
      containingClassifier = generateType( containingClassifierDoc );

      // remove the name of the parent class (including '.' from the name of this class
      interfaceName = interfaceName.substring( containingClassifierDoc.name().length() + 1 );
    }
    result = factory.constructInterface( umlPackage, interfaceName );

    // set parent class
    if ( containingClassifier != null ) {
      result.setNamespace( containingClassifier );
    }

    // visiblity
    if ( classDoc.isPublic() ) {
      result.setVisibility( VisibilityKindEnum.VK_PUBLIC );
    } else if ( classDoc.isPrivate() ) {
      result.setVisibility( VisibilityKindEnum.VK_PRIVATE );

    } else if ( classDoc.isProtected() ) {
      result.setVisibility( VisibilityKindEnum.VK_PROTECTED );
    }

    // final / abstract
    result.setLeaf( classDoc.isFinal() );
    result.setAbstract( classDoc.isAbstract() );

    factory.constructTagDocumentation( result, classDoc.commentText() );

    // set up interface generalization
    createSuperInetrfaces( classDoc, result );
    return result;
  }


  /**
   * This method instantiates the class in the proper packages and contaning classifiers,
   * together with the extended class and implemented interfaces assigned.
   * @param classDoc
   * @return the newly established classifier as a class
   */
  private Classifier gerenateClass(ClassDoc classDoc) {
    Classifier result;
    Classifier superClass;
    Classifier containingClassifier = null;
    UmlPackage umlPackage;

    ClassDoc superclassDoc;
    ClassDoc containingClassifierDoc;

    String className = classDoc.name();

    umlPackage = createPackage( classDoc.containingPackage() );

    // define parent class/interface
    containingClassifierDoc = classDoc.containingClass();
    if ( containingClassifierDoc != null ) {
      // instantiate the containig class / interface as a class / interface /datatype
      containingClassifier = generateType( containingClassifierDoc );

      // remove the name of the container class (including '.' from the name of this class
      className = className.substring( containingClassifierDoc.name().length() + 1 );
    }

    // instantiate this class
    result = factory.constructClass( umlPackage, className );

    // set containg class
    if ( containingClassifier != null ) {
      result.setNamespace( containingClassifier );
    }

    // visiblity
    if ( classDoc.isPublic() ) {
      result.setVisibility( VisibilityKindEnum.VK_PUBLIC );
    } else if ( classDoc.isPrivate() ) {
      result.setVisibility( VisibilityKindEnum.VK_PRIVATE );

    } else if ( classDoc.isProtected() ) {
      result.setVisibility( VisibilityKindEnum.VK_PROTECTED );
    }

    // final / abstract
    result.setLeaf( classDoc.isFinal() );
    result.setAbstract( classDoc.isAbstract() );

    factory.constructTagDocumentation( result, classDoc.commentText() );

    // set up class generalization
    superclassDoc = classDoc.superclass();
    if ( superclassDoc != null ) { // there is a superclass provided
      superClass = generateType( superclassDoc );

      factory.constructGeneralization( result, superClass );
    }

    // set up interface generalization
    createSuperInetrfaces( classDoc, result );
    return result;
  }


  /**
   * This method creates and binds the umlClass provided with the interfaces it implements.
   *
   * @param classDoc is the non-null description of the umlClass
   * @param umlClass is the non-null model class created
   */
  private void createSuperInetrfaces(ClassDoc classDoc, Classifier umlClass) {
    ClassDoc[] interfacesDoc;
    Classifier superClass;

    interfacesDoc = classDoc.interfaces();
    for (int i = 0; i < interfacesDoc.length; i++) {
      superClass = generateType( interfacesDoc[ i ] );
      factory.constructGeneralization( umlClass, superClass );
    }
  }


  /**
   * This method constructs an UML package with the documentation provided. Returns the UML package
   * built.
   * @param packageDoc
   * @return the package built
   */
  private UmlPackage createPackage(PackageDoc packageDoc) {
    UmlPackage result;

    // parse the java package name and create the packages as nested ones
    result = factory.constructPackage( packageDoc.name());
    factory.constructTagDocumentation( result, packageDoc.commentText() );

    return result;
  }


  /**
   * This method creates a constructor method with the documentation provided
   *
   * @param doc the constructor (method) documentation
   * @param thisClass is the class where the constructor is built
   * @return the created method (operation). It is not null
   */
  private Operation createConstructor(ConstructorDoc doc, Classifier thisClass) {
    Operation result;
    com.sun.javadoc.Parameter[] parameters;
    ParamTag[] parameterTags;
    ClassDoc[] exceptions;
    Classifier thrownException;

    Dependency dependency;

    Parameter parameter;
    String parameterComment;

    // create the construtor itself
    result = factory.constructOperation( doc.name() );
    result.setSpecification( false ); // assume the method exists, so it is not a specification
    result.setQuery( false ); // the constructors are not read-only

    factory.constructTagDocumentation( result, doc.commentText() );

    // visiblity
    if ( doc.isPublic() ) {
      result.setVisibility( VisibilityKindEnum.VK_PUBLIC );
    } else if ( doc.isPrivate() ) {
      result.setVisibility( VisibilityKindEnum.VK_PRIVATE );

    } else if ( doc.isProtected() ) {
      result.setVisibility( VisibilityKindEnum.VK_PROTECTED );
    }

    // scope
    if ( doc.isStatic() ) {
      result.setOwnerScope( ScopeKindEnum.SK_CLASSIFIER );
    } else {
      result.setOwnerScope( ScopeKindEnum.SK_INSTANCE );
    }

    // inhertitance
    result.setAbstract( false ); // the constructor cannot be abstract
    result.setLeaf( false ); // the constructor cannot be final

    // synchronization / concurrency
    if ( doc.isSynchronized() ) {
      result.setConcurrency( CallConcurrencyKindEnum.CCK_SEQUENTIAL );
    } else {
      result.setConcurrency( CallConcurrencyKindEnum.CCK_CONCURRENT );
    }
    // Skip specification
    // result.setSpecification( "specification..." );

    // signature
    parameters = doc.parameters();
    parameterTags = doc.paramTags();

    // define each parameter
    for (int i = 0; i < parameters.length; i++) {
      parameter = createParameter( parameters[ i ].name(),
                                   parameters[i].type(),
                                   ParameterDirectionKindEnum.PDK_INOUT );

      parameter.setBehavioralFeature( result );

      // bind documentation
      parameterComment = findComment( JAVADOC_RETURN_TAG_NAME, doc.tags() );
      if ( parameterComment != null ) {
        factory.constructTagDocumentation( parameter, parameterComment );
      }
    }

    // return/result type
    parameter = factory.constructParameter(RETURN_PARAMETER_NAME);

    //  provide no name - as of the convention for result parameters
    parameter.setType( thisClass ); // The type name incudes array dimensions
    parameter.setKind( ParameterDirectionKindEnum.PDK_RETURN );
    parameter.setVisibility( VisibilityKindEnum.VK_PUBLIC );
    parameter.setBehavioralFeature( result );

    // Bind method result (@return) documentation
    parameterComment = findComment( JAVADOC_RETURN_TAG_NAME, parameterTags );
    if ( parameterComment != null ) {
      factory.constructTagDocumentation( parameter, parameterComment );
    }

    // Add throws clause
    exceptions = doc.thrownExceptions();
    for (int i = 0; i < exceptions.length; i++) {
      thrownException = factory.constructException(exceptions[ i ].qualifiedName());

      // bind operation -> exception in a dependency
      dependency = factory.constructDependency( result, thrownException, "" );

      // stereotype the dependency
      factory.constructStereotype(dependency, Convention.STEREOTYPE_THROWS);
    }
    return result;
  }


  /**
   * This method registers a model method with the documentation provided. The method is not bound
   * to a class
   *
   * @param doc a non-null documentation of the method do be created
   * @return a non null method created
   */
  private Operation createMethod(MethodDoc doc) {
    Operation result;
    com.sun.javadoc.Parameter[] parameters;
    ParamTag[] parameterTags;
    ClassDoc[] exceptions;
//    Classifier thrownException;

//    Stereotype throwsStereotype;
//    Dependency dependency;

    Parameter parameter;
    String parameterComment;

    Type parameterTypeDoc;

    // create the constructor itself
    result = factory.constructOperation( doc.name() );
    result.setSpecification( false ); // assume the method exists, so it is not a specification
    result.setQuery( false ); // assume the method is not read-only

    factory.constructTagDocumentation( result, doc.commentText() );

    // visibility
    if ( doc.isPublic() ) {
      result.setVisibility( VisibilityKindEnum.VK_PUBLIC );
    } else if ( doc.isPrivate() ) {
      result.setVisibility( VisibilityKindEnum.VK_PRIVATE );

    } else if ( doc.isProtected() ) {
      result.setVisibility( VisibilityKindEnum.VK_PROTECTED );
    }

    // scope
    if ( doc.isStatic() ) {
      result.setOwnerScope( ScopeKindEnum.SK_CLASSIFIER );
    } else {
      result.setOwnerScope( ScopeKindEnum.SK_INSTANCE );
    }

    // inhertitance
    result.setAbstract( doc.isAbstract() );
    result.setLeaf( doc.isFinal() );

    // synchronization / concurrency
    if ( doc.isSynchronized() ) {
      result.setConcurrency( CallConcurrencyKindEnum.CCK_SEQUENTIAL );
    } else {
      result.setConcurrency( CallConcurrencyKindEnum.CCK_CONCURRENT );
    }
    // Skip specification
    // result.setSpecification( "specification..." );

    // signature
    parameters = doc.parameters();
    parameterTags = doc.paramTags();

    // define each parameter
    for (int i = 0; i < parameters.length; i++) {
      parameter = createParameter( parameters[ i ].name(),
                                   parameters[i].type(),
                                   ParameterDirectionKindEnum.PDK_INOUT );
      parameter.setBehavioralFeature( result );

      // bind parameter documentation
      parameterComment = findComment( parameters[ i ].name(), parameterTags );
      if ( parameterComment != null ) {
        factory.constructTagDocumentation( parameter, parameterComment );
      }
    }

    // return/result type
    parameterTypeDoc = doc.returnType();
    if ( parameterTypeDoc != null ) {
      parameter = createParameter( RETURN_PARAMETER_NAME,
                                   doc.returnType(),
                                   ParameterDirectionKindEnum.PDK_RETURN );

      parameter.setBehavioralFeature( result );

      // Bind method result (@return) documentation
      parameterComment = findComment( JAVADOC_RETURN_TAG_NAME, doc.tags() );
      if ( parameterComment != null ) {
        factory.constructTagDocumentation( parameter, parameterComment );
      }
    }

    // Add throws clause
    exceptions = doc.thrownExceptions();
    for (int i = 0; i < exceptions.length; i++) {
//      thrownException =
      factory.constructException( exceptions[ i ].qualifiedName());

// "throws" dependency between operations and exceptions is not properly supported by Rose TODO check again
//      throwsStereotype= modelManager.instantiateStereotype( MdrUml13Manager.STEREOTYPE_THROWS );
//
//      // bind operation -> exception in a dependency
//      dependency = modelManager.instantiateDependency( result, thrownException, "" );
//
//      // stereotype the dependency
//      throwsStereotype.getExtendedElement().add( dependency );
    }
    return result;
  }


  /**
   * This method instantiates a new method parameter as specified by the parameter description
   * received.
   * Pre-condition: All interfaces/datatypes/classes subject of documentation have already been created
   * @param parameterName is the parameter description
   * @param parameterType is the documentation of this type
   * @param kind IN/INOUT/OUT/RETURN
   * @return the parameter created. It is not null. No documentation is bound.
   */
  private Parameter createParameter(String parameterName,
                                    Type parameterType,
                                    ParameterDirectionKindEnum kind) {
    Parameter result;
    Classifier typeClassifier;

    result = factory.constructParameter(parameterName);

    try {
      typeClassifier = (Classifier) factory.locateModelElement( parameterType.qualifiedTypeName() );

    } catch (IllegalArgumentException ex) { // this is an unknown still type, thus it has not been documented as a model class
      typeClassifier = factory.constructDataType( parameterType.qualifiedTypeName());
    }
    result.setType( typeClassifier );
    result.setKind( kind );

    return result;
  }


  /**
   * This method creates an attribute with the corresponding name, visibility, type and scope
   * Pre-condition: All interfaces/datatypes/classes subject of documentation have already been created
   *
   * @param fieldDoc
   * @return the attribute cteated
   */
  private Attribute createAttribute(FieldDoc fieldDoc) {
    Type typeDoc;
    Attribute result;
    String initialValue;
    Expression initialValueExpression;

//    System.err.println("createAttribute: "+fieldDoc.name());

    // create the attribute
    result = factory.constructAttribute( fieldDoc.name() );
    factory.constructTagDocumentation( result, fieldDoc.commentText() );

    // visiblity
    if ( fieldDoc.isPublic() ) {
      result.setVisibility( VisibilityKindEnum.VK_PUBLIC );
    } else if ( fieldDoc.isPrivate() ) {
      result.setVisibility( VisibilityKindEnum.VK_PRIVATE );

    } else if ( fieldDoc.isProtected() ) {
      result.setVisibility( VisibilityKindEnum.VK_PROTECTED );
    }

    // scope
    if ( fieldDoc.isStatic() ) {
      result.setOwnerScope( ScopeKindEnum.SK_CLASSIFIER );
    } else {
      result.setOwnerScope( ScopeKindEnum.SK_INSTANCE );
    }

    // changeability
    if ( fieldDoc.isFinal() ) {
      result.setChangeability( ChangeableKindEnum.CK_FROZEN );
    } else {
      result.setChangeability( ChangeableKindEnum.CK_CHANGEABLE );
    }

    // type
    typeDoc = fieldDoc.type();
    assignTypeToAttribute( typeDoc, result );

    // initial value
    initialValue = fieldDoc.constantValueExpression();
    if ( initialValue != null && !initialValue.equals("") ) {
      initialValueExpression = factory.constructExpression( initialValue );
      result.setInitialValue( initialValueExpression );
    }
    return result;
  }


  /**
   * This method processes a type/class (of an attribute). It might be just
   * a primitive type, a class, a collection or a generic class. Il looks up
   * or creates the target type as an UML Class:<ul>
   * <li> the data types are just instantiated as data types in UML
   * <li> the classes are located as classes in the model. If not found, they are
   *      instantiated as data types
   * <li> the collections are represented:<ul>
   *      <li> if the collection is raw, the type of the attribute becomes Object
   *           and its multiplicity assigned is *
   *      <li> if the collection is a generic, the type of the attribute becomes the type parameter
   *           and the multiplicity is *
   *      </ul>
   * <li> the generic classes are represented as:<ul>
   *      <li> a class named after the generic class + the parameter types
   *      </ul>
   * </ul>
   * @param result
   * @param typeDoc
   * @returns a non-null representation of the class
   */
  private void assignTypeToAttribute(Type typeDoc, Attribute result) {
    Classifier type;
    int multiUpperBound;
    Class declaredAttributeClass;
    String associatedClassName;
    ParameterizedType genericType;
    Type[] arguments;

    if ( typeDoc.isPrimitive() ) { // an attribute of primitive type
    	try {
        type = (Classifier) factory.locateModelElement( typeDoc.simpleTypeName() ); // TODO CHECK AGAIN if qualified of simple name should be used

    	} catch (IllegalArgumentException ex) { // not found, this is an unknown primitive type
        type = factory.constructDataType( typeDoc.simpleTypeName());
      }
      result.setType( type );

      if ( isSingleValued( typeDoc ) ) {
        multiUpperBound = 1;
      } else { // an array of primitive values
        multiUpperBound =  Convention.UNLIMITED_UPPER_MULTIPLICITY;
      }
    } else { // an attribute of a class - associative attribute

      // load the type as a java class
      try {
        declaredAttributeClass = Class.forName( typeDoc.qualifiedTypeName() );

      } catch (Exception ex) { // invalid/unknown class declaration
        declaredAttributeClass = null;
      } // declaredAttributeClass == null when the class is not known (=> non-standard),
        // or the class of the attribute, otherwise

      if ( declaredAttributeClass != null
           && Collection.class.isAssignableFrom( declaredAttributeClass ) ) { // an association *-to-MANY
        multiUpperBound =  Convention.UNLIMITED_UPPER_MULTIPLICITY;

        // identify the associated class
        genericType = typeDoc.asParameterizedType();
        if ( genericType != null ) { // a generic collection, assumed with a single type parameter
          arguments = genericType.typeArguments();
          if ( arguments.length > 0 ) {
            associatedClassName = arguments[0].qualifiedTypeName();

          } else {
//            System.err.println(" A generic type found with no actual arguments");
            associatedClassName = Object.class.getName();
          }
        } else { // a raw collection - Objects are associated
          associatedClassName = Object.class.getName();
        }
      } else { // an association *-to-ONE
        multiUpperBound = 1;
        associatedClassName = typeDoc.qualifiedTypeName();
      }

      try {
        type = (Classifier) factory.locateModelElement( associatedClassName );

      } catch (IllegalArgumentException ex) { // this is an unknown primitive type
        type = factory.constructDataType( associatedClassName );
      }
      result.setType( type );
    }

    // multiplicity
    // TODO consider adding (as a comment?) the  multiplicity expression
    result.setMultiplicity( factory.constructMultiplicity(multiUpperBound ));
  }


  /**
   * This method checks if the type describes a simple (single-value) variable (true) or it is an array.
   * @param typeDoc a field/parameter type
   * @return true if the type documentation describes a single-value field type, false if it is an array.
   */
  private boolean isSingleValued(Type typeDoc) {
    return typeDoc.dimension() == null  || typeDoc.dimension().equals("");
  }


  /**
   * This method finds the contents of a general tag with the name provided
   *
   * @param tagName is the non-null pagarameter name
   * @param tags holds the comments in param tags
   * @return null if nothing found
   */
  private String findComment(String tagName, Tag[] tags) {
    String result = null;
    int i = 0;
    while ( result == null && i < tags.length ) {
      if ( tagName.equals( tags[ i ].name() ) ) {
        result = tags[ i ].text();
      }
      i++;
    }
    return result;
  }


  /**
   * This method finds the method parameter comment among the param tags provided
   *
   * @param parameterName is the non-null pagarameter name
   * @param parameterTags holds the comments in param tags
   * @return null if nothing found
   */
  private String findComment(String parameterName, ParamTag[] parameterTags) {
    String result = null;
    int i = 0;
    while ( result == null && i < parameterTags.length ) {
      if ( parameterName.equals( parameterTags[ i ].parameterName() ) ) {
        result = parameterTags[ i ].parameterComment();
      }
      i++;
    }
    return result;
  }
}