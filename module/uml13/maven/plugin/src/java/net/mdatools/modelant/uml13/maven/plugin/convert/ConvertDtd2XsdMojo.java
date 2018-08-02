/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.uml13.maven.plugin.convert;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

import com.thaiopensource.relaxng.translate.Driver;

/**
 * Convert a DTD into an XSD, using TRANG
 * @author Rusi Popov
 */
@Mojo(name="dtd-to-xsd",
  defaultPhase=LifecyclePhase.COMPILE
)
@Execute(phase=LifecyclePhase.COMPILE)
public class ConvertDtd2XsdMojo extends AbstractMojo  {

  private final Driver convertor = new Driver();

  private final List<String> parameters = new ArrayList<>();

  /**
   * The DTD file to convert
   * @parameter
   * @required
   */
  private File dtdFile;

  /**
   * The XSD file to convert into
   * @parameter
   * @required
   */
  private File xsdFile;

  /**
   */
  public ConvertDtd2XsdMojo() {
    setInputType( "dtd" );
    setOutputType( "xsd" );
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
   * Construct the .XSD file from the provided .DTD
   */
  public void execute() throws RuntimeException, IllegalArgumentException {
    List<String> parameters;

    parameters = new ArrayList<>(this.parameters);
    parameters.add( dtdFile.getAbsolutePath() );
    parameters.add( xsdFile.getAbsolutePath() );

    convertor.run(parameters.toArray( new String[0] ));
  }
}