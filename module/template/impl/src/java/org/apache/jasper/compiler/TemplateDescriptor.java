/*
 * Copyright (c) 2017 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Created on 27.08.2017
 */
package org.apache.jasper.compiler;

import java.io.File;

import net.mdatools.modelant.template.api.Template;

/**
 * Annotates a template (instance) with the class, name, filename and timestamp of its compilation.
 * Holds metadata on a template instance and "meta-logic" - decisions on that instance.
 * @author Rusi Popov
 */
public class TemplateDescriptor {
  /**
   * Invariant: If set to null, no time stamp check will be done, assuming there is no source, only the
   *            compiled Template will be used.
   */
  private final File templateSourceFile;

  /**
   * Invariant: never null
   */
  private final Template<?> template;

  /**
   * when the template was loaded
   */
  private final long timeStamp;

  /**
   * @param template not null template
   * @param templateSource null, when there is no (modifiable) source file of the template
   */
  public TemplateDescriptor(Template<?> template, File templateSource) {
    this.template = template;
    this.timeStamp = System.currentTimeMillis();
    this.templateSourceFile = templateSource;
  }

  /**
   * @return true if the template's source file was updated after its load
   */
  public boolean isStale() {
    boolean result;

    result = templateSourceFile != null
             && timeStamp < templateSourceFile.lastModified();
    return result;
  }

  public Template<?> getTemplate() {
    return template;
  }

  /**
   * @return the actual template source file, if isStale() (i.e. there is a template source file)
   * @see #isStale()
   */
  public File getTemplateSourceFile() {
    return templateSourceFile;
  }
}