/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.uml14.reverse;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jmi.reflect.RefPackage;

import org.omg.uml14.core.Attribute;
import org.omg.uml14.core.DataType;
import org.omg.uml14.core.UmlClass;
import org.omg.uml14.datatypes.Expression;
import org.omg.uml14.datatypes.VisibilityKindEnum;

import net.mdatools.modelant.core.api.Function;
import net.mdatools.modelant.repository.api.ModelFactory;
import net.mdatools.modelant.repository.api.ModelRepository;

/**
 * Reverse engineering logic for database schemas and storing the results as
 * UML 1.3 objects. The model produced is in fact a Platform Specific Model, which might need
 * additional processing and tuning.
 * Conventions for the model produced:
 * <ol>
 * <li>The database column types are converted to DataType instances named: &lt;type
 * name&gt;[_&lt;column size&gt;[_&lt;column precision&gt;]]. Additionally as tagged values named
 * {@link net.mdatools.modelant.uml13.metamodel.Convention.TAG_VALUE_DATA_LENGTH} and {@link net.mdatools.modelant.uml13.metamodel.Convention.TAG_VALUE_DATA_TYPE_PRECISION}
 * these values are bound to the concrete data type.
 * <li>The {@link net.mdatools.modelant.uml13.metamodel.Convention.TAG_VALUE_DATA_TYPE_PRECISION} tagged value is optional. When not provided, the precision
 * should be treated as 0
 * <li>The {@link net.mdatools.modelant.uml13.metamodel.Convention.TAG_VALUE_DATA_LENGTH} tagged value is mandatory.
 * <li>Any comments found while reverse engineering the database are bound as 'documentation' tagged
 * values. These tagged values are compatible with the Rose's approach to documentation. They are
 * optional.
 * <li>Each attribute pertaining to the table's primary key is bound a {@link net.mdatools.modelant.uml13.metamodel.Convention.TAG_VALUE_PRIMARY_KEY} tagged value
 * with "Primaty Key" value. Its value is the sequence order of the column in the tible's primary key.
 * </ol>
 *
 * @author Rusi Popov (popovr@mdatools.net)
 */
public class ReverseDatabaseOperation implements Function<Connection, RefPackage>{

  private static final Logger LOGGER = Logger.getLogger( ReverseDatabaseOperation.class.getName() );

  private static final String LEGAL_TABLE_NAME_REGEX = "^[a-z$#A-Z0-9_]+$";

  /**
   * Column name that contains the decimal digits for the column in the result set that describes a
   * column in a table
   *
   * @see java.sql.DatabaseMetaData#getColumns(java.lang.String,java.lang.String,java.lang.String,java.lang.String)
   */
  private static final String JDBC_COLUMN_DESCRIPTION_DECIMAL_DIGITS = "DECIMAL_DIGITS";

  /**
   * Column name that contains the column size for the column in the result set that describes a
   * column in a table
   *
   * @see java.sql.DatabaseMetaData#getColumns(java.lang.String,java.lang.String,java.lang.String,java.lang.String)
   */
  private static final String JDBC_COLUMN_DESCRIPTION_COLUMN_SIZE = "COLUMN_SIZE";

  /**
   * Column name that contains the DB type name of the column in the result set that describes a
   * column
   *
   * @see java.sql.DatabaseMetaData#getColumns(java.lang.String,java.lang.String,java.lang.String,java.lang.String)
   */
  private static final String JDBC_COLUMN_DESCRIPTION_TYPE_NAME = "TYPE_NAME";

  /**
   * Column name that contains the column name in the result set that describes a column
   *
   * @see java.sql.DatabaseMetaData#getColumns(java.lang.String,java.lang.String,java.lang.String,java.lang.String)
   */
  private static final String JDBC_COLUMN_DESCRIPTION_NAME = "COLUMN_NAME";

  /**
   * Column name that contains the column comments in the result set that describes a column
   *
   * @see java.sql.DatabaseMetaData#getColumns(java.lang.String,java.lang.String,java.lang.String,java.lang.String)
   */
  private static final String JDBC_COLUMN_DESCRIPTION_REMARKS = "REMARKS";

  /**
   * Column name that contains the column's default value (expression) in the result set that
   * describes a column
   *
   * @see java.sql.DatabaseMetaData#getColumns(java.lang.String,java.lang.String,java.lang.String,java.lang.String)
   */
  private static final String JDBC_COLUMN_DESCRIPTION_COLUMN_DEFAULT = "COLUMN_DEF";

  /**
   * Column name that contains the table name in the result set that describes a database table
   *
   * @see java.sql.DatabaseMetaData#getTables(java.lang.String,java.lang.String,java.lang.String,java.lang.String[])
   */
  private static final String JDBC_TABLE_DESCRIPTION_TABLE_NAME = "TABLE_NAME";

  /**
   * Column name that contains comments in the result set that describes a database table
   *
   * @see java.sql.DatabaseMetaData#getTables(java.lang.String,java.lang.String,java.lang.String,java.lang.String[])
   */
  private static final String JDBC_TABLE_DESCRIPTION_REMARKS = "REMARKS";

  /**
   * Column name that holds the name of the primary key field in the result set that describes a
   * table's primary keys
   *
   * @see java.sql.DatabaseMetaData#getPrimaryKeys(java.lang.String, java.lang.String,
   *      java.lang.String)
   */
  private static final String JDBC_PK_DESCRIPTION_COLUMN_NAME = "COLUMN_NAME";

  /**
   * Column name that holds the name of the order of the column in the primary in the result set
   * that describes a table's primary keys
   *
   * @see java.sql.DatabaseMetaData#getPrimaryKeys(java.lang.String, java.lang.String,
   *      java.lang.String)
   */
  private static final String JDBC_PK_DESCRIPTION_SEQENCE = "KEY_SEQ";

  /**
   * Column name that holds the name of the primary key in a relation
   *
   * @see java.sql.DatabaseMetaData#getImportedKeys(java.lang.String, java.lang.String,
   *      java.lang.String)
   */
  private static final String JDBC_RELATION_DESCRIPTION_PK_TABLE_NAME = "PKTABLE_NAME";

  /**
   * Column name that holds the name of the foreign key (field) in a relation
   *
   * @see java.sql.DatabaseMetaData#getImportedKeys(java.lang.String, java.lang.String,
   *      java.lang.String)
   */
  private static final String JDBC_RELATION_DESCRIPTION_FK_FIELD = "FKCOLUMN_NAME";

  /**
   * Column name that indicates what would happen if the primary key is deleted. If it contains
   * DatabaseMetaData.importedKeyCascade this indicates composition
   *
   * @see java.sql.DatabaseMetaData#getImportedKeys(java.lang.String, java.lang.String,
   *      java.lang.String)
   * @see java.sql.DatabaseMetaData#importedKeyCascade
   */
  private static final String JDBC_RELATION_DESCRIPTION_DELETE_RULE = "DELETE_RULE";

  /**
   * Type of the tables to reverse engineer, as of the specification of JDBC
   *
   * @see java.sql.DatabaseMetaData#getTableTypes()
   */
	private static final String[] TABLE_TYPES_TO_REVERSE = new String[] { "TABLE", "VIEW", "SYSTEM TABLE",
	                                                                      // "GLOBAL TEMPORARY", "LOCAL TEMPORARY",
																		  "ALIAS", "SYNONYM"};

  private final ModelRepository modelRepository;
  private final String[] schemes;

  /**
   * Could be null if no database/catalog restriction applied
   */
  private final String catalog;

  private Uml14ModelFactory factory;

  /**
   * @param modelRepository not null
   * @param catalog Could be null if no database/catalog restriction applied
   * @param schemes not null, not empty name of the schemes to reverse engineer.
   * NOTE: It might be case sensitive.
   */
  public ReverseDatabaseOperation(ModelRepository modelRepository, String catalog, String... schemes) {
    this.modelRepository = modelRepository;
    this.schemes = schemes;
    this.catalog = catalog;
  }

  /**
   * This method processes all tables in the schemes and registers classes for them into the extent
   * provided
   *
   * @param metadata is the database metadata to reverse engineer
   * @param schema the name of the DB schema to reverse engineer
   * @return non-null list of registered tables
   * @throws SQLException
   */
  protected List<UmlClass> processTables(DatabaseMetaData metadata, String schema) throws SQLException {
    List<UmlClass> result;
    ResultSet tableDescriptions;
    String tableName;
    String remarks;
    UmlClass umlClass;

    result = new ArrayList<>();

    // retrieve all tables in the schemes (no catalog restrictions)
    tableDescriptions = metadata.getTables( null, schema.toUpperCase(), "%", TABLE_TYPES_TO_REVERSE );
    try {
      while ( tableDescriptions.next() ) {
        // create the class
        tableName = tableDescriptions.getString( JDBC_TABLE_DESCRIPTION_TABLE_NAME );

        LOGGER.log(Level.INFO,"Table: {0}", tableName);

        if ( isValidTableName( tableName ) ) {
          umlClass = factory.constructClass( tableName );
          result.add( umlClass );

          remarks = tableDescriptions.getString( JDBC_TABLE_DESCRIPTION_REMARKS );

          factory.constructTagDocumentation( umlClass, remarks );
          factory.constructTagPersistent( umlClass );

          defineAttributes( metadata, umlClass, schema, tableName );
        } else {
          LOGGER.log(Level.INFO,"  skipped" );
        }
        LOGGER.log(Level.INFO,"");
      }
    } finally {
      tableDescriptions.close();
    }
    return result;
  }

  /**
   * Returns true if the table name is a legal one (otherwise JDBC functions would fail)
   * @param tableName
   */
  private boolean isValidTableName(String tableName) {
    return tableName.matches(LEGAL_TABLE_NAME_REGEX);
  }

  /**
   * This method processes all relationhips in the schemes and registers associations for them into
   * the extent provided. The relationships are identified on class-by-class basis.
   * @param tables non-null list of regitered tables
   * @param metadata is the database metadata to reverse engineer
   * @param schema the name of the DB schemes to reverse engineer
   * @throws SQLException
   */
  protected void processRelationships(List<UmlClass> tables, DatabaseMetaData metadata, String schema) throws SQLException {
    ResultSet relationDescriptions;
    String tableName;
    String otherTable;
    String thisField;
    UmlClass otherClass;
    boolean isComposite;

    // for each class/table find its relationships.
    for (UmlClass thisClass : tables) {

      tableName = thisClass.getName();

      LOGGER.log(Level.INFO, "Relations of: {0}", tableName );

      // retrieve the relationships - the tables this class/table refers through foreign keys
      relationDescriptions = metadata.getImportedKeys( catalog, schema, tableName );
      try {
        while ( relationDescriptions.next() ) {
          otherTable = relationDescriptions.getString( JDBC_RELATION_DESCRIPTION_PK_TABLE_NAME );
          thisField  = relationDescriptions.getString( JDBC_RELATION_DESCRIPTION_FK_FIELD ); // FK

          // find the other class
          try {
	          otherClass = findClass( otherTable );

	          // establish association

	          // other end - the referenced table (the primary key one)
	          // the name of the role of the other end (the PK one) is stated in the foreign key name at
	          // this send
	          // multiplicity: to-one

	          // this end (the foreign key table)
	          // the name of the role of this end is simply the name of this (FK) table
	          // multiplicity: from-more

	          isComposite = relationDescriptions.getShort( JDBC_RELATION_DESCRIPTION_DELETE_RULE )
	                        == DatabaseMetaData.importedKeyCascade; // "Cascade delete" in the constraint means composition

	          factory.constructAssociation( otherClass, thisField, 1, isComposite, true,
	                                        thisClass, "", net.mdatools.modelant.uml14.metamodel.Convention.UNLIMITED_UPPER_MULTIPLICITY,
	                                        thisClass.getNamespace(),
	                                        "" );
          } catch (IllegalArgumentException ex) {
          	LOGGER.log(Level.SEVERE, ex.getMessage());
          }
        }
      } finally {
        relationDescriptions.close();
      }
    }
  }

  /**
   * Finds the class with the name provided
   *
   * @param className
   * @return the non-null class with the name provided
   * @throws IllegalArgumentException when class with the name cannot be found
   */
  private UmlClass findClass(String className) throws IllegalArgumentException {
    return (UmlClass) factory.locateModelElement(className);
  }

  /**
   * Create an UML class in the extent provided that correspnds to the table. This
   * method binds the attributes with tagged values for documentation, non-null and primary key
   * indication. See the conventions stated in the class documentation.
   *
   * @param metadata
   * @param umlClass
   * @param schemes to search columns in
   * @param tableName
   * @throws SQLException
   */
  private void defineAttributes(DatabaseMetaData metadata, UmlClass umlClass, String schema, String tableName) throws SQLException {
      ResultSet attributesRS;
      String remark;
      String defaultValue;
      String columnName;
      Attribute attribute;
      Expression expression;
      DataType attributeType;
      int size;
      int precision;

      Map<String, Short> primaryKeys; // Maps PK column names to Short sequence order
      Short sequence;

      // list the primary keys of this table
      primaryKeys = definePrimaryKey( metadata, schema, tableName );

      // retrieve all columns of this table (no catalog restrictions)
      attributesRS = metadata.getColumns( null, schema, tableName, "%" );
      try {
        while ( attributesRS.next() ) {
          columnName = attributesRS.getString( JDBC_COLUMN_DESCRIPTION_NAME );

          // bind the attribute
          attribute = factory.constructAttribute( columnName );
          attribute.setOwner( umlClass );
          attribute.setVisibility( VisibilityKindEnum.VK_PUBLIC );

          attributeType = factory.constructDataType( attributesRS.getString( JDBC_COLUMN_DESCRIPTION_TYPE_NAME ));
          attribute.setType( attributeType );

          // Bind size & precision as tagged values of this attribute
          size      = attributesRS.getInt( JDBC_COLUMN_DESCRIPTION_COLUMN_SIZE );
          precision = attributesRS.getInt( JDBC_COLUMN_DESCRIPTION_DECIMAL_DIGITS );

          factory.constructTagSize( attribute, size );
          if ( precision > 0 ) {
            factory.constructTagFieldPrecision( attribute, precision );
          }

          // set the comments found
          remark = attributesRS.getString( JDBC_COLUMN_DESCRIPTION_REMARKS );
          factory.constructTagDocumentation( attribute, remark );

          // set default value found
          defaultValue = attributesRS.getString( JDBC_COLUMN_DESCRIPTION_COLUMN_DEFAULT );
          if ( defaultValue != null && !defaultValue.equals( "" ) ) {
            expression = factory.constructExpression( defaultValue );
            attribute.setInitialValue( expression );
          }

  //        // is the column nullable
  //        nullable = (attributesRS.getInt( JDBC_COLUMN_DESCRIPTION_NULLABLE ) == DatabaseMetaData.columnNullable);
  //        manager.instantiateTagNullable( attribute, nullable );

          // check for primary key
          sequence = primaryKeys.get( columnName );
          if ( sequence != null ) { // the column pertains to the primary key of this table
            factory.constructTagPrimaryKey( attribute, sequence.intValue() );
          }
        }
      } finally {
        attributesRS.close();
      }
    }

  /**
   * Indicates the primary key columns (attributes) of the class that corresponds to the
   * table (name) provided. Requires all attributes to have been added
   *
   * @param metadata
   * @param schemes
   * @param tableName
   * @return a Map of String column names to Short-s indicating the column order in the primary key
   * @throws SQLException
   */
  private Map<String, Short> definePrimaryKey(DatabaseMetaData metadata, String schema, String tableName) throws SQLException {
    Map<String, Short> result = new HashMap<>( 11 );
    ResultSet primaryKeys;
    String columnName;
    short sequence;

    // Retrieve the primary keys for the table
    primaryKeys = metadata.getPrimaryKeys( null, schema, tableName );
    try {
      while ( primaryKeys.next() ) {
        columnName = primaryKeys.getString( JDBC_PK_DESCRIPTION_COLUMN_NAME );
        sequence = primaryKeys.getShort( JDBC_PK_DESCRIPTION_SEQENCE );
        result.put( columnName, new Short( sequence ) );
      }
    } finally {
      primaryKeys.close();
    }
    return result;
  }

  /**
   * Prints the contents of the result set provided and closes it.
   *
   * @param resultSet
   * @throws SQLException
   */
  protected void dumpResultSet(ResultSet resultSet) throws SQLException {
    ResultSetMetaData resultSetMeta;
    StringBuilder line;

    resultSetMeta = resultSet.getMetaData();

    // dump the column names
    LOGGER.log(Level.INFO, "Column names:" );

    line = new StringBuilder(256);
    for (int i = 1; i <= resultSetMeta.getColumnCount(); i++) {
      if ( i > 1 ) {
        line.append( ", " );
      }
      line.append( resultSetMeta.getColumnLabel( i ) );
    }
    LOGGER.log(Level.INFO, line.toString() );
    LOGGER.log(Level.INFO, line.toString().replaceAll( ".", "-" ));

    // dump the contents column-by-column
    while ( resultSet.next() ) {
      line = new StringBuilder(256);
      for (int i = 1; i <= resultSetMeta.getColumnCount(); i++) {
        if ( i > 1 ) {
          line.append( ", " );
        }
        line.append( resultSet.getObject( i ) );
      }
      LOGGER.log(Level.INFO, line.toString() );
    }
    resultSet.close();
  }

  /**
   * @param connection not null
   * @return not null extent with the model of the database schemes
   * @throws IllegalArgumentException
   * @see net.mdatools.modelant.core.api.Function#execute(java.lang.Object)
   */
  public RefPackage execute(Connection connection) throws IllegalArgumentException {
    ModelFactory modelFactory;
    RefPackage result;
    DatabaseMetaData metadata;
    List<UmlClass> tables;

    modelFactory = modelRepository.loadMetamodel("UML13");
    result = modelFactory.instantiate("model");

    factory = new Uml14ModelFactory( result );
    factory.setModelName( schemes[0] );

    try {
      metadata = connection.getMetaData();

      for (String schema : schemes) {
	      // process all tables and register corresponding classes
	      tables = processTables( metadata, schema );

	      // describe the relations as associations
	      processRelationships( tables, metadata, schema );
      }
    } catch (SQLException ex) {
      throw new IllegalArgumentException(ex);
    }
    return result;
  }

}