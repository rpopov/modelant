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

import javax.jmi.reflect.RefStruct;

import net.mdatools.modelant.core.api.model.ConstructOperation;

/**
 * Refer a model Struct,
 * @author Rusi Popov
 */
public interface StructName extends Name<Name<?>> {

  /**
   * @return a non-null
   */
  ConstructOperation<RefStruct> constructCopyOperation();
}