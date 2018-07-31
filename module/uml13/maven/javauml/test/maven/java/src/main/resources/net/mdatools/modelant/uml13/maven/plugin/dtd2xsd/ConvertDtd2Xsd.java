/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.uml13.maven.plugin.dtd2xsd;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.thaiopensource.relaxng.translate.Driver;

import net.mdatools.modelant.core.api.Operation;

/**
 * Convert a DTD into an XSD
 * @author Rusi Popov
 */
public class ConvertDtd2Xsd implements Operation<File> {

  /**
   * The e extension of the produced file
   */
  private static final String XSD = ".xsd";

  private final Driver convertor = new Driver();

  private final List<String> parameters = new ArrayList<>();

  /**
   * Not null directory where to store the target file
   */
  private final File targetDirectory;

  /**
   * @param targetDirectory not null directory where to construct the result {@link #XSD} file
   * @throws IllegalArgumentException when invalid directory provided
   */
  public ConvertDtd2Xsd(File targetDirectory) throws IllegalArgumentException {
    setInputType( "dtd" );
    setOutputType( "xsd" );

    if ( targetDirectory == null || !targetDirectory.isDirectory() ) {
      throw new IllegalArgumentException("Expected a non-null directory provided instead of "+targetDirectory);
    }

    this.targetDirectory = targetDirectory;
  }


  private void setInputType(String param) {
    parameters.add( "-I" );
    parameters.add( param );
  }


  private void setOutputType(String param) {
    parameters.add( "-O" );
    parameters.add( param );
  }


  /**
   * @param dtdFile non-null .DTD file to convert to XSD
   * @return the .XSD file constructed from the provided .DTD in the target directory, named by appending .xsd
   *         to the source dtd file name
   */
  public File execute(File dtdFile) throws RuntimeException, IllegalArgumentException {
    File result;
    List<String> parameters;

    if ( dtdFile == null || !dtdFile.isFile() ) {
      throw new IllegalArgumentException("Expected a non-null source .DT file provided instead of "+dtdFile);
    }

    parameters = new ArrayList<>(this.parameters);
    parameters.add( dtdFile.getAbsolutePath() );

    result = new File(targetDirectory, dtdFile.getName()+XSD);
    parameters.add( result.getAbsolutePath() );

    convertor.run(parameters.toArray( new String[0] ));

    return result;
  }
}