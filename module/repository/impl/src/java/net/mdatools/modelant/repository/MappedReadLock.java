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

import java.util.IdentityHashMap;
import java.util.Map;

/**
 * Implementation mapping threads to states
 * Efficient when high number of threads are used to read the contents.
 * In cases of small number of such threads, consider the {@link ListReadLock}.
 */
class MappedReadLock implements ReadLock {

  private final Map<Thread, ThreadState> locks = new IdentityHashMap<>();

  /**
   * @see net.mdatools.modelant.repository.ReadLock#remove(net.mdatools.modelant.repository.ReadWriteMutex.ThreadState)
   */
  public void remove(ThreadState state) {
    locks.remove( state.getOwner() );
  }

  /**
   * @see net.mdatools.modelant.repository.ReadLock#isEmpty()
   */
  public boolean isEmpty() {
    return locks.isEmpty();
  }

  /**
   * @see net.mdatools.modelant.repository.ReadLock#put(net.mdatools.modelant.repository.ReadWriteMutex.ThreadState)
   */
  public void put(ThreadState state) {
    locks.put(state.getOwner(), state);
  }

  /**
   * @see net.mdatools.modelant.repository.ReadLock#get()
   */
  public ThreadState get() {
    return locks.get( Thread.currentThread() );
  }
}