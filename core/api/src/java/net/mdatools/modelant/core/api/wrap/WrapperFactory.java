/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.api.wrap;

import java.util.Collection;
import java.util.List;

/**
 * A factory of wrapper classes.
 * CONVENTION:<ul>
 * <li> each Factory subclass must have all its constructors <b>not public</b>, where one of them must be without parameters.
 *      This prevents accidental instantiation of the factory itself this way violating its Singleton property.
 * <li> each Factory instance caches internally all the wrapper objects it creates, in order to guarantee: <pre>
 *      (for each X, Y)( factory.wrap(X) == factory.wrap(X) &lt;=&gt; X == Y)
 *      </pre>
 * <li> The subclasses must provide the mapping from wrapped to wrapper classes.
 * </ul>
 * NOTE: There is no single class/interface parameter, one for allmethods in this interface, as
 *       this is a factory wrapping several different hierarchies of classes/interfaces (due to the
 *       specifics of the MOF and UML metamodels, where there is no common root notion).
 * @author Rusi Popov (popovr@mdatools.net)
 */
public interface WrapperFactory {

  /**
   * @param toWrap could be null
   * @return a non-null instance unique for each non-null wrapped object or null, when wrapped is null
   * @throws IllegalArgumentException when there is no wrapper class corresponding to the class of toWrap
   */
  <A> Wrapper<A> wrap(A toWrap) throws IllegalArgumentException;

  /**
   * @param toWrap is a non-null collection of (any) objects to wrap
   * @return a non-null ArrayList of wrapped objects. Note that the type is important, because
   *         it is used in any further wrapping
   * @throws IllegalArgumentException when there is no wrapper class for an object to wrap
   * @see #wrap(Object)
   */
  <A> List<Wrapper<A>> wrap(Collection<A> toWrap) throws IllegalArgumentException;

  /**
   * This method binds an already built wrapper for a wrapped object that <b>has not been used</b> in this factory.
   * Thus, any further call to wrap( wrapper.getWrapped() ) would return the wrapper object.
   * @param wrapper is a non-null wrapper object
   * @throws IllegalArgumentException when there is already a wrapper registered for wrapper.getWrapped()
   */
  <A> void bind(Wrapper<A> wrapper) throws IllegalArgumentException;

  /**
   * Make sure this method is the last method called on a factory instance.
   */
  void destroy();

  /**
   * @param key not null
   * @return any property bound in the factory to that key
   */
  String getProperty(String key);
}
