/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml13.behavioralelements.usecases;

/**
 * ExtensionPoint object instance interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface ExtensionPoint extends org.omg.uml13.foundation.core.ModelElement {
    /**
     * Returns the value of attribute location.
     * @return Value of attribute location.
     */
    public java.lang.String getLocation();
    /**
     * Sets the value of location attribute. See {@link #getLocation} for description 
     * on the attribute.
     * @param newValue New value to be set.
     */
    public void setLocation(java.lang.String newValue);
    /**
     * Returns the value of reference useCase.
     * @return Value of reference useCase.
     */
    public org.omg.uml13.behavioralelements.usecases.UseCase getUseCase();
    /**
     * Sets the value of reference useCase. See {@link #getUseCase} for description 
     * on the reference.
     * @param newValue New value to be set.
     */
    public void setUseCase(org.omg.uml13.behavioralelements.usecases.UseCase newValue);
    /**
     * Returns the value of reference extend.
     * @return Value of reference extend. Element type: {@link org.omg.uml13.behavioralelements.usecases.Extend}
     */
    public java.util.Collection/*<org.omg.uml13.behavioralelements.usecases.Extend>*/ getExtend();
}
