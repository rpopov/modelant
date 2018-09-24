/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.api.diff;

import java.util.List;

import javax.jmi.reflect.RefObject;

/**
 * The differences found in a single association on X and Y matching objects
 * @author Rusi Popov (popovr@mdatools.net)
 */
public interface AssociationDifference {

  /**
   * @return not null association name
   */
  String getAssociationName();

  /**
   * @return non-null instances associated in the association, that are bound to X, but they have no correspondent
   *         bound in the same association to Y
   */
  List<RefObject> getXMinusY();

  /**
   * @return non-null instances associated in the association, that are bound to Y, but they have no correspondent
   *         bound in the same association to X
   */
  List<RefObject> getYMinusX();
}