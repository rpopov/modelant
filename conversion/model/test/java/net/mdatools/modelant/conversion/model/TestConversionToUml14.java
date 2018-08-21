/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.conversion.model;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jmi.reflect.RefAssociation;
import javax.jmi.reflect.RefClass;
import javax.jmi.reflect.RefObject;
import javax.jmi.reflect.RefPackage;

import junit.framework.TestCase;
import net.mdatools.modelant.core.operation.element.PrintModelElement;
import net.mdatools.modelant.core.operation.element.RetrieveAssociations;
import net.mdatools.modelant.core.operation.element.RetrieveAttributes;
import net.mdatools.modelant.core.util.Navigator;
import net.mdatools.modelant.repository.api.ModelFactory;
import net.mdatools.modelant.repository.api.ModelRepository;
import net.mdatools.modelant.repository.api.ModelRepositoryFactory;

public class TestConversionToUml14 extends TestCase {

  public Logger LOGGER = Logger.getLogger( TestConversionToUml14.class.getName() );

  private ModelRepository repository;
  private ModelFactory sourceFactory;
  private ModelFactory targetFactory;
  private RefPackage sourceModel;
  private RefPackage targetModel;

  protected final RetrieveAttributes GET_ATTRIBUTES = new RetrieveAttributes();
  protected final RetrieveAssociations GET_ASSOCIATIONS = new RetrieveAssociations();

  protected void setUp() throws Exception {
    repository = ModelRepositoryFactory.construct(new File("target"));

    // cleanup the previously instantiated extents
    repository.deleteExtent( "SOURCE" );
    repository.deleteExtent( "UML13" );

    sourceFactory = repository.loadMetamodel("UML13");
    targetFactory = repository.loadMetamodel("UML14");

    sourceModel = sourceFactory.instantiate("SOURCE");
    targetModel = targetFactory.instantiate("TARGET");

    repository.readIntoExtent( sourceModel, "demo.xml", Thread.currentThread().getContextClassLoader() );
  }

  /**
   * Convert the loaded model and validate the result structure
   */
  public void testConvert() {
  	ConvertUml13ToUml14 convert;
  	Map<RefObject, RefObject> objectsMapping;

  	convert = new ConvertUml13ToUml14(sourceModel);

  	objectsMapping = convert.execute(targetModel);

  	assertTrue(!objectsMapping.isEmpty());
  }


  protected void tearDown() throws IOException {
    repository.writeExtent( targetModel, new File("target\\demo14.xmi"), ModelRepository.DEFAULT_XMI_VERSION);
    repository.shutdown();
  }
}
