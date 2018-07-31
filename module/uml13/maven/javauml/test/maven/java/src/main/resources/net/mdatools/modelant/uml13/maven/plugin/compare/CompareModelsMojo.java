/*
 * Copyright (c) 2017 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Created on 17.08.2017
 */
package net.mdatools.modelant.uml13.maven.plugin.compare;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.jmi.reflect.RefPackage;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import net.mdatools.modelant.core.api.diff.Export;
import net.mdatools.modelant.core.api.diff.ModelComparisonResult;
import net.mdatools.modelant.core.api.match.ConsideredEqual;
import net.mdatools.modelant.core.operation.model.CompareModels;
import net.mdatools.modelant.repository.api.ModelFactory;
import net.mdatools.modelant.repository.api.ModelRepository;
import net.mdatools.modelant.repository.api.ModelRepositoryFactory;
import net.mdatools.modelant.uml13.metamodel.CompareUml13Models;

/**
 * Compare two UML 1.3 models and report the differences, that are needed to convert the source model
 * into the target one.
 * @goal compare-models
 * @phase process-sources
 * @execute phase="process-sources"
 * @author Rusi Popov
 */
public class CompareModelsMojo extends AbstractMojo {

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

  /**
   * Pairs of &lt;metaclass&gt;, &lt;metapackage&gt; as pairs of source and target metamodel classes,
   * that should be considered equal.
   * @parameter
   */
  private List<ConsideredEqual> equals;

  /**
   * The mechanism to export the result of models comparison. Default: print the string representation
   * @parameter
   */
  private Export export = Export.DEFAULT;

  public void execute() throws MojoExecutionException {
    ModelRepository repository;
    ModelFactory metamodelFactory;
    CompareModels compare;
    RefPackage sourceExtent;
    RefPackage targetExtent;
    ModelComparisonResult result;
    List<ConsideredEqual> bindings;

    // lookup the implementation in the plugin's classpath, as it should be a dependency of this plugin
    repository = ModelRepositoryFactory.construct(workDirectory);
    try {
      metamodelFactory = repository.loadMetamodel("UML13");
      sourceExtent = metamodelFactory.instantiate("SOURCE");
      targetExtent = metamodelFactory.instantiate("TARGET");

      repository.readIntoExtent( sourceExtent, sourceModel );
      repository.readIntoExtent( targetExtent, targetModel );

      if (equals==null) {
        bindings = new ArrayList<>();
      } else {
        bindings = equals;
      }
      compare = new CompareUml13Models(bindings, sourceExtent);

      result = compare.execute( targetExtent );

      export.export( result );

    } catch (Exception ex) {
      throw new MojoExecutionException( "The target failed with:", ex);

    } finally {
      repository.shutdown();
    }
  }
}
