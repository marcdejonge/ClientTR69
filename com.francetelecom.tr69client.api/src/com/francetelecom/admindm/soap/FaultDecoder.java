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

import org.kxml2.kdom.Element;

import aQute.bnd.annotation.component.Component;

import com.francetelecom.admindm.api.RPCDecoder;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.api.XMLUtil;
/**
 * The Class FaultDecoder.
 */
@Component(properties="name=Fault")
public class FaultDecoder implements RPCDecoder {
    /**
     * Decode.
     * @param element the element
     * @return the RPC method
     * @throws Fault the fault
     */
    public final RPCMethod decode(final Element element) throws Fault {
        Fault result = null;
        Element detail = element.getElement("", "detail");
        Element Fault = detail.getElement("","cwmp:Fault");
        try {            
            String strFaultcode = XMLUtil.extractValue(Fault,"FaultCode");
            int faultcode = Integer.parseInt(strFaultcode);
            String strFaultstring = XMLUtil.extractValue(Fault,"FaultString");
            result = new Fault(faultcode, strFaultstring);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new Fault(FaultUtil.FAULT_9003, FaultUtil.STR_FAULT_9003, e);
        } catch (NullPointerException e) {
            e.printStackTrace();
            throw new Fault(FaultUtil.FAULT_9003, FaultUtil.STR_FAULT_9003, e);
        }
        return result;
    }
}
