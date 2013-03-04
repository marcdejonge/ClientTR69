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
import java.io.Serializable;
import com.francetelecom.admindm.api.RPCMethod;
import com.francetelecom.admindm.api.Session;
import com.francetelecom.admindm.soap.Fault;
/**
 * The Class Download.
 */
public abstract class Base implements RPCMethod, Serializable {
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    /** The engine must be re affected when the  Download is loaded from file.*/
    private transient IEngine engine;
    /**
     * Gets the engine.
     * @return the engine
     */
    public final IEngine getEngine() {
        return engine;
    }
    /**
     * The Constructor.
     * @param pEngine the engine
     */
    public Base(final IEngine pEngine) {
        this.engine = pEngine;
    }
    /**
     * Date of reception of the Download RPCCommand. This specific field allow
     * to determinate if the download is still required.
     */
    private long creationDate = System.currentTimeMillis();
    /** The command key. */
    private String commandKey = "";
    /** The file type. */
    private String fileType = "";
    /** The url. */
    private String url = "";
    /** The username. */
    private String username = "";
    /** The password. */
    private String password = "";
    /** The delay seconds. */
    private long delaySeconds = 0;
    
    
    /**
     * Gets the command key.
     * @return the command key
     */
    public final String getCommandKey() {
        return commandKey;
    }
    /**
     * Sets the command key.
     * @param pCommandKey the new command key
     */
    public final void setCommandKey(final String pCommandKey) {
        this.commandKey = pCommandKey;
    }
    /**
     * Gets the file type.
     * @return the file type
     */
    public final String getFileType() {
        return fileType;
    }
    /**
     * Sets the file type.
     * @param pFileType the new file type
     */
    public final void setFileType(final String pFileType) {
        this.fileType = pFileType;
    }
    /**
     * Gets the url.
     * @return the url
     */
    public final String getUrl() {
        return url;
    }
    /**
     * Sets the url.
     * @param pUrl the new url
     */
    public final void setUrl(final String pUrl) {
        this.url = pUrl;
    }
    /**
     * Gets the username.
     * @return the username
     */
    public final String getUsername() {
        return username;
    }
    /**
     * Sets the username.
     * @param pUsername the new username
     */
    public final void setUsername(final String pUsername) {
        this.username = pUsername;
    }
    /**
     * Gets the password.
     * @return the password
     */
    public final String getPassword() {
        return password;
    }
    /**
     * Sets the password.
     * @param pPassword the new password
     */
    public final void setPassword(final String pPassword) {
        this.password = pPassword;
    }
    
    /**
     * Gets the delay seconds.
     * @return the delay seconds
     */
    public final long getDelaySeconds() {
        return delaySeconds;
    }
    /**
     * Sets the delay seconds.
     * @param pDelaySeconds the new delay seconds
     */
    public final void setDelaySeconds(final long pDelaySeconds) {
        this.delaySeconds = pDelaySeconds;
    }
    
    /**
     * Perform.
     * @param session the session
     * @throws Fault the fault
     */
    public abstract void perform(final Session session) throws Fault;
    /**
     * Sets the creation date.
     * @param pCreationDate the new creation date
     */
    public final void setCreationDate(final long pCreationDate) {
        this.creationDate = pCreationDate;
    }
    /**
     * Gets the creation date.
     * @return the creation date
     */
    public final long getCreationDate() {
        return creationDate;
    }
    /**
     * id of the RPCMethod Request by ACS.
     */
    private String id= null;
    /**
     * Gets the id.
     * @return the Id.
     */
	public String getId() { 
		return id;
	}
	/**
	 * Setter the Id.
	 */
	public void setId(String id) {
		this.id=id;		
	}

}
