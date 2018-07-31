/*
 * Copyright (c) 2017 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Created on 31.10.2017
 */
package net.mdatools.modelant.core.wrap;

import net.mdatools.modelant.core.api.Translate;
import net.mdatools.modelant.core.api.wrap.WrapperFactory;

/**
 * A general mechanism for factory-specific names mapping.
 * Allows defining names translation, names' plural forms and any other factory-specific proeprties
 * @author Rusi Popov
 */
public class BaseTranslation implements Translate {

  private final WrapperFactory factory;

  public BaseTranslation(WrapperFactory factory) {
    this.factory = factory;
  }

  /**
   * @param original
   * @return
   * @see net.mdatools.modelant.core.api.Translate#translate(java.lang.String)
   */
  public final String translate(String original) {
    String result;

    original = original.trim();

    result = getProperty( original );
    if ( result == null ) {
      result = original;
    }
    return result;
  }

  /**
   * @see net.mdatools.modelant.core.api.Translate#translatePlural(java.lang.String)
   */
  public final String translatePlural(String word) {
    String result;

    word = word.trim();

    result = getProperty( Translate.PREFIX_PLURAL+word );
    if ( result == null ) {
      result = word+"s";
    }
    return result;
  }

  /**
   * @see net.mdatools.modelant.core.api.Translate#getProperty(java.lang.String)
   */
  public final String getProperty(String key) {
    String result;

    result = factory.getProperty( key );
    if (result != null ) { // an exact match found
      result = result.trim();

    } else {
      result = factory.getProperty( key.toLowerCase() );

      if (result != null) { // a general match found
        result = result.trim();

      } else {
        result = factory.getProperty( key.toUpperCase() );

        if (result != null) { // a general match found
          result = result.trim();
        }
      }
    }
    return result;
  }
}