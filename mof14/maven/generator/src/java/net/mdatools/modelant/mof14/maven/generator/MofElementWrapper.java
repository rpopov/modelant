/*
 * Copyright (c) i:FAO AG 2010. All Rights Reserved.
 *
 * This SOURCE CODE FILE, which has been provided by i:FAO AG as part
 * of a product of i:FAO AG for use ONLY by licensed users of the product,
 * includes CONFIDENTIAL and PROPRIETARY information.
 *
 * Created on 16.02.2010
 */
package net.mdatools.modelant.mof14.maven.generator;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jmi.model.GeneralizableElement;
import javax.jmi.model.ModelElement;
import javax.jmi.model.Namespace;
import javax.jmi.model.Tag;
import javax.jmi.reflect.RefClass;
import javax.jmi.reflect.RefPackage;

import net.mdatools.modelant.template.api.TemplateContext;
import net.mdatools.modelant.template.api.TemplateEngine;

/**
 * This class holds common methods to manage / query a MOF Objects repository
 *
 * @author Rusi Popov
 */
public class MofElementWrapper {

  public static final Logger LOGGER = Logger.getLogger( MofElementWrapper.class.getName() );

  /**
   * The <code>JAVAX_JMI_SUBSTITUTE_NAME</code> JMI standard tag ID for name substitution
   */
  private static final String JAVAX_JMI_SUBSTITUTE_NAME = "javax.jmi.substituteName";

  /**
   * The common prefix of all wrapper classes to generate for the instances of the classes the
   * wrapped model elements represent.
   */
  private static final String WRAPPER_CLASS_PREFIX = "Wrap";
  private static final String WRAPPER_CLASS_BASE_PREFIX = "Base" + WRAPPER_CLASS_PREFIX;

  /**
   * The <code>ORG_OMG_MOF_IDL_PREFIX</code> is the MOF tag ID for package name prefix
   */
  private static final String ORG_OMG_MOF_IDL_PREFIX = "org.omg.mof.idl_prefix";

  /**
   * The <code>JAVAX_JMI_PACKAGE_PREFIX</code> is the JMI standard tag ID for package name prefix
   */
  private static final String JAVAX_JMI_PACKAGE_PREFIX = "javax.jmi.packagePrefix";

  /**
   * The object this wrapper class wraps to allow its rendering. The reflective
   * interface of the model element is used in oder to make this class
   * independent of the actual model and interface.
   */
  private final ModelElement wrapped;


  /**
   */
  public MofElementWrapper(ModelElement wrapped) {
    this.wrapped = wrapped;
  }


  /**
   * @return the namespace/container n MOF of the wrapped object
   */
  public Namespace getNamespace() {
    return getWrapped().getContainer();
  }

  /**
   * Retrieves the tag assigned to the MOF object provided
   *
   * @param tagId is the non null tag Id as defined in JMI 1.0/MOF 1.4 to search tag with
   * @return the MOF Tag associated with the model element
   */
  public final Tag getTag(String tagId) {
    Tag result;
    RefPackage extent;
    RefClass tagProxy;
    Tag tag;
    Iterator<Tag> tagsIterator;
    ModelElement mofModelElement;

    mofModelElement = getWrapped();
    extent = mofModelElement.refOutermostPackage();
    tagProxy = extent.refClass( Tag.class.getSimpleName() );

    // find the tag bound to the model element
    result = null;
    tagsIterator = tagProxy.refAllOfClass().iterator();
    while ( result == null && tagsIterator.hasNext() ) {
      tag = tagsIterator.next();
      if ( tagId.equals( tag.getTagId() ) && tag.getElements().contains( mofModelElement ) ) {
        result = tag;
      }
    }
    return result;
  }


  /**
   * Retrieves the tag assigned to the MOF object provided
   *
   * @return the MOF Tag associated with the provided element
   */
  public final Collection<Tag> getAllTags() {
    Collection<Tag> result;
    RefPackage extent;
    RefClass tagProxy;
    Tag tag;
    Iterator<Tag> tagsIterator;
    ModelElement mofModelElement;

    mofModelElement = getWrapped();
    extent = mofModelElement.refOutermostPackage();
    tagProxy = extent.refClass( Tag.class.getSimpleName() );

    // collect all tags bound to the model element
    result = new ArrayList<Tag>();
    tagsIterator = tagProxy.refAllOfClass().iterator();
    while ( tagsIterator.hasNext() ) {
      tag = tagsIterator.next();
      if ( tag.getElements().contains( mofModelElement ) ) {
        result.add( tag );
      }
    }
    return result;
  }


  /**
   * @return the name of the model element as provided in the meta-model and optionally overridden
   *         through JMI flags, if it violates Java naming conventions
   */
  public final String calculateSimpleClassName() {
    String name;
    Tag tag;

    // find the class name
    tag = getTag( JAVAX_JMI_SUBSTITUTE_NAME );
    if ( tag == null ) { // no name substitution
      name = getWrapped().getName();

    } else { // name substitution through the tag's value
      name = (String) tag.getValues().get( 0 );
    }
    return name;
  }


  /**
   * Calculates the proper package name, regarding the name substitution and the package name
   * prefix.
   * @return the non-null qualified name of the namespace of the wrapped object
   */
  public String calculatePackageName() {
    String result;
    StringBuffer resultBuffer = new StringBuffer( 256 );

    ((MofElementWrapper) wrap( getWrapped().getContainer() )).constructRawQualifiedName( resultBuffer );

    // additionally format according to Java rules
    result = resultBuffer.toString().replaceAll( "[^a-zA-Z0-9.$]", "" );
    result = result.toLowerCase();

    return result;
  }


  /**
   * @return the qualified java class name of this element
   */
  public String calculateQualifiedClassName() {
    StringBuffer result = new StringBuffer( 256 );

    result.append( calculatePackageName() );
    if ( result.length() > 0 ) {
      result.append( "." );
    }
    result.append( calculateSimpleClassName() );

    return result.toString();
  }


  /**
   * @return the simple name of the wrapper class for the (meta)model element wrapped
   * @see #calculateSimpleClassName()
   */
  public final String calculateSimpleWrapperClassName() {
    String result;

    result = WRAPPER_CLASS_PREFIX + calculateSimpleClassName();

    return result.toString();
  }


  /**
   * @return the simple name of the base wrapper class for the (meta)model element wrapped
   * @see #calculateSimpleClassName()
   */
  public final String calculateSimpleBaseWrapperClassName() {
    String result;

    result = WRAPPER_CLASS_BASE_PREFIX + calculateSimpleClassName();

    return result.toString();
  }


  /**
   * Calculates the proper package name, regarding the name substitution and the package name
   * prefix.
   * @param wrapperPackage is the non-null, not empty package name where to generate all wrapper classes.
   * @return the non-null qualified name of the namespace of the wrapper class
   */
  public String calculateWrapperPackageName(String wrapperPackage) {
    String result;

    assert wrapperPackage != null && !wrapperPackage.trim().isEmpty()
           : "Expected a non-empty package name";

    wrapperPackage = wrapperPackage.trim().replaceAll( "[^a-zA-Z0-9]", "" ).toLowerCase();

    result = calculatePackageName().replaceAll( "^([a-z0-9]*\\.[a-z0-9]*\\.[a-z0-9]*)\\.(.*)$",
                                                "$1."+wrapperPackage+".$2" );
    return result;
  }


  /**
   * Calculates the proper package name, regarding the name substitution and the package name
   * prefix.
   * @param wrapperPackage is the not null, not empty package name where to generate all wrapper classes.
   * @return the non-null qualified name of the namespace of the wrapper class
   */
  public String calculateBaseWrapperPackageName(String wrapperPackage) {
    String result;

    assert wrapperPackage != null && !wrapperPackage.trim().isEmpty()
          : "Expected a non-empty package name";

    wrapperPackage = wrapperPackage.trim().replaceAll( "[^a-zA-Z0-9]", "" ).toLowerCase();

    result = calculatePackageName().replaceAll( "^([a-z0-9]*\\.[a-z0-9]*\\.[a-z0-9]*)\\.(.*)$",
                                                "$1."+wrapperPackage+".base.$2" );
    return result;
  }


  /**
   * This method calculates qualified name of a wrapper class for this model element
   * Convention:
   * The package prefix is inserted between 3rd and 4th package names, this way forming an unique namespace for the
   * wrapper classes.
   * @param prefix the simple package name where to generate the wrapper classes. If null or empty provided, this
   *        method is equal to {@link #calculateQualifiedClassName()}}
   */
  public String calculateQualifiedWrapperClassName(String prefix) {
    StringBuffer result = new StringBuffer( 256 );

    result.append( calculateWrapperPackageName( prefix ) );
    if ( result.length() > 0 ) {
      result.append( "." );
    }
    result.append( calculateSimpleWrapperClassName() );

    return result.toString();
  }


  /**
   * This method calculates the qualified name of the base wrapper class for this model element
   * Convention:
   * The package prefix is inserted between 3rd and 4th package names, this way forming an unique namespace for the
   * wrapper classes.
   * @param prefix the non-null, not empty simple package name where to generate the wrapper classes.
   */
  public String calculateQualifiedBaseWrapperClassName(String prefix) {
    StringBuffer result = new StringBuffer( 256 );

    result.append( calculateBaseWrapperPackageName( prefix ) );
    if ( result.length() > 0 ) {
      result.append( "." );
    }
    result.append( calculateSimpleBaseWrapperClassName() );

    return result.toString();
  }


  /**
   * Identifies the name of the wrapper super class
   * @param prefix is the non-null, not empty name of the package prefix this delagtor classs is in
   * @return a non-null, not empty
   */
  public String calculateQualifiedSuperclassWrapperName(String prefix) {
    String result;

    if ( ((GeneralizableElement) getWrapped()).getSupertypes().isEmpty() ) { // directly inherit
      result = Wrapper.class.getName();

    } else { // retrieve and format the superclass' name

      result = ((MofElementWrapper) wrap((ModelElement) ((GeneralizableElement) getWrapped()).getSupertypes().get( 0 ) )).calculateQualifiedWrapperClassName( prefix );
    }
    return result;
  }

  /**
   * Appends to resultBuffer the qualified name of the wrapped model element/namespace, considered as a Java
   * package name
   *
   * @param resultBuffer is not null
   */
  private void constructRawQualifiedName(StringBuffer resultBuffer) {
    Tag tag;
    ModelElement namespace;
    ModelElement container;

    namespace = getWrapped();

    if ( namespace != null ) {
      // check direct name substitution
      tag = getTag( JAVAX_JMI_SUBSTITUTE_NAME );
      if ( tag != null ) {
        resultBuffer.append( tag.getValues().get( 0 ) );

      } else {
        // check for JMI package prefix insertion
        tag = getTag( JAVAX_JMI_PACKAGE_PREFIX );
        if ( tag != null ) {
          resultBuffer.append( tag.getValues().get( 0 ) ).append( "." ).append( namespace.getName() );

        } else {
          // check for OMG IDL prefix insertion
          tag = getTag( ORG_OMG_MOF_IDL_PREFIX );
          if ( tag != null ) {
            resultBuffer.append( tag.getValues().get( 0 ) ).append( "." ).append( namespace.getName() );

          } else { // no name substitution - direct calculation
            container = namespace.getContainer();

            if ( container != null ) {
              ((MofElementWrapper) wrap( container )).constructRawQualifiedName( resultBuffer );
            }
            if ( resultBuffer.length() > 0 ) {
              resultBuffer.append( "." );
            }
            resultBuffer.append( namespace.getName() );
          }
        }
      }
    }
  }


  /**
   * Support method to collect the namespaces starting from outer-most
   * @param wrapped
   * @param result
   */
  private void collectMofNames(List<String> result) {
    Namespace owner;
    String name;
    Tag tag;

    owner = getWrapped().getContainer();
    if ( owner != null ) {
      ((MofElementWrapper) wrap( owner )).collectMofNames( result );
    }

    // check direct name substitution
    tag = getTag( JAVAX_JMI_SUBSTITUTE_NAME );
    if ( tag != null ) {
      name = (String) tag.getValues().get( 0 );
    } else {
      name = getWrapped().getName().replaceAll( "[^a-zA-Z0-9.$]", "" );
    }
    result.add( name );
  }

  /**
   * @return the qualified class name of this MOF element in the terms of MOF, i.e. a non-null
   *         list of names from the outer-most namespace (element 0) to the name of the class itself (element N)
   */
  public List<String> calculateQualifiedMofName() {
    List<String> result = new ArrayList<String>();

    collectMofNames( result );

    return result;
  }


  /**
   * @return true if the wrapped MOF class describes an abstract model class
   */
  public boolean isAbstract() {
    boolean result;

    result = getWrapped() instanceof GeneralizableElement
             && ((GeneralizableElement) getWrapped()).isAbstract();

    return result;
  }


  /**
   * @return true if the wrapped MOF class describes a model class with no supertype
   */
  public boolean isRoot() {
    boolean result;

    result = getWrapped() instanceof GeneralizableElement
             && ((GeneralizableElement) getWrapped()).getSupertypes().isEmpty();
    return result;
  }

  /**
   * Render the methods that are delegated to the wrapped delegate object. The methods are only those
   * public methods declared in the delegate class.
   * ASSUMPTION:<ul>
   * <li> we are building parallel hierarchies of classes one delegation to the other
   * <li> if the class, this model element describes, has no superclass, then there will be no more superclasses
   *      to generate. Thus, if the wrapper implements the same interfaces as the wrapped class, then it must
   *      wrap all wrapper's public methods, no matter if they were declared in it or inherited.
   * </ul>
   */
  public final void renderDelegatedDeclaredMethods(TemplateEngine engine, String delegateClassName, TemplateContext context) {
    Class delegateClass;
    List<Method> uniqueMethods;
    Method[] methodsToDelegateTo;

    uniqueMethods = new ArrayList<Method>();
    try {
      delegateClass = this.getClass().forName( delegateClassName );

      if ( !delegateClass.isInterface()                     // wrapping a class
           || delegateClass.getInterfaces().length <= 1 ) { // or wrapping an interface, inheriting from (no more than ) 1 interface

        if ( !(getWrapped() instanceof GeneralizableElement)
             || ((GeneralizableElement) getWrapped()).getSupertypes().isEmpty() ) { // no superclasses to generate

          addUniqueMethods( uniqueMethods, delegateClass.getMethods() );

        } else { // the inherited methods of the delegate are handled in the superclass of the wrapper/delegator
          addUniqueMethods( uniqueMethods, delegateClass.getDeclaredMethods() );

          // filter only the unique methods from the superfclass
          delegateClass = delegateClass.getSuperclass();
          if ( isBaseWrapper( delegateClass ) ) {
            addUniqueMethods( uniqueMethods, delegateClass.getDeclaredMethods() );
          }
        }
      } else { // wrapping an interface with multiple inheritance
               // then we just delegate ALL methods in that interface, not just the specifically declared ones,
               // no matter that we might override inherited methods
        addUniqueMethods( uniqueMethods, delegateClass.getMethods() );
      }

      // guarantee a determined order of rendered methods
      Collections.sort( uniqueMethods, new Comparator<Method>() {

        public int compare(Method m1, Method m2) {
          int result;

          result = m1.toString().compareTo( m2.toString() );

          return result;
        }
      });

      for (Method methodToDelegate : uniqueMethods) {
        engine.render( methodToDelegate, context);
      }
    } catch (Exception ex) {
      LOGGER.log( Level.SEVERE, "Rendering the delagate methods of " + delegateClassName + " caused: ", ex );
    }
  }

  /**
   * @return true if the class is a Base Wrapper class as of the conventions in this class
   */
  private boolean isBaseWrapper(Class delegateClass) {
    boolean result;

    result = delegateClass != null
             && delegateClass.getSimpleName().startsWith( WRAPPER_CLASS_BASE_PREFIX );

    return result;
  }


  /**
   * Add to uniqueMethods only the methods, not already added (considering the name and parameter types, skipping overrides) there:<ul>
   * <li> skip synthetic methods, because they actually refer overriding methods (from different classes), which causes names collisions
   * <li> skip service methods for wrappers
   * </ul>
   * @param uniqueMethods
   * @param methods
   */
  private void addUniqueMethods(List<Method> uniqueMethods, Method[] methods) {
    boolean found;
    Iterator<Method> uniquesInteratror;
    Method unique;

    for (Method methodToDelegate : methods) {

      if ( Modifier.isPublic( methodToDelegate.getModifiers())
           && !methodToDelegate.isSynthetic()
           && methodToDelegate.getDeclaringClass() != Object.class
           && methodToDelegate.getDeclaringClass() != Wrapper.class) {

        found = false;
        uniquesInteratror = uniqueMethods.iterator();
        while ( !found && uniquesInteratror.hasNext() ) {
          unique = uniquesInteratror.next();

          found = unique.getName().equals( methodToDelegate.getName())
                  && Arrays.equals( unique.getParameterTypes(),
                                    methodToDelegate.getParameterTypes());
        }
        if ( !found ) {
          uniqueMethods.add( methodToDelegate );
        }
      }
    }
  }


  /**
   * This method renders methods that are delegated to the wrapped delegate object. The methods are only those
   * public methods declared in the delegate class.
   */
  public final void renderDelegatedDeclaredConstants(TemplateEngine engine, String delegateClassName, TemplateContext context) {
    Class delegateClass;
    Field[] fieldsToDelegateTo;

    try {
      delegateClass = this.getClass().forName( delegateClassName );
      fieldsToDelegateTo = delegateClass.getDeclaredFields();

      for (Field fieldToDelegate : fieldsToDelegateTo) {
        if ( Modifier.isPublic( fieldToDelegate.getModifiers())
             && Modifier.isStatic( fieldToDelegate.getModifiers())
             && Modifier.isFinal( fieldToDelegate.getModifiers())) {
          engine.render( fieldToDelegate, context );
        }
      }
    } catch (Exception ex) {
      LOGGER.log( Level.SEVERE, "Rendering the delagate fields of " + delegateClassName + " caused: ", ex );
    }
  }

  /**
   * This method renders interfaces implemented by the delegate class
   */
  public final void renderImplementedInterfaces(TemplateEngine engine, String delegateClassName, TemplateContext context) {
    Class delegateClass;
    List <Class> implementedInterfaces;

    implementedInterfaces = new ArrayList<Class>();
    try {
      delegateClass = this.getClass().forName( delegateClassName );

      if ( delegateClass.isInterface() ) { // the wrapped class is actually an interface, the wrapper implements exactly it
        implementedInterfaces.add( delegateClass );

      } else { // the wrapped is a class, so the wrapper implements its interfaces (but cannot "implement the class")
        implementedInterfaces.addAll( Arrays.asList( delegateClass.getInterfaces() ));

        delegateClass = delegateClass.getSuperclass();
        if ( isBaseWrapper( delegateClass ) ) { // the actual interfaces to inherit are in the Base* class, unexpectedly not listed for the class itself
          implementedInterfaces.addAll( Arrays.asList( delegateClass.getInterfaces() ));
        }
      }

      if ( implementedInterfaces.size() > 0 ) {
        engine.render( implementedInterfaces, context );
      }
    } catch (Exception ex) {
      LOGGER.log( Level.SEVERE, "Rendering the interfaces of " + delegateClassName + " caused: ", ex );
    }
  }


  /**
   * CONVENTION:<ul>
   * <li> when the wrapper is used to create a new wrapped object, it might happen that this method needs public access.
   * <li> when wrapping a wrapper, this method is overridden, this way granting the outer-most wrapper
   *      access to the nested-most wrapped object (bypassing the wrappers)
   * </ul>
   * @return the deepest wrapped object
   */
  public final ModelElement getWrapped() {
    return wrapped;
  }


  /**
   * Call this method in subclasses in order to instantiate the wrapper class
   * that actually corresponds to the class of the object toWrap
   * @param toWrap is the object to wrap in another wrapper class. Might be null;
   * @return null, when null provided, otherwise a properly initialized
   *         wrapper class that wraps toWrap
   * @throws IllegalArgumentException when mapping is not possible
   * @see Wrapper#wrap(Object)
   */
  public final MofElementWrapper wrap(ModelElement toWrap) throws IllegalArgumentException {
    return new MofElementWrapper( toWrap );
  }


  /**
   * Call this method in subclasses in order to instantiate the wrapper class
   * that actually corresponds to the class of the object toWrap
   * @param toWrap is the object to wrap in another wrapper class. Might be null;
   * @return null, when null provided, otherwise a properly initialized
   *         collection of wrappers that wrap the elements of toWrap
   * @throws IllegalArgumentException when mapping is not possible
   * @see Wrapper#wrap(Collection)
   */
  public final List<MofElementWrapper> wrap(Collection<ModelElement> toWrap) throws IllegalArgumentException {
    List<MofElementWrapper> result;

    result = new ArrayList<>();
    for (ModelElement element : toWrap) {
      result.add( new MofElementWrapper( element ) );
    }
    return result;
  }
}