/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : 1.1
 * Module Name : SetParameterValuesBundle
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

package com.francetelecom.admindm.setParameterValues;
import org.kxml2.kdom.Element;

import aQute.bnd.annotation.component.Component;

import com.francetelecom.admindm.api.RPCEncoder;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.admindm.soap.FaultUtil;
import com.francetelecom.admindm.soap.Soap;
/**
 * The Class SetParameterValuesResponseEncoder.
 */
@Component(properties="name=SetParameterValuesResponse")
public final class SetParameterValuesResponseEncoder implements RPCEncoder {
    /**
     * Encode.
     * @param method the method
     * @return the element
     * @throws Fault the fault
     */
    public Element encode(final RPCMethod method) throws Fault {
        SetParameterValuesResponse stvr;
        if (method == null) {
            StringBuffer error = new StringBuffer(FaultUtil.STR_FAULT_9002);
            error.append(": try to encode "
                    + "null SetParameterValuesResponse.");
            throw new Fault(FaultUtil.FAULT_9002, error.toString());
        }
        Element result = null;
        if (method instanceof SetParameterValuesResponse) {
            stvr = (SetParameterValuesResponse) method;
            result = new Element();
            result.setName(stvr.getName());
            result.setNamespace(Soap.getCWMPNameSpace());
            Element eStatut = new Element();
            eStatut.setName("Status");
            eStatut.addChild(Element.TEXT, "" + stvr.getStatus());
            result.addChild(Element.ELEMENT, eStatut);
        } else {
            StringBuffer error = new StringBuffer(FaultUtil.STR_FAULT_9002);
            error.append(": Invlid cast into ");
            error.append(this.getClass().getName());
            throw new Fault(FaultUtil.FAULT_9002, error.toString());
        }
        return result;
    }
}
