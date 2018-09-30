/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.template;

/**
 * Key to lookup templates using their name and owner class
 */
class TemplateKey {
  public final Class<?> ownerClass;
  public final String templateName;
  private final int hash;

  /**
   * @param ownerClass not null
   * @param templateName not null
   */
  public TemplateKey(Class<?> ownerClass, String templateName) {
    this.ownerClass = ownerClass;
    this.templateName = templateName;
    this.hash = (ownerClass.hashCode()<<3)+templateName.hashCode();
  }

  public int hashCode() {
    return hash;
  }

  /**
   * @see java.lang.Object#equals(java.lang.Object)
   */
  public boolean equals(Object other) {
    return this == other
           || this.getClass() == other.getClass()
              && this.ownerClass == ((TemplateKey) other).ownerClass
              && this.templateName.equals( ((TemplateKey) other).templateName);
  }

  /**
   * @see java.lang.Object#toString()
   */
  public String toString() {
    StringBuilder builder = new StringBuilder();

    builder.append( "TemplateKey[ownerClass=" ).append( ownerClass ).append( ", templateName=" ).append( templateName )
           .append( "]" );
    return builder.toString();
  }
}