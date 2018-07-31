/*
 * Copyright (c) i:FAO AG 2018. All Rights Reserved.
 *
 * This SOURCE CODE FILE, which has been provided by i:FAO AG as part
 * of a product of i:FAO AG for use ONLY by licensed users of the product,
 * includes CONFIDENTIAL and PROPRIETARY information.
 *
 * Created on 5.03.2018 г.
 */
package net.mdatools.modelant.core.operation.model.export;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.jmi.reflect.RefObject;

import net.mdatools.modelant.core.api.diff.AssociationDifference;
import net.mdatools.modelant.core.api.diff.Export;
import net.mdatools.modelant.core.api.diff.InstanceDifference;
import net.mdatools.modelant.core.api.diff.ModelComparisonResult;
import net.mdatools.modelant.core.api.diff.ModelDifference;
import net.mdatools.modelant.core.operation.element.PrintModelElement;
import net.mdatools.modelant.core.util.Navigator;

/**
 * Export the results of models comparison in a JSON-like structured text
 * @author Rusi Popov
 */
public class StructuredTextExport implements Export {

  private static final String INDENT = "  ";

  private static final String ATTRIBUTE_QUALIFIED_NAME = "qualifiedName";

  /**
   * @see net.mdatools.modelant.core.api.diff.Export#export(net.mdatools.modelant.core.api.diff.ModelComparisonResult)
   */
  public void export(ModelComparisonResult modelDiff) {
    StringWriter result;
    PrintWriter out;

    result = new StringWriter();
    out = new PrintWriter(result);

    try {
      out.println("{");

      out.print(INDENT);
      out.print("deleted = ");
      exportModelDiff(out, indent(6), modelDiff.getDeleted());
      out.println(",");

      out.print(INDENT);
      out.print("added = ");
      exportModelDiff(out, indent(6), modelDiff.getAdded());
      out.println(",");

      out.print(INDENT);
      out.print("changed = ");
      exportInstances(out, indent(6), modelDiff.getChanged());
      out.println(",");

      out.print(INDENT);
      out.print("exactMatch = ");
      exportInstances(out, indent(6), modelDiff.getExactlyMatched());
      out.println(",");

      out.print(INDENT);
      out.print("exactMatchSize = ");
      out.print( modelDiff.getExactlyMatched().size());

      out.println();
      out.println("}");
    } finally {
      out.close();
    }
    System.out.println( result.toString() );
  }

  /**
   * Print the difference assuming it is not on a new line, does end with EOLN
   * @param out
   * @param indent
   * @param diff
   */
  private void export(PrintWriter out, String indent, InstanceDifference diff) {
    PrintModelElement print;

    print = new PrintModelElement(indent+indent(6));

    out.print( indent );
    out.print( "{" );
    out.print( "xObject = " );
    out.print( print.execute( diff.getXObject() ) );
    out.println(",");

    out.print( indent );
    out.print( "yObject = " );
    out.print( print.execute( diff.getYObject() ) );
    out.println(",");

    out.print( indent );
    out.print( "attributesWithDifferences = " );
    out.print( diff.getAttributesWithDifferences());
    out.println(",");

    out.print( indent );
    out.print( "associationDiffs =" );
    export( out, indent+indent(6), diff.getAssociationDiffs());
    out.print( "}" );
  }

  /**
   * Export a diff, assuming it starts on a new line, ends on a line, not terminating it with EOLN
   * @param out
   * @param indent
   * @param diff
   */
  private void export(PrintWriter out, String indent, ModelDifference diff) {
    Map<String, Collection<ModelDifference>> associations;

    out.print( indent );
    out.print("{ ");
    out.print( new PrintModelElement( indent+indent(2) ).execute( diff.getElement() ) );

    associations = diff.getAssociations();
    for (Map.Entry<String, Collection<ModelDifference>> entry : associations.entrySet()) {
      out.println( "," );

      out.print( indent+INDENT );
      out.print( "in the role of ");
      out.print( entry.getKey());
      out.print( " for " );
      exportModelDiff( out, indent+indent(5), entry.getValue() );
    }
    out.print("}");
  }

  private void export(PrintWriter out, String indent, List<AssociationDifference> diffs) {
    PrintModelElement print;

    print = new PrintModelElement(indent+indent(6));

    boolean first;

    out.print( "{" );
    first = true;
    for (AssociationDifference diff : diffs) {
      if ( !first) {
        out.print(",");
      }
      first = false;
      out.println();

      out.print( indent );
      out.print( "{" );
      out.print("associationName = ");
      out.print( diff.getAssociationName() );
      out.println( "," );

      out.print( indent );
      out.print("xMinusY = ");
      out.print( print.execute(diff.getXMinusY()));
      out.println( "," );

      out.print( indent );
      out.print("yMinusX = ");
      out.print( print.execute(diff.getYMinusX()));
      out.print( "}" );
    }
    out.print("}");
  }

  /**
   * Export a list of model differences, assuming it starts on a new line, ends without EOLN
   * @param out not null
   * @param indent not null indent of anything printed
   * @param diffs
   */
  private void exportModelDiff(PrintWriter out, String indent, Collection<ModelDifference> diffs) {
    boolean first;
    List<ModelDifference> sorted;

    sorted = new ArrayList<>(diffs);
    Collections.sort( sorted, new OrderModelDifferences() );

    out.print( "{" );
    first = true;
    for (ModelDifference diff : sorted) {
      if ( !first) {
        out.print(",");
      }
      first = false;
      out.println();
      export(out, indent, diff);
    }
    out.print("}");
  }

  /**
   *
   * @param out
   * @param indent
   * @param diffs
   */
  private void exportInstances(PrintWriter out, String indent, List<InstanceDifference> diffs) {
    boolean first;

    Collections.sort( diffs, new OrderInstanceDifferences() );

    out.print( "{" );
    first = true;
    for (InstanceDifference diff : diffs) {
      if ( !first) {
        out.print(",");
      }
      first = false;
      out.println();
      export(out, indent, diff);
    }
    out.print("}");
  }

  private String indent(int steps) {
    StringBuilder result;

    result = new StringBuilder(128);
    for (int i = 0; i < steps; i++) {
      result.append( INDENT );
    }
    return result.toString();
  }

  /**
   * Compare model elements by class and qualified name
   * @param element1 not null
   * @param element2 not null
   * @return int indicating the order of element1 vs element2
   */
  static int orderModelElements(RefObject element1, RefObject element2) {
    int result;

    result = Navigator.getMetaClassName(element1)
                      .compareTo( Navigator.getMetaClassName( element2) );

    if (result == 0) {
      result =  element1
                 .refGetValue( ATTRIBUTE_QUALIFIED_NAME )
                 .toString()
                 .compareTo( element2
                               .refGetValue( ATTRIBUTE_QUALIFIED_NAME )
                               .toString() );
    }
    return result;
  }

  /**
   * Compare by model differences by element's class name and qualified name
   */
  static class OrderModelDifferences implements Comparator<ModelDifference> {
    public int compare(ModelDifference o1, ModelDifference o2) {
      return orderModelElements( o1.getElement(), o2.getElement() );
    }
  }

  /**
   * Compare by class name and
   */
  static class OrderInstanceDifferences implements Comparator<InstanceDifference> {
    public int compare(InstanceDifference o1, InstanceDifference o2) {
      return orderModelElements( o1.getXObject(), o2.getXObject() );
    }
  }
}