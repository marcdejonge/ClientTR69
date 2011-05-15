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
package com.francetelecom.admindm.inform;
import java.util.Calendar;
import java.util.GregorianCalendar;
import junit.framework.TestCase;
import org.ow2.odis.test.TestUtil;
import com.francetelecom.admindm.model.Parameter;
import com.francetelecom.admindm.model.ParameterData;
import com.francetelecom.admindm.model.ParameterType;
import com.francetelecom.admindm.soap.Soap;
/**
 * The Class ScheduleInformTest.
 */
public final class ScheduleInformTest extends TestCase {
    /**
     * Test compute next occurrence delay.
     * @throws Exception the exception
     */
    public void testComputeNextOccurencyDelay() throws Exception {
        TestUtil.TRACE(this);
        Parameter param = new Parameter();
        param.setName("InternetGatewayDevice.ManagementServer."
                + "PeriodicInformInterval");
        param.setType(ParameterType.UINT);
        param.setValue(new Long(10));
      //----------------
        ParameterData parameterData = new ParameterData();
        parameterData.addOrUpdateParameter(param, "ACS");

        param = new Parameter();
        param.setName("InternetGatewayDevice.ManagementServer."
                + "PeriodicInformEnable");
        param.setType(ParameterType.BOOLEAN);
        param.setValue(Boolean.FALSE);
        parameterData.addOrUpdateParameter(param, "ACS");
        //----------------
        param = new Parameter();
        param.setName("InternetGatewayDevice.ManagementServer."
                + "PeriodicInformTime");
        param.setType(ParameterType.LONG);
        //param.setValue(new Long(20));
        param.setValue(new Long(1246912733054L));
        parameterData.addOrUpdateParameter(param, "ACS");
        //----------------
        ScheduleInform si = new ScheduleInform(parameterData);
        long start = 1246912733000L;
        //long start = System.currentTimeMillis();
        //---------------- 
        long delay = si.computeNextOccurenceDelay(start);
        System.out.println("delay:" + delay);
        delay = si.computeNextOccurenceDelay(start);
        System.out.println("delay:" + delay);
        delay = si.computeNextOccurenceDelay(start);
        System.out.println("delay:" + delay);
        assertTrue(delay > 0);
    }

    public void testComputeNextOccurencyDelay2() throws Exception {
        TestUtil.TRACE(this);
        Parameter param = new Parameter();
        param.setName("InternetGatewayDevice.ManagementServer."
                + "PeriodicInformInterval");
        param.setType(ParameterType.UINT);
        param.setValue(new Long(10));
      //----------------
        ParameterData parameterData = new ParameterData();
        parameterData.addOrUpdateParameter(param, "ACS");

        param = new Parameter();
        param.setName("InternetGatewayDevice.ManagementServer."
                + "PeriodicInformEnable");
        param.setType(ParameterType.BOOLEAN);
        param.setValue(Boolean.FALSE);
        parameterData.addOrUpdateParameter(param, "ACS");
        //----------------
        param = new Parameter();
        param.setName("InternetGatewayDevice.ManagementServer."
                + "PeriodicInformTime");
        param.setType(ParameterType.LONG);
        param.setValue(new Long(20));
        parameterData.addOrUpdateParameter(param, "ACS");
        //----------------
        ScheduleInform si = new ScheduleInform(parameterData);
        long start = 1246912733054L;
        //----------------
        long delay = si.computeNextOccurenceDelay(start);
        System.out.println("delay:" + delay);
        delay = si.computeNextOccurenceDelay(start);
        System.out.println("delay:" + delay);
        delay = si.computeNextOccurenceDelay(start);
        System.out.println("delay:" + delay);
        assertTrue(delay >= 0);
        assertTrue(delay <= 10000);
        Calendar cal = new GregorianCalendar();
        cal.set(2000, 8, 12);        
        param.setValue(new Long(1246912000000L));
        parameterData.addOrUpdateParameter(param, "ACS");
        start =cal.getTimeInMillis(); 
        delay = si.computeNextOccurenceDelay(start);
        assertTrue(delay >= 0);
        assertTrue(delay <= 10000);
        System.out.println("delay:" + delay);
    }
}
