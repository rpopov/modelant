package org.omg.uml13.foundation.extensionmechanisms;

/**
 * TaggedValue object instance interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface TaggedValue extends javax.jmi.reflect.RefObject {
    /**
     * Returns the value of attribute tag.
     * @return Value of attribute tag.
     */
    public java.lang.String getTag();
    /**
     * Sets the value of tag attribute. See {@link #getTag} for description on 
     * the attribute.
     * @param newValue New value to be set.
     */
    public void setTag(java.lang.String newValue);
    /**
     * Returns the value of attribute value.
     * @return Value of attribute value.
     */
    public java.lang.String getValue();
    /**
     * Sets the value of value attribute. See {@link #getValue} for description 
     * on the attribute.
     * @param newValue New value to be set.
     */
    public void setValue(java.lang.String newValue);
    /**
     * Returns the value of reference stereotype.
     * @return Value of reference stereotype.
     */
    public org.omg.uml13.foundation.extensionmechanisms.Stereotype getStereotype();
    /**
     * Sets the value of reference stereotype. See {@link #getStereotype} for 
     * description on the reference.
     * @param newValue New value to be set.
     */
    public void setStereotype(org.omg.uml13.foundation.extensionmechanisms.Stereotype newValue);
    /**
     * Returns the value of reference modelElement.
     * @return Value of reference modelElement.
     */
    public org.omg.uml13.foundation.core.ModelElement getModelElement();
    /**
     * Sets the value of reference modelElement. See {@link #getModelElement} 
     * for description on the reference.
     * @param newValue New value to be set.
     */
    public void setModelElement(org.omg.uml13.foundation.core.ModelElement newValue);
}
