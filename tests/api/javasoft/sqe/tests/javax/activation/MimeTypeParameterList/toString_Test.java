/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package	javasoft.sqe.tests.api.javax.activation.MimeTypeParameterList;

import	java.beans.*;
import	java.io.*;
import	javax.activation.*;
import	java.net.*;
import	com.sun.javatest.*;
import com.sun.javatest.lib.MultiTest; 
import	java.awt.datatransfer.*;
import	javasoft.sqe.tests.api.javax.activation.TestClasses.*;

/**
 * Create an instance of MimeTypeParameterList, then use it to call toString() method,
 * if it returns a string representation of this object, then this testcase passes,
 * otherwise it fails.
 */

public class toString_Test extends MultiTest
{
public static void main(String argv[])
{
	toString_Test lTest = new toString_Test();
	Status lStatus = lTest.run(argv, new PrintWriter(System.err, true), new PrintWriter(System.out, true));
	lStatus.exit();
}

public Status toStringTest()
{
        try {
              MimeTypeParameterList mtpl1 =  new MimeTypeParameterList(";charset=us-ascii;isoset=iso-9000;foo=abc");

              if( mtpl1 == null )
                  return Status.failed("Failed: to create MimeTypeParameterList object");
	      String tostr = mtpl1.toString();	// API TEST

	      if( tostr == null )
		  return Status.failed("toString() returned unexpected null string.");

	      MimeTypeParameterList mtpl2 = new MimeTypeParameterList();

	      if( mtpl2 == null )
                  return Status.failed("Failed: to create MimeTypeParameterList object");

              tostr = mtpl2.toString();       // API TEST

              if( tostr == null )
                  return Status.failed("toString() returned unexpected null string.");

        } catch(Exception ex) {
                return Status.failed("toString() threw " + ex.toString());
        }

	return Status.passed("toString() test succeeded");
}

}
