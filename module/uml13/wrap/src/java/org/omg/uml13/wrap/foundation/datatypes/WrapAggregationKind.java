/*
 * Copyright (c) 2001,2012 Rusi Popov, MDA Tools.net
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Rusi Popov (popovr@mdatools.net) - initial implementation
 */
package org.omg.uml13.wrap.foundation.datatypes;

import net.mdatools.modelant.core.wrap.Factory;

import org.omg.uml13.foundation.datatypes.AggregationKindEnum;
import org.omg.uml13.wrap.base.foundation.datatypes.BaseWrapAggregationKind;

/**
 * This is a wrapper of org.omg.uml13.foundation.datatypes.AggregationKind that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public class WrapAggregationKind extends BaseWrapAggregationKind {

  public WrapAggregationKind(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  public WrapAggregationKind(String value, Factory factory) {
    super( value, factory );
  }

  /**
   * @return true if and only if the aggregation kind is aggregation
   */
  public final boolean isAggregate() {
    return AggregationKindEnum.AK_AGGREGATE.equals( getWrapped() );
  }

  /**
   * @return true if and only if the aggregation kind is composite
   */
  public final boolean isComposite() {
    return AggregationKindEnum.AK_COMPOSITE.equals( getWrapped() );
  }

  /**
   * @return true if and only if the aggregation kind is NONE / not specified
   */
  public final boolean isNone() {
    return AggregationKindEnum.AK_NONE.equals( getWrapped() );
  }

  /**
   * Formatting aggregations
   * @return a non-null aggregation name
   */
  public String format() {
    String result;

    if ( isNone() ) {
      result = "NONE";
    } else if ( isComposite() ) {
      result = "COMPOSITE";
    } else if ( isAggregate() ) {
      result = "AGGREGATE";
    } else {
      result = "";
    }
    return result;
  }
}