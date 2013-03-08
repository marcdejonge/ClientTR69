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
 * The Class SetParamValuesFaultEncoder.
 */
@Component(properties="name=SetParamValuesFault")
public final class SetParamValuesFaultEncoder implements RPCEncoder {
    /**
     * Encode.
     * @param method the method
     * @return the element
     */
    public Element encode(final RPCMethod method) {
        SetParamValuesFault setParamValuesFault = (SetParamValuesFault) method;
        Element result = new Element();
        result.setName("SetParameterValuesFault");
        Element eParameterName = new Element();
        eParameterName.setName("ParameterName");
        eParameterName.addChild(Element.TEXT,setParamValuesFault.getParameterName());
        Element eFaultCode = new Element();
        eFaultCode.setName("FaultCode");
        eFaultCode.addChild(Element.TEXT,"" + setParamValuesFault.getFaultcode());
        Element eFaultString = new Element();
        eFaultString.setName("FaultString");
        eFaultString.addChild(Element.TEXT,"" + setParamValuesFault.getFaultstring());
        result.addChild(Element.ELEMENT,eParameterName);
        result.addChild(Element.ELEMENT,eFaultCode);
        result.addChild(Element.ELEMENT,eFaultString);
        return result;
    }
}
