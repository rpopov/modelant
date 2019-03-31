/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.apache.jasper.compiler;

import static net.mdatools.modelant.template.api.Convention.ENCODING_JAVA_FILE;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.mdatools.modelant.template.api.Template;
import net.mdatools.modelant.template.api.TemplateCompilationContext;

/**
 * Compiler of Templates to java classes and loader of the produced classes.
 * All templates of the same context should be compiled and loaded through the same instance of this class.
 * Convention:<ul>
 * <li> the template source file names are either:<ul>
 *      <li>constructed from the qualified java class name of the objects the template is applicable for, by appending
 *          as the actual class name the name of the template itself
 *          or
 *      <li>constructed from a relative file name, considering the directories in it as package names and the name of the
 *          template file to reproduce the same template name when reproduced from target class and template name
 *      </ul>
 * </ul>
 */
public class TemplateCompiler {

  /**
   * This is a common logger for all utils
   */
  private static final Logger LOGGER = Logger.getLogger( TemplateCompiler.class.getName() );

  /**
   * not null compiler
   */
  private final JavaCompiler javac;

  private final Format formatTemplateClassName;

  private final ClassLoader classLoader;

  private final File translateToJavaDirectory;

  private final String templateEncoding;

  private final boolean shouldKeepGenerated;
  /**
   * @param compilationContext not null
   * @throws IOException when invalid classpath entry is provided
   */
  public TemplateCompiler(TemplateCompilationContext compilationContext) throws IOException {
    List<File> classpath;
    File classOutputDirectory;

    this.formatTemplateClassName = new Format( compilationContext.getUniqueName() );

    classOutputDirectory = compilationContext.getClassDirectory().getAbsoluteFile();
    if ( !classOutputDirectory.exists() ) {
      classOutputDirectory.mkdirs();
    }

    // construct the classpath, having the template source and class directories first,
    // allowing overriding the packaged in .jars contents
    classpath = new ArrayList<>();
    classpath.add( getTemplateApiJar());
    classpath.add( compilationContext.getTemplateDirectory().getAbsoluteFile());
    classpath.add( classOutputDirectory);
    classpath.addAll( compilationContext.getClassPathAsList());

    LOGGER.log(Level.FINE, "Actual compilation classpath {0}", classpath);

    this.javac = new SunJavaCompiler(ENCODING_JAVA_FILE,
                                     classOutputDirectory,
                                     concatenateClassPath(classpath));

    this.classLoader = new URLClassLoader( convertToUrls( classpath ),
                                           getClass().getClassLoader() );

    this.translateToJavaDirectory = compilationContext.getJavaDirectory().getAbsoluteFile();
    this.templateEncoding = compilationContext.getTemplateEncoding();
    this.shouldKeepGenerated = compilationContext.shouldKeepGenerated();
  }

  /**
   * @param classpath not null
   * @return non-null array of URLs representing the files/directories in classpath
   * @throws MalformedURLException
   */
  private static URL[] convertToUrls(List<File> classpath) throws MalformedURLException {
    URL[] result;
    int i;

    i = 0;
    result = new URL[classpath.size()];
    for (File file : classpath) {
      result[i++] = file.toURL();
    }
    return result;
  }

  /**
   * @see net.mdatools.modelant.template.api.TemplateCompilationContext#getClassPath()
   */
  private static String concatenateClassPath(List<File> classPath) {
    StringBuilder result;

    // concatenate all artifacts in the classpath
    result = new StringBuilder(512);

    for (File artifact: classPath) {
      if (result.length() > 0) {
        result.append( File.pathSeparatorChar );
      }
      if ( artifact.getPath() != null ) { // the dependency is resolved
        result.append( artifact.getAbsolutePath() );
      }
    }
    return result.toString();
  }


  /**
   * @return the jar this class is in, so that the template core classes are found
   * @throws MalformedURLException
   */
  public static File getTemplateApiJar() throws MalformedURLException {
    File result;
    URL url;
    String resourceName;
    String location;
    int locationEndIndex;
    Class containingClass;

    containingClass = Template.class;

    resourceName = containingClass.getName().replace('.','/')+".class";
    url = containingClass.getClassLoader().getResource( resourceName );

    if ( url.getProtocol().equals("jar") ) { // jar:file:/c:/...!file URL
      location = url.getPath();
      locationEndIndex = location.indexOf( "!" );

      if ( locationEndIndex < 0 ) {
        throw new MalformedURLException("Expected to find '!' in "+url);
      }
      location = url.getPath().substring(0, locationEndIndex); // location already contains the nested URL
      location = new URL(location).getPath();

    } else if ( url.getProtocol().equals("file") ) {
      location = url.getPath();
      locationEndIndex = location.indexOf( resourceName );

      if ( locationEndIndex >= 0 ) {
        // extract the .jar / directory from class' URL
        location = location.substring( 0, locationEndIndex );
      }
    } else {
      throw new MalformedURLException("Expected a jar: or file: URL, instead of  "+url);
    }
    result = new File( location );

    return result;
  }

  /**
   * Compile the templates from file names, relative to the templates directory.
   *
   * @param templateFiles as not null <b>relative template files</b>, to be looked up in the templates directory
   *
   * @throws IOException when reading/writing the files fails
   */
  public void compile(List<File> templateFiles) throws IOException {
    List<File> javaFiles;
    File javaFile;

    javaFiles = new ArrayList<>();

    // translate all templates to Java
    for (File templateFile : templateFiles) {
      javaFile = translateTemplateToJava( templateFile );

      javaFiles.add( javaFile );
    }

    javac.compile( javaFiles );

    if ( !shouldKeepGenerated ) { // clean the generated files
      for(File file : javaFiles ) {
        file.delete();
      }
    }
  }

  /**
   * Translates the template, provided as a <b>relative</b> template file name, to Java and returns the absolute file name
   * of the generated .java file. Consider the provided template path as package name, the file name - as class name,
   *        as of the convention
   * @param template non-null, relative file with the template.
   * @return the absolute file name of the generated .java file, relative to the java output directory
   */
  private File translateTemplateToJava(File template) throws IOException {
    File result;
    String packageName;
    String javaFileName;
    String className;
    String wrappedClassName;
    TemplateLexer templateReader;
    GenerateTemplate generateTemplate;
    TemplateParser parser;
    URL templateSourceUrl;
    List<Generator> generators;

    packageName = formatTemplateClassName.formatPackageName(template);
    className = formatTemplateClassName.formatSimpleClassName(template);
    javaFileName = formatTemplateClassName.formatJavaFileName(packageName,className);

    wrappedClassName = formatTemplateClassName.formatQualifiedClassName(template.getParentFile());

    LOGGER.log( Level.FINE, "Translate template: {0} to Java: {1}", new Object[] { template, javaFileName } );

    templateSourceUrl = classLoader.getResource( template.getPath() );
    if ( templateSourceUrl == null ) {
      throw new IOException("Missing template source file: "+template.getPath());
    }

    templateReader = new TemplateLexer(templateSourceUrl, templateEncoding);
    try {
      parser = new TemplateParser( templateReader );
      generators = parser.parse();
    } finally {
      templateReader.close();
    }

    generateTemplate = new GenerateTemplate( packageName, className, wrappedClassName );

    result = new File(translateToJavaDirectory, javaFileName);
    generateTemplate.generate( result, generators );

    return result;
  }

  /**
   * Load a template. If it is not compiled, compile it first.
   * @param targetClass not  null
   * @param templateName not null
   * @return a timestamped loaded template (to be cached) or null when no template exists.
   *         Explicitly check the compatibility of the template with the class to render
   */
  public <T> TemplateDescriptor loadTemplate(Class<T> targetClass, String templateName) {
    TemplateDescriptor result;
    Class<Template<T>> templateClass;
    String templateClassName;
    File templateSourceFile;
    Date templateCompiledDate;
    Date templateSourceDate;
    URL templateClassUrl;
    URL templateSourceUrl;

    templateSourceFile= formatTemplateClassName.formatTemplateSourceFile( targetClass, templateName );
    templateClassName = formatTemplateClassName.formatQualifiedTemplateClassName( targetClass, templateName );

    try {
      // make sure that the latest version of the template is compiled and loaded, the template may not exist

      templateClassUrl = classLoader.getResource( templateClassName.replace('.', '/')+".class" );
      if ( templateClassUrl != null ) { // compiled
        templateCompiledDate= getTime(templateClassUrl);

        templateSourceUrl = classLoader.getResource( templateSourceFile.getPath() );
        if ( templateSourceUrl != null ) { // there is source
          templateSourceDate= getTime( templateSourceUrl );

          if ( templateCompiledDate.before( templateSourceDate ) ) { // the compiled template is obsolete

            compile(Arrays.asList( templateSourceFile )); // compiled successfully
          }
        }
      } else { // not compiled yet
        compile(Arrays.asList( templateSourceFile )); // compiled successfully
      }

      templateClass = (Class<Template<T>>) classLoader.loadClass( templateClassName );

      result = new TemplateDescriptor(templateClass.newInstance(),
                                      templateSourceFile);
    } catch (Exception ex) {
      LOGGER.log(Level.SEVERE, "Loading template "+templateClassName+" caused:", ex );
      result = null;
    }
    return result;
  }

  /**
   * Identify when the file storing the contents a URL refers was modified last
   * @param templateResourceUrl not null, local URL (only file: and jar: protocols used),
   *           the contents with this URL is stored locally (i.e. there is a file for it)
   * @return the creation/last modified time the file file storing the contents with that URL
   * @throws MalformedURLException
   */
  private static Date getTime(URL templateResourceUrl) throws MalformedURLException {
    Date result;
    String path;

    if ( templateResourceUrl.getProtocol().equals("jar") ) { // jar:file:/c:/... URL
      // compare the version of the JAR
      path = new URL(templateResourceUrl.getPath()).getPath();  // the URL is correct

      path = path.substring( 1, path.indexOf( '!' ) );

      result = new Date(new File( path ).lastModified());

    } else { // compare dates of the class and the ModelAnt Template standalone file

      result = new Date(new File( templateResourceUrl.getPath()).lastModified());
    }
    return result;
  }
}