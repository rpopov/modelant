/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.operation.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.jmi.reflect.RefAssociation;
import javax.jmi.reflect.RefClass;
import javax.jmi.reflect.RefEnum;
import javax.jmi.reflect.RefObject;
import javax.jmi.reflect.RefPackage;
import javax.jmi.reflect.RefStruct;

import junit.framework.TestCase;
import net.mdatools.modelant.core.api.diff.ModelComparisonResult;
import net.mdatools.modelant.core.api.match.MatchingCriteria;
import net.mdatools.modelant.core.operation.element.RetrieveAssociations;
import net.mdatools.modelant.core.operation.element.RetrieveAttributes;
import net.mdatools.modelant.repository.api.ModelFactory;
import net.mdatools.modelant.repository.api.ModelRepository;
import net.mdatools.modelant.repository.api.ModelRepositoryFactory;

/**
 * Test the metamodel copy operation to copy MOF 1.4 metamodel as it defines.
 * The problem is the way how MOF is instantiated - there is no root package to start navigating from.
 * Instead, the source & target extents are instanvces of Model. Thus, in order to make MOF compatible
 * with the other metamodels, they are wrapped in a package mock.
 * @author Rusi Popov
 */
public class CopyMetamodelTest extends TestCase {

  public Logger LOGGER = Logger.getLogger( CopyMetamodelTest.class.getName() );

  private ModelRepository repository;
  private ModelFactory metamodelFactory;
  private RefPackage sourceModel;
  private RefPackage targetModel;
  private RefPackage originalTargetModel;

  protected final RetrieveAttributes GET_ATTRIBUTES = new RetrieveAttributes();
  protected final RetrieveAssociations GET_ASSOCIATIONS = new RetrieveAssociations();

  protected void setUp() throws Exception {
    repository = ModelRepositoryFactory.construct(new File("target"));

    metamodelFactory = repository.loadMetamodel("MOF14");

    sourceModel = metamodelFactory.getMetamodelExtent();
    sourceModel = new RefPackageMock( sourceModel );

    originalTargetModel = repository.constructMetamodelExtent( "TARGET" );
    targetModel = new RefPackageMock( originalTargetModel );
  }


  /**
   * Copy the model into target, alter the model and detect the changes.
   */
  public void testCopyAndCopmare() throws Exception {
    CopyModel copy;
    CompareModels compare;
    Map<RefObject, RefObject> correspondence;
    ModelComparisonResult comparisonResult;

    copy = new CopyModel( sourceModel );
    try {
      correspondence = copy.execute( targetModel );

      // compare the models - without using the correspondence - they must be equal!
      compare = new CompareModels( MatchingCriteria.NAME_AND_NAMESPACE_MATCH,
                                   MatchingCriteria.NONE,
                                   new ArrayList<>(),
                                   sourceModel );
      comparisonResult = compare.execute( targetModel );
    } finally {
      repository.writeExtent( originalTargetModel, new File("target\\mof-copy.xml"), ModelRepository.DEFAULT_XMI_VERSION);
    }
    assertTrue("Expected no change detected after copying the MOF metamodel", comparisonResult.isExactMatch());
  }

  protected void tearDown() {
    repository.shutdown();
  }


  protected final ModelRepository getRepository() {
    return repository;
  }

  private class RefPackageMock implements RefPackage {
    private final RefPackage wrapped;

    public RefPackageMock(RefPackage wrapped) {
      this.wrapped = wrapped;
    }

    public RefPackage refImmediatePackage() {
      return null;
    }

    public RefObject refMetaObject() {
      return null;
    }

    public String refMofId() {
      return null;
    }

    public RefPackage refOutermostPackage() {
      return this;
    }

    public Collection refVerifyConstraints(boolean arg0) {
      return null;
    }

    public Collection refAllAssociations() {
      return new ArrayList();
    }

    public Collection refAllClasses() {
      return new ArrayList();
    }

    public Collection refAllPackages() {
      Collection result;

      result = new ArrayList();
      result.add( wrapped );

      return result;
    }

    public RefAssociation refAssociation(RefObject arg0) {
      return null;
    }

    public RefAssociation refAssociation(String arg0) {
      return null;
    }

    public RefClass refClass(RefObject arg0) {
      return null;
    }

    public RefClass refClass(String arg0) {
      return null;
    }

    public RefStruct refCreateStruct(RefObject arg0, List arg1) {
      return null;
    }

    public RefStruct refCreateStruct(String arg0, List arg1) {
      return null;
    }

    public void refDelete() {
    }

    public RefEnum refGetEnum(RefObject arg0, String arg1) {
      return null;
    }

    public RefEnum refGetEnum(String arg0, String arg1) {
      return null;
    }

    public RefPackage refPackage(RefObject arg0) {
      return wrapped;
    }

    public RefPackage refPackage(String arg0) {
      return wrapped;
    }
  }
}
