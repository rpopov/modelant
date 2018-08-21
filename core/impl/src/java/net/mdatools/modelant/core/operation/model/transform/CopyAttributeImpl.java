/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.core.operation.model.transform;

import java.util.Collection;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jmi.reflect.RefEnum;
import javax.jmi.reflect.RefObject;
import javax.jmi.reflect.RefPackage;
import javax.jmi.reflect.RefStruct;

import net.mdatools.modelant.core.api.Operation;
import net.mdatools.modelant.core.api.Procedure;
import net.mdatools.modelant.core.api.model.NameMapping;
import net.mdatools.modelant.core.api.name.EnumValueName;
import net.mdatools.modelant.core.api.name.StructName;
import net.mdatools.modelant.core.name.EnumValueNameImpl;
import net.mdatools.modelant.core.name.StructNameImpl;
import net.mdatools.modelant.core.operation.element.PrintModelElement;

/**
 * Copy a single attribute operation from the source attribute in the source object
 * to the target attribute in the target object.
 */
public class CopyAttributeImpl implements Procedure<RefObject> {

  private static final Logger LOGGER = Logger.getLogger( CopyAttributeImpl.class.getName() );

  private static final PrintModelElement PRINT_MODEL_ELEMENT = new PrintModelElement();

  private final String sourceName;
  private final String targetName;
  private final Map<RefObject, RefObject> objectsMap;
  private final RefPackage sourceExtent;
  private final RefPackage targetExtent;
  private final NameMapping valueMapping;

  /**
   * @param sourceName not null source attribute name
   * @param targetName not null target attribute name
   * @param objectsMap not null mapping of source model elements to target model elements
   * @param targetExtent not null target model extent
   * @param valueMapping not null
   */
  public CopyAttributeImpl(String sourceName,
                           String targetName,
                           Map<RefObject, RefObject> objectsMap,
                           RefPackage sourceExtent,
                           RefPackage targetExtent,
                           NameMapping valueMapping) {
    this.sourceName = sourceName;
    this.targetName = targetName;
    this.objectsMap = objectsMap;
    this.sourceExtent = sourceExtent;
    this.targetExtent = targetExtent;
    this.valueMapping = valueMapping;
  }

  /**
   * Copy the value of the source filed as a value of the target field with some
   * type transformation.
   *
   * Copied are single value attributes, assuming that the multiple-valued attributes
   * are associations.
   *
   * @param source not null source object
   */
  public void execute(RefObject source) throws IllegalArgumentException {
    Object sourceValue;
    RefObject target;

    target = objectsMap.get( source );
    if ( target != null ) {
      try {
        sourceValue = source.refGetValue( sourceName );

      } catch (Exception ex) {
        throw new IllegalArgumentException( "Getting '"+sourceName+"' attribute of object "
                                            +PRINT_MODEL_ELEMENT.execute( source )
                                            + " instance of "
                                            +PRINT_MODEL_ELEMENT.execute( source.refMetaObject() )+" failed with: ",  ex );
      }

      if ( sourceValue instanceof RefStruct ) {
        assignStructValue((RefStruct) sourceValue, target);

      } else if ( sourceValue instanceof RefEnum ) {
        assignEnumValue((RefEnum) sourceValue, target);

      } else if ( sourceValue instanceof RefObject ) { // this object should have been mapped
        assignObjectVlaue((RefObject) sourceValue, target );

      } else if (sourceValue != null) { // any other non-JMI/non-MOF object
        setValue(sourceValue, target);
      }
    }
  }

  /**
   * Convert and assign a struct value to the target model element.
   * @param sourceStruct not null source struct value
   * @param target not null target model value to update
   */
  private void assignStructValue(RefStruct sourceStruct, RefObject target) {
    RefStruct targetStruct;
    Operation<RefStruct> operation;

    operation = valueMapping.mapStruct(new StructNameImpl(sourceExtent, sourceStruct),
                                       targetExtent,
                                       objectsMap);
    try {
      targetStruct = operation.execute( sourceStruct );
      setValue(targetStruct, target);
    } catch (Exception ex) {
      LOGGER.log( Level.INFO,
                  "Mapping source struct "
                  +PRINT_MODEL_ELEMENT.execute( sourceStruct )
                  +" caused:", ex);
    }
  }

  /**
   * Convert and assign an enum value to the target model element.
   * @param sourceValue not null enum value
   * @param target not null target model value to update
   */
  private void assignEnumValue(RefEnum sourceValue, RefObject target) {
    EnumValueName targetEnumName;

    targetEnumName = valueMapping.mapEnum( new EnumValueNameImpl(sourceExtent, sourceValue));

    if ( targetEnumName != null ) {
      setValue( targetEnumName.lookupValue( targetExtent ), target );

    } else {
      LOGGER.log( Level.INFO,
                  "The source enum value of "
                  + PRINT_MODEL_ELEMENT.execute( sourceValue )
                  + " is not mapped. Skipped ");
    }
  }

  /**
   * @param sourceValue not null object value to convert and assign to the targetField of the target object
   * @param target not null target model object to update
   */
  private void assignObjectVlaue(RefObject sourceValue, RefObject target) {
    Object convertedValue;

    convertedValue = objectsMap.get( sourceValue );
    setValue(convertedValue, target);

    LOGGER.log( Level.FINE, "{0} mapped to {1}",
                new Object[] {PRINT_MODEL_ELEMENT.toPrint( sourceValue ),
                              PRINT_MODEL_ELEMENT.toPrint( convertedValue )});
  }

  /**
   * @param convertedValue not null
   * @param target not null
   */
  private void setValue(Object convertedValue, RefObject target) {
    Object targetValue;

    try {
      targetValue = target.refGetValue(targetName);

      if ( targetValue instanceof Collection ) {
        if ( convertedValue instanceof Collection ) {
          ((Collection) targetValue).addAll((Collection)  convertedValue);
        } else {
          ((Collection) targetValue).add( convertedValue );
        }
      } else {
        target.refSetValue( targetName, convertedValue );
      }
    } catch (Exception ex) {
      throw new IllegalArgumentException( "Setting '"+targetName+"' to value: '"
                                          +PRINT_MODEL_ELEMENT.execute( convertedValue )
                                          +"' on target object "
                                          +PRINT_MODEL_ELEMENT.execute( target )
                                          + " instance of "
                                          +PRINT_MODEL_ELEMENT.execute( target.refMetaObject() )+" failed with: ", ex);
    }
  }

  /**
   * @see java.lang.Object#toString()
   */
  public String toString() {
    return "Copy from "+sourceName+" to "+targetName;
  }
}