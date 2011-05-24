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
import junit.framework.TestCase;
import org.kxml2.io.KXmlParser;
import org.kxml2.kdom.Document;
import org.kxml2.kdom.Element;
import org.ow2.odis.test.TestUtil;
import com.francetelecom.admindm.soap.Fault;
/**
 * The Class SetParameterValuesDecoderTest.
 */
public final class SetParameterValuesDecoderTest extends TestCase {
    /** The decoder. */
    private SetParameterValuesDecoder decoder;
    
    /**
     * Test decode with Invalid Value.
     * @throws Exception the exception
     */
    public void testDecodeWithInvalidValue() throws Exception {
        TestUtil.TRACE(this);
        decoder = new SetParameterValuesDecoder();
        try {
            decoder.decode(null);
            fail("should throw a Fault Exception");
        } catch (Fault e) {
            System.out.println(e.getFaultstring());
            System.out.println("OK");
        }
        try {
            Element ele =new Element();
            ele.setName("SetParameterValues");
            decoder.decode(ele);
            fail("should throw a Fault Exception");
        } catch (Fault e) {
            System.out.println(e.getFaultstring());
            System.out.println("OK");
        }
    }
    /**
     * Test decode with Invalid Value.
     */
    public void testDecodeNominalCase() throws Exception {
        TestUtil.TRACE(this);
        File file = new File("./src/test/resources/setParameterValues.xml");
        FileReader reader = new FileReader(file);
        KXmlParser parser = new KXmlParser();
        parser.setInput(reader);
        Document doc = new Document();
        doc.parse(parser);
        
        
        decoder = new SetParameterValuesDecoder();
        SetParameterValues spv = (SetParameterValues)decoder.decode(doc.getRootElement());
        assertEquals("ParameterKey",spv.getParameterKey());
        assertEquals(2, spv.getParameterList().length);
        
        assertEquals("Name",spv.getParameterList()[0].getName());
        assertEquals("Value",spv.getParameterList()[0].getValue());
        assertEquals("Name2",spv.getParameterList()[1].getName());
        assertEquals("Value2",spv.getParameterList()[1].getValue());
        
    }
    /**
     * Test decode parameter value struct.
     */
    public void testDecodeParameterValueStruct() throws Exception {
        TestUtil.TRACE(this);
        decoder = new SetParameterValuesDecoder();
        Element element = new Element();
        element.setName("ParameterValueStruct");
        Element eName=new Element();
        eName.setName("Name");
        Element eValue=new Element();
        eValue.setName("Value");
        element.addChild(Element.ELEMENT, eName);
        element.addChild(Element.ELEMENT, eValue);
        try {
            decoder.decodeParameterStruct(element);
        } catch (Fault e) {
            e.printStackTrace();
            fail("should not throw a Fault Exception" + e.getFaultstring());
        }
    }
    
    /**
     * Test decode parameter value struct.
     */
    public void testDecodeParameterValueStructWithFault() throws Exception {
        TestUtil.COMMENT("Test with a parameter key too long");
        TestUtil.TRACE(this);
        File file = new File("./src/test/resources/setParamWithFault.xml");
        FileReader reader = new FileReader(file);
        KXmlParser parser = new KXmlParser();
        parser.setInput(reader);
        Document doc = new Document();
        doc.parse(parser);
        
        decoder = new SetParameterValuesDecoder();
        
        try {
            decoder.decodeParameterStruct(doc.getRootElement());
            fail("should throw a Fault Exception");
        } catch (Fault e) {
            System.out.println(e.getFaultstring());            
        }
    }

}
