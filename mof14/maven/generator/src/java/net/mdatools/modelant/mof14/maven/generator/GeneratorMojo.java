/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.mof14.maven.generator;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.jmi.model.EnumerationType;
import javax.jmi.model.ModelElement;
import javax.jmi.model.MofClass;
import javax.jmi.reflect.RefPackage;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.descriptor.PluginDescriptor;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

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
   * The directory where to compile the templates in, relative to local run (project) directory
   */
  private static final String GENERATE_TEMPLATE_CLASSES_DIR = "target/classes";

  /**
   * The directory to generate JAVA from template files, relative to local run directory
   */
  private static final String GENERATE_TEMPLATE_JAVA_DIR = "target/java";

  /**
   * The directory with template sources, relative to the MOJO's packaging .jar
   */
  public static final String TEMPLATE_ROOT_PATH = "/template";

  /**
   * The name of the file with the source (original) model
   */
  @Parameter(required=true)
  private File sourceMetamodel;

  /**
   * The directory where to generate the result files
   */
  @Parameter(property="project.output.directory", required=true)
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

  public void execute() throws MojoExecutionException {
    ModelRepository repository;
    RefPackage sourceExtent;

    // lookup the implementation in the plugin's classpath, as it should be a dependency of this plugin
    repository = ModelRepositoryFactory.construct(workDirectory);
    try {
      sourceExtent = repository.constructMetamodelExtent( "SOURCE" );
      repository.readIntoExtent( sourceExtent, sourceMetamodel );

      generateMetamodelApi(sourceExtent);
    } catch (Exception ex) {
      throw new MojoExecutionException( "The target failed with:", ex);

    } finally {
      repository.shutdown();
    }
  }

  /**
   * @param metamodelExtent not null
   * @throws IOException
   */
  private void generateMetamodelApi(RefPackage metamodelExtent) throws IOException {
    TemplateEngine engine;

    engine = TemplateEngineFactory.construct(constructCompilationContext());

    // render the interfaces of model classes
    for (MofClass metamodelClass : (Collection<MofClass>) metamodelExtent.refClass( "Class" ).refAllOfClass()) {
      generate(engine, metamodelClass);
    }

    // render interfaces for enumerations
    for (EnumerationType metamodelEnum : (Collection<EnumerationType>) metamodelExtent.refClass( "EnumerationType" ).refAllOfClass()) {
      generate(engine, metamodelEnum);
    }
  }

  /**
   * @param engine
   * @param metamodelClass
   * @throws IOException when generation failed for any reason
   */
  private void generate(TemplateEngine engine, ModelElement metamodelClass) throws IOException {
    render( engine, metamodelClass, "renderClass" );
  }

  /**
   * @param engine not null
   * @param metamodelEnum
   * @throws IOException
   */
  private void generate(TemplateEngine engine, EnumerationType metamodelEnum) throws IOException {
    render( engine, metamodelEnum, "renderClass" );
  }

  private void render(TemplateEngine engine, ModelElement metamodelClass, String template) throws IOException {
    MofElementWrapper wrapper;
    String qualifiedName;
    File outputFile;
    Map<String, Object> parameters;

    wrapper = new MofElementWrapper(metamodelClass );

    qualifiedName = wrapper.calculateQualifiedWrapperClassName( component );
    outputFile = new File(targetDirectory,
                          qualifiedName.replace( '.', File.separatorChar )+".java");

    parameters = new HashMap<>();
    parameters.put( "component", component );

    engine.render( outputFile,
                   wrapper,
                   template,
                   parameters);
  }

  private TemplateCompilationContext constructCompilationContext() {
    TemplateCompilationContext result;

    result = new TemplateCompilationContext() {

      /**
       * Where to compile the template
       * @see net.mdatools.modelant.template.api.TemplateCompilationContext#getClassDirectory()
       */
      public File getClassDirectory() {
        return new File(GENERATE_TEMPLATE_CLASSES_DIR);
      }

      /**
       * Where to generate the Java files
       * @see net.mdatools.modelant.template.api.TemplateCompilationContext#getJavaDirectory()
       */
      public File getJavaDirectory() {
        return new File(GENERATE_TEMPLATE_JAVA_DIR);
      }

      /**
       * Where the template source is copied from src/template
       * @see net.mdatools.modelant.template.api.TemplateCompilationContext#getTemplateDirectory()
       */
      public File getTemplateDirectory() {
        return new File(TEMPLATE_ROOT_PATH);
      }

      /**
       * @return all plugin's dependencies as classpath of the template engine and templates' compilation
       * @see net.mdatools.modelant.template.api.TemplateCompilationContext#getClassPath()
       */
      public String getClassPath() {
        StringBuilder result;
        PluginDescriptor pluginDescriptor;

        result = new StringBuilder(1024);

        pluginDescriptor = (PluginDescriptor) getPluginContext().get( "pluginDescriptor" );
        if ( pluginDescriptor != null ) {
          try {
            for (URL path : pluginDescriptor.getClassRealm().getURLs()) {
              if (result.length() > 0) {
                result.append( File.pathSeparator );
              }
              result.append( path.toString() );
            }
          } catch (Exception ex) {
            getLog().error( "Collecting the classpath caused: ", ex);
          }
        }

        if ( getLog().isDebugEnabled() ) {
          getLog().debug( "Collected plugin classpath: "+result);
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
