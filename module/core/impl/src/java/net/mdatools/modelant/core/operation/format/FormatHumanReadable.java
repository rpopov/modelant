/*
 * Copyright (c) 2017 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Created on 20.10.2017
 */
package net.mdatools.modelant.core.operation.format;

/**
 * Concatenate the words separated by a space, where first one starts with upper case, any next one
 * is in lower case
 * @author Rusi Popov
 */
public class FormatHumanReadable extends FormatWordsString {

  public FormatHumanReadable() {
    super( " " );
  }

  /**
   * @see net.mdatools.modelant.core.operation.format.FormatWordsString#prepare(java.lang.String)
   */
  protected final String prepare(String source) {
    return source;
  }

  /**
   * @see net.mdatools.modelant.core.operation.format.FormatWordsString#formatFirstWord(java.lang.StringBuilder, java.lang.String, boolean)
   */
  protected final void formatFirstWord(StringBuilder result, String word, boolean nextWordExists) {
    formatFirstCapitalAllLower(result, word );
  }

  /**
   * @see net.mdatools.modelant.core.operation.format.FormatWordsString#formatNextWord(java.lang.StringBuilder, java.lang.String, boolean)
   */
  protected final void formatNextWord(StringBuilder result, String word, boolean nextWordExists) {
    result.append( word.toLowerCase() );
  }
}
