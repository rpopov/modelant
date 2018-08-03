/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.uml13.maven.plugin.reverse;

import java.io.File;

import javax.jmi.reflect.RefPackage;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import net.mdatools.modelant.core.api.Function;
import net.mdatools.modelant.repository.api.ModelRepository;
import net.mdatools.modelant.repository.api.ModelRepositoryFactory;
import net.mdatools.modelant.uml13.reverse.ReverseXsdOperation;

/**
 * This class implements reverse engineering logic for XML schemas and storing the results as UML
 * 1.3 objects. The model produced is in fact a Platform Specific Model, which might need additional
 * processing and tuning.
 * <p>
 * Conventions for the model produced: <ul>
 * <li> The model elements (classes, association names) that represent elements in the output XML
 *      are marked with &lt;&lt;element&gt;&gt; stereotype
 * <li> For representation purposes local types (UML classes) could be introduced for XSD groups, unions,
 *      local / inlined types. All of them are marked with &lt;&lt;local type&gt;&gt; stereotype
 * <li>The column types are converted to DataType instances named: &lt;type
 *     name&gt;[_&lt;column size&gt;[_&lt;column precision&gt;]]. Additionally as tagged values named
 *     TAG_VALUE_DATA_TYPE_SIZE and TAG_VALUE_DATA_TYPE_PRECISION these values are bound to the concrete
 *     data type.
 * <li>The TAG_VALUE_DATA_TYPE_PRECISION tagged value is optional. When not provided, the precision
 *     should be treated as 0
 * <li>Any comments found while reverse engineering the XSD are bound as 'documentation' tagged
 *     values. These tagged values are compatible with the Rose's approach to documentation. They are
 *     optional.
 * </ul>
 * @author Rusi Popov
 */
@Mojo(name="xsd-to-uml13",
  defaultPhase=LifecyclePhase.COMPILE
)
@Execute(phase=LifecyclePhase.COMPILE)
public class ReverseEngineerXsdMojo extends AbstractMojo {

  /**
   * The schema file to reverse engineer
   */
	@Parameter(required=true)
  private File schemaFile;

  /**
   * The name of the file where to export the produced UML 1.3 model in XMI 1.2 format
   */
	@Parameter(required=true)
  private File outputFile;

  /**
   * The directory where to store the repository files
   */
	@Parameter(property="project.build.directory", required=true)
  private File workDir;

  /**
   * Performs the reverse engineering of the database by describing the database schema into the
   * repository provided.
   */
  public void execute() throws MojoFailureException {
    ModelRepository modelRepository;
    RefPackage extent;
    Function<File, RefPackage> reverse;

    try {
      modelRepository = ModelRepositoryFactory.construct(workDir);
      try {
        reverse = new ReverseXsdOperation( modelRepository );
        extent = reverse.execute( schemaFile );

        getLog().info( "Writing "+outputFile);
        modelRepository.writeExtent( extent,
                                     outputFile,
                                     ModelRepository.DEFAULT_XMI_VERSION );
      } finally {
        modelRepository.shutdown();
      }
    } catch (Exception ex) {
      throw new MojoFailureException("No model exported", ex);
    }
  }
}