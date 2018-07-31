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

import java.util.logging.Level;
import java.util.logging.Logger;

import org.netbeans.mdr.util.TransactionMutex;

/**
 * This class represents a mutex that allows multiple threads to lock the repository for reading (shared lock), while
 * no more than one thread could obtain write (exclusive) lock:<ul>
 * <li>The locks a thread obtains can be nested.
 * <li>Multiple threads could obtain/release locks in parallel.
 * <li>No more than one thread could obtain write lock, but once obtained, the same thread could obtain multiple write locks.
 *     When the last write lock is released, the transaction must be committed/rolled back
 * <li>
 * </ul>
 * @author Rusi Popov
 */
public class ReadWriteMutex extends TransactionMutex {

  private static final Logger LOGGER = Logger.getLogger( ReadWriteMutex.class.getName() );

  /**
   * INVARIANT:
   * 1. There are four states of this mutex:
   * - free = no thread has obtained any lock (neither read,nor write)
   * - read lock - all threads that have obtained a lock, obtained a read lock
   * - write lock - there is exactly one thread that obtained a write lock
   * - error - invalid sequence of calls
   * 2. Transitions:
   *   free -> read lock when lock(false) called
   *   free -> write lock when lock(true) called
   *   read lock  -> read lock when lock(false) called
   *                 a tread that has obtained a read lock called unlock(), and there are more locks obtained by that thread
   *   read lock  -> free a tread that has obtained a read lock called unlock() and there are no more locks obtained by that thread
   *   read lock  -> error when unlock() called with no lock
   *   write lock -> write lock when lock(true) or lock(false) called by the same thread that already has obtained the write lock
   *   write lock -> write lock when unlock() called
   *   write lock -> free when unlock() called by the thread that has obtained write lock and there are no more write locks
   *   write lock -> error when unlock() called by other thread
   *
   * 3. When a thread calls lock(false) when the mutex is in write lock state, then this thread is set to wait until the
   *    write lock is released.
   * 4. When a thread calls lock(true) when the mutex is in read state, the thread is set to wait until no read lock
   *    by other thread is obtained.
   *
   * 5. States representation:
   *
   *     free state <=> writeState == null & readState is empty
   *     read state <=> writeState == null & readState is not empty
   *     write state<=> writeState != null (then readState is empty)
   *     error state<=> error is true and there is
   */
  private ThreadState writeState;
  private final ReadLock readState = new ListReadLock();

  /**
   * Indicates if until the outer-most leave(unlock) and error was reported to it
   */
  private boolean failureDetected;

  /**
   * Number of other threads waiting in {@link #enter(boolean)} for {@link #obtainLock(boolean)}} obtaining a lock.
   * In case there is no other pending locks, the current thread does not need to notify any other thread,
   * which seems to be the most time-consuming operation.
   */
  private int waitingForLock;

  /**
   * @param storage
   * @param notifier
   * @param repository
   * @see TransactionMutex
   */
  public ReadWriteMutex(Object storage, Object notifier, Object repository) {
    super( storage, notifier, repository );
  }


  /**
   * @see org.netbeans.mdr.util.TransactionMutex#willFail()
   */
  public synchronized boolean willFail() {
    return failureDetected;
  }


  /**
   * @see org.netbeans.mdr.util.TransactionMutex#pendingChanges()
   * @return true if there are changes in progress (i.e. the state is write lock)
   */
  public synchronized boolean pendingChanges() {
    return isWriteState();
  }


  /**
   * @see org.netbeans.mdr.util.TransactionMutex#enter(boolean)
   */
  public synchronized void enter(boolean writeAccess) {
    if ( isFreeState() ) {
      obtainLock( writeAccess );

    } else if ( isReadState() ) { // writing cannot start until this is free
                                  // there is no way this thread to obtain write lock while waiting
      try {
        while ( writeAccess && !isFreeState() ) {
          LOGGER.log(Level.INFO, "Waiting all read locks to be released, in order to obtain a write lock {0} and {1}", new Object[]{readState, writeAccess});

          try {
            waitingForLock++;
            wait();
          } finally {
            waitingForLock--;
          }
        } // no exception thrown, free or obtaining a read lock

        obtainLock( writeAccess );
      } catch (InterruptedException ex) {
        throw new IllegalStateException( ex );
      }
    } else { // write state
      assert isWriteState() : "Expected we are in write lock state";

      if ( writeState.doesOwnTheLock() ) {
        LOGGER.log(Level.FINE, "Obtaining a write lock {0}", writeState);

        writeState.lock();

      } else { // another thread is attempting to obtain any lock while in writing state
        try {
          while ( isWriteState() ) {
            LOGGER.log(Level.INFO, "Waiting all write locks to be released, in order to obtain a write lock in this thread, {0} and {1}", new Object[]{readState, writeAccess});

            try {
              waitingForLock++;
              wait();
            } finally {
              waitingForLock--;
            }
          } // no exception thrown, free or read state

          assert isFreeState() : "Expected free state is reached";

          obtainLock( writeAccess );
        } catch (InterruptedException ex) {
          throw new IllegalStateException( ex );
        }
      }
    }
  }


  /**
   * @see org.netbeans.mdr.util.TransactionMutex#leave(boolean)
   * @param fail <code>false</code> indicates transaction
   *           success, <code>true</code> its failure. Failure is allowed
   *           only if the outermost transaction is a write transaction.
   * @return true if the outer-most transaction completed
   */
  public synchronized boolean leave(boolean fail) {
    boolean result;
    ThreadState state;

    failureDetected |= fail;

    if ( isFreeState() ) {
      throw new IllegalStateException( "Trying to release a non-existent transaction" );

    } else if ( isReadState() ) {
      LOGGER.log(Level.FINE, "Releasing a read lock {0}", readState);

      state = readState.get();

      if ( state == null ) { // this thread has not obtained any lock
        throw new IllegalStateException( "Trying to release a non-existent lock in this thread" );
      }
      result = state.unlock();

      if ( result ) { // this thread does not own any other lock
        readState.remove( state );

        if ( isFreeState() ) { // the outer-most transaction completed - clean the global error flag
          failureDetected = false;

          if ( waitingForLock > 0 ) { // there are threads blocked waiting to obtain a lock
            // notifyAll();
            notify();
          }
        }
      }
    } else { // write state
      assert isWriteState() : "Expected we are in write lock state";

      LOGGER.log(Level.FINE, "Releasing a write lock {0}", writeState);

      if ( writeState.doesOwnTheLock() ) {
        result = writeState.unlock();

        if ( result ) { // leaving the write lock state
          try {
            end( failureDetected );
          } finally {
            writeState = null;
            failureDetected = false;

            if ( waitingForLock > 0 ) { // there are threads blocked waiting to obtain a lock
              // notifyAll();
              notify();
            }
            assert isFreeState() : "Expected free state is reached";
          }
        }
      } else { // another thread is attempting to release a lock
        throw new IllegalStateException( "Trying to release a read lock that should have not been obtained, because we are in write lock state" );
      }
    }
    return result;
  }


  /**
   * PRE-CONDITION:<ul>
   * <li> if writeAccess => isFreeState()
   * <li> if readAccess => isFreeState() || read state
   * </ul>
   * @param writeAccess indicates write lock (true) or readlock (false) is attempted
   */
  private void obtainLock(boolean writeAccess) {
    ThreadState state;

    assert !writeAccess || isFreeState() : "Expexcted this mutext is in free (no lock) state when obtaining write lock";

    if ( writeAccess ) {
      LOGGER.log(Level.FINE, "Obtaining a write lock {0}", writeState);

      writeState = new ThreadState();
      writeState.lock();
    } else {
      LOGGER.log(Level.FINE, "Obtaining a read lock {0}", readState);

      state = readState.get();
      if ( state == null ) {
        state = new ThreadState();
        readState.put( state );
      }
      state.lock();
    }
  }


  private boolean isWriteState() {
    return writeState != null;
  }


  private boolean isReadState() {
    return writeState == null && !readState.isEmpty();
  }


  private boolean isFreeState() {
    return writeState == null && readState.isEmpty();
  }
}