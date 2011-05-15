/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : 1.1
 * Module Name : DownloadBundle
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

package com.francetelecom.admindm.download;
import java.io.Serializable;
import org.kxml2.kdom.Element;
/**
 * The Class FaultStruct.
 */
public class FaultStruct implements Serializable {
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    /** The fault code. */
    private int faultCode = 0;
    /** The fault string. */
    private String faultString = "";
    /**
     * Sets the fault code.
     * @param pFaultCode the fault code
     */
    public final void setFaultCode(final int pFaultCode) {
        this.faultCode = pFaultCode;
    }
    /**
     * Gets the fault code.
     * @return the fault code
     */
    public final int getFaultCode() {
        return faultCode;
    }
    /**
     * Sets the fault string.
     * @param pFaultString the new fault string
     */
    public final void setFaultString(final String pFaultString) {
        this.faultString = pFaultString;
    }
    /**
     * Gets the fault string.
     * @return the fault string
     */
    public final String getFaultString() {
        return faultString;
    }
    /**
     * Encode.
     * @return the element
     */
    public final Element encode() {
        Element eFaultStruct = new Element();
        eFaultStruct.setName("FaultStruct");
        Element eFaultCode = new Element();
        eFaultCode.setName("FaultCode");
        eFaultCode.addChild(Element.TEXT, "" + getFaultCode());
        Element eFaultString = new Element();
        eFaultString.setName("FaultString");
        eFaultString.addChild(Element.TEXT, getFaultString());
        eFaultStruct.addChild(Element.ELEMENT, eFaultCode);
        eFaultStruct.addChild(Element.ELEMENT, eFaultString);
        return eFaultStruct;
    }
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + faultCode;
        result = prime * result
                + ((faultString == null) ? 0 : faultString.hashCode());
        return result;
    }
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FaultStruct other = (FaultStruct) obj;
        if (faultCode != other.faultCode)
            return false;
        if (faultString == null) {
            if (other.faultString != null)
                return false;
        } else if (!faultString.equals(other.faultString))
            return false;
        return true;
    }
}
