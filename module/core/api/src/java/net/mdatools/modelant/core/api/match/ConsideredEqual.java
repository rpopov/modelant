/*
 * Copyright (c) 2017 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Created on 04.10.2017
 */
package net.mdatools.modelant.core.api.match;

import javax.jmi.reflect.RefObject;
import javax.jmi.reflect.RefPackage;

import net.mdatools.modelant.core.api.Selector;

/**
 * Defines a set of "from" (old) objects that corresponds to a set of "to" (new) objects, considered as equal
 * because of other reasons, overriding any comparison rules.
 */
public interface ConsideredEqual {

  /**
   * @return the non-null selection criteria (like in the &lt;select&gt;) over the "new" model,
   * to retrieve the "new" model elements to be treated as equal to the "old" ones.
   * Defines a set of "new"  model objects.
   */
  Selector<RefPackage, RefObject> selectOld();

  /**
   * @return the non-null selection criteria (like in the &lt;select&gt;) over the "old" model,
   * to retrieve the "old" model elements to be treated as equal to the "old" ones.
   * Defines a set of "new"  model objects.
   */
  Selector<RefPackage, RefObject> selectNew();

  /**
   * No objects are considered equal by external criteria
   */
  ConsideredEqual EMPTY = new ConsideredEqual() {
    public Selector<RefPackage, RefObject> selectOld() {
      return Selector.EMPTY;
    }
    public Selector<RefPackage, RefObject> selectNew() {
      return Selector.EMPTY;
    }
  };

  /**
   * Defines a set of "from" objects that corresponds to a set of "to" objects, considered as equal
   * because of other reasons, overriding any comparison rules.
   */
  public class EqualSelectors implements ConsideredEqual {
    private final Selector<RefPackage,RefObject> oldSet;
    private final Selector<RefPackage,RefObject> newSet;

    /**
     * @param oldSet not null
     * @param newSet not null
     */
    public EqualSelectors(Selector<RefPackage,RefObject> oldSet, Selector<RefPackage,RefObject> newSet) {
      assert oldSet != null : "Expected is 'old' set defined";
      assert newSet != null : "Expected is 'new' set defined";

      this.oldSet = oldSet;
      this.newSet = newSet;
    }

    /**
     * @see net.mdatools.modelant.core.api.match.ConsideredEqual#selectOld()
     */
    public Selector<RefPackage,RefObject> selectOld() {
      return oldSet;
    }

    /**
     * @see net.mdatools.modelant.core.api.match.ConsideredEqual#selectNew()
     */
    public Selector<RefPackage,RefObject> selectNew() {
      return newSet;
    }
  }
}