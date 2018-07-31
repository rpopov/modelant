/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.apache.jasper.compiler;

/**
 * Generate a comment in the code of the translated template
 * @author Rusi Popov
 */
public class CommentGenerator implements Generator {

  private final String text;

  public CommentGenerator(String text) {
    this.text = text;
  }


  /**
   * @see org.apache.jasper.compiler.Generator#generateMethod(org.apache.jasper.compiler.TemplateWriter)
   */
  public void generateMethod(TemplateWriter out) {
    out.print( "/*" );
    out.printMultiLn( text );
    out.println(" */");
  }


  /**
   * @see org.apache.jasper.compiler.Generator#generateDeclaration(org.apache.jasper.compiler.TemplateWriter)
   */
  public void generateDeclaration(TemplateWriter out) {
  }
}
