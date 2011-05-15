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

import java.util.Date;
import org.ow2.odis.test.TestUtil;
import com.francetelecom.admindm.soap.Fault;
import junit.framework.TestCase;

public class ParameterTest extends TestCase {
    public void testToString() {
        TestUtil.TRACE(this, "toString and getTextValue must never throw any exception");
        Parameter param;
        //----------
        param = new Parameter();
        param.setType(ParameterType.INT);
        param.setValueWithout(new Integer(10));
        System.out.println(param.toString());
        System.out.println(param.getTextValue(""));
      //----------
        param = new Parameter();
        param.setType(ParameterType.UINT);
        param.setValueWithout(new Integer(10));
        System.out.println(param.toString());
        System.out.println(param.getTextValue(""));
      //----------
        param = new Parameter();
        param.setType(ParameterType.BOOLEAN);
        param.setValueWithout(Boolean.TRUE);
        System.out.println(param.toString());
        System.out.println(param.getTextValue(""));
        //----------
        param = new Parameter();
        param.setType(ParameterType.DATE);
        param.setValueWithout(new Long(0l));
        System.out.println(param.toString());
        System.out.println(param.getTextValue(""));
        //----------
        param = new Parameter();
        param.setType(ParameterType.LONG);
        param.setValueWithout(new Long(4l));
        System.out.println(param.toString());
        System.out.println(param.getTextValue(""));
        //----------
        param = new Parameter();
        param.setType(ParameterType.DATE);
        param.setValueWithout(new Long(45l));
        System.out.println(param.toString());
        System.out.println(param.getTextValue(""));
        //----------
        param = new Parameter();
        param.setType(ParameterType.STRING);
        param.setValueWithout(new Long(0l));
        System.out.println(param.toString());
        System.out.println(param.getTextValue(""));
        //----------
        param = new Parameter();
        param.setType(ParameterType.DATE);
        param.setValueWithout(null);
        System.out.println(param.toString());
        System.out.println(param.getTextValue(""));
        //----------
        param = new Parameter();
        param.setType(ParameterType.LONG);
        param.setValueWithout(null);
        System.out.println(param.toString());
        System.out.println(param.getTextValue(""));
    }
    
    public void testGetBOOLEAN() throws Fault {
        TestUtil.TRACE(this);
        Object result;
        result = Parameter.getBOOLEAN("test", "true");        
        assertEquals(Boolean.TRUE,result);
        result = Parameter.getBOOLEAN("test", "false");        
        assertEquals(Boolean.FALSE,result);
        
    }
    
    public void testGetDate() throws Fault {
        TestUtil.TRACE(this);
        Long result;
        result = Parameter.getDATE("test", "");        
        assertEquals(new Long(-1),result);
        result = Parameter.getDATE("test", "0001-01-01T00:00:00Z");        
        assertEquals(new Long(-1),result);
        result = Parameter.getDATE("test", "2001-01-01T00:00:00Z");        
        Date date = new Date(result.longValue());        
        assertEquals("compare "+date.toLocaleString(),"1 janv. 2001 00:00:00", date.toLocaleString());        
        result = Parameter.getDATE("test", "1001-01-01T00:00:00Z");
        date = new Date(result.longValue());
        assertEquals("compare "+date.toLocaleString(),"1 janv. 1001 00:00:00", date.toLocaleString());        
    }
//    public void testSetType() {
//        fail("Not yet implemented");
//    }
//    public void testSetAccessList() {
//        fail("Not yet implemented");
//    }
//    public void testSetNotification() {
//        fail("Not yet implemented");
//    }
//    public void testSetDirectValue() {
//        fail("Not yet implemented");
//    }
//    public void testSetValueWithout() {
//        fail("Not yet implemented");
//    }
//    public void testGetValueStringStringInt() {
//        fail("Not yet implemented");
//    }
}
