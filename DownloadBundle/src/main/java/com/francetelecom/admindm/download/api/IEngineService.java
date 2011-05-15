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
 * The Interface IEngineService.
 */
public interface IEngineService {    
    /**
     * Adds the autonomous download.
     * @param download the download
     * @return true, if successful
     * @throws Fault the fault
     */
    boolean addAutonomousDownload(final Download download) throws Fault;
    
    /**
     * Adds the autonomous up load.
     * @param upload the upload
     * @return true, if successful
     * @throws Fault the fault
     */
    boolean addAutonomousUpLoad(final Upload upload) throws Fault;
}
