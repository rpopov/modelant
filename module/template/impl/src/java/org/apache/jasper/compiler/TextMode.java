/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.apache.jasper.compiler;

import java.io.IOException;

/**
 * Parse a text until open* lexem or eof is found
 */
class TextMode implements Mode {

  /**
   * @see org.apache.jasper.compiler.Mode#parseNextLexem(TemplateLexer)
   */
  public void parseNextLexem(TemplateLexer templateLexer) throws IOException {
    StringBuilder resultText;
    Lexem resultLexem;

    resultLexem = Lexem.END;
    resultText = new StringBuilder(256);
    while ( !templateLexer.isEofReached() && resultLexem == Lexem.END) {

      // read the text until a possible open* lexem start
      while ( !templateLexer.isEofReached() && templateLexer.getNextChar() != '<') {
        resultText.append( templateLexer.getNextChar() );
        templateLexer.readNextChar();
      }

      // read the open lxem or
      if ( !templateLexer.isEofReached() ) { // getNextChar() == '<'
        templateLexer.readNextChar();

        if (!templateLexer.isEofReached() && templateLexer.getNextChar()== '%') { // at least scriptlet start recognized
          resultLexem = Lexem.OPEN_SCRIPTLET;
          templateLexer.readNextChar();

          if ( templateLexer.getNextChar() == '=' ) {
            resultLexem = Lexem.OPEN_EXPRESSION;
            templateLexer.readNextChar();

          } else if ( templateLexer.getNextChar() == '@'  ) {
            resultLexem = Lexem.OPEN_DIRECTIVE;
            templateLexer.readNextChar();

          } else if ( templateLexer.getNextChar() == '-'   ) {
            templateLexer.readNextChar();

            if ( templateLexer.getNextChar() == '-' ) {
              resultLexem = Lexem.OPEN_COMMENT;
              templateLexer.readNextChar();

            } else { // <%-X read, where X != '-' - this is not a comment open, just a text, continue parsing
              resultLexem = Lexem.END;
              resultText.append("<%-");
            }
          }
        } else { // not a start, the "terminator" is part of the text
          resultText.append( '<' );
        }
      }
    }

    // update the owner parser
    templateLexer.setLexem( resultLexem );
    templateLexer.setText( resultText.toString() );
  }
}