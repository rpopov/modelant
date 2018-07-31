/*
 * Copyright (c) 2001,2012,2018 Rusi Popov, MDA Tools.net
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

import org.netbeans.mdr.NBMDRepositoryImpl;
import org.netbeans.mdr.util.EventNotifier;
import org.netbeans.mdr.util.TransactionMutex;

import junit.framework.TestCase;
import net.mdatools.modelant.repository.spi.ModelRepositorySetup;

public class ReadWriteMutexTest extends TestCase {

  private static final int MAX = 16384;

  private ModelRepositorySetup repository;
  private TransactionMutex mutex;

  protected void setUp() throws Exception {
    System.err.println( "Setup" );

    repository = new RepositoryAdapter();  // ModelRepositoryFactory.construct();
    repository.initialize( new File("target") );

    mutex = new ReadWriteMutex( ((NBMDRepositoryImpl)((RepositoryAdapter) repository).getMdRepository()).getMdrStorage(),
                                new EventNotifier(),
                                ((RepositoryAdapter) repository).getMdRepository());
  }

  protected void tearDown() throws Exception {
    repository.shutdown();
  }

  public void testEnterExit() {
    mutex.enter( false );
    mutex.leave();
  }

  public void testEnterExitManySequential() {
    for (int i=0; i < MAX; i++) {
      mutex.enter( false );
      mutex.leave();
    }
  }

  public void testEnterExitManyNested() {
    for (int i=0; i < MAX; i++) {
      mutex.enter( false );
    }
    for (int i=0; i < MAX; i++) {
      mutex.leave();
    }
  }

  public void testEnterExitManyNestedExtra() {
    testEnterExitManyNested();
    try {
      mutex.leave();
      fail("Expected to fail");
    } catch (Exception ex) {
//      ex.printStackTrace();
    }
  }

  public void testEnterExitManyNestedWriteRead() {
    for (int i=0; i < MAX; i++) {
      mutex.enter( true );
    }
    mutex.enter( false );
    mutex.leave();
    for (int i=0; i < MAX; i++) {
      mutex.leave();
    }
  }

  public void testEnterExitManyNestedReadsAndAWriteRead() {
    for (int i=0; i < MAX; i++) {
      mutex.enter( false );
    }
// this should block
//    mutex.enter( true );
//    mutex.leave();
    for (int i=0; i < MAX; i++) {
      mutex.leave();
    }
  }
}