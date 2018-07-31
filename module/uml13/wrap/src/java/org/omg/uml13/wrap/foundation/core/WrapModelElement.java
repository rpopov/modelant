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
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import javax.jmi.reflect.RefPackage;

import net.mdatools.modelant.core.wrap.Factories;
import net.mdatools.modelant.core.wrap.Factory;
import net.mdatools.modelant.uml13.metamodel.Convention;
import net.mdatools.modelant.util.FormatHelper;
import net.mdatools.modelant.util.UniqueNamesGenerator;

import org.omg.uml13.foundation.core.Comment;
import org.omg.uml13.foundation.core.Dependency;
import org.omg.uml13.foundation.core.ModelElement;
import org.omg.uml13.foundation.core.Namespace;
import org.omg.uml13.foundation.extensionmechanisms.Stereotype;
import org.omg.uml13.foundation.extensionmechanisms.TaggedValue;
import org.omg.uml13.modelmanagement.Model;
import org.omg.uml13.wrap.Uml13WrapFactory;
import org.omg.uml13.wrap.base.foundation.core.BaseWrapModelElement;
import org.omg.uml13.wrap.foundation.extensionmechanisms.WrapTaggedValue;
import org.omg.uml13.wrap.modelmanagement.WrapModel;

/**
 * This is a wrapper of org.omg.uml13.foundation.core.ModelElement that allows adding specific
 * custom methods and using them as templates for [code] generation.
 */
public class WrapModelElement extends BaseWrapModelElement {

  private static UniqueNamesGenerator uniqueNames = new UniqueNamesGenerator();

  /**
   * Caches the name of this formatted as of a java constant name. Lazily initialized by formatConstantName()
   * @see #formatAllCapitalName()
   */
  private String nameAllCaps;

  /**
   * Caches the name of this formatted as of a java constant name. Lazily initialized by formatConstantName()
   * @see #formatAllCapitalName()
   */
  private String nameAllLower;

  /**
   * Caches the name of this formatted as of a java field name. Lazily initialized by formatFieldName()
   * @see #formatFirstLowerName()
   */
  private String nameFirstLower;

  /**
   * Caches the name of this formatted as of a java class name. Lazily initialized by formatFirstCapitalName()
   * @see #formatFirstCapitalName()
   */
  private String nameFirstCap;

  /**
   * The cached qualified name of this model element
   * @see #formatQualifiedName()
   */
  private String qualifiedName;

  /**
   * @param wrapped
   * @param factory
   */
  public WrapModelElement(java.lang.Object wrapped, Factory factory) {
    super( wrapped, factory );
  }

  /**
   * @param elements is not null collection of model element wrappers
   * @param condition is a condition over the elements collection. null means True
   * @return a non-null collection of model elements (instances of UML 1.3 metamodel) of the original collection,
   *         that satisfy the condition provided
   */
  public static final <X extends ModelElement> List<X> filter(Collection<X> elements, ModelElementCondition<X> condition) {
    List<X> result;
    Iterator<X> elementIterator;
    X element;

    result = new ArrayList<X>();
    if ( condition == null  ) {
      result.addAll(elements);
      
    } else {
      elementIterator = elements.iterator();
      while ( elementIterator.hasNext() ) {
        element = elementIterator.next();
  
        if ( condition.eval( element ) ) {
          result.add( element );
        }
      }
    }
    return result;
  }


  /**
   * @param elements is not null collection of model element wrappers
   * @param condition is a condition over the elements collection. null means True
   * @return true if there is at least one model element among the elements that satisfies the condition.  
   */
  public static final boolean check(Collection<ModelElement> elements, 
                                    ModelElementCondition<ModelElement> condition) {
    boolean result;
    Iterator<ModelElement> elementIterator;
    ModelElement element;

    if ( condition == null  ) {
      result = true;
      
    } else {
      result = false;
      
      elementIterator = elements.iterator();
      while ( !result && elementIterator.hasNext() ) {
        element = elementIterator.next();       
        result = condition.eval( element );
      }
    }
    return result;
  }


  /**
   * @return true if the class is defined in java or javax package, so it is
   *         treated as standard
   */
  public boolean isJavaStandardClass() {
    boolean result;

    result = getQualifiedName().startsWith( "java." )
             || getQualifiedName().startsWith( "javax." );
    return result;
  }

  /**
   * @return true if the class is defined in (a subpackage of) the domain model package
   */
  public boolean isDomainClass() {
    boolean result;

    result = getQualifiedName().indexOf( "."+Convention.MODEL_PACKAGE_DOMAIN+"." ) > -1;

    return result;
  }

  /**
   * @param stereotypeName is the name of the stereotype to check. If empty or null string provided,
   *        it is treated as no stereotype assigned.
   * @return true if the wrapped model element has a s stereotype with the name provided, where if the
   *         name is empty or null, this checks if there is no stereotype assigned
   */
  public final boolean isOfStereotype(String stereotypeName) {
    boolean result;
    Stereotype stereotype;

    stereotype = getStereotype();

    if ( stereotypeName == null
         || stereotypeName.trim().length() == 0 ) { // check no stereotype provided
      result = stereotype == null
               || stereotype.getName() == null
               || stereotype.getName().trim().length() == 0;

    } else {
      result = stereotype != null
               && stereotype.getName() != null
               && stereotype.getName().equals( stereotypeName );
    }
    return result;
  }
  /**
   * This method finds the Stereotype instances associated with the ModelElement instance.
   *
   * @return the stereotype found, if none found it returns null
   */
  public final Stereotype getStereotype() {
    return getFactory().locateStereotype( getWrapped() );
  }


  /**
   * @return the name of the near-most namespace of this class
   */
  public final String getNamespaceName() {
    return getNamespace().getName();
  }

  /**
   * Retrieves the documentation bound to this model element. Note that it is provided as a tagged
   * value, so the tag name is not standartized and may vary between the UML GUI tools. Here
   * "documentation" tag name is used. If no documentation is found the null value is returned.
   * @return a string of all documentation
   */
  public final String getDocumentation() {
    StringBuilder result = new StringBuilder();
    String content;
    Iterator<Comment> commentsIterator;
    TaggedValue tag;

    tag = getTaggedValue( Convention.TAG_VALUE_DOCUMENTATION );
    if ( tag != null && tag.getValue() != null ) {
      result.append( tag.getValue() );
    }

    // collect all comments as a single string
    commentsIterator = getComment().iterator();
    while ( commentsIterator.hasNext() ) {
      content = commentsIterator.next().getName().trim(); // assumption: name() != null
      if ( !content.equals("") ) {
        result.append( content );
        result.append( "\r\n" );
      }
    }
    return result.toString();
  }


  /**
   * The history tagged value bound to this model element
   * @return null if no value bound
   */
  public String getHistory() {
    String result;
    TaggedValue tag;

    tag = getTaggedValue( Convention.TAG_VALUE_HISTORY);
    if ( tag != null ) {
      result = tag.getValue();
    } else {
      result = null;
    }
    return result;
  }

  /**
   * This method adds a new line to the model element's history. The model element's history is represented
   * as TAG_VALUE_HISTORY tagged value and can be accessed through locateTagHistory() method. No explict creation
   * of this tag is needed - the method handles it. This method allows allows tracking transformations for each model
   * element.
   * @param line the original qualified name
   */
  public void addTagHistory( String line) {
    TaggedValue history;
    String previousHistory;

    history = getTaggedValue( Convention.TAG_VALUE_HISTORY );
    if ( history == null ) {
      history = WrapTaggedValue.instantiateTagHistory(getWrapped());
    }
    if ( line != null && line.trim().length() > 0 ) {
      previousHistory = history.getValue();

      // add the new record / line
      if ( !previousHistory.equals("") ) {
        previousHistory += "\r\n";
      }
      previousHistory += line;
      history.setValue( previousHistory );
    }
  }

  /**
   * @param tagName a non-null tag name
   * @return the tag with the name bound to this model element
   */
  public final TaggedValue getTaggedValue(String tagName) {
    TaggedValue result = null;
    TaggedValue tag;
    Iterator<TaggedValue> sourceIterator;

    sourceIterator = getTaggedValues().iterator();
    while ( result == null && sourceIterator.hasNext() ) {
      tag = sourceIterator.next();

      if ( tagName.equals( tag.getTag() ) ) {
        result = tag;
      }
    }
    return result;
  }

  /**
   * @param tagName a non-null tag name
   * @return the value of the tag bound to this model element or null
   */
  public final String getTaggedValueString(String tagName) {
    String result;
    TaggedValue tag;
    
    tag = getTaggedValue( tagName );
    if ( tag == null ) {
      result = null;
    } else {
      result = tag.getValue();
    }
    return result;
  }


  /**
   * @param tagValueName the name of the tag value to check
   * @return true if there is a tag value value assigned to this model element and
   *         its value is "true"
   */
  public final boolean isBooleanTagSet(String tagValueName) {
    boolean result;
    TaggedValue tag;

    tag = getTaggedValue( tagValueName );
    result = tag != null
             && tag.getValue() != null
             && tag.getValue().equalsIgnoreCase("true");
    return result;
  }


  /**
   * Retrieves the persistence property bound to this model element.
   * Note that it is provided as a tagged value, so the tag name is not standartized and may vary between
   * the UML GUI tools. Here "persistence" tag name is used.
   * @return true if the model element is marked as "persistent"
   */
  public boolean isPersistent() {
    boolean result;
    TaggedValue tag;

    tag = getTaggedValue( Convention.TAG_VALUE_PERSISTENCE );

    result = tag != null
             && tag.getValue() != null
             && tag.getValue().equals(Convention.TAG_VALUE_PERSISTENCE_VALUE);
    return result;
  }


  /**
   * @return formats the name of this attribute as of Java constant name -
   *         all words are in capital letters, the words are separated with "_"
   */
  public final String formatAllCapitalName() {
    if ( nameAllCaps == null ) {
      nameAllCaps = FormatHelper.formatConstantName( getLogicalName() );
    }
    return nameAllCaps;
  }

  /**
   * @return formats the name of this attribute as of Java package name
   */
  public final String formatAllLowerName() {
    if ( nameAllLower == null ) {
      nameAllLower = FormatHelper.formatAllLower( getLogicalName() );
    }
    return nameAllLower;
  }

  /**
   * @return formats the name of this attribute as a Java field name with the first
   *         letter in lower case, any other first name - in upper case
   */
  public final String formatFirstLowerName() {
    if ( nameFirstLower == null ) {
      nameFirstLower = FormatHelper.formatFirstLower( getLogicalName() );
    }
    return nameFirstLower;
  }

  /**
   * @return formats the name of this attribute as a Java class name - all
   *         first letters of contained names are in upper case
   */
  public final String formatFirstCapitalName() {
    if ( nameFirstCap == null ) {
      nameFirstCap = FormatHelper.formatClassName( getLogicalName() );
    }
    return nameFirstCap;
  }

  /**
   * @return the logical name of this model element, formatted as of its Java meaning.
   *         Note that in some cases this name is other than the name in the model. For
   *         example, in Rose the primitive data types might include extra details in the name.
   */
  public String getLogicalName() {
    return translate( getName() );
  }

  /**
   * @return the name of this model element formatted in the rules of java qualified class name.
   *         It consists of &lt;qualified namespace name&gt;.&lt;logical name of this element&gt;
   *         The java standard classes are not formatted
   */
  public String formatQualifiedName() {
    String result;

    if ( isJavaStandardClass() ) {
      // skip formatting the names of the standard classes - they might not follow the conventions
      result = getName();
    } else {
      result = getQualifiedName();
    }
    return result;
  }

  /**
   * @return the qualified name of this model element as they are located
   *         in the actual model
   */
  private String getQualifiedName() {
    if ( qualifiedName == null ) {
      qualifiedName = constructQualifiedNameAsString( getQualifiedNameList() );       
    }
    return qualifiedName;
  }

  
  /**
   * @param qualifiedNameAsList is a non-null list of names representing a qualified name 
   * @return formatted as a string name the qualified name the parameter is 
   */
  private String constructQualifiedNameAsString(List<String> qualifiedNameAsList) {
    StringBuilder result;
    
    result = new StringBuilder( 256 );
    
    for (String name : qualifiedNameAsList ) {
      if ( result.length() > 0 ) {
        result.append( "." );
      }
      result.append( name );
    }
    return result.toString();
  }
  
  /**
   * @return a non-null list with the simple names forming the qualified name of this model element as they are located
   *         in the actual model
   */
  public List<String> getQualifiedNameList() {
    List<String> result;
    Namespace umlPackage;
    
    umlPackage = getNamespace();
    if ( umlPackage != null ) {
      result = ((WrapModelElement) wrap(getNamespace())).getQualifiedNameList();
    } else {
      result = new ArrayList<String>();
    }
    result.add( getLogicalName() );
    return result;
  }
  
  
  /**
   * PRE-CONDITION:
   *   This model element DOES NOT REPRESENT a Java standard class
   *   This is a domain class
   * @return the corresponding qualified package name
   */
  public String formatPackageName() {
    return formatQualifiedNamespaceName();
  }

  /**
   * @return the non-null qualified name of the package this model element is in.
   * @see #formatQualifiedName()
   */
  public String formatQualifiedNamespaceName() {
    String result;
    Namespace umlPackage;

    umlPackage = getNamespace();
    if ( umlPackage != null ) {
      result = ((WrapModelElement) wrap( umlPackage )).formatQualifiedName();
    } else {
      result = "";
    }
    return result;
  }

  /**
   * PRE-CONDITION:
   *   This model element DOES NOT REPRESENT a Java standard class
   * @return the corresponding base package name
   */
  public String formatBasePackageName() {
    String result;
    List<String> listQualifiedName;
    Iterator<String> namesIterator;
    int i;
    
    listQualifiedName = ((WrapModelElement) wrap( getNamespace() )).getQualifiedNameList();

    // insert "base" before the first (lowest index) name of a package that allows base package
    i = 0;
    namesIterator = listQualifiedName.listIterator();
    while ( namesIterator.hasNext() ) {
      if ( isBasePackageAllowedName( namesIterator.next() ) ) {
        listQualifiedName.add(i, "base");
        break;
      }
      i++;
    }
    result = constructQualifiedNameAsString( listQualifiedName );
    return result;
  }
  
  /**
   * @param name
   * @return true if the name provided is described in the global properties as allowing/requiring a base package 
   */
  private boolean isBasePackageAllowedName(String name) {
    boolean result;
    
    result = getFactory().getProperty( Convention.PREFIX_BASE_PACKAGE_ALLOWED_NAME+name) != null;
        
    return result; 
  }
  
  /**
   * This method generates a unique name out of the name provided
   * @param result is non-null
   * @return a non-null unique name
   */
  public final String getUniqueName(String result) {
    result = uniqueNames.getUnique( result );
    return result;
  }

  /**
   * Tests if the current model element has assigned the tag with the name and value
   * provided.
   *<p/>
   * Attributes:<ul>
   * <li> tag holds the name of the tag to check for
   * <li> value is is the tag value to check for. When empty or not provided, then
   *      this condition checks that the TAG IS NOT ASSIGNED a value or it is empty.
   *      When it is non-empty, then this method checks for exact match of tag name and value
 * </ul>
   * @param tag
   * @param value
   * @return true if there is a tag with exact name and value or there is no tag with that name
   */
  public boolean hasTag(String tag, String value) {
    boolean result;
    TaggedValue actualTag;

    actualTag = getTaggedValue( tag );

    if ( value == null || value.isEmpty() ) { // checking for NOT ASSIGNED tag
      result = actualTag == null
               || actualTag.getValue() == null
               || actualTag.getValue().isEmpty();

    } else { // searching for exact values match
      result = actualTag != null
               && value.equals( actualTag.getValue() );
    }
    return result;
  }

  /**
   * @return a non-null collection of all tags bound to the wrapped model element
   */
  public Collection<TaggedValue> getTaggedValues() {
    Collection<TaggedValue> result;

    result = getFactory().locateTaggedValues( getWrapped() );

    return result;
  }

  /**
   * This method adds a documentation tagged value with the text provided
   * @param text
   */
  public void addComment(String text) {
    TaggedValue documentation;

    documentation = getTaggedValue( Convention.TAG_VALUE_DOCUMENTATION);
    if ( documentation == null ) {
      WrapTaggedValue.instantiateTagDocumentation( getWrapped(), text);

    } else if ( documentation.getValue().indexOf( text ) < 0 ) {
      documentation.setValue( documentation.getValue()+"\n\r"+text);
    }
  }


  /**
   * Retrieves the [only] model in the extent of the model element wrapped
   * @return a non-null model instance
   * @throws NoSuchElementException if there is [still] no Model in the extent
   */
  public final Model getModel() throws NoSuchElementException {
    Model result;

    result = WrapModel.getModel( getExtent() );

    return result;
  }

  /**
   * @return the extent of the wrapped object
   */
  public final RefPackage getExtent() {
    return refOutermostPackage();
  }


  /**
   * This method removes all tagged values associated with this model element
   */
  public void removeTaggedValues() {
    TaggedValue tag;
    List<TaggedValue> tagsToRemove;
    Iterator<TaggedValue> sourceIterator;

    tagsToRemove = new ArrayList<TaggedValue>(getTaggedValues());

    // remove the tags themselves
    sourceIterator = tagsToRemove.iterator();
    while ( sourceIterator.hasNext() ) {
      tag = sourceIterator.next();
      sourceIterator.remove();
      tag.refDelete();
    }
  }


  /**
   * This method removes all Dependency instances associated to the model element provided either as a client or supplier.
   */
  public void removeDepenencies() {
    Iterator dependencyIterator;
    Dependency dependency;

    // remove the dependencies this model element is a client in
    dependencyIterator = getClientDependency().iterator();
    while ( dependencyIterator.hasNext() ) {
      dependency = (Dependency) dependencyIterator.next();
      dependencyIterator.remove();
      dependency.getClient().remove( getWrapped() );

      if ( dependency.getClient().isEmpty() ) { // no more clients exist - this is an empty dependency
        dependency.refDelete();
      }
    }

    // remove the dependencies this model element is a supplier of
    dependencyIterator = getClientDependency().iterator();
    while ( dependencyIterator.hasNext() ) {
      dependency = (Dependency) dependencyIterator.next();
      dependencyIterator.remove();
      dependency.getSupplier().remove( getWrapped() );

      if ( dependency.getSupplier().isEmpty() ) { // no more suppliers exist - this is an empty dependency
        dependency.refDelete();
      }
    }
  }


  /**
   * This method changes its parameter list by sorting model elements in it by their names
   * @param wrappedModelElements is a collection of wrapped model elements 
   */
  public static void sortByName(List<ModelElement> wrappedModelElements) {
    final Factory factory;
    
    factory = Factories.getFactory( Uml13WrapFactory.class );
    
    // sort the classes by simple name
    Collections.sort(wrappedModelElements, new Comparator<ModelElement>() {
         public int compare(ModelElement o1, ModelElement o2) {
            String name1 = ((WrapModelElement) factory.wrap(o1)).formatFirstCapitalName();
            String name2 = ((WrapModelElement) factory.wrap(o2)).formatFirstCapitalName();

            return name1.compareTo(name2);
         }
         public boolean equals(ModelElement o1, ModelElement o2) {
           return o1 == o2;
         }
      });
  }

  /**
   * This method changes its parameter list by sorting model elements in it by their qualified names
   * @param wrappedModelElements is a collection of wrapped model elements 
   */
  public static void sortByQualifiedName(List<ModelElement> wrappedModelElements) {
    final Factory factory;
    
    factory = Factories.getFactory( Uml13WrapFactory.class );
    
    // sort the classes by simple name
    Collections.sort(wrappedModelElements, new Comparator<ModelElement>() {
         public int compare(ModelElement o1, ModelElement o2) {
            String name1 = ((WrapModelElement) factory.wrap(o1)).formatQualifiedName();
            String name2 = ((WrapModelElement) factory.wrap(o2)).formatQualifiedName();

            return name1.compareTo(name2);
         }

         public boolean equals(ModelElement o1, ModelElement o2) {
           return o1 == o2;
         }
      });
  }
  
  /**
   * @param singleForm
   * @return the known plural form of the single form text provided
   */
  public final String getPluralForm(String singleForm) {
    return translatePlural( singleForm );
  }
}