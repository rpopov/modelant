package org.omg.uml13.foundation.core;

/**
 * ModelElement object instance interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface ModelElement extends org.omg.uml13.foundation.core.Element {
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
    public org.omg.uml13.foundation.datatypes.VisibilityKind getVisibility();
    /**
     * Sets the value of visibility attribute. See {@link #getVisibility} for 
     * description on the attribute.
     * @param newValue New value to be set.
     */
    public void setVisibility(org.omg.uml13.foundation.datatypes.VisibilityKind newValue);
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
    public org.omg.uml13.foundation.core.Namespace getNamespace();
    /**
     * Sets the value of reference namespace. See {@link #getNamespace} for description 
     * on the reference.
     * @param newValue New value to be set.
     */
    public void setNamespace(org.omg.uml13.foundation.core.Namespace newValue);
    /**
     * Returns the value of reference clientDependency.
     * @return Value of reference clientDependency. Element type: {@link org.omg.uml13.foundation.core.Dependency}
     */
    public java.util.Collection/*<org.omg.uml13.foundation.core.Dependency>*/ getClientDependency();
    /**
     * Returns the value of reference constraint.
     * @return Value of reference constraint. Element type: {@link org.omg.uml13.foundation.core.Constraint}
     */
    public java.util.Collection/*<org.omg.uml13.foundation.core.Constraint>*/ getConstraint();
    /**
     * Returns the value of reference supplierDependency.
     * @return Value of reference supplierDependency. Element type: {@link org.omg.uml13.foundation.core.Dependency}
     */
    public java.util.Collection/*<org.omg.uml13.foundation.core.Dependency>*/ getSupplierDependency();
    /**
     * Returns the value of reference presentation.
     * @return Value of reference presentation. Element type: {@link org.omg.uml13.foundation.core.PresentationElement}
     */
    public java.util.Collection/*<org.omg.uml13.foundation.core.PresentationElement>*/ getPresentation();
    /**
     * Returns the value of reference targetFlow.
     * @return Value of reference targetFlow. Element type: {@link org.omg.uml13.foundation.core.Flow}
     */
    public java.util.Collection/*<org.omg.uml13.foundation.core.Flow>*/ getTargetFlow();
    /**
     * Returns the value of reference sourceFlow.
     * @return Value of reference sourceFlow. Element type: {@link org.omg.uml13.foundation.core.Flow}
     */
    public java.util.Collection/*<org.omg.uml13.foundation.core.Flow>*/ getSourceFlow();
    /**
     * Returns the value of reference templateParameter3.
     * @return Value of reference templateParameter3. Element type: {@link org.omg.uml13.foundation.core.TemplateParameter}
     */
    public java.util.Collection/*<org.omg.uml13.foundation.core.TemplateParameter>*/ getTemplateParameter3();
    /**
     * Returns the value of reference binding.
     * @return Value of reference binding.
     */
    public org.omg.uml13.foundation.core.Binding getBinding();
    /**
     * Sets the value of reference binding. See {@link #getBinding} for description 
     * on the reference.
     * @param newValue New value to be set.
     */
    public void setBinding(org.omg.uml13.foundation.core.Binding newValue);
    /**
     * Returns the value of reference comment.
     * @return Value of reference comment. Element type: {@link org.omg.uml13.foundation.core.Comment}
     */
    public java.util.Collection/*<org.omg.uml13.foundation.core.Comment>*/ getComment();
    /**
     * Returns the value of reference elementResidence.
     * @return Value of reference elementResidence. Element type: {@link org.omg.uml13.foundation.core.ElementResidence}
     */
    public java.util.Collection/*<org.omg.uml13.foundation.core.ElementResidence>*/ getElementResidence();
    /**
     * Returns the value of reference templateParameter.
     * @return Value of reference templateParameter. Element type: {@link org.omg.uml13.foundation.core.TemplateParameter}
     */
    public java.util.List/*<org.omg.uml13.foundation.core.TemplateParameter>*/ getTemplateParameter();
    /**
     * Returns the value of reference templateParameter2.
     * @return Value of reference templateParameter2. Element type: {@link org.omg.uml13.foundation.core.TemplateParameter}
     */
    public java.util.Collection/*<org.omg.uml13.foundation.core.TemplateParameter>*/ getTemplateParameter2();
}
