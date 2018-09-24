/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.condition;

import javax.jmi.reflect.RefClass;
import javax.jmi.reflect.RefObject;
import javax.jmi.reflect.RefPackage;

import net.mdatools.modelant.core.api.Condition;
import net.mdatools.modelant.core.util.Navigator;


/**
 * Check if a model element is of a meta-model class.
 * @author Rusi Popov (popovr@mdatools.net)
 */
public class IsInstanceOf implements Condition<RefObject> {

  private final String metaClassName;

  /**
   * The name of the metaclass that holds the model elements for processing.
   * <p>
   * Format: <b>{&lt;package&gt;::}&lt;meta class&gt; </b>
   * <p>
   * In UML 1.3 the metaclass name might be:
   * <ul>
   * <li>Foundation::Core::Class - contains all model classes
   * <li>Foundation::Core::Attribute - holds all the attributes of model classes
   * <li>Behavioral_Elements::State_Machines::State holds all states in state machine models
   * </ul>
   * @param metaclass is the metaclass to set.
   */
  public IsInstanceOf(String metaclass) {
    if ( metaclass == null || metaclass.isEmpty() ) {
      throw new IllegalArgumentException("Expected a non-empty metaclass provided");
    }
    this.metaClassName = metaclass;
  }

  /**
   * @return the result of eval() method of the class provided
   */
  public final boolean eval(RefObject value) {
    boolean result;
    RefPackage extent;
    RefClass metaclass;

    extent = value.refOutermostPackage();

    metaclass = Navigator.getMetaClass( extent, metaClassName );
    result = value.refIsInstanceOf( metaclass.refMetaObject(), true );

    return result;
  }
}