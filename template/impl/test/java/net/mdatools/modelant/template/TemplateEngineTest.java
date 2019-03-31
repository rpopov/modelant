/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.template;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.jasper.compiler.TemplateCompiler;

import junit.framework.TestCase;
import net.mdatools.modelant.template.api.TemplateCompilationContext;
import net.mdatools.modelant.template.api.TemplateEngine;

/**
 * @author Rusi Popov (popovr@mdatools.net)
 */
public class TemplateEngineTest extends TestCase {

  private static final String TEMPLATE_NAME = "renderProperty";
  private static final String TEMPLATE = "java/lang/String/"+TEMPLATE_NAME+".jsp";
  private static final String TEMPLATE_JAVA = "test/java/lang/String/"+TEMPLATE_NAME+".java";
  private static final String TEMPLATE_CLASS = "test/java/lang/String/"+TEMPLATE_NAME+".class";

  private static final String TEXT_TO_RENDER = "render!me";

  private TemplateCompilationContext context;

  protected void setUp() throws Exception {
    context = new TemplateCompilationContext() {

      /**
       * Where to compile the template
       * @see net.mdatools.modelant.template.api.TemplateCompilationContext#getClassDirectory()
       */
      public File getClassDirectory() {
        return new File("target/test-classes"); // relative to local run (project) directory
      }

      /**
       * Where to generate the Java files
       * @see net.mdatools.modelant.template.api.TemplateCompilationContext#getJavaDirectory()
       */
      public File getJavaDirectory() {
        return new File("target/java"); // relative to local run directory
      }

      /**
       * Where the template source is copied from test/resources/template
       * @see net.mdatools.modelant.template.api.TemplateCompilationContext#getTemplateDirectory()
       */
      public File getTemplateDirectory() {
        return new File("target/test-classes/template");
      }

      /**
       * @see net.mdatools.modelant.template.api.TemplateCompilationContext#getClassPathAsList()
       */
      public List<File> getClassPathAsList() throws MalformedURLException {
        List<File> result;

        result = new ArrayList<>();
        result.add( TemplateCompiler.getTemplateApiJar( ) );

        return result;
      }

      /**
       * use default
       */
      public String getTemplateEncoding() {
        return null;
      }

      public String getUniqueName() {
        return "test";
      }

      public boolean shouldCompileForDebug() {
        return true;
      }

      public boolean shouldKeepGenerated() {
        return true;
      }
    };
    new File(context.getClassDirectory(), TEMPLATE_CLASS).delete();
  }

  public void testConstruct() throws IOException {
    TemplateEngine engine;

    engine = new ConstructTemplateEngineImpl().constructTemplateEngine( context );

    assertNotNull("Instantiating template engine", engine );
  }

  public void testCompile() throws IOException {
    TemplateEngine engine;

    engine = new ConstructTemplateEngineImpl().constructTemplateEngine( context );

    assertNotNull("Instantiating template engine", engine );

    assertTrue( "Expected Template file exists (copied by maven) "
               +new File(context.getTemplateDirectory(), TEMPLATE).getAbsolutePath(),
                new File(context.getTemplateDirectory(), TEMPLATE).getAbsoluteFile().exists());

    engine.compile( Arrays.asList( new File(TEMPLATE) ) );

    assertTrue("Expected java source produced in "+new File(context.getJavaDirectory(), TEMPLATE_JAVA).getAbsolutePath(),
               new File(context.getJavaDirectory(), TEMPLATE_JAVA).getAbsoluteFile().exists());

    assertTrue("Expected class source produced in "+new File(context.getClassDirectory(), TEMPLATE_CLASS).getAbsolutePath(),
               new File(context.getClassDirectory(), TEMPLATE_CLASS).getAbsoluteFile().exists());
  }

  public void testRun() throws IOException {
    TemplateEngine engine;
    String result;

    engine = new ConstructTemplateEngineImpl().constructTemplateEngine( context );

    assertNotNull("Instantiating template engine", engine );

    assertTrue("Template "+TEMPLATE+" should exist in Template dir (copied by maven) "+new File(context.getTemplateDirectory(), TEMPLATE).getAbsolutePath(),
               new File(context.getTemplateDirectory(), TEMPLATE).getAbsoluteFile().exists());

    result = engine.render( TEXT_TO_RENDER, TEMPLATE_NAME, (Map)null );

    assertEquals("Expected template produced", "Wrapped:"+TEXT_TO_RENDER+"\r\nSome standard text\r\n", result);
  }
}
