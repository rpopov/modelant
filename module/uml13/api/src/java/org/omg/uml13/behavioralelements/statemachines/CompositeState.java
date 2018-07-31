/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml13.behavioralelements.statemachines;

/**
 * CompositeState object instance interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface CompositeState extends org.omg.uml13.behavioralelements.statemachines.State {
    /**
     * Returns the value of attribute isConcurrent.
     * @return Value of attribute isConcurrent.
     */
    public boolean isConcurrent();
    /**
     * Sets the value of isConcurrent attribute. See {@link #isConcurrent} for 
     * description on the attribute.
     * @param newValue New value to be set.
     */
    public void setConcurrent(boolean newValue);
    /**
     * Returns the value of reference subvertex.
     * @return Value of reference subvertex. Element type: {@link org.omg.uml13.behavioralelements.statemachines.StateVertex}
     */
    public java.util.Collection/*<org.omg.uml13.behavioralelements.statemachines.StateVertex>*/ getSubvertex();
}
