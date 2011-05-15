/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : 1.1
 * Module Name : ServerComBundle
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
package com.francetelecom.admindm.com;
import java.net.DatagramPacket;
import com.francetelecom.admindm.model.IParameterData;
/**
 * @author mpcy8647
 */
public final class UDPConnectionRequestProcessor implements Runnable {
    /** data model.*/
    private IParameterData parameterData = null;
    /** is running ? */
    private boolean isRunning = false;
    /**
     * Initiates a UDP Connection Request.
     */
    public UDPConnectionRequestProcessor() {
    }
    /**
     * <p>
     * Set the parameter data
     * </p>
     * .
     * @param pParameterData the parameter data
     */
    public void setParameterData(final IParameterData pParameterData) {
        this.parameterData = pParameterData;
    }
    
    /**
     * Start the UDP Connection Request processing.
     * @throws Exception the exception
     */
    public void start() throws Exception {
        if (this.parameterData == null) {
            throw new Exception("set the parameterData attribute");
        }
        isRunning = true;
        new Thread(this).start();
    }
    /**
     * Stop the UDP Connection Request processing.
     */
    public void stop() {
        isRunning = false;
    }
    /**
     * <p>
     * Thread routine.
     * </p>
     * <p>
     * The following actions are performed:
     * <ul>
     * <li>get a UDPConnectionRequest Datagram packet from the queue.</li>
     * <li>treats the UDPConnectionRequest.</li>
     * </ul>
     * </p>
     */
    public void run() {
        UDPConnectionRequest udpConnectionRequest;
        DatagramPacket packet;
        UDPServer server = UDPServer.getInstance();
        while (isRunning) {
            // get the datagramPacket of a UDPConnectionRequest from the queue
            packet = server.getUDPConnectionRequestQueue().remove();
            // treat the UDPConnectionRequest
            udpConnectionRequest= new UDPConnectionRequest(parameterData,
                    packet);
            udpConnectionRequest.treatUDPConnectionRequest();
        }
    }
}
