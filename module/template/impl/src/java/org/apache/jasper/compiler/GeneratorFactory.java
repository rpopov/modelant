/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.apache.jasper.compiler;

/**
 * Produce a Generator of the elements of the abstract syntax tree
 */
interface GeneratorFactory {
  /**
   * @param text not null text, parsed in this mode
   * @param lexemStart not null start position of the parsed text
   * @param lexemEnd end position of the lexem to generate for
   * @return non-null generator - part of the template's abstract syntax tree
   */
  Generator construct(String text, Mark lexemStart, Mark lexemEnd);
}