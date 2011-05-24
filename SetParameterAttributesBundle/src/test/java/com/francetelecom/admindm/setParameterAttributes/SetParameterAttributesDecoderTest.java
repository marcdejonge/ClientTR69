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

import java.io.File;
import java.io.FileReader;
import javax.swing.JPopupMenu.Separator;
import junit.framework.TestCase;
import org.kxml2.io.KXmlParser;
import org.kxml2.kdom.Document;
import org.ow2.odis.test.TestUtil;
import com.francetelecom.admindm.api.RPCDecoder;
import com.francetelecom.admindm.model.SetParameterAttributesStruct;
import com.francetelecom.admindm.soap.Fault;

public class SetParameterAttributesDecoderTest extends TestCase {    
    /**
     * Test decode empty field.
     * @throws Exception the exception
     */
    public void testDecode() throws Exception {
        TestUtil.COMMENT("Each field is empty");
        TestUtil.TRACE(this);
        RPCDecoder decoder = new SetParameterAttributesDecoder();

        File file = new File("./src/test/resources/SetParameterAttributes.xml");
        FileReader reader = new FileReader(file);
        KXmlParser parser = new KXmlParser();
        parser.setInput(reader);
        Document doc = new Document();
        doc.parse(parser);
        SetParameterAttributes spa;
        try {
            spa = (SetParameterAttributes)decoder.decode(doc.getRootElement()); 
            assertEquals(2,spa.getParameterList().length);
            SetParameterAttributesStruct st ;
         // test first item value
            st = spa.getParameterList()[0];
            assertEquals(true,st.isNotificationChange());
            assertEquals(0, st.getNotification());
            assertEquals(true,st.isAccessListChange());
            
         // test second item value
            
            st = spa.getParameterList()[1];
            assertEquals(false,st.isNotificationChange());
            assertEquals(2, st.getNotification());
            assertEquals(false,st.isAccessListChange());
        } catch (Fault e) {
            fail("Should  not throw a Fault " + e.getFaultstring());
        }
        
    }
    /**
     * Test decode empty field.
     * @throws Exception the exception
     */
    public void testDecodeEmptyField() throws Exception {
        TestUtil.COMMENT("Each field is empty");
        TestUtil.TRACE(this);
        RPCDecoder decoder = new SetParameterAttributesDecoder();
        
        File file = new File("./src/test/resources/SetParameterAttributesEmpty.xml");
        FileReader reader = new FileReader(file);
        KXmlParser parser = new KXmlParser();
        parser.setInput(reader);
        Document doc = new Document();
        doc.parse(parser);

        try {
            decoder.decode(doc.getRootElement());
            fail("Should throw a Fault : some tag miss");
        } catch (Fault e) {
            System.out.println(e.getFaultstring());
            System.out.println("OK");
        }
    }
    
    
    /**
     * Test decode empty field.
     * @throws Exception the exception
     */
    public void testDecode1Item() throws Exception {
        TestUtil.COMMENT("Only One ParameterStruct");
        TestUtil.TRACE(this);
        RPCDecoder decoder = new SetParameterAttributesDecoder();

        File file = new File("./src/test/resources/trameTest.xml");
        FileReader reader = new FileReader(file);
        KXmlParser parser = new KXmlParser();
        parser.setInput(reader);
        Document doc = new Document();
        doc.parse(parser);
        SetParameterAttributes spa;
        try {
            spa = (SetParameterAttributes)decoder.decode(doc.getRootElement()); 
            assertEquals(1,spa.getParameterList().length);
            SetParameterAttributesStruct st ;
         // test first item value
            st = spa.getParameterList()[0];
            assertEquals(true,st.isNotificationChange());
            assertEquals(1, st.getNotification());
            assertEquals(false,st.isAccessListChange());
            } catch (Fault e) {
            fail("Should  not throw a Fault " + e.getFaultstring());
        }
        
    }
    
}
