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
/**
 * The Class CheckBooleanTest.
 */
public class CheckBooleanTest extends TestCase {
    /**
     * Test check.
     */
    public void testCheck() {
        TestUtil.TRACE(this);
        CheckBoolean check = CheckBoolean.getInstance();
        try {
            check.check("0");
            check.check("1");
            check.check("true");
            check.check("false");
        } catch (Fault e) {
            fail("Should not have a Fault exception");
        }
        try {
            check.check(null);
            fail("Must have a Fault exception");
        } catch (Fault e) {
        }
        try {
            check.check("TRUE");
            fail("Must have a Fault exception");
        } catch (Fault e) {
        }
        try {
            check.check("FALSE");
            fail("Must have a Fault exception");
        } catch (Fault e) {
        }
    }
}
