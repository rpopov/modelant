/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.template.api;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * The settings needed for the compilation of a set of Templates.
 * The unique name identifies the template sets and makes their templates unique.
 * The implementation should be provided by the client of of the Template Engine.
 * @author Rusi Popov (popovr@mdatools.net)
 */
public interface TemplateCompilationContext {

  /**
   * @return non empty unique name to differentiate the set of templates to compile in this context
   *         from any other set to compile in other contexts. It also should avoid producing
   *         standard package names that are forbidden for custom class loaders
   */
  public String getUniqueName();


  /**
   * @return the non-null directory where to hold the templates
   */
  public File getTemplateDirectory();


  /**
   * @return the non-null source directory where to hold the translated to JAVA templates
   */
  public File getJavaDirectory();


  /**
   * @return non-null local directory where to generate the template compilation classes
   */
  public File getClassDirectory();


  /**
   * @return non-null template compilation classpath string with platform-specific separators,
   *         including the template directory and the class directory
   * @throws MalformedURLException when a classpath entry in invalid format is found
   * @see #getTemplateDirectory()
   * @see #getClassDirectory()
   */
  public List<File> getClassPathAsList() throws MalformedURLException;

  /**
   * @return true if the generated Java files from the templates should not be deleted
   *         (for tracing purposes)
   */
  public boolean shouldKeepGenerated();

  /**
   * @return true if the compiler should include debug information
   */
  public boolean shouldCompileForDebug();


  /**
   * @return maybe null encoding of the template files, default: ISO-8859-1
   */
  public String getTemplateEncoding();


  /**
   * @return the jar this class is in, so that the template core classes are found
   * @throws MalformedURLException
   */
  static File getTemplateApiJar() throws MalformedURLException {
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
}