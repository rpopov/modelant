/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.uml14.reverse;

import java.io.File;

import javax.jmi.reflect.RefPackage;

import org.omg.uml14.Uml14Package;
import org.omg.uml14.core.Stereotype;
import org.omg.uml14.core.TaggedValue;
import org.omg.uml14.core.UmlClass;
import org.omg.uml14.modelmanagement.UmlPackage;

import net.mdatools.modelant.core.api.Function;
import net.mdatools.modelant.core.name.PackageNameImpl;

public class ReverseXsdOperationTest extends TestModel {
  private Function<File, RefPackage> reverse;


  protected void setUp() throws Exception {
    super.setUp();

    reverse = new ReverseXsdOperation(modelRepository);
  }


  public void testPackageComment() {
    Uml14ModelFactory factory;
    UmlPackage namespace;
    TaggedValue documentation;

    extent = (Uml14Package) reverse.execute( new File("test/xsd/xsd-package-comment.xsd") );
    factory = new Uml14ModelFactory(extent);

    // locate the package for the target name space
    namespace = (UmlPackage) factory.locateModelElement("xml.ns.j2ee");

    assertNotNull("There is a package representinhg the target namespace of the schema", namespace );

    documentation = factory.getTaggedValue( namespace, net.mdatools.modelant.uml14.metamodel.Convention.TAG_VALUE_DOCUMENTATION );

    assertNotNull("There is a documenation for the package representinhg the target namespace of the schema", documentation );
    assertNotNull("The documenation text shuld not be empty", documentation.getDataValue() );
  }

  public void testClassComment() {
    Uml14ModelFactory factory;
    UmlPackage namespace;
    TaggedValue documentation;
    UmlClass element;
    UmlClass elementType;
    Stereotype stereotype;

    extent = (Uml14Package) reverse.execute( new File("test/xsd/xsd-class-comment.xsd") );
    factory = new Uml14ModelFactory(extent);

    // locate the package for the target name space
    namespace = (UmlPackage) factory.locateModelElement("xml.ns.j2ee");

    assertNotNull("There is a package representinhg the target namespace of the schema", namespace );

    element = (UmlClass) factory.locateLocalModelElement( namespace, "ejb-jar" );
    assertNotNull("Expected the root element \"ejb-jar\" is defined", element);

    stereotype = (Stereotype) factory.locateModelElement(Uml14ModelFactory.STEREOTYPE_ELEMENT);
    assertNotNull("Expected the <<element>> stereotype defined", stereotype);

    assertTrue("Expected the <<element>> stereotype is assigned to the root element", element.getStereotype().contains(stereotype));

    elementType = (UmlClass) factory.locateLocalModelElement( namespace, "ejb-jarType" );
    assertNotNull("Expected the type of the root element \"ejb-jarType\" is defined", elementType);

    documentation = factory.getTaggedValue( element, net.mdatools.modelant.uml14.metamodel.Convention.TAG_VALUE_DOCUMENTATION );

    assertNotNull("There is a documenation for the root element", documentation );
    assertNotNull("The documenation for the root element is not null", documentation.getDataValue() );
    assertFalse("The documenation for the root element is not empty", documentation.getDataValue().isEmpty() );

    documentation = factory.getTaggedValue( elementType, net.mdatools.modelant.uml14.metamodel.Convention.TAG_VALUE_DOCUMENTATION );

    assertNotNull("There is a documenation for the root type element", documentation );
    assertNotNull("The documenation for the root type element is not null", documentation.getDataValue() );
    assertFalse("The documenation for the root type element is not empty", documentation.getDataValue().isEmpty() );
  }

  public void testClassGroupAttribute() {
    Uml14ModelFactory factory;
    UmlPackage namespace;
    UmlClass elementType;
    UmlClass descriptionGroup;

    extent = (Uml14Package) reverse.execute( new File("test/xsd/xsd-class-group-attributes.xsd") );
    factory = new Uml14ModelFactory(extent);

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
    Uml14ModelFactory factory;
    UmlPackage namespace;
    UmlClass elementType;
    int count;

    extent = (Uml14Package) reverse.execute( new File("test/xsd/xsd-class-group-associations.xsd") );
    factory = new Uml14ModelFactory(extent);

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