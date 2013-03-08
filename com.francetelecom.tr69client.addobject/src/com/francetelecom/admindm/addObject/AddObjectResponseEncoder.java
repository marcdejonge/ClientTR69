/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : 1.1
 * Module Name : AddObjectBundle
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
package com.francetelecom.admindm.addObject;

import org.kxml2.kdom.Element;

import aQute.bnd.annotation.component.Component;

import com.francetelecom.admindm.api.RPCEncoder;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.soap.Soap;
/**
 * The Class AddObjectResponseEncoder.
 */
@Component(properties="name=AddObjectResponse")
public class AddObjectResponseEncoder implements RPCEncoder {
    /**
     * Encode.
     * @param method the method
     * @return the element
     */
    public final Element encode(final RPCMethod method) {
        AddObjectResponse aor = (AddObjectResponse) method;
        Element result = new Element();
        result.setName(aor.getName());
        result.setNamespace( Soap.getCWMPNameSpace());
        Element eInstanceNumber = new Element();
        eInstanceNumber.setName("InstanceNumber");
        eInstanceNumber.addChild(Element.TEXT, "" + aor.getInstanceNumber());
        result.addChild(Element.ELEMENT,eInstanceNumber);
        Element eStatus = new Element();
        eStatus.setName("Status");
        eStatus.addChild(Element.TEXT, "" + aor.getStatus());
        result.addChild(Element.ELEMENT, eStatus);
        return result;
    }
}
