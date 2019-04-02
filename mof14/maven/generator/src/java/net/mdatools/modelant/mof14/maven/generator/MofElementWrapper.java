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
import java.util.List;
import java.util.logging.Logger;

import javax.jmi.model.GeneralizableElement;
import javax.jmi.model.ModelElement;

import net.mdatools.modelant.mof14.maven.generator.name.ConstructName;
import net.mdatools.modelant.mof14.maven.generator.name.ConstructQualifiedName;
import net.mdatools.modelant.mof14.maven.generator.name.DecorateNameWithTag;
import net.mdatools.modelant.mof14.maven.generator.name.GetName;
import net.mdatools.modelant.template.api.TemplateContext;
import net.mdatools.modelant.template.api.TemplateEngine;

/**
 * This class holds common methods to manage / query a MOF Objects repository
 *
 * @author Rusi Popov
 * @param <T> the MOF type of the wrapped element
 */
public class MofElementWrapper<T extends ModelElement> {

  private static final String JMI_CLASS_PROXY_SUFFIX = "Class";

  private static final Logger LOGGER = Logger.getLogger( MofElementWrapper.class.getName() );

  /**
   * The object this wrapper class wraps to allow its rendering. The reflective
   * interface of the model element is used in oder to make this class
   * independent of the actual model and interface.
   */
  private final T wrapped;

  private static final ConstructName constructSimpleName =
      new DecorateNameWithTag( DecorateNameWithTag.JAVAX_JMI_SUBSTITUTE_NAME,
                               new GetName() );

  private static final ConstructName constructQualifiedName = new ConstructQualifiedName( constructSimpleName );

  /**
   * Construct the package name as of JMI
   */
  private static final ConstructName constructJmiQualifiedName = new ConstructQualifiedName( constructSimpleName, "jmi" );

  /**
   * @param wrapped not null
   */
  public MofElementWrapper(T wrapped) {
    assert wrapped != null : "Expected a non-null wobject to wrap";

    this.wrapped = wrapped;
  }


  /**
   * @return the name of the model element as provided in the meta-model and optionally overridden
   *         through JMI flags, if it violates Java naming conventions
   */
  public final String calculateSimpleInterfaceName() {
    return constructSimpleName.constructName( getWrapped() );
  }

  /**
   * @return the name of the model element as provided in the meta-model and optionally overridden
   *         through JMI flags, if it violates Java naming conventions
   */
  public final String calculateSimpleInterfaceProxyName() {
    return calculateSimpleInterfaceName()+JMI_CLASS_PROXY_SUFFIX;
  }
  /**
   * Calculates the proper package name, regarding the name substitution and the package name
   * prefix.
   * @return the non-null qualified name of the namespace of the wrapped object
   */
  public String calculatePackageName() {
    return constructQualifiedName.constructName( getWrapped().getContainer() );
  }

  /**
   * Calculates the proper package name to hold the JMI-compatible definitions interface (base)
   * @return the non-null qualified name of the namespace of the wrapped object
   */
  public String calculateJmiPackageName() {
    return constructJmiQualifiedName.constructName( getWrapped().getContainer() );
  }


  /**
   * @return the qualified java class name of this element
   */
  public String calculateQualifiedInterfaceName() {
    return constructQualifiedName.constructName( getWrapped() );
  }

  /**
   * @return the non-null class-proxy name, as of JMI
   */
  public String calculateQualifiedInterfaceProxyName() {
    return calculateQualifiedInterfaceName()+JMI_CLASS_PROXY_SUFFIX;
  }


  /**
   * @return the qualified java class name of this element
   */
  public String calculateQualifiedJmiInterfaceName() {
    return constructJmiQualifiedName.constructName( getWrapped() );
  }


  /**
   * @return the name of the class/proxy class from the main interface
   */
  public String calculateQualifiedJmiInterfaceProxyName() {
    return calculateQualifiedJmiInterfaceName()+JMI_CLASS_PROXY_SUFFIX;
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
  public static final MofElementWrapper<ModelElement> wrap(ModelElement toWrap) throws IllegalArgumentException {
    return new MofElementWrapper<>( toWrap );
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
   * Render the <code>extends &lt;package proxy&gt;</code>
   * @param engine not null
   * @param context not null
   * @throws IOException
   */
  public final void renderPackageExtends(TemplateEngine engine, TemplateContext context) throws IOException {
    engine.render( this, context );
  }

  /**
   * Render the <code>[package &lt;super-interface&gt;]</code>
   * @param engine not null
   * @param context not null
   * @throws IOException
   */
  public final void renderStatementPackage(TemplateEngine engine, TemplateContext context) throws IOException {
    engine.render( this, context );
  }

  /**
   * Render the <code>extends &lt;interface&gt; {, &lt;interface&gt;}</code>
   * @param engine not null
   * @param context not null
   * @throws IOException
   */
  public final void renderStatementExtends(TemplateEngine engine, TemplateContext context) throws IOException {
    engine.render( this, context );
  }

  /**
   * Render the <code>{import &lt;super-interface&gt;;}</code>
   * @param engine not null
   * @param context not null
   * @throws IOException
   */
  public final void renderStatementImports(TemplateEngine engine, TemplateContext context) throws IOException {
    engine.render( this, context );
  }
}