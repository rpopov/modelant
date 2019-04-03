/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.api.match;

import javax.jmi.reflect.RefObject;
import javax.jmi.reflect.RefPackage;

import net.mdatools.modelant.core.api.Select;

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
  Select<RefPackage, RefObject> selectOld();

  /**
   * @return the non-null selection criteria (like in the &lt;select&gt;) over the "old" model,
   * to retrieve the "old" model elements to be treated as equal to the "old" ones.
   * Defines a set of "new"  model objects.
   */
  Select<RefPackage, RefObject> selectNew();

  /**
   * No objects are considered equal by external criteria
   */
  ConsideredEqual EMPTY = new ConsideredEqual() {
    public Select<RefPackage, RefObject> selectOld() {
      return Select.EMPTY;
    }
    public Select<RefPackage, RefObject> selectNew() {
      return Select.EMPTY;
    }
  };

  /**
   * Defines a set of "from" objects that corresponds to a set of "to" objects, considered as equal
   * because of other reasons, overriding any comparison rules.
   */
  public class EqualSelectors implements ConsideredEqual {
    private final Select<RefPackage,RefObject> oldSet;
    private final Select<RefPackage,RefObject> newSet;

    /**
     * @param oldSet not null
     * @param newSet not null
     */
    public EqualSelectors(Select<RefPackage,RefObject> oldSet, Select<RefPackage,RefObject> newSet) {
      assert oldSet != null : "Expected is 'old' set defined";
      assert newSet != null : "Expected is 'new' set defined";

      this.oldSet = oldSet;
      this.newSet = newSet;
    }

    /**
     * @see net.mdatools.modelant.core.api.match.ConsideredEqual#selectOld()
     */
    public Select<RefPackage,RefObject> selectOld() {
      return oldSet;
    }

    /**
     * @see net.mdatools.modelant.core.api.match.ConsideredEqual#selectNew()
     */
    public Select<RefPackage,RefObject> selectNew() {
      return newSet;
    }
  }
}