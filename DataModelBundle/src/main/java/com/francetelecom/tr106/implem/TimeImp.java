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
import java.util.TimeZone;
import com.francetelecom.admindm.api.Getter;
import com.francetelecom.admindm.api.StorageMode;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.tr106.gen.Time;
/**
 * The Class TimeImp.
 */
public class TimeImp extends Time {
    /**
     * Instantiates a new time imp.
     * @param data the data
     * @param basePath the base path
     */
    public TimeImp(IParameterData data, String basePath) {
        super(data, basePath);
    }
    /**
     * Initialize.
     * @throws Fault the fault
     * @see com.francetelecom.tr106.gen.Time#initialize()
     */
    public void initialize() throws Fault {
        super.initialize();
        getParamCurrentLocalTime().setGetter(new GetCurrentLocalTime());
        getParamCurrentLocalTime().setStorageMode(StorageMode.COMPUTED);
        getParamLocalTimeZone().setGetter(new GetLocalTimeZone());
        getParamLocalTimeZone().setStorageMode(StorageMode.COMPUTED);
    }
    /**
     * The Class GetTime.
     */
    class GetCurrentLocalTime implements Getter {
        /**
         * Gets the.
         * @param sessionId the session id
         * @return the object
         * @see com.francetelecom.admindm.api.Getter#get(java.lang.String)
         */
        public Object get(final String sessionId) {
            return new Long(System.currentTimeMillis());
        }
    }
    /**
     * The Class GetTimeZone.
     */
    class GetLocalTimeZone implements Getter {
        /**
         * Gets the.
         * @param sessionId the session id
         * @return the object
         * @see com.francetelecom.admindm.api.Getter#get(java.lang.String)
         */
        public Object get(final String sessionId) {
            TimeZone zone = TimeZone.getDefault();
            return zone.getDisplayName();
        }
    }
}
