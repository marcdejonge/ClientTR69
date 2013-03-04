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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.francetelecom.admindm.api.CheckCallBack;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.admindm.soap.FaultUtil;
/**
 * The Class CheckEnum.
 */
public final class CheckEnum implements CheckCallBack {
    /** The list of authorized value. */
    private final List lsAuthorizedValue;
    /**
     * Instantiates a new check base on an authorized value list.
     * @param pLsAuthorizedValue the authorized value
     */
    public CheckEnum(final Object[] pLsAuthorizedValue) {
        lsAuthorizedValue = new ArrayList();
        for (int i = 0; i < pLsAuthorizedValue.length; i++) {
            lsAuthorizedValue.add(pLsAuthorizedValue[i]);
        }
    }
    /**
     * Instantiates a new check base on an authorized value list.
     * @param pLsAuthorizedValue the authorized value list
     */
    public CheckEnum(final List pLsAuthorizedValue) {
        this.lsAuthorizedValue = pLsAuthorizedValue;
    }
    /**
     * Check.
     * @param value the value
     * @throws Fault the fault
     * @see com.francetelecom.admindm.api.CheckCallBack#check(java.lang.Object)
     */
    public void check(final Object value) throws Fault {
        if (!lsAuthorizedValue.contains(value)) {
            StringBuffer error = new StringBuffer(FaultUtil.STR_FAULT_9007);
            error.append(" ");
            error.append(value);
            error.append(" doesn't belong to authorized value:");
            Iterator it = lsAuthorizedValue.iterator();
            while (it.hasNext()) {
                error.append("\"");
                error.append(it.next());
                error.append("\" ");
            }
            throw new Fault(FaultUtil.FAULT_9007, error.toString());
        }
    }
}
