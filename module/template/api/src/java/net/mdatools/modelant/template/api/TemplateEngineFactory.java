/*
 * Copyright (c) 2017 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Created on 19.08.2017
 */
package net.mdatools.modelant.template.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * A well known factory of initialized Template Engines, as of [A4], according to the
 * provided implementation, found in classpath.
 * @author Rusi Popov
 */
public final class TemplateEngineFactory {

  /**
   * Factory and cache of Template Engines, as of [A4] architecture
   * map: path -> TemplateEngine
   * TODO: REMOVE, avoid using the engines as singletons
   */
  private static final Map<String, TemplateEngine> engines = new HashMap<>();

  private TemplateEngineFactory() {
  }

  /**
   * This method creates a context for a set of templates (quite the same as the web context for ModelAnt Templates) in
   * the same context.
   * @param compilationContext not null
   * @return non-null template engine
   * @throws RuntimeException on instantiation problems
   */
  public synchronized static TemplateEngine construct(TemplateCompilationContext compilationContext) throws RuntimeException {
    TemplateEngine result;
    ConstructTemplateEngine construct;
    String key;

    key = constructKey( compilationContext );
    result = engines.get( key );
    if ( result == null ) {
      construct = ServiceLoader.load(ConstructTemplateEngine.class).iterator().next(); // not null

      try {
        result = construct.constructTemplateEngine(compilationContext);
      } catch (IOException ex) {
        throw new IllegalArgumentException("Constructing template engine for context \""
                                           +compilationContext.getUniqueName()
                                           +"\" failed with:",
                                           ex);
      }

      engines.put( key, result );
    }
    return result;
  }

  private static String constructKey(TemplateCompilationContext compilationContext) {
    return compilationContext.getTemplateDirectory().getAbsolutePath();
  }
}