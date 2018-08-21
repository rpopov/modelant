/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml14.core;

/**
 * ModelElement object instance interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface ModelElement extends org.omg.uml14.core.Element {
    /**
     * Returns the value of attribute name.
     * @return Value of attribute name.
     */
    public java.lang.String getName();
    /**
     * Sets the value of name attribute. See {@link #getName} for description 
     * on the attribute.
     * @param newValue New value to be set.
     */
    public void setName(java.lang.String newValue);
    /**
     * Returns the value of attribute visibility.
     * @return Value of attribute visibility.
     */
    public org.omg.uml14.datatypes.VisibilityKind getVisibility();
    /**
     * Sets the value of visibility attribute. See {@link #getVisibility} for 
     * description on the attribute.
     * @param newValue New value to be set.
     */
    public void setVisibility(org.omg.uml14.datatypes.VisibilityKind newValue);
    /**
     * Returns the value of attribute isSpecification.
     * @return Value of attribute isSpecification.
     */
    public boolean isSpecification();
    /**
     * Sets the value of isSpecification attribute. See {@link #isSpecification} 
     * for description on the attribute.
     * @param newValue New value to be set.
     */
    public void setSpecification(boolean newValue);
    /**
     * Returns the value of reference namespace.
     * @return Value of reference namespace.
     */
    public org.omg.uml14.core.Namespace getNamespace();
    /**
     * Sets the value of reference namespace. See {@link #getNamespace} for description 
     * on the reference.
     * @param newValue New value to be set.
     */
    public void setNamespace(org.omg.uml14.core.Namespace newValue);
    /**
     * Returns the value of reference clientDependency.
     * @return Value of reference clientDependency. Element type: {@link org.omg.uml14.core.Dependency}
     */
    public java.util.Collection/*<org.omg.uml14.core.Dependency>*/ getClientDependency();
    /**
     * Returns the value of reference constraint.
     * @return Value of reference constraint. Element type: {@link org.omg.uml14.core.Constraint}
     */
    public java.util.Collection/*<org.omg.uml14.core.Constraint>*/ getConstraint();
    /**
     * Returns the value of reference targetFlow.
     * @return Value of reference targetFlow. Element type: {@link org.omg.uml14.core.Flow}
     */
    public java.util.Collection/*<org.omg.uml14.core.Flow>*/ getTargetFlow();
    /**
     * Returns the value of reference sourceFlow.
     * @return Value of reference sourceFlow. Element type: {@link org.omg.uml14.core.Flow}
     */
    public java.util.Collection/*<org.omg.uml14.core.Flow>*/ getSourceFlow();
    /**
     * Returns the value of reference comment.
     * @return Value of reference comment. Element type: {@link org.omg.uml14.core.Comment}
     */
    public java.util.Collection/*<org.omg.uml14.core.Comment>*/ getComment();
    /**
     * Returns the value of reference templateParameter.
     * @return Value of reference templateParameter. Element type: {@link org.omg.uml14.core.TemplateParameter}
     */
    public java.util.List/*<org.omg.uml14.core.TemplateParameter>*/ getTemplateParameter();
    /**
     * Returns the value of reference stereotype.
     * @return Value of reference stereotype. Element type: {@link org.omg.uml14.core.Stereotype}
     */
    public java.util.Collection/*<org.omg.uml14.core.Stereotype>*/ getStereotype();
    /**
     * Returns the value of reference taggedValue.
     * @return Value of reference taggedValue. Element type: {@link org.omg.uml14.core.TaggedValue}
     */
    public java.util.Collection/*<org.omg.uml14.core.TaggedValue>*/ getTaggedValue();
}
