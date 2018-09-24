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
 * Replace the &lt; start-of-tag and &gt; end-of-tag characters by corresponding HTML entities.
 * @author Rusi Popov (popovr@mdatools.net)
 */
public class EscapeHtmlTags implements Operation<String> {

  /**
   * @see net.mdatools.modelant.core.api.Function#execute(java.lang.Object)
   */
  public String execute(String toEscape) throws RuntimeException, IllegalArgumentException {
    StringBuilder result;
    char character;

    result = new StringBuilder();

    for (int i=0; i< toEscape.length(); i++) {
      character = toEscape.charAt(i);

      if ( character == '<' ) {
        result.append( "&lt;" );

      } else if ( character == '>' ) {
        result.append( "&gt;" );

      } else { // the char is not a special one add it to the result as is
        result.append( character );
      }
    }
    return result.toString();
  }
}
