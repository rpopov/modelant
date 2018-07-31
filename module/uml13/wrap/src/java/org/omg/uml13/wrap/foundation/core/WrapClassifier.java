/*
 * Copyright (c) 2001,2012 Rusi Popov, MDA Tools.net
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Rusi Popov (popovr@mdatools.net) - initial implementation
 */
package org.omg.uml13.wrap.foundation.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import net.mdatools.modelant.core.wrap.Factories;
import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.core.wrap.Wrapper;

import org.omg.uml13.foundation.core.AssociationEnd;
import org.omg.uml13.foundation.core.Attribute;
import org.omg.uml13.foundation.core.Classifier;
import org.omg.uml13.foundation.core.Feature;
import org.omg.uml13.foundation.core.GeneralizableElement;
import org.omg.uml13.foundation.core.Generalization;
import org.omg.uml13.foundation.core.Interface;
import org.omg.uml13.foundation.core.Method;
import org.omg.uml13.foundation.core.ModelElement;
import org.omg.uml13.foundation.core.Namespace;
import org.omg.uml13.foundation.core.Operation;
import org.omg.uml13.foundation.core.Parameter;
import org.omg.uml13.foundation.core.ParameterClass;
import org.omg.uml13.foundation.core.UmlAssociation;
import org.omg.uml13.foundation.core.UmlClass;
import org.omg.uml13.wrap.Uml13WrapFactory;
import org.omg.uml13.wrap.base.foundation.core.BaseWrapClassifier;
import org.omg.uml13.wrap.foundation.datatypes.WrapAggregationKind;
import org.omg.uml13.wrap.modelmanagement.WrapModel;

/**
 * This is a wrapper of org.omg.uml13.foundation.core.Classifier that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public class WrapClassifier extends BaseWrapClassifier {
  /**
   * No restriction on association ends
   */
  private static final AssociationEndCondition NO_RESTRICTIION = null;

  public WrapClassifier(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  /**
   * @see WrapAssociationEnd#isAssociationToDomain()
   */
  public AssociationEndCondition getAssocisationToDomainCondition() {
    return getAssocisationToDomainCondition( getFactory() );
  }

  /**
   * @see WrapAssociationEnd#isAssociationToDomain()
   */
  public static AssociationEndCondition getAssocisationToDomainConditionF() {
    return getAssocisationToDomainCondition( Factories.getFactory( Uml13WrapFactory.class ) );
  }

  /**
   * @param factory is a factory instance that builds wrappers in this hierarchy
   * @return a non-null condition to determine association ends whose other end is bound to a domain class
   * @see WrapAssociationEnd#isAssociationToDomain()
   */
  public static AssociationEndCondition getAssocisationToDomainCondition(Factory factory) {
    return new AssociationEndCondition( factory ) {
      public boolean eval(AssociationEnd thisEnd) {
        boolean result;

        result = ((WrapAssociationEnd) wrap(thisEnd)).isAssociationToDomain();

        return result;
      }
    };
  }

  /**
   * @return a filter that lists only associations ends whose other end is a supported (in general, navigable)
   *         association to a domain class
   * @see WrapAssociationEnd#isAssociationToDomain()
   * @see WrapAssociationEnd#isAssociationSupported()
   */
  public AssociationEndCondition getAssocisationSupportedToDomainCondition() {
    return new AssociationEndCondition( getFactory() ) {
      public boolean eval(AssociationEnd thisEnd) {
        boolean result;
        WrapAssociationEnd wrappedThisEnd;

        wrappedThisEnd = (WrapAssociationEnd) wrap( thisEnd );
        result= wrappedThisEnd.isAssociationSupported()
                &&  wrappedThisEnd.isAssociationToDomain( );
        return result;
      }
    };

  }

  /**
   * @return a filter lists only associations ends that support associations to exactly 1 domain class
   */
  public AssociationEndCondition getAssocisationSupportedToDomainToExactlyOneCondition() {
    return new AssociationEndCondition( getFactory() ) {
      public boolean eval(AssociationEnd thisEnd) {
        boolean result;
        WrapAssociationEnd wrapThisEnd;
        WrapAssociationEnd wrapOtherEnd;
        WrapAggregationKind otherAggregation;

        wrapThisEnd = (WrapAssociationEnd) wrap( thisEnd );
        wrapOtherEnd = (WrapAssociationEnd) wrap( wrapThisEnd.getOtherAssociationEnd() );

        otherAggregation = wrapOtherEnd.getAggregationKind();

        result= wrapThisEnd.isAssociationSupported()
                &&  wrapThisEnd.isAssociationToDomain()
                && !otherAggregation.isAggregate()
                && !otherAggregation.isComposite()
                && wrapOtherEnd.isSingular()
                && wrapOtherEnd.isMandatory()
                && !wrapOtherEnd.getType().isAbstract();
        return result;
      }
    };
  }

  /**
   * @see #getAssocisationSupportedForPersistentClassesRootCondition(Factory)
   */
  public AssociationEndCondition getAssocisationSupportedForPersistentClassesRootCondition() {
    return getAssocisationSupportedForPersistentClassesRootCondition( getFactory() );
  }

  /**
   * @see #getAssocisationSupportedForPersistentClassesRootCondition(Factory)
   */
  public static AssociationEndCondition getAssocisationSupportedForPersistentClassesRootConditionF() {
    return getAssocisationSupportedForPersistentClassesRootCondition( Factories.getFactory( Uml13WrapFactory.class ) );
  }

  /**
   * @param factory is a non-null factory
   * @return a filter that lists only associations ends that support associations and the other
   * ends of these associations can be converted to EJB
   */
  public static AssociationEndCondition getAssocisationSupportedForPersistentClassesRootCondition(Factory factory) {
    return new AssociationEndCondition( factory ) {
      public boolean eval(AssociationEnd thisEnd) {
        WrapAssociationEnd wrapThisEnd;

        wrapThisEnd = (WrapAssociationEnd) wrap( thisEnd );

        return wrapThisEnd.isAssocisationPersistenceSupported();
      }
    };
  }

  /**
   * @see #getAssocisationToDbTableCondition(Factory)
   */
  public AssociationEndCondition getAssocisationToDbTableCondition() {
    return getAssocisationToDbTableCondition( getFactory() );
  }

  /**
   * @see #getAssocisationToDbTableCondition(Factory)
   */
  public static AssociationEndCondition getAssocisationToDbTableConditionF() {
    return getAssocisationToDbTableCondition( Factories.getFactory( Uml13WrapFactory.class ) );
  }

  /**
   * This filter lists only associations ends where the other ends of these associations
   * can be converted to EJB and this end holds the foreign key to it
   */
  public static AssociationEndCondition getAssocisationToDbTableCondition(Factory factory) {
    return new AssociationEndCondition( factory ) {
      public boolean eval(AssociationEnd thisEnd) {
        WrapAssociationEnd wrapThisEnd;

        wrapThisEnd = (WrapAssociationEnd) wrap( thisEnd );

        return wrapThisEnd.isAssociationToDbTable();
      }
    };
  }

  /**
   * This method filters all association ends pertaining to associations between a class in the
   * collection provided or a subclass of its.
   * <b>
   * This method actually does the Object-Relational Mapping of all associations of the classes listed.
   * The association ends returned pertain to DIFFERENT associations, so there is no way to process the
   * same association twice.
   * </b>
   * @param classesCollection a non-null collection of model elements, which are all
   *        Applicable for EJB
   * @return a non-null collection of association ends whose types are domain classes
   *         and they pertain to UNIQUE ASSOCIATIONS, i.e. there are no two ends pertaining
   *         to the same association.
   * @see #getOrmAssociationEnds(AssociationEndCondition)
   * @see <a href="http://www.agiledata.org/essays/mappingObjects.html">Object-relational mapping strategies</a>
   * @see <a href="http://www.agiledata.org/essays/impedanceMismatch.html">Object-relational mapping in general</a>
   */
  public static Collection getOrmAssociationEnds(Collection<Wrapper> classesCollection,
                                                 AssociationEndCondition condition) {
    Collection result = new LinkedHashSet( 111 ); // This structure keeps the order of the added classes, which makes the results possible to reproduce
    Set uniqueAssciations; // already processed associations
    WrapClassifier wrappedClass;
    Iterator classIterator;
    Iterator endIterator;
    Factory factory;

    AssociationEnd thisEnd;

    factory = Factories.getFactory( Uml13WrapFactory.class );

    uniqueAssciations = new HashSet(111);

    // collect all subclasses of all classes listed
    classIterator = classesCollection.iterator();
    while ( classIterator.hasNext() ) {
      wrappedClass = (WrapClassifier) factory.wrap( ((Wrapper)classIterator.next()).getWrapped() );

      endIterator = wrappedClass.getOrmAssociationEnds( condition ).iterator();
      while ( endIterator.hasNext() ) {
        thisEnd = (AssociationEnd) endIterator.next();

        if ( uniqueAssciations.add( thisEnd.getAssociation() ) ) { // still not known association
          result.add( thisEnd );
        }
      }
    }
    return new ArrayList(result);
  }

  /**
   * <b> Builds a list of all classes that are associated to the wrapped one or to
   * any of its subclasses. This method actually does the Object-Relational Mapping of all associations of this
   * class and its subclasses to a single list of associated classes at the other side of
   * the associations. The result classes are suitable for the same ORM policy, i.e. these
   * are the root superclasses of the associated clases<p/>
   * This mapping is suitable for the O/R Mapping of the class and all its subclasses (hierarchy)
   * to a single table policy to generate corresponding foreign keys.<p/>
   * Note that there are three more O/R Mapping policies clearly described bellow
   * </b>
   * @param condition imposes an optional restriction on the association ends to collect
   * @return a non-null list of all association ends of umlClass
   * @see <a href="http://www.agiledata.org/essays/mappingObjects.html">Object-relational mapping strategies</a>
   * @see <a href="http://www.agiledata.org/essays/impedanceMismatch.html">Object-relational mapping in general</a>
   */
  public Collection getOrmAssociatedClasses(AssociationEndCondition condition) {
    Collection result = new LinkedHashSet( 31 );
    Iterator endsIterator;

    AssociationEnd thisEnd;
    WrapAssociationEnd wrapThisEnd;

    Classifier otherClass;
    WrapClassifier wrapOtherClass;

    endsIterator = getOrmAssociationEnds( condition ).iterator();
    while ( endsIterator.hasNext() ) {
      thisEnd = (AssociationEnd) endsIterator.next();

      wrapThisEnd = (WrapAssociationEnd) wrap( thisEnd );
      otherClass = wrapThisEnd.getOtherAssociationEnd().getType();
      wrapOtherClass = (WrapClassifier) wrap( otherClass );

      result.add( wrapOtherClass.getRootSuperclass() );
    }
    return result;
  }

  /**
   * <b>
   * This method actually does the Object-Relational Mapping of all associations of this
   * class and its subclasses to a single list of associations.<p/>
   * This mapping is suitable for the O/R Mapping of the class and all its subclasses (hierarchy)
   * to a single table policy to generate corresponding foreign keys.<p/>
   * Note that there are three more O/R Mapping policies clearly described bellow
   * <p/>
   * As part of the overall mapping process this method makes sure there are no "other ends"
   * that have the same role and different bound root classes. This guarantees the proper mapping
   * of the foreign keys at "this end".
   * </b>
   *
   * @return a non-null collection of association ends at THIS END bound to this class or to
   *         a subclass of this class, that satisfy the condition provided.
   * @see <a href="http://www.agiledata.org/essays/mappingObjects.html">Object-relational mapping strategies</a>
   * @see <a href="http://www.agiledata.org/essays/impedanceMismatch.html">Object-relational mapping in general</a>
   */
  public Collection getOrmAssociationEnds(AssociationEndCondition condition) {
    return getOrmUniqueAssociationEnds( condition, new HashSet(11) );
  }


  /**
   * @param condition
   * @param rejected a non-null set of rejected/repeated associations. Any association found in this
   *        set will NOT be added to the result. Any associations that the method rejects are added
   *        to this parameter, so they could be reused in other calls this method, so that once excluded
   *        an association will not be added again
   * @return a non-null list of unique of association ends at THIS side, whose other ends refer classes
   *         in unique roles
   */
  private Collection getOrmUniqueAssociationEnds(AssociationEndCondition condition, Set rejected) {
    Collection result;

    Classifier thisClass;
    AssociationEnd thisEnd;

    WrapClassifier wrapThisClass;
    WrapAssociationEnd wrapThisEnd;
    WrapAssociationEnd WrapOtherEnd;

    WrapClassifier wrapOtherClass;
    WrapClassifier wrapOtherRootClass;
    WrapClassifier wrapOtherMappedClass;

    Iterator classesIterator;
    Iterator thisEndsIterator;

    Map otherEndToRootMap = new HashMap( 31 );

    result = new ArrayList();

    classesIterator = getAllSubclasses().iterator();
    while ( classesIterator.hasNext() ) {
      thisClass = (Classifier) classesIterator.next();
      wrapThisClass = (WrapClassifier) wrap( thisClass );

      // collect the ends at WrapThisClass and guarantee there are no other ends
      // with the same role and different root classes
      thisEndsIterator = wrapThisClass.getAssociationEnds( condition ).iterator();
      while ( thisEndsIterator.hasNext() ) {
        thisEnd = (AssociationEnd) thisEndsIterator.next();
        wrapThisEnd = (WrapAssociationEnd) wrap( thisEnd );
        WrapOtherEnd = (WrapAssociationEnd) wrap( wrapThisEnd.getOtherAssociationEnd() );

        wrapOtherClass = (WrapClassifier) wrap( WrapOtherEnd.getType() );
        wrapOtherRootClass = (WrapClassifier) wrap( wrapOtherClass.getRootSuperclass() );

        // INVARIANT:
        //   otherEndToRotmap maps all already processed "other ends" to their view root classes (types)
        //   if otherEndToRotmap contains WrapOtherEnd.name => otherEndToRotmap maps WrapOtherEnd.name to WrapOtherRootClass

        wrapOtherMappedClass = (WrapClassifier) otherEndToRootMap.get( WrapOtherEnd.formatFirstCapitalName() );
        if ( wrapOtherMappedClass == null ) { // not visited yet
          if ( !rejected.contains(thisEnd.getAssociation()) ) { // and not explicitly rejected
            result.add( thisEnd );
            otherEndToRootMap.put( WrapOtherEnd.formatFirstCapitalName(), wrapOtherRootClass );

          } else {
            LOGGER.log( Level.FINE,
                        "Skipping association to {0} class in {1} role as already rejected",
                        new Object[]{ wrapOtherRootClass.getLogicalName(),
                                      WrapOtherEnd.formatFirstCapitalName() });
          }
        } else { // repeated mapping

          rejected.add( thisEnd.getAssociation() );

          if ( wrapOtherMappedClass == wrapOtherRootClass ) { // already known mapping to the same other root class, not needed
            LOGGER.log( Level.FINE,
                        "Skipping association to {0} class in {1} role as already known",
                        new Object[]{ wrapOtherRootClass.getLogicalName(),
                                      WrapOtherEnd.formatFirstCapitalName() });
          } else { // wrong mapping to another table with the same role (FK) name
            LOGGER.log( Level.SEVERE,
                        "***  Association to {0} class in {1} role already mapped to {2} class",
                        new Object[]{ wrapOtherRootClass.getLogicalName(),
                                      WrapOtherEnd.formatFirstCapitalName(),
                                      wrapOtherMappedClass.getLogicalName()});
          }
        }
      }
    }
    return result;
  }

  /**
   * Builds a list of all association ends that the target umlClass participates in as their type
   * @return a non-null list of all association ends of umlClass
   */
  public Collection getAssociationEnds() {
    return getAssociationEnds( NO_RESTRICTIION );
  }


  /**
   * Builds a list of all association ends bound to the wrapped umlClass. The
   * wrapped classifier is their type.
   * @param condition imposes an optional restriction on the association ends to collect
   * @return a non-null list of all association ends of umlClass
   */
  public Collection getAssociationEnds(AssociationEndCondition condition) {
    Collection result;
    AssociationEnd end;
    Iterator endsItertaor;

    result = getFactory().locateAssociationEndsOf(getWrapped());

    if ( condition != null ) {
      endsItertaor = result.iterator();
      result = new ArrayList();

      while ( endsItertaor.hasNext() ) {
        end = (AssociationEnd) endsItertaor.next();

        if ( condition.eval( end ) ) {
          result.add( end );
        }
      }
    }
    return result;
  }

  /**
   * Builds a list of all classes that are associated to the wrapped one.
   * NOTE: the result might INCLUDE the wrapped class itself, if it is associated
   * @param condition imposes an optional restriction on the association ends to collect
   * @return a non-null list of all association ends of umlClass,
   */
  public Collection getAssociatedClasses(AssociationEndCondition condition) {
    Set result = new LinkedHashSet();
    Iterator associationEndIterator;

    AssociationEnd thisEnd;
    WrapAssociationEnd wrapThisEnd;

    Classifier otherClass;

    // collect all referred associated classes with no duplicates
    associationEndIterator = getAssociationEnds( condition ).iterator();
    while ( associationEndIterator.hasNext() ) {
      thisEnd = (AssociationEnd) associationEndIterator.next();

      wrapThisEnd = (WrapAssociationEnd) wrap( thisEnd );
      otherClass = wrapThisEnd.getOtherAssociationEnd().getType();

      result.add( otherClass );
    }
    return result;
  }

  /**
   * @param startFromClasses is a non-null collection of page classes
   * @param associationEndCondition is the condition the association to comply
   * @return a collection of unique classes that are referred by
   *         (associated to) the "start from classes" and satisfy the condition
   */
  public static Collection getAssociatedClasses(Collection startFromClasses,
                                                AssociationEndCondition associationEndCondition) {
    Collection result = new LinkedHashSet(19); // This structure keeps the order of the added classes, which makes the results possible to reproduce
    Classifier baseClass;
    Iterator classIterator;
    WrapClassifier type;
    Factory factory;

    factory = Factories.getFactory( Uml13WrapFactory.class );

    // collect the unique referred associated classes
    classIterator = startFromClasses.iterator();
    while ( classIterator.hasNext() ) {
      baseClass = (Classifier) classIterator.next();

      // add unique assoc ends from all subclasses in the result ordered collection
      type = (WrapClassifier) factory.wrap( baseClass );
      result.addAll( type.getAssociatedClasses( associationEndCondition ) );
    }
    return result;

  }


  /**
   * @return a condition to recognize only those model elements (Classifiers), that represent ANT TYPES, but are not used in the 
   *         tasks though their specific custom interfaces/superclasses. Thus the condition would succeed only on ANT type definitions, 
   *         that are for general use and are not task-specific ANT type definitions
   */
  public static ModelElementCondition<ModelElement> getAntStandardTypeCondition() {
    return new ModelElementCondition<ModelElement>( Factories.getFactory( Uml13WrapFactory.class ) ) {
      public boolean eval(ModelElement modelElement) {
        boolean result;
        Iterator superclassIterator;
        Iterator parameterIterator;
        Parameter parameter;
        ParameterClass parameterClass;
        Classifier superclass;

        result = modelElement instanceof Classifier;
                
        if ( result ) {
          parameterClass = (ParameterClass) WrapParameter.getClassProxy( modelElement.refOutermostPackage() );

          superclassIterator = ((WrapClassifier) wrap(modelElement)).getAllSuperclasses().iterator();
          while ( result && superclassIterator.hasNext() ) {
            superclass = (Classifier) superclassIterator.next();
            
            if ( ((WrapClassifier) wrap(superclass)).formatQualifiedName().indexOf( "org.apache.tools.ant." ) == -1 ) { // superclass is a custom type
              
              // check there is NO parameter of that supertype, so the type is a general purpose type
              parameterIterator = parameterClass.refAllOfClass().iterator();
              while ( result && parameterIterator.hasNext() ) {
                parameter = (Parameter) parameterIterator.next();
                
                if ( !parameter.getName().equals( "return" ) ) { // a formal input parameter of a method
                  result = parameter.getType() != superclass;
                }
              }              
            }
          }
        }
        return result;
      }
    };
  }
  
  
  /**
   * Builds a list of all subclasses of the class provided, including itself
   * @return non-null list of all subclasses that are direct or indirect subclasses of modelClass
   */
  public List getAllSubclasses() {
    List result = new ArrayList( 20 );
    Classifier classToCheck;
    GeneralizableElement subclass;
    Iterator generalizationIterator;

    int i = 0;

    // breadth-first search of all subclasses
    result.add( getWrapped() );
    while ( i < result.size() ) { // INVARIANT: result contains only unique UmlClass instances
                                  // processed up to i-1
      classToCheck = (Classifier) result.get( i++ );

      // check subclasses
      generalizationIterator = classToCheck.getSpecialization().iterator();
      while ( generalizationIterator.hasNext() ) {
        subclass = ((Generalization) generalizationIterator.next()).getChild(); // it might be not only class, but
                                                                                // association, classifier, etc.
        if ( subclass instanceof UmlClass && !result.contains( subclass ) ) { // not checked yet
          result.add( subclass ); // subclass added for investigation
        }
      }
    }
    return result;
  }

  /**
   * Builds a list of all superclasses of the class provided, including itself
   * @return a non-null list of superclasses extended directly or indirectly
   */
  public Collection getAllSuperclasses() {
    List result = new ArrayList( 20 );
    GeneralizableElement classToCheck;
    GeneralizableElement superclass;
    Iterator generalizationIterator;

    int i = 0;

    // breadth-first search of all subclasses
    result.add( getWrapped() );
    while ( i < result.size() ) { // INVARIANT: result contains only unique UmlClass instances
                                  // processed up to i-1
      classToCheck = (GeneralizableElement) result.get( i++ );

      // check superclasses
      generalizationIterator = classToCheck.getGeneralization().iterator();
      while ( generalizationIterator.hasNext() ) {
        superclass = ((Generalization) generalizationIterator.next()).getParent(); // it might be not only
                                                                                   // class, but association,
                                                                                   // classifier, etc.
        if ( // superclass instanceof UmlClass &&
             !result.contains( superclass ) ) { // not checked yet
          result.add( superclass ); // subclass added for investigation
        }
      }
    }
    return result;
  }

  /**
   * @return all attributes declared in the class
   */
  public Collection getAttributes() {
    List result;

    result = filter(getFeature(),
                    new ModelElementCondition<ModelElement>(getFactory()) {
                      public boolean eval(ModelElement modelElement) {
                        boolean result;
                        result = modelElement instanceof Attribute;
                        return result;
                      }
                    });
    return result;
  }

  /**
   * This method checks if thisClass has attributes.
   * @return true if thisClass has at least one attribute, which is not a static field / constant
   */
  public boolean hasAttributes() {
    boolean result;

    result = check( getFeature(),
                    new ModelElementCondition<ModelElement>(getFactory()) {
                      public boolean eval(ModelElement modelElement) {
                        return modelElement instanceof Attribute
                               && !((WrapAttribute) wrap(modelElement)).isConstant();
                      }
                    } );
    return result;
  }

  /**
   * Builds a list of all attributes of all subclasses of this. Unique attributes are
   * listed. Constants are not included.
   * <b>
   * This method actually does the Object-Relational Mapping of all attributes of this
   * class and its subclasses to a single list of attributes.<p/>
   * This mapping is suitable for the O/R Mapping of the class and all its subclasses (hierarchy)
   * to a single table policy.<p/>
   * Note that there are three more O/R Mapping policies clearly described bellow
   * </b>
   * @return a collection of all attributes of the listed classes
   * @see <a href="http://www.agiledata.org/essays/mappingObjects.html">Object-relational mapping strategies</a>
   * @see <a href="http://www.agiledata.org/essays/impedanceMismatch.html">Object-relational mapping in general</a>
   */
  public Collection getOrmAttributes() {
    return selectOrmAttributes(new AttributeKey() {
      public String getKey(Attribute attribute) {
        return ((WrapAttribute) WrapClassifier.this.getFactory().wrap(attribute)).formatFirstLowerName();
      }
    });
  }


  /**
   * @return a list of attributes in this class and its subclasses that are unique against the
   *         AttributeKey strategy to calculate their keys. Constants are not included.
   */
  private Collection selectOrmAttributes(AttributeKey keyConstructor) {
    ArrayList result = new ArrayList( 128 );
    Iterator classIt;
    UmlClass classToProcess;
    Iterator attribIt;
    Feature feature;
    Attribute existingAttrib;
    HashMap processed = new HashMap(); // a set of processd attributes names used to ensure
                                       // attribute uniqueness among the class hierarchy
    String key;

    classIt = getAllSubclasses().iterator();
    while ( classIt.hasNext() ) {
      classToProcess = (UmlClass) classIt.next();
      attribIt = ((WrapClassifier) wrap(classToProcess)).getAttributes().iterator();

      while ( attribIt.hasNext() ) {
        feature = (Feature) attribIt.next();

        if ( feature instanceof Attribute
             && !((WrapAttribute) wrap(feature)).isConstant() ) { // this is an attribute, but not
                                                                  // a field constant
          // check repeated names (rompating their DB column names)
          key = keyConstructor.getKey( (Attribute) feature );
          existingAttrib = (Attribute) processed.get( key );

          if ( existingAttrib == null ) { // unique attribute
            result.add( feature );
            processed.put( key, feature );

          } else if ( existingAttrib.getType().equals( ((Attribute) feature).getType() )
                      && existingAttrib.getTargetScope().equals( ((Attribute) feature).getTargetScope() ) ) { // same
                                                                                                              // name,
            // do nothing - the field already has been added                                                                                                              // type,
            LOGGER.log( Level.FINE,
                        "***  Skipped attribute {0}.{1} as already known",
                        new Object[]{classToProcess.getName(), feature.getName()});
                                                                                                              // scope
          } else { // same name, different type
            LOGGER.log( Level.SEVERE,
                        "***  Repeated attribute {0}.{1} with another type"
                        + " within the class hierarchy. Please provide a unique field name",
                        new Object[]{classToProcess.getName(), feature.getName()});
          }
        }
      }
    }
    return result;
  }

  /**
   * This interface represents the criteria to calculate the key of the attribute,
   * so only attributes with unique values of these keys could be selected
   */
  private abstract class AttributeKey {
    /**
     * @param attribute
     * @return the key
     */
    public abstract String getKey(Attribute attribute);
  }


  /**
   * Builds a list of all class opertaions (method specifications)- instances of Operation. Note
   * that the operation is a specification of several methods (see UML 1.3 Meta Model doc), so
   * consider usage of getMethods() instead.
   * @return a non-null list of Operation instances that represent the methods of umlClass provided
   */
  public Collection getMethods() {
    List result;

    result = filter(getFeature(),
                    new ModelElementCondition<ModelElement>(getFactory()) {
                      public boolean eval(ModelElement modelElement) {
                        return modelElement instanceof Method;
                      }
                    });
    return result;
  }

  /**
   * Builds a list of all class operations (method specifications)- instances of Operation. Note
   * that the operation is a specification of several methods (see UML 1.3 Meta Model doc), so
   * consider usage of getMethods() instead.
   * @return a non-null list of all method <b> specifications </b> of umlClass
   */
  public Collection getOperations() {
    List result;

    result = filter(getFeature(),
                    new ModelElementCondition<ModelElement>(getFactory()) {
                      public boolean eval(ModelElement modelElement) {
                        return modelElement instanceof Operation;
                      }
                    });
    return result;
  }

  /**
   * Retrieves the superclass of the class provided, that has no more superclasses
   * @return a non-list of all superclasses of modelClass that have no superclasses,
   *        i.e. all root superclasses
   */
  public UmlClass getRootSuperclass() {
    UmlClass result = ((UmlClass) getWrapped());
    HashSet visited = new HashSet( 7 );
    UmlClass superclass;

    GeneralizableElement classToCheck;
    Iterator generalizationIterator;

    do {
      // find a superclass
      superclass = null;

      generalizationIterator = result.getGeneralization().iterator();
      while ( superclass == null && generalizationIterator.hasNext() ) {
        classToCheck = ((Generalization) generalizationIterator.next()).getParent(); // it might be not only
                                                                                     // class, but association,
                                                                                     // classifier, etc.
        if ( classToCheck instanceof UmlClass
             && !visited.contains( classToCheck ) ) { // not checked yet
          visited.add( classToCheck ); // subclass added for investigation
          superclass = (UmlClass) classToCheck;
        }
      } // superclass == null if no superclass found or superclass is a super class of result

      if ( superclass != null ) { // a real superclass found
        result = superclass;
      }
    } while ( superclass != null );

    return result;
  }

  /**
   * Builds a list of the immediate subclasses of this
   * @return non-null list of all immediate subclasses of this
   */
  public Collection getSubclasses() {
    Set result = new LinkedHashSet( 19 );
    GeneralizableElement subclass;
    Iterator generalizationIterator;

    // check subclasses
    generalizationIterator = getSpecialization().iterator();
    while ( generalizationIterator.hasNext() ) {
      subclass = ((Generalization) generalizationIterator.next()).getChild(); // it might be not only class, but
                                                                              // association, classifier, etc.
      if ( subclass instanceof UmlClass ) {
        result.add( subclass );
      }
    }
    return result;
  }


  /**
   * Finds the direct superclass of this class
   * @return the direct superclass of this model class or null when no one exists
   */
  public UmlClass getSuperclass() {
    UmlClass result = null;
    Generalization mgen;
    GeneralizableElement mgenel;
    Iterator generalizationIterator;

    generalizationIterator = getGeneralization().iterator();
    while ( result == null && generalizationIterator.hasNext() ) {
      mgen = (Generalization) generalizationIterator.next();

      mgenel = mgen.getParent();
      if ( mgenel instanceof UmlClass ) {
        result = (UmlClass) mgenel;
      }
    }
    return result;
  }

  /**
   * Builds a list of all direct superclasses of the class provided
   * @return non-null list of direct superclasses (the interfaces are not included)
   */
  public List getSuperclasses() {
    List result = new ArrayList();
    Generalization mgen;
    GeneralizableElement mgenel;
    Iterator generalizationIterator;

    generalizationIterator = getGeneralization().iterator();
    while ( generalizationIterator.hasNext() ) {
      mgen = (Generalization) generalizationIterator.next();

      mgenel = mgen.getParent();
      if ( mgenel instanceof UmlClass ) {
        result.add( mgenel );
      }
    }
    return result;
  }

  /**
   * Builds a list of all direct interfaces the class provided implements
   * @return non-null list of all interfaces directly implemented by this model class
   */
  public List getSuperinterfaces() {
    List result = new ArrayList();
    Iterator generalizationIterator = getGeneralization().iterator();
    Generalization mgen;
    GeneralizableElement mgenel;

    while ( generalizationIterator.hasNext() ) {
      mgen = (Generalization) generalizationIterator.next();

      mgenel = mgen.getParent();
      if ( mgenel instanceof Interface ) {
        result.add( mgenel );
      }
    }
    return result;
  }

  /**
   * This method checks if thisClass has associated collections at the other end of its associations ends.
   * @return true if thisClass has at least one association, which other end is collection (X-TO-MANY association)
   */
  public boolean hasAssociatedCollections() {
    boolean result = false;
    AssociationEnd associationEnd;
    WrapAssociationEnd otherEnd;
    WrapAssociationEnd thisEnd;
    Iterator assocEndIterator;

    assocEndIterator = getAssociationEnds( getAssocisationToDomainCondition( getFactory() ) ).iterator();
    while ( assocEndIterator.hasNext() && !result ) {
      associationEnd = (AssociationEnd) assocEndIterator.next();

      thisEnd = (WrapAssociationEnd) wrap( associationEnd);
      otherEnd= (WrapAssociationEnd) wrap( thisEnd.getOtherAssociationEnd());

      result = thisEnd.isAssociationSupported()
               && !otherEnd.isSingular();  // Association X-TO-MANY instance at the other end
    }


    return result;
  }

  /**
   * @return true if this class is "void"
   */
  public boolean isVoid() {
    return "void".equals( getName() );
  }


  /**
   * Check if the wrapped model class is a subclass of another model class described
   * by its qualified name.
   * @param qualifiedTargetName is the qualified name of a model class
   * @return true if the wrapped model class is
   * @throws IllegalArgumentException
   */
  public boolean isSubclassOf(String qualifiedTargetName) throws IllegalArgumentException {
    boolean result;
    ModelElement targetSuperclass;

    targetSuperclass = ((WrapModel) Factories.wrap( getModel() )).locateQualifiedModelElement( qualifiedTargetName );

    if ( !(targetSuperclass instanceof Classifier) ) {
      throw new IllegalArgumentException("Expected "+qualifiedTargetName+" describes an instance of Classifier instead of: "+targetSuperclass);
    }

    // check if baseClass is amongst the superclasses/iterfaces of currentClassifier
    result = isSubclassOf( (Classifier) targetSuperclass );
    return result;
  }


  /**
   * @return true if this class has no superclass & it is marked as persistent.
   *         This way it can be an EJB 2.x
   */
  public boolean isPersistentClassesRoot() {
    boolean result;

    result = isPersistent()
             && listSuperclassesOf().size() <= 1;

    return result;
  }


  /**
   * INstantiates an association from the wrapped class(ifier) to the provided one with the
   * details provided.
   * @param otherClass
   * @param thisRole
   * @param isThisComposition
   * @param isThisNavigable
   * @param thisMultiplicity
   * @param otherRole
   * @param otherMultiplicity
   * @param documentation
   * @return a non-null association
   */
  public UmlAssociation createAssociation(Classifier otherClass,
                                          String thisRole,
                                          boolean isThisComposition,
                                          boolean isThisNavigable,
                                          int thisMultiplicity,
                                          String otherRole,
                                          int otherMultiplicity,
                                          String documentation) {
    UmlAssociation result;

    result = WrapUmlAssociation.instantiateAssociation( getWrapped(),
                                                        thisRole,
                                                        isThisComposition,
                                                        isThisNavigable,
                                                        (isThisComposition?1: thisMultiplicity),
                                                        otherClass,
                                                        otherRole,
                                                        otherMultiplicity,
                                                        getNamespace(),
                                                        documentation );
    return result;
  }

  /**
   * @return the first namespace this class is in, that is not a class. This
   *   way finding the package of the outer-most class
   */
  public Namespace getPackage() {
    Namespace result;

    result = getNamespace();
    while ( result instanceof Classifier ) {
      result = result.getNamespace();
    }
    return result;
  }

  /**
   * This method locates the feature with the name provided held in the classifier provided
   *
   * @param classifier is the classifier where to search in. It must not be null.
   * @param elementName is a non-empty string package name
   * @return null if no package found, otherwise the package with the name specified
   */
  public static ModelElement locateFeature(Classifier classifier, String elementName) {
    ModelElement result = null;
    ModelElement ownedElement;
    Iterator ownedElementsIterator;

    // select the collection of packages to search for
    ownedElementsIterator = classifier.getFeature().iterator();
    while ( result == null && ownedElementsIterator.hasNext() ) {
      ownedElement = (ModelElement) ownedElementsIterator.next();

      if ( elementName.equals( ownedElement.getName() ) ) {
        result = ownedElement;
      }
    }
    return result;
  }

  /**
   * @param superclass is the model superclass to search for amongst the superclasses of current
   * @return true if currentClassifier is the same of subclass of baseClass
   */
  public boolean isSubclassOf(Classifier superclass) {
    boolean result;
    Iterator superIterator;
    GeneralizableElement superClassifier;

    result = superclass == getWrapped();
    if ( !result ) {
      superIterator = getGeneralization().iterator();
      while ( !result
              && superIterator.hasNext() ) {
        superClassifier = ((Generalization)superIterator.next()).getParent();

        result = ((WrapClassifier) wrap(superClassifier)).isSubclassOf( superclass );
      }
    }
    return result;
  }

  /**
   * Default implementation for all Classifiers.
   * @return non-null list of classes that immediately implement this interface. Returns
   *         a list holding the wrapped classifier.
   */
  public Collection getImmediateImplementations() {
    List result = new ArrayList( 1 );
    result.add( getWrapped() );
    return result;
  }

}