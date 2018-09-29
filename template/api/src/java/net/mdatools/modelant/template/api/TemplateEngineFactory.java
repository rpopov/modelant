/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.template.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

import net.mdatools.modelant.template.spi.TemplateEngineSetup;

/**
 * A well known factory of initialized Template Engines, according to the
 * provided implementation, found in classpath. See [A4]
 * @author Rusi Popov (popovr@mdatools.net)
 */
public final class TemplateEngineFactory {

  /**
   * Factory and cache of Template Engines, as of [A4] architecture
   * map: path -> TemplateEngine
   */
  private static final Map<String, TemplateEngine> engines = new HashMap<>();

  private TemplateEngineFactory() {
  }

  /**
   * Construct a singleton template engine for the unique compilation contest. The context's identifier is its unique name.
   * @param compilationContext not null
   * @return non-null template engine
   * @throws RuntimeException on instantiation problems
   */
  public synchronized static TemplateEngine construct(TemplateCompilationContext compilationContext) throws RuntimeException {
    TemplateEngine result;
    TemplateEngineSetup construct;
    String key;

    key = compilationContext.getUniqueName();
    result = engines.get( key );
    if ( result == null ) {
      construct = ServiceLoader.load(TemplateEngineSetup.class).iterator().next(); // not null

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

  /**
   * @param uniqueName not null unique name of a template (set) Engine
   * @return non-null engine already registered with that unique name
   * @throws IllegalArgumentException when no engine found / the name is not bound
   * @see #construct(TemplateCompilationContext)
   */
  public TemplateEngine getEngine(String uniqueName) throws IllegalArgumentException {
    TemplateEngine result;

    result = engines.get( uniqueName );
    if ( result == null ) {
      throw new IllegalArgumentException("Template engine for context \""
                                         +uniqueName
                                         +"\" is still not constructed");
    }
		return result;
  }
}