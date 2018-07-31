/*
 * Copyright (c) 2017 Rusi Popov, MDA Tools.net All rights reserved. 
 * 
 * This program and the accompanying materials are made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Created on 22.08.2017
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
 * TODO: decide if overwriting / setting of parameters is allowed
 * 
 * @author Rusi Popov
 */
public class TemplateContextImpl implements TemplateContext {

  private final PrintWriter out;
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
}
