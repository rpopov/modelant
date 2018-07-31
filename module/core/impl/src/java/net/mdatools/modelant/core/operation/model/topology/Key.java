/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.operation.model.topology;

import javax.jmi.model.GeneralizableElement;
import javax.jmi.reflect.RefObject;

import net.mdatools.modelant.core.util.key.Hash;

/**
 * This class represents a key<level, MOF class> in order to split by class and thus reduce
 * the set of objects to compare for matching.
 * @author Rusi Popov
 */
final class Key {
  private final int level;
  private final String className;
  private final int valuesHash;

  /**
   * caches the hash code so it is not calculated all the time
   */
  private final int hash;

  public Key(RefObject forObject, int level, int valuesHash) {

    assert forObject != null : "Expected a non-null model element to represent";

    this.level = level;
    this.className = ((GeneralizableElement) forObject.refMetaObject()).getName();
    this.valuesHash = valuesHash;
    this.hash = (Hash.hash(className)<<4) + (valuesHash<<2)+level;
  }

  public boolean isReady() {
    return level == 0;
  }

  public int hashCode() {
    return hash;
  }

  public boolean equals(Object obj) {
    boolean result;

    result = obj == this
             || obj != null
                && Key.class == obj.getClass()
                && level == ((Key) obj).level
                && valuesHash == ((Key) obj).valuesHash
                && className.equals( ((Key) obj).className );
    return result;
  }

  public String toString() {
    return getClass().getSimpleName()+"{level:"+level+", class:"+className+", valuesHash="+valuesHash+"}";
  }
}