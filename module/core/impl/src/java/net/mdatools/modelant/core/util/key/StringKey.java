/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.util.key;

/**
 * This is an effectively hashed string key for use hashed data structures.
 * @author Rusi Popov
 */
public final class StringKey {

  private final String string;

  /**
   * caches the hash code so it is not calculated all the time
   */
  private final int hash;

  public StringKey(String string) {
    this.string = string;
    this.hash = Hash.hash(string);
  }

  public int hashCode() {
    return hash;
  }

  public boolean equals(Object obj) {
    boolean result;

    result = obj == this
             || obj != null
                && obj.getClass() == StringKey.class
                && ( string == null && ((StringKey) obj).string == null
                     || string != null && string.equals( ((StringKey) obj).string ) );
    return result;
  }

  public String toString() {
    return getClass().getSimpleName()+"{string:"+string+"}";
  }
}