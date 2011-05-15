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
import junit.framework.TestCase;
import org.ow2.odis.test.TestUtil;
import com.francetelecom.admindm.MockSession;
import com.francetelecom.admindm.RPCMethodMng;
import com.francetelecom.admindm.api.EventCode;
import com.francetelecom.admindm.model.EventStruct;
import com.francetelecom.admindm.model.IParameterData;
/**
 * The Class InformResponseTest.
 */
public class InformResponseTest extends TestCase {
    /**
     * Test events management.
     * @throws Exception the exception
     */
    public void testEventsManagement() throws Exception {
        TestUtil.TRACE(this);
        RPCMethodMng.getInstance();
        MockSession session = new MockSession();
        IParameterData data = session.getParameterData();
        EventStruct event1 = new EventStruct(EventCode.M_REBOOT, "test");
        data.addEvent(event1);
        EventStruct event2 = new EventStruct("9999", "test");
        data.addEvent(event2);
        Inform inform = new Inform(data,1);
        inform.perform(session);
        session.setLastRPCMethod(inform);
        InformResponse informResponse = new InformResponse();
        informResponse.perform(session);
        Object[] obs = data.getEventsArray();
        assertEquals(1, obs.length);
        assertEquals(event2, obs[0]);
    }
    
    public void testEventsBOOT() throws Exception {
        TestUtil.TRACE(this);
        RPCMethodMng.getInstance();
        MockSession session = new MockSession();
        IParameterData data = session.getParameterData();
        EventStruct event1 = new EventStruct(EventCode.BOOT, "");
        data.addEvent(event1);
        EventStruct event2 = new EventStruct(EventCode.BOOTSTRAP, "");
        data.addEvent(event2);
        Inform inform = new Inform(data,1);
        inform.perform(session);
        session.setLastRPCMethod(inform);
        InformResponse informResponse = new InformResponse();
        informResponse.perform(session);
        Object[] obs = data.getEventsArray();
        assertEquals(0, obs.length);        
    }

}
