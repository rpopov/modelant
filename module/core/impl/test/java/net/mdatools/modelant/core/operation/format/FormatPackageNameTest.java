/*
 * Copyright (c) 2017 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Created on 29.10.2017
 */
package net.mdatools.modelant.core.operation.format;

import junit.framework.TestCase;
import net.mdatools.modelant.core.api.Function;

public class FormatPackageNameTest extends TestCase {

  public void testExecute() {
    Function<String, String> format;

    format = new FormatPackageName();

    assertEquals( "", format.execute( "" ) );
    assertEquals( "abcd", format.execute( "ABCD" ) );

    assertEquals(  "abcdefgh" , format.execute( "ABCD EFGH" ) );
    assertEquals(  "abcdefgh" , format.execute( "ABCD_EFGH" ) );
    assertEquals(  "abcd.efgh" , format.execute( "ABCD:EFGH" ) );
    assertEquals(  "abcd.efgh" , format.execute( "ABCD::EFGH" ) );
    assertEquals(  "abcdefgh" , format.execute( "AbcdEfgh" ) );
    assertEquals(  "abcdefgh.ijkl" , format.execute( "AbcdEfgh:Ijkl" ) );
  }
}
