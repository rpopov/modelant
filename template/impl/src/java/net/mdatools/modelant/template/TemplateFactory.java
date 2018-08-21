/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.template;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.jasper.compiler.TemplateCompiler;
import org.apache.jasper.compiler.TemplateDescriptor;

import net.mdatools.modelant.template.api.Template;
import net.mdatools.modelant.template.api.TemplateCompilationContext;

/**
 * Implementation of the javax.servlet.ServletContext interface that servlets see.
 * Do not instantiate this class directly!
 */
public final class TemplateFactory {
  /**
   * This is a common logger for all utils
   */
  private static final Logger LOGGER = Logger.getLogger( TemplateFactory.class.getName() );

  /**
   * Maps URI to corresponding template, optimized for faster key search
   */
  private final Map<TemplateKey, TemplateDescriptor> templates = new HashMap<>();

  /**
   * not null
   */
  private final TemplateCompiler templateCompiler;

  /**
   * @param compilationContext is the context name relative to rootPath or the context itself. It could be null.
   * @throws IOException
   */
  public TemplateFactory(TemplateCompilationContext compilationContext) throws IOException {
    this.templateCompiler = new TemplateCompiler(compilationContext);
  }


  /**
   * @param templateFiles not null template files
   * @throws IOException when the compilation fails
   */
  public void compile(List<File> templateFiles) throws IOException {
    templateCompiler.compile( templateFiles );
  }


  /**
   * Lookup the template to render the instances of the provided class either<ul>
   * <li>as an already cached template
   * or
   * <li>as a template, still not loaded, but loading it now
   * or
   * <li>as a cached decision that there is no such template for that clas, no more than one attempt to load
   *     the template is made
   * </ul>
   * @param targetClass not null
   * @param templateName not null, not empty
   * @return the template with the provided name defined for the target class or null when not found
   * @throws IOException when load or template compilation problems are found
   */
  public <T> Template<? extends T> getTemplate(Class<T> targetClass, String templateName) throws IOException {
    Template<? extends T> result;
    TemplateDescriptor templateDescriptor;
    TemplateKey key;

    LOGGER.log( Level.FINE,
                "calling template {0}/{1} ",
                new Object[]{targetClass, templateName});

    key = new TemplateKey(targetClass, templateName);
    synchronized ( templates ) {
      templateDescriptor = templates.get( key );

      if ( templateDescriptor != null ) {
        if ( templateDescriptor.isStale() ) { // the template is updated after it was loaded here

          templateCompiler.compile( Arrays.asList(templateDescriptor.getTemplateSourceFile()) );
          templateDescriptor = templateCompiler.loadTemplate(targetClass, templateName);

          templates.put( key, templateDescriptor );
        }
        result = (Template<? extends T>) templateDescriptor.getTemplate();

      } else { // still not cached

        if ( !templates.containsKey( key ) ) { // the template is still not loaded

          templateDescriptor = templateCompiler.loadTemplate(targetClass, templateName); // could be null when the template does no exist, store the decision not to attempt to load it again
          templates.put( key, templateDescriptor );

          if ( templateDescriptor != null ) {
            result = (Template<? extends T>) templateDescriptor.getTemplate();

          } else { // there is no template
            result = null;
          }
        } else { // already decided that the template does not exist
          result = null;
        }
      }
    }
    return result;
  }
}