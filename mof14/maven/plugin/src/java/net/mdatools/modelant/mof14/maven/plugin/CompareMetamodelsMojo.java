/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.mof14.maven.plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.jmi.reflect.RefPackage;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import net.mdatools.modelant.core.api.diff.Export;
import net.mdatools.modelant.core.api.diff.ModelComparisonResult;
import net.mdatools.modelant.core.api.match.ConsideredEqual;
import net.mdatools.modelant.core.operation.model.CompareModels;
import net.mdatools.modelant.mof14.metamodel.CompareMof14Models;
import net.mdatools.modelant.repository.api.ModelRepository;
import net.mdatools.modelant.repository.api.ModelRepositoryFactory;

/**
 * Compare two metamodels and report the differences, that are needed to convert the source metamodel into the target one
 * @author Rusi Popov
 */
@Mojo(name="compare-mof14-metamodels",
      defaultPhase=LifecyclePhase.COMPILE
)
@Execute(phase=LifecyclePhase.COMPILE)
public class CompareMetamodelsMojo extends AbstractMojo {

  /**
   * Where to generate the API sources
   */
	@Parameter(property="project.build.sourceDirectory", required=true)
  private File sourceMetamodel;

  /**
   * Where the metamodel is located
   */
	@Parameter(required=true)
  private File targetMetamodel;

  /**
   * Where the work files are located
   */
	@Parameter(property="project.build.directory", required=true)
  private File workDirectory;

  /**
   * Pairs of &lt;metaclass&gt;, &lt;metapackage&gt; as pairs of source and target metamodel classes,
   * that should be considered equal.
   */
	@Parameter
  private List<ConsideredEqual> equals;

  /**
   * The mechanism to export the result of models comparison. Default: print the string representation
   * @parameter
   */
  private Export export = Export.DEFAULT;

  public void execute() throws MojoExecutionException {
    ModelRepository repository;
    CompareModels compare;
    RefPackage sourceExtent;
    RefPackage targetExtent;
    ModelComparisonResult result;
    List<ConsideredEqual> bindings;

    // lookup the implementation in the plugin's classpath, as it should be a dependency of this plugin
    repository = ModelRepositoryFactory.construct(workDirectory);
    try {
      sourceExtent = repository.constructMetamodelExtent( "SOURCE" );
      repository.readIntoExtent( sourceExtent, sourceMetamodel );

      targetExtent = repository.constructMetamodelExtent( "TARGET" );
      repository.readIntoExtent( targetExtent, targetMetamodel );

      if (equals==null) {
        bindings = new ArrayList<>();
      } else {
        bindings = equals;
      }
      compare = new CompareMof14Models(bindings, sourceExtent);

      result = compare.execute( targetExtent );

      export.export( result );

    } catch (Exception ex) {
      throw new MojoExecutionException( "The target failed with:", ex);

    } finally {
      repository.shutdown();
    }
  }
}
