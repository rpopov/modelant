/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.operation.format;

import java.util.Iterator;

import net.mdatools.modelant.core.api.Operation;
import net.mdatools.modelant.core.util.NameTokenizer;

/**
 * Extract the words from the string, format them ad concatenate them according
 * @author Rusi Popov
 */
public abstract class FormatWordsString implements Operation<String> {

  private final String separator;

  /**
   * @param separator not null, possibly empty string to use as a separator between the result words
   */
  protected FormatWordsString(String separator) {
    if ( separator == null) {
      throw new IllegalArgumentException("Expected a non-null separator string");
    }
    this.separator = separator;
  }


  /**
   * @param source not null string to parse
   * @return string of the formatted words from the source string, concatenated with the separator string provided
   * @throws RuntimeException
   * @throws IllegalArgumentException
   * @see net.mdatools.modelant.core.api.Function#execute(java.lang.Object)
   */
  public final String execute(String source) throws RuntimeException, IllegalArgumentException {
    StringBuilder result;
    Iterator<String> wordIterator;
    String word;

    assert source != null : "Expected a non-null source";

    result = new StringBuilder(source.length());

    source = prepare(source);

    wordIterator = new NameTokenizer( source );
    while ( wordIterator.hasNext() ) {
      word = wordIterator.next();

      if ( result.length() == 0 ) {
        formatFirstWord( result, word, wordIterator.hasNext() );

      } else {
        result.append( separator );
        formatNextWord( result, word, wordIterator.hasNext() );
      }
    }
    return result.toString();
  }

  /**
   * Implement any pre-processing of the source string, before parsing it into words
   * @param source the not-null string to format
   * @return the sanitized string to parse into words
   */
  protected abstract String prepare(String source);


  /**
   * Concatenate the formatted word into the result builder
   * @param result non null store of the produced word, result.lenght() = 0
   * @param word not null, not empty word of letters and digits, which is NOT the first word identified in the source string
   * @param nextWordExists true, if there are more words to be formatted, false when this is the only word to format
   */
  protected abstract void formatFirstWord(StringBuilder result, String word, boolean nextWordExists);


  /**
   * Concatenate the formatted word into the result builder
   * @param result non null store of the produced word, result.lenght() != 0
   * @param word not null, not empty word of letters and digits, which is the first word identified in the source string
   * @param nextWordExists true, if there are more words to be formatted, false when this is the last word to format
   */
  protected abstract void formatNextWord(StringBuilder result, String word, boolean nextWordExists);


  /**
   * Append to result the string provided, with first letter capitalized and all next in lower case
   *
   * @param name not null to capitalize
   * @param result not null result/output parameter
   */
  protected final void formatFirstCapitalAllLower(StringBuilder result, String name) {
    if ( name.length() > 0 ) {
      result.append(Character.toUpperCase( name.charAt( 0 ) ) );
      result.append(name.substring( 1 ).toLowerCase() );
    }
  }
}
