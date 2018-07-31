package net.mdatools.modelant.uml13.maven.javauml;

import static net.mdatools.modelant.uml13.reverse.ReverseEngineerJavaDoclet.PARAMETER_MODEL;
import static net.mdatools.modelant.uml13.reverse.ReverseEngineerJavaDoclet.PARAMETER_TARGET_FILE;
import static net.mdatools.modelant.uml13.reverse.ReverseEngineerJavaDoclet.PARAMETER_WORK_DIR;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.descriptor.PluginDescriptor;
import org.apache.maven.plugins.javadoc.AbstractJavadocMojo;
import org.apache.maven.plugins.javadoc.JavadocJar;
import org.apache.maven.plugins.javadoc.options.DocletArtifact;
import org.apache.maven.reporting.MavenReportException;

import net.mdatools.modelant.uml13.reverse.ReverseEngineerJavaDoclet;

/**
 * Reverse engineer to UML 1.3 the java sources aggregated with their dependencies (when configured).
 */
public class ReverseEngineerJavaMojo extends JavadocJar {

  /**
   * The dependency where the doclet is
   */
  private static final String DOCLET_ARTIFACT_ID = "modelant.uml13.reverse";


  /* INVARIANT:
   * 1. AbstractJavadocMojo cannot be extended directly due to the abstract default-level method doExcute().
   * 2. The plugin.xml is already generated, therefore no annotations are used.
   * 3. The idea to have the dependencies injected in private fields makes impossible instantiating and
   *    delegating to the superclass and imposes the use of reflection
   *    to inject doclet artifact, which cannot be set in plugin.xml (obviously Maven uses the plugin.xml element's text
   *    only an never instantiates structures as field initializers.
   * 4. The artifact with the doclet implementation is a dependency of this plugin.
   * 5. There are no additionalJOptions, additionalOptions to be set in Maven files.
   */

  /**
   * Where the work files are located
   *
   * @parameter expression="${project.build.directory}"
   */
  private File workDirectory;


  /**
   * The file path and name of the model to produce
   *
   * @parameter expression="${project.build.directory}/model.xml"
   */
  private File modelFile;

  /**
   * @see org.apache.maven.plugins.javadoc.AbstractJavadocMojo#isAggregator()
   */
  protected boolean isAggregator() {
    return true;
  }


  /**
   * @see org.apache.maven.plugins.javadoc.JavadocJar#doExecute()
   */
  public void doExecute() throws MojoExecutionException {
  }


  /**
   * @throws MojoExecutionException
   * @throws MojoFailureException
   * @see org.apache.maven.plugins.javadoc.AbstractJavadocMojo#execute()
   */
  public void execute() throws MojoExecutionException, MojoFailureException {
    configure();

    if ( failOnError ) {
      try {
        executeReport( Locale.getDefault() );

      } catch (MavenReportException e) {
        throw new MojoExecutionException(e.getMessage(), e);
      }
    } else {
      try {
        executeReport( Locale.getDefault() );

      } catch (Exception e) {
        getLog().error(e.getClass().getSimpleName()+": Error while generating Javadoc: " + e.getMessage(), e );
      }
    }
  }

  /**
   * See the class invariant
   * @throws MojoFailureException
   */
  private void configure() throws MojoFailureException {
    String[] options;
    List<String> optionsList;
    DocletArtifact docletArtifact;

    docletArtifact = lookupDocletArtifactInPluginDescriptor();

    setPrvateFieldValue( "docletArtifact", docletArtifact );
    setPrvateFieldValue( "doclet", ReverseEngineerJavaDoclet.class.getName() );
    setPrvateFieldValue( "stylesheet", "maven" );
    setPrvateFieldValue( "applyJavadocSecurityFix", Boolean.FALSE);

    // Define doclet parameters
    options = getPrvateFieldValue( "additionalJOptions");
    if ( options != null ) {
      optionsList = new ArrayList<>(Arrays.asList( options ));
    } else {
      optionsList = new ArrayList<>();
    }
    optionsList.add( "-J-D"+PARAMETER_MODEL+"="+getProject().getArtifactId()+":"+getProject().getVersion());
    optionsList.add( "-J-D"+PARAMETER_WORK_DIR+"="+workDirectory.getAbsolutePath());
    optionsList.add( "-J-D"+PARAMETER_TARGET_FILE+"="+modelFile);

    setPrvateFieldValue( "additionalJOptions", optionsList.toArray( new String[0] ) );
  }

  /**
   * @return non-null artifact where the doclet class is
   * @throws MojoFailureException when not found
   */
  private DocletArtifact lookupDocletArtifactInPluginDescriptor() throws MojoFailureException {
    DocletArtifact result;
    PluginDescriptor descriptor;
    Iterator<Artifact> artifactIterator;
    Artifact artifact;

    descriptor = (PluginDescriptor) getPluginContext().get("pluginDescriptor");

    result = null;
    artifactIterator = descriptor.getArtifacts().iterator();
    while (result == null && artifactIterator.hasNext()) {
      artifact = artifactIterator.next();

      if ( DOCLET_ARTIFACT_ID.equals(artifact.getArtifactId()) ) {
        result = new DocletArtifact();

        result.setGroupId( artifact.getGroupId() );
        result.setArtifactId( artifact.getArtifactId() );
        result.setVersion( artifact.getVersion() );
      }
    }
    if ( result == null ) {
      throw new MojoFailureException("Missing "+DOCLET_ARTIFACT_ID+" artifact");
    }
    return result;
  }


  private void setPrvateFieldValue(String fieldName, Object value) throws MojoFailureException {
    Field result;
    Field[] fields;

    try {
      result = null;
      fields = AbstractJavadocMojo.class.getDeclaredFields();

      for (int i=0; result == null && i<fields.length; i++ ) {
        if ( fieldName.equals( fields[i].getName() ) ) {
          result = fields[i];
        }
      }
      if ( result == null ) {
        throw new IllegalArgumentException("Field \""+fieldName+"\" cannot be found in "+getClass());
      }
      result.setAccessible( true );
      result.set( this, value );

    } catch (Exception ex) {
      throw new MojoFailureException( "Setting "+fieldName+" to "+value+" caused:", ex );
    }
  }

  private <T> T getPrvateFieldValue(String fieldName) throws MojoFailureException {
    T result;
    Field resultField;
    Field[] fields;

    try {
      resultField = null;
      fields = AbstractJavadocMojo.class.getDeclaredFields();

      for (int i=0; resultField == null && i<fields.length; i++ ) {
        if ( fieldName.equals( fields[i].getName() ) ) {
          resultField = fields[i];
        }
      }
      if ( resultField == null ) {
        throw new IllegalArgumentException("Field \""+fieldName+"\" cannot be found in "+getClass());
      }
      resultField.setAccessible( true );
      result = (T) resultField.get( this);

    } catch (Exception ex) {
      throw new MojoFailureException( "Getting "+fieldName+" caused:", ex );
    }
    return result;
  }
}