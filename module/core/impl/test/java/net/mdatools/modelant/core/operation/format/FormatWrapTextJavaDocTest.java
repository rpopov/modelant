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

public class FormatWrapTextJavaDocTest extends TestCase {

  public void testFormatWrapTextJavaDoc() {
    Function<String, String> format;

    format = new FormatWrapTextJavaDoc();

    assertEquals( "", format.execute( "" ) );
    assertEquals( "   * word\r\n", format.execute( "word" ) );
    assertEquals( "   * word word\r\n", format.execute( "word word" ));
    assertEquals( "Keep the separators",
                  "   * word   word\r\n", format.execute( "word   word" ));
    assertEquals( "Keep the separators",
                  "   * word\t\t\tword\r\n", format.execute( "word\t\t\tword" ));
    assertEquals( "Keep the new lines",
                  "   * word\r\n   * word\r\n", format.execute( "word\r\nword" ));
    assertEquals( "Split the lines, trim the leading and trailing separators",
                  "   * word word word word word word word word word word word word word word word word word word word word\r\n   * word word word word word word word word word word word word\r\n",
                  format.execute("word word word word word word word word word word word word word word word word word word word word word word word word word word word word word word word word" ));
  }
}
