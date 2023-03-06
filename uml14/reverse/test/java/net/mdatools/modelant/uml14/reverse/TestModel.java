/*
 * Copyright c 2018 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package net.mdatools.modelant.uml14.reverse;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Consumer;

import org.omg.uml14.Uml14Package;
import org.omg.uml14.core.AssociationEnd;
import org.omg.uml14.core.Attribute;
import org.omg.uml14.core.Classifier;
import org.omg.uml14.core.Feature;
import org.omg.uml14.core.UmlAssociation;
import org.omg.uml14.core.UmlClass;

import junit.framework.TestCase;
import net.mdatools.modelant.repository.api.ModelRepository;
import net.mdatools.modelant.repository.api.ModelRepositoryFactory;

/**
 * Base class for the reverse engineering tests
 * @author Rusi Popov
 */
abstract class TestModel extends TestCase {

  protected ModelRepository modelRepository;
  protected Uml14Package extent;

  protected void setUp() throws Exception {
    super.setUp();

    modelRepository = ModelRepositoryFactory.construct(new File("target"));
  }

  protected int countAttributes(UmlClass elementType) {
    int result;

    result = 0;
    for (Feature feature : (Collection<Feature>) elementType.getFeature()) {
      result ++;
      assertTrue("Each feature is an attribute", feature instanceof Attribute);
    }
    return result;
  }


  protected int countAssociationsOf(UmlClass elementType) {
    Collection<UmlAssociation> allAssociations;
    int count;
    AssociationEnd thisEnd;
    AssociationEnd otherEnd;
    Iterator<AssociationEnd> ends;

    allAssociations = extent.getCore().getUmlAssociation().refAllOfClass();
    count = 0;
    for (UmlAssociation assoc : allAssociations) {
      ends = assoc.getConnection().iterator();
      thisEnd = ends.next();
      otherEnd = ends.next();

      if (thisEnd.getName().equals(elementType)
          || otherEnd.getName().equals(elementType)) {
        count ++;
      }
    }
    return count;
  }

  protected void checkAssociation(UmlAssociation association,
                                  Classifier type1,
                                  Consumer<AssociationEnd> do1,
                                  Classifier type2,
                                  Consumer<AssociationEnd> do2) {
    for (AssociationEnd end: (Collection<AssociationEnd>) association.getConnection()) {
      if ( type1.equals( end.getName()  ) ) {
        do1.accept( end );

      } else if ( type2.equals( end.getName()  ) ) {
        do2.accept( end );
      }
    }
  }

  /**
   * @throws Exception
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    if (extent != null) {
      modelRepository.writeExtent( extent,
                                   new File("target/"+getName()+".xmi"),
                                   ModelRepository.DEFAULT_XMI_VERSION );
    }
    modelRepository.shutdown();
    super.tearDown();
  }
}