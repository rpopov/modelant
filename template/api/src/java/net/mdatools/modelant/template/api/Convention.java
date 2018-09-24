/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.template.api;

/**
 * This interface defines the common properties and convenient settings
 * @author Rusi Popov (popovr@mdatools.net)
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
