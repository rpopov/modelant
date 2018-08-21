/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.api.name;

import javax.jmi.reflect.JmiException;
import javax.jmi.reflect.RefEnum;
import javax.jmi.reflect.RefPackage;

/**
 * Enumerated value name
 */
public interface EnumValueName extends Name<Name<?>> {

  /**
   * @param targetExtent not null
   * @return the enum value this represents in the target model
   * @throws JmiException when no enum value with this name found
   */
  RefEnum lookupValue(RefPackage targetExtent) throws JmiException;
}