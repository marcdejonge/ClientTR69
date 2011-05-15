/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : 1.1
 * Module Name : AddObjectBundle
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
package com.francetelecom.admindm.addObject;
import java.io.File;
import junit.framework.TestCase;
import org.ow2.odis.test.TestUtil;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.Parameter;
import com.francetelecom.admindm.model.ParameterData;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.admindm.soap.FaultUtil;
/**
 * The Class AddObjectTest.
 */
public class AddObjectTest extends TestCase {
    /**
     * Test perform with an unknown object.
     * @throws Exception the exception
     */
    public void testPerformWithAnUnknownObject() throws Exception {
        TestUtil.TRACE(this);
        File file = File.createTempFile("test", "data");
        AddObject addObject = new AddObject(file, "test.", "test");
        MockSession session = new MockSession(null);
        try {
            addObject.perform(session);
            fail("Should throw a Fault");
        } catch (Fault e) {
            assertEquals(FaultUtil.FAULT_9005, e.getFaultcode());
            System.out.println("OK");
        }
    }
    /**
     * Test perform with an invalid object name.
     * @throws Exception the exception
     */
    public void testPerformWithAnInvalidObjectName() throws Exception {
        TestUtil.TRACE(this);
        File file = File.createTempFile("test", "data");
        AddObject addObject = new AddObject(file, "test", "test");
        MockSession session = new MockSession(null);
        try {
            addObject.perform(session);
            fail("Should throw a Fault");
        } catch (Fault e) {
            System.out.println(e.getFaultstring());
            assertEquals(FaultUtil.FAULT_9003, e.getFaultcode());
            System.out.println("OK");
        }
    }
    /**
     * Test perform with an object without factory.
     * @throws Exception the exception
     */
    public void testPerformWithAnObjectWithoutFactory() throws Exception {
        TestUtil.TRACE(this);
        File file = File.createTempFile("test", "data");
        AddObject addObject = new AddObject(file, "InternetGateway.test.",
                "test");
        MockSession session = new MockSession(null);
        IParameterData data = session.getParameterData();
        data.createOrRetrieveParameter("InternetGateway.test.");
        try {
            addObject.perform(session);
            fail("Should throw a Fault");
        } catch (Fault e) {
            System.out.println(e.getFaultstring());
            assertEquals(FaultUtil.FAULT_9003, e.getFaultcode());
            System.out.println("OK");
        }
    }
    /**
     * Test generate object id.
     * @throws Exception the exception
     */
    public void testGenerateObjectId() throws Exception {
        TestUtil.COMMENT("should generate 1,3,4 sequence because the 2 is "
                + "already present");
        TestUtil.TRACE(this);
        File file = File.createTempFile("test", "data");
        AddObject addObject = new AddObject(file, "InternetGateway.test.",
                "test");
        ParameterData data = new ParameterData();
        Parameter params;
        params = data.createOrRetrieveParameter("InternetGateway.test.");
        MockFactory f = new MockFactory("InternetGateway.test.");
        params.setFactory(f);
        f.createObjectInstance(2, data);
        long id = addObject.generateObjectId("InternetGateway.test.", data);
        assertEquals(1, id);
        id = addObject.generateObjectId("InternetGateway.test.", data);
        assertEquals(3, id);
        id = addObject.generateObjectId("InternetGateway.test.", data);
        assertEquals(4, id);
        addObject = new AddObject(file, "InternetGateway.test.", "test");
        id = addObject.generateObjectId("InternetGateway.test.", data);
        assertEquals(5, id);
    }
    /**
     * Test perform.
     * @throws Exception the exception
     */
    public void testPerform() throws Exception {
        TestUtil.TRACE(this);
        File file = File.createTempFile("test", "data");
        AddObject addObject = new AddObject(file, "InternetGateway.test.",
                "test");
        MockSession session = new MockSession(null);
        IParameterData data = session.getParameterData();
        Parameter params = data
                .createOrRetrieveParameter("InternetGateway.test.");
        params.setFactory(new MockFactory("InternetGateway.test."));
        addObject.perform(session);
        assertNotNull(data.getParameter("InternetGateway.test.1."));
        assertNotNull(data.getParameter("InternetGateway.test.1.isCreated"));
        addObject.perform(session);
        assertNotNull(data.getParameter("InternetGateway.test.2."));
        assertNotNull(data.getParameter("InternetGateway.test.2.isCreated"));
    }
}
