/*
 * Copyright (c) 2017 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Created on 09.09.2017
 */
package org.apache.jasper.compiler;

/**
 * Generate a class import declaration
 */
final class DirectiveGenerator implements Generator {
  private final String importFragment;


  public DirectiveGenerator(String importFragment) {
    this.importFragment = importFragment;
  }
  public void generateMethod(TemplateWriter out) {
  }

  public void generateDeclaration(TemplateWriter out) {
    out.println(importFragment);
  }
}