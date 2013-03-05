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
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;
import com.francetelecom.admindm.api.Log;
import com.francetelecom.admindm.api.StorageMode;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.model.Parameter;
import com.francetelecom.admindm.model.ParameterType;
import com.francetelecom.admindm.soap.Fault;

/**
 * The listener interface for receiving OSGIBundle events. The class that is
 * interested in processing a OSGIBundle event implements this interface, and
 * the object created with that class is registered with a component using the
 * component's <code>addOSGIBundleListener<code> method. When
 * the OSGIBundle event occurs, that object's appropriate
 * method is invoked.
 * @see OSGIBundleEvent
 */
public class OSGIBundleListener implements BundleListener {

    private BundleNumberOfEntriesUpdater updater;
    /** The Constant osgiPath. */
    public static final String osgiPath = "OSGI.";
    
    /** The Constant bundlePath. */
    public static final String bundlePath = osgiPath + "Bundle.";
    
    /** The data. */
    private final IParameterData data;
    
    /**
     * Instantiates a new oSGI bundle listener.
     * @param pData the data
     * @param pUpdater the updater
     */
    public OSGIBundleListener(final IParameterData pData,
            final BundleNumberOfEntriesUpdater pUpdater) {
        super();
        this.data = pData;
        this.updater =pUpdater;
    }
    
    /**
     * Bundle changed.
     * @param event the event
     */
    public void bundleChanged(BundleEvent event) {
        createOrUpdateDataModel(event.getBundle());
        updater.updateValue();
    }
    
    /**
     * Creates the or update data model.
     * @param bundle the bundle
     */
    public void createOrUpdateDataModel(Bundle bundle) {
        if (bundle != null) {
            long id = bundle.getBundleId();
            StringBuffer path = new StringBuffer(data.getRoot() + bundlePath);
            path.append(id).append(".");
            Parameter param;            
            try {
                path = new StringBuffer(data.getRoot() + bundlePath).append(id).append(".");
                Log.debug(path.toString());
                param = data.createOrRetrieveParameter(path.toString());
                param.setStorageMode(StorageMode.COMPUTED);
                param.setType(ParameterType.ANY);
                param.setValue("");
                param.setWritable(false);
                
                path = new StringBuffer(data.getRoot() + bundlePath).append(id);
                path.append(".Location");
                param = data.createOrRetrieveParameter(path.toString());
                param.setStorageMode(StorageMode.COMPUTED);
                param.setType(ParameterType.STRING);
                param.setValue(bundle.getLocation());                
                param.setWritable(false);
                
                path = new StringBuffer(data.getRoot() + bundlePath).append(id);
                path.append(".State");
                param = data.createOrRetrieveParameter(path.toString());                
                StateModifier modifier = new StateModifier(data,bundle,param);
                param.setStorageMode(StorageMode.COMPUTED);                
                param.setType(ParameterType.STRING);
                param.setSetter(modifier);
                param.setGetter(modifier);
                param.getLsCheckCallBack().clear();
                param.addCheck(modifier); 
                param.setWritable(true);
            } catch (Fault e) {
                e.printStackTrace();
            }
        }
    }
}
