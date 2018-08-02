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
   * @parameter
   * @required
   */
  private String template;

  /**
   * The file to produce
   * @parameter
   * @required
   */
  private File targetFile;

  /**
   * The name of the system (TODO check if there are others in MAVEN) property whose value to render
   * @parameter
   * @required
   */
  private String property;


  /**
   * This method is used by the enclosing task ForEachTask.
   */
  public final void execute() {
    TemplateEngine engine;
    Object targetObject;

    targetObject = System.getProperty( property ); // TODO USE THE COMMON MAVEN PROPERTIES
    try {
      engine = TemplateEngineFactory.construct( this );
      engine.render( targetFile, targetObject, template, null );
    } catch (Exception ex) {
      LOGGER.log( Level.SEVERE, "Compilation failed: ", ex);
    }
  }
}