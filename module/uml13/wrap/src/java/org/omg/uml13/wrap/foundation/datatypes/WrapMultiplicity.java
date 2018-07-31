/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml13.wrap.foundation.datatypes;

import java.util.Iterator;

import javax.jmi.reflect.RefPackage;

import net.mdatools.modelant.core.wrap.Factory;

import org.omg.uml13.foundation.datatypes.MultiplicityRange;
import org.omg.uml13.wrap.base.foundation.datatypes.BaseWrapMultiplicity;

/**
 * This is a wrapper of org.omg.uml13.foundation.datatypes.Multiplicity that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public class WrapMultiplicity extends BaseWrapMultiplicity {

  public WrapMultiplicity(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  public WrapMultiplicity(RefPackage extent) {
    super( extent );
  }

  public WrapMultiplicity(RefPackage extent, int upperBound) {
    super( extent );

    getRange().add(new WrapMultiplicityRange( extent, upperBound ).getWrapped() );
  }

  /**
   * isSingular checks if the multiplicity is like 0..1 or 1..1, so it is a singular one
   * NOTE: Rose represents "0..*" as lower=0, upper=-1
   * @return true if and only if the multiplicity is 0..1 or 1..1
   */
  public boolean isSingular() {
    boolean result;
    MultiplicityRange range;
    Iterator ragnesIterator;

    ragnesIterator = getRange().iterator();
    if ( ragnesIterator.hasNext() ) {
      range = (MultiplicityRange)ragnesIterator.next();

      result = range.getLower() >= 0
               && range.getLower() <= range.getUpper()
               && range.getUpper() <= 1
               && !ragnesIterator.hasNext();
    } else {
      result = false;
    }
    return result;
  }


  /**
   * isMandatory checks if the multiplicity is like 1..1 or 1..N, so it is a mandatory
   * to have at least one associated object
   * NOTE: Rose represents "0..*" as lower=0, upper=-1
   * @return true if and only if the multiplicity is 1..1 or 1..N
   */
  public boolean isMandatory() {
    boolean result;
    MultiplicityRange range;
    Iterator ragnesIterator;

    ragnesIterator = getRange().iterator();
    if ( ragnesIterator.hasNext() ) {
      range = (MultiplicityRange)ragnesIterator.next();

      result = range.getLower() >= 1
               && range.getLower() <= range.getUpper()
               && !ragnesIterator.hasNext();
    } else {
      result = false;
    }
    return result;
  }

  /**
   * Remove all ranges associated with this multiplicity
   */
  public void remove() {
    MultiplicityRange range;

    Iterator rangeIterator = getRange().iterator();
    while ( rangeIterator.hasNext() ) {
      range = (MultiplicityRange) rangeIterator.next();
      rangeIterator.remove();
      range.refDelete();
    }
    refDelete();
  }

}