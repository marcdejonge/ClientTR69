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

import junit.framework.TestCase;
import org.ow2.odis.test.TestUtil;
import com.francetelecom.admindm.soap.Fault;

public class CheckDateTest extends TestCase {
    public void testCheckWithNull() {
        TestUtil.TRACE(this);
        CheckDate cd = CheckDate.getInstance();
        try {
            cd.check(null);
            fail("should throw a fault");
        } catch (Fault e) {
            System.out.println("Ok");
            System.out.println(e.getFaultcode());
            System.out.println(e.getFaultstring());
        }
    }
    public void testCheckWithBadValue() {
        TestUtil.TRACE(this);
        CheckDate cd = CheckDate.getInstance();
        try {
            cd.check("badvalue");
            fail("should throw a fault");
        } catch (Fault e) {
            System.out.println("Ok");
            System.out.println(e.getFaultcode());
            System.out.println(e.getFaultstring());
        }
    }
    public void testCheckWithBadClass() {
        TestUtil.TRACE(this);
        CheckDate cd = CheckDate.getInstance();
        try {
            cd.check(new Long(90));
            fail("should throw a fault");
        } catch (Fault e) {
            System.out.println("Ok");
            System.out.println(e.getFaultcode());
            System.out.println(e.getFaultstring());
        }
    }
    public void testCheckWithBadValue2() {
        TestUtil.TRACE(this);
        CheckDate cd = CheckDate.getInstance();
        try {
            cd.check(new Long(90));
            fail("should throw a fault");
        } catch (Fault e) {
            System.out.println("Ok");
            System.out.println(e.getFaultcode());
            System.out.println(e.getFaultstring());
        }
    }
    public void testCheckWithUnknownValue() {
        TestUtil.TRACE(this);
        CheckDate cd = CheckDate.getInstance();
        try {
            cd.check("2009-13-01T00:00:00Z");      
            fail("should throw a fault no 13 month");
        } catch (Fault e) {
            System.out.println(e.getFaultcode());
            System.out.println(e.getFaultstring());            
        }
    }
    
    public void testCheckWithUnknownValue3() {
        TestUtil.TRACE(this);
        CheckDate cd = CheckDate.getInstance();
        try {
            cd.check("2009-10-32T00:00:00Z");      
            fail("should throw a fault no 32 days");
        } catch (Fault e) {
            System.out.println(e.getFaultcode());
            System.out.println(e.getFaultstring());            
        }
    }
    public void testCheckWithGoodValue() {
        TestUtil.TRACE(this);
        CheckDate cd = CheckDate.getInstance();
        try {
            cd.check("2009-01-01T00:00:00Z");   
            cd.check("0001-01-01T00:00:00Z");
            cd.check("0101-01-01T00:00:00");
        } catch (Fault e) {
            System.out.println(e.getFaultcode());
            System.out.println(e.getFaultstring());
            fail("should not throw a fault");
        }
    }
    
}
