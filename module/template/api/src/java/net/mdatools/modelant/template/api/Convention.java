/*
 * Copyright (c) 2001,2012 Rusi Popov, MDA Tools.net
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Rusi Popov (popovr@mdatools.net) - initial implementation
 */
package net.mdatools.modelant.template.api;

/**
 * This interface defines the common properties and convenient settings
 * @author Rusi Popov
 */
public interface Convention {

  /**
   * The encoding of the generated Java file form the template
   */
  String ENCODING_JAVA_FILE = "UTF-8";

  /**
   * Default encoding of the template files if one is not stated in the context
   */
  String ENCODING_DEFAULT = "ISO-8859-1";

  /**
   * The suffix of the template files
   */
  String TEMPLATE_FILE_SUFFIX = ".jsp";
}
