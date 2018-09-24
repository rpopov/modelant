/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.util.key;

/**
 * @author Rusi Popov (popovr@mdatools.net)
 */
public class Hash {

  /**
   * Calculates a fast hash based on chars spread among the whole string
   * @param string
   * @return a hash for that string
   */
  public static int hash(String string) {
    int result = 0;
    int pos = 0;
    int step;
    int length;
    
    if ( string != null 
         && (length = string.length() ) > 0 ){ 
         
       step = 1 + (length >> 3); 
       for ( int i = 0; i < 8 && i < length; i++ ) {
         result = result * 7 + string.charAt( pos );
         pos = (pos + step) % length;
       }
    }
    return result;
  }
}