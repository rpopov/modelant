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

public class FormatAllLowerTest extends TestCase {

  public void testExecute() {
    Function<String, String> format;

    format = new FormatAllLower();

    assertEquals("", format.execute( "" ) );
    assertEquals("abcd", format.execute( "ABCD" ) );

    assertEquals("abcdefgh" , format.execute( "ABCD EFGH" ) );
    assertEquals("abcdefgh" , format.execute( "ABCD_EFGH" ) );
    assertEquals("abcdefgh" , format.execute( "ABCD:EFGH" ) );
    assertEquals("abcdefgh" , format.execute( "ABCD::EFGH" ) );
    assertEquals("abcdefgh" , format.execute( "AbcdEfgh" ) );
    assertEquals("abcdefghijkl" , format.execute( "AbcdEfgh:Ijkl" ) );
  }
}
