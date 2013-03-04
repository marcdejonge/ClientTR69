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

package com.francetelecom.admindm.download.api;

import com.francetelecom.admindm.soap.Fault;

/**
 * The Interface ApplyAction.
 */
public interface IApplyAction {

    /**
     * Checks if the file type is applicable for applying this action.
     * @param fileType the file type
     * @return true, if checks if is applicable
     */
    boolean isApplicable(final String fileType);

    /**
     * Apply the file.
     * @param name the name of the file
     * @return true if need to reboot
     * @throws Fault the fault
     */
    boolean apply(final String name)throws Fault;
}
