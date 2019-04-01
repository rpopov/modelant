/*
 * Copyright (c) Rusi Popov, MDA Tools.net 2019
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Created on Apr 1, 2019
 */
package net.mdatools.modelant.mof14.maven.generator.name;

import java.util.logging.Logger;

import javax.jmi.model.ModelElement;

/**
 * A general mechanism of identifying names, considering all their decorations and oevrriding
 * with tags of other mechanisms.
 * @author Rusi Popov
 */
public interface ConstructName {

  Logger LOGGER = Logger.getLogger( ConstructName.class.getName() );

  /**
   * @param element not null element whose name is to retrieve
   * @return name,  null not defined
   */
  String constructName(ModelElement element);
}
