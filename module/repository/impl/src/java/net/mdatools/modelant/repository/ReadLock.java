/*
 * Copyright (c) 2017 Rusi Popov, MDA Tools.net All rights reserved. 
 * 
 * This program and the accompanying materials are made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Created on 26.12.2017
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