/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : 1.1
 * Module Name : OSGiBundle
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

package com.francetelecom.admindm.osgi;
import org.osgi.framework.BundleContext;
import com.francetelecom.admindm.api.Log;
import com.francetelecom.admindm.api.StorageMode;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.Parameter;
import com.francetelecom.admindm.model.ParameterType;
import com.francetelecom.admindm.soap.Fault;

/**
 * The Class BundleNumberOfEntriesUpdater.
 */
public class BundleNumberOfEntriesUpdater {
    
    /** The context. */
    private BundleContext context;
    
    /** The parameter. */
    private Parameter parameter;
    
    /**
     * Instantiates a new getter number of entries.
     * @param context the context
     * @param data the data
     */
    BundleNumberOfEntriesUpdater(BundleContext context, IParameterData data) {
        this.context = context;
        StringBuffer temp = new StringBuffer(data.getRoot());
        temp.append(OSGIBundleListener.osgiPath);
        temp.append("BundleNumberOfEntries");
        try {
            parameter = data.createOrRetrieveParameter(temp.toString());
            parameter.setType(ParameterType.UINT);
            parameter.setStorageMode(StorageMode.COMPUTED);
            parameter.setWritable(false);
            parameter.setDirectValue(new Long(0));
        } catch (Fault e) {
            Log.error("BUG on OSGI implem ", e);
        }
    }
    
    /**
     * Update value.
     */
    public void updateValue() {
        if (parameter != null) {
            parameter.setDirectValue(new Long(context.getBundles().length));
        }
    }
}
