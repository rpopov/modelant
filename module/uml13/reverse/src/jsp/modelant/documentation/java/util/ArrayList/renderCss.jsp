<%--
 * Copyright (c) 2001,2012 Rusi Popov, MDA Tools.net
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Rusi Popov (popovr@mdatools.net) - initial implementation
 --%><%--
  * This remplate renders the list of all packages shown as the root page in the right frame
--%><%@page wraps="java.util.List"
%>

/* allow the nested and outer borders connect */
td {
    padding-left:0px;
    padding-right:0px;
    padding-bottom: 0px;
}

/* Use 1 pixel for table borders */

/* Outer-most table */
table.visible {
    empty-cells:show;
    border-spacing: 0px 0px;
    border-top: 0px solid #000000;
    border-left: 1px solid #000000;
    border-right: 0px solid #000000;
    border-bottom: 1px solid #000000;
}
table.visible th {
    border-top: 1px solid #000000;
    border-left: 0px solid #000000;
    border-right: 1px solid #000000;
    border-bottom: 0px solid #000000;
}
table.visible tr td {
    border-top: 1px solid #000000;
    border-left: 0px solid #000000;
    border-right: 1px solid #000000;
    border-bottom: 0px solid #000000;
}

/* Nested tables */
td table.visible {
    empty-cells:show;
    border-spacing: 0px 0px;
    border-top: 0px solid #000000;
    border-left: 0px solid #000000;
    border-right: 0px solid #000000;
    border-bottom: 0px solid #000000;
}

td table.visible th:last-of-type {
    border-top: 1px solid #000000;
    border-left: 0px solid #000000;
    border-right: 0px solid #000000;
    border-bottom: 0px solid #000000;
}
td table.visible tr td:last-of-type {
    border-top: 1px solid #000000;
    border-left: 0px solid #000000;
    border-right: 0px solid #000000;
    border-bottom: 0px solid #000000;
}

/* Nested table which is not the last among the nested content in the same TD must have explicitly closed its bottom line */
td table.visible :not(:last-child) tr td {
    border-bottom: 1px solid #000000;
}

pre {
   font-size:  medium;
}

/* Common font and color */
body, th, td {
   font-size:  small;
   font-family: Helvetica, Arial, sans-serif;
   background-color: #FFFFFF;
}