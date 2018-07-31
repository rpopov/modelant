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
/*
 * Based on Apache Tomcat code
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
   * @param out not null diagnostic messages stream
   * @throws IOException on file or compilation errors foun
   */
  void compile(List<File> sources) throws IOException;

}
