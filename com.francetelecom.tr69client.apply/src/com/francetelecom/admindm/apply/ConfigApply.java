/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : 1.1
 * Module Name : ApplyBundle
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
package com.francetelecom.admindm.apply;
import com.francetelecom.admindm.soap.Fault;
/**
 * The Class ConfigApply.
 */
public final class ConfigApply extends AbstractApply {
	
	/**
	 * Instantiates a new config apply.
	 * 
	 * @param pTracker the tracker
	 */
	public ConfigApply() {
	}
	
	
    /**
     * Always accept the file. if you won't accept it because the file is
     * corrupted call rejectBadFile(name) for other cause just return false.
     * @param name the name
     * @return false (no reboot needed)
     * @throws Fault the fault
     */
    public boolean apply(final String name) throws Fault {
        return false;
    }
    /**
     * Checks if is applicable.
     * @param fileType the file type
     * @return true, if checks if is applicable
     */
    public boolean isApplicable(final String fileType) {
        return "3 Vendor Configuration File".equals(fileType);
    }
}
