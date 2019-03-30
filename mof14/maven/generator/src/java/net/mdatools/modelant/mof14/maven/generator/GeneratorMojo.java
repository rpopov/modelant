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
import java.util.Collection;

import javax.jmi.model.EnumerationType;
import javax.jmi.model.ModelElement;
import javax.jmi.model.MofClass;
import javax.jmi.reflect.RefPackage;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import net.mdatools.modelant.repository.api.ModelRepository;
import net.mdatools.modelant.repository.api.ModelRepositoryFactory;
import net.mdatools.modelant.template.api.TemplateCompilationContext;
import net.mdatools.modelant.template.api.TemplateEngine;
import net.mdatools.modelant.template.api.TemplateEngineFactory;
import net.mdatools.modelant.template.maven.plugin.CompilationContext;

/**
 * In the outputDirectory generate the metamodel API interfaces and default templates for any
 * class in the metamodel, this way building the infrastructure for code generation in that language.
 * @author Rusi Popov (popovr@mdatools.net)
 */
@Mojo(name="generate-metamodel-wrapper",
      defaultPhase=LifecyclePhase.GENERATE_SOURCES
)
@Execute(phase=LifecyclePhase.GENERATE_SOURCES)
public class GeneratorMojo extends CompilationContext {

  /**
   * The name of the file with the source (original) model
   */
  @Parameter(required=true)
  private File sourceMetamodel;

  /**
   * The directory where to generate the result files
   */
  @Parameter(defaultValue="${project.build.directory}/generated-sources", required=true)
  private File outputDirectory;

  /**
   * @throws MojoExecutionException
   * @see org.apache.maven.plugin.Mojo#execute()
   */
  public void execute() throws MojoExecutionException {
    ModelRepository repository;
    RefPackage sourceExtent;

    repository = ModelRepositoryFactory.construct(getClassDirectory());
    try {
      sourceExtent = repository.constructMetamodelExtent( "SOURCE" );
      repository.readIntoExtent( sourceExtent, sourceMetamodel );

      outputDirectory.mkdirs();

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
   * This MOJO actually defines the compilation context
   * @return
   */
  private TemplateCompilationContext constructCompilationContext() {
    return this;
  }

  /**
   * @param engine
   * @param metamodelClass
   * @throws IOException when generation failed for any reason
   */
  private void generate(TemplateEngine engine, ModelElement metamodelClass) throws IOException {
    MofElementWrapper wrapper;

    wrapper = new MofElementWrapper(metamodelClass );

    renderInyterface( engine, wrapper);
    renderJmiInterface( engine, wrapper);
  }

  private void renderInyterface(TemplateEngine engine,
                                MofElementWrapper wrapper) throws IOException {
    String qualifiedName;
    File outputFile;

    qualifiedName = wrapper.calculateQualifiedInterfaceName();
    outputFile = new File(outputDirectory,
                          qualifiedName.replace( '.', File.separatorChar )+".java");

    engine.render( outputFile,
                   wrapper,
                   "renderInterface");
  }

  private void renderJmiInterface(TemplateEngine engine,
                                  MofElementWrapper wrapper) throws IOException {
    String qualifiedName;
    File outputFile;

    qualifiedName = wrapper.calculateQualifiedJmiInterfaceName();
    outputFile = new File(outputDirectory,
                          qualifiedName.replace( '.', File.separatorChar )+".java");

    engine.render( outputFile,
                   wrapper,
                   "renderJmiInterface");
  }
}
