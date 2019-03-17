/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.template.api;

import java.io.PrintWriter;

/**
 * The common features a template needs to retrieve from it environment:<ul>
 * <li> type safe parameters
 * <li> output stream
 * </ul>
 * @author Rusi Popov (popovr@mdatools.net)
 */
public interface TemplateContext {
  /**
   * @return non-null writer where to print the textual output of the template
   */
  PrintWriter getWriter();

  /**
   * @param name not null parameter name
   * @return the parameter value bound to the name (it could be null)
   */
  <T> T get(String name);
}
