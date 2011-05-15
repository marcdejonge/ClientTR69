/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : 1.1
 * Module Name : DownloadBundle
 *
 * Copyright © 2011 France Telecom
 *
 * This software is distributed under the Apache License, Version 2.0
 * (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 or see the "license.txt" file for
 * more details
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Author : Orange Labs R&D O.Beyler
 */
package com.francetelecom.admindm.download;
import junit.framework.TestCase;
import org.ow2.odis.test.TestUtil;
import com.francetelecom.admindm.soap.Fault;
/**
 * The Class TransferCompleteResponseTest.
 */
public final class TransferCompleteResponseTest extends TestCase {
    /** The engine. */
    IEngine engine;
    /**
     * Sets the up.
     * @throws Exception the exception
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        engine = new MockDownloadEngine();
    }
    /**
     * Test get name.
     */
    public void testGetName() {
        TestUtil.TRACE(this);
        TransferCompleteResponse tr = new TransferCompleteResponse(engine);
        assertEquals("TransferCompleteResponse", tr.getName());
    }
    /**
     * Test perform bad case.
     * @throws Fault the fault
     */
    public void testPerformBadCase() throws Fault {
        TestUtil.TRACE(this);
        TransferCompleteResponse tr = new TransferCompleteResponse(engine);
        try {
            tr.perform(new MockSession(null));
            fail("should throw a Fault :"
                    + "Must have a TransfertComplete as last RPCMethod");
        } catch (Fault e) {
            System.out.println("Ok");
        }
        try {
            tr.perform(new MockSession(new TransferCompleteResponse(engine)));
            fail("should throw a Fault :"
                    + "Must have a TransfertComplete as last RPCMethod");
        } catch (Fault e) {
            System.out.println("Ok");
        }
    }
    /**
     * Test perform nominal case.
     * @throws Fault the fault
     */
    public void testPerformNominalCase() throws Fault {
        TestUtil.TRACE(this);
        TransferComplete tc = new TransferComplete();
        TransferCompleteResponse tr = new TransferCompleteResponse(engine);
        tr.perform(new MockSession(tc));
    }
}
