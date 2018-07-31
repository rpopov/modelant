/*
 * Copyright (c) 2001,2012 Rusi Popov, MDA Tools.net
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Rusi Popov (popovr@mdatools.net) - initial implementation
 */
package org.omg.uml13.wrap.foundation.core;

import net.mdatools.modelant.core.wrap.Factory;

import org.omg.uml13.wrap.base.foundation.core.BaseWrapElement;

/**
 * This is a wrapper of org.omg.uml13.foundation.core.Element that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public class WrapElement extends BaseWrapElement {

  public WrapElement(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  public final String formatRTF( String text ) {
    String result;

    result = text.replaceAll( "\r\n",     "\\\\par " )
                 .replaceAll( "<p>\r\n",  "\\\\par " )
                 .replaceAll( "\r\n<p>",  "\\\\par " )
                 .replaceAll( "<p>",      "\\\\par " )
                 .replaceAll( "<ul>",     "\\\\par " )
                 .replaceAll( "</ul>",    "\\\\par " )
                 .replaceAll( "<li>",     "\\\\par " )

                 .replaceAll( "\r\n\u0095", "\\\\par -\\t" )
                 .replaceAll( "\u0095", "-" )
                 .replaceAll( "\u0092", "'" )
                 .replaceAll( "\u0093", "'" )
                 .replaceAll( "\u0094", "'" )
                 .replaceAll( "\"\t", "-\\t" )
                 .replaceAll( "-- ",  "-\\t" )
                 .replaceAll( "--",   "-\\t" )

                 .replaceAll( "\r\n", "\\\\par " )
                 .replaceAll( "\n",   "\\\\par " )
                 .replaceAll( "\r",   "\\\\par " )
                 .replaceAll( "\t",   " " )

                 .replaceAll( "  ",   " " );

    return result;
  }
}