/*
 * Copyright (c) 2010 Rusi Popov, MDA Tools.net
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Rusi Popov (popovr@mdatools.net) - initial implementation
 */
package org.apache.jasper.compiler;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import static net.mdatools.modelant.template.api.Convention.TEMPLATE_FILE_SUFFIX;;

/**
 * Extracted and simplified by Rusi Popov
 */
class Format {

  /**
   * All JAVA keywords not allowed as package names
   */
  private static final Set<String> KEYWORDS = new HashSet<>();
  static {
    KEYWORDS.add("abstract");
    KEYWORDS.add("boolean");
    KEYWORDS.add("break");
    KEYWORDS.add("byte");
    KEYWORDS.add("case");
    KEYWORDS.add("catch");
    KEYWORDS.add("char");
    KEYWORDS.add("class");
    KEYWORDS.add("const");
    KEYWORDS.add("continue");
    KEYWORDS.add("default");
    KEYWORDS.add("do");
    KEYWORDS.add("double");
    KEYWORDS.add("else");
    KEYWORDS.add("extends");
    KEYWORDS.add("final");
    KEYWORDS.add("finally");
    KEYWORDS.add("float");
    KEYWORDS.add("for");
    KEYWORDS.add("goto");
    KEYWORDS.add("if");
    KEYWORDS.add("implements");
    KEYWORDS.add("import");
    KEYWORDS.add("instanceof");
    KEYWORDS.add("int");
    KEYWORDS.add("interface");
    KEYWORDS.add("long");
    KEYWORDS.add("native");
    KEYWORDS.add("new");
    KEYWORDS.add("package");
    KEYWORDS.add("private");
    KEYWORDS.add("protected");
    KEYWORDS.add("public");
    KEYWORDS.add("return");
    KEYWORDS.add("short");
    KEYWORDS.add("static");
    KEYWORDS.add("super");
    KEYWORDS.add("switch");
    KEYWORDS.add("synchronized");
    KEYWORDS.add("this");
    KEYWORDS.add("throw");
    KEYWORDS.add("throws");
    KEYWORDS.add("transient");
    KEYWORDS.add("try");
    KEYWORDS.add("void");
    KEYWORDS.add("volatile");
    KEYWORDS.add("while");
  }
  private static final String TRANSLATED_TEMPLATE_FILE_SUFFIX = ".java";

  /**
   * A prefix to the generated package names for ModelAnt Templates when converted to java,
   * in order to avoid standard package names that are forbidden for custom class loaders
   */
  private final String packagePrefix;

  /**
   * @param uniquePackagePrefix not null, not empty unique package name to add to any template package names
   *        in the templates set to process, in order to make them unique among all other
   *        template sets.
   */
  public Format(String uniquePackagePrefix) {
    StringBuilder packageName;

    if ( uniquePackagePrefix == null || uniquePackagePrefix.isEmpty() ) {
      throw new IllegalArgumentException("Expected not null, not empty unique prefix for template package names");
    }
    packageName = new StringBuilder(128);
    toPackage( new File( uniquePackagePrefix ), packageName );

    packagePrefix = packageName.toString();
  }

  /**
   * @param templateFile not null relative template file, where the directories form the package name
   * @return a package name, consisting of the unique context name and the directories of the template file
   */
  public final String formatPackageName(File templateFile) {
    StringBuilder result;

    result = new StringBuilder(256);

    result.append( packagePrefix );
    toPackage( templateFile.getParentFile(),
               result );

    return result.toString();
  }

  /**
   * @param templateFile not null relative template file, where the directories form the package name
   * @return a template class name as the file name
   */
  public final String formatSimpleClassName(File templateFile) {
    String name;

    name = removeExtension( templateFile.getName() );

    return formatClassName( name );
  }

  /**
   * @param parentClassDirectory not null relative directory path, representing the (parent) class
   * @return a qualified (parent) class name
   */
  public final String formatQualifiedClassName(File parentClassDirectory) {
    String result;

    result = parentClassDirectory.getPath().replace( File.separatorChar, '.' );

    return result;
  }

  /**
   * Format the provided name suitable to be class name - no keywords, letters and digits only,
   * though the naming conventions are not applied, neither checking if the
   *
   * @param name not null, not empty
   * @return format the
   */
  private String formatClassName(String name) {
    StringBuilder result = new StringBuilder(256);

    if ( KEYWORDS.contains( name ) ) { // the name is a keyword - make it not a keyword
      result.append( '_' ).append( name );
    } else {
      convertClassNameToAlphaNumeric( name, result );
    }
    return result.toString();
  }

  /**
   * @param name not null simple file name, optionally containing an extension
   * @return not null, not empty file name without any extensions
   */
  private static String removeExtension(String name) {
    int dotIndex;

    dotIndex = name.indexOf( '.' );
    if ( dotIndex == 0 ) { // actually no name provided - just extensions
      throw new IllegalArgumentException("Expected a non-empty file name in "+name);

    } else if ( dotIndex > 0 ) {
      name = name.substring( 0, dotIndex );
    }
    return name;
  }

  /**
   * @param packageName = formatPackageName(f)
   * @param className = formatClassName(f)
   * @return relative file name of the java file to translate the template to
   */
  public String formatJavaFileName(String packageName, String className) {
    StringBuilder result;

    result = new StringBuilder(128);

    result.append( packageName.toString().replace( '.', File.separatorChar ) )
          .append( File.separator )
          .append( className )
          .append( TRANSLATED_TEMPLATE_FILE_SUFFIX );

    return result.toString();
  }

  public String formatQualifiedTemplateClassName(Class<?> targetClass, String templateName) {
    StringBuilder result;

    result = new StringBuilder(128);

    result.append( packagePrefix )
          .append( "." )
          .append( targetClass.getName().toLowerCase() )
          .append( "." )
          .append( formatClassName( templateName ) );

    return result.toString();
  }

  /**
   * The source name DOES NOT include the unique name of the context
   * @param targetClass not null
   * @param templateName not null, not empty
   * @return the name of the .java file where the template is expected to be translated to Java
   */
  public File formatTemplateSourceFile(Class<?> targetClass, String templateName) {
    StringBuilder result;

    result = new StringBuilder(128);

    result.append( targetClass.getName().replace( ".", File.separator) )
          .append( File.separatorChar )
          .append( formatClassName( templateName ) )
          .append( TEMPLATE_FILE_SUFFIX );

    return new File( result.toString() );
  }

  /**
   * Construct a Java package name from the directory path provided.
   * Any keywords are replaced
   * @param directory is a <b>relative directory</b> (as a File). null indicates the
   *        default package
   * @param result non-null where to store the produced package name
   */
  private static void toPackage(File directory, StringBuilder result) {
    File parent;
    String name;

    if ( directory != null ) { // non-default directory
      parent = directory.getParentFile();

      // construct into result the outer package name
      toPackage( parent, result );
      if ( result.length() > 0 ) {
        result.append( '.' );
      }

      // construct into result this package name
      name = directory.getName();
      if ( KEYWORDS.contains( name ) ) { // the name is a keyword - make it not a keyword
        result.append( '_' ).append( name );

      } else { // the name is not a keyword - apply the general conversion
        convertPackageNameToAlphaNumeric( result, name );
      }
    }
  }

  /**
   * @param result not null where to store the produced package name, consisting only of digits, letters and _
   * @param name not null, containing any characters
   */
  private static void convertPackageNameToAlphaNumeric(StringBuilder result, String name) {
    char charAt;
    for (int i = 0; i < name.length(); i++) {
      charAt = name.charAt( i );

      if ( Character.isLetterOrDigit( charAt )) {
        result.append( Character.toLowerCase( charAt ) );

      } else if (  charAt == '.'  ) { // . -> _
        result.append( '_' );

      } else {
        result.append( toAlphaNumeric( charAt ) );
      }
    }
  }

  /**
   * @param name not null, containing any characters
   * @param result not null where to store the produced class name, consisting only of digits and letters,
   *       not starting with a
   */
  private static void convertClassNameToAlphaNumeric(String name, StringBuilder result) {
    char charAt;

    for (int i = 0; i < name.length(); i++) {
      charAt = name.charAt( i );

      if ( i== 0 ) { // start char
        if ( Character.isLetter( charAt )) {
          result.append( charAt );

        } else {
          result.append( "_" );

          if (Character.isDigit( charAt ) ) { // starting with a digit
            result.append( charAt );
          } else {
            result.append( toAlphaNumeric( charAt ) );
          }
        }
      } else { // any other char
        if ( Character.isLetterOrDigit( charAt )) {
          result.append( charAt );
        } else {
          result.append( toAlphaNumeric( charAt ) );
        }
      }
    }
  }

  /**
   * @param ch
   * @return the HEX representation of the character ch in 5 digits starting with 0
   */
  private static String toAlphaNumeric(char ch) {
    String result;

    result = "0000"+ Integer.toHexString( ch );
    result = result.substring( result.length() - 4 );

    return result;
  }
}