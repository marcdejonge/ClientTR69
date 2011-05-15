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

import java.io.File;
import java.io.FileReader;
import junit.framework.TestCase;
import org.kxml2.io.KXmlParser;
import org.kxml2.kdom.Document;
import org.kxml2.kdom.Element;
import com.francetelecom.admindm.api.RPCDecoder;

public class FaultDecoderTest extends TestCase {


    RPCDecoder decoder = new FaultDecoder();
    
    public void testDecode() throws Exception {
        File file = new File("./src/test/resources/Fault2.xml");        
        FileReader reader = new FileReader(file);
        KXmlParser parser = new KXmlParser();
        parser.setInput(reader);
        Document doc = new Document();
        doc.parse(parser);
        try {
            for (int i=0;i<doc.getRootElement().getChildCount();i++)
            {
                Element e=doc.getRootElement().getElement(i);
                if (e!=null) System.out.println(e.getNamespace()+":"+e.getName());
            }
            Element d= doc.getRootElement().getElement("","soapenv:Body");
            d=d.getElement(1);
            Fault result= (Fault)decoder.decode(d);
            
            assertEquals(result.getFaultcode(),9001);
            assertEquals(result.getFaultstring(),"Request Denied");
        } catch (Fault e) {
            e.printStackTrace();
            fail("Should not throw a Fault");
            
        }
    }
}
