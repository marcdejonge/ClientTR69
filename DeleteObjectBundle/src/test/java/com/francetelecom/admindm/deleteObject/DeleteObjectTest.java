/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : 1.1
 * Module Name : DeleteObjectBundle
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
package com.francetelecom.admindm.deleteObject;
import junit.framework.TestCase;
import org.ow2.odis.test.TestUtil;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.Parameter;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.admindm.soap.FaultUtil;
/**
 * The Class DeleteObjectTest.
 */
public class DeleteObjectTest extends TestCase {
    /**
     * Test perform with unknown parameter.
     */
    public void testPerformWithUnknownParameter() {
        TestUtil.TRACE(this);
        DeleteObject deleteObject = new DeleteObject("test", "tt");
        MockSession session = new MockSession(null);
        try {
            deleteObject.perform(session);
            fail("Should throws a fault");
        } catch (Fault e) {
            System.out.println(e.getFaultstring());
            assertEquals(FaultUtil.FAULT_9005, e.getFaultcode());
            System.out.println("OK");
        }
    }
    
    /**
     * Test perform with read only parameter.
     */
    public void testPerformWithReadOnlyParameter() throws Exception {
        TestUtil.TRACE(this);
        DeleteObject deleteObject = new DeleteObject("InternetGateway.test.", "tt");
        MockSession session = new MockSession(null);
        IParameterData data = session.getParameterData();
        Parameter param ;
        param = data.createOrRetrieveParameter("InternetGateway.test.");
        param.setWritable(false);
        try {
            deleteObject.perform(session);
            fail("Should throws a fault");
        } catch (Fault e) {
            System.out.println(e.getFaultstring());
            assertEquals(FaultUtil.FAULT_9005, e.getFaultcode());
            System.out.println("OK");
        }
    }
    /**
     * Test perform with read only parameter.
     */
    public void testPerformNominal() throws Exception {
        TestUtil.TRACE(this);
        DeleteObject deleteObject = new DeleteObject("InternetGateway.test.1.", "tt");
        MockSession session = new MockSession(null);
        IParameterData data = session.getParameterData();
        Parameter param ;
        Parameter param1 ;
        Parameter param2 ;
        param = data.createOrRetrieveParameter("InternetGateway.test.1.");
        param.setWritable(true);
        param1 = data.createOrRetrieveParameter("InternetGateway.test.1.titi");
        param1.setWritable(false);
        param2 = data.createOrRetrieveParameter("InternetGateway.test.1.tutu");
        param2.setWritable(true);
        try {
            deleteObject.perform(session);        
        } catch (Fault e) {
            System.out.println(e.getFaultstring());
            e.printStackTrace();
            fail("Should not throws a fault");            
        }               
        assertNull(data.getParameter("InternetGateway.test.1."));
        assertNull(data.getParameter("InternetGateway.test.1.titi"));
        assertNull(data.getParameter("InternetGateway.test.1.tutu"));        
    }



}
