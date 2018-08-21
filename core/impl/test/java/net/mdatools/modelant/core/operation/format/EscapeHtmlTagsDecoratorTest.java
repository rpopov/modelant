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

public class EscapeHtmlTagsDecoratorTest extends TestCase {

  public void testEscape() {
    Operation<String> format;

    format = new EscapeHtmlTags();

    assertEquals("abc lt gt xyz", format.execute( "abc lt gt xyz" ));
    assertEquals("abc &lt; &gt; xyz", format.execute("abc < > xyz"));
  }
}
