/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.uml14.maven.plugin;

/**
 * A pair of correspondent classes in the metamodels to compare, that should be considered equal
 * Used as &lt;metaclass&gt; element in the plugin's configuration.
 * @author Rusi Popov
 */
public class Equal extends net.mdatools.modelant.core.operation.model.match.Equal {
  /**
   * Used by MAVEN - it will take care to initialize the fields before instance's use.
   */
  public Equal() {
  }
}
