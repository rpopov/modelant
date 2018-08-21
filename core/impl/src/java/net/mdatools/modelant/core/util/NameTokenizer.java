/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class is a parser of identifiers used to split them into a sequence of words that they are
 * composed of. The words are identified when they are terminated by non-alphanumeric characters or
 * by changing the letter's case. It is useful when parsing the words that are included in for
 * example in Java identifiers. This class will parse its name NameTokenizer into 2 words: "Name"
 * and "Tokenizer". The words identified start with a letter and contain letter of digit. Any
 * non-alphanumeric characters are skipped and used only to terminate words. No empty words are
 * identified. Rules to identify words:
 * <ul>
 * <li>Words can consist all of lower case letters
 * <li>Words can start with 1 upper case letter and continue in lower case
 * <li>Words starting with more than 1 upper case letter are split into two words:
 * <ul>
 * <li>a word all in upper case
 * <li>a next word starting with exactly one letter in upper case and all other in lower case (at
 * least one exists)
 * </ul>
 * <li>Words that contain digits are terminated before the first non-digit letter after the digits
 * <li>Any leading digits are skipped
 * </ul>
 * @author rpopov
 */
public class NameTokenizer implements Iterator<String> {
  /**
   * Internal states of the name parser.
   */
  private static final int SEPARATOR_STATE = 0;

  private static final int WORD_WITH_FIRST_UPPER_STATE = 1;

  private static final int WORD_IN_LOWER_CASE_STATE = 2;

  private static final int WORD_WITH_DIGITS_STATE = 3;

  /**
   * Here are stored the identified words in the order they occure
   */
  private final Iterator<String> wordsIterator;


  /**
   * This is the only constructor of the name tokenizer - it prepares it for parsing the source
   * string/identifier.
   *
   * @param source is the identifier or general text to be parsed
   */
  public NameTokenizer(String source) {
    this.wordsIterator = parse( source ).iterator();
  }


  /**
   * This method parses the source identifier/text and returns a list of words identified. The word
   * separators are:
   * <ul>
   * <li>Changes to Upper case all first letters of the words in it
   * <li>Removes all spaces, _ ,$,\t\n\r\f etc
   * </ul>
   * Rules to identify words:
   * <ul>
   * <li>Words can consist all of lower case letters
   * <li>Words can start with 1 upper case letter and continue in lower case
   * <li>Words starting with more than 1 upper case letter are split into two words:
   * <ul>
   * <li>a word all in upper case
   * <li>a next word starting with exactly one letter in upper case and all other in lower case (at
   * least one exists)
   * </ul>
   * <li>Words that contain digits are terminated before the first non-digit letter after the
   * digits
   * <li>Any leading digits are skipped
   * </ul>
   *
   * @param source to format a class name from
   * @return a list of the words identified
   */
  private static List<String> parse(String source) {
    List<String> result;
    int len;
    StringBuilder word;
    int i = 0;
    char current;
    int state;

    result = new ArrayList<>();

    len = source.length();
    word = new StringBuilder( len );
    i = 0;
    state = SEPARATOR_STATE;

    // parses the name char-by-char and add the words identified in result list
    while ( i < len ) {
      current = source.charAt( i++ );

      switch ( state ) {
        case SEPARATOR_STATE: // only separators have been parsed (if any)
          if ( Character.isLetter( current ) ) { // Class name may start only with a letter
            word.append( current );

            if ( Character.isUpperCase( current ) ) { // a word started in upper case
              state = WORD_WITH_FIRST_UPPER_STATE;

            } else { // a word started in lower case
              state = WORD_IN_LOWER_CASE_STATE;
            }
          } else if ( Character.isDigit( current ) && result.size() > 0 ) { // a word started with a digit, but not the first word in the identifier
            word.append( current );
            state = WORD_WITH_DIGITS_STATE;

          } // any word separators are skipped
          break;

        case WORD_WITH_FIRST_UPPER_STATE: // the first word letter has been added in capital
          if ( Character.isLetterOrDigit( current ) ) {
            if ( Character.isLowerCase( current ) ) { // may be this lower case letter is the last
                                                      // of this word
              word.append( current );
              state = WORD_IN_LOWER_CASE_STATE;

            } else if ( Character.isDigit( current ) ) { // the current word continues with digits
              word.append( current );
              state = WORD_WITH_DIGITS_STATE;

            } else { // the second letter of this word is in upper case

              if ( i < len && Character.isLowerCase( source.charAt( i ) ) ) { // a new word with
                                                                              // first capital and
                                                                              // next lower
                // starts with the current letter if it is capital
                // complete this word
                result.add( word.toString() );

                // construct a new word starting from this - it is with first capital (and next
                // lower) case letters
                word.setLength( 0 );
                word.append( current );

              } else { // this word is (by now) all in capital
                word.append( current );
              }
            }
          } else { //not a letter or digit recognized - this is a terminator
            // complete this word
            result.add( word.toString() );

            // construct an empty new word starting from this
            word.setLength( 0 );
            state = SEPARATOR_STATE;
          }
          break;

        case WORD_IN_LOWER_CASE_STATE: // any upper case letter will be treated as a new word start
          if ( Character.isLowerCase( current ) ) { // the same word continues
            word.append( current );

          } else if ( Character.isDigit( current ) ) { // the current word continues with digits
            word.append( current );
            state = WORD_WITH_DIGITS_STATE;

          } else { // this word terminates
            // store the current word
            result.add( word.toString() );

            // start the new word
            word.setLength( 0 );

            if ( Character.isUpperCase( current ) ) { // a new word started
              word.append( current );
              state = WORD_WITH_FIRST_UPPER_STATE;
            } else { // a terminator found
              state = SEPARATOR_STATE;
            }
          }
          break;
        case WORD_WITH_DIGITS_STATE: // any non-digit letter will be treated as a new word start
          if ( Character.isDigit( current ) ) { // the same word continues
            word.append( current );
          } else {
            // store the current word
            result.add( word.toString() );

            // start the new word
            word.setLength( 0 );

            if ( Character.isLetter( current ) ) { // a new word begins
              word.append( current );

              if ( Character.isUpperCase( current ) ) { // a new word started in upper case
                state = WORD_WITH_FIRST_UPPER_STATE;

              } else { // a word started in lower case
                state = WORD_IN_LOWER_CASE_STATE;
              }
            } else { // non-alphanumeric letters are treated as separators
              state = SEPARATOR_STATE;
            }
          }
          break;
      }
    }
    //  store the last word parsed
    if ( word.length() > 0 ) {
      result.add( word.toString() );
    }
    return result;
  }


  /**
   * This method removes the word just retrieved by next(). Its usage in the context of this class
   * seems to be obsolete. It is provided for completeness.
   *
   * @see java.util.Iterator#remove()
   */
  public void remove() {
    wordsIterator.remove();
  }


  /**
   * This method checks if there is a following word identified.
   *
   * @return true is one exists
   * @see java.util.Iterator#hasNext()
   */
  public boolean hasNext() {
    return wordsIterator.hasNext();
  }


  /**
   * This method returns the next word identified if one exists. The object it returns is a string.
   *
   * @return a string representing the next word identified
   * @see java.util.Iterator#next()
   */
  public String next() {
    return wordsIterator.next();
  }
}