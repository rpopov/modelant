/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.jmi.model.Classifier;
import javax.jmi.model.ModelElement;
import javax.jmi.reflect.InvalidNameException;
import javax.jmi.reflect.JmiException;
import javax.jmi.reflect.RefAssociation;
import javax.jmi.reflect.RefBaseObject;
import javax.jmi.reflect.RefClass;
import javax.jmi.reflect.RefFeatured;
import javax.jmi.reflect.RefObject;
import javax.jmi.reflect.RefPackage;
import javax.jmi.reflect.RefStruct;

import net.mdatools.modelant.core.api.Operation;
import net.mdatools.modelant.core.api.name.Name;
import net.mdatools.modelant.core.operation.element.PrintModelElement;


/**
 * Retrieve associated model elements, a single model element, or a single
 * attribute value, starting from the current model being processed, down an explicit
 * path provided.
 * @author Rusi Popov
 */
public class Navigator {

  private static final PrintModelElement PRINT_MODEL_ELEMENT = new PrintModelElement();

  /**
   * Regular expression for allowed separators in object navigation paths
   */
  public static final String OBJECT_PATH_SEPARATORS = "\\.";

  /**
   * The explicit attribute name that refers the MOF ID of the object.
   */
  public static final String NAME_MOFID = "MOFID";

  /**
   * An element of the object navigation path that leads to the obejct's meta-object
   */
  public static final String NAME_METAOBJECT = "METAOBJECT";

  /**
   * An element of the object navigation path that leads to the qualified name of
   * the obejct's meta-object within the MOF metamodel
   */
  public static final String NAME_METACLASSNAME = "METACLASSNAME";

  /**
   * An element of the object navigation path that leads to the obejct's outer-most package
   */
  public static final String NAME_OUTER_MOST_PACKAGE = "OPACKAGE";

  /**
   * An element of the object navigation path that leads to the obejct's immediate package
   */
  public static final String NAME_IMMEDIATE_PACKAGE = "IPACKAGE";

  /**
   * Collects all values the path describes starting form the startFrom object
   * <pre>
   * Format: {asssociationName.}[attributeName]
   *
   *  associationName = name
   *                    | METAOBJECT
   *                    | METACLASSNAME
   *                    | IPACKAGE
   *                    | OPACKAGE
   *
   *  attributeName = name
   *                  | MOFID
   * where:
   *   MOFID refers the MOF ID of the object to process
   *   METAOBJECT retrieves the meta-object of the object to process. This allows reflective navigation.
   *   METACLASSNAME retrieves the qualified (in the meta-model) name of the meta-class for the processed object.
   *   IPACKAGE retrieves the immediate package in the meta-model the processed object is in
   *   OPACKAGE retrieves the outer-most package (the extent) where object processed is in
   *
   * </pre>
   * @param startFrom
   * @param path is a non-null path following *-to-many or *-to-one associations and ending optionally with attribute
   * @param reportExceptions in some cases it makes no sense to report the exceptions, while generating them allocates
   *         significant resources, but the client code does not use them. Set it to true in order to suppress throwing
   *         the exceptions.
   * @return a non-null collection of non-null and non-empty string values
   * @throws JmiException
   */
  public static List<? extends Object> collectValues(RefFeatured startFrom,
                                                     String path,
                                                     boolean reportExceptions) throws JmiException {
    ArrayList<Object> result = new ArrayList<Object>();
    String[] parsedPath;

    parsedPath = path.split(OBJECT_PATH_SEPARATORS);

    collectAllValues( startFrom, parsedPath, 0, result, reportExceptions);
    return result;
  }

  private static void collectAllValues(Object startFrom,
                                       String[] parsedPath,
                                       int startIndex,
                                       List<Object> output,
                                       boolean reportExceptions) {
    Object result;
    String itemName;
    Iterator associatedIterator;

    if ( startIndex >= parsedPath.length ) { // startFrom is the result to store
      if ( startFrom != null ) {
        output.add( startFrom );
      }
    } else { // there are still associations to go
      itemName = parsedPath[startIndex];
      try {
        result = getReflectiveValue( startFrom, itemName );

      } catch (Exception ex) {
        if ( reportExceptions ) {
          throw new IllegalArgumentException("Navigating down the path: " + Arrays.asList(Arrays.copyOf( parsedPath, startIndex-1 ))
                                           + " reached " + PRINT_MODEL_ELEMENT.execute( startFrom )+" where could not find "+itemName,
                                           ex);
        }
        result = null;
      }

      if ( result instanceof Collection ) { // this is not an attribute value, so these are associated model elements
        associatedIterator = ((Collection) result).iterator();
        while ( associatedIterator.hasNext() ) {
          collectAllValues( associatedIterator.next(),
                            parsedPath,
                            startIndex+1,
                            output,
                            reportExceptions);
        }
      } else if ( result != null ) {
        collectAllValues( result,
                          parsedPath,
                          startIndex+1,
                          output,
                          reportExceptions);
      }
    }
  }

  /**
   * Retrieve a single value using of the named association or attribute, using the MOF reflective interfaces
   * @param startFrom
   * @param itemName
   * @return the retrieved value
   * @throws InvalidNameException when the value could not be retrieved
   */
  public static Object getReflectiveValue(Object startFrom, String itemName) throws InvalidNameException {
    Object result;

    if ( NAME_MOFID.equalsIgnoreCase( itemName ) ) {
      if ( startFrom instanceof RefBaseObject ) {
        result = ((RefBaseObject) startFrom).refMofId();
      } else {
        throw new InvalidNameException( itemName,
                                        "In order to retrieve the MOF ID of the processed object ("+ NAME_MOFID
                                        + ") expected an instance of RefBaseObject "
                                        + " instead of "+ startFrom);
      }
    } else if ( NAME_METAOBJECT.equalsIgnoreCase( itemName ) ) {
      if ( startFrom instanceof RefBaseObject) {
        result = ((RefBaseObject) startFrom).refMetaObject();
      } else {
        throw new InvalidNameException( itemName,
                                        "In order to retrieve the meta-object of the processed object ("+ NAME_METAOBJECT
                                        + ") expected an instance of RefBaseObject "
                                        + " instead of "+ startFrom);
      }
    } else if ( NAME_METACLASSNAME.equalsIgnoreCase( itemName ) ) {
      if ( startFrom instanceof RefObject  ) {
        result = constructQualifiedName((ModelElement) ((RefObject) startFrom).refClass().refMetaObject());

      } else {
        throw new InvalidNameException( itemName,
                                        "In order to retrieve the metaclass of the processed object ("+ NAME_METACLASSNAME
                                        + ") expected an instance of RefObject "
                                        + " instead of "+ startFrom);
      }
    } else if ( NAME_OUTER_MOST_PACKAGE.equalsIgnoreCase( itemName )) {
      if ( startFrom instanceof RefBaseObject) {
        result = ((RefBaseObject) startFrom).refOutermostPackage();
      } else {
        throw new InvalidNameException( itemName,
                                        "In order to retrieve the outer-most package in the meta-model the processed object is in"+ NAME_OUTER_MOST_PACKAGE
                                        + " expected an instance of RefBaseObject "
                                        + " instead of "+ startFrom);
      }
    } else if ( NAME_IMMEDIATE_PACKAGE.equalsIgnoreCase( itemName )) {
      if ( startFrom instanceof RefBaseObject) {
        result = ((RefBaseObject) startFrom).refImmediatePackage();
      } else {
        throw new InvalidNameException( itemName,
                                        "In order to retrieve the immediate package in the meta-model the processed object is in ("+ NAME_IMMEDIATE_PACKAGE
                                        + ") expected an instance of RefBaseObject "
                                        + " instead of "+ startFrom);
      }
    } else if ( startFrom instanceof RefFeatured ) {
      try {
        result = ((RefFeatured) startFrom).refGetValue( itemName );
      } catch (JmiException ex) {
        throw new IllegalArgumentException(" Retrieving the value of field '"+itemName+"'"
//                                        + " of "+ PRINT_MODEL_ELEMENT.execute( startFrom )
                                        + " caused ", ex);
      }
    } else if ( startFrom instanceof RefStruct ) {
        try {
          result = ((RefStruct) startFrom).refGetValue( itemName );
        } catch (JmiException ex) {
          throw new IllegalArgumentException(" Retrieving the value of field '"+itemName+"'"
//                                          + " of "+ PRINT_MODEL_ELEMENT.execute( startFrom )
                                            + " caused ", ex);
        }
    } else {
      throw new InvalidNameException( itemName,
                                      "Expected a RefFeatured or RefStruct instance instead of "
                                      +startFrom
                                      +" to get its "+itemName );
    }
    return result;
  }


  /**
   * This method locates all objects in the model package/extent
   * @param sourceExtent non-null extent where to find the metaclass
   * @return an iterator on all objects in meta-classes in this or nested packages
   */
  public static List<RefObject> getAllObjects(RefPackage sourceExtent ) {
    ArrayList<RefObject> result;

    result = new ArrayList<RefObject>();


    process(sourceExtent,
		    new ProcessPackage() {
					public RefPackage execute(RefPackage refPackage) throws RuntimeException, IllegalArgumentException {
				    for (RefClass metaClass : (Collection<RefClass>) refPackage.refAllClasses()) {
				      result.addAll( metaClass.refAllOfClass() );
				    }
						return null;
					}
				});
    return result;
  }

  /**
   * @param sourceExtent non-null extent where to find the metaclasses
   * @return non-null list of all meta-classes in sourceExtent and its sub-packages
   */
  public static List<RefAssociation> getAllAssociations(RefPackage sourceExtent ) {
    ArrayList<RefAssociation> result;

    result = new ArrayList<>();

    process(sourceExtent,
    		    new ProcessPackage() {
							public RefPackage execute(RefPackage refPackage) throws RuntimeException, IllegalArgumentException {
								result.addAll( refPackage.refAllAssociations() );
								return null;
							}
						});
    return result;
  }

  /**
   * This method locates all objects in the model package/extent
   * @param sourceExtent non-null extent where to find the metaclass
   * @return an iterator on all objects in meta-classes in this or nested packages
   */
  public static List<RefClass> getAllClasses(RefPackage sourceExtent ) {
    ArrayList<RefClass> result;

    result = new ArrayList<>();

    process(sourceExtent,
				    new ProcessPackage() {
							public RefPackage execute(RefPackage refPackage) throws RuntimeException, IllegalArgumentException {
								result.addAll( refPackage.refAllClasses() );
								return null;
							}
						});
    return result;
  }

  /**
   * Process the provided package in a specific way.
   * The operation's result is not used for further processing.
   */
  private interface ProcessPackage extends Operation<RefPackage> {
  }

  /**
   * This method adds all model objects found in the meta classes in the package provided
   * or in its subpackages
   * @param thisPackage is the meta-package to collect objects in
   * @param result is a non-null list of all model objects
   */
  private static void process(RefPackage thisPackage, ProcessPackage processPackage) {
    processPackage.execute(thisPackage);

    for (RefPackage nested : (Collection<RefPackage>) thisPackage.refAllPackages()) {
      process( nested, processPackage);
    }
  }


  /**
   * This method finds the metapackage with the name provided
   *
   * @param rootPackage is the top-most package / the model's extent
   * @param metaPackageName a meta package name with syntax [&lt;package&gt;{::&lt;package&gt;}]
   * @return the non-null meta package
   * @throws JmiException if the meta package name is not a valid one
   */
  public static RefPackage getMetaPackage(RefPackage rootPackage, String metaPackageName) throws JmiException {
    RefPackage result;
    String[] parsedPath;
    String itemName;

    assert rootPackage != null : "Expected a non-null package";

    result = rootPackage;

    // parse syntax [<package>{::<package>}]
    parsedPath = Name.parseQualifiedName( metaPackageName );

    for (int i = 0; i < parsedPath.length; i++) {
      itemName = parsedPath[i];

      try {
        result = result.refPackage( itemName );
      } catch (JmiException ex) {
        throw new IllegalArgumentException("Looking up the package "+ metaPackageName
                                        + " down the path: " + Arrays.asList( Arrays.copyOf( parsedPath, i ) )
                                        + " reached " + PRINT_MODEL_ELEMENT.execute( result )
                                        + " for which retrieving the nested package '"+itemName+"'"
                                        + " caused ", ex);
      }
    }
    return result;
  }

  /**
   * This method parses the metaclass parameter, matches it with the meta model structure, and finds
   * the meta class with the qualified metaclass name.
   *
   * @param rootPackage is the top-most package / the model's extent
   * @param metaClassName a meta class qualified name with syntax {&lt;package&gt;::} &lt;class&gt;
   * @return the non-null metaclass described in the <code> metaclass </code> attribute
   * @throws JmiException if the metaclass name is not a valid one
   */
  public static RefClass getMetaClass(RefPackage rootPackage, String metaClassName) throws JmiException {
    RefClass result = null;
    int i;
    String[] parsedPath;
    String itemName;

    assert rootPackage != null : "Expected a non-null package";

    // parse syntax {<package>::}<class>
    parsedPath = Name.parseQualifiedName( metaClassName );
    i = 0;
    while ( i < parsedPath.length ) {
      itemName = parsedPath[i++];

      try {
        if ( i < parsedPath.length ) { // this is a package name
          rootPackage = rootPackage.refPackage( itemName );

        } else { // this is a meta class name, the parsing process will terminate
          result = rootPackage.refClass( itemName );
        }
      } catch (JmiException ex) {
        throw new IllegalArgumentException("Looking up the package "+ metaClassName
                                        + " down the path: " + Arrays.asList( Arrays.copyOf( parsedPath, i-1 ) )
                                        + " reached " + PRINT_MODEL_ELEMENT.execute( result )
                                        + " for which retrieving the nested package '"+itemName+"'"
                                        + " caused ", ex);
      }
    }
    return result;
  }

  /**
   * This method constructs the name of the metaclass of the model element provided.
   * @param element is the non-null object to find metaclass of
   * @return the non-null metaclass name
   * @throws JmiException if the metaclass name is not a valid one
   */
  public static String getMetaClassName(RefObject element) throws JmiException {
    assert element != null : "Expected a non-null model element";

    return constructQualifiedName((ModelElement) element.refMetaObject());
  }

  /**
   * @param refPackage
   * @return the qualified name of the class provided
   */
  public static String constructQualifiedName(RefPackage refPackage) {
    return constructQualifiedName((ModelElement) refPackage.refMetaObject());
  }

  /**
   * @param metaObject this is a MOF object
   * @return the qualified name of the MOF element, calculated down the containment relation
   */
  public static String constructQualifiedName(ModelElement metaObject) {
    StringBuilder result = new StringBuilder(256);

    insertQualifiedName( metaObject, result );

    return result.toString();
  }

  /**
   * Construct in result the qualified name of the metaObject
   * @param metaObject not null
   * @param result not null
   */
  private static void insertQualifiedName(ModelElement metaObject, StringBuilder result) {
    ModelElement next;

    next = metaObject.getContainer();
    if ( next != null ) {
      insertQualifiedName( next, result );
      result.append(Name.METAMODEL_PATH_SEPARATOR);
    }
    result.append( metaObject.getName() );
  }

  /**
   * This method retrieves the descriptions of all super classes in the metamodel
   * @param mofMetaObject
   * @return a non-null unique collection of superclasses of the provided class from the metamodel
   */
  public static Collection<Classifier> getAllSuperMetaObejcts(Classifier mofMetaObject) {
    Collection result;
    List<Classifier> toProcess;

    result = new HashSet(11);
    toProcess = new ArrayList<Classifier>();
    toProcess.add( mofMetaObject );

    while ( !toProcess.isEmpty() ) {
      mofMetaObject = toProcess.remove( 0 );

      if ( result.add( mofMetaObject ) ) {
         toProcess.addAll( mofMetaObject.allSupertypes() );
      }
    }
    return result;
  }
}