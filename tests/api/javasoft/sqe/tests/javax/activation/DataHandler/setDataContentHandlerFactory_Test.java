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

package	javasoft.sqe.tests.api.javax.activation.DataHandler;

import	java.io.*;
import	java.awt.datatransfer.*;
import	javax.activation.*;
import	com.sun.javatest.*;
import	com.sun.javatest.lib.MultiTest; 
import	javasoft.sqe.tests.api.javax.activation.TestClasses.*;

/** 
 * Use an instance of DataHandler of invoke setDataContentHandlerFactory() api
 * with DataContentHandlerFactory object if this operation is successfull then
 * this testcase passes, otherwise if an exception is thrown then it fails.
 */

public class setDataContentHandlerFactory_Test extends MultiTest
{
private String message = null;

public static void main(String argv[])
{
	setDataContentHandlerFactory_Test lTest = new setDataContentHandlerFactory_Test();
	Status lStatus = lTest.run(argv, new PrintWriter(System.err, true), new PrintWriter(System.out, true));
	lStatus.exit();
}

/*
* Tests setDataContentHandlerFactory:
*/
public Status setDataContentHandlerFactoryTest()
{
    DataHandler dh = new DataHandler(new TestCommandObject(), "foo/goo");
    dh.setCommandMap(new TestCommandMap("foo/goo"));

    if (!checkFlavors(dh, "foo/goo"))
	return Status.failed("DataHandler. using mapped DataContentHandler "  + message);

    // now create and use the factory: Passing the factory a mimeType different
    // from the one in the dh, allows us to verify overriding the TestDCH provided by 
    // the CommandMap by returning a distinct DataFlavor in getTransferDataFlavors.
    // Note that it will cause other Transferable API in DataHandler to fail, so this
    // is a kludge only for this specific test

    DataContentHandlerFactory dchf = new TestDCHFactory("moo/hoo"); // overrides mimeType in returned DataFlavor
    dh.setDataContentHandlerFactory(dchf);		// API TEST

    // This now fails because of bug ID 4108514

    if (!checkFlavors(dh, "moo/hoo"))
	return Status.failed("DataHandler. after setDataContentHandlerFactory "  + message);

    try {	// should even throw for null according to javadoc
	dh.setDataContentHandlerFactory(null);		// API TEST
    } catch (Error err) {
	return Status.passed("DataHandlerTest.dchFactoryTwiceTest succeeded");
    }
 
    return Status.failed("DataHandler.setDataContentHandlerFactory(): second call didn't throw Error");
}
// The only way to determine if a dh is using the appropriate DataContentHandler
// is to see if  getTransferDataFlavors returns what we expect from a TestDCH
// the assumption is that some other

private boolean checkFlavors(DataHandler dh, String mimeType)
{
    DataFlavor dfs[] = dh.getTransferDataFlavors();

    if (dfs == null) {
        message = "getTransferDataFlavors() returned null";
        return false;
    }
    if (dfs.length != 1){
        message = "getTransferDataFlavors() has wrong number of elements";
        return false;
    }
    DataFlavor df = dfs[0];

    if (!df.isMimeTypeEqual(mimeType)){
        message = "getTransferDataFlavors() contains wrong flavor: " + df.getMimeType();
        return false;
    }
    return true;
}

}
