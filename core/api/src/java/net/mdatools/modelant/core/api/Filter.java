/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.api;

import java.util.Collection;

/**
 * Filter the collection result of a selector and produce a sub-collection of it
 * @param <T> the type of the argument and result collections' contents
 * @author Rusi Popov (popovr@mdatools.net)
 */
public interface Filter<T> extends Selector<Collection<T>, T> {
}
