/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.repository;

/**
 * This class represents the state of a thread. This class must be accessed only by thread it
 * represents.
 * Requires explicit synchronization on access.
 *
 * @author Rusi Popov
 */
class ThreadState {
  /**
   * Number of locks the threat has obtained
   */
  private int locks;

  /**
   * The thread that owns the locks
   */
  private final Thread owner;


  /**
   * Constructs an non-locked thread state
   */
  public ThreadState() {
    this.locks = 0;
    this.owner = Thread.currentThread();
  }


  /**
   * @return owner
   * @see #owner
   */
  public final Thread getOwner() {
    return owner;
  }


  /**
   * Registers a lock on this/owner thread
   */
  public final void lock() {
    assert owner == Thread.currentThread() : "Expected the owner thread accesses this";
    locks++;
  }


  /**
   * Releases a lock on this/owner thread
   *
   * @return true when no more locks are left
   */
  public final boolean unlock() {
    assert owner == Thread.currentThread() : "Expected the owner thread accesses this";

    locks--;
    if ( locks < 0 ) {
      throw new IllegalStateException( "Called more times unlock() than lock()" );
    }
    return locks == 0;
  }


  /**
   * @see java.lang.Object#toString()
   */
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append( "ThreadState {locks=" ).append( locks )
           // .append( ", owner=" ).append( owner )
           .append( "}" );
    return builder.toString();
  }


  /**
   * @return true if the current thread owns this lock
   */
  public boolean doesOwnTheLock() {
    return owner == Thread.currentThread();
  }
}