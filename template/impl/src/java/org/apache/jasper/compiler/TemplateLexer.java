/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.apache.jasper.compiler;

import static net.mdatools.modelant.template.api.Convention.ENCODING_DEFAULT;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

/**
 * Lexer of the ModelAnt Template language.
 * Please note:<ul>
 * <li> if we would have defined the Template language consisting of the lexems: TEXT, START TAG, END TAG, CODE, etc,
 * then detecting the TEXT and CODE lexems the next lexem would also be detected as a terminator of the text/clde. Thus the
 * template language is not LL(1), it is LL(2), so the parsing approach should be more complex
 * <li> once the text/code is not nested and each text/code is terminated by an opening/closing lexem or the EOF, then it makes
 * sense to get at once 2 lexems (as of LL(2) above) or just have the text and code a property of the open/close/EOF lexem read,
 * having no dedicated text and code lexems. Thus:<ul>
 *   <li> the text is bound to the next OPENING lexem, so it produces TEXT generator
 *   <li> the opening lexem switches the mode/context to parsing specific part of the code
 *   <li> the closing lexem of the code actually produces the CODE generator
 * </ul>
 * </ul>
 * @author Rusi Popov
 */
class TemplateLexer {

  private final Reader reader;
  private final Mark position;

  /**
   * If the stream's end was reached
   */
  private boolean eofReached;

  /**
   * If !isEofReached, nextChar holds the next character to read - the first char of the next lexem
   * after nextLexem
   */
  private char nextChar;

  /**
   * The lexem to process, not null
   */
  private Lexem lexem;

  /**
   * If reading template text or code
   */
  private Mode mode;

  /**
   * The textual value of the lexem found. Not null.
   */
  private String text;

  /**
   * Not null position where the lexem started
   */
  private Mark lexemPosition;

  /**
   * Open the provided source and read the first lexem of it
   * @param templateSourceUrl
   * @param templateEncoding
   * @throws IOException
   */
  public TemplateLexer(URL templateSourceUrl, String templateEncoding) throws IOException {
    if ( templateEncoding == null ) {
      templateEncoding = ENCODING_DEFAULT;
    }
    this.reader = new BufferedReader( new InputStreamReader( templateSourceUrl.openStream(), templateEncoding ));
    this.position = new Mark(templateSourceUrl.toString());

    this.mode = new TextMode();

    readNextChar();
    readNextLexem();
  }

  /**
   * Read the next character and set the isEofReached and nextChar
   * @throws IOException
   */
  void readNextChar() throws IOException {
    int ch;

    if ( !eofReached ) {
      ch = reader.read();
      eofReached = (ch == -1);
      nextChar = (char) ch;

      if ( ch == '\n' ) {
        position.newLine();
      } else {
        position.newChar();
      }
    }
  }

  /**
   * Requires: !isEofReached
   * @return the currently read / preapred next character to read
   */
  char getNextChar() {
    assert !eofReached : "Expected the end of the template source is not reached";
    return nextChar;
  }

  final boolean isEofReached() {
    return eofReached;
  }

  /**
   * @return the current lexem read
   */
  public final Lexem getLexem() {
    return lexem;
  }

  /**
   * Read the next lexem+text from the source
   * @throws IOException
   */
  public void readNextLexem() throws IOException {
    if ( eofReached ) {
      setLexem( Lexem.END );
      setText( "" );
    } else {
      lexemPosition = new Mark(position);
      mode.parseNextLexem(this); // lexem, text are set
      mode = lexem.nextMode;
    }
  }

  /**
   * Use the current lexem read to produce the element of the abstract syntax tree
   * for the parsed text for it
   * @return not null
   */
  public final Generator generate() {
    return lexem.generate.construct( text, lexemPosition, new Mark(position));
  }

  public final void close() throws IOException {
    reader.close();
  }

  final void setText(String text) {
    this.text = text;
  }

  final void setLexem(Lexem lexem) {
    this.lexem = lexem;
  }
}