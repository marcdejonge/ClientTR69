/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : 1.1
 * Module Name : SetParameterAttributesBundle
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

package com.francetelecom.admindm.setParameterAttributes;

import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;
import org.ow2.odis.test.TestUtil;
import com.francetelecom.admindm.model.Parameter;
import com.francetelecom.admindm.model.ParameterData;
import com.francetelecom.admindm.model.SetParameterAttributesStruct;
import com.francetelecom.admindm.soap.Fault;

/**
 * The Class SetParameterAttributesTest.
 */
public class SetParameterAttributesTest extends TestCase {
    
    /**
     * Test restore param.
     */
    public void testRestoreParam() {
        TestUtil.COMMENT("Aims of this test is to verify that even ");
        TestUtil.COMMENT("if the parameter is change several times");
        TestUtil.COMMENT("the param must be reset to the initial value");
        TestUtil.TRACE(this);
        Parameter param = new Parameter();
        String[] accessList = {"Subscriber"};
        String[] accessList2 = {"Subscriber", "test"};        
        param.setAccessList(accessList);
        param.setNotification(1);
        List lsSave = new ArrayList();
        lsSave.add(new Save(param));
        param.setNotification(4);
        param.setAccessList(accessList2);
        lsSave.add(new Save(param));
        SetParameterAttributes.restoreParam(lsSave);
        assertEquals(param.getNotification(), 1);
        assertEquals(param.getAccessList(), accessList);
    }
    public void testPerformWithNull() throws Exception {
        TestUtil.TRACE(this);
        try {
        SetParameterAttributes spa =new SetParameterAttributes();        
        spa.setParameterList(null);
        ParameterData data = new ParameterData();        
        MockSession session = new MockSession(null);
        session.data = data;
        spa.perform(session);
        fail("Should throw an exception");
        } catch (Fault e){
            System.out.println(e.getFaultstring());
        }        
    }
    public void testPerformWithEmpty() throws Exception {
        TestUtil.TRACE(this);
        try {
        SetParameterAttributes spa =new SetParameterAttributes();        
        spa.setParameterList(null);
        ParameterData data = new ParameterData();        
        MockSession session = new MockSession(null);
        session.data = data;
        SetParameterAttributesStruct[] plist= new SetParameterAttributesStruct[0]; 
        spa.setParameterList(plist);
        spa.perform(session);
        fail("Should throw an exception");
        } catch (Fault e){
            System.out.println(e.getFaultcode());
            System.out.println(e.getFaultstring());
        }        
    }
    public void testPerformWithUnknows() throws Exception {
        TestUtil.TRACE(this);
        try {
        SetParameterAttributes spa =new SetParameterAttributes();        
        spa.setParameterList(null);
        ParameterData data = new ParameterData();        
        MockSession session = new MockSession(null);
        session.data = data;
        SetParameterAttributesStruct[] plist= new SetParameterAttributesStruct[1];
        plist[0] = new SetParameterAttributesStruct();
        String[]accessList ={};
        plist[0].setAccessList(accessList);
        plist[0].setAccessListChange(false);
        plist[0].setNotification(2);
        plist[0].setNotificationChange(true);
        plist[0].setName("device");
        spa.setParameterList(plist);
        spa.perform(session);
        fail("Should throw an exception");
        } catch (Fault e){
            System.out.println(e.getFaultcode());
            System.out.println(e.getFaultstring());
        }
    }
    public void testPerformWithKnows() throws Exception {
        TestUtil.TRACE(this);
        try {
        SetParameterAttributes spa =new SetParameterAttributes();        
        spa.setParameterList(null);
        ParameterData data = new ParameterData();
        data.createOrRetrieveParameter("device");
        MockSession session = new MockSession(null);
        session.data = data;
        SetParameterAttributesStruct[] plist= new SetParameterAttributesStruct[1];
        plist[0] = new SetParameterAttributesStruct();
        String[]accessList ={};
        plist[0].setAccessList(accessList);
        plist[0].setAccessListChange(false);
        plist[0].setNotification(2);
        plist[0].setNotificationChange(true);
        plist[0].setName("device");
        spa.setParameterList(plist);        
        spa.perform(session);        
        assertEquals(SetParameterAttributesResponse.NAME,session.methodResponse.getName());
        } catch (Fault e){                
            System.out.println(e.getFaultstring());
            fail("Should not throw an exception");
        }
    }
    public void testPerformWithOneError() throws Exception {
        TestUtil.COMMENT("Check that a denied active notification is respected,");
        TestUtil.COMMENT("and if an error occured all transaction rollback.");
        TestUtil.TRACE(this);
        Parameter param1=null;
        Parameter param2=null;
        try {
            SetParameterAttributes spa =new SetParameterAttributes();        
        spa.setParameterList(null);
        ParameterData data = new ParameterData();
        param1 = data.createOrRetrieveParameter("device");
        param1.setNotification(1);
        param2 =data.createOrRetrieveParameter("device2");
        param2.setNotification(1);
        param2.setActiveNotificationDenied(true);
        
        MockSession session = new MockSession(null);
        session.data = data;
        SetParameterAttributesStruct[] plist= new SetParameterAttributesStruct[2];
        plist[0] = new SetParameterAttributesStruct();
        String[]accessList ={};
        plist[0].setAccessList(accessList);
        plist[0].setAccessListChange(false);
        plist[0].setNotification(2);
        plist[0].setNotificationChange(true);
        plist[0].setName("device");
        plist[1] = new SetParameterAttributesStruct();
        plist[1].setAccessList(accessList);
        plist[1].setAccessListChange(false);
        plist[1].setNotification(2);
        plist[1].setNotificationChange(true);
        plist[1].setName("device2");
        
        spa.setParameterList(plist);
        
        spa.perform(session);        
        fail("Should throw an error the parameter device2 must refuse active");
        } catch (Fault e){                
            System.out.println(e.getFaultstring());
            assertTrue(param1.getNotification()==1);
            assertTrue(param2.getNotification()==1);
        }
    }

    
    public void testSetAttribute() throws Exception {
        TestUtil.COMMENT("Check that a denied active notification is respected.");
        TestUtil.TRACE(this);
        
        Parameter param = new Parameter();
        param.setName("test");
        param.setNotification(1);
        param.setActiveNotificationDenied(true);
        SetParameterAttributesStruct setParam = new SetParameterAttributesStruct();
        String[]accessList ={};
        setParam.setAccessList(accessList);
        setParam.setAccessListChange(false);
        setParam.setNotification(2);
        setParam.setNotificationChange(true);
        try {
            SetParameterAttributes.setAttribute(param, setParam);
            fail("Should throw a Fault");
        } catch (Fault e) {
            System.out.println(e.getFaultstring());
            System.out.println("Ok");
            assertEquals(1, param.getNotification());
        }
    }
}
