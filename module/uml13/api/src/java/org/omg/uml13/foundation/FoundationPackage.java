package org.omg.uml13.foundation;

/**
 * Foundation package interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface FoundationPackage extends javax.jmi.reflect.RefPackage {
    /**
     * Returns nested package DataTypes.
     * @return Proxy object related to nested package DataTypes.
     */
    public org.omg.uml13.foundation.datatypes.DataTypesPackage getDataTypes();
    /**
     * Returns nested package Core.
     * @return Proxy object related to nested package Core.
     */
    public org.omg.uml13.foundation.core.CorePackage getCore();
    /**
     * Returns nested package ExtensionMechanisms.
     * @return Proxy object related to nested package ExtensionMechanisms.
     */
    public org.omg.uml13.foundation.extensionmechanisms.ExtensionMechanismsPackage getExtensionMechanisms();
}
