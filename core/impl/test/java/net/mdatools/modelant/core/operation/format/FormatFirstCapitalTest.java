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

public class FormatFirstCapitalTest extends TestCase {

  public void testExecute() {
    Function<String, String> format;

    format = new FormatFirstCapital();

    assertEquals("", format.execute( "" ) );
    assertEquals("Abcd", format.execute( "ABCD" ) );

    assertEquals("AbcdEfgh" , format.execute( "ABCD EFGH" ) );
    assertEquals("AbcdEfgh" , format.execute( "ABCD_EFGH" ) );
    assertEquals("AbcdEfgh" , format.execute( "ABCD:EFGH" ) );
    assertEquals("AbcdEfgh" , format.execute( "ABCD::EFGH" ) );
    assertEquals("AbcdEfgh" , format.execute( "AbcdEfgh" ) );
    assertEquals("AbcdEfghIjkl" , format.execute( "AbcdEfgh:Ijkl" ) );
    assertEquals("TRavelPolicyAlertSetting" , format.execute( "TRavelPOLICYAlertSetting" ) );
    assertEquals("TravelPolicyAlertSetting" , format.execute( "TravelPOLICYAlertSetting" ) );
    assertEquals("" , format.execute( "-1" ) );
  }
}
