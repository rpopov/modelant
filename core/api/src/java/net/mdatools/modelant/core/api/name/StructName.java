/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.api.name;

import javax.jmi.reflect.RefStruct;

import net.mdatools.modelant.core.api.model.ConstructOperation;

/**
 * Refer a model Struct,
 * @author Rusi Popov (popovr@mdatools.net)
 */
public interface StructName extends Name<Name<?>> {

  /**
   * @return a non-null
   */
  ConstructOperation<RefStruct> constructCopyOperation();
}