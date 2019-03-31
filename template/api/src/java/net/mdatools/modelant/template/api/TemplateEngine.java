/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.template.api;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * This class gives a simple interface to the ModelAnt Object-Oriented templates mechanism.
 * <pre>
 * Usage:
 *   To generate and initiate the output creation:
 *
 *     targetFile = new File( fileName );
 *     templateEngine.render( targetFile, targetObject, template, getProperties() );
 *
 *
 *   In regular Java classes use as part of output creation:
 *
 *     public void renderXYZ(context) {
 *       TemplateEngine.render( this, context );
 *     }
 *
 *     this will use the renderXYZ template to process *this* object
 * </pre>
 * @see Template
 * @author Rusi Popov (popovr@mdatools.net), 2002, 2012, 2017
 */
public interface TemplateEngine {

  /**
   * Use the template for the target object's class and render that object into a file. When the same template is called many times, it is really
   * better to implement the call in a method, call it directly, hiding the template's name, thus reducing the complexity
   * and allowing compile-time check.
   * @param targetFile is the non-null file to create to store the template's output
   * @param targetObject is the non-null object to call the template of/for
   * @param template is the non-null, non-empty template name
   * @param bindings is a possibly null map to provide global context properties of the template.
   *                 The template may modify its contents.
   * @throws IOException
   */
  void render(File targetFile,
              Object targetObject,
              String template,
              Map<String, Object> bindings) throws IOException;
  /**
   * Use the template for the target object's class and render that object into a file. When the same template is called many times, it is really
   * better to implement the call in a method, call it directly, hiding the template's name, thus reducing the complexity
   * and allowing compile-time check.
   * @param targetFile is the non-null file to create to store the template's output
   * @param targetObject is the non-null object to call the template of/for
   * @param template is the non-null, non-empty template name
   * @throws IOException
   */
  void render(File targetFile,
              Object targetObject,
              String template) throws IOException;

  /**
   * Use the template for the target object's class and render that object into a file.
   * Uses the calling method name as the name of the template to apply.
   * @param targetFile is the non-null file to create to store the template's output
   * @param targetObject is the non-null object to call the template of/for
   * @throws IOException
   */
  void render(File targetFile,
              Object targetObject) throws IOException;

  /**
   * Use the template for the target object's class and render that object to a string.
   * @param targetObject is the non-null object to call the template of/for
   * @param template is the non-null, non-empty template name
   * @param bindings is a possibly null map to provide global context properties of the template.
   *                 The template may modify its contents.
   * @return the result of template's rendering
   * @throws IOException
   */
  String render(Object targetObject,
                String template,
                Map<String, Object> bindings) throws IOException;

  /**
   * Use the template for the target object's class and render that object to a string.
   * @param targetObject is the non-null object to call the template of/for
   * @param template is the non-null, non-empty template name
   * @return the result of template's rendering
   * @throws IOException
   */
  String render(Object targetObject, String template) throws IOException;

  /**
   * This method calls the template for the target object, as part of a rendering sequence.
   * When the same template is called many times, it is really better to implement
   * the call in a method, call it directly, hiding the template's name, thus reducing the complexity
   * and allowing compile-time check.
   * IMPORTANT: This method does NOT provide compile time correctness checking of the template names.
   * @param targetObject not null object to render
   * @param context not null
   * @param template not nullS
   * @throws IOException
   */
  void render(Object targetObject,
              TemplateContext context,
              String template) throws IOException;


  /**
   * This method uses <b>the name of the calling method</b> as the name of the template to call.
   * @param targetObject not null object to render
   * @param context not null
   * @throws IOException
   */
  void render(Object targetObject, TemplateContext context) throws IOException;


  /**
   * This method renders the collection of model elements using the render method NAME provided.
   * It internally maps each of them to a ModelAnt Template before calling its
   * <b>render(pageContext, methodName)</b> method<pre>
   * Usage:
   *   render( collection, "renderUVW", context);
   *
   *   where renderUVW is the name of the template to call
   * </pre>
   * @param collection is a non-null collection model elements
   * @param template is a non-null name of a method to call for each element of that collection
   * @param context not null
   * @param <T> the type of the objects held in the collection. This should guarantee the minimum template presence.
   * @throws IOException
   */
  <T> void render(Collection<T> collection, String template, TemplateContext context) throws IOException;


  /**
   * This method renders the collection of model elements
   * by delegating it to the renderer provided
   * @param collection is a non-null collection model elements
   * @param template is a non-null renderer to call for each element of that collection
   * @param context not null
   * @throws IOException
   */
  <T> void render(Collection<T> collection, Template<T> template, TemplateContext context) throws IOException;


  /**
   * Compile the templates held as local files
   * @param templateFiles not null
   * @throws IOException
   */
  void compile(List<File> templateFiles) throws IOException;
}