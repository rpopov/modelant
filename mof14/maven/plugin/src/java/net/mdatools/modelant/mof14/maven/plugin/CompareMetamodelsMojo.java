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
 * @author Rusi Popov (popovr@mdatools.net)
 */
@Mojo(name="compare-mof14-metamodels",
      defaultPhase=LifecyclePhase.COMPILE
)
@Execute(phase=LifecyclePhase.COMPILE)
public class CompareMetamodelsMojo extends AbstractMojo {

  /**
   * The name of the file with the source (original) model
   */
	@Parameter(property="project.build.sourceDirectory", required=true)
  private File sourceMetamodel;

  /**
   * The name of the file with the target (changed) model
   */
	@Parameter(required=true)
  private File targetMetamodel;

  /**
   * The directory where the temporary internal files are located
   */
	@Parameter(property="project.build.directory", required=true)
  private File workDirectory;

  /**
   * Pairs of source and target model elements, that should be considered equal.
   * The model elements are referred by their qualified name, reflecting their nesting in other model
   * elements as namespaces. For example: Foundation::Core refers the MOF 1.4 model element named
   * "Core", nested in the model element named "Foundation" (as its namespace), which is a top-level
   * model element (not nested in anything).
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
