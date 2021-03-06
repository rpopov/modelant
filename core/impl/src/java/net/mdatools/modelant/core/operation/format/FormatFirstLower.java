/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.operation.format;

/**
 * Concatenate the words without separators, where first one is in lower case, any next one
 * is with first letter capitalized
 * @author Rusi Popov (popovr@mdatools.net)
 */
public class FormatFirstLower extends FormatWordsString {

  public FormatFirstLower() {
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
    result.append( word.toLowerCase() );
  }

  /**
   * @see net.mdatools.modelant.core.operation.format.FormatWordsString#formatNextWord(java.lang.StringBuilder, java.lang.String, boolean)
   */
  protected void formatNextWord(StringBuilder result, String word, boolean nextWordExists) {
    formatFirstCapitalAllLower( result, word );
  }
}
