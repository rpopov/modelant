/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.template.maven.plugin;

import java.io.File;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.descriptor.PluginDescriptor;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Parameter;

import net.mdatools.modelant.template.api.TemplateCompilationContext;

/**
 * The Template Compilation Context set up through MOJO properties
 * @author Rusi Popov (popovr@mdatools.net)
 */
public abstract class CompilationContext extends AbstractMojo implements TemplateCompilationContext {

  /**
   * Non-empty unique name to differentiate the set of templates to compile in this maven project
   * from any other set to compile in other projects. It also should avoid producing
   * standard package names that are forbidden for custom class loaders.
   * It should not be among the forbidden package names like: java, com.sun
   */
  @Parameter(required=true)
  private String uniqueName;

  /**
   * Where the source files of the project templates are
   * src/template
   */
  @Parameter(defaultValue="${project.build.sourceDirectory}/../template", required=true)
  private File templateDirectory;

  /**
   * The directory where to hold the translated to JAVA templates
   * target/template-java
   */
  @Parameter(defaultValue="${project.build.directory}/template-java", required=true)
  private File javaSourceDirectory;

  /**
   * Where to hold the compilation result files - the standard classes directory
   * target/classes
   */
  @Parameter(property="project.build.outputDirectory", required=true, readonly=true)
  private File classDirectory;

  /**
   * PLEXUS should inject here the descriptor of the plugin this MOJO is in,
   * so it could access its classpath
   */
  @Component(role=org.apache.maven.plugin.descriptor.PluginDescriptor.class)
  private PluginDescriptor pluginDescriptor;

  /**
   * If the generated Java files from the templates should not be deleted
   * (for tracing purposes) set it to true
   */
  @Parameter(alias="keepGenerated")
  private boolean keepGenerated;

  /**
   * If the compiler should include debug information, set it to true
   */
  @Parameter(alias="compileForDebug")
  private boolean compileForDebug;

  /**
   * Encoding of the template files, default: ISO-8859-1
   */
  @Parameter(defaultValue="ISO-8859-1")
  private String templateEncoding;

  /**
   * Provide to the Template Engine
   * @see net.mdatools.modelant.template.api.TemplateCompilationContext#getUniqueName()
   */
  public String getUniqueName() {
    return uniqueName;
  }

  /**
   * Provide to the Template Engine
   * @see net.mdatools.modelant.template.api.TemplateCompilationContext#getTemplateDirectory()
   */
  public File getTemplateDirectory() {
    return templateDirectory;
  }

  /**
   * Provide to the Template Engine
   * @see net.mdatools.modelant.template.api.TemplateCompilationContext#getJavaDirectory()
   */
  public File getJavaDirectory() {
    return javaSourceDirectory;
  }

  /**
   * Provide to the Template Engine
   * @see net.mdatools.modelant.template.api.TemplateCompilationContext#getClassDirectory()
   */
  public File getClassDirectory() {
    return classDirectory;
  }

  /**
   * Provide to the Template Engine
   * @see net.mdatools.modelant.template.api.TemplateCompilationContext#getClassPath()
   */
  public String getClassPath() {
    StringBuilder result;

    // concatenate all artifacts in the classparh
    result = new StringBuilder(512);
    for (Artifact artifact: pluginDescriptor.getArtifacts()) {
      if (result.length() > 0) {
        result.append( File.pathSeparatorChar );
      }
      result.append( artifact.getFile().getAbsolutePath() );
    }

    // add this project's classes to classpath, as the template refers them
    if (result.length() > 0) {
      result.append( File.pathSeparatorChar );
    }
    result.append( getClassDirectory() );

    return result.toString();
  }

  /**
   * Provide to the Template Engine
   * @see net.mdatools.modelant.template.api.TemplateCompilationContext#shouldKeepGenerated()
   */
  public boolean shouldKeepGenerated() {
    return keepGenerated;
  }

  /**
   * Provide to the Template Engine
   * @see net.mdatools.modelant.template.api.TemplateCompilationContext#shouldCompileForDebug()
   */
  public boolean shouldCompileForDebug() {
    return compileForDebug;
  }

  /**
   * @see net.mdatools.modelant.template.api.TemplateCompilationContext#getTemplateEncoding()
   */
  public String getTemplateEncoding() {
    return templateEncoding;
  }
}