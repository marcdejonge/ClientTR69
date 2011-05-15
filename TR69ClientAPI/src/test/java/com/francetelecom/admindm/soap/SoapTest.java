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
package com.francetelecom.admindm.soap;

import junit.framework.TestCase;
import org.ow2.odis.test.TestUtil;

public class SoapTest extends TestCase {

    public void testConvertDate2String() {
        TestUtil.TRACE(this);
        System.out.println(Soap.convertDate2String(System.currentTimeMillis()));
    }

    public void testConvertDate2Long() throws Exception {
        TestUtil.TRACE(this);        
        long start = System.currentTimeMillis();
        start = (start / 1000) * 1000; // to eliminate the millisecond
        System.out.println("start value" + start);
        String date = Soap.convertDate2String(start);
        System.out.println(date);
        long end = Soap.convertDate2Long(date);
        System.out.println("end value" + end);
        assertTrue(start == end);
    }

}
