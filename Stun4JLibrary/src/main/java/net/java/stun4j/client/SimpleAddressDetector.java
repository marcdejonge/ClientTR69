/*
 * Stun4j, the OpenSource Java Solution for NAT and Firewall Traversal.
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package net.java.stun4j.client;

import net.java.stun4j.*;
import net.java.stun4j.message.*;
import net.java.stun4j.stack.*;
import net.java.stun4j.attribute.Attribute;
import net.java.stun4j.attribute.MappedAddressAttribute;
import java.net.DatagramSocket;

/**
 * The class provides basic means of discovering a public IP address. All it does
 * is send a binding request through a specified port and return the mapped address
 * it got back or null if there was no reponse
 *
 * <p>Organisation: <p> Louis Pasteur University, Strasbourg, France</p>
 * <p>Network Research Team (http://www-r2.u-strasbg.fr)</p></p>
 * @author Emil Ivov
 * @version 0.1
 */

public class SimpleAddressDetector
{
    /**
     * Indicates whether the underlying stack has been initialized and started
     * and that the discoverer is operational.
     */
    private boolean started = false;

    /**
     * The stack to use for STUN communication.
     */
    private StunStack stunStack = null;

    /**
     * The provider to send our messages through
     */
    private StunProvider stunProvider = null;

    /**
     * The address of the stun server
     */
    private StunAddress serverAddress = null;

    /**
     * A utility used to flatten the multithreaded architecture of the Stack
     * and execute the discovery process in a synchronized manner
     */
    private BlockingRequestSender requestSender = null;

    /**
     * Creates a StunAddressDiscoverer. In order to use it one must start the
     * discoverer.
     * @param serverAddress the address of the server to interrogate.
     */
    public SimpleAddressDetector(StunAddress serverAddress)
    {
        this.serverAddress = serverAddress;
    }


    /**
     * Shuts down the underlying stack and prepares the object for garbage
     * collection.
     */
    public void shutDown()
    {
        stunStack.shutDown();
        stunStack = null;
        stunProvider = null;
        requestSender = null;

        this.started = false;

    }

    /**
     * Puts the discoverer into an operational state.
     * @throws StunException if we fail to bind or some other error occurs.
     */
    public void start() throws StunException
    {
        stunStack = StunStack.getInstance();
        stunStack.start();

        stunProvider = stunStack.getProvider();



        started = true;
    }


    /**
     * Creates a listening point from the following address and attempts to
     * discover how it is mapped so that using inside the application is possible.
     * @param address the [address]:[port] pair where ther request should be
     * sent from.
     * @return a StunAddress object containing the mapped address or null if
     * discovery failed.
     * @throws StunException if something fails along the way.
     */
    public StunAddress getMappingFor(StunAddress address)
        throws StunException
    {
        NetAccessPointDescriptor apDesc = new NetAccessPointDescriptor(address);

        stunStack.installNetAccessPoint(apDesc);

        requestSender = new BlockingRequestSender(stunProvider, apDesc);
        StunMessageEvent evt = null;
        try
        {
            evt = requestSender.sendRequestAndWaitForResponse(
                         MessageFactory.createBindingRequest(), serverAddress);
        }
        finally
        {
            //free the port to allow the application to use it.
            stunStack.removeNetAccessPoint(apDesc);
        }

        if(evt != null)
        {
            Response res = (Response)evt.getMessage();
            MappedAddressAttribute maAtt =
                        (MappedAddressAttribute)
                                res.getAttribute(Attribute.MAPPED_ADDRESS);
            if(maAtt != null)
                return maAtt.getAddress();
        }

        return null;
    }

    /**
    * Creates a listening point for the specified socket and attempts to
    * discover how its local address is NAT mapped.
    * @param socket the socket whose address needs to be resolved.
    * @return a StunAddress object containing the mapped address or null if
    * discovery failed.
    * @throws StunException if something fails along the way.
    */
   public StunAddress getMappingFor(DatagramSocket socket)
       throws StunException
   {
       NetAccessPointDescriptor apDesc =  stunStack.installNetAccessPoint(socket);

       requestSender = new BlockingRequestSender(stunProvider, apDesc);
       StunMessageEvent evt = null;
       try
       {
           evt = requestSender.sendRequestAndWaitForResponse(
                        MessageFactory.createBindingRequest(), serverAddress);
       }
       finally
       {
           stunStack.removeNetAccessPoint(apDesc);
       }

       if(evt != null)
       {
           Response res = (Response)evt.getMessage();
           MappedAddressAttribute maAtt =
                       (MappedAddressAttribute)
                               res.getAttribute(Attribute.MAPPED_ADDRESS);
           if(maAtt != null)
               return maAtt.getAddress();
       }

       return null;
   }


    /**
     * Creates a listening point from the specified port and attempts to
     * discover how it is being mapped.
     * @param port the local port where to send the request from.
     * @return a StunAddress object containing the mapped address or null if
     * discovery failed.
     * @throws StunException if something fails along the way.
     */
    public StunAddress getMappingFor(int port)
        throws StunException
    {
        return getMappingFor(new StunAddress(port));
    }


/*
    public static void main(String[] args)
        throws Exception
    {
        SimpleAddressDetector detector = new SimpleAddressDetector(
                                new StunAddress("stun01.sipphone.com", 3478));
        detector.start();
        StunAddress mappedAddr = detector.getMappingFor(5060);

        System.out.println("address is " + mappedAddr);

        detector.shutDown();
    }
*/

}
