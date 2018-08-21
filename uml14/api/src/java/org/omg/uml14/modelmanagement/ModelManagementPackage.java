/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml14.modelmanagement;

/**
 * Model_Management package interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface ModelManagementPackage extends javax.jmi.reflect.RefPackage {
    public org.omg.uml14.datatypes.DataTypesPackage getDataTypes();
    public org.omg.uml14.core.CorePackage getCore();
    /**
     * Returns UmlPackage class proxy object.
     * @return UmlPackage class proxy object.
     */
    public org.omg.uml14.modelmanagement.UmlPackageClass getUmlPackage();
    /**
     * Returns Model class proxy object.
     * @return Model class proxy object.
     */
    public org.omg.uml14.modelmanagement.ModelClass getModel();
    /**
     * Returns Subsystem class proxy object.
     * @return Subsystem class proxy object.
     */
    public org.omg.uml14.modelmanagement.SubsystemClass getSubsystem();
    /**
     * Returns ElementImport class proxy object.
     * @return ElementImport class proxy object.
     */
    public org.omg.uml14.modelmanagement.ElementImportClass getElementImport();
    /**
     * Returns AImportedElementElementImport association proxy object.
     * @return AImportedElementElementImport association proxy object.
     */
    public org.omg.uml14.modelmanagement.AImportedElementElementImport getAImportedElementElementImport();
    /**
     * Returns APackageElementImport association proxy object.
     * @return APackageElementImport association proxy object.
     */
    public org.omg.uml14.modelmanagement.APackageElementImport getAPackageElementImport();
}
