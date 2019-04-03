/*
 * Copyright (c) Rusi Popov, MDA Tools.net 2019
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Created on Apr 3, 2019
 */
package net.mdatools.modelant.mof14.maven.generator.select;

import javax.jmi.model.MofPackage;
import javax.jmi.model.Tag;

import net.mdatools.modelant.core.api.Condition;
import net.mdatools.modelant.mof14.maven.generator.name.ConstructName;

/**
 * Choose only those packages that have no ignoreLifecycle tag set to "true"
 * @author Rusi Popov
 */
public class IsLifecyclePackage implements Condition<MofPackage> {

  /**
   * Tag name that indicates if a Package class should not be generated, when set to "true"
   * See JMI 1.0 specification, section 4.6.4
   */
  private static final String TAG_IGNORE_LIFECYCLE = "javax.jmi.ignoreLifecycle";

  public boolean eval(MofPackage argument) throws RuntimeException, IllegalArgumentException {
    boolean result;
    Tag tag;

    tag = ConstructName.getTag(argument, TAG_IGNORE_LIFECYCLE );
    result = tag == null || !Boolean.parseBoolean( (String) tag.getValues().get(0) );

    return result;
  }

}
