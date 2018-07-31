/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.conversion.maven.plugin;

import java.io.File;

import javax.jmi.reflect.RefPackage;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import net.mdatools.modelant.conversion.model.ConvertUml13ToUml14;
import net.mdatools.modelant.repository.api.ModelFactory;
import net.mdatools.modelant.repository.api.ModelRepository;
import net.mdatools.modelant.repository.api.ModelRepositoryFactory;

/**
 * Convert the source UML 1.3 model to UML 1.4 and export it in XMI 1.2
 * @goal uml13-to-uml14
 * @phase compile
 * @execute phase="compile"
 * @author Rusi Popov
 */
public class ConvertUml13ToUml14Model extends AbstractMojo {

  /**
   * Where to generate the API sources
   *
   * @parameter property="project.build.sourceDirectory"
   * @required
   */
  private File sourceModel;

  /**
   * Where the metamodel is located
   *
   * @parameter
   * @required
   */
  private File targetModel;

  /**
   * Where the work files are located
   *
   * @parameter property="project.build.directory"
   * @required
   */
  private File workDirectory;

  public void execute() throws MojoExecutionException {
    ModelRepository repository;
    ModelFactory uml13Factory;
    ModelFactory uml14Factory;

    ConvertUml13ToUml14 convert;

    RefPackage uml13Extent;
    RefPackage uml14Extent;

    // lookup the implementation in the plugin's classpath, as it should be a dependency of this plugin
    repository = ModelRepositoryFactory.construct(workDirectory);
    try {
      uml13Factory = repository.loadMetamodel("UML13");
      uml14Factory = repository.loadMetamodel("UML14");

      uml13Extent = uml13Factory.instantiate("SOURCE");
      uml13Factory.readModel(uml13Extent, sourceModel);

      uml14Extent = uml14Factory.instantiate("TARGET");

      convert = new ConvertUml13ToUml14(uml13Extent);
      convert.execute( uml14Extent );

      repository.writeExtent( uml14Extent, targetModel, ModelRepository.DEFAULT_XMI_VERSION );

    } catch (Exception ex) {
      throw new MojoExecutionException( "The target failed with:", ex);

    } finally {
      repository.shutdown();
    }
  }
}
