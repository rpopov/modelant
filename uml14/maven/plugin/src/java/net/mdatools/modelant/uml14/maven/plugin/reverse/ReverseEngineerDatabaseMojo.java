/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.uml14.maven.plugin.reverse;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.jmi.reflect.RefPackage;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import net.mdatools.modelant.core.api.Function;
import net.mdatools.modelant.repository.api.ModelRepository;
import net.mdatools.modelant.repository.api.ModelRepositoryFactory;
import net.mdatools.modelant.uml14.reverse.ReverseDatabaseOperation;

/**
 * Reverse engineering logic for database schemas and storing the results as
 * UML 1.3 objects. The model produced is in fact a Platform Specific Model, which might need
 * additional processing and tuning.
 * Conventions for the model produced:
 * <ol>
 * <li>The database column types are converted to DataType instances named: &lt;type
 * name&gt;[_&lt;column size&gt;[_&lt;column precision&gt;]]. Additionally as tagged values named
 * {@link net.mdatools.modelant.uml13.metamodel.Convention.TAG_VALUE_DATA_LENGTH} and {@link net.mdatools.modelant.uml13.metamodel.Convention.TAG_VALUE_DATA_TYPE_PRECISION}
 * these values are bound to the concrete data type.
 * <li>The {@link net.mdatools.modelant.uml13.metamodel.Convention.TAG_VALUE_DATA_TYPE_PRECISION} tagged value is optional. When not provided, the precision
 * should be treated as 0
 * <li>The {@link net.mdatools.modelant.uml13.metamodel.Convention.TAG_VALUE_DATA_LENGTH} tagged value is mandatory.
 * <li>Any comments found while reverse engineering the database are bound as 'documentation' tagged
 * values. These tagged values are compatible with the Rose's approach to documentation. They are
 * optional.
 * <li>Each attribute pertaining to the table's primary key is bound a {@link net.mdatools.modelant.uml13.metamodel.Convention.TAG_VALUE_PRIMARY_KEY} tagged value
 * with "Primaty Key" value. Its value is the sequence order of the column in the tible's primary key.
 * </ol>
 *
 * @author Rusi Popov (popovr@mdatools.net)
 */
@Mojo(name="database-to-uml13",
  defaultPhase=LifecyclePhase.COMPILE
)
@Execute(phase=LifecyclePhase.COMPILE)
public class ReverseEngineerDatabaseMojo extends AbstractMojo {

  /**
   * Database driver-specific URL
   */
	@Parameter(required=true)
  private String url;

  /**
   * Database user to connect the database
   */
	@Parameter(required=true)
  private String user;

  /**
   * Database user's password
   */
	@Parameter(required=true)
  private String password;

  /**
   * The java class name of the database driver to connect the database.
   * The .jar with that class file should be provided as a dependency of this plugin
   */
	@Parameter(required=true)
  private String driver;

  /**
   * Optional catalog / database name to restrict the table selection.
   * For some databases it is mandatory. Case sensitive.
   */
  @Parameter(required=false)
  private String catalog;

  /**
   * A comma-separated list of database schemes to reverse engineer into a single model.
   * Case sensitive.
   */
	@Parameter(required=true)
  private String[] schema;

  /**
   * The name of the file where to export the produced UML 1.3 model in XMI 1.2 format
   */
	@Parameter(required=true)
  private File outputFile;

  /**
   * The directory where to store the repository files
   */
	@Parameter(property="project.build.directory",required=true)
  private File workDir;

  /**
   * Performs the reverse engineering of the database by describing the database schema into the
   * repository provided.
   * @throws MojoExecutionException
   */
  public void execute() throws MojoExecutionException {
    Connection connection;
    ModelRepository modelRepository;
    RefPackage extent;
    Function<Connection, RefPackage> operation;

    try {
      modelRepository = ModelRepositoryFactory.construct(workDir);
      try {
        Class.forName( driver );
        connection = DriverManager.getConnection( url, user, password );
        try {
          operation = new ReverseDatabaseOperation(modelRepository, catalog, schema);
          extent = operation.execute( connection );
        } finally {
          connection.close();
        }
        modelRepository.writeExtent( extent,
                                     outputFile,
                                     ModelRepository.DEFAULT_XMI_VERSION);
      } finally {
        modelRepository.shutdown();
      }
    } catch (SQLException e) {
      throw new MojoExecutionException( "Connecting database:" + url + " with user:" + user + " and password:" + password
                                        + " caused exception:", e);
    } catch (ClassNotFoundException e) {
      throw new MojoExecutionException( "Driver class " + driver + " not found", e);

    } catch (Exception e) {
      throw new MojoExecutionException( "", e);
    }
  }
}