/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml14.collaborations;

/**
 * Collaboration object instance interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface Collaboration extends org.omg.uml14.core.GeneralizableElement, org.omg.uml14.core.Namespace {
    /**
     * Returns the value of reference interaction.
     * @return Value of reference interaction. Element type: {@link org.omg.uml14.collaborations.Interaction}
     */
    public java.util.Collection/*<org.omg.uml14.collaborations.Interaction>*/ getInteraction();
    /**
     * Returns the value of reference representedClassifier.
     * @return Value of reference representedClassifier.
     */
    public org.omg.uml14.core.Classifier getRepresentedClassifier();
    /**
     * Sets the value of reference representedClassifier. See {@link #getRepresentedClassifier} 
     * for description on the reference.
     * @param newValue New value to be set.
     */
    public void setRepresentedClassifier(org.omg.uml14.core.Classifier newValue);
    /**
     * Returns the value of reference representedOperation.
     * @return Value of reference representedOperation.
     */
    public org.omg.uml14.core.Operation getRepresentedOperation();
    /**
     * Sets the value of reference representedOperation. See {@link #getRepresentedOperation} 
     * for description on the reference.
     * @param newValue New value to be set.
     */
    public void setRepresentedOperation(org.omg.uml14.core.Operation newValue);
    /**
     * Returns the value of reference constrainingElement.
     * @return Value of reference constrainingElement. Element type: {@link org.omg.uml14.core.ModelElement}
     */
    public java.util.Collection/*<org.omg.uml14.core.ModelElement>*/ getConstrainingElement();
    /**
     * Returns the value of reference usedCollaboration.
     * @return Value of reference usedCollaboration. Element type: {@link org.omg.uml14.collaborations.Collaboration}
     */
    public java.util.Collection/*<org.omg.uml14.collaborations.Collaboration>*/ getUsedCollaboration();
}
