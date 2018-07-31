/*
 * Copyright (c) 2017 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package net.mdatools.modelant.template.api;

import java.io.File;

/**
 * The settings needed for the compilation of a set of Templates.
 * The unique name identifies the template sets and makes their templates unique.
 * The implementation should be provided by the client of of the Template Engine.
 * @author Rusi Popov
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
   * @see #getTemplateDirectory()
   * @see #getClassDirectory()
   */
  public String getClassPath();

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
}