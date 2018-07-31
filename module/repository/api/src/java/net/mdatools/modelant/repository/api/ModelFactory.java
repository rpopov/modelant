/*
 * Copyright (c) 2017 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Created on 10.12.2017
 */
package net.mdatools.modelant.repository.api;

import java.io.File;
import java.io.IOException;

import javax.jmi.reflect.InvalidNameException;
import javax.jmi.reflect.RefPackage;
import javax.jmi.xmi.MalformedXMIException;

import net.mdatools.modelant.repository.spi.MetamodelFactorySetup;

/**
 * Any metamodel management should be represented as an instance of this interface.
 * Load the metamodel-specific instance of this interface through the ModelRepository,
 * which guarantees that the repository is operational.
 * The instances of this interface must also implement the {@link MetamodelFactorySetup}
 * interface for integration with the  repository. Note that {@link MetamodelFactorySetup} is an
 * internal interface, not for use by client applications, therefore this interface <b>does not</b>
 * implement it.<pre>
 * Usage:
 *
 * repository = ModelRepositoryFactory.construct(workDirectory);
 * metamodelFactory = repository.loadMetamodel("UML13");
 * sourceExtent = metamodelFactory.instantiate("SOURCE");
 * repository.readIntoExtent( sourceExtent, sourceModel );
 * </pre>
 * @see ModelRepository
 * @author Rusi Popov
 */
public interface ModelFactory {

  /**
   * @return non-null extent where the metamodel is loaded
   */
  RefPackage getMetamodelExtent();

  /**
   * Instantiate the metamodel in a new unique extent
   * @return non-null extent where to load/produce a model
   * @throws IllegalArgumentException in case the extent is not available or the metamodel load failed
   * @see #readModel(RefPackage)
   */
  RefPackage instantiate();

  /**
   * Instantiate the metamodel in the provided extent
   * @param extentName not null, unique name of a not used extent
   * @return non-null extent where to load/produce a model
   * @throws IllegalArgumentException in case the extent is not available or the metamodel load failed
   * @see #readModel(RefPackage)
   */
  RefPackage instantiate(String extentName);

  /**
   * Read the model in the provided extent
   * Lookup the model relative the class path this factory was loaded
   * @param model not null, model = instantiate()
   * @param modelUrl the URL/file of the model to load
   * @throws MalformedXMIException
   * @throws IOException
   */
  void readModel(RefPackage model, String modelUrl) throws IOException, MalformedXMIException;

  /**
   * Read the model in the provided extent
   * @param model not null, model = instantiate()
   * @param modelUrl the URL/file of the model to load
   * @param loader not null, to use to resolve the (usually relative) URL/URI in
   * @throws MalformedXMIException
   * @throws IOException
   */
  void readModel(RefPackage model, String modelUrl, ClassLoader loader) throws IOException, MalformedXMIException;

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
  void readModel(RefPackage extent, File file) throws IOException, MalformedXMIException;


  /**
   * Exports the model held in the extent to the outputFile specified
   * @param extent
   * @param file
   * @throws IOException
   * @throws InvalidNameException
   */
  void writeExtent(RefPackage extent, File file) throws IOException, InvalidNameException;


  /**
   * Exports the model held in the extent to the outputFile specified
   * @param extent not empty
   * @param file not empty
   * @param xmiVersion not empty version of XMI to export in
   * @throws IOException
   * @throws InvalidNameException
   */
  void writeExtent(RefPackage extent, File file, String xmiVersion) throws IOException, InvalidNameException;

}
