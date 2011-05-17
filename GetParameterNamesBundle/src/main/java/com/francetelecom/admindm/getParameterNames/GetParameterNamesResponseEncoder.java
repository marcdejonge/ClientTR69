/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : 1.1
 * Module Name : GetParameterNamesBundle
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
package com.francetelecom.admindm.getParameterNames;
import org.kxml2.kdom.Element;
import com.francetelecom.admindm.api.RPCEncoder;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.admindm.soap.FaultUtil;
import com.francetelecom.admindm.soap.Soap;
/**
 * The Class GetParameterNamesResponseEncoder.
 */
public class GetParameterNamesResponseEncoder implements RPCEncoder {
    /**
     * Encode.
     * @param method the method
     * @return the element
     * @throws Fault the fault
     */
    public final Element encode(final RPCMethod method) throws Fault {
        if (method == null) {
            throw new Fault(FaultUtil.FAULT_9002, "encode null pointer");
        }
        GetParameterNamesResponse gpnr = (GetParameterNamesResponse) method;
        Element result = new Element();
        result.setName(gpnr.getName());
        result.setNamespace(Soap.getCWMPNameSpace());
        Element eParameterList = new Element();
        eParameterList.setName("ParameterList");
        result.addChild(Element.ELEMENT, eParameterList);
        StringBuffer value = new StringBuffer("xsd:string[");
        value.append(gpnr.getParameterList().length);
        value.append("]");
        for (int i = 0; i < gpnr.getParameterList().length; i++) {
            eParameterList.addChild(Element.ELEMENT,
                    gpnr.getParameterList()[i].encode());
        }
        return result;
    }
}
