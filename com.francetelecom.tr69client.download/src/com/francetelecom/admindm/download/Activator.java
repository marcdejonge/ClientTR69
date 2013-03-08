/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : 1.1
 * Module Name : DownloadBundle
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
package com.francetelecom.admindm.download;
import java.util.Collections;
import java.util.Hashtable;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import aQute.bnd.annotation.component.Activate;
import aQute.bnd.annotation.component.Component;
import aQute.bnd.annotation.component.Deactivate;

import com.francetelecom.admindm.api.EventBehavior;
import com.francetelecom.admindm.api.EventCode;
/**
 * The Class Activator.
 */
@Component
public final class Activator {
    /** The Constant EVT_BEHAVIOR_M_DOWNLOAD. */
    private static final EventBehavior EVT_BEHAVIOR_M_DOWNLOAD =
        new EventBehavior(
                false, EventCode.ALWAYS_RETRY, TransferCompleteResponse.NAME);
    /** The Constant EVT_BEHAVIOR_TRANSFER_COMPLETE. */
    private static final EventBehavior EVT_BEHAVIOR_TRANSFER_COMPLETE =
        new EventBehavior(
            true, EventCode.ALWAYS_RETRY, TransferCompleteResponse.NAME);

    private ServiceRegistration<EventBehavior> downloadRegistration;
	private ServiceRegistration<EventBehavior> transferRegistration;
 
    /**
     * Start.
     * @param context the context
     * @throws Exception the exception
     */
    @Activate
    public void start(final BundleContext context) throws Exception {
    	downloadRegistration = context.registerService(EventBehavior.class, 
    					                               EVT_BEHAVIOR_M_DOWNLOAD, 
    					                               new Hashtable<String, String>(Collections.singletonMap("name", EventCode.M_DOWNLOAD)));
    	transferRegistration = context.registerService(EventBehavior.class, 
    			                                       EVT_BEHAVIOR_TRANSFER_COMPLETE, 
    			                                       new Hashtable<String, String>(Collections.singletonMap("name", EventCode.TRANSFER_COMPLETE)));
    }

    /**
     * Stop.
     * @param context the context
     * @throws Exception the exception
     */
    @Deactivate
    public void stop(final BundleContext context) throws Exception {
    	downloadRegistration.unregister();
    	transferRegistration.unregister();
    }
}
