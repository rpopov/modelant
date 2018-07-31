/*
 * Copyright (c) i:FAO AG 2018. All Rights Reserved.
 *
 * This SOURCE CODE FILE, which has been provided by i:FAO AG as part
 * of a product of i:FAO AG for use ONLY by licensed users of the product,
 * includes CONFIDENTIAL and PROPRIETARY information.
 *
 * Created on 16.04.2018 г.
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