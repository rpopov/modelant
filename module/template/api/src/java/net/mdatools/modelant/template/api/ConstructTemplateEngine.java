/*
 * Copyright (c) 2017 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Created on 20.08.2017
 */
package net.mdatools.modelant.template.api;

import java.io.IOException;

/**
 * A constructor service for template engine instances. The TemplateEngineFactory delegates to it
 * the instantiation of the engine with specific parameters.
 *
 * @author Rusi Popov
 */
public interface ConstructTemplateEngine {

  /**
   * The only constructor of template engines. Its implementation should be provided externally.
   * @param context non null
   * @return non-null template engine initialized with the provided parameters
   * @throws IOException
   */
  TemplateEngine constructTemplateEngine(TemplateCompilationContext context) throws IOException;

}
