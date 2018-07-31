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

import static net.mdatools.modelant.template.api.Convention.ENCODING_JAVA_FILE;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import net.mdatools.modelant.template.api.Template;
import net.mdatools.modelant.template.api.TemplateContext;
import net.mdatools.modelant.template.api.TemplateEngine;

/**
 * JSP code generator "backend".
 *
 * @author Anil K. Vijendran
 * Contributors:
 *    Rusi Popov (popovr@mdatools.net) - deep refactoring for ModelANT purposes, specifically the logging and
 *    cut off the tag libraries and extensive features, just to keep the templates simple.
 *    Refactored the original design with phases and generators, though this must be revised totally to apply the
 *    standard parsing and translation practices.
 *    Honestly, I just changed as less as possible in this original
 */
class GenerateTemplate {

  /**
   * These classes/packages are automatically imported by the generated code. FIXME: Need to trim
   * this to what is there in PR2 and verify with all our generators -akv.
   */
  private static final String[] STANDARD_IMPORTS = { "java.io.PrintWriter",
                                                     "java.io.IOException",
                                                     Template.class.getName(),
                                                     TemplateContext.class.getName(),
                                                     TemplateEngine.class.getName()};
  private final String packageName;

  private final String className;

  private final String wrappedClassName;


  /**
   * @param packageName non-null package name of the template class to generate
   * @param className non-null non-empty name of the class to translate the template to
   * @param wrappedClassName non-null non-empty qualified class name of the wrapped class
   */
  public GenerateTemplate(String packageName, String className, String wrappedClassName) {
    this.packageName = packageName;
    this.className = className;
    this.wrappedClassName = wrappedClassName;
  }

  public void generate(File result, List<Generator> generators) throws UnsupportedEncodingException,
                                                                       FileNotFoundException,
                                                                       IOException {
    TemplateWriter writer;

    // make sure the directories structure to the target file exists
    if ( result.getParentFile() != null ) {
      result.getParentFile().mkdirs();
    }

    writer = new TemplateWriter(
               new PrintWriter(
                 new OutputStreamWriter(
                   new FileOutputStream( result ),
                   ENCODING_JAVA_FILE ) ) );
    try {
      // First the package name:
      if ( !"".equals( packageName ) && packageName != null ) {
        writer.print( "package ");
        writer.print( packageName );
        writer.println(";");
      }
      writer.println();

      writer.print( "import ");
      writer.print( wrappedClassName );
      writer.println( ";" );
      writer.println();

      for(String importItem : STANDARD_IMPORTS ) {
        writer.print( "import ");
        writer.print( importItem );
        writer.println( ";" );
      }
      writer.println();

      for (Generator generator:generators) {
        generator.generateDeclaration( writer );
      }

      writer.print( "public class ");
      writer.print( className );
      writer.print( " implements " + Template.class.getSimpleName() +"<"+wrappedClassName+">"+ " {");
      writer.println();
      writer.pushIndent();
      writer.println( "public void render("
                      + "final "+wrappedClassName+" wrapped, "
                      + "final "+TemplateEngine.class.getSimpleName()+" engine, "
                      + "final "+TemplateContext.class.getSimpleName()+" context) throws IOException {" );
      writer.pushIndent();

      writer.println( "final PrintWriter out;" );
      writer.println();

      writer.println( "out = context.getWriter();" );
      writer.println();

      for (Generator gen : generators) {
        gen.generateMethod( writer );
      }

      writer.popIndent();
      writer.println( "}" );
      writer.popIndent();
      writer.println( "}" );
    } finally {
      writer.close();
    }
  }
}