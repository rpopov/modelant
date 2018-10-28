/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.repository.api;

import java.io.File;
import java.io.IOException;

import javax.jmi.reflect.InvalidNameException;
import javax.jmi.reflect.RefPackage;
import javax.jmi.xmi.MalformedXMIException;

import net.mdatools.modelant.repository.spi.ModelFactorySetup;

/**
 * Any metamodel management should be represented as an instance of this interface.
 * Load the metamodel-specific instance of this interface through the ModelRepository,
 * which guarantees that the repository is operational.
 * The instances of this interface must also implement the {@link ModelFactorySetup}
 * interface for integration with the  repository. Note that {@link ModelFactorySetup} is an
 * internal interface, not for use by client applications, therefore this interface <b>does not</b>
 * implement it.<pre>
 * Usage:
 *
 * repository = ModelRepositoryFactory.construct(workDirectory);
 * metamodelFactory = repository.loadMetamodel("UML13");
 * sourceExtent = metamodelFactory.instantiate("SOURCE");
 * repository.readIntoExtent( sourceExtent, sourceModel );
 * </pre>
 * @param M the model's root JMI package interface, which gives access to the loaded model
 * @see ModelRepository
 * @author Rusi Popov (popovr@mdatools.net)
 */
public interface ModelFactory<P extends RefPackage> {

  /**
   * @return non-null extent where the metamodel is loaded
   */
  RefPackage getMetamodelExtent();

  /**
   * Instantiate the metamodel in a new unique extent
   * @return non-null extent where to load/produce a model
   * @throws IllegalArgumentException in case the extent is not available or the metamodel load failed
   * @see #readModel(RefPackage, File)
   */
  P instantiate();

  /**
   * Instantiate the metamodel in the provided extent
   * @param extentName not null, unique name of a not used extent
   * @return non-null extent where to load/produce a model
   * @throws IllegalArgumentException in case the extent is not available or the metamodel load failed
   * @see #readModel(RefPackage, File)
   */
  P instantiate(String extentName);

  /**
   * Read the model in the provided extent
   * Lookup the model relative the class path this factory was loaded
   * @param model not null, model = instantiate()
   * @param modelUrl the URL/file of the model to load
   * @throws MalformedXMIException
   * @throws IOException
   */
  void readModel(P model, String modelUrl) throws IOException, MalformedXMIException;

  /**
   * Read the model in the provided extent
   * @param model not null, model = instantiate()
   * @param modelUrl the URL/file of the model to load
   * @param loader not null, to use to resolve the (usually relative) URL/URI in
   * @throws MalformedXMIException
   * @throws IOException
   */
  void readModel(P model, String modelUrl, ClassLoader loader) throws IOException, MalformedXMIException;

  /**
   * Read a metamodel or a model into an extent.
   * @param extentName
   * @param file non-null metamodel in XMI format
   * @throws IOException
   * @throws MalformedXMIException
   */
  void readModel(String extentName, File file) throws IOException, MalformedXMIException;

  /**
   * Read a metamodel or a model into an extent.
   * @param extent not null
   * @param file non-null metamodel in XMI format
   * @throws IOException
   * @throws MalformedXMIException
   */
  void readModel(P extent, File file) throws IOException, MalformedXMIException;


  /**
   * Exports the model held in the extent to the outputFile specified
   * @param extent
   * @param file
   * @throws IOException
   * @throws InvalidNameException
   */
  void writeExtent(P extent, File file) throws IOException, InvalidNameException;


  /**
   * Exports the model held in the extent to the outputFile specified
   * @param extent not empty
   * @param file not empty
   * @param xmiVersion not empty version of XMI to export in
   * @throws IOException
   * @throws InvalidNameException
   */
  void writeExtent(P extent, File file, String xmiVersion) throws IOException, InvalidNameException;

}
