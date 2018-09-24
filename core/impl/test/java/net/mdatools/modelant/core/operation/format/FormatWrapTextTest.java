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

/**
 * Test the FormatWrapText operation
 * @author Rusi Popov (popovr@mdatools.net)
 */
public class FormatWrapTextTest extends TestCase {

  public void testFormatWrapText() {
    Function<String, String> format;

    format = new FormatWrapText();

    assertEquals( "", format.execute( "" ) );
    assertEquals( "word\r\n", format.execute( "word" ) );
    assertEquals( "word word\r\n", format.execute( "word word" ));
    assertEquals( "Keep the separators",
                  "word   word\r\n", format.execute( "word   word" ));
    assertEquals( "Keep the separators",
                  "word\t\t\tword\r\n", format.execute( "word\t\t\tword" ));
    assertEquals( "Keep the new lines",
                  "word\r\nword\r\n", format.execute( "word\r\nword" ));
    assertEquals( "Split the lines, trim the leading and trailing separators",
                  "word word word word word word word word word word word word word word word word word word word word word\r\nword word word word word word word word word word word\r\n",
                  format.execute("word word word word word word word word word word word word word word word word word word word word word word word word word word word word word word word word" ));
  }


  public void testFormatWrapTextStringString() {
    Function<String, String> format;

    format = new FormatWrapText("!","$");

    assertEquals( "$", format.execute( "" ) );
    assertEquals( "!word\r\n", format.execute( "word" ) );
    assertEquals( "!word word\r\n", format.execute( "word word" ));
    assertEquals( "Keep the separators",
                  "!word   word\r\n", format.execute( "word   word" ));
    assertEquals( "Keep the separators",
                  "!word\t\t\tword\r\n", format.execute( "word\t\t\tword" ));
    assertEquals( "Keep the separators",
                  "!word\r\n!word\r\n", format.execute( "word\r\nword" ));
    assertEquals( "Split the lines, trim the leading and trailing separators",
                  "!word word word word word word word word word word word word word word word word word word word word word\r\n!word word word word word word word word word word word\r\n",
                  format.execute("word word word word word word word word word word word word word word word word word word word word word word word word word word word word word word word word" ));
  }


  public void testFormatWrapTextStringStringInt() {
    Function<String, String> format;

    format = new FormatWrapText("!","$",20);

    assertEquals( "$", format.execute( "" ) );
    assertEquals( "!word\r\n", format.execute( "word" ) );
    assertEquals( "!word word\r\n", format.execute( "word word" ));
    assertEquals( "Keep the separators",
                  "!word   word\r\n", format.execute( "word   word" ));
    assertEquals( "Keep the separators",
                  "!word\t\t\tword\r\n", format.execute( "word\t\t\tword" ));
    assertEquals( "Keep the new lines",
                  "!word\r\n!word\r\n", format.execute( "word\r\nword" ));
    assertEquals( "Split the lines, trim the leading and trailing separators",
                  "!word word word word\r\n!word word word word\r\n!word word word\r\n",
                  format.execute("word word word word word word word word word word word" ));
  }


  public void testFormatWrapTextStringStringIntString() {
    Function<String, String> format;

    format = new FormatWrapText("!","$",20,"\n");

    assertEquals( "$", format.execute( "" ) );
    assertEquals( "!word\n", format.execute( "word" ) );
    assertEquals( "!word word\n", format.execute( "word word" ));
    assertEquals( "Keep the separators",
                  "!word   word\n", format.execute( "word   word" ));
    assertEquals( "Keep the separators",
                  "!word\t\t\tword\n", format.execute( "word\t\t\tword" ));
    assertEquals( "Keep the new lines",
                  "!word\n!word\n", format.execute( "word\r\nword" ));
    assertEquals( "Split the lines, trim the leading and trailing separators",
                  "!word word word word\n!word word word word\n!word word word\n",
                  format.execute("word word word word word word word word word word word" ));
    assertEquals( "Split into paragraphs, trim the leading and trailing separators",
                  "!word word word\n!word word word\n!word word word\n",
                  format.execute("word word word   \r\n  word word word \r\n word word word " ));
  }
}
