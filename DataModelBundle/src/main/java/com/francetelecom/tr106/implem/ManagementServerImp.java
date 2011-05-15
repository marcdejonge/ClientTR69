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
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.tr106.gen.ManagementServer;
/**
 * The Class ManagementServerImp.
 */
public class ManagementServerImp extends ManagementServer {
    /**
     * Instantiates a new management server imp.
     * @param data the data
     * @param basePath the base path
     */
    public ManagementServerImp(IParameterData data, String basePath) {
        super(data, basePath);
    }
    /**
     * Initialize.
     * @throws Fault the fault
     * @see com.francetelecom.tr106.gen.ManagementServer#initialize()
     */
    public void initialize() throws Fault {
        super.initialize();
        getParamPeriodicInformTime().setValueWithout(new Long(0));
        getParamPeriodicInformInterval().setValueWithout(new Long(30));
        getParamPeriodicInformEnable().setValueWithout(Boolean.FALSE);
        getParamNATDetected().setValueWithout(Boolean.FALSE);
        getParamNATDetected().setMandatoryNotification(true);
        getParamNATDetected().setNotification(2);
        getParamSTUNEnable().setValueWithout(Boolean.FALSE);
        getParamSTUNEnable().setMandatoryNotification(true);
        getParamUDPConnectionRequestAddress().setMandatoryNotification(true);
        getParamUDPConnectionRequestAddress().setNotification(2);
//        getParamConnectionRequestUsername().setMandatoryNotification(true);
//        getParamConnectionRequestPassword().setMandatoryNotification(true);
    }
}
