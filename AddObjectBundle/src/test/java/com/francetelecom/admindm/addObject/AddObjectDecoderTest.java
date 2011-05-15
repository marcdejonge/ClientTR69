/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : 1.1
 * Module Name : AddObjectBundle
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
package com.francetelecom.admindm.addObject;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import junit.framework.TestCase;
import org.kxml2.io.KXmlParser;
import org.kxml2.kdom.Document;
import org.ow2.odis.test.TestUtil;
import com.francetelecom.admindm.soap.Fault;

/**
 * The Class AddObjectDecoderTest.
 */
public final class AddObjectDecoderTest extends TestCase {
    
    /**
     * Test decode.
     * @throws Exception the exception
     */
    public void testDecode() throws Exception {
        TestUtil.TRACE(this);
        AddObjectDecoder decoder = new AddObjectDecoder(null);
        File file = new File("./src/test/resources/addObject.xml");        
        FileReader reader = new FileReader(file);
        KXmlParser parser = new KXmlParser();
        parser.setInput(reader);
        Document doc = new Document();
        doc.parse(parser);
        try {
            AddObject result= (AddObject)decoder.decode(doc.getRootElement());
            assertEquals(result.getObjectName(),"ObjectName");
            assertEquals(result.getParameterKey(),"ParameterKey");
        } catch (Fault e) {
            fail("Should not throw a Fault");
        }
    }
}
