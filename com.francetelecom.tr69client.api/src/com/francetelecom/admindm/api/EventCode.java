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
/**
 * The Interface EventCode.
 */
public interface EventCode {
    /** The Constant BOOTSTRAP. */
    String BOOTSTRAP = "0 BOOTSTRAP";
    /** The Constant BOOT. */
    String BOOT = "1 BOOT";
    /** The Constant PERIODIC. */
    String PERIODIC = "2 PERIODIC";
    /** The Constant SCHEDULED. */
    String SCHEDULED = "3 SCHEDULED";
    /** The Constant VALUE_CHANGE. */
    String VALUE_CHANGE = "4 VALUE CHANGE";
    /** The Constant KICKED. */
    String KICKED = "5 KICKED";
    /** The Constant CONNECTION_REQUEST. */
    String CONNECTION_REQUEST = "6 CONNECTION REQUEST";
    /** The Constant TRANSFER_COMPLETE. */
    String TRANSFER_COMPLETE = "7 TRANSFER COMPLETE";
    /** The Constant DIAGNOSTICS_COMPLETE. */
    String DIAGNOSTICS_COMPLETE = "8 DIAGNOSTICS COMPLETE";
    /** The Constant REQUEST_DOWNLOAD. */
    String REQUEST_DOWNLOAD = "9 REQUEST DOWNLOAD";
    /** The Constant AUTONOMOUS_TRANSFER_COMPLETE. */
    String AUTONOMOUS_TRANSFER_COMPLETE = "10 AUTONOMOUS TRANSFER COMPLETE";
    /** The Constant M_REBOOT. */
    String M_REBOOT = "M Reboot";
    /** The Constant M_SCHEDULE_INFORM. */
    String M_SCHEDULE_INFORM = "M ScheduleInform";
    /** The Constant M_DOWNLOAD. */
    String M_DOWNLOAD = "M Download";
    /** The Constant M_UPLOAD. */
    String M_UPLOAD = "M Upload";
    /** The CPE MUST NOT ever discard an undelivered BOOTSTRAP event. */
    int DISCARD_OTHER_EVENTS = -1;
    /** The Constant NEVER_RETRY. */
    int NEVER_RETRY = 0;
    /** The Constant ALWAYS_RETRY. */
    int ALWAYS_RETRY = 1;
    /** The Constant RETRY_UNTIL_REBOOT. */
    int RETRY_UNTIL_REBOOT = 2;
    /** The Constant MUST_NOT_EVER_DISCARD. */
    int MUST_NOT_EVER_DISCARD = 3;
}
