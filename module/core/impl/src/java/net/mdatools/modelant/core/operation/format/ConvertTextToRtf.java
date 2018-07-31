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
 * Convert some known HTML tags to corresponding RTF tags
 * @author Rusi Popov
 */
public class ConvertTextToRtf implements Operation<String> {

  /**
   * Convert some known HTML tags to corresponding RTF tags
   * @param text
   * @return the text including RTF tags
   * @see net.mdatools.modelant.core.api.Function#execute(java.lang.Object)
   */
  public String execute(String text) throws RuntimeException, IllegalArgumentException {
    String result;

    result = text.replaceAll( "<p>",      "\\\\par " )
                 .replaceAll( "<ul>",     "\\\\par " )
                 .replaceAll( "</ul>",    "\\\\par " )
                 .replaceAll( "<li> *",   "\\\\par " )

                 .replaceAll( "\r\n\u0095", "\\\\par -\t" )
                 .replaceAll( "\u0095", "-" )
                 .replaceAll( "\u0092", "'" )
                 .replaceAll( "\u0093", "'" )
                 .replaceAll( "\u0094", "'" )
                 .replaceAll( "\"\t", "-\t" )
                 .replaceAll( "-- *", "-\t" )

                 .replaceAll( "\r\n *", "\\\\par " )
                 .replaceAll( "\n *",   "\\\\par " )
                 .replaceAll( "\r *",   "\\\\par " )

                 .replaceAll( "  +",   " " );
    return result;
  }
}
