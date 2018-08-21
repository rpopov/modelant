/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.wrap;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import net.mdatools.modelant.core.api.wrap.Wrapper;
import net.mdatools.modelant.core.api.wrap.WrapperFactory;

/**
 * This class is the root class for all factories of wrapper classes.
 * CONVENTION:<ul>
 * <li> each Factory subclass must have all its constructors <b>protected</b>, where one of them must be without parameters.
 *      This prevents accidental instantiation of the factory itself this way violating its Singleton property.
 * <li> each Factory instance caches internally all the wrapper objects it creates, in order to guarantee: <pre>
 *      (for each X, Y)( factory.wrap(X) == factory.wrap(X) &lt;=&gt; X == Y)
 *      </pre>
 * <li> The subclasses must provide the mapping from wrapped to wrapper classes.
 * </ul>
 * @author Rusi Popov
 */
public abstract class BaseWrapperFactory implements WrapperFactory {

  /**
   * Maps wrapped to wrapper objects, so that whenever a new wrapper is requested
   * for an object, its already existing wrapper will be re-used.
   */
  private final Map<Object, Wrapper<? extends Object>> wrappedToWrapperMap = new IdentityHashMap<>(1023);


  /**
   * @param toWrap could be null
   * @return a non-null instance unique for each non-null wrapped object or null, when wrapped is null
   * @throws IllegalArgumentException when there is no wrapper class corresponding to the class of toWrap
   */
  public final <A> Wrapper<A> wrap(A toWrap) throws IllegalArgumentException {
    Wrapper<A> result;

    if ( toWrap == null ) {
      result = null;
    } else {
      result = (Wrapper<A>) wrappedToWrapperMap.get( toWrap );
      if ( result == null ) {
        result = constructWrapper( toWrap );
        wrappedToWrapperMap.put(toWrap, result );
      }
    }
    return result;
  }


  /**
   * @param toWrap is a non-null collection of (any) objects to wrap
   * @return a non-null ArrayList of wrapped objects. Note that the type is important, because
   *         it is used in any further wrapping
   * @throws IllegalArgumentException when there is no wrapper class for an object to wrap
   * @see #wrap(Object)
   */
  public final <A> List<Wrapper<A>> wrap(Collection<A> toWrap) throws IllegalArgumentException {
    List<Wrapper<A>> result;

    if ( toWrap == null ) {
      result = null;
    } else {
      result = new ArrayList<>( toWrap.size() );
      for (A obj : toWrap ) {
        result.add( wrap( obj ) );
      }
    }
    return result;
  }

  /**
   * Make sure this method is the last method called on a factory instance.
   */
  public final void destroy() {
    wrappedToWrapperMap.clear();
  }

  /**
   * This method binds an already built wrapper for a wrapped object that <b>has not been used</b> in this factory.
   * Thus, any further call to wrap( wrapper.getWrapped() ) would return the wrapper object.
   * @param wrapper is a non-null wrapper object
   * @throws IllegalArgumentException when there is already a wrapper registered for wrapper.getWrapped()
   */
  public final <A> void bind(Wrapper<A> wrapper) throws IllegalArgumentException {
    Wrapper<A> existing;

    existing = (Wrapper<A>) wrappedToWrapperMap.put(wrapper.getWrapped(), wrapper );
    if ( existing != null ) {
      throw new IllegalArgumentException( "Registering a wrapper for an object that was already wrapped" );
    }
  }

  /**
   * @return a non null initialized wrapper instance or an exception is thrown.
   *         It is Wrapper by default
   */
  private <A> Wrapper<A> constructWrapper(A toWrap) throws IllegalArgumentException {
    Wrapper<A> result;
    Class resultClass;
    Constructor constructor;

    // because the wrapper class could be mapped by class or interface, try finding it both ways
    resultClass = getMappedClass( toWrap );
    try {
      constructor = resultClass.getConstructor(Object.class, WrapperFactory.class);
      result = (Wrapper<A>) constructor.newInstance( toWrap, this );
    } catch (Exception ex) {
      throw new IllegalArgumentException( ex );
    }
    return result;
  }


  /**
   * Recursively find the root wrapped object and use its  class (or interface) to map it to the actual wrapper class
   * @param toWrap not null object to wrap, which by itself might be a wrapper
   * @return the wrapper class
   * @throws IllegalArgumentException when no wrapper class found
   */
  private <A> Class getMappedClass(A toWrap) throws IllegalArgumentException {
    Class sourceClass;
    Class result;

    if ( toWrap instanceof Wrapper ) {
      result = getMappedClass(((Wrapper) toWrap).getWrapped());

    } else {
      sourceClass = toWrap.getClass();
      result = getMappedClass( sourceClass );

      if ( result == null &&  sourceClass.getInterfaces().length > 0) {
        result = getMappedClass( sourceClass.getInterfaces()[0] );

        if (result == null) {
          throw new IllegalArgumentException( "Could not identify the wrapper class for: "+toWrap );
        }
      }
    }
    return result;
  }

  /**
   * This method maps a class to wrap to the corresponding wrapper class
   * @param original is a non-null class to wrap
   * @return a possibly null wrapper class for that original class. Null is returned
   *         when there is no class to map to.
   */
  protected abstract Class<? extends Wrapper> getMappedClass(Class<?> original);
}