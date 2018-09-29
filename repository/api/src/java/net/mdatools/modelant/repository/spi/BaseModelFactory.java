/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.repository.spi;

import java.io.File;
import java.io.IOException;

import javax.jmi.model.ModelPackage;
import javax.jmi.reflect.InvalidNameException;
import javax.jmi.reflect.RefPackage;
import javax.jmi.xmi.MalformedXMIException;

import net.mdatools.modelant.repository.api.ModelFactory;
import net.mdatools.modelant.repository.api.ModelRepository;

/**
 * Base implementation of all Metamodel Factories, compliant to the contract defined.
 * Let all implementations of (@link "net.mdatools.modelant.repository.api.MetamodelFactory"} extend it
 * by providing a unique name.
 * Protocol: <pre>
 *
 * repository = ModelRepositoryFactory.construct(workDirectory);
 * metamodelFactory = repository.loadMetamodel("UML13");
 * sourceExtent = metamodelFactory.instantiate("SOURCE");
 * repository.readIntoExtent( sourceExtent, sourceModel );
 *
 * </pre>
 * @author Rusi Popov (popovr@mdatools.net)
 */
public abstract class BaseModelFactory implements ModelFactory, ModelFactorySetup {

  /**
   * The owner repository
   */
  private ModelRepository repository;

  /**
   * The only extent where the metamodel is (to be) loaded in
   */
  private ModelPackage mofExtent;

  /**
   * @see ModelFactorySetup#construct(ModelRepository, ClassLoader)
   */
  public final ModelFactory construct(ModelRepository repository, ClassLoader loader) {
    if ( this.repository != null ) {
      throw new IllegalStateException("Expected is this method is called only once");
    }
    this.repository = repository;

    try {
      mofExtent = (ModelPackage) repository.constructMetamodelExtent( getMetamodelName() );

      repository.readIntoExtent( mofExtent, getMetamodelPath(), loader);
    } catch (Exception ex) {
      throw new IllegalArgumentException(ex);
    }
    return this;
  }

  /**
   * @see ModelFactory#getMetamodelExtent()
   */
	public final RefPackage getMetamodelExtent() {
		return mofExtent;
	}

	/**
   * @see net.mdatools.modelant.repository.api.ModelFactory#instantiate(java.lang.String)
   */
  public final RefPackage instantiate() {
    return instantiate(getMetamodelName()+System.currentTimeMillis());
  }

  /**
   * @see net.mdatools.modelant.repository.api.ModelFactory#instantiate(java.lang.String)
   */
  public final RefPackage instantiate(String extentName) {
    RefPackage result;

    try {
      result = repository.constructModelExtent(mofExtent, getModelPackageName(), extentName );
    } catch (Exception ex) {
      throw new IllegalArgumentException("The instantiation of the metamodel "+getMetamodelName()+" caused: "+ex);
    }
    return result;
  }

  /**
   * @return non-null name of the root package to instantiate, so that instance becomes the model extent.
   *         Note that the standard metamodels need a single root package, which imposes the use a *_DIFF.xml
   *         file to define that root and import the metamodel packages in it. Return the name of that root package.
   */
  protected abstract String getModelPackageName();

  /**
   * @return non-null path in the class path, actually in the current jar, of the mdetmodel file
   */
  protected abstract String getMetamodelPath();

  /**
   * @see net.mdatools.modelant.repository.api.ModelFactory#readModel(javax.jmi.reflect.RefPackage, java.lang.String)
   */
  public final void readModel(RefPackage model, String modelUrl) throws IOException, MalformedXMIException {
    readModel( model, modelUrl, getClass().getClassLoader() );
  }

  public final void readModel(RefPackage model, String modelUrl, ClassLoader loader) throws IOException, MalformedXMIException {
    repository.readIntoExtent( model, modelUrl, loader );

  }

  /**
   * @see net.mdatools.modelant.repository.api.ModelRepository#readIntoExtent(java.lang.String, java.io.File)
   */
  public final void readModel(String extentName, File file) throws IOException, MalformedXMIException {
    repository.readIntoExtent( extentName, file );
  }

  /**
   * @see net.mdatools.modelant.repository.api.ModelRepository#readIntoExtent(javax.jmi.reflect.RefPackage, java.io.File)
   */
  public final void readModel(RefPackage extent, File file) throws IOException, MalformedXMIException {
    repository.readIntoExtent( extent, file );
  }

  /**
   * @see net.mdatools.modelant.repository.api.ModelFactory#writeExtent(javax.jmi.reflect.RefPackage, java.io.File)
   */
  public final void writeExtent(RefPackage extent, File file) throws IOException, InvalidNameException {
    repository.writeExtent( extent, file, ModelRepository.DEFAULT_XMI_VERSION );
  }

  /**
   * @see net.mdatools.modelant.repository.api.ModelFactory#writeExtent(javax.jmi.reflect.RefPackage, java.io.File, java.lang.String)
   */
  public final void writeExtent(RefPackage extent, File file, String xmiVersion) throws IOException, InvalidNameException {
    repository.writeExtent( extent, file, xmiVersion );
  }
}