/*
 * Copyright (c) 2017 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Created on 28.10.2017
 */
package net.mdatools.modelant.core.operation.format;

import junit.framework.TestCase;
import net.mdatools.modelant.core.api.Function;

public class FormatAbbreviatedTest extends TestCase {

  public void testExecute() {
    Function<String, String> format;

    format = new FormatAbbreviated();

    assertEquals("", format.execute( "" ) );
    assertEquals("Abcd", format.execute( "ABCD" ) );

    assertEquals("AEfgh" , format.execute( "ABCD EFGH" ) );
    assertEquals("AEfgh" , format.execute( "ABCD_EFGH" ) );
    assertEquals("AEfgh" , format.execute( "ABCD:EFGH" ) );
    assertEquals("AEfgh" , format.execute( "ABCD::EFGH" ) );
    assertEquals("AEfgh" , format.execute( "AbcdEfgh" ) );
    assertEquals("AEIjkl" , format.execute( "AbcdEfgh:Ijkl" ) );
  }
}
