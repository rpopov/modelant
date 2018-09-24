/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.util.topology;

import junit.framework.TestCase;
import net.mdatools.modelant.core.operation.model.topology.EquivalenceClassesMap;
import net.mdatools.modelant.core.operation.model.topology.EquivalenceClassesMapImpl;

/**
 * @author Rusi Popov (popovr@mdatools.net)
 */
public class EquivalenceClassesMapTest extends TestCase {

  private EquivalenceClassesMap<String> eq = new EquivalenceClassesMapImpl<String>();

  /**
   * Test method for {@link net.mdatools.modelant.core.operation.model.topology.EquivalenceClassesMap#add(java.lang.Object, java.lang.Object)}.
   */
  public void testAddX() {
    eq.add( "from", "to1" );
    eq.add( "from", "to2" );
    eq.add( "from", "to3" );

    assertEquals( 1, eq.getEquivalents("from").size());
    assertEquals( 3, eq.getEquivalents("to1").size());

    assertEquals( "to1" , eq.map("from") );
    assertEquals( "from", eq.map("to1"));
    assertEquals( "from", eq.map("to2"));
    assertEquals( "from", eq.map("to3"));

    assertEquals( "from", eq.getRepresentative("from"));
    assertEquals( "to1" , eq.getRepresentative("to1"));
    assertEquals( "to1" , eq.getRepresentative("to2"));
    assertEquals( "to1" , eq.getRepresentative("to3"));

    assertEquals( 3, eq.getEquivalents("to1").size());
    assertEquals( 3, eq.getEquivalents("to2").size());
    assertEquals( 3, eq.getEquivalents("to3").size());

    assertEquals( 1, eq.getXKeys().size());
  }

  public void testAddY() {
    eq.add( "to1", "from" );
    eq.add( "to2", "from" );
    eq.add( "to3", "from" );

    assertEquals( 1, eq.getEquivalents("from").size());
    assertEquals( 3, eq.getEquivalents("to1").size());

    assertEquals( "to1" , eq.map("from") );
    assertEquals( "from", eq.map("to1"));
    assertEquals( "from", eq.map("to2"));
    assertEquals( "from", eq.map("to3"));

    assertEquals( "from", eq.getRepresentative("from"));
    assertEquals( "to1" , eq.getRepresentative("to1"));
    assertEquals( "to1" , eq.getRepresentative("to2"));
    assertEquals( "to1" , eq.getRepresentative("to3"));

    assertEquals( 3, eq.getEquivalents("to1").size());
    assertEquals( 3, eq.getEquivalents("to2").size());
    assertEquals( 3, eq.getEquivalents("to3").size());

    assertEquals( 1, eq.getXKeys().size());
  }
}