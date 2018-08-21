/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml13.foundation.extensionmechanisms;

/**
 * Extension_Mechanisms package interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface ExtensionMechanismsPackage extends javax.jmi.reflect.RefPackage {
    /**
     * Returns Stereotype class proxy object.
     * @return Stereotype class proxy object.
     */
    public org.omg.uml13.foundation.extensionmechanisms.StereotypeClass getStereotype();
    /**
     * Returns TaggedValue class proxy object.
     * @return TaggedValue class proxy object.
     */
    public org.omg.uml13.foundation.extensionmechanisms.TaggedValueClass getTaggedValue();
    /**
     * Returns ARequiredTagStereotype association proxy object.
     * @return ARequiredTagStereotype association proxy object.
     */
    public org.omg.uml13.foundation.extensionmechanisms.ARequiredTagStereotype getARequiredTagStereotype();
    /**
     * Returns AStereotypeExtendedElement association proxy object.
     * @return AStereotypeExtendedElement association proxy object.
     */
    public org.omg.uml13.foundation.extensionmechanisms.AStereotypeExtendedElement getAStereotypeExtendedElement();
    /**
     * Returns AConstrainedElement2StereotypeConstraint association proxy object.
     * @return AConstrainedElement2StereotypeConstraint association proxy object.
     */
    public org.omg.uml13.foundation.extensionmechanisms.AConstrainedElement2StereotypeConstraint getAConstrainedElement2StereotypeConstraint();
    /**
     * Returns AModelElementTaggedValue association proxy object.
     * @return AModelElementTaggedValue association proxy object.
     */
    public org.omg.uml13.foundation.extensionmechanisms.AModelElementTaggedValue getAModelElementTaggedValue();
}
