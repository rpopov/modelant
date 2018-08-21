/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;

import javax.jmi.model.ModelPackage;
import javax.jmi.model.MofPackage;
import javax.jmi.reflect.InvalidNameException;
import javax.jmi.reflect.RefPackage;
import javax.jmi.xmi.MalformedXMIException;

import org.netbeans.api.mdr.CreationFailedException;
import org.netbeans.api.mdr.DTDProducer;
import org.netbeans.api.mdr.JMIMapper;
import org.netbeans.api.mdr.JMIStreamFactory;
import org.netbeans.api.mdr.MDRepository;
import org.netbeans.api.xmi.XMIReader;
import org.netbeans.api.xmi.XMIReaderFactory;
import org.netbeans.api.xmi.XMIWriter;
import org.netbeans.api.xmi.XMIWriterFactory;
import org.netbeans.lib.jmi.mapping.FileStreamFactory;
import org.netbeans.lib.jmi.mapping.JMIMapperCFImpl;
import org.netbeans.lib.jmi.mapping.JMIMapperImpl;

import net.mdatools.modelant.repository.api.ModelFactory;
import net.mdatools.modelant.repository.spi.MetamodelFactorySetup;
import net.mdatools.modelant.repository.spi.ModelRepositorySetup;

/**
 * This class provides the base functionality to access the
 * MetaData Repository in a controlled way. <pre>
 * Usage:
 *
 *   initialize( storage file );
 *   try {
 *
 *     beginTransaction();
 *     try {
 *       getRepository();
 *       manipulate
 *
 *     } catch (Exception ex) {
 *       // rollback
 *       endTransaction(false);
 *
 *     } finally {
 *       // commit
 *       endTransaction(true);
 *     }
 *   } finally {
 *     shutdownAll()
 *   }
 * </pre>
 *
 * @see <a href="http://netbeans.org/download/5_5/javadoc/org-netbeans-api-mdr/index.html?org/netbeans/api/mdr/JMIStreamFactory.html">JMIStreamFactory</a>
 */
public final class RepositoryAdapter implements ModelRepositorySetup {

  /**
   * The wrapped repository, it is actually org.netbeans.mdr.NBMDRepositoryImpl
   * INVARIANT:
   *   repository != null if and only if this adapter is initialized
   *   once initialized, it cannot change back to not initialized state, but it could be shut down several times
   */
  private MDRepository repository;

  /**
   * The actual file used to store the MDR
   * Set by initialize(). It never changes - the file name will be reused if the repository is used after shutdown()
   */
  private String storageFile;

  /**
   * If at shut down we should keep the storage file (true) or delete them (false)
   */
  private boolean keepStorageFile;

  /**
   * All metamodel factories already produced, making sure they are singletons
   */
  private final Map<String, ModelFactory> METAMODEL_FACTORIES= new HashMap<>();

  /**
   * Prevent instantiating it
   */
  public RepositoryAdapter() {
  }

  /**
   * @see net.mdatools.modelant.repository.spi.ModelRepositorySetup#initialize(File)
   */
  public void initialize(File workDir) {
    File targetFile;
    ClassLoader oldLoader;


    if ( isInitialized() ) {
      throw new IllegalStateException("Already initialized");
    }

    // assign repository storage file to create
    if ( workDir == null ) {
      workDir = new File(".");
    }

    // use a temporary file that actually could not be reused
    targetFile = new File(workDir, "mdr.storage" + new Date().getTime() );

    // file storage - the value must implement org.netbeans.mdr.persistence.StorageFactory
    if ( !targetFile.exists() && targetFile.getParentFile() != null ) { // nowhere to store the file
      targetFile.getParentFile().mkdirs();
    }
    storageFile = targetFile.getAbsolutePath();
    keepStorageFile = false;

    System.getProperties().put( "org.netbeans.mdr.persistence.Dir", storageFile );
    System.getProperties().put( "org.netbeans.mdr.persistence.btreeimpl.cacheSize", "655360");
    System.getProperties().put( "org.netbeans.mdr.persistence.btreeimpl.cacheThreshHold", "655000");

    // default in-memory cache size, overriding the default 2K
    System.getProperties().put( "org.netbeans.mdr.persistence.btreeimpl.btreestorage.MDRCache.threshhold", "655000");

    // perf.mdr.MDRCache DEBUG print out
 //      System.getProperties().put( "perf.mdr.MDRCache", "true");

    // file storage - the value must implement org.netbeans.mdr.persistence.StorageFactory
    System.getProperties().put("org.netbeans.mdr.storagemodel.StorageFactoryClassName",
                               org.netbeans.mdr.persistence.btreeimpl.btreestorage.BtreeFactory.class.getName());

    // in-memory storage
    // There are problems with MOF extent when building a distribution. Use only the file storage.
//      System.getProperties().put("org.netbeans.mdr.storagemodel.StorageFactoryClassName",
//                                 org.netbeans.mdr.persistence.memoryimpl.StorageFactoryImpl.class.getName());

    System.getProperties().put( "MDRStorageProperty.mutexClass", ReadWriteMutex.class.getName());

    oldLoader = Thread.currentThread().getContextClassLoader();

    Thread.currentThread().setContextClassLoader( RepositoryAdapter.class.getClassLoader() );
    try {
      repository = new org.netbeans.mdr.NBMDRepositoryImpl();
    } finally {
      Thread.currentThread().setContextClassLoader( oldLoader );
    }
  }

  /**
   * @return true if properly initialized
   */
  private boolean isInitialized() {
    return repository != null;
  }

  /**
   * @see net.mdatools.modelant.repository.api.ModelRepository#beginReadOnlyTransaction()
   */
  public void beginReadOnlyTransaction() {
    getMdRepository().beginTrans( false );
  }

  /**
   *
   * @see net.mdatools.modelant.repository.api.ModelRepository#beginWriteTransaction()
   */
  public void beginWriteTransaction() {
    getMdRepository().beginTrans( true );
  }

  /**
   * @return non-null repository implementation
   */
  MDRepository getMdRepository() {
    if ( !isInitialized() ) {
      throw new RuntimeException( "The Meta-Data Repository is not initialized" );
    }
    return  repository;
  }

  /**
   * @see net.mdatools.modelant.repository.api.ModelRepository#endTransaction(boolean)
   */
  public void endTransaction(boolean shouldCommit) {
    getMdRepository().endTrans( shouldCommit );
  }


  /**
   *
   * @see net.mdatools.modelant.repository.api.ModelRepository#shutdown()
   */
  public void shutdown() {
    if ( isInitialized() ) {
      repository.shutdown();

      if ( !keepStorageFile && storageFile != null ) {
        new File( storageFile+".btd" ).delete();
        new File( storageFile+".btx" ).delete();
      }
      repository = null;
    }
  }


  /**
   * @see net.mdatools.modelant.repository.api.ModelRepository#constructMetamodelExtent(java.lang.String)
   */
  public RefPackage constructMetamodelExtent(String extentName) throws Exception {
    RefPackage result;

    if ( extentName == null ) {
      throw new IllegalArgumentException( "Expected a non-empty name for newly instantiated package.");
    }
    result = getMdRepository().createExtent( extentName );

    return result;
  }

  /**
   * @see net.mdatools.modelant.repository.api.ModelRepository#constructModelExtent(java.lang.String, java.lang.String, java.lang.String)
   */
  public RefPackage constructModelExtent(String mofExtentName,
                                         String metaPackageName,
                                         String modelExtentName) throws IllegalArgumentException, CreationFailedException {
    ModelPackage mofPackage;

    if ( mofExtentName == null || mofExtentName.isEmpty() ) {
      throw new IllegalArgumentException( "Expected a non-empty extent (mofExtentName) containing the package");
    }
    mofPackage = (ModelPackage) getExtent( mofExtentName );

    return constructModelExtent( mofPackage, metaPackageName, modelExtentName );
  }

  /**
   * @see net.mdatools.modelant.repository.api.ModelRepository#constructModelExtent(javax.jmi.model.ModelPackage, java.lang.String, java.lang.String)
   */
  public RefPackage constructModelExtent(ModelPackage mofPackage,
                                         String metaPackageName,
                                         String modelExtentName) throws CreationFailedException {
    RefPackage result;
    MofPackage metaPackage;
    MofPackage pack;
    Iterator<MofPackage> it;

    if ( metaPackageName == null || metaPackageName.isEmpty() ) {
      throw new IllegalArgumentException( "Expected a non-empty package (metaPackageName).");
    }
    if ( modelExtentName == null || modelExtentName.isEmpty()) {
      throw new IllegalArgumentException( "Expected a non-empty name for the newly instantiated package");
    }

    metaPackage = null;
    it = mofPackage.getMofPackage().refAllOfClass().iterator();
    while ( metaPackage == null && it.hasNext() ) {
      pack = it.next();

      if ( pack.getName().equals( metaPackageName ) ) {
        metaPackage = pack;
      }
    }
    if ( metaPackage == null ) {
      throw new CreationFailedException( metaPackageName + " does not exist in extent " + mofPackage);
    }
    result = getMdRepository().createExtent( modelExtentName, metaPackage );
    return result;
  }

  /**
   * @see net.mdatools.modelant.repository.api.ModelRepository#getExtent(java.lang.String)
   */
  public RefPackage getExtent(String name) {
    RefPackage result;

    if ( name == null || name.isEmpty() ) {
      throw new IllegalArgumentException( "Expected a non-empty extent name");
    }

    result = getMdRepository().getExtent( name );
    return result;
  }

  /**
   * @see net.mdatools.modelant.repository.api.ModelRepository#deleteExtent(java.lang.String)
   */
  public void deleteExtent(String name) {
    RefPackage extent;

    extent = getExtent( name );
    if ( extent != null ) {
      extent.refDelete();
    }
  }

  /**
   * @see net.mdatools.modelant.repository.api.ModelRepository#getExtentNames()
   */
  public String[] getExtentNames() {
    String[] result;
    result = getMdRepository().getExtentNames();
    return result;
  }

  /**
   * @see net.mdatools.modelant.repository.api.ModelRepository#readIntoExtent(java.lang.String, java.lang.String)
   */
  public void readIntoExtent(String extentName, String url) throws IOException, MalformedXMIException {
    readIntoExtent( extentName, url, Thread.currentThread().getContextClassLoader() );
  }

  /**
   * @see net.mdatools.modelant.repository.api.ModelRepository#readIntoExtent(java.lang.String, java.lang.String, java.lang.ClassLoader)
   */
  public void readIntoExtent(String extentName, String url, ClassLoader classLoader) throws IOException, MalformedXMIException {
    RefPackage targetExtent;

    targetExtent = getExtent( extentName );
    if ( targetExtent == null ) {
      throw new IllegalArgumentException( "Unknown extent name \""+extentName+"\"");
    }

    readIntoExtent( targetExtent, url, classLoader );
  }

  /**
   * @see net.mdatools.modelant.repository.api.ModelRepository#readIntoExtent(javax.jmi.reflect.RefPackage, java.lang.String)
   */
  public void readIntoExtent(RefPackage targetExtent,
                             String url) throws IOException, MalformedXMIException {
    readIntoExtent( targetExtent, url, Thread.currentThread().getContextClassLoader() );
  }

  /**
   * @see net.mdatools.modelant.repository.api.ModelRepository#readIntoExtent(javax.jmi.reflect.RefPackage, java.lang.String, java.lang.ClassLoader)
   */
  public void readIntoExtent(RefPackage targetExtent,
                             String url,
                             ClassLoader classLoader) throws IOException, MalformedXMIException {
    XMIReader xmiReader;
    URL resourceUri;
    InputStream resourceAsStream;

    if ( url == null || url.isEmpty()) {
     throw new IllegalArgumentException( "Expected a non-empty URI of the file to import.");
    }

    resourceUri = classLoader.getResource( url );
    if ( resourceUri == null ) {
      throw new IllegalArgumentException( "Cannot find in classpath "+url);
    }

    xmiReader = XMIReaderFactory.getDefault().createXMIReader();

    resourceAsStream = classLoader.getResourceAsStream( url );
    try {
      xmiReader.read( resourceAsStream,
                      resourceUri.toString(),
                      targetExtent );
    } finally {
      resourceAsStream.close();
    }
  }

  /**
   * @see net.mdatools.modelant.repository.api.ModelRepository#readIntoExtent(java.lang.String, java.io.File)
   */
  public void readIntoExtent(String extentName, File file) throws IOException, MalformedXMIException {
    RefPackage targetExtent;
    XMIReader xmiReader;

    xmiReader = XMIReaderFactory.getDefault().createXMIReader();
    targetExtent = getMdRepository().getExtent( extentName );

    if ( targetExtent  == null ) {
      throw new IllegalArgumentException( "Unknown extent name \""+targetExtent+"\"");
    }
    if ( file == null || file.getName().isEmpty() ) {
      throw new IllegalArgumentException( "Provided an empty file to import.");
    }

    xmiReader.read( new FileInputStream( file ),
                    file.toURI().toString(),
                    targetExtent );
  }

  /**
   * @see net.mdatools.modelant.repository.api.ModelRepository#readIntoExtent(javax.jmi.reflect.RefPackage, java.io.File)
   */
  public void readIntoExtent(RefPackage extent, File file) throws IOException, MalformedXMIException {
    XMIReader xmiReader;

    if ( extent  == null ) {
      throw new IllegalArgumentException( "Expected non-null extent");
    }
    if ( file == null || file.getName().isEmpty() ) {
      throw new IllegalArgumentException( "Provided an empty file to import.");
    }

    xmiReader = XMIReaderFactory.getDefault().createXMIReader();
    xmiReader.read( new FileInputStream( file ),
                    file.toURI().toString(),
                    extent );
  }

  /**
   * @see net.mdatools.modelant.repository.api.ModelRepository#writeDtd(java.io.File, java.lang.String)
   */
  public void writeDtd(File file, String extent) throws FileNotFoundException {
    if ( file == null ) {
      throw new IllegalArgumentException( "Expected a non-null export file");
    }

    if ( extent == null || extent.trim().isEmpty()) {
      throw new IllegalArgumentException( "Expected a not empty extent to export");
    }
    DTDProducer.getDefault().generate( new FileOutputStream( file ),
                                       getExtent( extent ) );
  }

  /**
   * @see net.mdatools.modelant.repository.api.ModelRepository#writeExtent(java.lang.String, java.io.File)
   */
  public void writeExtent(String extent, File file) throws IOException, InvalidNameException {
    writeExtent( extent, file, DEFAULT_XMI_VERSION );
  }

  /**
   * @see net.mdatools.modelant.repository.api.ModelRepository#writeExtent(java.lang.String, java.io.File, java.lang.String)
   */
  public void writeExtent(String extent, File file, String xmiVersion) throws IOException, InvalidNameException {
    RefPackage extentRootPackage;

    if ( file == null ) {
      throw new IllegalArgumentException("Expected a non-null export file");
    }

    if ( extent == null || extent.trim().isEmpty()) {
      throw new IllegalArgumentException( "Expected a non-empty extent to export");
    }

    if ( xmiVersion == null ) {
      throw new IllegalArgumentException("Expected a non-empty XMI version");
    }

    extentRootPackage = getMdRepository().getExtent( extent );
    if ( extentRootPackage == null ) {
      throw new InvalidNameException( "Not existing extent "+extent+" to export was sppecified" );
    }

    writeExtent( extentRootPackage, file, xmiVersion );
  }

  /**
   * @see net.mdatools.modelant.repository.api.ModelRepository#writeExtent(javax.jmi.reflect.RefPackage, java.io.File, java.lang.String)
   */
  public void writeExtent(RefPackage extent, File file, String xmiVersion) throws IOException {
    OutputStream out;
    XMIWriter xmiWriter;

    xmiWriter = XMIWriterFactory.getDefault().createXMIWriter();

    if ( file.getParentFile() != null ) {
      file.getParentFile().mkdirs();
    }
    out = new FileOutputStream( file );
    try {
      xmiWriter.write( out, extent, xmiVersion );
    } finally {
      out.close();
    }
  }

  /**
   * @see net.mdatools.modelant.repository.api.ModelRepository#getByMofId(java.lang.String)
   */
  public Object getByMofId(String mofId) {
    return getMdRepository().getByMofId( mofId );
  }

  /**
   * @see net.mdatools.modelant.repository.api.ModelRepository#mapJava(java.io.File, java.lang.String, java.io.File)
   */
  public void mapJava(File dir, String extentName, File headerFile) throws IOException {
    JMIMapperImpl result;
    BufferedReader reader;
    StringWriter header;
    int ch;

    result = new JMIMapperImpl();

    if ( headerFile != null ) {
      reader = new BufferedReader( new FileReader( headerFile ) );
      try {
        header = new StringWriter();
        try {
          while ( (ch = reader.read()) != -1 ) {
            header.write( ch );
          }
        } finally {
          header.close();
        }
        result.setHeader( header.toString() );
      } finally {
        reader.close();
      }
    }
    execute( result, dir, extentName );
  }

  /**
   * @see net.mdatools.modelant.repository.api.ModelRepository#mapClasses(java.io.File, java.lang.String)
   */
  public void mapClasses(File dir, String extentName) throws IOException {
    execute( new JMIMapperCFImpl(), dir, extentName );
  }

  /**
   * @throws IOException
   * @throws Exception
   */
  private void execute(JMIMapper mapper, File dir, String extentName) throws IOException {
    RefPackage extent;
    JMIStreamFactory streamFactory;

    if ( dir == null ) {
      throw new IllegalArgumentException( "Expected a valid directory to generate in");
    }

    streamFactory = new FileStreamFactory( dir );

    extent = getExtent( extentName );
    if ( extent == null ) {
      throw new IllegalArgumentException( "There is no extent named: "+extentName);
    }
    mapper.generate( streamFactory, extent );
  }

  /**
   * @see net.mdatools.modelant.repository.api.ModelRepository#loadMetamodel(java.lang.String)
   */
  public ModelFactory loadMetamodel(String metamodelName) throws IllegalStateException, IllegalArgumentException {
    return loadMetamodel( metamodelName,
                          Thread.currentThread().getContextClassLoader() );
  }

  /**
   * @see net.mdatools.modelant.repository.api.ModelRepository#loadMetamodel(java.lang.String)
   */
  public ModelFactory loadMetamodel(String metamodelName, ClassLoader loader) throws IllegalStateException, IllegalArgumentException {
    ModelFactory result;
    MetamodelFactorySetup factorySpi;
    Iterator<MetamodelFactorySetup> factorySpiIterator;

    if ( !isInitialized() ) {
      throw new IllegalStateException("Expected this repository adapter has been initialized");
    }

    if ( metamodelName == null ) {
      throw new IllegalArgumentException("Expected a non-null metamodel name");
    }

    result = METAMODEL_FACTORIES.get( metamodelName );
    if ( result == null ) { // no ready made factories, construct one
      factorySpiIterator = ServiceLoader.load( MetamodelFactorySetup.class, loader ).iterator();
      while ( result == null && factorySpiIterator.hasNext() ) {
        factorySpi = factorySpiIterator.next();

        if (metamodelName.equals(factorySpi.getMetamodelName())) {
          result = factorySpi.construct( this, loader );
        }
      }

      if ( result == null) {
        throw new IllegalArgumentException("No factory found for metamodel "+metamodelName);
      }
      METAMODEL_FACTORIES.put( metamodelName, result );
    }
    return result;
  }
}