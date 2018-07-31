package org.omg.uml13.behavioralelements.statemachines;

/**
 * ChangeEvent class proxy interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface ChangeEventClass extends javax.jmi.reflect.RefClass {
    /**
     * The default factory operation used to create an instance object.
     * @return The created instance object.
     */
    public ChangeEvent createChangeEvent();
    /**
     * Creates an instance object having attributes initialized by the passed 
     * values.
     * @param name 
     * @param visibility 
     * @param isSpecification 
     * @param changeExpression 
     * @return The created instance object.
     */
    public ChangeEvent createChangeEvent(java.lang.String name, org.omg.uml13.foundation.datatypes.VisibilityKind visibility, boolean isSpecification, org.omg.uml13.foundation.datatypes.BooleanExpression changeExpression);
}
