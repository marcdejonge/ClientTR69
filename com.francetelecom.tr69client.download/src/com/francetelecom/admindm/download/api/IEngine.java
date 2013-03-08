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
import java.util.List;

import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.soap.Fault;
/**
 * The Interface IDownloadEngine.
 */
public interface IEngine  extends IEngineService {
    /**
     * Gets the last transfer complete.
     * @return the last transfer complete
     */
    List<TransferComplete> getLastTransferComplete();
    /**
     * When the Download task is finished the we remove the download request to
     * the list and set it to lastTransferComplete variable. The
     * lastTransferComplete will only set to null when the a
     * TransferCompleteResponse is received. The transfer is considered as
     * successful only if the downloaded file is applied. If it has failed the
     * file is deleted.
     * @param downloadTask the download task
     */
    void finishDownloadTask(final DelayedTask downloadTask);
    /**
     * Adds the download.
     * @param download the download
     * @return true, if successful
     * @throws Fault the fault
     */
    boolean addDownload(final Download download) throws Fault;
    /**
     * Adds the upload.
     * @param download the download
     * @return true, if successful
     * @throws Fault the fault
     */
    boolean addUpload(final Upload download) throws Fault;
    /**
     * Restore data.
     */
    void restoreData();
    /**
     * Sets the data.
     * @param data the new data
     */
    void setData(IParameterData data);
}
