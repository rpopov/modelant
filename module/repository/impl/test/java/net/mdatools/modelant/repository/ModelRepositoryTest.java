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
package net.mdatools.modelant.repository;

import java.io.File;

import junit.framework.TestCase;
import net.mdatools.modelant.repository.api.ModelRepository;
import net.mdatools.modelant.repository.api.ModelRepositoryFactory;

public class ModelRepositoryTest extends TestCase {

  private ModelRepository repository;

  protected void setUp() throws Exception {
    repository = ModelRepositoryFactory.construct(new File("target"));
  }

  protected void tearDown() throws Exception {
    repository.shutdown();
  }

  public void testComitWriteTransaction() {
    repository.beginWriteTransaction();
    repository.endTransaction( true );
  }

  public void testRollbackWriteTransaction() {
    repository.beginWriteTransaction();
    repository.endTransaction( false );
  }

  public void testComitReadOnlyTransaction() {
    repository.beginReadOnlyTransaction();
    repository.endTransaction( true );
  }

  public void testRollbackReadTransaction() {
    repository.beginReadOnlyTransaction();
    repository.endTransaction( false );
  }

  // TODO add more
}