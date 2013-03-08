/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : 1.1
 * Module Name : GetParameterAttributesBundle
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
package com.francetelecom.admindm.getParameterAttributes;
import java.util.ArrayList;
import java.util.List;
import org.kxml2.kdom.Element;

import aQute.bnd.annotation.component.Component;

import com.francetelecom.admindm.api.RPCDecoder;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.soap.Fault;
/**
 * The Class GetParameterAttributesDecoder.
 */
@Component(properties="name=GetParameterAttributes")
public final class GetParameterAttributesDecoder implements RPCDecoder {
    /**
     * Decode.
     * @param element the element
     * @return the RPC method
     * @throws Fault the decoder exception
     */
    public RPCMethod decode(final Element element) throws Fault {
        GetParameterAttributes gpa = new GetParameterAttributes();
        Element eParameterName = element.getElement("","ParameterNames");
        List<Element> eString = new ArrayList<Element>();
        for (int index = 0;index<eParameterName.getChildCount();index++){
            Element tmp = eParameterName.getElement(index);
            int type = eParameterName.getType(index);
            if (tmp != null && type == Element.ELEMENT && "string".equals(tmp.getName())) {
            	eString.add(tmp);
            }
        }
        int size = eString.size();
        gpa.setParameterNames(new String[size]);
        for (int i = 0; i < size; i++) {
            Element temp = (Element) eString.get(i);
            if (temp.getChildCount()>0) {
                gpa.getParameterNames()[i] = temp.getText(0);
            } else{
                gpa.getParameterNames()[i] = "";
            }
        }        
        return gpa;
    }
}
