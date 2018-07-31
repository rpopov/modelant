/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.operation.format;

/**
 * Wrap and format the text to be included in javadoc (multi-line) comments for class features
 * (methods and fields).
 *
 * @author Rusi Popov
 */
public class FormatWrapTextJavaDoc extends FormatWrapText {

  /**
   *
   */
  public FormatWrapTextJavaDoc() {
    super("   * ", "");
  }
}
