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
package net.mdatools.modelant.template.api;

import java.io.IOException;


/**
 * The interface of the ModelAnt Templates. Each ModelAnt Template is bound to a class (referred as "wrapped") and
 * renders its instances.
 * In addition to the regular methods, the wrapped class can have either
 * <b>explicit or implicit render methods</b>.
 * <ul>
 * <li>The <b>explicit</b> render methods are just regular Java methods, that delegate to the methods of the TemplateEngine
 * by transferring itself (this) as the first parameter.<br/>
 * Note that the methods<ul>
 * <li>{@link TemplateEngine#render(Object, TemplateContext)}
 * <li>{@link TemplateEngine#render(java.util.Collection, Template, TemplateContext)}
 * </ul>
 * </ul>
 * use the name of the Java method that immediately calls them as the name of the ModelAnt Template to call -
 * see the implicit methods below.<br/>
 * The methods:<ul>
 * <li>{@link TemplateEngine#render(Object, String, TemplateContext)}
 * <li>{@link TemplateEngine#render(java.util.Collection, String, TemplateContext)}
 * </ul>
 * have as an additional parameter the name of the ModelAnt Template to call.
 * <li>The <b>implicit</b> render methods are implemented just as ModelAnt Templates following the convention:
 *   <ul>
 *   <li> Create a corresponding ModelAnt Template for the wrapper method must be
 * <pre>
 *      &lt;directory for the package of the wrapper class&gt;/
 *        &lt;directory with class name&gt;/
 *          &lt;name of the ModelAnt Template&gt;.jsp
 * </pre>
 * <li> The ModelAnt Template must wrap the wrapped class by using the directive:<br>
 * <li> The ModelAnt Template guarantees that the scriptlets can access the local variable <b>wrapped</b>
 * containing the <b>non-null object</b> to render the template for.
 * <li> The ModelAnt Template use scriptlets to generate the dynamic content. The scriptlets allow calling any methods of
 * the wrapped object, including rendering it through other ModelAnt Templates or rendering other objects.
 * @param <W> The type of the wrapped object
 * @author Rusi Popov
 */
public interface Template<W> {

  /**
   * The template method to call
   * @param wrapped is the non-null object to apply the object to
   */
  void render(W wrapped, TemplateEngine engine, TemplateContext context) throws IOException;

}