/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.uml13.wrap.task;

import net.mdatools.modelant.core.wrap.Factories;
import net.mdatools.modelant.repository.RepositoryAdapter;
import net.mdatools.modelant.util.task.StackedTask;

import org.apache.tools.ant.BuildException;
import org.omg.uml13.wrap.Uml13WrapFactory;

/**
 * This method notifies the UML 1.3 factory to cache the associations that are not supported by the UML 1.3 metamodel and
 * this way speed up the processing of the UML 1.3 models.
 */
public class BeginIntensiveReadTask extends StackedTask {

  private String extent;

  /**
   * @see org.apache.tools.ant.Task#execute()
   */
  public void execute() throws BuildException {
    Uml13WrapFactory uml13factory;

    if ( extent == null || extent.isEmpty()) {
      throw new BuildException("Expected an extent name provided", getLocation());
    }
    uml13factory = Factories.getFactory( Uml13WrapFactory.class );
    uml13factory.beginIntensive( RepositoryAdapter.getExtent( extent ) );
  }

  /**
   * @param extent the name of the repository extent where the model is loaded
   */
  public final void setExtent(String extent) {
    this.extent = extent;
  }
}