/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : 1.1
 * Module Name : DataModelBundle
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
package com.francetelecom.tr106.implem;
import com.francetelecom.admindm.api.Getter;
import com.francetelecom.admindm.api.StorageMode;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.tr106.gen.DeviceInfo;
/**
 * The Class DeviceInfoImp.
 */
public final class DeviceInfoImp extends DeviceInfo {
    /** The Constant start. */
    static final long START = System.currentTimeMillis();
    /**
     * Instantiates a new device info imp.
     * @param data the data
     * @param basePath the base path
     */
    public DeviceInfoImp(final IParameterData data, final String basePath) {
        super(data, basePath);
    }
    /**
     * Initialize.
     * @throws Fault the fault
     * @see com.francetelecom.tr106.gen.DeviceInfo#initialize()
     */
    public void initialize() throws Fault {
        super.initialize();
        getParamDescription().setStorageMode(StorageMode.DM_ONLY);
        getParamDescription().setValue("");
        getParamManufacturerOUI().setStorageMode(StorageMode.DM_ONLY);
        getParamManufacturer().setStorageMode(StorageMode.DM_ONLY);
        getParamUpTime().setGetter(new GetUpTime());
        getParamFirstUseDate().setValue(new Long(0));
    }
    /**
     * The Class GetUpTime.
     */
    final class GetUpTime implements Getter {
        /** The Constant NB_MS_PER_MINUTE. */
        private static final int NB_MS_PER_MINUTE = 1000;
        /**
         * Gets the Value.
         * @param sessionId the session id
         * @return the object
         * @see com.francetelecom.admindm.api.Getter#get(java.lang.String)
         */
        public Object get(final String sessionId) {
            long now = System.currentTimeMillis();
            return new Long((now - START) / NB_MS_PER_MINUTE);
        }
    }
}
