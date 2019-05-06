/*
 * Copyright (c) Rusi Popov, MDA Tools.net 2019
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Created on 6.05.2019 г.
 */
package net.mdatools.modelant.mof14.maven.generator.select;

import java.util.Collection;

import javax.jmi.model.ModelElement;

import net.mdatools.modelant.core.api.Select;

/**
 * Collect the contents of the provided element used as a namespace
 * @author Rusi Popov
 */
public class CollectContents implements Select<ModelElement,ModelElement>{
  public Collection<ModelElement> execute(ModelElement argument) throws RuntimeException, IllegalArgumentException {
    return (Collection<ModelElement>) argument.getConstraints();
  }
}
