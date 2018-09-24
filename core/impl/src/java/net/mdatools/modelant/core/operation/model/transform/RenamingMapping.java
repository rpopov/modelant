/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.operation.model.transform;

import static net.mdatools.modelant.core.name.NameImpl.NO_MAP_NAME;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jmi.reflect.RefAssociationLink;
import javax.jmi.reflect.RefObject;
import javax.jmi.reflect.RefPackage;
import javax.jmi.reflect.RefStruct;

import net.mdatools.modelant.core.api.Operation;
import net.mdatools.modelant.core.api.Procedure;
import net.mdatools.modelant.core.api.model.ConstructOperation;
import net.mdatools.modelant.core.api.model.ConstructProcedure;
import net.mdatools.modelant.core.api.model.NameMapping;
import net.mdatools.modelant.core.api.name.AssociationName;
import net.mdatools.modelant.core.api.name.ClassName;
import net.mdatools.modelant.core.api.name.EnumValueName;
import net.mdatools.modelant.core.api.name.FieldName;
import net.mdatools.modelant.core.api.name.Name;
import net.mdatools.modelant.core.api.name.StructName;

/**
 * Define correspondence between the source and target models either as:<ul>
 * <li> direct name-to-name mapping
 * <li> direct parent package name-to-name mapping and deriving the specific name-to-name mapping for nested elements
 * <li> explicit name-to-transformation mapping
 * </ul>
 * @author Rusi Popov (popovr@mdatools.net)
 */
public class RenamingMapping implements NameMapping {

  private static final Logger LOGGER = Logger.getLogger( RenamingMapping.class.getName() );

  /**
   * Maps source to target names for transformation
   */
  private final Map<Name<?>, Name<?>> renaming = new HashMap<>();

  /**
   * Maps source model name to a constructor of procedures to update the target model elements
   */
  private final Map<Name<?>, ConstructProcedure<?>> nameToMethodMap = new HashMap<>();

  /**
   * Maps source model name to a constructor operations to produce the target model elements
   * Used only for operations on RefStruct
   */
  private final Map<StructName, ConstructOperation<RefStruct>> nameToOperationMap = new HashMap<>();

  /*
   * INVARIANT:
   * 1. renaming.keySet() intersect nameToMethodMap.keySet() = EMPTY
   * 2. nameToMethodMap maps different types considering the key:
   *   key instanceof ClassName - mapped is produce Procedure<RefObject>
   *   key instanceof AssociationName - mapped is produce Procedure<RefAssociationLink>
   *   key instanceof FieldName - mapped is produce Procedure<RefObejct>
   */

  /**
   * Implement in the subclass to initialize itself by calling the map() methods
   */
  protected RenamingMapping() {
  }

  /**
   * Register a name-to-name mapping
   * @param key not null qualified name, not a struct name
   * @param name not null qualified name
   */
  protected final <T extends Name<?>> void set(T key, T name) {
    assert key != null : "Expected a non-null key provided";
    assert !(key instanceof StructName) : "Expected "+key+" is not a struct name";

    assert name != null : "Expected a non-null name provided";

    renaming.put(key, name);
    nameToMethodMap.remove(key);
  }

  /**
   * Mark a class,association, field, enum or struct as not mapped
   * @param key not null
   */
  protected final <T extends Name<?>> void unset(T key) {
    set(key, NO_MAP_NAME);
  }

  /**
   * Register a field name transfer method
   *
   * As of the requirements of {@link NameMapping#mapMetaFieldName(FieldName, RefPackage, RefPackage, Map)}, the
   * procedure should NOT write into target attributes, that are NOT CHANGEABLE.
   *
   * @param key not null
   * @param translate not null
   */
  protected final void set(FieldName key,
                           ConstructProcedure<RefObject> translate) {

    assert key != null : "Expected a non-null key provided";
    assert translate != null : "Expected a non-null translate operation provided";

    nameToMethodMap.put( key, translate );
  }

	/**
   * Map an association to a transfer procedure.
   *
   * As of the requirements of {@link NameMapping#mapMetaAssociation(AssociationName, RefPackage, RefPackage, Map)}, the
   * procedure should NOT copy links into target associations, that are DERIVED.
   *
   * @param key
   * @param translate
   */
  protected final void set(AssociationName key,
                           ConstructProcedure<RefAssociationLink> translate) {
    assert key != null : "Expected a non-null key provided";
    assert translate != null : "Expected a non-null translate operation provided";

    nameToMethodMap.put( key, translate );
  }

	/**
   * Mark an association as mapped to the target one in the same direction of the links
   * @param key not null qualified name of the original association
   * @param target not null qualified name
   */
  protected final void setForward(AssociationName key, AssociationName target) {
		assert key != null : "Expected a non-null key provided";
		assert target != null : "Expected a non-null target provided";

    set( key,
    		 target.newForwardLinkProduction());
  }

	/**
   * Mark an association as mapped to the target one in the opposite direction of the links
   * @param key not null qualified name of the original association
   * @param target not null qualified name
   */
  protected final void setBackward(AssociationName key, AssociationName target) {
		assert key != null : "Expected a non-null key provided";
		assert target != null : "Expected a non-null target provided";

    set( key,
    		 target.newBackwardLinkProduction());
  }

	/**
   * Map a class to a conversion procedure
   * @param key
   * @param translate
   */
  protected final void set(ClassName key,
                           ConstructProcedure<RefObject> translate) {
    assert key != null : "Expected a non-null key provided";
    assert translate != null : "Expected a non-null translate operation provided";

    nameToMethodMap.put( key, translate );
  }

  /**
   * Map a struct type to a conversion operation
   * @param key
   * @param translate
   */
  protected final void set(StructName key,
                           ConstructOperation<RefStruct> translate) {
    assert key != null : "Expected a non-null key provided";
    assert translate != null : "Expected a non-null translate operation provided";

    nameToOperationMap.put( key, translate );
  }

	/**
	 * @see NameMapping#mapMetaClass(ClassName, RefPackage, RefPackage, Map)
	 */
	public final Procedure<RefObject> mapMetaClass(ClassName className,
	                                               RefPackage sourceExtent,
	                                               RefPackage targetExtent,
	                                               Map<RefObject, RefObject> objectsMap) {
	  ConstructProcedure<RefObject> mapped;

	  mapped	= (ConstructProcedure<RefObject>) lookupName(className);

	  return mapped.construct( sourceExtent, targetExtent, objectsMap, this );
	}

	/**
	 * @see NameMapping#mapMetaAssociation(AssociationName, RefPackage, RefPackage, Map)
	 */
	public final Procedure<RefAssociationLink> mapMetaAssociation(AssociationName associationName,
	                                                              RefPackage sourceExtent,
	                                                              RefPackage targetExtent,
	                                                              Map<RefObject, RefObject> objectsMap) {
	  ConstructProcedure<RefAssociationLink> mapped;

	  mapped	= (ConstructProcedure<RefAssociationLink>) lookupName(associationName);

	  return mapped.construct( sourceExtent, targetExtent, objectsMap, this );
	}

	/**
	 * @see NameMapping#mapMetaFieldName(FieldName, RefPackage, RefPackage, Map)
	 */
	public final Procedure<RefObject> mapMetaFieldName(FieldName fieldName,
	                                                   RefPackage sourceExtent,
	                                                   RefPackage targetExtent, Map<RefObject, RefObject> objectsMap) {
	  ConstructProcedure<RefObject> mapped;

	  mapped	= (ConstructProcedure<RefObject>) lookupName(fieldName);

	  return mapped.construct(sourceExtent, targetExtent, objectsMap, this);
	}



  /**
   * @param source not null source metamodel name
   * @return possibly null target metamodel name mapped to the source name.
   *         NOTE: It might happen that not all names, that are mapped to procedures through map*(name)
   *               are mapped to names. Use this method for testing purposes only
   */
  public final <T extends Name<?>> Name<T> getName(Name<T> source) {
    return constructMappedName( source );
  }

	/**
	 * @param name not null
	 * @return the non-null method to produce the transformation for the name into the corresponding object into the target extent
	 */
	private ConstructProcedure<?> lookupName(Name<?> name) {
	  ConstructProcedure<?> result;
		Name<?> mappedName;

		result = nameToMethodMap.get(name);

		if ( result==null ) { // no explicit method mapped
			mappedName = constructMappedName(name);

			if ( mappedName != null ) {
			  result = mappedName.constructTransfromation();
			} else {
				result = name.constructNoTransfromation();
			}
		} else { // log the explicit procedure mapping to complete the name mapping log in constructMappedName

	    LOGGER.log( Level.FINE, "{0} mapped to {1}", new Object[] {name, result});
		}
		return result;
	}

  /**
   * @param name not null
   * @return the non-null method to produce the transformation for the name into the corresponding object into the target extent
   */
  private ConstructOperation<RefStruct> lookupNameOperation(StructName name) {
    ConstructOperation<RefStruct> result;
    StructName mappedName;

    result = nameToOperationMap.get(name);

    if ( result==null ) { // no explicit method mapped
      mappedName = constructMappedName(name);

      if ( mappedName != null ) {
        result = mappedName.constructCopyOperation();
      } else {
        result = new ConstructOperation<RefStruct>() {
          public Operation<RefStruct> construct(RefPackage targetExtent, Map<RefObject, RefObject> objectsMap,
                                                NameMapping valueMapping) {
            return new Operation<RefStruct>() {
              public RefStruct execute(RefStruct argument) throws RuntimeException, IllegalArgumentException {
                return null;
              }
            };
          }
        };
      }
    } else { // log the explicit procedure mapping to complete the name mapping log in constructMappedName

      LOGGER.log( Level.FINE, "{0} mapped to {1}", new Object[] {name, result});
    }
    return result;
  }

	/**
	 * Use the defaults and existing mappings to construct the name that corresponds to the provided one in the target mode
	 * @param name not null
	 * @return null when no mapping defined
	 */
	private <P extends Name<?>, T extends Name<P>> T constructMappedName(T name) {
		T result;
		Name<?> constructedParent;

    result = (T) renaming.get(name);
    if ( result == NO_MAP_NAME ) { // stop any mapping
      result = null;

    } else if ( result == null
		            && name.getOwner() != null ) {
		  constructedParent = constructMappedName((Name<?>) name.getOwner());

      if ( constructedParent != null ) {
      	result = (T) name.constructName((P) constructedParent, name.getName());
      }
    }

    LOGGER.log( Level.FINE, "{0} mapped to {1}", new Object[] {name, result});

		return result;
	}

  /**
   * @see net.mdatools.modelant.core.api.model.NameMapping#mapEnum(net.mdatools.modelant.core.api.name.EnumValueName)
   */
  public final EnumValueName mapEnum(EnumValueName value) {
    return constructMappedName(value);
  }

  /**
   * @see net.mdatools.modelant.core.api.model.NameMapping#mapEnum(net.mdatools.modelant.core.api.name.EnumValueName)
   */
  public final Operation<RefStruct> mapStruct(StructName structName, RefPackage targetExtent, Map<RefObject, RefObject> objectsMap) {
    return lookupNameOperation( structName ).construct( targetExtent, objectsMap, this );
  }
}