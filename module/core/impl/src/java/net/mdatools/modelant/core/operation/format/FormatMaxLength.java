/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.operation.format;

import net.mdatools.modelant.core.api.Operation;

/**
 * Convert a string to a constant name by:<ol>
 * <li>Abbreviate each word in the name, except the last one to its first letter in upper case.
 * <li>Add the whole last word to the abbreviated name, with first letter in upper case
 * <li>Restrict the name up to <b>length</b> characters
 * </ol>
 * @see FormatAbbreviated
 * @author Rusi Popov
 */
public class FormatMaxLength implements Operation<String> {

  private final int length;

  /**
   * @param length is the maximum name size where to fit the abbreviated name
   */
  public FormatMaxLength(int length) {
    this.length = length;
  }


  /**
   * @param name is a string to construct abbreviated constant name
   * @return the a
   */
  public final String execute(String name) throws RuntimeException, IllegalArgumentException {
    String result;
    int limit;

    limit = Math.min( length, name.length() );

    result = name.substring( 0, limit );
    return result;
  }
}
