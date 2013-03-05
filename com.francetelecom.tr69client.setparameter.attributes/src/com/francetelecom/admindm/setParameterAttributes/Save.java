/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : 1.1
 * Module Name : SetParameterAttributesBundle
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


package com.francetelecom.admindm.setParameterAttributes;
import java.io.Serializable;
import com.francetelecom.admindm.model.Parameter;
/**
 * The Class Save aim is to keep the information about the parameter.
 */
public class Save implements Serializable {
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    /**
     * Instantiates a new save.
     * @param pParam the param
     */
    public Save(final Parameter pParam) {
        super();
        this.param = pParam;
        this.notification = param.getNotification();
        this.accessList = param.getAccessList();
    }
    /**
     * Restore the parameter attribute.
     */
    public void restore() {
        param.setNotification(notification);
        param.setAccessList(accessList);
    }
    /** The param. */
    private final Parameter param;
    /** The notification. */
    private final int notification;
    /** The access list. */
    private final String[] accessList;
}
