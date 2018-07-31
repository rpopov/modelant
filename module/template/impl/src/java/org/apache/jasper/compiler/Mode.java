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
 * Mode of parsing - text parsing until any open lexem + the lexem itself
 * or parsing code until closing lexem + the lexem itself
 * @author Rusi Popov
 */
interface Mode {
  /**
   * Requires: !isEofReached
   * Read the next lexem into lexem and text fields of the owner class
   * @param templateLexer not null lexer to read the source through
   * @throws IOException
   */
  void parseNextLexem(TemplateLexer templateLexer) throws IOException;
}