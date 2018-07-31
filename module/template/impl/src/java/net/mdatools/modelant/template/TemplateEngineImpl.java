/*
 * Copyright (c) 2001,2012 Rusi Popov, MDA Tools.net
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Rusi Popov (popovr@mdatools.net) - initial implementation
 */
package net.mdatools.modelant.template;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.mdatools.modelant.template.api.Template;
import net.mdatools.modelant.template.api.TemplateCompilationContext;
import net.mdatools.modelant.template.api.TemplateContext;
import net.mdatools.modelant.template.api.TemplateEngine;

/**
 * Provide a simple interface to the ModelAnt Object-Oriented templates mechanism.
 * Some notions:<ul>
 * <li><b>context</b> is a named set of templates for a set of classes
 * </ul><pre>
 * Usage:
 *   To generate and initiate the output creation:
 *
 *     targetFile = new File( fileName );
 *     TemplateEngine.render( targetFile, targetObject, context, template, getProperties() );
 *
 *
 *   In ModelAnt Templates use:
 *
 *       TemplateEngine.render( target object, template name, request, response );
 *
 *   In regular Java classes use as part of output creation:
 *
 *     public void renderXYZ(ServletRequest request, ServletResponse response) {
 *       TemplateEngine.render( this, request, response );
 *     }
 * </pre>
 * @author Rusi Popov, 2002, 2012, 2017
 */
class TemplateEngineImpl implements TemplateEngine {

  /**
   * Initialize the method-identification framework, based on the PRIVATE methods of
   * Throwable class
   */
  private static final Method getStackTraceDepthMethod;

  private static final Method getStackTraceElementMethod;
  static {
    try {
      getStackTraceDepthMethod = Throwable.class.getDeclaredMethod( "getStackTraceDepth", (Class[]) null );
      getStackTraceDepthMethod.setAccessible( true );

      getStackTraceElementMethod = Throwable.class.getDeclaredMethod( "getStackTraceElement", new Class[] { int.class } );
      getStackTraceElementMethod.setAccessible( true );
    } catch (Exception ex) {
      throw new RuntimeException( "Cannot initialize render methods identification mechanism", ex );
    }
  }

  /**
   */
  private final TemplateFactory templateFactory;

  /**
   * Encoding of the files/writers to produce
   */
  private final String outputEncoding;

  /**
   * @param compilationContext not null
   * @throws IOException
   */
  TemplateEngineImpl(TemplateCompilationContext compilationContext) throws IOException {
    this.templateFactory = new TemplateFactory( compilationContext );
    this.outputEncoding = compilationContext.getTemplateEncoding();
  }

  /**
   * @see net.mdatools.modelant.template.api.TemplateEngine#render(java.io.File, java.lang.Object, java.lang.String, java.util.Map)
   */
  public final void render(File targetFile,
                           Object targetObject,
                           String template,
                           Map<String, Object> bindings) throws IOException {
    PrintWriter out;
    TemplateContext context;

    out = new PrintWriter(new OutputStreamWriter(new FileOutputStream( targetFile.getAbsoluteFile() ), outputEncoding));
    try {
      context = new TemplateContextImpl(out, bindings);

      render( targetObject, template, context );
    } finally {
      out.close();
    }
  }

  /**
   * @see net.mdatools.modelant.template.api.TemplateEngine#render(java.lang.Object, java.lang.String, java.util.Map)
   */
  public final String render(Object targetObject,
                             String template,
                             Map<String, Object> bindings) throws IOException {
    PrintWriter out;
    TemplateContext context;
    StringWriter writer;

    writer = new StringWriter();

    out = new PrintWriter(writer);
    try {
      context = new TemplateContextImpl(out, bindings);

      render( targetObject, template, context );
    } finally {
      out.close();
    }
    return writer.toString();
  }

  /**
   * This method calls the template for the target object, as part of a rendering sequence.
   * When the same template is called many times, it is really better to implement
   * the call in a method, call it directly, hiding the template's name, thus reducing the complexity
   * and allowing compile-time check.
   * IMPORTANT: This method does NOT provide compile time correctness checking of the template names.
   * @param targetObject not null
   * @param method
   * @throws IOException
   */
  public final void render(Object targetObject,
                           String method,
                           TemplateContext templateContext) throws IOException {
    Template template;
    Class targetClass;

    if ( targetObject == null ) {
      throw new IllegalArgumentException("Expected a npon-null object to render");
    }
    targetClass = targetObject.getClass();
    template = templateFactory.getTemplate( targetClass, method );

    // TODO: IMPLEMENT THE DYNAMIC LOOKUP OF TEMPLATES & INHERITANCE FROM SUPERCLASS/SUPERINTERFACE

    template.render( targetObject, this, templateContext );
  }

  /**
   * This method uses <b>the name of the calling method</b> as the name of the template to call.
   * @param targetObject
   * @param context
   * @throws IOException
   */
  public final void render(Object targetObject,
                           TemplateContext context) throws IOException {
    StackTraceElement frame;
    Throwable stackHandle;

    // find name of the calling method and use it as template name
    try {
      stackHandle = new Throwable();
      frame = (StackTraceElement) getStackTraceElementMethod.invoke( stackHandle,
                                                                     new Object[] { new Integer( 1 ) } );
    } catch (Exception ex) {
      throw new RuntimeException( "Cannot find calling method", ex );
    }
    render( targetObject, frame.getMethodName(), context );
  }

  /**
   * This method renders the collection of model elements using the render method NAME provided.
   * It internally maps each of them to a ModelAnt Template before calling its
   * <b>render(pageContext, methodName)</b> method<pre>
   * Usage:
   *   render( collection, "renderUVW", request, response);
   *
   *   where renderUVW is the name of the template to call
   * </pre>
   * @param collection is a non-null collection model elements
   * @param template is a non-null name of a method to call for each element of that collection
   * @param request
   * @param response
   * @throws IOException
   */
  public final <T> void render(Collection<T> collection,
                               String template,
                               TemplateContext context) throws IOException {
    Iterator<T> iterator;

    iterator = collection.iterator();
    while ( iterator.hasNext() ) {
      render( iterator.next(), template, context );
    }
  }

  /**
   * This method renders the collection of model elements
   * by delegating it to the renderer provided
   * @param collection is a non-null collection model elements
   * @param template is a non-null renderer to call for each element of that collection
   * @param context
   * @throws IOException
   */
  public final <T> void render(Collection<T> collection,
                               Template<T> template,
                               TemplateContext context) throws IOException {
    Iterator<T> iterator;

    iterator = collection.iterator();
    while ( iterator.hasNext() ) {
      template.render( iterator.next(), this, context);
    }
  }

  /**
   * @see net.mdatools.modelant.template.api.TemplateEngine#compileTemplate(java.util.List)
   */
  public void compile(List<File> templateFiles) throws IOException {
    templateFactory.compile( templateFiles );
  }
}