/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : 1.1
 * Module Name : DownloadBundle
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
package com.francetelecom.admindm.download;
import java.io.OutputStreamWriter;
import junit.framework.TestCase;
import org.kxml2.io.KXmlSerializer;
import org.kxml2.kdom.Document;
import org.ow2.odis.test.TestUtil;
import org.xmlpull.v1.XmlSerializer;
import com.francetelecom.admindm.api.RPCEncoder;
import com.francetelecom.admindm.download.api.DownloadResponse;
import com.francetelecom.admindm.soap.Soap;
/**
 * The Class DownloadResponseEncoderTest.
 */
public final class DownloadResponseEncoderTest extends TestCase {
    /**
     * Test encode.
     * @throws Exception the exception
     */
    public void testEncode() throws Exception {
        TestUtil.TRACE(this);
        DownloadResponse tc;
        tc = new DownloadResponse();
        RPCEncoder encoder = new DownloadResponseEncoder();
        Document doc = Soap.getDocument(encoder.encode(tc), "test");
        XmlSerializer serial = new KXmlSerializer();
        OutputStreamWriter writer = new OutputStreamWriter(System.out);
        serial.setOutput(writer);
        doc.write(serial);
        System.out.println("");
    }
}
