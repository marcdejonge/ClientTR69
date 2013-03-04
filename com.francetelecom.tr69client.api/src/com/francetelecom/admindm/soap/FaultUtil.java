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
package com.francetelecom.admindm.soap;
/**
 * The Class FaultUtil.
 */
public final class FaultUtil {
    /**
     * Hide the default constructor.
     */
    private FaultUtil() {
    }
    /**
     * The Constant FAULT_9000. Method not supported.
     */
    public static final int FAULT_9000 = 9000;
    /**
     * The Constant FAULT_9001. Request denied (no reason specified).
     */
    public static final int FAULT_9001 = 9001;
    /**
     * The Constant FAULT_9002. Internal error.
     * */
    public static final int FAULT_9002 = 9002;
    /**
     * The Constant FAULT_9003. Invalid arguments.
     */
    public static final int FAULT_9003 = 9003;
    /**
     * The Constant FAULT_9004. Resources exceeded.
     */
    public static final int FAULT_9004 = 9004;
    /**
     * The Constant FAULT_9005. Invalid parameter name.
     */
    public static final int FAULT_9005 = 9005;
    /**
     * The Constant FAULT_9006. Invalid parameter type.
     */
    public static final int FAULT_9006 = 9006;
    /**
     * The Constant FAULT_9007. Invalid parameter value
     */
    public static final int FAULT_9007 = 9007;
    /**
     * The Constant FAULT_9008. Attempt to set a non-writable parameter.
     */
    public static final int FAULT_9008 = 9008;
    /**
     * The Constant FAULT_9009. Notification request rejected.
     */
    public static final int FAULT_9009 = 9009;
    /**
     * The Constant FAULT_9010. Download failure.
     */
    public static final int FAULT_9010 = 9010;
    /**
     * The Constant FAULT_9011. Upload failure.
     */
    public static final int FAULT_9011 = 9011;
    /**
     * The Constant FAULT_9012. File transfer server authentication failure.
     */
    public static final int FAULT_9012 = 9012;
    /**
     * The Constant FAULT_9013. Unsupported protocol for file transfer.
     */
    public static final int FAULT_9013 = 9013;
    /**
     * The Constant FAULT_9014. Download failure: unable to join multicast
     * group.
     */
    public static final int FAULT_9014 = 9014;
    /**
     * The Constant FAULT_9015. Download failure: unable to join file server.
     */
    public static final int FAULT_9015 = 9015;
    /**
     * The Constant FAULT_9016. Download failure: unable to access file.
     */
    public static final int FAULT_9016 = 9016;
    /**
     * The Constant FAULT_9017. Download failure: unable to complete download.
     */
    public static final int FAULT_9017 = 9017;
    /**
     * The Constant FAULT_9018. Download failure: file corrupted.
     */
    public static final int FAULT_9018 = 9018;
    /**
     * The Constant FAULT_9019. Download failure: file authentication failure.
     */
    public static final int FAULT_9019 = 9019;
    /** The Constant STR_FAULT_9000. */
    public static final String STR_FAULT_9000 = "Method not supported";
    /** The Constant STR_FAULT_9001. */
    public static final String STR_FAULT_9001 =
        "Request denied (no reason specified)";
    /**
     * The Constant STR_FAULT_9002.<br/>
     * Internal error.
     */
    public static final String STR_FAULT_9002 = "Internal error";
    /**
     * The Constant STR_FAULT_9003.<br/>
     * Invalid arguments.
     */
    public static final String STR_FAULT_9003 = "Invalid arguments";
    /**
     * The Constant STR_FAULT_9004.<br/>
     * Invalid arguments.
     */
    public static final String STR_FAULT_9004 = "Resources exceeded";
    /** 
     * The Constant STR_FAULT_9005.<br/>
     * Invalid parameter name.
     */
    public static final String STR_FAULT_9005 = "Invalid parameter name";
    /**
     * The Constant STR_FAULT_9006.<br/>
     * Invalid parameter type.
     */
    public static final String STR_FAULT_9006 = "Invalid parameter type";
    /**
     * The Constant STR_FAULT_9007.<br/>
     * Invalid parameter value.
     */
    public static final String STR_FAULT_9007 = "Invalid parameter value";
    /** 
     * The Constant STR_FAULT_9008.<br/>
     * Attempt to set a non-writable parameter.
     */
    public static final String STR_FAULT_9008 =
        "Attempt to set a non-writable parameter";
    /**
     * The Constant STR_FAULT_9009.<br/>
     * Notification request rejected.
     */
    public static final String STR_FAULT_9009 = "Notification request rejected";
    /**
     * The Constant STR_FAULT_9010.<br/>
     * Download failure.
     */
    public static final String STR_FAULT_9010 = "Download failure";
    /**
     * The Constant STR_FAULT_9011.<br/>
     * Upload failure.
     */
    public static final String STR_FAULT_9011 = "Upload failure";
    /** 
     * The Constant STR_FAULT_9012.<br/>
     * File transfer server authentification failure.
     */
    public static final String STR_FAULT_9012 =
        "File transfer server authentification failure";
    /** The Constant STR_FAULT_9013. */
    public static final String STR_FAULT_9013 =
        "Unsupported protocol for file transfer";
    /** The Constant STR_FAULT_9014. */
    public static final String STR_FAULT_9014 =
        "Download failure: unable to join multicast group";
    /** The Constant STR_FAULT_9015. */
    public static final String STR_FAULT_9015 =
        "Download failure: unable to join file server";
    /** The Constant STR_FAULT_9016. */
    public static final String STR_FAULT_9016 =
        "Download failure: unable to access file";
    /** The Constant STR_FAULT_9017. */
    public static final String STR_FAULT_9017 =
        "Download failure: unable to complete download";
    /** The Constant STR_FAULT_9018. */
    public static final String STR_FAULT_9018 =
        "Download failure: file corrupted";
    /** The Constant STR_FAULT_9019. */
    public static final String STR_FAULT_9019 =
        "Download failure: file authentification failure";
    /** The Constant FAULT_8005. */
    public static final int FAULT_8005 = 8005;
    /**
     * Give predefine type.
     * @param code value
     * @return the predefined type for this code
     */
    public static String getType(final int code) {
        String result = "Server";
        if (code == FAULT_9003 || code == FAULT_9005 || code == FAULT_9006
                || code == FAULT_9007 || code == FAULT_9008) {
            result = "Client";
        }
        return result;
    }
}
