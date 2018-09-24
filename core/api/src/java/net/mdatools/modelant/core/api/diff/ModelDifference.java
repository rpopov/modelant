/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.api.diff;

import java.util.Collection;
import java.util.Map;

import javax.jmi.reflect.RefObject;

/**
 * A change in the model caused either by adding or deleting a model element to a model, which has no correspondent
 * in the other model compared.
 * The role of this difference indicates it is added/deleted, whereas the difference by itself does not.
 * @author Rusi Popov (popovr@mdatools.net)
 */
public interface ModelDifference {

  /**
   * @return the non-null element of this change - the element added/deleted to the corresponding model
   */
  RefObject getElement();

  /**
   * @return non-null map of association names to associated model differences in the same role of added/deleted
   *         to the {@link #getElement()} as the role of this to the model.
   */
  Map<String, Collection<ModelDifference>> getAssociations();
}
