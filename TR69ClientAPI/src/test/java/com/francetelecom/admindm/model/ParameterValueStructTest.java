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

import java.io.OutputStreamWriter;
import junit.framework.TestCase;
import org.kxml2.io.KXmlSerializer;
import org.kxml2.kdom.Document;
import org.ow2.odis.test.TestUtil;
import org.xmlpull.v1.XmlSerializer;
import com.francetelecom.admindm.soap.Soap;

public class ParameterValueStructTest extends TestCase {
    public void testEncoded() throws Exception {
        TestUtil.TRACE(this);
        ParameterValueStruct pvs = new ParameterValueStruct("name","value",ParameterType.STRING);
        Document doc = Soap.getDocument(pvs.encoded(), "test");
        XmlSerializer serial = new KXmlSerializer();
        OutputStreamWriter writer = new OutputStreamWriter(System.out);
        serial.setOutput(writer);
        doc.write(serial);
        System.out.println("");
    }
}
