/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.apache.jasper.compiler;

abstract class DecorateGenerator implements Generator {

  protected final Generator generator;
  protected final Mark start;
  protected final Mark stop;


  protected DecorateGenerator(Generator generator, Mark start, Mark stop) {
    this.generator = generator;
    this.start = start;
    this.stop = stop;
  }

  /**
   * Generates "start-of the code block" comment
   * @param out The ServletWriter
   */
  protected void generateStartComment(TemplateWriter out) {
    out.print( "// begin" );
    out.print( " [file=" );
    out.print( start.getFile() );
    out.print( ";from=" );
    out.print( start.toShortString());
    out.print( ";to=" );
    out.print( stop.toShortString());
    out.println( "]" );
    out.pushIndent();
  }

  /**
   * Generates "end-of the code block" comment
   * @param out The ServletWriter
   */
  protected void generateEndComment(TemplateWriter out) {
    out.popIndent();
    out.println( "// end" );
  }

}