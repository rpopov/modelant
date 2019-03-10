/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.mof14.maven.generator;

import java.io.File;

import javax.jmi.reflect.RefPackage;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import net.mdatools.modelant.repository.api.ModelRepository;
import net.mdatools.modelant.repository.api.ModelRepositoryFactory;

/**
 * Generate the wrapper classes and default templates for any class in the metamodel,
 * this way building the infrastructure for code generation in that language.
 * @author Rusi Popov (popovr@mdatools.net)
 */
@Mojo(name="generate",
      defaultPhase=LifecyclePhase.GENERATE_SOURCES
)
@Execute(phase=LifecyclePhase.GENERATE_SOURCES)
public class GeneratorMojo extends AbstractMojo {

  /**
   * The name of the file with the source (original) model
   */
  @Parameter(required=true)
  private File sourceMetamodel;

  /**
   * The directory where to generate the source files
   */
  @Parameter(property="project.generate.directory", required=true)
  private File targetDirectory;

  /**
   * The directory where the temporary internal files are located
   */
  @Parameter(property="project.build.directory", required=true)
  private File workDirectory;

  public void execute() throws MojoExecutionException {
    ModelRepository repository;
    RefPackage sourceExtent;

    // lookup the implementation in the plugin's classpath, as it should be a dependency of this plugin
    repository = ModelRepositoryFactory.construct(workDirectory);
    try {
      sourceExtent = repository.constructMetamodelExtent( "SOURCE" );
      repository.readIntoExtent( sourceExtent, sourceMetamodel );

    } catch (Exception ex) {
      throw new MojoExecutionException( "The target failed with:", ex);

    } finally {
      repository.shutdown();
    }
  }
}
