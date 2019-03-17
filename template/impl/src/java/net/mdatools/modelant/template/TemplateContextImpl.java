/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.template;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import net.mdatools.modelant.template.api.TemplateContext;

/**
 * The context where a template is run in. It provides the global parameters
 * of the templates.
 *
 * @author Rusi Popov (popovr@mdatools.net)
 */
public class TemplateContextImpl implements TemplateContext {

  /**
   * not null writer of the contents to generate
   */
  private final PrintWriter out;

  /**
   * not null binding of parameter names to values
   */
  private final Map<String, Object> bindings;

  /**
   * @param out not null writer to generate the output to
   * @param bindings may be null name-value bindings as global parameters
   */
  public TemplateContextImpl(PrintWriter out, Map<String, Object> bindings) {

    assert out != null : "Expected non-null output writer provided";

    this.out = out;

    if ( bindings != null ) {
      this.bindings = bindings;
    } else {
      this.bindings = new HashMap<>();
    }
  }

  /**
   * @see net.mdatools.modelant.template.api.TemplateContext#getWriter()
   */
  public PrintWriter getWriter() {
    return out;
  }

  /**
   * @see net.mdatools.modelant.template.api.TemplateContext#get(java.lang.String)
   */
  public <T> T get(String name) {
    return (T) bindings.get( name );
  }
}
