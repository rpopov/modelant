/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.api.model;

import java.util.Map;

import javax.jmi.reflect.RefObject;
import javax.jmi.reflect.RefPackage;

import net.mdatools.modelant.core.api.Operation;

/**
 * A mechanism to construct operations over model elements in the context of model transformation.
 * @author Rusi Popov (popovr@mdatools.net)
 */
public interface ConstructOperation<T> {
  /**
   * @param targetExtent not null target model extent
   * @param objectsMap not null source-to-target model elements map
   * @param valueMapping not null existing mapping of source to target objects
   * @return a non-null operation to process instances of T and produce corresponding elements of the
   *         target model
   */
  Operation<T> construct(RefPackage targetExtent, Map<RefObject, RefObject> objectsMap, NameMapping valueMapping);

}