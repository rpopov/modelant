/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.uml14.reverse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.jmi.reflect.RefPackage;

import org.omg.uml14.Uml14Package;
import org.omg.uml14.core.UmlAssociation;
import org.omg.uml14.core.UmlClass;

import net.mdatools.modelant.core.api.Function;

public class ReverseDatabaseOperationTest extends TestModel {

  private Connection connection;

  protected void setUp() throws Exception {
    super.setUp();

    Class.forName( "org.apache.derby.jdbc.EmbeddedDriver" );
    connection = DriverManager.getConnection("jdbc:derby:memory:myDb;create=true", "sa", "sa");
  }


  public void testA() throws SQLException {
    Function<Connection, RefPackage> operation;
    Statement statement;
    UmlClass tableA;
    Uml14ModelFactory factory;

    statement = connection.createStatement();
    statement.execute( "create table A (C1 NUMERIC(10))" );

    operation = new ReverseDatabaseOperation( modelRepository, null, "SA" );
    extent = (Uml14Package) operation.execute( connection );
    factory = new Uml14ModelFactory(extent);

    tableA = (UmlClass) factory.locateModelElement("A");

    assertNotNull("Class A expected", tableA);
    assertEquals("Class A contains 1 attribute", 1, tableA.getFeature().size());
  }

  public void testAB() throws SQLException {
    Function<Connection, RefPackage> operation;
    Statement statement;
    UmlClass tableA;
    UmlClass tableB;
    Uml14ModelFactory factory;

    statement = connection.createStatement();
    statement.execute( "create table A (ID   NUMERIC(10) PRIMARY KEY)" );
    statement.execute( "create table B (A_ID NUMERIC(10) REFERENCES A)" );

    operation = new ReverseDatabaseOperation( modelRepository, null, new String[] {"SA"} );
    extent = (Uml14Package) operation.execute( connection );
    factory = new Uml14ModelFactory(extent);

    tableA = (UmlClass) factory.locateModelElement("A");
    tableB = (UmlClass) factory.locateModelElement("B");

    assertNotNull("Class A expected", tableA);
    assertNotNull("Class B expected", tableB);
    assertEquals("Class A contains 1 attribute", 1, tableA.getFeature().size());
    assertEquals("Class B contains 1 attribute", 1, tableB.getFeature().size());

    assertEquals("A-B associations", 1, countAssociationsOf( tableA ));

    checkAssociation( (UmlAssociation) extent.getCore().getUmlAssociation().refAllOfClass().iterator().next(),
                      tableA,
                      end -> {
                        assertEquals( "End at A","A_ID",end.getName() );
                      },
                      tableB,
                      end -> {
                        assertTrue( "End at B",end.getName().isEmpty() );
                      });
  }

  /**
   * @throws Exception
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    Statement statement;

    statement = connection.createStatement();
    try {
      statement.execute( "drop table B" );
    } catch (Exception ex) {
      // do nothing
    }
    try {
      statement.execute( "drop table A" );
    } catch (Exception ex) {
      // do nothing
    }
    connection.close();

    super.tearDown();
  }
}
