/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.repository.api;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.jmi.model.ModelPackage;
import javax.jmi.reflect.InvalidNameException;
import javax.jmi.reflect.RefPackage;
import javax.jmi.xmi.MalformedXMIException;

import org.netbeans.api.mdr.CreationFailedException;

/**
 * The access to the MetaData Repository in a controlled way. <pre>
 * Usage:
 *   repository = ModelRepositoryFactory.construct(new File(storage dir));
 *   try {
 *     repository.beginTransaction();
 *     try {
 *
 *       manipulate
 *
 *       shoulCommit = true;
 *     } catch (Exception ex) {
 *       shoulCommit = false;
 *     } finally {
 *       repository.endTransaction(shoulCommit);
 *     }
 *   } finally {
 *     repository.shutdown()
 *   }
 * </pre>
 *
 * {@linkplain "http://netbeans.org/download/5_5/javadoc/org-netbeans-api-mdr/index.html?org/netbeans/api/mdr/JMIStreamFactory.html"}
 */
public interface ModelRepository {

  /**
   * Default XMI version for export. The possible values supported by MDR are 1.2 and 2.0
   */
  String DEFAULT_XMI_VERSION = "1.2";

  /**
   * Load the factory to manipulate the metamodel and its instances in the context of the
   * initialized model repository.
   * Requires this repository is already initialized.
   * Use the {@link java.util.ServiceLoader} mechanism to load the actual metamodel implementation.
   * @param metamodelName non-null
   * @return not null factory for the metamodel provided
   * @throws IllegalStateException if this repository is not initialized yet
   * @throws IllegalArgumentException if of no factory for the metamodel found
   * @see net.mdatools.modelant.repository.spi.ModelRepositorySetup#initialize(File)
   */
  ModelFactory loadMetamodel(String metamodelName) throws IllegalStateException, IllegalArgumentException;


  /**
   * Load the factory to manipulate the metamodel and its instances in the context of the
   * initialized model repository.
   * Requires this repository is already initialized.
   * Use the {@link java.util.ServiceLoader} mechanism to load the actual metamodel implementation.
   * Expected is that the MetamodelFactories instantiated implement also MetamodelFactorySetup service provider interface
   * for their initialization.
   * @param metamodelName non-null
   * @param loader not null class loader to lookup
   * @return not null factory for the metamodel provided
   * @throws IllegalStateException if this repository is not initialized yet
   * @throws IllegalArgumentException if of no factory for the metamodel found
   * @see net.mdatools.modelant.repository.spi.ModelRepositorySetup#initialize(File)
   */
  ModelFactory loadMetamodel(String metamodelName, ClassLoader loader) throws IllegalStateException, IllegalArgumentException;


  /**
   *
   */
  void beginReadOnlyTransaction();


  /**
   *
   */
  void beginWriteTransaction();


  /**
   * @param shouldCommit
   */
  void endTransaction(boolean shouldCommit);


  /**
   *
   */
  void shutdown();


  /**
   * Create a new MOF extent with the name provided.
   * @param extentName non-empty name for newly instantiated package.
   * @return the newly created extent
   * @throws Exception
   */
  RefPackage constructMetamodelExtent(String extentName) throws Exception;


  /**
   * Instantiate a metamodel package, this way creating a repository for models as metamodel instances.
   *
   * @param mofExtentName non-empty name to assign to the newly created MOF extent or metamodel extent/model repository.
   * @param modelExtentName non-empty name of the MOF extent where to lookup the metamodel package to instantiate a model repository
   * @param metaPackageName non-empty name of the metamodel package to instantiate and create a model repository
   * @return non-null extent created
   */
  RefPackage constructModelExtent(String mofExtentName,
                                  String metaPackageName,
                                  String modelExtentName) throws IllegalArgumentException, CreationFailedException;

  /**
   * Instantiate a metamodel package, this way creating a repository for models as metamodel instances.
   *
   * @param mofExtent non-null newly created MOF extent or metamodel extent/model repository.
   * @param modelExtentName non-empty name of the MOF extent where to lookup the metamodel package to instantiate a model repository
   * @param metaPackageName non-empty name of the metamodel package to instantiate and create a model repository
   * @return non-null extent created
   */
  RefPackage constructModelExtent(ModelPackage mofExtent,
                                  String metaPackageName,
                                  String modelExtentName) throws IllegalArgumentException, CreationFailedException;


  /**
   * @param name non-empty extent to lookup
   * @return the extent found or null
   */
  RefPackage getExtent(String name);


  /**
   * Destroy the extent with the provided name
   * @param name
   */
  void deleteExtent(String name);


  /**
   * @return the names of all extents in the repository
   */
  String[] getExtentNames();


  /**
   * Read a metamodel in into a MOF extent or reads a model into a model repository.
   * Both metamodel and model should be in XMI format.
   * @param extentName
   * @param url
   * @throws IOException
   * @throws MalformedXMIException
   */
  void readIntoExtent(String extentName, String url) throws IOException, MalformedXMIException;


  /**
   * Read a metamodel in into a MOF extent or reads a model into a model repository.
   * @param extentName
   * @param url non-null metamodel in XMI format
   * @param classLoader not null classloader to resolve the url through
   * @throws IOException
   * @throws MalformedXMIException
   */
  void readIntoExtent(String extentName, String url, ClassLoader classLoader) throws IOException, MalformedXMIException;

  /**
   * Read the model/metamodel into the target extent using the thread's classsloader
   * @param targetExtent not null
   * @param url not null
   * @throws IOException
   * @throws MalformedXMIException
   */
  void readIntoExtent(RefPackage targetExtent,
                      String url) throws IOException, MalformedXMIException;

  /**
   * Read the model/metamodel into the target extent, using the classloader to resolve the url
   * @param targetExtent not null
   * @param url non-null metamodel in XMI format
   * @param classLoader not null
   * @throws IOException
   * @throws MalformedXMIException
   */
  void readIntoExtent(RefPackage targetExtent,
                      String url,
                      ClassLoader classLoader) throws IOException, MalformedXMIException;


  /**
   * Read a metamodel or a model into an extent.
   * @param extentName
   * @param file non-null metamodel in XMI format
   * @throws IOException
   * @throws MalformedXMIException
   */
  void readIntoExtent(String extentName, File file) throws IOException, MalformedXMIException;

  /**
   * Read a metamodel or a model into an extent.
   * @param extent not null
   * @param file non-null metamodel in XMI format
   * @throws IOException
   * @throws MalformedXMIException
   */
  void readIntoExtent(RefPackage extent, File file) throws IOException, MalformedXMIException;


  /**
   * Produces XMI DTD for given metamodel.
   * @param file the name of the DTD file to generate
   * @param extent the name of the extent with the metamodel to generate DTD for
   * @throws FileNotFoundException
   */
  void writeDtd(File file, String extent) throws FileNotFoundException;


  /**
   * Exports the model held in the extent to the outputFile specified
   * @param extent
   * @param file
   * @throws IOException
   * @throws InvalidNameException
   */
  void writeExtent(String extent, File file) throws IOException, InvalidNameException;


  /**
   * Exports the model held in the extent to the outputFile specified
   * @param extent not empty
   * @param file not empty
   * @param xmiVersion not empty version of XMI to export in
   * @throws IOException
   * @throws InvalidNameException
   */
  void writeExtent(String extent, File file, String xmiVersion) throws IOException, InvalidNameException;


  /**
   * Exports the model held in the extent to the outputFile specified
   * @param extent
   * @param file
   * @param xmiVersion
   * @throws IOException
   */
  void writeExtent(RefPackage extent, File file, String xmiVersion) throws IOException;


  /**
   * @param mofId
   * @return
   */
  Object getByMofId(String mofId);


  /**
   * Generate metamodel API (JMI mapping) as source files.
   * @param dir non-nill destination directory where to generate the JMI API sources
   * @param extentName non-null name of the MOF extent where the metamodel to generate API for is loaded.
   * @param headerFile optional containing specific JAVADOC to be put in the header of each java generated
   * @throws IOException
   */
  void mapJava(File dir, String extentName, File headerFile) throws IOException;


  /**
   * Creates metamodel API (JMI mapping) as compiled classes.
   * @throws IOException
   */
  void mapClasses(File dir, String extentName) throws IOException;
}