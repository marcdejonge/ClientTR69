/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : 1.1
 * Module Name : ApplyBundle
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
package com.francetelecom.admindm.apply;
import com.francetelecom.admindm.download.api.IApplyAction;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.admindm.soap.FaultUtil;
/**
 * The Class AbstractApply.
 */
public abstract class AbstractApply implements IApplyAction {
    /**
     * Reject bad file.
     * @param location the location
     * @return the fault
     */
    protected final Fault rejectBadFile(final String location) {
        return new Fault(FaultUtil.FAULT_9018, faultMsg(location));
    }

    /**
     * Reject bad file.
     * @param location the location
     * @param e the exception
     * @return the fault
     */
    protected final Fault rejectBadFile(final String location,
            final Exception e) {
        return new Fault(FaultUtil.FAULT_9018, faultMsg(location), e);
    }
    /**
     * Fault msg.
     * @param name the name
     * @return the string
     */
    private String faultMsg(final String name) {
        StringBuffer error = new StringBuffer(FaultUtil.STR_FAULT_9018);
        error.append(" unable to apply '");
        error.append(name);
        error.append("' ");
        return error.toString();
    }
}
