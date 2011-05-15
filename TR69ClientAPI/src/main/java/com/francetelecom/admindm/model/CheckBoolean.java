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
 * The Class CheckBoolean.
 */
public final class CheckBoolean implements CheckCallBack {
    /** The Constant ZERO. */
    private static final String ZERO = "0";
    /** The Constant ONE. */
    private static final String ONE = "1";
    /** The Constant TRUE. */
    private static final String TRUE = "true";
    /** The Constant FALSE. */
    private static final String FALSE = "false";
    /**
     * Check.
     * @param value the value
     * @throws Fault the fault
     */
    public void check(final Object value) throws Fault {
        if (value == null) {
            StringBuffer error = new StringBuffer(FaultUtil.STR_FAULT_9007);
            error.append(": Boolean value is expected instead of null.");
            throw new Fault(FaultUtil.FAULT_9007, error.toString());
        }
        String stringValue = value.toString();
        if ((!ZERO.equals(stringValue)
                && (!ONE.equals(stringValue))
                && (!TRUE.equals(stringValue))
                && (!FALSE.equals(stringValue)))) {
            StringBuffer error = new StringBuffer(FaultUtil.STR_FAULT_9007);
            error.append(": Boolean format '");
            error.append(value);
            error.append("' is invalid.");
            throw new Fault(FaultUtil.FAULT_9007, error.toString());
        }
    }
    /**
     * Instantiates a new check boolean and hide the constructor.
     */
    private CheckBoolean() {
    }
    /** The unique instance. */
    private static CheckBoolean instance = new CheckBoolean();
    /**
     * Gets the single instance of CheckBoolean.
     * @return single instance of CheckBoolean
     */
    public static CheckBoolean getInstance() {
        return instance;
    }
}
