/*
 * Copyright (c) Rusi Popov, MDA Tools.net 2019
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Created on Apr 1, 2019
 */
package net.mdatools.modelant.mof14.maven.generator.name;

import java.util.logging.Level;

import javax.jmi.model.ModelElement;

/**
 * Plain name, not null
 * @author Rusi Popov
 */
public class GetName implements ConstructName {
  public String constructName(ModelElement element) {
    String result;

    result = element.getName();
    if ( result == null ) {
      result = "";
    }
    result = result.replaceAll( "[^a-zA-Z0-9$]", "" );

    LOGGER.log( Level.FINE, "name = {0}", result );
    return result;
  }
}
