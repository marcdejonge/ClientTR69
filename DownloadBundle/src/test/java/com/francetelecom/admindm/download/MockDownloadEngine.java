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
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import com.francetelecom.admindm.download.api.Download;
import com.francetelecom.admindm.download.api.IApplyAction;
import com.francetelecom.admindm.download.api.IDownloadProtocol;
import com.francetelecom.admindm.download.api.IUploadProtocol;
import com.francetelecom.admindm.download.api.Upload;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.soap.Fault;
/**
 * The Class MockDownloadEngine.
 */
public final class MockDownloadEngine implements IEngine {
    public MockDownloadEngine(){        
    }
    /**
     * Adds the apply action.
     * @param action the action
     */
    public void addApplyAction(IApplyAction action) {
    }
    /**
     * Adds the download.
     * @param download the download
     * @return true, if adds the download
     * @throws Fault the fault
     */
    public boolean addDownload(Download download) throws Fault {
        return true;
    }
    /** The protocol. */
    public IDownloadProtocol protocol;
    /**
     * Find protocol availability.
     * @param download the download
     * @return the i download protocol
     * @throws Fault the fault
     */
    public IDownloadProtocol findProtocolAvailability(Download download)
            throws Fault {
        return protocol;
    }
    /**
     * Finish download task.
     * @param downloadTask the download task
     */
    public void finishDownloadTask(DelayedTask downloadTask) {
    }
    /**
     * Removes the apply action.
     * @param action the action
     */
    public void removeApplyAction(IApplyAction action) {
    }
    /**
     * Restore data.
     */
    public void restoreData() {
    }
    /**
     * Sets the datafile.
     * @param dataFile the data file
     */
    public void setDatafile(File dataFile) {
    }
    /** The ls last transfert complete. */
    public List lsLastTransfertComplete = new ArrayList();
    /**
     * Gets the last transfer complete.
     * @return the last transfer complete
     */
    public List getLastTransferComplete() {
        return lsLastTransfertComplete;
    }
    /**
     * Adds the download protocol.
     * @param protocol the protocol
     */
    public void addDownloadProtocol(IDownloadProtocol protocol) {
    }
    /**
     * Removes the all apply actions.
     */
    public void removeAllApplyActions() {
    }
    /**
     * Removes the all download protocols.
     */
    public void removeAllDownloadProtocols() {
    }
    /**
     * Removes the download protocol.
     * @param protocol the protocol
     */
    public void removeDownloadProtocol(IDownloadProtocol protocol) {
    }
    public void setData(IParameterData data) {        
    }
    public boolean addUpload(Upload download) throws Fault {
        // TODO Auto-generated method stub
        return false;
    }
    public void addUploadProtocol(IUploadProtocol protocol) {
        // TODO Auto-generated method stub
        
    }
    public void removeAllUploadProtocols() {
        // TODO Auto-generated method stub
        
    }
    public void removeUploadProtocol(IUploadProtocol protocol) {
        // TODO Auto-generated method stub
        
    }
    public boolean addAutonomousDownload(Download download) throws Fault {
        // TODO Auto-generated method stub
        return false;
    }
    public boolean addAutonomousUpLoad(Upload upload) throws Fault {
        // TODO Auto-generated method stub
        return false;
    }
}
