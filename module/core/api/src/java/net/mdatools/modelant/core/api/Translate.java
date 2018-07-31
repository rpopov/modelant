/*
 * Copyright (c) 2017 Rusi Popov, MDA Tools.net All rights reserved. 
 * 
 * This program and the accompanying materials are made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Created on 31.10.2017
 */
package net.mdatools.modelant.core.api;

public interface Translate<A> {

  /**
   * The prefix of the properties for plural forms translation using a properties file.
   * This the context properties file
   */
  String PREFIX_PLURAL = "plural.";


  /**
   * This method translates an original string. First it searches for
   * for exact match, and if it is not found, searches for a lower case match (a general one),
   * and if no mapping is found the type is returned as it is.
   * @param original is a non-null string to be translated
   * @return the translation or the same string
   */
  String translate(String original);


  /**
   * Returns the plural form of the word provided. If no plural form is
   * found, this method returns the same word with "s" appended.
   * @param word is the non-null word to search its plural form
   * @return the plural form as detected as of the translation map and rules
   */
  String translatePlural(String word);


  /**
   * This method allows finding properties for Wrapper subclasses, loaded through the same factory instance
   * @param key is a non-null, non-empty key
   * @return the string value bound to key. Returns null when nothing found
   */
  String getProperty(String key);

}