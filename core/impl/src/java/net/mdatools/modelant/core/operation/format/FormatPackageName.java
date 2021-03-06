/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.operation.format;

/**
 * Concatenate the words "." separators, where all words are in lower case.
 * The letter case change, the spaces and "_" are NOT treated as words separators.
 * @author Rusi Popov (popovr@mdatools.net)
 */
public class FormatPackageName extends FormatWordsString {

  /*
   * INVARIANT
   *   all words are in lower case
   */

  /**
   * No separator between the words
   */
  public FormatPackageName() {
    this( "." );
  }

  /**
   * @see net.mdatools.modelant.core.operation.format.FormatWordsString#prepare(java.lang.String)
   */
  protected final String prepare(String source) {
    String result;

    result = source.toLowerCase() // see the invariant
                   .replace( " ", "")
                   .replace( "_", "");
    return result;
  }

  /**
   * @param separator non-null separator of the identified words
   */
  public FormatPackageName(String separator) {
    super( separator );
  }

  /**
   * @see net.mdatools.modelant.core.operation.format.FormatWordsString#formatFirstWord(java.lang.StringBuilder, java.lang.String, boolean)
   */
  protected final void formatFirstWord(StringBuilder result, String word, boolean nextWordExists) {
    result.append( word );
  }

  /**
   * @see net.mdatools.modelant.core.operation.format.FormatWordsString#formatNextWord(java.lang.StringBuilder, java.lang.String, boolean)
   */
  protected final void formatNextWord(StringBuilder result, String word, boolean nextWordExists) {
    result.append( word );
  }
}
