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
import java.util.TimerTask;
import com.francetelecom.admindm.api.EventCode;
import com.francetelecom.admindm.api.Log;
import com.francetelecom.admindm.model.EventStruct;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.admindm.soap.FaultUtil;
/**
 * The Class DownloadTask.
 */
public final class DelayedTask extends TimerTask {
    /** The download. */
    private Download download = null;
    /** The upload. */
    private Upload upload = null;
    
    private boolean isAutonomous ;
    /**
     * Gets the transfer.
     * @return the transfer
     */
    public TransferComplete getTransfer() {
        return transfer;
    }
    /**
     * Gets the download.
     * @return the download
     */
    public Download getDownload() {
        return download;
    }
    /**
     * Gets the download.
     * @return the download
     */
    public Upload getUpload() {
        return upload;
    }

    /** The protocol. */
    private IDownloadProtocol protocol = null;
    /** The protocol. */
    private IUploadProtocol protocolupload = null;
    /**
     * Instantiates a new download task.
     * @param pDownload the download
     * @param pProtocol the protocol
     * @param pEngine the engine
     */
    public DelayedTask(final Download pDownload, 
            final IDownloadProtocol pProtocol, 
            final IEngine pEngine, 
            final boolean pIsAutonomous ) {
        this.download = pDownload;
        this.protocol = pProtocol;
        this.engine = pEngine;
        this.isAutonomous = pIsAutonomous;
    }
    /**
     * Instantiates a new download task.
     * @param pDownload the download
     * @param pProtocol the protocol
     * @param pEngine the engine
     */
    public DelayedTask(final Upload pUpload,
            final IUploadProtocol pProtocol,
            final IEngine pEngine,
            final boolean pIsAutonomous ) {
        this.upload = pUpload;
        this.protocolupload = pProtocol;
        this.engine = pEngine;
        this.isAutonomous = pIsAutonomous;
    }

    /** The engine. */
    private final IEngine engine;
    /** The transfer. */
    private TransferComplete transfer;
    /** The Constant one Second in Ms. */
    private static final long ONE_SECOND = 1000;
    /** The Constant one hour in Ms. */
    private static final long ONE_HOUR = 3600 * ONE_SECOND;
    /**
     * Run is synchronized to be sure that two download can not be done
     * simultaneously. If a download hasn't receive the transfer Response we
     * need to wait until it.
     * @see java.util.TimerTask#run()
     */
    public synchronized void run() {
        Log.info("====================================");
        
        if (isAutonomous) {
            Log.info("try to do autonomous transfert");
        }
        if (download != null) {
                Log.info("try to download:" + download.getUrl());
            }
        if (upload != null) {
            Log.info("try to upload:" + upload.getUrl());
        }
        Log.info("====================================");
        transfer = new TransferComplete();
        transfer.setCommandKey(download.getCommandKey());
        transfer.setCompleteTime(0);
        transfer.setId(download.getName() + download.getUrl());
        List ls = transfer.getLsEvent();
        ls.add(new EventStruct(EventCode.TRANSFER_COMPLETE, ""));
        ls.add(new EventStruct(EventCode.M_DOWNLOAD, download.getCommandKey()));
        try {
            checkDelay(download);
            transfer.setStartTime(System.currentTimeMillis());
            if (protocol != null) {
                protocol.onDownload(download);
            } else {
                protocolupload.onUpload(upload);
            }
            transfer.setCompleteTime(System.currentTimeMillis());
            transfer.getFaultStruct().setFaultCode(0);
            transfer.getFaultStruct().setFaultString("");
        } catch (Fault e) {
            transfer.getFaultStruct().setFaultCode(e.getFaultcode());
            transfer.getFaultStruct().setFaultString(e.getFaultstring());
        } catch (Exception e) {
            transfer.setStartTime(0);
            transfer.setCompleteTime(0);
            transfer.getFaultStruct().setFaultCode(FaultUtil.FAULT_9010);
            transfer.getFaultStruct().setFaultString(FaultUtil.STR_FAULT_9010);
        }
        engine.finishDownloadTask(this);
    }
    /**
     * Check delay.
     * @param download the download
     * @throws Fault the fault
     */
    protected static void checkDelay(final Download download) throws Fault {
        long time = System.currentTimeMillis();
        long maxTime = download.getCreationDate() + ONE_HOUR
                + download.getDelaySeconds() * ONE_SECOND;
        if (time > maxTime) {
            throw new Fault(FaultUtil.FAULT_9010, FaultUtil.STR_FAULT_9010
                    + ": out of delay");
        }
    }
}
