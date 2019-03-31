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

import javax.jmi.model.Association;
import javax.jmi.model.ModelPackage;
import javax.jmi.model.MofClass;
import javax.jmi.model.MofPackage;
import javax.jmi.reflect.RefPackage;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import net.mdatools.modelant.repository.api.ModelRepository;
import net.mdatools.modelant.repository.api.ModelRepositoryFactory;
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

      generateMetamodelApi((ModelPackage) sourceExtent);
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
  private void generateMetamodelApi(ModelPackage metamodelExtent) throws IOException {
    TemplateEngine engine;

    engine = TemplateEngineFactory.construct(this);

    // render the interfaces of model classes
    for (MofClass metamodelClass : (Collection<MofClass>) metamodelExtent.getMofClass().refAllOfClass()) {
      generate(engine, metamodelClass);
    }

    for (Association metamodelAssociation : (Collection<Association>) metamodelExtent.getAssociation().refAllOfClass()) {
      generate(engine, metamodelAssociation);
    }

    for (MofPackage metamodelPackage : (Collection<MofPackage>) metamodelExtent.getMofPackage().refAllOfClass()) {
      generate(engine, metamodelPackage);
    }
  }

  /**
   * @param engine
   * @param metamodelClass
   * @throws IOException when generation failed for any reason
   */
  private void generate(TemplateEngine engine, MofClass metamodelClass) throws IOException {
    MofElementWrapper<MofClass> wrapper;

    wrapper = new MofElementWrapper<>(metamodelClass );

    renderInterface( engine, wrapper);
    renderJmiInterface( engine, wrapper);

    renderInterfaceProxy( engine, wrapper);
    renderJmiInterfaceProxy( engine, wrapper);
  }

  /**
   * @throws IOException when generation failed for any reason
   */
  private void generate(TemplateEngine engine, Association metamodelAssociation) throws IOException {
    MofElementWrapper<Association> wrapper;

    wrapper = new MofElementWrapper<>(metamodelAssociation );

    renderAssociation( engine, wrapper);
    renderJmiAssociation( engine, wrapper);
  }

  /**
   * @throws IOException when generation failed for any reason
   */
  private void generate(TemplateEngine engine, MofPackage metamodelPackage) throws IOException {
    MofElementWrapper<MofPackage> wrapper;

    wrapper = new MofElementWrapper<MofPackage>(metamodelPackage);

    renderPackage( engine, wrapper);
    renderJmiPackage( engine, wrapper);
  }

  /**
   * Note: the method name is the name if the template to call
   */
  private void renderInterface(TemplateEngine engine,
                               MofElementWrapper<MofClass> wrapper) throws IOException {
    String qualifiedName;

    qualifiedName = wrapper.calculateQualifiedInterfaceName();
    engine.render( toSourceFileName(qualifiedName), wrapper);
  }

  /**
   * Note: the method name is the name if the template to call
   */
  private void renderInterfaceProxy(TemplateEngine engine,
                                    MofElementWrapper<MofClass> wrapper) throws IOException {
    String qualifiedName;

    qualifiedName = wrapper.calculateQualifiedInterfaceProxyName();
    engine.render( toSourceFileName(qualifiedName), wrapper);
  }

  /**
   * Note: the method name is the name if the template to call
   */
  private void renderJmiInterface(TemplateEngine engine,
                                  MofElementWrapper<MofClass> wrapper) throws IOException {
    String qualifiedName;

    qualifiedName = wrapper.calculateQualifiedJmiInterfaceName();
    engine.render( toSourceFileName(qualifiedName), wrapper);
  }

  /**
   * Note: the method name is the name if the template to call
   */
  private void renderJmiInterfaceProxy(TemplateEngine engine,
                                       MofElementWrapper<MofClass> wrapper) throws IOException {
    String qualifiedName;

    qualifiedName = wrapper.calculateQualifiedJmiInterfaceProxyName();

    engine.render( toSourceFileName(qualifiedName), wrapper);
  }

  /**
   * Note: the method name is the name if the template to call
   */
  private void renderAssociation(TemplateEngine engine,
                                 MofElementWrapper<Association> wrapper) throws IOException {
    String qualifiedName;

    qualifiedName = wrapper.calculateQualifiedInterfaceName();
    engine.render( toSourceFileName(qualifiedName), wrapper);
  }

  /**
   * Note: the method name is the name if the template to call
   */
  private void renderJmiAssociation(TemplateEngine engine,
                                    MofElementWrapper<Association> wrapper) throws IOException {
    String qualifiedName;

    qualifiedName = wrapper.calculateQualifiedJmiInterfaceName();
    engine.render( toSourceFileName(qualifiedName), wrapper);
  }

  /**
   * Note: the method name is the name if the template to call
   */
  private void renderPackage(TemplateEngine engine,
                             MofElementWrapper<MofPackage> wrapper) throws IOException {
    String qualifiedName;

    qualifiedName = wrapper.calculateQualifiedInterfaceName();
    engine.render( toSourceFileName(qualifiedName), wrapper);
  }

  /**
   * Note: the method name is the name if the template to call
   */
  private void renderJmiPackage(TemplateEngine engine,
                                MofElementWrapper<MofPackage> wrapper) throws IOException {
    String qualifiedName;

    qualifiedName = wrapper.calculateQualifiedJmiInterfaceName();
    engine.render( toSourceFileName(qualifiedName), wrapper);
  }

  /**
   * @param className java class name
   * @return the target absolute file to store the Java code for that class
   */
  private File toSourceFileName(String className) {
    return new File(outputDirectory,
                    className.replace( '.', File.separatorChar )+".java");
  }
}