/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.api;

import net.mdatools.modelant.core.api.operation.Identity;

/**
 * The unary operation is a single argument function (mapping) from one set to itself
 * @param <T> the type of the argument and result
 * @author Rusi Popov
 */
public interface Operation<T> extends Function<T, T> {
  Operation<?> DEFAULT = new Identity<>();
}
