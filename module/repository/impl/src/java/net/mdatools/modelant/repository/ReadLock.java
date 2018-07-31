/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.repository;

/**
 * Common mechanism for obtaining read locks
 */
interface ReadLock {

  /**
   * @param state not null
   */
  void remove(ThreadState state);

  boolean isEmpty();

  /**
   * @param state not null
   */
  void put(ThreadState state);

  /**
   * @return the possibly null state for the current thread
   */
  ThreadState get();
}