/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : 1.1
 * Module Name : GetParameterValuesBundle
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
package com.francetelecom.admindm.getParameterValues;
import java.util.ArrayList;
import java.util.List;
import org.kxml2.kdom.Element;

import aQute.bnd.annotation.component.Component;

import com.francetelecom.admindm.api.RPCDecoder;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.admindm.soap.FaultUtil;
/**
 * The Class GetParameterValuesDecoder.
 */
@Component(properties="name=GetParameterValues")
public class GetParameterValuesDecoder implements RPCDecoder {
    /**
     * Decode.
     * @param element the element
     * @return the RPC method
     * @throws Fault the fault
     */
    public final RPCMethod decode(final Element element) throws Fault {
        GetParameterValues result = new GetParameterValues();
        Element eParameterName;
        eParameterName = element.getElement("","ParameterNames");
        if (eParameterName == null) {
            StringBuffer error = new StringBuffer(FaultUtil.STR_FAULT_9003);
            error.append(": ParameterNames tags is missing.");
            throw new Fault(FaultUtil.FAULT_9003, error.toString());
        }
        List<Object> eString = new ArrayList<Object>();
        for (int index=0;index<eParameterName.getChildCount();index++){
            if ((eParameterName.getType(index)==Element.ELEMENT)&&
                    "string".equals(eParameterName.getElement(index).getName())){
                eString.add(eParameterName.getChild(index));
            }
        }        
        int size = eString.size();
        result.setParameterNames(new String[size]);
        for (int i = 0; i < size; i++) {
            Element temp = (Element) eString.get(i);
            if (temp.getChildCount()>0){
                result.getParameterNames()[i] = temp.getText(0);
            } else  {
                result.getParameterNames()[i] = "";
            }
        }
        return result;
    }
}
