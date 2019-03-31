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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.jmi.model.GeneralizableElement;
import javax.jmi.model.ModelElement;
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
public class MofElementWrapper<T extends ModelElement> {

  private static final Logger LOGGER = Logger.getLogger( MofElementWrapper.class.getName() );

  /**
   * The <code>JAVAX_JMI_SUBSTITUTE_NAME</code> JMI standard tag ID for name substitution
   */
  private static final String JAVAX_JMI_SUBSTITUTE_NAME = "javax.jmi.substituteName";

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
  private final T wrapped;


  /**
   * @param wrapped not null
   */
  public MofElementWrapper(T wrapped) {
    assert wrapped != null : "Expected a non-null wobject to wrap";

    this.wrapped = wrapped;
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
  public final String calculateSimpleInterfaceName() {
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

    wrap( getWrapped().getContainer() ).constructRawQualifiedName( resultBuffer );

    // additionally format according to Java rules
    result = resultBuffer.toString().replaceAll( "[^a-zA-Z0-9.$]", "" );
    result = result.toLowerCase();

    return result;
  }

  /**
   * Calculates the proper package name to hold the JMI-compatible definitions interface (base)
   * @return the non-null qualified name of the namespace of the wrapped object
   */
  public String calculateJmiPackageName() {
    return "jmi."+calculatePackageName();
  }



  /**
   * @return the qualified java class name of this element
   */
  public String calculateQualifiedInterfaceName() {
    StringBuffer result = new StringBuffer( 256 );

    result.append( calculatePackageName() );
    if ( result.length() > 0 ) {
      result.append( "." );
    }
    result.append( calculateSimpleInterfaceName() );

    return result.toString();
  }

  /**
   * @return the qualified java class name of this element
   */
  public String calculateQualifiedJmiInterfaceName() {
    StringBuffer result = new StringBuffer( 256 );

    result.append( calculateJmiPackageName() );
    if ( result.length() > 0 ) {
      result.append( "." );
    }
    result.append( calculateSimpleInterfaceName() );

    return result.toString();
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
              wrap( container ).constructRawQualifiedName( resultBuffer );
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
   */
  public static final MofElementWrapper wrap(ModelElement toWrap) throws IllegalArgumentException {
    return new MofElementWrapper( toWrap );
  }


  /**
   * Call this method in subclasses in order to instantiate the wrapper class
   * that actually corresponds to the class of the object toWrap
   * @param toWrap is the object to wrap in another wrapper class. Might be null;
   * @return null, when null provided, otherwise a properly initialized
   *         collection of wrappers that wrap the elements of toWrap
   * @throws IllegalArgumentException when mapping is not possible
   */
  public static final List<MofElementWrapper> wrap(Collection<ModelElement> toWrap) throws IllegalArgumentException {
    List<MofElementWrapper> result;

    result = new ArrayList<>();
    for (ModelElement element : toWrap) {
      result.add( new MofElementWrapper( element ) );
    }
    return result;
  }

  /**
   * Render the <code>extends &lt;interface&gt; {, &lt;interface&gt;}</code>
   * @param engine not null
   * @param context not null
   * @throws IOException
   */
  public final void renderExtends(TemplateEngine engine, TemplateContext context) throws IOException {
    engine.render( this, context );
  }

  /**
   * Render the <code>{import &lt;super-interface&gt;;}</code>
   * @param engine not null
   * @param context not null
   * @throws IOException
   */
  public final void renderImports(TemplateEngine engine, TemplateContext context) throws IOException {
    engine.render( this, context );
  }


  /**
   * @return the non-null class-proxy name, as of JMI
   */
  public String calculateQualifiedInterfaceProxyName() {
    return calculateQualifiedInterfaceProxyName()+"Class";
  }


  public String calculateQualifiedJmiInterfaceProxyName() {
    return calculateQualifiedJmiInterfaceProxyName()+"Class";
  }
}