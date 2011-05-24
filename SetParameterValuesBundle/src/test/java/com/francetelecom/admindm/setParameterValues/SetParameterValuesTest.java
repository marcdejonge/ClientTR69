/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : 1.1
 * Module Name : SetParameterValuesBundle
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

package com.francetelecom.admindm.setParameterValues;
import java.io.File;
import java.io.FileReader;
import java.util.Iterator;
import java.util.List;
import junit.framework.TestCase;
import org.kxml2.io.KXmlParser;
import org.kxml2.kdom.Document;
import org.ow2.odis.test.TestUtil;
import com.francetelecom.admindm.api.StorageMode;
import com.francetelecom.admindm.model.CheckBoolean;
import com.francetelecom.admindm.model.CheckLength;
import com.francetelecom.admindm.model.CheckMaximum;
import com.francetelecom.admindm.model.CheckMinimum;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.Parameter;
import com.francetelecom.admindm.model.ParameterData;
import com.francetelecom.admindm.model.ParameterType;
import com.francetelecom.admindm.model.ParameterValueStruct;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.admindm.soap.FaultUtil;
import com.francetelecom.admindm.soap.SetParamValuesFault;
/**
 * The Class SetParameterValuesTest.
 */
public final class SetParameterValuesTest extends TestCase {
    /**
     * Test perform with empty.
     * @throws Exception the exception
     */
    public void testPerformWithEmpty() throws Exception {
        TestUtil.TRACE(this);
        ParameterValueStruct[] pParameterList = new ParameterValueStruct[0];
        String pParameterKey = "test";
        SetParameterValues spv;
        spv = new SetParameterValues(pParameterList, pParameterKey);
        MockSession session = new MockSession(null);
        try {
            spv.perform(session);
        } catch (Exception e) {
            fail("Should not throws an error");
        }
        assertEquals("test", session.getParameterData().getParameterKey());
    }
    /**
     * Test perform with double value.
     * @throws Exception the exception
     */
    public void testPerformWithDoubleValue() throws Exception {
        TestUtil.TRACE(this);
        ParameterValueStruct[] parameterList = new ParameterValueStruct[2];
        parameterList[0] = new ParameterValueStruct("test", "t");
        parameterList[1] = new ParameterValueStruct("test", "t");
        String pParameterKey = "test";
        SetParameterValues spv;
        spv = new SetParameterValues(parameterList, pParameterKey);
        MockSession session = new MockSession(null);
        try {
            spv.perform(session);
            fail("Should throws an Fault");
        } catch (Fault e) {
            assertEquals(FaultUtil.FAULT_9003, e.getFaultcode());
        }
    }
    /**
     * Test perform with unknown value.
     * @throws Exception the exception
     */
    public void testPerformWithUnknownValue() throws Exception {
        TestUtil.TRACE(this);
        ParameterValueStruct[] parameterList = new ParameterValueStruct[1];
        parameterList[0] = new ParameterValueStruct("test", "t");
        String pParameterKey = "test";
        SetParameterValues spv;
        spv = new SetParameterValues(parameterList, pParameterKey);
        MockSession session = new MockSession(null);
        try {
            spv.perform(session);
            fail("Should throws an Fault");
        } catch (Fault e) {
            System.out.println(e.getFaultstring());
            List ls = e.getLsSetParamValuesFaults();
            Iterator it = ls.iterator();
            while (it.hasNext()) {
                SetParamValuesFault t = (SetParamValuesFault) it.next();
                System.out.println("==>" + t.getFaultstring());
                assertEquals(FaultUtil.FAULT_9005, t.getFaultcode());
            }
            assertEquals(FaultUtil.FAULT_9003, e.getFaultcode());
        }
    }
    /**
     * Test perform with a Boolean parameter.
     * @throws Exception the exception
     */
    public void testPerformWithBooleanParameter() throws Exception {
        TestUtil.TRACE(this);
        ParameterValueStruct[] parameterList = new ParameterValueStruct[1];
        parameterList[0] = new ParameterValueStruct(
                "InternetGateway.isBoolean", "true");
        String pParameterKey = "test";
        SetParameterValues spv;
        spv = new SetParameterValues(parameterList, pParameterKey);
        MockSession session = new MockSession(null);
        IParameterData data = session.getParameterData();
        Parameter param;
        param = data.createOrRetrieveParameter("InternetGateway.isBoolean");
        param.setType(ParameterType.BOOLEAN);
        param.setWritable(true);
        param.setValue(Boolean.FALSE);
        try {
            spv.perform(session);
            assertEquals(Boolean.TRUE, param.getValue());
        } catch (Fault e) {
            System.out.println(e.getFaultstring());
            List ls = e.getLsSetParamValuesFaults();
            Iterator it = ls.iterator();
            while (it.hasNext()) {
                SetParamValuesFault t = (SetParamValuesFault) it.next();
                System.out.println("==>" + t.getFaultstring());
            }
            fail("Should not throws an Fault");
        }
    }
    /**
     * Test perform with a Integer parameter.
     * @throws Exception the exception
     */
    public void testPerformWithIntegerParameter() throws Exception {
        TestUtil.TRACE(this);
        ParameterValueStruct[] parameterList = new ParameterValueStruct[1];
        parameterList[0] = new ParameterValueStruct("InternetGateway.test",
                "16");
        String pParameterKey = "test";
        SetParameterValues spv;
        spv = new SetParameterValues(parameterList, pParameterKey);
        MockSession session = new MockSession(null);
        IParameterData data = session.getParameterData();
        Parameter param;
        param = data.createOrRetrieveParameter("InternetGateway.test");
        param.setType(ParameterType.INT);
        param.setWritable(true);
        param.setValue(new Integer(12));
        try {
            spv.perform(session);
            assertEquals(new Integer(16), param.getValue());
        } catch (Fault e) {
            System.out.println(e.getFaultstring());
            List ls = e.getLsSetParamValuesFaults();
            Iterator it = ls.iterator();
            while (it.hasNext()) {
                SetParamValuesFault t = (SetParamValuesFault) it.next();
                System.out.println("==>" + t.getFaultstring());
            }
            fail("Should not throws an Fault");
        }
    }
    /**
     * Test perform with a Integer parameter.
     * @throws Exception the exception
     */
    public void testPerformWithLongParameter() throws Exception {
        TestUtil.TRACE(this);
        ParameterValueStruct[] parameterList = new ParameterValueStruct[1];
        parameterList[0] = new ParameterValueStruct("InternetGateway.test",
                "16");
        String pParameterKey = "test";
        SetParameterValues spv;
        spv = new SetParameterValues(parameterList, pParameterKey);
        MockSession session = new MockSession(null);
        IParameterData data = session.getParameterData();
        Parameter param;
        param = data.createOrRetrieveParameter("InternetGateway.test");
        param.setType(ParameterType.LONG);
        param.setWritable(true);
        param.setValue(new Long(12));
        try {
            spv.perform(session);
            assertEquals(new Long(16), param.getValue());
        } catch (Fault e) {
            System.out.println(e.getFaultstring());
            List ls = e.getLsSetParamValuesFaults();
            Iterator it = ls.iterator();
            while (it.hasNext()) {
                SetParamValuesFault t = (SetParamValuesFault) it.next();
                System.out.println("==>" + t.getFaultstring());
            }
            fail("Should not throws an Fault");
        }
    }
    /**
     * Test perform with a read only parameter.
     * @throws Exception the exception
     */
    public void testPerformWithAReadOnlyParameter() throws Exception {
        TestUtil.TRACE(this);
        ParameterValueStruct[] parameterList = new ParameterValueStruct[1];
        parameterList[0] = new ParameterValueStruct("InternetGateway.test",
                "16");
        String pParameterKey = "test";
        SetParameterValues spv;
        spv = new SetParameterValues(parameterList, pParameterKey);
        MockSession session = new MockSession(null);
        IParameterData data = session.getParameterData();
        Parameter param;
        param = data.createOrRetrieveParameter("InternetGateway.test");
        param.setType(ParameterType.LONG);
        param.setWritable(false);
        param.setValue(new Long(12));
        try {
            spv.perform(session);
            fail("Should throw an Fault");
        } catch (Fault e) {
            System.out.println(e.getFaultstring());
            List ls = e.getLsSetParamValuesFaults();
            Iterator it = ls.iterator();
            while (it.hasNext()) {
                SetParamValuesFault t = (SetParamValuesFault) it.next();
                System.out.println("==>" + t.getFaultstring());
            }
        }
        assertEquals(new Long(12), param.getValue());
    }
    
    /**
     * Test perform with a bad value.
     * @throws Exception the exception
     */
    public void testPerformWithABadValue() throws Exception {
        TestUtil.TRACE(this);
        ParameterValueStruct[] parameterList = new ParameterValueStruct[1];
        parameterList[0] = new ParameterValueStruct("InternetGateway.test",
                "-16");
        String pParameterKey = "test";
        SetParameterValues spv;
        spv = new SetParameterValues(parameterList, pParameterKey);
        MockSession session = new MockSession(null);
        IParameterData data = session.getParameterData();
        Parameter param;
        param = data.createOrRetrieveParameter("InternetGateway.test");
        param.setType(ParameterType.UINT);
        param.setWritable(true);
        param.setValue(new Long(12));
        try {
            spv.perform(session);
            fail("Should throw an Fault");
        } catch (Fault e) {
            System.out.println(e.getFaultstring());
            List ls = e.getLsSetParamValuesFaults();
            Iterator it = ls.iterator();
            while (it.hasNext()) {
                SetParamValuesFault t = (SetParamValuesFault) it.next();
                System.out.println("==>" + t.getFaultstring());
            }
        }
        assertEquals(new Long(12), param.getValue());
    }

    /**
     * Test perform with a read only parameter and two valid parameters. We need
     * to test if the initial value is restored.
     * @throws Exception the exception
     */
    public void testAtomicityOfSet() throws Exception {
        TestUtil.TRACE(this);
        ParameterValueStruct[] parameterList = new ParameterValueStruct[3];
        parameterList[0] = new ParameterValueStruct("InternetGateway.test",
                "16");
        parameterList[1] = new ParameterValueStruct("InternetGateway.test1",
                "15");
        parameterList[2] = new ParameterValueStruct("InternetGateway.test2",
                "14");
        String pParameterKey = "test";
        SetParameterValues spv;
        spv = new SetParameterValues(parameterList, pParameterKey);
        MockSession session = new MockSession(null);
        IParameterData data = session.getParameterData();
        Parameter param;
        Parameter param1;
        Parameter param2;
        param = data.createOrRetrieveParameter("InternetGateway.test");
        param.setType(ParameterType.LONG);
        param.setWritable(true);
        param.setValue(new Long(0));
        param1 = data.createOrRetrieveParameter("InternetGateway.test1");
        param1.setType(ParameterType.LONG);
        param1.setWritable(false);
        param1.setValue(new Long(1));
        param2 = data.createOrRetrieveParameter("InternetGateway.test2");
        param2.setType(ParameterType.LONG);
        param2.setWritable(true);
        param2.setValue(new Long(2));
        try {
            spv.perform(session);
            fail("Should throw an Fault");
        } catch (Fault e) {
            System.out.println(e.getFaultstring());
            List ls = e.getLsSetParamValuesFaults();
            Iterator it = ls.iterator();
            while (it.hasNext()) {
                SetParamValuesFault t = (SetParamValuesFault) it.next();
                System.out.println("==>" + t.getFaultstring());
            }
        }
        assertEquals(new Long(0), param.getValue());
        assertEquals(new Long(1), param1.getValue());
        assertEquals(new Long(2), param2.getValue());
    }
    public void testPeriodicInform() throws Exception{
        TestUtil.TRACE(this);
        File file = new File("./src/test/resources/setParam.xml");
        FileReader reader = new FileReader(file);
        KXmlParser parser = new KXmlParser();
        parser.setInput(reader);
        Document doc = new Document();
        doc.parse(parser);
        SetParameterValuesDecoder decoder = new SetParameterValuesDecoder();
        SetParameterValues spv = (SetParameterValues)decoder.decode(doc.getRootElement());
        
        MockSession session = new MockSession(null);
        ParameterData data = new ParameterData();
        session.data = data;
        Parameter param ;        
        param = data.createOrRetrieveParameter("Device.ManagementServer.PeriodicInformEnable");
        param.setNotification(0);
        param.setType(ParameterType.BOOLEAN);
        param.addCheck(CheckBoolean.getInstance());
        param.setWritable(true);
        param.setValue(Boolean.FALSE);
        try {
            spv.perform(session);
        } catch (Fault e) {
            System.out.println(e.toString());
        }
        System.out.println(param.getValue());
    }
    
    public void testBug48219_1() throws Exception {
        TestUtil.TRACE(this);
        ParameterValueStruct[] parameterList = new ParameterValueStruct[1];
        parameterList[0] = new ParameterValueStruct("Device.PeriodicInformInterval","0");        
        String pParameterKey = "test";
        SetParameterValues spv;
        spv = new SetParameterValues(parameterList, pParameterKey);
        MockSession session = new MockSession(null);
        IParameterData data = session.getParameterData();
        
        Parameter param;        
        param = data.createOrRetrieveParameter("Device.PeriodicInformInterval");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.UINT);
        param.addCheck(new CheckMinimum(0));
        param.addCheck(new CheckMaximum(4294967295L));
        param.setValue(new Long(0));
        param.addCheck(new CheckMinimum(1));
        param.setWritable(true);
        try {
            spv.perform(session);
            fail("Should throw an Fault");
        } catch (Fault e) {
            System.out.println(e.getFaultstring());
            List ls = e.getLsSetParamValuesFaults();
            Iterator it = ls.iterator();
            while (it.hasNext()) {
                SetParamValuesFault t = (SetParamValuesFault) it.next();
                System.out.println("==>" + t.getFaultstring());
            }
        }
    }

    public void testBug48219_2() throws Exception {
        TestUtil.TRACE(this);
        ParameterValueStruct[] parameterList = new ParameterValueStruct[1];
        parameterList[0] = new ParameterValueStruct("Device.KickURL","test");        
        String pParameterKey = "test";
        SetParameterValues spv;
        spv = new SetParameterValues(parameterList, pParameterKey);
        MockSession session = new MockSession(null);
        IParameterData data = session.getParameterData();
        
        Parameter param;
        param = data.createOrRetrieveParameter("Device." + "KickURL");        
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setType(ParameterType.STRING);
        param.setValue("");
        param.setWritable(false);
        try {
            spv.perform(session);
            fail("Should throw an Fault");
        } catch (Fault e) {
            System.out.println(e.getFaultstring());
            List ls = e.getLsSetParamValuesFaults();
            Iterator it = ls.iterator();
            while (it.hasNext()) {
                SetParamValuesFault t = (SetParamValuesFault) it.next();
                System.out.println("==>" + t.getFaultstring());
            }
        }
    }
    public void testBug48219_3() throws Exception {
        TestUtil.TRACE(this);
        ParameterValueStruct[] parameterList = new ParameterValueStruct[1];
        
        StringBuffer value = new StringBuffer("");
        for (int i =0 ;i<257;i++) {
            value.append("a");
        }
        
        parameterList[0] = new ParameterValueStruct("Device.Password",value.toString());        
        
        String pParameterKey = "";
        SetParameterValues spv;
        spv = new SetParameterValues(parameterList, pParameterKey);
        MockSession session = new MockSession(null);
        IParameterData data = session.getParameterData();
        
        Parameter param;        
        param = data.createOrRetrieveParameter("Device.Password");
        param.setNotification(0);
        param.setStorageMode(StorageMode.DM_ONLY);
        param.setHidden(true);
        param.setType(ParameterType.STRING);
        param.addCheck(new CheckLength(256));
        param.setValue("");
        param.setWritable(true);
        try {
            spv.perform(session);
            fail("Should throw an Fault");
        } catch (Fault e) {
            System.out.println(e.getFaultstring());
            List ls = e.getLsSetParamValuesFaults();
            Iterator it = ls.iterator();
            while (it.hasNext()) {
                SetParamValuesFault t = (SetParamValuesFault) it.next();
                System.out.println("==>" + t.getFaultstring());
            }
        }
    }

    /**
     * Test perform with a Integer parameter.
     * @throws Exception the exception
     */
    public void testPerformWithDateParameter() throws Exception {
        TestUtil.TRACE(this);
        ParameterValueStruct[] parameterList = new ParameterValueStruct[1];
        parameterList[0] = new ParameterValueStruct("Device.ManagementServer.PeriodicInformTime",
                "0001-01-01T00:00:00Z");
        String pParameterKey = "test";
        SetParameterValues spv;
        spv = new SetParameterValues(parameterList, pParameterKey);
        MockSession session = new MockSession(null);
        IParameterData data = session.getParameterData();
        Parameter param;
        param = data.createOrRetrieveParameter("Device.ManagementServer.PeriodicInformTime");
        param.setType(ParameterType.DATE);
        param.setWritable(true);
        param.setValue(new Long(100));
        try {
            spv.perform(session);
            assertEquals(new Long(-1), param.getValue());
        } catch (Fault e) {
            System.out.println(e.getFaultstring());
            List ls = e.getLsSetParamValuesFaults();
            Iterator it = ls.iterator();
            while (it.hasNext()) {
                SetParamValuesFault t = (SetParamValuesFault) it.next();
                System.out.println("==>" + t.getFaultstring());
            }
            fail("Should not throws an Fault");
        }
    }
    
}
