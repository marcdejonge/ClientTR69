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
package com.francetelecom.admindm.model;
import com.francetelecom.admindm.api.CheckCallBack;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.admindm.soap.FaultUtil;
/**
 * The Class CheckLength.
 */
public final class CheckLength implements CheckCallBack {
    /** The max string length. */
    private final int maxStringLength;
    /**
     * The Constructor.
     * @param pMaxStringLength the max string length
     */
    public CheckLength(final int pMaxStringLength) {
        super();
        this.maxStringLength = pMaxStringLength;
    }
    /**
     * Check.
     * @param value the value
     * @throws Fault the fault
     */
    public void check(final Object value) throws Fault {
        String chaine = (String) value;
        if (chaine != null && chaine.length() > maxStringLength) {
            StringBuffer error = new StringBuffer(FaultUtil.STR_FAULT_9007);
            error.append(": '");
            error.append(value);
            error.append("' is too long. (maximum allowed is ");
            error.append(maxStringLength);
            error.append(").");
            throw new Fault(FaultUtil.FAULT_9007, error.toString());
        }
    }
}
