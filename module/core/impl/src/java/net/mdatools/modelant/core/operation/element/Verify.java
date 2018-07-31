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
package net.mdatools.modelant.core.operation.element;

import java.util.Collection;
import java.util.Iterator;

import javax.jmi.reflect.JmiException;
import javax.jmi.reflect.RefBaseObject;

import net.mdatools.modelant.core.api.Function;

/**
 * Verify the model element it is called for against the validation rules form the metamodel.
 * Any violations are reported in the task log.
 * @author Rusi Popov
 */
public class Verify implements Function<RefBaseObject, String> {

  /**
   * This rule that validates the constraints defined in the target extent. Any violations are
   * reported in the task log as errors.
   */
  public String execute(RefBaseObject target) {
    StringBuilder result;
    Collection<JmiException> verificationResult;
    Iterator<JmiException> exceptionIterator;
    JmiException exception;

    result = new StringBuilder();

    verificationResult = target.refVerifyConstraints( true ); // deep validation
    exceptionIterator = verificationResult.iterator();
    while ( exceptionIterator.hasNext() ) {
      exception = exceptionIterator.next();

      result.append(new PrintModelElement().execute( exception ));
    }
    return result.toString();
  }
}