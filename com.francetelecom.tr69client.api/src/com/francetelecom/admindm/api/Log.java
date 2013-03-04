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
package com.francetelecom.admindm.api;
import org.osgi.service.log.LogService;
/**
 * The Class Log.
 */
public final class Log {

    /**
     * Instantiates a new log.
     */
    private Log() {
    }

    /** The log. */
    private static LogService log;
    /**
     * Sets the log service.
     * @param pLog the new log service
     */
    public static void setLogService(final LogService pLog) {
        log = pLog;
    }
    /**
     * Warn.
     * @param msg the msg
     */
    public static void warn(final String msg) {
        if (log != null) {
            log.log(LogService.LOG_WARNING, msg);
        }
    }
    /**
     * Debug.
     * @param msg the msg
     */
    public static void debug(final String msg) {
        if (log != null) {
            log.log(LogService.LOG_DEBUG, msg);
        }
    }
    /**
     * Info.
     * @param msg the msg
     */
    public static void info(final String msg) {
        if (log != null) {
            log.log(LogService.LOG_INFO, msg);
        }
    }
    /**
     * Error.
     * @param msg the msg
     */
    public static void error(final String msg) {
        if (log != null) {
            log.log(LogService.LOG_ERROR, msg);
        }
    }
    /**
     * Error.
     * @param msg the msg
     * @param e the exception
     */
    public static void error(final String msg, final Exception e) {
        if (log != null) {
            log.log(LogService.LOG_ERROR, msg, e);
        }
    }
}
