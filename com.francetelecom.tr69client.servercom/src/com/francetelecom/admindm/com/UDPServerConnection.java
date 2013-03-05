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
import com.francetelecom.admindm.api.Log;
import com.francetelecom.admindm.stunclient.ISTUNCLient;

/**
 * <p>
 * This class represents a connection to the UDP server of the CPE.
 * </p>
 * <p>
 * The UDP server is intended to receive:
 * <ul>
 * <li>UDP Connection Request from the ACS.</li>
 * <li>STUN messages.</li>
 * </ul>
 * The difference is given by the first byte of the datagram packet. Indeed, for
 * the STUN messages, the first byte is either 0 or 1. For the UDP Connection
 * Request, the first byte is an ASCII encoded alphabetics letter.
 * </p>
 * @author mpcy8647
 */
public final class UDPServerConnection extends Thread {
    
    /** the datagram packet to analyze. */
    private final DatagramPacket datagramPacket;

    /**
     * <p>
     * Instantiates a new UDPServerConnection.
     * </p>
     * <p>
     * The datagram packet will be treated in the run method.
     * </p>
     * @param pDatagramPacket the datagram packet
     */
    public UDPServerConnection(final DatagramPacket pDatagramPacket) {
        this.datagramPacket = pDatagramPacket;
    }
    
    /**
     * Run.
     * @see java.lang.Thread#run()
     */
    public void run() {
        super.run();
        byte[] data = datagramPacket.getData();
        if ((data != null) && (data.length > 0)) {
            if ((data[0] == 0) || (data[0] == 1)) {
                // this is a STUN message
                Log.debug("STUN message received");
                ISTUNCLient stunClient = UDPServer.getInstance()
                        .getSTUNClient();
                if (stunClient != null) {
                    stunClient.addMessage(data);
                }
            } else {
                String dataString = new String(data);
                if ((dataString != null)
                        && (Character.isLetter(dataString.charAt(0)))) {
                    Log.debug("UDP Connection Request from the server");
                    UDPServer.getInstance().getUDPConnectionRequestQueue()
                            .add(datagramPacket);
                } else {
                    Log.error("wrong udp connection");
                }
            }
        }
    }
}
