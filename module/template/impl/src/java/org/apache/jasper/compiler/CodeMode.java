/*
 * Copyright (c) 2017 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Created on 09.09.2017
 */
package org.apache.jasper.compiler;

import java.io.IOException;

/**
 * Parse the text until the terminator (closing) lexem to find.
 * If EOF is found, then the lexem is not found and throw a corresponding
 * exception.
 */
class CodeMode implements Mode {

  /**
   * The the terminator to read the text up to, which actually is the lexem
   * to find
   */
  private final Lexem terminator;

  public CodeMode(Lexem terminator) {
    this.terminator = terminator;
  }

  /**
   * Parse the text until the terminator (closing lexem) is found.
   * This text is actually the fragment of the code to produce
   * @see org.apache.jasper.compiler.Mode#parseNextLexem(TemplateLexer)
   */
  public void parseNextLexem(TemplateLexer templateLexer) throws IOException {
    StringBuilder resultText;
    StringBuilder terminatorText;
    boolean foundTerminator;
    char terminatorStart;
    int positionInTerminator;

    resultText = new StringBuilder(256);
    terminatorStart = terminator.name.charAt( 0 );

    do {
      // collect the text until the (possible) terminator
      while ( !templateLexer.isEofReached() && templateLexer.getNextChar() != terminatorStart) {
        resultText.append( templateLexer.getNextChar() );
        templateLexer.readNextChar();
      }

      // parse the terminator
      terminatorText = new StringBuilder();
      positionInTerminator = 0;
      while ( !templateLexer.isEofReached()
              && positionInTerminator < terminator.name.length()
              && templateLexer.getNextChar() == terminator.name.charAt( positionInTerminator ) ) {
        terminatorText.append( templateLexer.getNextChar() );
        templateLexer.readNextChar();
        positionInTerminator++;
      }

      foundTerminator = positionInTerminator >= terminator.name.length();

      if ( !foundTerminator ) { // just a text found
        resultText.append( terminatorText );
      }
    } while (!foundTerminator && !templateLexer.isEofReached());

    if ( !foundTerminator ) { // the code is not closed appropriately
      throw new IOException("Expected \""+terminator.name+"\" instead of end-of-file");
    }

    // update the owner parser
    templateLexer.setLexem( terminator );
    templateLexer.setText( resultText.toString() );
  }
}