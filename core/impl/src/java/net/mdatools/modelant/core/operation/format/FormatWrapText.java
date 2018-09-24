/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.operation.format;

import java.util.StringTokenizer;

import net.mdatools.modelant.core.api.Function;

/**
 * Wrap the provided text by adding the prefix to all lines. If the text is empty,
 * defaultVal is returned.
 *
 * @author Rusi Popov (popovr@mdatools.net)
 */
public class FormatWrapText implements Function<String,String>{

  private static final String SEPARATOR_TAB = "\t";
  private static final String SEPARATOR_SPACE = " ";
  private static final String SEPARATORS = SEPARATOR_SPACE+SEPARATOR_TAB;

  /**
   * OS specific End-Of-Line terminator
   */
  private final String lineSeparator;

  /**
   * The maximum length of a line in JavaDoc comments. After that length the text should be wrapped
   * on the next line. Guaranteed at least one word in a line.
   */
  private final int maxLineLength;
  private final String prefix;
  private final String defaultVal;

  /**
   * Wrap the paragraphs to 105 characters terminated by system-specific line separator
   * without any prefix and default value.
   */
  public FormatWrapText() {
    this("", "");
  }

  /**
   * Wrap the paragraphs to 105 characters terminated by "line.separator"
   * @param prefix not null is prefix to be inserted in the beginning of each new line
   * @param defaultVal non null is the value to prefix the first line
   */
  public FormatWrapText(String prefix, String defaultVal) {
    this(prefix, defaultVal, 105);
  }

  /**
   * Wrap the paragraphs to maxLineLength characters terminated by "line.separator"
   * @param prefix not null is prefix to be inserted in the beginning of each new line
   * @param defaultVal non null is the value to prefix the first line
   * @param maxLineLength &gt; 0
   */
  public FormatWrapText(String prefix, String defaultVal,int maxLineLength) {
    this(prefix, defaultVal, maxLineLength, System.getProperty( "line.separator" ));
  }

  /**
   * Wrap the paragraphs to maxLineLength characters terminated by lineSeparator
   * @param prefix not null is prefix to be inserted in the beginning of each new line
   * @param defaultVal non null is the value to prefix the first line
   * @param maxLineLength &gt; 0
   * @param lineSeparator not null
   */
  public FormatWrapText(String prefix, String defaultVal,int maxLineLength, String lineSeparator) {
    assert prefix != null : "Expected a non-null prefix provided";
    assert defaultVal != null : "Expected a non-null default value provided";
    assert lineSeparator != null : "Expected a non-null line separator provided";
    assert maxLineLength > 0 : "Expected a positive line length";

    this.prefix = prefix;
    this.defaultVal = defaultVal;
    this.maxLineLength = maxLineLength;
    this.lineSeparator = lineSeparator;
  }


  /**
   * Adds the prefix to all lines and replaces the line terminators LF or CR LF with the system
   * specific line terminator (CR LF or LF only). If the line is empty, defaultVal is returned.
   *
   * @param line to format
   * @return a string where all new lines (including the first one) are prefixed with the prefix
   *         string
   */
  public final String execute(String line) {
    final String result;
    String paragraph;
    StringBuilder resultLines;
    StringTokenizer paragraphs;

    if ( line != null && !line.isEmpty() ) { // there are comments
      resultLines = new StringBuilder();

      paragraphs = new StringTokenizer(line, "\n\r\f" );
      while ( paragraphs.hasMoreTokens() ) {
        paragraph = paragraphs.nextToken();

        wrapParagraph( paragraph, resultLines );
      }
      result = resultLines.toString();
    } else {
      result = defaultVal;
    }
    return result;
  }

  /**
   * Wrap the paragraph into the result lines
   * @param paragraph not null
   * @param resultLines not null
   */
  private void wrapParagraph(String paragraph, StringBuilder resultLines) {
    String token;
    StringTokenizer tokens;
    int linesize;

    linesize = 0;
    tokens = new StringTokenizer(paragraph, SEPARATORS, true); // keep the separators
    while ( tokens.hasMoreTokens() ) {
      token = tokens.nextToken();

      if ( linesize == 0 ) { // new line started
        if ( !token.equals( SEPARATOR_SPACE ) && !token.equals( SEPARATOR_TAB ) ) { // first real token
          resultLines.append( prefix );
          linesize += prefix.length();

          resultLines.append( token );
          linesize += token.length();

        } // else // parsing a separator char leading the line - do nothing, skip it
      } else { // not the first token - just collect it
        resultLines.append( token );
        linesize += token.length();
      }

      if ( linesize >= maxLineLength ) { // end of line reached
        thrimTrailingSeparator( resultLines );
        resultLines.append(lineSeparator);
        linesize=0;
      }
    }
    if (linesize>0) { // the last line is incomplete
      thrimTrailingSeparator( resultLines );
      resultLines.append(lineSeparator);
    }
  }

  /**
   * Correct the length of the resultLines to trim the trailing separators
   * @param resultLines
   */
  private void thrimTrailingSeparator(StringBuilder resultLines) {
    while (SEPARATORS.contains(""+resultLines.charAt( resultLines.length()-1 ))) {
      resultLines.setLength( resultLines.length()-1 );
    }
  }
}