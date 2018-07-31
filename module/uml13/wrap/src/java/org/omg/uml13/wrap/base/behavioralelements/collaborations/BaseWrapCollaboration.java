/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.omg.uml13.wrap.base.behavioralelements.collaborations;

import javax.jmi.reflect.RefClass;
import javax.jmi.reflect.RefPackage;
import org.omg.uml13.behavioralelements.collaborations.Collaboration;
import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.core.wrap.Factories;

/**
 * This is a wrapper of org.omg.uml13.behavioralelements.collaborations.Collaboration that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public abstract class BaseWrapCollaboration extends org.omg.uml13.wrap.foundation.core.WrapGeneralizableElement implements org.omg.uml13.behavioralelements.collaborations.Collaboration {

  /**
   * Wraps an already existing object into a new wrapper instance
   */
  protected BaseWrapCollaboration(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  /**
   * Constructs a new wrapped object
   */
  public BaseWrapCollaboration(RefPackage extent) {
    this( getClassProxy( extent ).refCreateInstance( null ),
          Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ));
    Factories.getFactory( org.omg.uml13.wrap.Uml13WrapFactory.class ).bind( this );
  }

  /**
   * This method retrieves the factory for org.omg.uml13.behavioralelements.collaborations.Collaboration (also known as "class proxy")
   * model class.
   */
  public static RefClass getClassProxy(RefPackage extent) {
    return ((org.omg.uml13.Uml13Package) extent).getBehavioralElements().getCollaborations().getCollaboration();
  }


  /**
   * Overrides the super implementation with the proper wrapped class
   * @see net.mdatools.modelant.core.wrap.Wrapper#getWrapped()
   */
  public Collaboration getWrapped() {
    return (Collaboration) super.getWrapped();
  }

  /**
   * @see net.mdatools.modelant.core.wrap.Wrapper#getDelegate()
   */
  protected Collaboration getDelegate() {
    return (Collaboration) super.getDelegate();
  }


  /**
   * @see javax.jmi.reflect.RefBaseObject#equals(java.lang.Object)
   */
  public boolean equals(java.lang.Object arg0) {
    return getDelegate().equals(arg0);
  }

  /**
   * @see javax.jmi.reflect.RefObject#refIsInstanceOf(javax.jmi.reflect.RefObject, boolean)
   */
  public boolean refIsInstanceOf(javax.jmi.reflect.RefObject arg0, boolean arg1) {
    return getDelegate().refIsInstanceOf(arg0, arg1);
  }

  /**
   * @see org.omg.uml13.foundation.core.GeneralizableElement#isAbstract()
   */
  public boolean isAbstract() {
    return getDelegate().isAbstract();
  }

  /**
   * @see org.omg.uml13.foundation.core.GeneralizableElement#isLeaf()
   */
  public boolean isLeaf() {
    return getDelegate().isLeaf();
  }

  /**
   * @see org.omg.uml13.foundation.core.GeneralizableElement#isRoot()
   */
  public boolean isRoot() {
    return getDelegate().isRoot();
  }

  /**
   * @see org.omg.uml13.foundation.core.ModelElement#isSpecification()
   */
  public boolean isSpecification() {
    return getDelegate().isSpecification();
  }

  /**
   * @see javax.jmi.reflect.RefBaseObject#hashCode()
   */
  public int hashCode() {
    return getDelegate().hashCode();
  }

  /**
   * @see javax.jmi.reflect.RefFeatured#refGetValue(java.lang.String)
   */
  public java.lang.Object refGetValue(java.lang.String arg0) {
    return getDelegate().refGetValue(arg0);
  }

  /**
   * @see javax.jmi.reflect.RefFeatured#refGetValue(javax.jmi.reflect.RefObject)
   */
  public java.lang.Object refGetValue(javax.jmi.reflect.RefObject arg0) {
    return getDelegate().refGetValue(arg0);
  }

  /**
   * @see javax.jmi.reflect.RefFeatured#refInvokeOperation(java.lang.String, java.util.List)
   */
  public java.lang.Object refInvokeOperation(java.lang.String arg0, java.util.List arg1) throws javax.jmi.reflect.RefException {
    return getDelegate().refInvokeOperation(arg0, arg1);
  }

  /**
   * @see javax.jmi.reflect.RefFeatured#refInvokeOperation(javax.jmi.reflect.RefObject, java.util.List)
   */
  public java.lang.Object refInvokeOperation(javax.jmi.reflect.RefObject arg0, java.util.List arg1) throws javax.jmi.reflect.RefException {
    return getDelegate().refInvokeOperation(arg0, arg1);
  }

  /**
   * @see javax.jmi.reflect.RefBaseObject#refMofId()
   */
  public java.lang.String refMofId() {
    return getDelegate().refMofId();
  }

  /**
   * @see org.omg.uml13.foundation.core.ModelElement#getName()
   */
  public java.lang.String getName() {
    return getDelegate().getName();
  }

  /**
   * @see javax.jmi.reflect.RefBaseObject#refVerifyConstraints(boolean)
   */
  public java.util.Collection refVerifyConstraints(boolean arg0) {
    return getDelegate().refVerifyConstraints(arg0);
  }

  /**
   * @see org.omg.uml13.behavioralelements.collaborations.Collaboration#getConstrainingElement()
   */
  public java.util.Collection getConstrainingElement() {
    return getDelegate().getConstrainingElement();
  }

  /**
   * @see org.omg.uml13.behavioralelements.collaborations.Collaboration#getInteraction()
   */
  public java.util.Collection getInteraction() {
    return getDelegate().getInteraction();
  }

  /**
   * @see org.omg.uml13.foundation.core.GeneralizableElement#getGeneralization()
   */
  public java.util.Collection getGeneralization() {
    return getDelegate().getGeneralization();
  }

  /**
   * @see org.omg.uml13.foundation.core.GeneralizableElement#getSpecialization()
   */
  public java.util.Collection getSpecialization() {
    return getDelegate().getSpecialization();
  }

  /**
   * @see org.omg.uml13.foundation.core.ModelElement#getClientDependency()
   */
  public java.util.Collection getClientDependency() {
    return getDelegate().getClientDependency();
  }

  /**
   * @see org.omg.uml13.foundation.core.ModelElement#getComment()
   */
  public java.util.Collection getComment() {
    return getDelegate().getComment();
  }

  /**
   * @see org.omg.uml13.foundation.core.ModelElement#getConstraint()
   */
  public java.util.Collection getConstraint() {
    return getDelegate().getConstraint();
  }

  /**
   * @see org.omg.uml13.foundation.core.ModelElement#getElementResidence()
   */
  public java.util.Collection getElementResidence() {
    return getDelegate().getElementResidence();
  }

  /**
   * @see org.omg.uml13.foundation.core.ModelElement#getPresentation()
   */
  public java.util.Collection getPresentation() {
    return getDelegate().getPresentation();
  }

  /**
   * @see org.omg.uml13.foundation.core.ModelElement#getSourceFlow()
   */
  public java.util.Collection getSourceFlow() {
    return getDelegate().getSourceFlow();
  }

  /**
   * @see org.omg.uml13.foundation.core.ModelElement#getSupplierDependency()
   */
  public java.util.Collection getSupplierDependency() {
    return getDelegate().getSupplierDependency();
  }

  /**
   * @see org.omg.uml13.foundation.core.ModelElement#getTargetFlow()
   */
  public java.util.Collection getTargetFlow() {
    return getDelegate().getTargetFlow();
  }

  /**
   * @see org.omg.uml13.foundation.core.ModelElement#getTemplateParameter2()
   */
  public java.util.Collection getTemplateParameter2() {
    return getDelegate().getTemplateParameter2();
  }

  /**
   * @see org.omg.uml13.foundation.core.ModelElement#getTemplateParameter3()
   */
  public java.util.Collection getTemplateParameter3() {
    return getDelegate().getTemplateParameter3();
  }

  /**
   * @see org.omg.uml13.foundation.core.ModelElement#getTemplateParameter()
   */
  public java.util.List getTemplateParameter() {
    return getDelegate().getTemplateParameter();
  }

  /**
   * @see org.omg.uml13.foundation.core.Namespace#getOwnedElement()
   */
  public java.util.List getOwnedElement() {
    return getDelegate().getOwnedElement();
  }

  /**
   * @see javax.jmi.reflect.RefObject#refClass()
   */
  public javax.jmi.reflect.RefClass refClass() {
    return getDelegate().refClass();
  }

  /**
   * @see javax.jmi.reflect.RefObject#refImmediateComposite()
   */
  public javax.jmi.reflect.RefFeatured refImmediateComposite() {
    return getDelegate().refImmediateComposite();
  }

  /**
   * @see javax.jmi.reflect.RefObject#refOutermostComposite()
   */
  public javax.jmi.reflect.RefFeatured refOutermostComposite() {
    return getDelegate().refOutermostComposite();
  }

  /**
   * @see javax.jmi.reflect.RefBaseObject#refMetaObject()
   */
  public javax.jmi.reflect.RefObject refMetaObject() {
    return getDelegate().refMetaObject();
  }

  /**
   * @see javax.jmi.reflect.RefBaseObject#refImmediatePackage()
   */
  public javax.jmi.reflect.RefPackage refImmediatePackage() {
    return getDelegate().refImmediatePackage();
  }

  /**
   * @see javax.jmi.reflect.RefBaseObject#refOutermostPackage()
   */
  public javax.jmi.reflect.RefPackage refOutermostPackage() {
    return getDelegate().refOutermostPackage();
  }

  /**
   * @see org.omg.uml13.foundation.core.ModelElement#getBinding()
   */
  public org.omg.uml13.foundation.core.Binding getBinding() {
    return getDelegate().getBinding();
  }

  /**
   * @see org.omg.uml13.behavioralelements.collaborations.Collaboration#getRepresentedClassifier()
   */
  public org.omg.uml13.foundation.core.Classifier getRepresentedClassifier() {
    return getDelegate().getRepresentedClassifier();
  }

  /**
   * @see org.omg.uml13.foundation.core.ModelElement#getNamespace()
   */
  public org.omg.uml13.foundation.core.Namespace getNamespace() {
    return getDelegate().getNamespace();
  }

  /**
   * @see org.omg.uml13.behavioralelements.collaborations.Collaboration#getRepresentedOperation()
   */
  public org.omg.uml13.foundation.core.Operation getRepresentedOperation() {
    return getDelegate().getRepresentedOperation();
  }

  /**
   * @see org.omg.uml13.foundation.core.ModelElement#getVisibility()
   */
  public org.omg.uml13.foundation.datatypes.VisibilityKind getVisibility() {
    return getDelegate().getVisibility();
  }

  /**
   * @see javax.jmi.reflect.RefFeatured#refSetValue(java.lang.String, java.lang.Object)
   */
  public void refSetValue(java.lang.String arg0, java.lang.Object arg1) {
    getDelegate().refSetValue(arg0, arg1);
  }

  /**
   * @see javax.jmi.reflect.RefFeatured#refSetValue(javax.jmi.reflect.RefObject, java.lang.Object)
   */
  public void refSetValue(javax.jmi.reflect.RefObject arg0, java.lang.Object arg1) {
    getDelegate().refSetValue(arg0, arg1);
  }

  /**
   * @see javax.jmi.reflect.RefObject#refDelete()
   */
  public void refDelete() {
    getDelegate().refDelete();
  }

  /**
   * @see org.omg.uml13.behavioralelements.collaborations.Collaboration#setRepresentedClassifier(org.omg.uml13.foundation.core.Classifier)
   */
  public void setRepresentedClassifier(org.omg.uml13.foundation.core.Classifier arg0) {
    getDelegate().setRepresentedClassifier(arg0);
  }

  /**
   * @see org.omg.uml13.behavioralelements.collaborations.Collaboration#setRepresentedOperation(org.omg.uml13.foundation.core.Operation)
   */
  public void setRepresentedOperation(org.omg.uml13.foundation.core.Operation arg0) {
    getDelegate().setRepresentedOperation(arg0);
  }

  /**
   * @see org.omg.uml13.foundation.core.GeneralizableElement#setAbstract(boolean)
   */
  public void setAbstract(boolean arg0) {
    getDelegate().setAbstract(arg0);
  }

  /**
   * @see org.omg.uml13.foundation.core.GeneralizableElement#setLeaf(boolean)
   */
  public void setLeaf(boolean arg0) {
    getDelegate().setLeaf(arg0);
  }

  /**
   * @see org.omg.uml13.foundation.core.GeneralizableElement#setRoot(boolean)
   */
  public void setRoot(boolean arg0) {
    getDelegate().setRoot(arg0);
  }

  /**
   * @see org.omg.uml13.foundation.core.ModelElement#setBinding(org.omg.uml13.foundation.core.Binding)
   */
  public void setBinding(org.omg.uml13.foundation.core.Binding arg0) {
    getDelegate().setBinding(arg0);
  }

  /**
   * @see org.omg.uml13.foundation.core.ModelElement#setName(java.lang.String)
   */
  public void setName(java.lang.String arg0) {
    getDelegate().setName(arg0);
  }

  /**
   * @see org.omg.uml13.foundation.core.ModelElement#setNamespace(org.omg.uml13.foundation.core.Namespace)
   */
  public void setNamespace(org.omg.uml13.foundation.core.Namespace arg0) {
    getDelegate().setNamespace(arg0);
  }

  /**
   * @see org.omg.uml13.foundation.core.ModelElement#setSpecification(boolean)
   */
  public void setSpecification(boolean arg0) {
    getDelegate().setSpecification(arg0);
  }

  /**
   * @see org.omg.uml13.foundation.core.ModelElement#setVisibility(org.omg.uml13.foundation.datatypes.VisibilityKind)
   */
  public void setVisibility(org.omg.uml13.foundation.datatypes.VisibilityKind arg0) {
    getDelegate().setVisibility(arg0);
  }
}