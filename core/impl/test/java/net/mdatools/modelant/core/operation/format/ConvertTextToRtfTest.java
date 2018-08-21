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

public class ConvertTextToRtfTest extends TestCase {

  public void testExecute() {
    Operation<String> format;

    format = new ConvertTextToRtf();

    assertEquals("abc lt gt xyz", format.execute( "abc lt gt xyz" ));
    assertEquals("\\par ", format.execute("\r"));
    assertEquals("\\par ", format.execute("\r\n"));
  }
}
