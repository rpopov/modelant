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
package net.mdatools.modelant;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import junit.framework.TestCase;

public class ThrowableTest extends TestCase {

  private static Method getStackTraceDepthMethod;
  private static Method getStackTraceElementMethod;

  protected void setUp() throws Exception {
    super.setUp();

    getStackTraceDepthMethod = Throwable.class.getDeclaredMethod("getStackTraceDepth", null);
    getStackTraceDepthMethod.setAccessible( true );

    getStackTraceElementMethod = Throwable.class.getDeclaredMethod("getStackTraceElement",
                                                           new Class[]{ int.class });
    getStackTraceElementMethod.setAccessible( true );
  }


  public void testStackTraceDepth() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
    Throwable th = new Throwable();
    int depth;

    depth = ((Integer)getStackTraceDepthMethod.invoke(th, null)).intValue();
    assertTrue("Non-zero depth", depth > 0);
  }


  public void testStackTraceElement() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
    Throwable th = new Throwable();
    int depth;
    StackTraceElement el;

    depth = ((Integer)getStackTraceDepthMethod.invoke(th, null)).intValue();
    assertTrue("Non-zero depth", depth > 0);
    for (int i=0;i<depth;i++) {
      el = (StackTraceElement) getStackTraceElementMethod.invoke(th, new Object[]{new Integer(i)});
      assertNotNull("Stack trace element should be not-null", el);
//      System.out.println(el);
    }
  }
}
