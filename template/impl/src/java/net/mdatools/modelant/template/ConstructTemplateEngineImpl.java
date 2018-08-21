/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.template;

import java.io.IOException;

import net.mdatools.modelant.template.api.ConstructTemplateEngine;
import net.mdatools.modelant.template.api.TemplateCompilationContext;
import net.mdatools.modelant.template.api.TemplateEngine;

/**
 * The published service to instantiate and initialized a template engine, as of [A4]
 * @author Rusi Popov
 */
public class ConstructTemplateEngineImpl implements ConstructTemplateEngine {

  /**
   * @see net.mdatools.modelant.template.api.ConstructTemplateEngine#constructTemplateEngine(TemplateCompilationContext)
   */
  public TemplateEngine constructTemplateEngine(TemplateCompilationContext context) throws IOException {
    TemplateEngineImpl result;
    
    result = new TemplateEngineImpl( context );

    return result;
  }
}
