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
import junit.framework.TestCase;
import org.ow2.odis.test.TestUtil;
import com.francetelecom.admindm.download.api.Download;
/**
 * The Class HTTPDownloadProtocolTest.
 */
public final class HTTPDownloadProtocolTest extends TestCase {
    /**
     * Test is prefix compatible.
     */
    public void testIsPrefixCompatible() {
        TestUtil.TRACE(this);
        HTTPDownloadProtocol protocol = new HTTPDownloadProtocol();
        assertTrue(protocol.isPrefixCompatible("http://"));
        assertTrue(protocol.isPrefixCompatible("HTTP://"));
        assertFalse(protocol.isPrefixCompatible("ftp://"));
        assertFalse(protocol.isPrefixCompatible("https://"));
    }
    /**
     * Test on http download.
     * @throws Exception the exception
     */
    /*public void testOnHTTPDownload() throws Exception {
        TestUtil.TRACE(this);
        Download download = new Download(new MockDownloadEngine());
        File f = new File("shell-1.0.0.RC2.jar");
        if (f.exists()) {
            System.out.println("delete old test.jar");
            f.delete();
        }
        download.setUrl("http://xxxx/shell-1.0.0.RC2.jar");
        download.setPassword("xxxxx");
        download.setUsername("xxxxx");
        download.setFileType("1 Firmware Upgrade Image");
        download.setTargetFileName("shell-1.0.0.RC2.jar");
        new HTTPDownloadProtocol().onDownload(download);
        if (!f.exists()) {
            fail("download failed");
        }
    }*/
    /**
     * Test on http download with htt ppass.
     * @throws Exception the exception
     */
   /* public void testOnHTTPDownloadWithHTTPpass() throws Exception {
        TestUtil.TRACE(this);
        Download download = new Download(new MockDownloadEngine());
        File f = new File("livre.gif");
        if (f.exists()) {
            System.out.println("delete old livre.gif");
            f.delete();
        }
        download.setUrl("http://xxxx/html/livre.gif");
        download.setFileType("1 Firmware Upgrade Image");
        download.setPassword("mobo7205");
        download.setUsername("mobo7205");
        download.setTargetFileName("livre.gif");
        new HTTPDownloadProtocol().onDownload(download);
        if (!f.exists()) {
            fail("download failed");            
        }
        f.delete();
    }*/
    // public void testOnHTTPDownloadWithHTTPS() throws Exception {
    // TestUtil.TRACE(this);
    // Download download = new Download();
    // File f=new File ("toto.txt");
    // if (f.exists()){
    // System.out.println("delete old livre.gif");
    // f.delete();
    // }
    // download.setUrl("https://g-lacier:8443/test/toto.txt ");
    // download.setFileType("1 Firmware Upgrade Image");
    // download.setTargetFileName("toto.txt");
    // new HTTPDownloadProtocol().onDownload(download);
    // if (!f.exists()){
    // fail("download failed");
    // }
    //
    // }
    /**
     * Test on download session manager.
     * @throws Exception the exception
     */
    /*public void testOnDownloadSessionManager() throws Exception {
        TestUtil.TRACE(this);
        Download download = new Download(new MockDownloadEngine());
        File f = new File("DownloadBundle-0.0.1-SNAPSHOT.jar");
        if (f.exists()) {
            System.out.println("delete old DownloadBundle-0.0.1-SNAPSHOT.jar");
            f.delete();
        }
        download.setUrl("http://xxxx/DownloadBundle-0.0.1-SNAPSHOT.jar");
        download.setFileType("1 Firmware Upgrade Image");
        download.setPassword("mobo7205");
        download.setUsername("mobo7205");
        download.setTargetFileName("");
        new HTTPDownloadProtocol().onDownload(download);
        if (!f.exists()) {
            fail("download failed");
        }
        f.delete();
    }*/
}
