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
import java.io.IOException;
import junit.framework.TestCase;
import org.ow2.odis.test.TestUtil;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.ParameterData;
import com.francetelecom.admindm.soap.Fault;
public class EngineTest extends TestCase {
    Engine engine;
    protected void setUp() throws Exception {
        super.setUp();
        engine = new Engine();
    }
    protected void tearDown() throws Exception {
        super.tearDown();
        engine = null;
    }
    public void testPersistance() throws IOException, Fault {
        TestUtil.COMMENT("Verify that download and TransfertComplete "
                + "will persist when store and restore2.");
        TestUtil.TRACE(this);
        Engine enginetmp = new Engine();
        IParameterData data = new ParameterData();
        File f = File.createTempFile("xxx", "tmp");
        enginetmp.setDatafile(f);
        enginetmp.setData(data);
        TransferComplete transfert = new TransferComplete();
        transfert.setCommandKey("terst");
        transfert.setStartTime(12345l);
        transfert.setCompleteTime(782345l);
        enginetmp.getLastTransferComplete().add(transfert);
        enginetmp.storeData();
        Engine enginetmp2 = new Engine();
        enginetmp2.setDatafile(f);
        enginetmp2.restoreData();
        assertEquals(1, enginetmp2.getLastTransferComplete().size());
        assertEquals(transfert, enginetmp2.getLastTransferComplete().get(0));
        f.delete();
    }
    
    /*public void testIsMulticastDownload() throws Exception {
        TestUtil.TRACE(this);
        boolean result;
        result = Engine.isMulticastDownload("http://g-odis-1/toto.html");
        assertFalse(result);
        result = Engine
                .isMulticastDownload("http://239.254.254.254/toto.html");
        assertTrue(result);
        result = Engine.isMulticastDownload("239.254.254.254/toto.html");
        assertTrue(result);
        result = Engine.isMulticastDownload("");
        assertFalse(result);
        result = Engine.isMulticastDownload("//");
        assertFalse(result);
        try {
            result = Engine.isMulticastDownload("http://tarta/toto.html");
            fail("should have a fault");
        } catch (Fault e) {
            System.out.println("OK");
        }
    }*/
}
