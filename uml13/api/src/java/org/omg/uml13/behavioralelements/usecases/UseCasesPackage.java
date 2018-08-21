/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml13.behavioralelements.usecases;

/**
 * Use_Cases package interface.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface UseCasesPackage extends javax.jmi.reflect.RefPackage {
    /**
     * Returns UseCase class proxy object.
     * @return UseCase class proxy object.
     */
    public org.omg.uml13.behavioralelements.usecases.UseCaseClass getUseCase();
    /**
     * Returns Actor class proxy object.
     * @return Actor class proxy object.
     */
    public org.omg.uml13.behavioralelements.usecases.ActorClass getActor();
    /**
     * Returns UseCaseInstance class proxy object.
     * @return UseCaseInstance class proxy object.
     */
    public org.omg.uml13.behavioralelements.usecases.UseCaseInstanceClass getUseCaseInstance();
    /**
     * Returns Extend class proxy object.
     * @return Extend class proxy object.
     */
    public org.omg.uml13.behavioralelements.usecases.ExtendClass getExtend();
    /**
     * Returns Include class proxy object.
     * @return Include class proxy object.
     */
    public org.omg.uml13.behavioralelements.usecases.IncludeClass getInclude();
    /**
     * Returns ExtensionPoint class proxy object.
     * @return ExtensionPoint class proxy object.
     */
    public org.omg.uml13.behavioralelements.usecases.ExtensionPointClass getExtensionPoint();
    /**
     * Returns ABaseExtend2 association proxy object.
     * @return ABaseExtend2 association proxy object.
     */
    public org.omg.uml13.behavioralelements.usecases.ABaseExtend2 getABaseExtend2();
    /**
     * Returns AExtensionExtend association proxy object.
     * @return AExtensionExtend association proxy object.
     */
    public org.omg.uml13.behavioralelements.usecases.AExtensionExtend getAExtensionExtend();
    /**
     * Returns AIncludeAddition association proxy object.
     * @return AIncludeAddition association proxy object.
     */
    public org.omg.uml13.behavioralelements.usecases.AIncludeAddition getAIncludeAddition();
    /**
     * Returns AInclude2Base association proxy object.
     * @return AInclude2Base association proxy object.
     */
    public org.omg.uml13.behavioralelements.usecases.AInclude2Base getAInclude2Base();
    /**
     * Returns AExtensionPointUseCase association proxy object.
     * @return AExtensionPointUseCase association proxy object.
     */
    public org.omg.uml13.behavioralelements.usecases.AExtensionPointUseCase getAExtensionPointUseCase();
    /**
     * Returns AExtensionPointExtend association proxy object.
     * @return AExtensionPointExtend association proxy object.
     */
    public org.omg.uml13.behavioralelements.usecases.AExtensionPointExtend getAExtensionPointExtend();
}
