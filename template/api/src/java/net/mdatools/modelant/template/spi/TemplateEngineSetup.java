/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.template.spi;

import java.io.IOException;

import net.mdatools.modelant.template.api.TemplateCompilationContext;
import net.mdatools.modelant.template.api.TemplateEngine;

/**
 * A constructor service for template engine instances. The TemplateEngineFactory delegates to it
 * the instantiation of the engine with specific parameters.
 *
 * @author Rusi Popov (popovr@mdatools.net)
 */
public interface TemplateEngineSetup {

  /**
   * The only constructor of template engines. Its implementation should be provided externally.
   * @param context non null
   * @return non-null template engine initialized with the provided parameters
   * @throws IOException
   */
  TemplateEngine constructTemplateEngine(TemplateCompilationContext context) throws IOException;

}
