/*
 * Copyright (c) 2017 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Created on 03.12.2017
 */
package net.mdatools.modelant.core.operation.navigator;

import java.io.File;
import java.util.List;
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

public class NavigatorTest extends TestCase {

  public Logger LOGGER = Logger.getLogger( NavigatorTest.class.getName() );

  private ModelRepository repository;
  private ModelFactory modelFactory;
  private RefPackage sourceModel;

  protected final RetrieveAttributes GET_ATTRIBUTES = new RetrieveAttributes();
  protected final RetrieveAssociations GET_ASSOCIATIONS = new RetrieveAssociations();

  protected void setUp() throws Exception {
    repository = ModelRepositoryFactory.construct(new File("target"));

    // cleanup the previously instantiated extents
    repository.deleteExtent( "SOURCE" );
    repository.deleteExtent( "UML13" );

    modelFactory = repository.loadMetamodel("UML13");

    sourceModel = modelFactory.instantiate("SOURCE");
  }

  /**
   * Count the associations in UML 1.3 metamodel
   */
  public void testGetAllAssociations() {
  	List<RefAssociation> associations; 
  
  	associations = Navigator.getAllAssociations(sourceModel);
  
  	LOGGER.log( Level.FINE, "All associations: {0}", new PrintModelElement().toPrint(associations));

  	assertEquals(104, associations.size());
  }

  /**
   * Count the classes in UML 1.3 metamodel
   */
  public void testGetAllClasses() {
  	List<RefClass> classes;
  	
  	classes = Navigator.getAllClasses(sourceModel);

  	LOGGER.log( Level.FINE, "All classes: {0}", new PrintModelElement().toPrint(classes));
  	
  	assertEquals(110, classes.size());
  }

  /**
   * Count the objects in UML 1.3 model loaded
   */
  public void testGetAllObjects() throws Exception {
  	List<RefObject> objects;
  	
    repository.readIntoExtent( sourceModel, "demo.xml", Thread.currentThread().getContextClassLoader() );

    objects = Navigator.getAllObjects(sourceModel);
  	
  	assertEquals(2627, objects.size());
  }


  protected void tearDown() {
    repository.shutdown();
  }


  protected final ModelRepository getRepository() {
    return repository;
  }
}
