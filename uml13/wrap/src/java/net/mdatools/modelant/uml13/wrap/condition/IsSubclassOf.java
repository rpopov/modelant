/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.uml13.wrap.condition;


import net.mdatools.modelant.core.wrap.Factories;
import net.mdatools.modelant.util.task.StackedTask;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.condition.Condition;
import org.omg.uml13.foundation.core.Classifier;
import org.omg.uml13.wrap.foundation.core.WrapClassifier;

/**
 * This condition checks if a model class, specified in the 'property' attribute is a subclass of another one.
 * @author Rusi Popov (popovr@mdatools.net)
 */
public class IsSubclassOf extends StackedTask implements Condition {

  /**
   * The qualified name of the model class to
   */
  public String base;

  /**
   */
  public boolean eval() throws BuildException {
    boolean result;
    Object value;

    // retrieve the repository adapter for the current model element
    value = getMandatoryProperty( getProperty() );
    if ( !(value instanceof Classifier) ) {
      throw new BuildException("Expected an instance of Classifier held name in '"+getProperty()+"' attribute, instead of: "+value, getLocation());
    }

    // retrieve the base class/interface
    if ( base == null || base.isEmpty() ) {
      throw new BuildException("Expected a non-null qualified class name in 'base' attribute", getLocation());
    }

    try {
      result = ((WrapClassifier) Factories.wrap( value )).isSubclassOf( base );
    } catch (Exception ex) { // not uml13, or not classifier, or not wrapped,...
      throw new BuildException("Expected a UML 1.3 Classifier instance:", ex);
    }
    return result;
  }

  /**
   * @param base holds the qualified name of a model class to check subclasses of
   */
  public final void setBase(String base) {
    this.base = base;
  }

  /**
   * @param property the name of the property holding the model class to check if it is a subclass of the base class. Default: this
   * @see net.mdatools.modelant.util.task.StackedTask#setProperty(java.lang.String)
   */
  public void setProperty(String property) {
    super.setProperty( property );
  }
}