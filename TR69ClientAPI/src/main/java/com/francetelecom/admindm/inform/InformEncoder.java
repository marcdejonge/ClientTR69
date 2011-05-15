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
import java.util.Iterator;
import org.kxml2.kdom.Element;
import com.francetelecom.admindm.api.RPCEncoder;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.model.EventStruct;
import com.francetelecom.admindm.model.ParameterValueStruct;
import com.francetelecom.admindm.soap.Soap;
/**
 * The Class InformEncoder.
 */
public final class InformEncoder implements RPCEncoder {
    /**
     * encode the RPCMethod.
     * @param method the method
     * @see RPCEncoder#encode(RPCMethod)
     * @return Element
     */
    public Element encode(final RPCMethod method) {
        Element eInform = null;
        if (method instanceof Inform) {
            Inform inform = (Inform) method;
            eInform = new Element();
            eInform.setName("Inform");
            eInform.setNamespace(Soap.getCWMPNameSpace());
            eInform.addChild(Element.ELEMENT, inform.getDeviceId().encoded());
            int size = inform.getEvent().size();
            Element eLsEvent = new Element();
            eLsEvent.setName("Event");
            eInform.addChild(Element.ELEMENT, eLsEvent);
            eLsEvent.setAttribute(Soap.getSoapEncNameSpace(), "arrayType",
                    "cwmp:EventStruct[" + size + "]");
            Iterator it = inform.getEvent().iterator();
            while (it.hasNext()) {
                EventStruct evt = (EventStruct) it.next();
                eLsEvent.addChild(Element.ELEMENT, evt.encoded());
            }
            Element eMaxEnvelopes = eInform.createElement("", "MaxEnvelopes");
            eInform.addChild(Element.ELEMENT, eMaxEnvelopes);
            eMaxEnvelopes.addChild(Element.TEXT, "1");
            Element eTime = eInform.createElement("", "CurrentTime");
            eInform.addChild(Element.ELEMENT, eTime);
            eTime.addChild(Element.TEXT, Soap.convertDate2String(System
                    .currentTimeMillis()));
            Element eRetryCount = eInform.createElement("", "RetryCount");
            eInform.addChild(Element.ELEMENT, eRetryCount);
            eRetryCount.addChild(Element.TEXT, ""+inform.getRetryCount());
            Element eParameterList = eInform
                    .createElement("", "ParameterList");
            eInform.addChild(Element.ELEMENT, eParameterList);
            size = inform.getParameterList().size();
            eParameterList.setAttribute(Soap.getSoapEncNameSpace(),
                    "arrayType", "cwmp:ParameterValueStruct[" + size + "]");
            it = inform.getParameterList().iterator();
            while (it.hasNext()) {
                ParameterValueStruct pvs = (ParameterValueStruct) it.next();
                eParameterList.addChild(Element.ELEMENT, pvs.encoded());
            }
        }
        return eInform;
    }
}
