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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.francetelecom.admindm.api.CheckCallBack;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.admindm.soap.FaultUtil;
/**
 * The Class CheckDate.
 * @author Olivier Beyler - OrangeLabs -
 */
public final class CheckDate implements CheckCallBack {
    /**
     * Check.
     * @param value the value
     * @throws Fault the fault
     */
    public void check(final Object value) throws Fault {
        Pattern time = Pattern.compile(
        // Absolute time 2009-01-01T00:00:00Z
                // time zoned Z is optional
                "(" + "[2][0-9][0-9][0-9]" + // 2000 to 2999
                        "-" + // -
                        "([0][1-9]|[1][0-2])" + // 01 - alias January ... 12
                        // alias December
                        "-" + // -
                        "([0][1-9]|[1-2][0-9]|[3][0-1])" + // from 01 to 31
                        "T" + // T
                        "([0-1][0-9]|[2][0-3])" + // 00 hours
                        ":" + // :
                        "[0-5][0-9]" + // 00 minutes
                        ":" + // :
                        "[0-5][0-9]" + // 00 seconds
                        "([Z]|([/+|/-]([0][0-9]|[1][0-9])))?" + // 00 TIME ZONE
                        ")|("
                        // relative time 0001-01-01T00:00 (0001 is
                        + "[0-1][0-9][0-9][0-9]" + // 0000
                        "-" + // -
                        "([0][1-9]|[1][0-2])" + // 01 - alias January ... 12
                        // alias December
                        "-" + // -
                        "([0][1-9]|[1-2][0-9]|[3][0-1])" + // from 01 to 31
                        "T" + // T
                        "([0-1][0-9]|[2][0-3])" + // 00 hours
                        ":" + // :
                        "[0-5][0-9]" + // 00 minutes
                        ":" + // :
                        "[0-5][0-9]" + // 00 seconds
                        ")" + // 00 seconds
                        "([Z]|([/+|/-]([0][0-9]|[1][0-9])))?" + // 00 TIME ZONE
                        "");
        if (value==null){
            throwFault("value is null");
        }
        Matcher m = time.matcher(value.toString());
        if (!m.matches()) {
            throwFault(value.toString());
        }
    }


    private void throwFault(final String value) throws Fault {
        StringBuffer error;
        error = new StringBuffer(FaultUtil.STR_FAULT_9007);
        error.append(" Date format is invalid '");
        error.append(value);
        error.append("' instead of AAAA-MM-DDThh:mm:ss");
        error.append(" or AAAA-MM-DDThh:mm:ssZ+XX");
        error.append(" or AAAA-MM-DDThh:mm:ssZ-XX");
        throw new Fault(FaultUtil.FAULT_9007, error.toString());
    }
    
    
    /**
     * Instantiates a new check boolean and hide the constructor.
     */
    private CheckDate() {
    }
    /** The unique instance. */
    private static CheckDate instance = new CheckDate();
    /**
     * Gets the single instance of CheckDate.
     * @return single instance of CheckDate
     */
    public static CheckDate getInstance() {
        return instance;
    }
}
