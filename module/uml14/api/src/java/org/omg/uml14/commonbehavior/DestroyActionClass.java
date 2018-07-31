package org.omg.uml14.commonbehavior;

/**
 * DestroyAction class proxy interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface DestroyActionClass extends javax.jmi.reflect.RefClass {
    /**
     * The default factory operation used to create an instance object.
     * @return The created instance object.
     */
    public DestroyAction createDestroyAction();
    /**
     * Creates an instance object having attributes initialized by the passed 
     * values.
     * @param name 
     * @param visibility 
     * @param isSpecification 
     * @param recurrence 
     * @param target 
     * @param isAsynchronous 
     * @param script 
     * @return The created instance object.
     */
    public DestroyAction createDestroyAction(java.lang.String name, org.omg.uml14.datatypes.VisibilityKind visibility, boolean isSpecification, org.omg.uml14.datatypes.IterationExpression recurrence, org.omg.uml14.datatypes.ObjectSetExpression target, boolean isAsynchronous, org.omg.uml14.datatypes.ActionExpression script);
}
