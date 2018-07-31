/*
 * Copyright (c) 2017 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Created on 22.08.2017
 */
package org.apache.jasper.compiler;

/**
 * Generate comments around the contents of the wrapped generator
 * @author Rusi Popov
 */
class DecorateDirectiveGenerator extends DecorateGenerator implements Generator {
  /**
   * @param generator not null to delegate to
   * @param start not null
   * @param stop not null
   */
  DecorateDirectiveGenerator(Generator generator, Mark start, Mark stop) {
    super(generator, start, stop);
  }


  public final void generateMethod(TemplateWriter out) {
    generator.generateMethod( out );
  }

  /**
   * @see org.apache.jasper.compiler.Generator#generateDeclaration(org.apache.jasper.compiler.TemplateWriter)
   */
  public void generateDeclaration(TemplateWriter out) {
    generateStartComment( out );
    generator.generateDeclaration( out );
    generateEndComment( out );
  }
}