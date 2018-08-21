/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.template.maven.plugin;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import net.mdatools.modelant.template.api.TemplateEngine;
import net.mdatools.modelant.template.api.TemplateEngineFactory;

/**
 * Apply the named template on the value of the named system property, in order to produce the contents of
 * the target file.
 * @author Rusi Popov
 */
@Mojo(name="apply-templates",
      defaultPhase=LifecyclePhase.COMPILE
)
@Execute(phase=LifecyclePhase.COMPILE)
public class ApplyTemplateMojo extends CompilationContext {

  /**
   * This is a common logger for all utils
   */
  private static final Logger LOGGER = Logger.getLogger( ApplyTemplateMojo.class.getName() );

  /**
   * The name of the template to apply
   */
	@Parameter(required=true)
  private String template;

  /**
   * The file to produce
   */
	@Parameter(required=true)
  private File targetFile;

  /**
   * The value (usually a maven property value) to render
   */
	@Parameter(required=true)
  private Object targetObject;


  /**
   * This method is used by the enclosing task ForEachTask.
   */
  public final void execute() {
    TemplateEngine engine;

    try {
      engine = TemplateEngineFactory.construct( this );
      engine.render( targetFile, targetObject, template, null );
    } catch (Exception ex) {
      LOGGER.log( Level.SEVERE, "Compilation failed: ", ex);
    }
  }
}