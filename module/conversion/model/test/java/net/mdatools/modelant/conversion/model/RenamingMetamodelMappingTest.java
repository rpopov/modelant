/*
 * Copyright (c) i:FAO AG 2018. All Rights Reserved.
 *
 * This SOURCE CODE FILE, which has been provided by i:FAO AG as part
 * of a product of i:FAO AG for use ONLY by licensed users of the product,
 * includes CONFIDENTIAL and PROPRIETARY information.
 *
 * Created on 11.04.2018 г.
 */
package net.mdatools.modelant.conversion.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.jmi.model.Attribute;
import javax.jmi.model.Classifier;
import javax.jmi.model.Feature;
import javax.jmi.model.ModelElement;
import javax.jmi.model.MofClass;
import javax.jmi.model.MofPackage;
import javax.jmi.model.MultiplicityType;
import javax.jmi.model.NameNotFoundException;
import javax.jmi.model.NameNotResolvedException;
import javax.jmi.model.Namespace;
import javax.jmi.model.ScopeKind;
import javax.jmi.model.VisibilityKind;
import javax.jmi.reflect.RefClass;
import javax.jmi.reflect.RefException;
import javax.jmi.reflect.RefFeatured;
import javax.jmi.reflect.RefObject;
import javax.jmi.reflect.RefPackage;

import junit.framework.TestCase;
import net.mdatools.modelant.core.name.ClassNameImpl;
import net.mdatools.modelant.core.name.FieldNameImpl;
import net.mdatools.modelant.core.operation.model.transform.RenamingMapping;

public class RenamingMetamodelMappingTest extends TestCase {

  private static final RenamingMapping UML13_TO_UML14_MAPPING = new Uml13ToUml14Mapping();

  private MofPackage foundation;
  private MofPackage core;
  private Classifier associationEnd;


  private MockClassifier modelElement;


  private MockPackage datatTypes;


  private MockClassifier visibilityKind;


  private MockAttribute specialization;


  private MockClassifier generalizableElement;

  private MockAttribute attribute;

  private static class MockModelElement implements ModelElement {
    private final Namespace container;
    private final String name;

    public MockModelElement(Namespace container, String name) {
      super();
      this.container = container;
      this.name = name;
    }

    public RefClass refClass() {
      return null;
    }

    public void refDelete() {
    }

    public RefFeatured refImmediateComposite() {
      return null;
    }

    public boolean refIsInstanceOf(RefObject arg0, boolean arg1) {
      return false;
    }

    public RefFeatured refOutermostComposite() {
      return null;
    }

    public Object refGetValue(RefObject arg0) {
      return null;
    }

    public Object refGetValue(String arg0) {
      return null;
    }

    public Object refInvokeOperation(RefObject arg0, List arg1) throws RefException {
      return null;
    }

    public Object refInvokeOperation(String arg0, List arg1) throws RefException {
      return null;
    }

    public void refSetValue(RefObject arg0, Object arg1) {
    }

    public void refSetValue(String arg0, Object arg1) {
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
      return null;
    }

    public Collection refVerifyConstraints(boolean arg0) {
      return null;
    }

    public Collection findRequiredElements(Collection arg0, boolean arg1) {
      return null;
    }

    public String getAnnotation() {
      return null;
    }

    public Collection getConstraints() {
      return null;
    }

    public Namespace getContainer() {
      return container;
    }

    public String getName() {
      return name;
    }

    public List getQualifiedName() {
      return null;
    }

    public Collection getRequiredElements() {
      return null;
    }

    public boolean isFrozen() {
      return false;
    }

    public boolean isRequiredBecause(ModelElement arg0, String[] arg1) {
      return false;
    }

    public boolean isVisible(ModelElement arg0) {
      return false;
    }

    public void setAnnotation(String arg0) {
    }

    public void setContainer(Namespace arg0) {
    }

    public void setName(String arg0) {
    }
  }

  private static class MockPackage extends MockModelElement implements MofPackage {

    public MockPackage(Namespace container, String name) {
      super( container, name );
    }

    public List allSupertypes() {
      return null;
    }

    public List findElementsByTypeExtended(MofClass arg0, boolean arg1) {
      return null;
    }

    public List getSupertypes() {
      return null;
    }

    public VisibilityKind getVisibility() {
      return null;
    }

    public boolean isAbstract() {
      return false;
    }

    public boolean isLeaf() {
      return false;
    }

    public boolean isRoot() {
      return false;
    }

    public ModelElement lookupElementExtended(String arg0) throws NameNotFoundException {
      return null;
    }

    public void setAbstract(boolean arg0) {
    }

    public void setLeaf(boolean arg0) {
    }

    public void setRoot(boolean arg0) {
    }

    public void setVisibility(VisibilityKind arg0) {
    }

    public List findElementsByType(MofClass arg0, boolean arg1) {
      return null;
    }

    public List getContents() {
      return null;
    }

    public ModelElement lookupElement(String arg0) throws NameNotFoundException {
      return null;
    }

    public boolean nameIsValid(String arg0) {
      return false;
    }

    public ModelElement resolveQualifiedName(List arg0) throws NameNotResolvedException {
      return null;
    }
  }

  private static class MockClassifier extends MockModelElement implements Classifier {
    private List<Classifier> supertypes;
    private List<Feature> contents;

    public MockClassifier(Namespace container,
                          String name,
                          List<Classifier> supertypes,
                          List<Feature> contents) {
      super( container, name );
      this.supertypes = supertypes;
      this.contents = contents;
    }

    public List<Classifier> allSupertypes() {
      return supertypes;
    }

    public List<ModelElement> findElementsByTypeExtended(MofClass arg0, boolean arg1) {
      return null;
    }

    public List<Classifier> getSupertypes() {
      return supertypes;
    }

    public VisibilityKind getVisibility() {
      return null;
    }

    public boolean isAbstract() {
      return false;
    }

    public boolean isLeaf() {
      return false;
    }

    public boolean isRoot() {
      return false;
    }

    public ModelElement lookupElementExtended(String arg0) throws NameNotFoundException {
      return null;
    }

    public void setAbstract(boolean arg0) {
    }

    public void setLeaf(boolean arg0) {
    }

    public void setRoot(boolean arg0) {
    }

    public void setVisibility(VisibilityKind arg0) {
    }

    public List findElementsByType(MofClass arg0, boolean arg1) {
      return null;
    }

    public List<Feature> getContents() {
      return contents;
    }

    public ModelElement lookupElement(String arg0) throws NameNotFoundException {
      return null;
    }

    public boolean nameIsValid(String arg0) {
      return false;
    }

    public ModelElement resolveQualifiedName(List arg0) throws NameNotResolvedException {
      return null;
    }
  }

  private static class MockFeature extends MockModelElement implements Feature {
    public MockFeature(Namespace container, String name) {
      super( container, name );
    }

    public ScopeKind getScope() {
      return null;
    }

    public VisibilityKind getVisibility() {
      return null;
    }

    public void setScope(ScopeKind arg0) {
    }

    public void setVisibility(VisibilityKind arg0) {
    }
  }

  private static class MockAttribute extends MockFeature implements Attribute {
    public MockAttribute(Namespace container, String name) {
      super( container, name );
    }

    public MultiplicityType getMultiplicity() {
      return null;
    }

    public boolean isChangeable() {
      return false;
    }

    public void setChangeable(boolean arg0) {
    }

    public void setMultiplicity(MultiplicityType arg0) {
    }

    public Classifier getType() {
      return null;
    }

    public void setType(Classifier arg0) {
    }

    public boolean isDerived() {
      return false;
    }

    public void setDerived(boolean arg0) {
    }
  }

  protected void setUp() throws Exception {
    foundation = new MockPackage( null, "Foundation");
    core = new MockPackage( foundation, "Core");
    datatTypes = new MockPackage( foundation, "Data_Types");
    modelElement = new MockClassifier( core, "ModelElement", new ArrayList<>(), new ArrayList<>());
    associationEnd = new MockClassifier( core, "AssociationEnd", Arrays.asList( modelElement ), new ArrayList<>());
    visibilityKind = new MockClassifier( datatTypes, "VisibilityKind", Arrays.asList( modelElement ), new ArrayList<>());
    generalizableElement = new MockClassifier( core, "GeneralizableElement", Arrays.asList( modelElement), new ArrayList<>());
    specialization = new MockAttribute(generalizableElement, "specialization");
    attribute = new MockAttribute(generalizableElement, "attribute");
  }


  public void testMapMetaClassName() {
    assertEquals("Core::AssociationEnd", UML13_TO_UML14_MAPPING.getName(new ClassNameImpl(associationEnd)).toString());
    assertEquals("Data_Types::VisibilityKind", UML13_TO_UML14_MAPPING.getName(new ClassNameImpl(visibilityKind)).toString());
  }


  public void testMapMetaFieldName() {
    assertNotNull(UML13_TO_UML14_MAPPING.getName(new FieldNameImpl(attribute)));
    assertNull(UML13_TO_UML14_MAPPING.getName(new FieldNameImpl(specialization)));
  }


  public void testMapMetaEnumValueName() {
//    fail( "Not yet implemented" );
  }
}
