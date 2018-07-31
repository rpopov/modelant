/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.operation.format;

import junit.framework.TestCase;
import net.mdatools.modelant.core.api.Function;

public class FormatHumanReadableTest extends TestCase {

  public void testExecute() {
    Function<String, String> format;

    format = new FormatHumanReadable();

    assertEquals( "", format.execute( "" ) );
    assertEquals( "Abcd", format.execute( "ABCD" ) );

    assertEquals(  "Abcd efgh" , format.execute( "ABCD EFGH" ) );
    assertEquals(  "Abcd efgh" , format.execute( "ABCD_EFGH" ) );
    assertEquals(  "Abcd efgh" , format.execute( "ABCD:EFGH" ) );
    assertEquals(  "Abcd efgh" , format.execute( "ABCD::EFGH" ) );
    assertEquals(  "Abcd efgh" , format.execute( "AbcdEfgh" ) );
    assertEquals(  "Abcd efgh ijkl" , format.execute( "AbcdEfgh:Ijkl" ) );
  }
}
