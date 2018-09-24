/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.api.model;

import java.util.Map;

import javax.jmi.reflect.RefAssociationLink;
import javax.jmi.reflect.RefObject;
import javax.jmi.reflect.RefPackage;
import javax.jmi.reflect.RefStruct;

import net.mdatools.modelant.core.api.Operation;
import net.mdatools.modelant.core.api.Procedure;
import net.mdatools.modelant.core.api.name.AssociationName;
import net.mdatools.modelant.core.api.name.ClassName;
import net.mdatools.modelant.core.api.name.EnumValueName;
import net.mdatools.modelant.core.api.name.FieldName;
import net.mdatools.modelant.core.api.name.StructName;

/**
 * A map from the instances of metamodel packages, classes, structs, enums and values from one metamodel
 * to another, effectively defining correspondence from a source model in a source metamodel into a target model
 * in a target metamodel.
 * @author Rusi Popov (popovr@mdatools.net)
 */
public interface NameMapping {
  /**
   * @param value not null
   * @return name of the enumerated value in the target model or null, when value is not mapped
   */
  EnumValueName mapEnum(EnumValueName value);

  /**
   * The Reflective interface for Structs imposes the need any Struct types to be copied through a
   * dedicated, explicit procedure, which handle the correspondence and order of the struct's fields.
   * <b>
   * Assumption: The structs do not nest and the structs do not refer objects/model elements -
   * the field values are only primitive values.
   * </b>
   * @param structName not null
   * @param targetExtent not null
   * @param objectsMap not null
   * @return name of the struct in the target model or null, when struct is not mapped
   */
  Operation<RefStruct> mapStruct(StructName structName, RefPackage targetExtent, Map<RefObject, RefObject> objectsMap);

  /**
   * Retrieve the procedure to produce the corresponding instance(s) in the target model and bind them
   * in the provided objectsMap
   * @param classifier not null metaobject describing a model element of the source model
   * @param sourceExtent TODO
   * @param targetExtent not null target model
   * @param objectsMap not null correspondence between source and target model elements
   * @return the non-null procedure to produce
   */
  Procedure<RefObject> mapMetaClass(ClassName classifier, RefPackage sourceExtent, RefPackage targetExtent, Map<RefObject, RefObject> objectsMap);

  /**
   * Identify the corresponding association from the target metamodel.
   * <b>
   * If the target/mapped attribute is DERIVED, the produced procedure should not set/produce links in it
   * </b>
   * @param association not null metaobject describing a model element of the source model
   * @param sourceExtent TODO
   * @param targetExtent not null target model
   * @param objectsMap non-null mapping of original model objects to target model objects
   * @return the non-null function to produce a new association link from the original one, the target model extent and the mapping between original and produced objectss
   */
  Procedure<RefAssociationLink> mapMetaAssociation(AssociationName association, RefPackage sourceExtent, RefPackage targetExtent, Map<RefObject, RefObject> objectsMap);


  /**
   * Identify the operation to copy/transform the named field reading its value from a source model object and
   * translating/transforming it and assigning it to object(s) in the target model.
   * <b>
   * If the target/mapped attribute is NOT CHANGEABLE, the produced procedure should not set it
   * </b>
   * @param feature not null
   * @param sourceExtent TODO
   * @param targetExtent not null target model
   * @param objectsMap
   * @return the non-null function/procedure to update the object(s) of the target model
   */
  Procedure<RefObject> mapMetaFieldName(FieldName feature, RefPackage sourceExtent, RefPackage targetExtent, Map<RefObject, RefObject> objectsMap);
}