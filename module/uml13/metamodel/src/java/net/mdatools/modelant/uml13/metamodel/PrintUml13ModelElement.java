/*
 * Copyright (c) 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Created on 14.01.2018
 */
package net.mdatools.modelant.uml13.metamodel;

import net.mdatools.modelant.core.operation.model.topology.ClassCriteria;
import net.mdatools.modelant.core.operation.model.topology.ExceptionCriteria;
import net.mdatools.modelant.core.operation.model.topology.ListCriteria;
import net.mdatools.modelant.core.operation.model.topology.SimpleCriteria;
import net.mdatools.modelant.core.operation.element.PrintElementRestricted;

/**
 * Print UML 1.3 model elements, considering their nesting and most important, e.g. identifying
 * attributes and associations.
 * @author Rusi Popov
 */
public class PrintUml13ModelElement extends PrintElementRestricted {

  private static final ListCriteria PRINT =  new ListCriteria();
  static {
    ListCriteria except;

    PRINT.add(new ClassCriteria("ModelElement", new SimpleCriteria("name", "namespace")));
    PRINT.add(new ClassCriteria("Feature", new SimpleCriteria("", "owner")));
    PRINT.add(new ClassCriteria("Parameter", new SimpleCriteria("", "behavioralFeature")));

    PRINT.add(new ClassCriteria("Association", new SimpleCriteria("connection.name", "connection.type"))); // The associations are identified by the classes they bind and their roles
    PRINT.add(new ClassCriteria("AssociationEnd", new SimpleCriteria("association.name, association.connection.type.name", "type")));

    except = new ListCriteria();
    except.add(new ClassCriteria("Association", new SimpleCriteria("", "namespace")));   // The associations are identified by the classes they bind and their roles
    except.add(new ClassCriteria("AssociationEnd", new SimpleCriteria("", "namespace")));// when printing the association end do not print its association which just would print it

    PRINT.add(new ExceptionCriteria(except));

    PRINT.add(new ClassCriteria("TaggedValue", new SimpleCriteria("tag,value", "modelElement")));

    PRINT.add(new ClassCriteria("Expression", new SimpleCriteria("body", "")));

    PRINT.add(new ClassCriteria("Multiplicity", new SimpleCriteria("", "range")));
    PRINT.add(new ClassCriteria("MultiplicityRange", new SimpleCriteria("lower,upper", "")));

    PRINT.add(new ClassCriteria("StateMachine", new SimpleCriteria("", "context")));
    PRINT.add(new ClassCriteria("StateVertex", new SimpleCriteria("", "container")));
    PRINT.add(new ClassCriteria("State", new SimpleCriteria("", "stateMachine")));
    PRINT.add(new ClassCriteria("Pseudostate", new SimpleCriteria("kind", "container")));
    PRINT.add(new ClassCriteria("Transition", new SimpleCriteria("", "source, target")));
    PRINT.add(new ClassCriteria("Dependency", new SimpleCriteria("", "supplier, client")));

    PRINT.add(new ClassCriteria("Partition", new SimpleCriteria("", "activityGraph")));
    PRINT.add(new ClassCriteria("Guard", new SimpleCriteria("", "expression")));
    PRINT.add(new ClassCriteria("Message", new SimpleCriteria("", "sender, receiver")));
    PRINT.add(new ClassCriteria("ChangeEvent", new SimpleCriteria("", "changeExpression")));
    PRINT.add(new ClassCriteria("SignalEvent", new SimpleCriteria("", "signal")));
    PRINT.add(new ClassCriteria("TimeEvent", new SimpleCriteria("", "when")));

    PRINT.add(new ClassCriteria("ClassifierRole", new SimpleCriteria("", "base")));
    PRINT.add(new ClassCriteria("Stereotype", new SimpleCriteria("baseClass", "")));
    PRINT.add(new ClassCriteria("Signal", new SimpleCriteria("", "context")));
  };

  /**
   */
  public PrintUml13ModelElement() {
    this("");
  }
  /**
   * @param prefix not null common prefix to any line printed, this way supporting
   *        the nesting of printed elements
   */
  public PrintUml13ModelElement(String prefix) {
    super( prefix, PRINT );
  }
}
