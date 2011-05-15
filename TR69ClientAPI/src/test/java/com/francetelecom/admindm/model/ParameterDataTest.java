/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : 1.1
 * Module Name : TR69ClientAPI
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
 */ 
package com.francetelecom.admindm.model;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import junit.framework.TestCase;
import org.ow2.odis.test.TestUtil;
import com.francetelecom.admindm.soap.Fault;

public class ParameterDataTest extends TestCase {
    
    /**
     * Test store and restore event.
     * @throws Exception the exception
     */
    public void testStoreAndRestoreEvent() throws Exception {
        TestUtil.TRACE(this);
        ParameterData data = new ParameterData();
        File tempFile = File.createTempFile("temporaire", "tmp");
        System.out.println(tempFile.getAbsolutePath());
        data.setEventFile(tempFile);
        EventStruct event1 = new EventStruct("test1","unitaire1");
        EventStruct event2 = new EventStruct("test2","unitaire2");
        EventStruct event3 = new EventStruct("test3","unitaire3");
        data.addEvent(event1);
        data.addEvent(event2);
        data.addEvent(event3);
        // restore on new parameterData
        data = new ParameterData();
        data.setEventFile(tempFile);
        data.restoreEvents();
        Iterator it = data.getEventIterator();
        EventStruct restore;
        restore = (EventStruct) it.next();
        assertEquals(restore.getEventCode(), event1.getEventCode());
        restore = (EventStruct) it.next();
        assertEquals(restore.getEventCode(), event2.getEventCode());
        restore = (EventStruct) it.next();
        assertEquals(restore.getEventCode(), event3.getEventCode());
    }
    
    public void testStart() throws Exception {
        ParameterData data = new ParameterData();        
        data.setModelLoaded(true);
    }
    
    ParameterData initData() throws Fault{
        ParameterData data = new ParameterData();
        Parameter param;
        //-----------
        param = data.createOrRetrieveParameter("Device.");
        param.setType(ParameterType.ANY);
        //-----------        
        param = data.createOrRetrieveParameter("Device.Manufacturer.");
        param.setType(ParameterType.ANY);
        //-----------
        param = data.createOrRetrieveParameter("Device.Manufacturer.name");
        param.setType(ParameterType.STRING);
        param.setValue("nameValue");
        //-----------        
        param = data.createOrRetrieveParameter("Device.Manufacturer.type");
        param.setType(ParameterType.STRING);
        param.setValue("typeValue");
        return data;
    }
    public void testExtractParameterListWithFullPath() throws Exception {
        TestUtil.TRACE(this);
        ParameterData data = initData();
        List ls = data.extractParameterList("Device.Manufacturer.name");
        assertEquals(1,ls.size());
        Parameter param = (Parameter)ls.get(0);
        assertEquals(param.getName(), "Device.Manufacturer.name");
        assertEquals(param.getValue(), "nameValue");
    }
    public void testExtractParameterListWithPartialPath() throws Exception {
        TestUtil.TRACE(this);
        ParameterData data = initData();
        List ls = data.extractParameterList("Device.Manufacturer.");
        assertEquals(3,ls.size());
        Parameter param;
        param = (Parameter)ls.get(0);
        assertEquals(param.getName(), "Device.Manufacturer.");
        param = (Parameter)ls.get(1);
        assertEquals(param.getName(), "Device.Manufacturer.name");
        assertEquals(param.getValue(), "nameValue");
        param = (Parameter)ls.get(2);
        assertEquals(param.getName(), "Device.Manufacturer.type");
        assertEquals(param.getValue(), "typeValue");
    }
    
    
    public void testExtractParameterListWithUnknowsParameter() throws Exception{
        TestUtil.TRACE(this);
        ParameterData data = initData();
        try {
            data.extractParameterList("Device.Manufacturer.unknow");
            fail("should throws an error");
        } catch(Fault e){
            System.out.println("Fault :"+e.getFaultcode());
            System.out.println(e.getFaultstring());
            System.out.println("OK");
        }
        try {
            // test with a partial path
            data.extractParameterList("Device.Manufacturer.Test.");
            fail("should throws an error");
        } catch(Fault e){
            System.out.println("Fault :"+e.getFaultcode());
            System.out.println(e.getFaultstring());
            System.out.println("OK");
        }        
    }
    
}
