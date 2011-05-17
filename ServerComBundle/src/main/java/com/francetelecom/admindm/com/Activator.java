/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : 1.1
 * Module Name : ServerComBundle
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
package com.francetelecom.admindm.com;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;
import com.francetelecom.admindm.api.FileUtil;
import com.francetelecom.admindm.api.ICom;
import com.francetelecom.admindm.api.Log;
import com.francetelecom.admindm.stunclient.ISTUNCLient;
/**
 * The Class Activator.
 */
public final class Activator implements BundleActivator, ServiceListener {
    /** The STUN client service. */
    private ServiceReference stunClientServiceRef = null;
    /** bundle context. */
    private BundleContext bundleContext = null;
    /**
     * Instantiates a new activator.
     */
    public Activator() {
        super();
    }
    /** The com. */
    private Com com;
    /**
     * Start.
     * @param context the context
     * @throws Exception the exception
     */
    public void start(final BundleContext context) throws Exception {
        this.bundleContext = context;
        File conf = FileUtil.getFileFromShortName(FileUtil.CONFIG);
        if (conf != null) {
            InputStream in = null;
            try {
                Properties properties = new Properties();
                // TODO ne pas passer par un fichier de properties
                in = new FileInputStream(conf);
                properties.load(in);
                Log.debug("bestInterface = " + properties.getProperty("bestInterface"));
                UDPServer.setBestNI(properties.getProperty("bestInterface"));
                HttpServer.setBestNI(properties.getProperty("bestInterface"));
                

            } catch (Exception e) {
            	e.printStackTrace();
            }
        }
        com = new Com();
        context.registerService(ICom.class.getName(), com, null);
        // try to get a STUN client service
        stunClientServiceRef = context.getServiceReference(ISTUNCLient.class
                .getName());
        if (stunClientServiceRef != null) {
            // get the service object
            ISTUNCLient stunClient = (ISTUNCLient) context
                    .getService(stunClientServiceRef);
            com.setSTUNClient(stunClient);
        }
        // add a service listener
        context.addServiceListener(this, "(" + Constants.OBJECTCLASS + "="
                + ISTUNCLient.class.getName() + ")");
    }
    /**
     * Stop.
     * @param context the context
     * @throws Exception the exception
     */
    public void stop(final BundleContext context) throws Exception {
        com.setRunning(false);
        context.removeServiceListener(this);
    }
    /**
     * <p>
     * This method is called when a service event about the STUN client is
     * emitted.
     * </p>
     */
    public void serviceChanged(final ServiceEvent event) {
        switch (event.getType()) {
        case ServiceEvent.REGISTERED:
            Log.debug("a new STUN client has been detected");
            if (stunClientServiceRef == null) {
                Log.debug("UDP server use the new STUN client");
                stunClientServiceRef = event.getServiceReference();
                // get the STUN client object
                ISTUNCLient stunClient = (ISTUNCLient) bundleContext
                        .getService(stunClientServiceRef);
                this.com.setSTUNClient(stunClient);
            } /* else nothing to do */
            break;
        case ServiceEvent.UNREGISTERING:
            if (stunClientServiceRef == event.getServiceReference()) {
                stunClientServiceRef = null;
                this.com.setSTUNClient(null);
                Log.debug("unregister the STUN client from UDPServer");
            }
            break;
        default:
            break;
        }
    }
}
