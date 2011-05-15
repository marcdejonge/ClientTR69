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
import java.io.File;
import java.io.FileReader;
import junit.framework.TestCase;
import org.kxml2.io.KXmlParser;
import org.kxml2.kdom.Document;
import org.ow2.odis.test.TestUtil;
import com.francetelecom.admindm.api.RPCDecoder;
import com.francetelecom.admindm.download.api.Download;
import com.francetelecom.admindm.soap.Fault;
/**
 * The Class DownloadDecoderTest.
 */
public final class DownloadDecoderTest extends TestCase {
    /**
     * Test decode.
     * @throws Exception the exception
     */
    public void testDecode() throws Exception {
        TestUtil.COMMENT("Each field is complete");
        TestUtil.TRACE(this);
        RPCDecoder decoder = new DownloadDecoder(new MockDownloadEngine());
        File file = new File("./src/test/resources/download_1.xml");
        FileReader reader = new FileReader(file);
        KXmlParser parser = new KXmlParser();
        parser.setInput(reader);
        Document doc = new Document();
        doc.parse(parser);
        decoder.decode(doc.getRootElement());
    }
    /**
     * Test decode empty field.
     * @throws Exception the exception
     */
    public void testDecodeEmptyField() throws Exception {
        TestUtil.COMMENT("Each field is empty");
        TestUtil.TRACE(this);
        RPCDecoder decoder = new DownloadDecoder(new MockDownloadEngine());
        File file = new File("./src/test/resources/download_2.xml");
        FileReader reader = new FileReader(file);
        KXmlParser parser = new KXmlParser();
        parser.setInput(reader);
        Document doc = new Document();
        doc.parse(parser);
        try {
            decoder.decode(doc.getRootElement());
            fail("Should throw a Fault url musn't be empty");
        } catch (Fault e) {
            System.out.println("OK");
        }
    }
    /**
     * Test decode with fault.
     * @throws Exception the exception
     */
    public void testDecodeWithFault() throws Exception {
        TestUtil.COMMENT("Try to set invalid value to check "
                + "the Fault exception");
        TestUtil.COMMENT("- Check with not a number as FileSize");
        TestUtil.COMMENT("- Check with not a number as Delay");
        TestUtil.TRACE(this);
        RPCDecoder decoder = new DownloadDecoder(new MockDownloadEngine());
        File file;
        Document doc;
        // -----------------------
        // FileSize invalid
        file = new File("./src/test/resources/download_3.xml");
        FileReader reader = new FileReader(file);
        KXmlParser parser = new KXmlParser();
        parser.setInput(reader);
        doc = new Document();
        doc.parse(parser);
        try {
            decoder.decode(doc.getRootElement());
            fail("Should have a Fault Exception");
        } catch (Fault e) {
            System.out.println("OK");
        }
        // -----------------------
        // Delay invalid
        file = new File("./src/test/resources/download_4.xml");
        reader = new FileReader(file);
        parser = new KXmlParser();
        parser.setInput(reader);
        doc = new Document();
        doc.parse(parser);
        try {
            decoder.decode(doc.getRootElement());
            fail("Should have a Fault Exception");
        } catch (Fault e) {
            System.out.println("OK");
        }
        // -----------------------
        // URL invalid
        file = new File("./src/test/resources/download_5.xml");
        reader = new FileReader(file);
        parser = new KXmlParser();
        parser.setInput(reader);
        doc = new Document();
        doc.parse(parser);
        try {
            decoder.decode(doc.getRootElement());
            fail("Should have a Fault Exception");
        } catch (Fault e) {
            System.out.println("OK");
        }
        // -----------------------
        // delay empty
        file = new File("./src/test/resources/download_6.xml");
        reader = new FileReader(file);
        parser = new KXmlParser();
        parser.setInput(reader);
        doc = new Document();
        doc.parse(parser);
        try {
            decoder.decode(doc.getRootElement());
        } catch (Fault e) {
            System.out.println(e.getFaultstring());
            fail("Should not have a Fault Exception");
        }
        // -----------------------
        // delay negative
        file = new File("./src/test/resources/download_7.xml");
        reader = new FileReader(file);
        parser = new KXmlParser();
        parser.setInput(reader);
        doc = new Document();
        doc.parse(parser);
        try {
            decoder.decode(doc.getRootElement());            
            fail("Should not have a Fault Exception");
        } catch (Fault e) {
            System.out.println(e.getFaultstring());
            System.out.println("OK");            
        }
    }
    /**
     * Extract filename from Download information.
     */
    public void testExtractFilename() {
        TestUtil.TRACE(this);
        Download download = new Download(new MockDownloadEngine());
        download.setUrl("http://g-lacier:8443/test/toto.txt");
        download.setFileType("1 Firmware Upgrade Image");
        download.setTargetFileName("tuto.txt");
        assertEquals(HTTPDownloadProtocol.extractFilename(download),
                "tuto.txt");
        download.setTargetFileName("");
        assertEquals(HTTPDownloadProtocol.extractFilename(download),
                "toto.txt");
    }
}
