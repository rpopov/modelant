/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.operation.format;

import junit.framework.TestCase;
import net.mdatools.modelant.core.api.Operation;
import net.mdatools.modelant.core.api.operation.Compose;

public class AbbreviatedNameTest extends TestCase {

  public void testExecute() {
    Operation<String> format;

    format = new Compose(new FormatMaxLength(5), new FormatAbbreviated());

    assertEquals("", format.execute( "" ) );
    assertEquals("Abcd", format.execute( "ABCD" ) );

    assertEquals("AEfgh" , format.execute( "ABCD EFGH" ) );
    assertEquals("AEfgh" , format.execute( "ABCD_EFGH" ) );
    assertEquals("AEfgh" , format.execute( "ABCD:EFGH" ) );
    assertEquals("AEfgh" , format.execute( "ABCD::EFGH" ) );
    assertEquals("AEfgh" , format.execute( "AbcdEfgh" ) );
    assertEquals("AEIjk" , format.execute( "AbcdEfgh:Ijkl" ) );
  }

}
