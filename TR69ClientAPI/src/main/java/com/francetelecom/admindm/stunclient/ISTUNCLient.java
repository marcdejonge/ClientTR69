/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : 1.1
 * Module Name : TR69ClientAPI
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
 */ 
package com.francetelecom.admindm.stunclient;

import java.net.DatagramSocket;

/**
 * @author mpcy8647
 * 
 */
public interface ISTUNCLient {

	/**
	 * Start the STUN client.
	 * 
	 * @param pSocket
	 *            socket to be used by the STUN client
	 */
	public void start(final DatagramSocket pSocket);

	/**
	 * <p>
	 * Stop gracefully the STUN client.
	 * </p>
	 * <p>
	 * The start method must be called in the past.
	 * </p>
	 */
	public void stop();

	/**
	 * <p>
	 * Add a new Stun message received from the datagram socket.
	 * </p>
	 * 
	 * @param message
	 *            message
	 */
	public void addMessage(final byte[] message);

	// /**
	// * <p>
	// * Gets the binding maintainer.
	// * </p>
	// *
	// * @return the binding maintainer
	// */
	// public BindingMaintainer getBindingMaintainer();
	//
	// /**
	// * <p>
	// * Add a new Stun message received from the datagram socket.
	// * </p>
	// *
	// * @param message
	// * message
	// */
	// public void addMessage(final byte[] message);
	//
	// /**
	// * Gets the public address.
	// *
	// * @return the public address
	// */
	// public String getPublicAddress();
	//
	// /**
	// * Gets the message processor.
	// *
	// * @return the message processor
	// */
	// public MessageProcessor getMessageProcessor();
	//
	// /**
	// * Gets the socket used by the STUN client.
	// *
	// * @return socket
	// */
	// public DatagramSocket getSocket();
	//
	// /**
	// * Get the port of the datagram socket.
	// *
	// * @return port
	// */
	// public int getPort();
	//
	// /**
	// * Get the address of the datagram socket.
	// *
	// * @return address (ip form)
	// */
	// public String getAddress();
	//
	// /**
	// * Set the last mapped host and port.
	// *
	// * @param host
	// * host part of the last received stun mapped address attribute
	// * @param port
	// * port part of the last received stun mapped address attribute
	// */
	// public void setLastReceivedMappedAddress(final String host, final int
	// port);

}
