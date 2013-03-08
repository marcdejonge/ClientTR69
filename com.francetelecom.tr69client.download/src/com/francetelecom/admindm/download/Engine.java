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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;

import org.osgi.framework.BundleContext;

import aQute.bnd.annotation.component.Activate;
import aQute.bnd.annotation.component.Component;
import aQute.bnd.annotation.component.Reference;

import com.francetelecom.admindm.api.FileUtil;
import com.francetelecom.admindm.api.Log;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.download.api.DelayedTask;
import com.francetelecom.admindm.download.api.Download;
import com.francetelecom.admindm.download.api.FaultStruct;
import com.francetelecom.admindm.download.api.IApplyAction;
import com.francetelecom.admindm.download.api.IDownloadProtocol;
import com.francetelecom.admindm.download.api.IEngine;
import com.francetelecom.admindm.download.api.IUploadProtocol;
import com.francetelecom.admindm.download.api.TransferComplete;
import com.francetelecom.admindm.download.api.Upload;
import com.francetelecom.admindm.model.EventStruct;
import com.francetelecom.admindm.model.IParameterData;
import com.francetelecom.admindm.soap.Fault;
import com.francetelecom.admindm.soap.FaultUtil;

/**
 * The Class Engine. The aim of is class is to schedule the download or upload.
 * @author Olivier Beyler - OrangeLabs -
 */
@Component(immediate=true, provide=IEngine.class)
public final class Engine implements IEngine {
    
    /** The max transfer queue. */
	private static final int MAX_TRANSFER_QUEUE = 100;

	/** The Constant Number of millisecond per second. */
	private static final int NB_MS_PER_SECOND = 1000;

	/** The list apply actions. */
    private final List<IApplyAction> lsApplyActions = new ArrayList<IApplyAction>();
    
    /**
	 * Adds an apply action.
	 * @param action the action
	 */
	@Reference(dynamic=true, multiple=true, optional=true)
	public void addApplyAction(final IApplyAction action) {
	    lsApplyActions.add(action);
	}

	/**
	 * Remove an apply action.
	 * @param action the action
	 */
	public void removeApplyAction(final IApplyAction action) {
	    lsApplyActions.remove(action);
	}

	/** List protocols available to manage a download request. */
    private List<IDownloadProtocol> lsDownloadProtocol = new ArrayList<IDownloadProtocol>();
    
    /**
	 * Adds the download protocol.
	 * @param protocol the protocol
	 */
    @Reference(dynamic=true, multiple=true, optional=true)
	public void addDownloadProtocol(final IDownloadProtocol protocol) {
	    lsDownloadProtocol.add(protocol);
	}

	/**
	 * Removes the download protocol.
	 * @param protocol the protocol
	 */
	public void removeDownloadProtocol(final IDownloadProtocol protocol) {
	    lsDownloadProtocol.remove(protocol);
	}

	/** List protocols available to manage a upload request. */
    private List<IUploadProtocol> lsUploadProtocol = new ArrayList<IUploadProtocol>();
    
    /**
	 * Adds the upload protocol.
	 * @param protocol the protocol
	 */
    @Reference(dynamic=true, multiple=true, optional=true)
	public void addUploadProtocol(final IUploadProtocol protocol) {
	    lsUploadProtocol.add(protocol);
	}

	/**
	 * Removes the upload protocol.
	 * @param protocol the protocol
	 */
	public void removeUploadProtocol(final IUploadProtocol protocol) {
	    lsUploadProtocol.remove(protocol);
	}

	/** The data file. */
	private transient File dataFile = null;

	/** The list file transfer queue. */
	private List<RPCMethod> lsFileTransferQueue = new ArrayList<RPCMethod>();

	/** The parameter data. */
	private IParameterData parameterData;

	/**
	 * The last transfer complete. Store the last transfer Complete to re send
	 * it until the transfer response has been receive to be sure that the ACS
	 * is notified of the transfer.
	 */
	private List<TransferComplete> lsLastTransferComplete = Collections.synchronizedList(new ArrayList<TransferComplete>());

	/**
     * The Constructor.
     */
    public Engine() {
        lsDownloadProtocol.add(new HTTPDownloadProtocol());
        lsUploadProtocol.add(new FTPUploadProtocol());
    }
    
    @Activate
    public void activate(BundleContext context) {
    	dataFile = context.getDataFile("download.data");
    }
    
    /**
	 * Gets the last transfer complete.
	 * @return the last transfer complete
	 */
	public List<TransferComplete> getLastTransferComplete() {
	    return lsLastTransferComplete;
	}

	/**
	 * When the Download task is finished the we remove the download request to
	 * the list and set it to lastTransferComplete variable. The
	 * lastTransferComplete will only set to null when the a
	 * TransferCompleteResponse is received. The transfer is considered as
	 * successful only if the downloaded file is applied. If it has failed the
	 * file is deleted.
	 * @param downloadTask the download task
	 */
	public void finishDownloadTask(final DelayedTask downloadTask) {
	    TransferComplete transfer = downloadTask.getTransfer();
	    Download download = downloadTask.getDownload();
	    getLastTransferComplete().add(transfer);
	    lsFileTransferQueue.remove(download);
	    Iterator<IApplyAction> itAction = lsApplyActions.iterator();
	    boolean isApply = false;
	    String typeFile = download.getFileType();
	    String filename = download.getTargetFileName();
	    boolean needReboot = false;
	    try {
	        while (itAction.hasNext() && !isApply) {
	            IApplyAction action = itAction.next();
	            if (action.isApplicable(typeFile)) {
	                needReboot = action.apply(filename);
	                isApply = true;
	            }
	        }
	        if (!isApply) {
	            StringBuffer error = new StringBuffer(FaultUtil.STR_FAULT_9010);
	            error.append(": don't know how to apply '");
	            error.append(typeFile);
	            error.append("'");
	            throw new Fault(FaultUtil.FAULT_9010, error.toString());
	        }
	        if (needReboot) {
	            // TODO suggest to reboot
	        }
	    } catch (Fault e) {
	        FaultStruct fault;
	        fault = transfer.getFaultStruct();
	        fault.setFaultCode(e.getFaultcode());
	        fault.setFaultString(e.getFaultstring());
	        File file = new File(filename);
	        file.delete();
	    }
	    Iterator<EventStruct> itEvent = transfer.getLsEvent().iterator();
	    while (itEvent.hasNext()) {
	        parameterData.addEvent(itEvent.next());
	    }
	    parameterData.addOutgoingRequest(downloadTask.getTransfer());
	    storeData();
	}

	/**
	 * Store data will persist only the lastTranfertComplete queue and the list
	 * of download request. The download requests have to be done even in case
	 * of reboot.
	 */
	protected void storeData() {
	    try {
	        dataFile.createNewFile();
	        FileOutputStream ostream = new FileOutputStream(dataFile);
	        ObjectOutputStream p = new ObjectOutputStream(ostream);
	        p.writeObject(lsLastTransferComplete);
	        p.writeObject(lsFileTransferQueue);
	        ostream.close();
	    } catch (IOException e) {
	        // should not occurred.
	        Log.error(e.getLocalizedMessage(), e);
	    }
	}

	/**
     * Clear file transfer queue.
     */
    protected void clearFileTransferQueue() {
        lsFileTransferQueue.clear();
    }
    
    /**
     * Adds the download.
     * @param download the download
     * @return true, if successful
     * @throws Fault the fault
     */
    public boolean addDownload(final Download download) throws Fault {
        return addDownload(download, false);
    }
    
    /**
     * Adds the download.
     * @param download the download
     * @return true, if successful
     * @throws Fault the fault
     */
    public boolean addAutonomousDownload(final Download download) throws Fault {
        return addDownload(download, true);
    }
    
    /**
     * Adds the download.
     * @param download the download
     * @param isAutonomous the is autonomous
     * @return true, if successful
     * @throws Fault the fault
     */
    public boolean addDownload(final Download download, boolean isAutonomous)
            throws Fault {
        checkFileCPETransferQueue();
        IDownloadProtocol protocol = findProtocolAvailability(download);
        DelayedTask dt;
        dt = new DelayedTask(download, protocol, this, isAutonomous);
        lsFileTransferQueue.add(download);
        storeData();
        if (isMulticastDownload(download.getFailureURL())) {
            // Download must start as soon as possible
            new Timer().schedule(dt, 0);
        } else {
            new Timer().schedule(dt, download.getDelaySeconds()
                    * NB_MS_PER_SECOND);
        }
        return true;
    }
    
    /**
     * Checks if is multicast download.
     * @param url the url
     * @return true, if is multicast download
     * @throws Fault the fault
     */
    protected static boolean isMulticastDownload(final String url)
            throws Fault {
        boolean result = false;
        int position = url.indexOf("://");
        if (position > 0) {
            position = position + "://".length();
        } else {
            position = 0;
        }
        int lastPosition = url.indexOf('/', position);
        if (lastPosition == -1) {
            lastPosition = url.length();
        }
        String address = url.substring(position, lastPosition);
        try {
            InetAddress adress = InetAddress.getByName(address);
            result = adress.isMulticastAddress();
        } catch (UnknownHostException e) {
            StringBuffer error = new StringBuffer(FaultUtil.STR_FAULT_9013);
            error.append(": unable to determine if host '");
            error.append(url);
            error.append("' is or not a multicast address.");
            error.append("This host is unknown.");
            throw new Fault(FaultUtil.FAULT_9013, error.toString(), e);
        }
        return result;
    }
    
    /**
     * Find protocol availability.
     * @param download the download
     * @return the i download protocol
     * @throws Fault the fault
     */
    public IDownloadProtocol findProtocolAvailability(final Download download)
            throws Fault {
        String url = download.getUrl();
        Iterator<IDownloadProtocol> it = lsDownloadProtocol.iterator();
        while (it.hasNext()) {
            IDownloadProtocol protocol = it.next();
            if (protocol.isPrefixCompatible(url)) {
                return protocol;
            }
        }
        StringBuffer error = new StringBuffer(FaultUtil.STR_FAULT_9013);
        throw new Fault(FaultUtil.FAULT_9013, error.toString());
    }
    
    /**
     * Set the filename of the upload in regards of the type file and find
     * protocol availability.
     * @param upload the upload
     * @return the i upload protocol
     * @throws Fault the fault
     */
    public IUploadProtocol findProtocolAvailability(final Upload upload)
            throws Fault {
        File file = FileUtil.getFileFromShortName(upload.getFileType());
        if (file == null) {
            StringBuffer error = new StringBuffer(FaultUtil.STR_FAULT_9007);
            error.append(": filetype ");
            error.append(upload.getFileType());
            error.append(" is not supported by this CPE.");
            throw new Fault(FaultUtil.FAULT_9007, error.toString());
        }
        upload.setFilename(file.getAbsolutePath());
        String url = upload.getUrl();
        Iterator<IUploadProtocol> it = lsUploadProtocol.iterator();
        while (it.hasNext()) {
            IUploadProtocol protocol = it.next();
            if (protocol.isPrefixCompatible(url)) {
                return protocol;
            }
        }
        StringBuffer error = new StringBuffer(FaultUtil.STR_FAULT_9013);
        error.append(": protocol is not supported.");
        throw new Fault(FaultUtil.FAULT_9013, error.toString());
    }
    
    /**
     * Check file CPE transfer queue.
     * @throws Fault the fault
     */
    private void checkFileCPETransferQueue() throws Fault {
        if (lsFileTransferQueue.size() >= MAX_TRANSFER_QUEUE) {
            StringBuffer error = new StringBuffer(FaultUtil.STR_FAULT_9004);
            error.append(": the max download queue is already reached.");
            throw new Fault(FaultUtil.FAULT_9004, error.toString());
        }
    }
        
    /**
     * Restore data.
     */
    @SuppressWarnings("unchecked")
	public void restoreData() {
        if (dataFile != null && dataFile.exists()) {
            try {
                FileInputStream istream = new FileInputStream(dataFile);
                ObjectInputStream p = new ObjectInputStream(istream);
                lsLastTransferComplete = (List<TransferComplete>) p.readObject();
                lsFileTransferQueue = (ArrayList<RPCMethod>) p.readObject();
                istream.close();
            } catch (FileNotFoundException e) {
                // could be normal if no saved file is exist.
                Log.debug("No file to restore for dowload");
            } catch (IOException e) {
                Log.error("should not occurred.", e);
            } catch (ClassNotFoundException e) {
                Log.error("should not occurred.", e);
            }
        }
    }
    
    /**
     * Sets the data.
     * @param data the data
     */
    public void setData(IParameterData data) {
        this.parameterData = data;
    }
    
    /* (non-Javadoc)
     * @see com.francetelecom.admindm.download.IEngine#addUpload(com.francetelecom.admindm.download.api.Upload)
     */
    public boolean addUpload(final Upload upload) throws Fault {
        return addUpload(upload, false);
    }
    
    /**
     * Adds the autonomous upload.
     * @param upload the upload
     * @return true, if successful
     * @throws Fault the fault
     */
    public boolean addAutonomousUpload(final Upload upload) throws Fault {
        return addUpload(upload, true);
    }
    
    /**
     * Adds the upload.
     * @param upload the upload
     * @param isAutonomous the is autonomous
     * @return true, if adds the upload
     * @throws Fault the fault
     */
    public boolean addUpload(final Upload upload, boolean isAutonomous)
            throws Fault {
        checkFileCPETransferQueue();
        IUploadProtocol protocol = findProtocolAvailability(upload);
        DelayedTask dt;
        dt = new DelayedTask(upload, protocol, this, isAutonomous);
        lsFileTransferQueue.add(upload);
        storeData();
        new Timer().schedule(dt, upload.getDelaySeconds() * NB_MS_PER_SECOND);
        return true;
    }
    
    /**
     * Adds the autonomous up load.
     * @param upload the upload
     * @return true, if adds the autonomous up load
     * @throws Fault the fault
     */
    public boolean addAutonomousUpLoad(Upload upload) throws Fault {
        return addUpload(upload,true);
    }
}
