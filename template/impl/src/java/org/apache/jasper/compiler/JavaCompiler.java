/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.apache.jasper.compiler;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Compiler of the produced java code
 */
public interface JavaCompiler {

  /**
   * @param sources not null list of ABSOLUTE file names
   * @throws IOException on file or compilation errors foun
   */
  void compile(List<File> sources) throws IOException;

}
