/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.template.maven.plugin;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.descriptor.PluginDescriptor;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import net.mdatools.modelant.template.api.TemplateCompilationContext;

/**
 * The Template Compilation Context set up through MOJO properties
 * @author Rusi Popov (popovr@mdatools.net)
 */
public abstract class CompilationContext extends AbstractMojo implements TemplateCompilationContext {

  /**
   * Non-empty unique name (in the format of java package identifier) to differentiate the set of templates to compile
   * in this maven project from any other set to compile in other projects.
   * It should not be among the forbidden package names like: java, com.sun, as these package names are forbidden
   * by custom class loaders.
   * This is a logical name, that represents the templatesDirectory in memory.
   * @see #templateDirectory
   */
  @Parameter(defaultValue="template", required=true)
  private String name;

  /**
   * Where the source files of the project templates are stored in the file system and distribution .jars
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
   * Provide any compilation classpath as project dependencies.
   * NOTE: This would allow using common DepednecyManagement definition, whereas if these dependencies were
   * provided as dependencies in the plugin, explicit versions will have to be provided.
   */
  @Parameter( defaultValue = "${project}", readonly = true )
  private MavenProject project;

  /**
   * Provide any compilation classpath as project dependencies.
   * NOTE: This would allow using common DepednecyManagement definition, whereas if these dependencies were
   * provided as dependencies in the plugin, explicit versions will have to be provided.
   */
  @Parameter( defaultValue = "${plugin}", readonly = true )
  private PluginDescriptor plugin;

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
    return name;
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
   * @return non-null list of local files and directories forming the classpath of this context
   * @see net.mdatools.modelant.template.api.TemplateCompilationContext#getClassPathAsList()
   */
  public List<File> getClassPathAsList() throws MalformedURLException {
    List<File> result;

    result = new ArrayList<>();

    for (Artifact artifact: project.getDependencyArtifacts()) {
      if ( artifact.getFile() != null ) { // the dependency is resolved
        result.add( artifact.getFile());
      }
    }
    for (Artifact artifact: plugin.getArtifacts()) {
      if ( artifact.getFile() != null ) { // the dependency is resolved
        result.add( artifact.getFile());
      }
    }
    return result;
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