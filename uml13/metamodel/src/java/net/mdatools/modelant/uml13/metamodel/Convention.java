/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.uml13.metamodel;


/**
 * All model specific naming conventions
 * @author Rusi Popov (popovr@mdatools.net)
 */
public interface Convention {

  /**
   * The prefix that indicates in the .properties file a simple name of a package, that to be excluded
   * from the package name construction. Note that some tools impose such common package prefixes, even
   * though they are not needed by UML 1.3 standard
   * <pre>
   *   Format ::= 'EXCLUDED_PACKAGE_NAME.'&lt;simple package name&gt;
   * </pre>
   */
  String PREFIX_EXCLUDED_PACKAGE_NAME = "EXCLUDED_PACKAGE_NAME.";

  /**
   * The prefix that indicates in the .properties file a simple name of a package, that needs 'base' package
   * generation when used in code generation. The 'base' packages and classes effectively split the manually
   * written code from the generated (the 'base' one).
   * <pre>
   *   Format ::= 'BASE_PACKAGE_ALLOWED.'&lt;simple package name&gt;
   * </pre>
   */
  String PREFIX_BASE_PACKAGE_ALLOWED_NAME = "BASE_PACKAGE_ALLOWED.";


  /**
   * The prefix that indicates in the .properties file the Java [primitive] types mapping to wrapper classes
   * <pre>
   * Format ::= 'MAP_PRIMITIVE_TYPE_TO_WRAPPER_CLASS.'&lt;type&gt; '=' &lt;class&gt;
   * </pre>
   */
  String PREFIX_MAP_PRIMITIVE_TYPE_TO_WRAPPER_CLASS = "MAP_PRIMITIVE_TYPE_TO_WRAPPER_CLASS.";

  /**
   * The name of the UML stereotype that represents the "throws" relation  between and operation and an exception.
   */
  String STEREOTYPE_THROWS = "throws";

  /**
   * The name of the UML stereotype that represents interfaces when they are represented as classes, because
   * of the Java specifics, like attributes (constants) in interfaces, which are incompatible with UML interfaces.
   */
  String STEREOTYPE_INTERFACE = "Interface";

  /**
   * The tag name to bind the name of the reverse engineered name
   */
  String TAG_FILE_NAME = "file.name";

  /**
   * The name of a tagged value with the comments/remarks/documentation found for the model elements
   * created. The tagged value contains non-empty comment string only. The tagged value is optional.
   * The name selected is compatible with the Rose's documenattion.
   */
  String TAG_VALUE_DOCUMENTATION = "documentation";

  /**
   * The name of the tagged value indicating a class or attribute is persistent. This name is
   * compatible with Rational Rose
   */
  String TAG_VALUE_PERSISTENCE = "persistence";

  /**
   * The value of a tagged value that indicates that a model element is persistent. This value is
   * compatible with Rational Rose
   *
   * @see #TAG_VALUE_PERSISTENCE
   */
  String TAG_VALUE_PERSISTENCE_VALUE = "persistent";

  /**
   * The value of a tagged value that indicates that a model element is NOT persistent. This value
   * is compatible with Rational Rose
   *
   * @see #TAG_VALUE_PERSISTENCE
   */
  String TAG_VALUE_TRANSIENT_VALUE = "transient";

  /**
   * The name of a tagged value that indicates if an attribute/column pertains to the table's
   * primary key. It is bound to the sequence order of the column in the promary key. This tagged
   * value is compatible with Rose's Data Modeler edition.
   */
  String TAG_VALUE_PRIMARY_KEY = "RationalRose$Data Modeler:ConstraintType";

  /**
   * The name of a tagged value that indicates if an attribute/column pertains to the table's
   * primary key. It is bound to the sequence order of the column in the promary key. This value is
   * compatible with Rose's Data Modeler edition.
   */
  String TAG_VALUE_PRIMARY_KEY_VALUE = "Primary Key";

  /**
   * The name of a tagged value this process binds to classes to indicate their original qualified name
   */
  String TAG_VALUE_HISTORY = "history";

  /**
   * The name of a tagged value this process binds to the Data Types created. The tagged value
   * contains the precision of the concrete numeric type. This tagged value is not compatible with
   * Rational Rose. It follows the conventions of vegas reverse engineering. // "RationalRose$Data
   * Modeler:Scale"; // This tagged value is compatible with Rose's Data Modeler edition.
   */
  String TAG_VALUE_DATA_TYPE_PRECISION = "scale";

  /**
   * The name of an Attribute tagged value binding an int that states the maximum size of the values of that attribute
   */
  String TAG_VALUE_DATA_LENGTH = "length";

  /**
   * CONVENTION
   * The name of the model package where all domain packages and classes are
   * NOTE: use it surrounded with "." in order to avoid names collision
   */
  String MODEL_PACKAGE_DOMAIN = "domain";

  /**
   * The name of the model parameter that describes a method's result
   */
  String RETURN_PARAMETER_NAME = "return";

  /**
   * This constant indicates unlimited int values (or not specified ones).
   */
  int UNLIMITED = -1;

  /**
   * This constant indicates "*" upper bound in multiplicity ranges.
   */
  int UNLIMITED_UPPER_MULTIPLICITY = UNLIMITED;

  /**
   * The <code>DEFAULT_MODEL_NAME</code> field holds the default value of OPTION_MODEL_NAME
   */
  String RESULT_MODEL_NAME = "model";
}