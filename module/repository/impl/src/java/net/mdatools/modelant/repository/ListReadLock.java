/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation using list of locks, assuming that the number of threads obtaining locks is quite limited,
 * which might make the list implementation more efficient than a map.
 */
class ListReadLock implements ReadLock {

  private final List<ThreadState> locks = new ArrayList<>();

  /**
   * @see net.mdatools.modelant.repository.ReadLock#remove(net.mdatools.modelant.repository.ReadWriteMutex.ThreadState)
   */
  public void remove(ThreadState state) {
    locks.remove( state );
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
    locks.add(state);
  }

  /**
   * @see net.mdatools.modelant.repository.ReadLock#get()
   */
  public ThreadState get() {
    ThreadState result;
    ThreadState state;
    Thread current;

    current = Thread.currentThread();
    result = null;
    for (int i=0; result== null && i<locks.size(); i++) {
      state = locks.get( i );

      if ( current == state.getOwner()  ) {
        result = state;
      }
    }
    return result;
  }
}