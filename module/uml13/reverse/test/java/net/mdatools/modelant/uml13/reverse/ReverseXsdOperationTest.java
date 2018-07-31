/*
 * Copyright (c) i:FAO AG 2018. All Rights Reserved.
 *
 * This SOURCE CODE FILE, which has been provided by i:FAO AG as part
 * of a product of i:FAO AG for use ONLY by licensed users of the product,
 * includes CONFIDENTIAL and PROPRIETARY information.
 *
 * Created on 12.05.2018 г.
 */
package net.mdatools.modelant.uml13.reverse;

import java.io.File;

import javax.jmi.reflect.RefPackage;

import org.omg.uml13.Uml13Package;
import org.omg.uml13.foundation.core.UmlClass;
import org.omg.uml13.foundation.extensionmechanisms.Stereotype;
import org.omg.uml13.foundation.extensionmechanisms.TaggedValue;
import org.omg.uml13.modelmanagement.UmlPackage;

import net.mdatools.modelant.core.api.Function;
import net.mdatools.modelant.core.name.NameImpl;
import net.mdatools.modelant.core.name.PackageNameImpl;

public class ReverseXsdOperationTest extends TestModel {
  private Function<File, RefPackage> reverse;


  protected void setUp() throws Exception {
    super.setUp();

    reverse = new ReverseXsdOperation(modelRepository);
  }


  public void testPackageComment() {
    Uml13ModelFactory factory;
    UmlPackage namespace;
    TaggedValue documentation;

    extent = (Uml13Package) reverse.execute( new File("test/xsd/xsd-package-comment.xsd") );
    factory = new Uml13ModelFactory(extent);

    // locate the package for the target name space
    namespace = (UmlPackage) factory.locateModelElement("xml.ns.j2ee");

    assertNotNull("There is a package representinhg the target namespace of the schema", namespace );

    documentation = factory.getTaggedValue( namespace, net.mdatools.modelant.uml13.metamodel.Convention.TAG_VALUE_DOCUMENTATION );

    assertNotNull("There is a documenation for the package representinhg the target namespace of the schema", documentation );
    assertNotNull("The documenation text shuld not be empty", documentation.getValue() );
  }

  public void testClassComment() {
    Uml13ModelFactory factory;
    UmlPackage namespace;
    TaggedValue documentation;
    UmlClass element;
    UmlClass elementType;
    Stereotype stereotype;

    extent = (Uml13Package) reverse.execute( new File("test/xsd/xsd-class-comment.xsd") );
    factory = new Uml13ModelFactory(extent);

    // locate the package for the target name space
    namespace = (UmlPackage) factory.locateModelElement("xml.ns.j2ee");

    assertNotNull("There is a package representinhg the target namespace of the schema", namespace );

    element = (UmlClass) factory.locateLocalModelElement( namespace, "ejb-jar" );
    assertNotNull("Expected the root element \"ejb-jar\" is defined", element);

    stereotype = (Stereotype) factory.locateModelElement(Uml13ModelFactory.STEREOTYPE_ELEMENT);
    assertNotNull("Expected the <<element>> stereotype defined", stereotype);

    assertTrue("Expected the <<element>> stereotype is assigned to the root element", stereotype.getExtendedElement().contains( element ));

    elementType = (UmlClass) factory.locateLocalModelElement( namespace, "ejb-jarType" );
    assertNotNull("Expected the type of the root element \"ejb-jarType\" is defined", elementType);

    documentation = factory.getTaggedValue( element, net.mdatools.modelant.uml13.metamodel.Convention.TAG_VALUE_DOCUMENTATION );

    assertNotNull("There is a documenation for the root element", documentation );
    assertNotNull("The documenation for the root element is not null", documentation.getValue() );
    assertFalse("The documenation for the root element is not empty", documentation.getValue().isEmpty() );

    documentation = factory.getTaggedValue( elementType, net.mdatools.modelant.uml13.metamodel.Convention.TAG_VALUE_DOCUMENTATION );

    assertNotNull("There is a documenation for the root type element", documentation );
    assertNotNull("The documenation for the root type element is not null", documentation.getValue() );
    assertFalse("The documenation for the root type element is not empty", documentation.getValue().isEmpty() );
  }

  public void testClassGroupAttribute() {
    Uml13ModelFactory factory;
    UmlPackage namespace;
    UmlClass elementType;
    UmlClass descriptionGroup;

    extent = (Uml13Package) reverse.execute( new File("test/xsd/xsd-class-group-attributes.xsd") );
    factory = new Uml13ModelFactory(extent);

    // locate the package for the target name space
    namespace = (UmlPackage) factory.locateModelElement("xml.ns.j2ee");

    assertNotNull("There is a package representinhg the target namespace of the schema", namespace );

    elementType = (UmlClass) factory.locateLocalModelElement( namespace, "listenerType" );
    assertNotNull("Expected the type of the root element \"listenerType\" is defined", elementType);

    assertEquals("Associations of elementType ", 4, countAssociationsOf( elementType ));
    assertEquals("Attributes of listenerType ", 1, countAttributes( elementType ));

    descriptionGroup = (UmlClass) factory.locateLocalModelElement( namespace, "descriptionGroup" );
    assertNull("Expected the \"descriptionGroup\" is inlined", descriptionGroup);
  }


  public void testClassGroupAssociations() {
    Uml13ModelFactory factory;
    UmlPackage namespace;
    UmlClass elementType;
    int count;

    extent = (Uml13Package) reverse.execute( new File("test/xsd/xsd-class-group-associations.xsd") );
    factory = new Uml13ModelFactory(extent);

    // locate the package for the target name space
    namespace = (UmlPackage) factory.locateModelElement(new PackageNameImpl(new PackageNameImpl(new PackageNameImpl("xml"), "ns"), "j2ee"));

    assertNotNull("There is a package representinhg the target namespace of the schema", namespace );

    assertSame(namespace, factory.locateModelElement("xml.ns.j2ee"));

    elementType = (UmlClass) factory.locateLocalModelElement( namespace, "listenerType" );
    assertNotNull("Expected the type of the root element \"listenerType\" is defined", elementType);

    // the group is in-lined
    count = countAssociationsOf( elementType );
    assertEquals("Associations of elementType", 4, count);
  }


  /**
   * @throws Exception
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }
}