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

import org.apache.maven.project.MavenProject;

import net.mdatools.modelant.repository.api.ModelRepository;
import net.mdatools.modelant.repository.api.ModelRepositoryFactory;
import net.mdatools.modelant.template.api.TemplateCompilationContext;
import net.mdatools.modelant.template.api.TemplateEngine;
import net.mdatools.modelant.template.api.TemplateEngineFactory;

/**
 * Generate the wrapper classes and default templates for any class in the metamodel,
 * this way building the infrastructure for code generation in that language.
 * @author Rusi Popov (popovr@mdatools.net)
 */
@Mojo(name="generate-metamodel-wrapper",
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

  /**
   * The name of the product the component is of as a java identifier
   */
  @Parameter(required=true)
  private String product;

  /**
   * The simple package name of wraper classes. If missing, then the core 'wrap' is assumed
   */
  @Parameter(defaultValue="wrap", required=true)
  private String component;

  /**
   * The Java package name of the root metamodel API - find it in the metamodel XMI definition
   */
  @Parameter(required=true)
  private String metamodelRootPackage;

  /**
   * The package name of the factory class to generate
   */
  @Parameter(required=true)
  private String metamodelMofPrefix;

  @Parameter(property="project", readonly=true)
  private MavenProject project;

  public void execute() throws MojoExecutionException {
    ModelRepository repository;
    RefPackage sourceExtent;

    // lookup the implementation in the plugin's classpath, as it should be a dependency of this plugin
    repository = ModelRepositoryFactory.construct(workDirectory);
    try {
      sourceExtent = repository.constructMetamodelExtent( "SOURCE" );
      repository.readIntoExtent( sourceExtent, sourceMetamodel );

      System.out.println(project.getCompileClasspathElements());

      generateMetamodelApi(sourceExtent);
    } catch (Exception ex) {
      throw new MojoExecutionException( "The target failed with:", ex);

    } finally {
      repository.shutdown();
    }
  }

  /**
   * @param metamodelExtent not null
   */
  private void generateMetamodelApi(RefPackage metamodelExtent) {
    TemplateEngine engine;

    engine = TemplateEngineFactory.construct(constructCompilationContext());

  }

  private TemplateCompilationContext constructCompilationContext() {
    TemplateCompilationContext result;

    result = new TemplateCompilationContext() {

      /**
       * Where to compile the template
       * @see net.mdatools.modelant.template.api.TemplateCompilationContext#getClassDirectory()
       */
      public File getClassDirectory() {
        return new File("target/classes"); // relative to local run (project) directory
      }

      /**
       * Where to generate the Java files
       * @see net.mdatools.modelant.template.api.TemplateCompilationContext#getJavaDirectory()
       */
      public File getJavaDirectory() {
        return new File("target/java"); // relative to local run directory
      }

      /**
       * Where the template source is copied from test/resources/template
       * @see net.mdatools.modelant.template.api.TemplateCompilationContext#getTemplateDirectory()
       */
      public File getTemplateDirectory() {
        return new File("/template"); // relative to the backaging .jar
      }

      /**
       * @return all plugin's dependencies as classpath of the template engine and templates' compilation
       * @see net.mdatools.modelant.template.api.TemplateCompilationContext#getClassPath()
       */
      public String getClassPath() {
        StringBuilder result;

        result = new StringBuilder(1024);
        try {
          for (String path : project.getCompileClasspathElements()) {
            if (result.length() > 0) {
              result.append( File.pathSeparator );
            }
            result.append( path );
          }
        } catch (Exception ex) {
          getLog().error( "Collecting the classpath caused: ", ex);
        }
        return result.toString();
      }

      /**
       * use default
       */
      public String getTemplateEncoding() {
        return null;
      }

      public String getUniqueName() {
        return "metamodel";
      }

      public boolean shouldCompileForDebug() {
        return true;
      }

      public boolean shouldKeepGenerated() {
        return true;
      }
    };
    result.getClassDirectory().delete();
    result.getClassDirectory().mkdirs();

    return result;
  }
}
