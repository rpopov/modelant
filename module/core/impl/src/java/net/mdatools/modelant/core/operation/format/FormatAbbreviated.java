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
 * Convert a string to a constant name by:<ol>
 * <li>Abbreviate each word in the name, except the last one to its first letter in upper case.
 * <li>Add the whole last word to the abbreviated name, with first letter in upper case
 * <li>Restrict the name up to <b>length</b> characters
 * </ol>
 * @author Rusi Popov
 */
public class FormatAbbreviated extends FormatWordsString {

  public FormatAbbreviated() {
    super( "" );
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
  protected void formatFirstWord(StringBuilder result, String word, boolean nextWordExists) {
    addWord( result, word, nextWordExists );
  }

  /**
   * Abbreviate the provided word or capitalize its first letter
   * @param result
   * @param word
   * @param nextWordExists
   */
  private void addWord(StringBuilder result, String word, boolean nextWordExists) {
    if (nextWordExists) {
      result.append( Character.toUpperCase( word.charAt( 0 )) );
    } else {
      formatFirstCapitalAllLower( result, word );
    }
  }

  /**
   * @see net.mdatools.modelant.core.operation.format.FormatWordsString#formatNextWord(java.lang.StringBuilder, java.lang.String, boolean)
   */
  protected void formatNextWord(StringBuilder result, String word, boolean nextWordExists) {
    addWord( result, word, nextWordExists );
  }
}
