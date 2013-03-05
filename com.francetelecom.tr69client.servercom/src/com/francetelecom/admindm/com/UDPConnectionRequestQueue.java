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
import java.util.ArrayList;
import java.util.List;
import com.francetelecom.admindm.api.Log;
/**
 * <p>
 * This class is a queue a UDPConnectionRequest messages.
 * </p>
 * <p>
 * These messages are treated based on the incoming order (first in, first out).
 * </p>
 * @author mpcy8647
 */
public final class UDPConnectionRequestQueue {
    /** list of datagrampacket (UDPConnectionRequest). */
    private final List list;
    /**
     * <p>
     * Private constructor.
     * </p>
     * <p>
     * It initializes the list.
     * </p>
     */
    public UDPConnectionRequestQueue() {
        list = new ArrayList();
    }

    /**
     * <p>
     * Add a new received DatagramPacket (UDPConnectionRequest).
     * </p>
     * <p>
     * This method notifies also all the object waiting for it.
     * </p>
     * @param datagramPacket the datagram packet
     */
    public synchronized void add(final DatagramPacket datagramPacket) {
        Log.debug("add UDPConnectionRequest to the queue");
        this.list.add(datagramPacket);
        notifyAll();
    }
    /**
     * <p>
     * Remove the first packet in the list.
     * </p>
     * <p>
     * This methods blocks the execution while the list is empty.
     * </p>
     * @return the first received DatagramPacket in the list
     */
    public synchronized DatagramPacket remove() {
        waitWhileEmpty();
        return (DatagramPacket) this.list.remove(0);
    }
    /**
     * <p>
     * This method blocks the execution while the list of DatagramPacket is
     * empty.
     * </p>
     */
    private synchronized void waitWhileEmpty() {
        while (list.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Log.error("unable to wait");
            }
        }
    }
}
