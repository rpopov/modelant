/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.template.maven.plugin;

import static net.mdatools.modelant.template.api.Convention.TEMPLATE_FILE_SUFFIX;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.shared.model.fileset.FileSet;
import org.apache.maven.shared.model.fileset.util.FileSetManager;

import net.mdatools.modelant.template.api.TemplateEngine;
import net.mdatools.modelant.template.api.TemplateEngineFactory;

/**
 * Compile the listed ModelAnt Templates, this way allowing their validation
 * as part of the build process.
 * @author Rusi Popov (popovr@mdatools.net)
 */
@Mojo(name="compile-templates",
defaultPhase=LifecyclePhase.COMPILE
)
@Execute(phase=LifecyclePhase.COMPILE)
public class CompileTemplateMojo extends CompilationContext {

  /**
   * Selection of all ModelAnt Templates below sourceDirectory
   */
  @Parameter
  private FileSet fileSet;

  /**
   * This method is used by the enclosing task ForEachTask.
   */
  public final void execute() throws MojoFailureException {
    String[] includedFiles;
    File relativeSourceFile;
    List<File> templateFiles;
    TemplateEngine engine;
    FileSetManager manager;

    // provide default
    if ( fileSet == null ) {
      fileSet = new FileSet();
      fileSet.setDirectory( getTemplateDirectory().getAbsolutePath() );
      fileSet.setIncludes( Arrays.asList("**/*"+TEMPLATE_FILE_SUFFIX) );
    }

    templateFiles = new ArrayList<>();
    manager = new FileSetManager();

    includedFiles = manager.getIncludedFiles(fileSet);

    if ( getLog().isDebugEnabled() ) {
      getLog().debug( "Template source directory:"+getTemplateDirectory().getAbsolutePath());
    }

    if ( includedFiles.length == 0 ) {
      throw new MojoFailureException("No template files selected");
    }

    for ( String includedFileName: includedFiles) {
      relativeSourceFile = new File( includedFileName ); // relative to fileset.directory

      templateFiles.add( relativeSourceFile );
      if ( getLog().isDebugEnabled() ) {
        getLog().debug( "add:"+relativeSourceFile);
      }
    }

    try {
      engine = TemplateEngineFactory.construct( this );
      engine.compile( templateFiles );
    } catch (Exception ex) {
      getLog().error("Compilation failed: ", ex);
    }
  }
}