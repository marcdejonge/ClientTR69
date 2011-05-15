/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : 1.1
 * Module Name : ServerComBundle
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
package com.francetelecom.admindm.com;
import java.io.File;
import java.io.FileReader;
import junit.framework.TestCase;
import org.kxml2.io.KXmlParser;
import org.kxml2.kdom.Document;
import org.ow2.odis.test.TestUtil;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.ParameterData;
import com.francetelecom.admindm.soap.Fault;
/**
 * The Class TR69SessionTest.
 */
public class TR69SessionTest extends TestCase {
    /**
     * Test parse.
     * @throws Exception the exception
     */
    public void testParse() throws Exception {
        TestUtil.TRACE(this);
        IParameterData data = new ParameterData();
        MockRPCMng mng = new MockRPCMng();
        TR69Session session = new TR69Session(data, mng, 1);
        File file = new File("./src/test/resources/GetRPCMethod.xml");
        FileReader reader = new FileReader(file);
        KXmlParser parser = new KXmlParser();
        parser.setInput(reader);
        Document doc = new Document();
        doc.parse(parser);
        session.parse(doc);
    }
    
    public void testParse2() throws Exception {
        TestUtil.TRACE(this);
        IParameterData data = new ParameterData();
        MockRPCMng mng = new MockRPCMng();
        TR69Session session = new TR69Session(data, mng, 1);
        File file = new File("./src/test/resources/InformResponse.xml");
        FileReader reader = new FileReader(file);
        KXmlParser parser = new KXmlParser();
        parser.setInput(reader);
        Document doc = new Document();
        doc.parse(parser);
        session.parse(doc);
    }
    public void testParse3() throws Exception {
        TestUtil.COMMENT("[ Bugs #47582 ]Exception in thread ");
        TestUtil.COMMENT("RuntimeException: Document has no root element!");
        TestUtil.COMMENT(" Aim: avoid crash of Com");
        TestUtil.TRACE(this);
        IParameterData data = new ParameterData();
        MockRPCMng mng = new MockRPCMng();
        TR69Session session = new TR69Session(data, mng, 1);
        File file = new File("./src/test/resources/BadMessage.xml");
        FileReader reader = new FileReader(file);
        KXmlParser parser = new KXmlParser();
        parser.setInput(reader);
        Document doc = new Document();
        doc.parse(parser); 
        try {
            session.parse(doc);
            fail("Should throw an exception");
        } catch (Fault e){
            System.out.println(e.getFaultstring());        
            assertEquals(9002,e.getFaultcode());
        }
    }
    public void testParse4() throws Exception {
        TestUtil.TRACE(this);
        IParameterData data = new ParameterData();
        MockRPCMng mng = new MockRPCMng();
        TR69Session session = new TR69Session(data, mng, 1);
        File file = new File("./src/test/resources/ACS.xml");
        FileReader reader = new FileReader(file);
        KXmlParser parser = new KXmlParser();
        parser.setInput(reader);
        Document doc = new Document();
        doc.parse(parser);
        session.parse(doc);
    }
}
