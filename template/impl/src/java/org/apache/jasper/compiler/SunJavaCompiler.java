/*
 *
 * ====================================================================
 *
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 1999 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "The Jakarta Project", "Tomcat", and "Apache Software
 *    Foundation" must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache"
 *    nor may "Apache" appear in their names without prior written
 *    permission of the Apache Group.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */

package org.apache.jasper.compiler;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import net.mdatools.modelant.template.api.TemplateCompilationContext;


/**
 * @author Anil K. Vijendran
 *
 * Contributors:
 *    Rusi Popov (popovr@mdatools.net) - extreme refactoring and documenting for ModelANT purposes
 */
class SunJavaCompiler implements JavaCompiler {

  /**
   * This is a common logger for all utils
   */
  private static final Logger LOGGER = Logger.getLogger( SunJavaCompiler.class.getName() );

  private final String classpath;

  private final TemplateCompilationContext compilationContext;

  private final String encodingJavaFile;

  /**
   * @param compilationContext not null
   * @param encodingJavaFile the encoding of the java files to compile
   * @throws MalformedURLException when invalid classpath entry provided
   */
  public SunJavaCompiler(TemplateCompilationContext compilationContext, String encodingJavaFile) throws MalformedURLException {
    this.classpath = System.getProperty( "java.class.path" )
                     + File.pathSeparator
                     + compilationContext.getClassPath()
                     + File.pathSeparator
                     + compilationContext.getClassDirectory().getAbsolutePath();

    this.compilationContext = compilationContext;
    this.encodingJavaFile = encodingJavaFile;
  }


  /**
   * @see org.apache.jasper.compiler.JavaCompiler#compile(java.util.List, java.io.OutputStream)
   */
  public void compile(List<File> sources) throws IOException {
    boolean result;
    javax.tools.JavaCompiler compiler;
    StandardJavaFileManager fileManager;
    Iterable<? extends JavaFileObject> compilationUnits;
    CompilationTask task;
    List<String> options;
    Writer writer;
    ByteArrayOutputStream out;

    LOGGER.log( Level.FINE,
                "Compiling sources: {0}",
                sources );

    compiler = ToolProvider.getSystemJavaCompiler();
    fileManager = compiler.getStandardFileManager(null, null, null);

    compilationUnits = fileManager.getJavaFileObjectsFromFiles(sources);

    if ( !compilationContext.getClassDirectory().exists() ) {
      compilationContext.getClassDirectory().mkdirs();
    }

    // NOTE: Once absolute source file names are provided, no need of providing source directory

    options = new ArrayList<>();
    options.add( "-g" );
    options.add( "-encoding" );
    options.add( encodingJavaFile );
    options.add( "-classpath" );
    options.add( classpath );
    options.add( "-d" );
    options.add( compilationContext.getClassDirectory().getAbsolutePath() );

    out = new ByteArrayOutputStream( 256 );

    writer = new OutputStreamWriter(out);
    try {
      task = compiler.getTask( writer,
                               fileManager,
                               null,
                               options,
                               null,
                               compilationUnits );
      result = task.call();
    } finally {
      writer.close();
    }
    if ( !result ) {// compilation failed
      throw new IOException( "Unable to compile: "+ out.toString() );
    }
  }
}