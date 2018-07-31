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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;

/**
 * @author Anil K. Vijendran
 * Contributors:
 *    Rusi Popov (popovr@mdatools.net) - deep refactoring and documenting for ModelANT purposes
 */
public class TemplateWriter {
  private static int TAB_WIDTH = 2;

  private static String SPACES = "                              ";

  // Current indent level:
  private int indent = 0;
  private String indentStr ="";

  // The sink writer:
  private PrintWriter writer;

  /**
   * Indicates if anything was printed on the current line being printed
   * (false) or the line is still blank (true)
   */
  private boolean isLineEmpty = true;


  public TemplateWriter(PrintWriter writer) {
    this.writer = writer;
  }


  public void close() throws IOException {
    writer.close();
  }


  public void pushIndent() {
    indent += TAB_WIDTH;

    if ( indent > SPACES.length() ) {
      indent = SPACES.length();
    }
    indentStr = SPACES.substring( 0, indent );
  }


  public void popIndent() {
    indent -= TAB_WIDTH;

    if ( indent < 0 ) {
      indent = 0;
    }
    indentStr = SPACES.substring( 0, indent );
  }

  public void println(String line) {
    indent();
    writer.println( line );
    isLineEmpty = true;
  }


  public void println() {
    writer.println();
    isLineEmpty = true;
  }


  private void indent() {
    if ( isLineEmpty ) {
      writer.print( indentStr );
      isLineEmpty = false;
    }
  }


  public void print(String s) {
    if ( s != null && !s.isEmpty() ) {
      indent();
    }
    writer.print( s );
  }

  public void print(char s) {
    indent();
    writer.print( s );
  }


  public void printMultiLn(String multiline) {
    // Try to be smart (i.e. indent properly) at generating the code:
    BufferedReader reader = new BufferedReader( new StringReader( multiline ) );
    try {
      for (String line = null; (line = reader.readLine()) != null;)
        println( line );
    } catch (IOException ex) {
      // Unlikely to happen, since we're acting on strings
    }
  }
}
