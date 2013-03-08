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

import com.francetelecom.admindm.api.RPCEncoder;
import com.francetelecom.admindm.api.RPCMethod;

/**
 * The Class FaultEncoder.
 */
@Component(properties="name=Fault")
public class FaultEncoder implements RPCEncoder {

    /**
     * Encode.
     * @param method the method
     * @return the element
     * @throws Fault the fault
     */
    public final Element encode(final RPCMethod method) throws Fault {
        Fault fault = (Fault) method;
        Element eSoapFault = new Element();
        eSoapFault.setName("Fault");
        eSoapFault.setNamespace(Soap.getSoapEnvNameSpace());
        Element eFaultcode = new Element();
        eFaultcode.setName("faultcode");
        eFaultcode.addChild(Element.TEXT,FaultUtil.getType(fault.getFaultcode()));
        Element eFaultstring = new Element();
        eFaultstring.setName("faultstring");
        eFaultstring.addChild(Element.TEXT,"CWMP Fault");
        Element eDetail = new Element();
        eDetail.setName("detail");
        Element eCmpwFault = new Element();
        eCmpwFault.setName("Fault");
        eCmpwFault.setNamespace(Soap.getCWMPNameSpace());
        eDetail.addChild(Element.ELEMENT,eCmpwFault);
        Element eFaultCode = new Element();
        eFaultCode.setName("FaultCode");
        eFaultCode.addChild(Element.TEXT,"" + fault.getFaultcode());
        eCmpwFault.addChild(Element.ELEMENT,eFaultCode);
        Element eFaultString = new Element();
        eFaultString.setName("FaultString");
        eFaultString.addChild(Element.TEXT,"" + fault.getFaultstring());
        eCmpwFault.addChild(Element.ELEMENT,eFaultString);
        SetParamValuesFault setParamValuesFault;
        RPCEncoder encoder = new SetParamValuesFaultEncoder();
        while (fault.getLsSetParamValuesFaults() != null
                && !fault.getLsSetParamValuesFaults().isEmpty()) {
            setParamValuesFault = (SetParamValuesFault) fault
                    .getLsSetParamValuesFaults().remove(0);
            eCmpwFault.addChild(Element.ELEMENT,
                    encoder.encode(setParamValuesFault));
        }
        eSoapFault.addChild(Element.ELEMENT,eFaultcode);
        eSoapFault.addChild(Element.ELEMENT,eFaultstring);
        eSoapFault.addChild(Element.ELEMENT,eDetail);
        return eSoapFault;
    }

}
