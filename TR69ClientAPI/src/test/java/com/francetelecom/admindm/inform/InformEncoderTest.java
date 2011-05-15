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
package com.francetelecom.admindm.inform;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;
import org.kxml2.io.KXmlSerializer;
import org.kxml2.kdom.Document;
import org.kxml2.kdom.Element;
import org.ow2.odis.test.TestUtil;
import org.xmlpull.v1.XmlSerializer;
import com.francetelecom.admindm.model.EventStruct;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.Parameter;
import com.francetelecom.admindm.model.ParameterData;
import com.francetelecom.admindm.soap.Soap;
public class InformEncoderTest extends TestCase {
    public void testEncodeWithoutAnyValue() throws Exception {
        TestUtil.TRACE(this);
        InformEncoder encoder = new InformEncoder();
        IParameterData data = new ParameterData();
        Element e = encoder.encode(new Inform(data,1));
        Document doc = Soap.getDocument(e, "test");
        XmlSerializer serial = new KXmlSerializer();
        OutputStreamWriter writer = new OutputStreamWriter(System.out);
        serial.setOutput(writer);
        doc.write(serial);
        System.out.println("");
    }
    
    public void testEncodeWithAnyValue() throws Exception {
        TestUtil.TRACE(this);
        InformEncoder encoder = new InformEncoder();
        IParameterData data = new ParameterData();
        List ls= new ArrayList();
        
        ls.add(new EventStruct("test","teste"));
        ls.add(new EventStruct("test2","teste2"));
        
        data.setRoot("Device.");
        Parameter param;
        param = data.createOrRetrieveParameter("Device.DeviceInfo.Manufacturer");        
        param.setDirectValue("orange");
        param = data.createOrRetrieveParameter("Device.DeviceInfo.ManufacturerOUI");
        param.setDirectValue("oOui");
        param = data.createOrRetrieveParameter("Device.DeviceInfo.ProductClass");
        param.setDirectValue("myproduct");
        param = data.createOrRetrieveParameter("Device.DeviceInfo.SerialNumber");
        param.setDirectValue("12345");
        Inform inform = new Inform(data,1);
        inform.setEvent(ls);
        Element e = encoder.encode(inform);
        Document doc = Soap.getDocument(e, "test");
        XmlSerializer serial = new KXmlSerializer();
        OutputStreamWriter writer = new OutputStreamWriter(System.out);
        serial.setOutput(writer);
        doc.write(serial);
        System.out.println("");
    }
}
