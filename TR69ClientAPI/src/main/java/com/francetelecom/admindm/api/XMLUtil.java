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
package com.francetelecom.admindm.api;
import org.kxml2.kdom.Element;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.admindm.soap.FaultUtil;
/**
 * The Class XMLUtil.
 * @author Olivier Beyler - OrangeLabs -
 */
public final class XMLUtil {
    /**
     * Hide default constructor.
     */
    private XMLUtil() {
    }
    /**
     * Extract value.
     * @param element the element
     * @param paramName the param name
     * @return the string
     * @throws Fault the fault
     */
    public static String extractValue(final Element element,
            final String paramName) throws Fault {
        if (element == null) {
            StringBuffer error = new StringBuffer(FaultUtil.STR_FAULT_9003);
            error.append(": bad xml");
            throw new Fault(FaultUtil.FAULT_9003, error.toString());
        }
        if (paramName == null) {
            StringBuffer error = new StringBuffer(FaultUtil.STR_FAULT_9002);
            throw new Fault(FaultUtil.FAULT_9002, error.toString());
        }
        String result = null;
        Element eParamName = null;
        int index = element.indexOf(null, paramName, 0);        
        if (index >= 0) {
            eParamName = element.getElement(index);
            result = "";
            for (int i= 0 ;i<eParamName.getChildCount();i++) {
                if (eParamName.getType(i)==Element.TEXT) {
                    result = eParamName.getText(i);        
                }
            }
        }
        if (result == null) {
            StringBuffer error = new StringBuffer(FaultUtil.STR_FAULT_9003);
            error.append(": ");
            error.append(paramName);
            error.append(" is not defined.");
            throw new Fault(FaultUtil.FAULT_9003, error.toString());
        }
        return (result.trim());
    }
}
