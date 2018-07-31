/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.repository.maven;

import java.io.File;
import java.util.Date;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import net.mdatools.modelant.repository.api.ModelRepository;
import net.mdatools.modelant.repository.api.ModelRepositoryFactory;

/**
 * Creates the JMI API packages for a metamodel, creates corresponding JARs, generates JAVADOC and zips it.
 * Use it to establish new metamodels like UML 1.3, UML 1.4, CWM,...
 *
 * @goal generate-model-api
 * @phase process-sources
 * @execute phase="process-sources"
 * @author Rusi Popov
 */
public class GenerateModelApiSourceMojo extends AbstractMojo {

  /**
   * Where to generate the API sources
   *
   * @parameter property="project.build.sourceDirectory"
   * @required
   */
  private File sourceDirectory;

  /**
   * Where the metamodel is located
   *
   * @parameter
   * @required
   */
  private File metamodelFile;

  /**
   * Where the work files are located
   *
   * @parameter property="project.build.directory"
   * @required
   */
  private File workDirectory;

  /**
   * The optional javadoc header to include in the generated sources
   *
   * @parameter
   */
  private File headerFile;


  public void execute() throws MojoExecutionException {
    boolean success;
    ModelRepository repository;
    String extentName;
    boolean shouldCommit;

    // set up the directories
    if ( !sourceDirectory.exists() ) {
      success = sourceDirectory.mkdirs();
      if ( !success ) {
        throw new MojoExecutionException( "Directory "+sourceDirectory+" is not created." );
      }
    }

    // lookup the implementation in the plugin's classpath, as it should be a dependency of this plugin
    repository = ModelRepositoryFactory.construct(workDirectory);
    try {
      repository.beginWriteTransaction();
      shouldCommit = false;
      try {
        extentName = ""+(new Date().getTime());

        repository.constructMetamodelExtent( extentName );
        try {
          repository.readIntoExtent( extentName, metamodelFile );
          repository.mapJava( sourceDirectory,
                              extentName, // unique extent name
                              headerFile );
        } finally {
          repository.deleteExtent( extentName );
        }
        shouldCommit = true;
      } finally {
        repository.endTransaction( shouldCommit );
      }
    } catch (Exception ex) {
      throw new MojoExecutionException( "The generation of API for "+metamodelFile+" failed with:", ex);

    } finally {
      repository.shutdown();
    }
  }
}
