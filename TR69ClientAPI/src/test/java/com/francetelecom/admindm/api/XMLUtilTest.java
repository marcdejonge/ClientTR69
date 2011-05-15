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
package com.francetelecom.admindm.api;
import junit.framework.TestCase;
import org.kxml2.kdom.Element;
import org.ow2.odis.test.TestUtil;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.admindm.soap.FaultUtil;
/**
 * The Class XMLUtilTest.
 */
public final class XMLUtilTest extends TestCase {
    /**
     * Test extract value limit case.
     */
    public void testExtractValueLimitCase() {
        TestUtil.TRACE(this);
        try {
            XMLUtil.extractValue(null, null);
            fail("Should throw a Fault");
        } catch (Fault e) {
            assertTrue(e.getFaultcode() == FaultUtil.FAULT_9003);
        }
        try {
            Element test = new Element();
            test.setName("toto");
            XMLUtil.extractValue(test, null);
            fail("Should throw a Fault");
        } catch (Fault e) {
            assertTrue(e.getFaultcode() == FaultUtil.FAULT_9002);
        }
    }
    /**
     * Test extract value nominal case.
     */
    public void testExtractValueNominalCase() {
        TestUtil.TRACE(this);
        try {
            Element test = new Element();
            test.setName("toto");
            Element child = new Element();
            child.setName("test");
            child.setNamespace("");
            test.addChild(Element.ELEMENT,child);
            assertEquals("", XMLUtil.extractValue(test, "test"));
        } catch (Fault e) {
            e.printStackTrace();
            fail("Should not occured.");
        }
    }
    /**
     * Test extract value nominal case.
     */
    public void testExtractValueNominalCase2() {
        TestUtil.TRACE(this);
        try {
            Element test = new Element();
            test.setName("toto");
            Element child = new Element();
            child.setName("test");
            child.setNamespace("");
            child.addChild(Element.TEXT, "typ");
            test.addChild(Element.ELEMENT,child);
            assertEquals("typ", XMLUtil.extractValue(test, "test"));
        } catch (Fault e) {
            e.printStackTrace();
            fail("Should not occured.");
        }
    }
}
