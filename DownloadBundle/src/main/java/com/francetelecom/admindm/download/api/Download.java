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
import com.francetelecom.admindm.api.Session;
import com.francetelecom.admindm.download.IEngine;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.admindm.soap.FaultUtil;
/**
 * The Class Download.
 */
public final class Download extends Base {
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    /** The file size. */
    private long fileSize = 0;
    /** The target file name. */
    private String targetFileName = "";
    /** The success url. */
    private String successURL = "";
    /** The failure url. */
    private String failureURL = "";
    /**
     * The Constructor.
     * @param pEngine the engine
     */
    public Download(final IEngine pEngine) {
        super(pEngine);
    }
    /**
     * Gets the file size.
     * @return the file size
     */
    public final long getFileSize() {
        return fileSize;
    }
    /**
     * Sets the file size.
     * @param pFileSize the new file size
     */
    public final void setFileSize(final long pFileSize) {
        this.fileSize = pFileSize;
    }
    /**
     * Gets the target file name.
     * @return the target file name
     */
    public final String getTargetFileName() {
        return targetFileName;
    }
    /**
     * Sets the target file name.
     * @param pTargetFileName the new target file name
     */
    public final void setTargetFileName(final String pTargetFileName) {
        this.targetFileName = pTargetFileName;
    }
    /** The Constant NAME. */
    public static final String NAME = "Download";
    /**
     * Gets the name.
     * @return the name
     * @see com.francetelecom.admindm.api.RPCMethod#getName()
     */
    public String getName() {
        return NAME;
    }
    
    /**
     * Gets the success url.
     * @return the success url
     */
    public final String getSuccessURL() {
        return successURL;
    }
    /**
     * Sets the success url.
     * @param pSuccessURL the new success url
     */
    public final void setSuccessURL(final String pSuccessURL) {
        this.successURL = pSuccessURL;
    }
    /**
     * Gets the failure url.
     * @return the failure url
     */
    public final String getFailureURL() {
        return failureURL;
    }
    /**
     * Sets the failure url.
     * @param pFailureURL the new failure url
     */
    public final void setFailureURL(final String pFailureURL) {
        this.failureURL = pFailureURL;
    }
    /**
     * Perform.
     * @param session the session
     * @throws Fault the fault
     */
    public void perform(final Session session) throws Fault {
        // always add the download request to the download engine and answers
        // that the download isn't yet finished.
        if (getEngine() == null) {
            // The Engine must not be null
            // The engine must be set
            throw new Fault(FaultUtil.FAULT_9002, FaultUtil.STR_FAULT_9002);
        }
        getEngine().addDownload(this);
        DownloadResponse response = new DownloadResponse();
        response.setStatus(1);
        response.setStartTime(0);
        response.setCompleteTime(0);
        session.doASoapResponse(response);
    }
}
