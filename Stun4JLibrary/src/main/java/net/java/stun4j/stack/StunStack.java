/*
 * Stun4j, the OpenSource Java Solution for NAT and Firewall Traversal.
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package net.java.stun4j.stack;

import net.java.stun4j.message.Message;
import net.java.stun4j.StunException;
import net.java.stun4j.StunMessageEvent;
import net.java.stun4j.NetAccessPointDescriptor;
import java.net.DatagramSocket;

/**
 * The entry point to the Stun4J stack. The class is used to start, stop and
 * configure the stack.
 *
 * <p>Organisation: Louis Pasteur University, Strasbourg, France</p>
 *               <p>Network Research Team (http://www-r2.u-strasbg.fr)</p></p>
 * @author Emil Ivov
 * @version 0.1
 */
public class StunStack
{
    /**
     * We shouldn't need more than one stack in the same application.
     */
    private static StunStack stackInstance = null;

    /**
     * Our network gateway.
     */
    private NetAccessManager netAccessManager = new NetAccessManager();

    /**
     * The number of threads to split our flow in.
     */
    public static final int DEFAULT_THREAD_POOL_SIZE = 5;

    private StunProvider stunProvider = null;

    /**
     * Returns a reference to the singleton StunStack insance. If the stack
     * had not yet been initialised, a new instance will be created.
     *
     * @return a reference to the StunStack.
     */
    public static synchronized StunStack getInstance()
    {
        if (stackInstance == null)
            stackInstance = new StunStack();
        return stackInstance;
    }

    //----------------------- PUBLIC INTERFACE ---------------------------------
    /**
     * Puts the stack into an operational state.
     */
    public void start()
    {
        netAccessManager.start();
    }

    /**
     * Stops the stack. Tests have shown that on some OSes, it sometimes takes
     * a while for sockets to free their port and trying to bind to that port
     * immediately after gets us an exception. We therefore null the socket,
     * wait for 200 miliseconds and call the garbage collector this seems to be
     * working fine but is not what one could call neat behaviour, so if you'd
     * like to disable it - set the net.java.stun4j.stack.HARD_SOCK_CLOSE
     * property to anything different from true. If you'd want us to wait more
     * or less - set net.java.stun4j.stack.WAIT_FOR_SOCK_CLOSE to the
     * appropriate number of miliseconds.
     */
    public static void shutDown()
    {
        if (stackInstance == null)
            return;
        stackInstance.stunProvider.shutDown();
        stackInstance.netAccessManager.shutDown();

        stackInstance.netAccessManager = null;
        stackInstance.stunProvider = null;
        stackInstance = null;
    }

    public void setThreadPoolSize(int threadPoolSize)
        throws StunException
    {
        netAccessManager.setThreadPoolSize(threadPoolSize);
    }

    /**
     * Creates and starts the specified Network Access Point.
     *
     * @param apDescriptor A descriptor containing the address and port of the
     * STUN server that the newly created access point will communicate with.
     * @throws StunException
     *           <p>NETWORK_ERROR if we fail to create or bind the datagram socket.</p>
     *           <p>ILLEGAL_STATE if the stack had not been started.</p>
     */
    public void installNetAccessPoint(NetAccessPointDescriptor apDescriptor)
        throws StunException
    {
        checkStarted();

        netAccessManager.installNetAccessPoint(apDescriptor);
    }

    /**
     * Creates and starts the specified Network Access Point based on the specified
     * socket and returns a relevant descriptor.
     *
     * @param sock The socket that the new access point should represent.
     * @throws StunException
     *           <p>NETWORK_ERROR if we fail to create or bind the datagram socket.</p>
     *           <p>ILLEGAL_STATE if the stack had not been started.</p>
     * @return a descriptor of the newly created access point.
     */
   public NetAccessPointDescriptor installNetAccessPoint(DatagramSocket sock)
       throws StunException
   {
       checkStarted();

       return netAccessManager.installNetAccessPoint(sock);
   }


    /**
     * Stops and deletes the specified access point.
     * @param apDescriptor the access  point to remove
     */
    public void removeNetAccessPoint(NetAccessPointDescriptor apDescriptor)
        throws StunException
    {
        checkStarted();

        netAccessManager.removeNetAccessPoint(apDescriptor);
    }



    /**
     * Returns a StunProvider instance to be used for sending and receiving
     * mesages.
     *
     * @return an instance of StunProvider
     */
    public StunProvider getProvider()
    {
        return stunProvider;
    }

    //-------------------- internal stuff --------------------------------------
    /**
     * Private constructor as we want a singleton pattern.
     */
    private StunStack()
    {
        stunProvider = new StunProvider(this);

        netAccessManager.setEventHandler(getProvider());
    }

    /**
     * Throws a StunException.ILLEGAL_STATE if the stack had not been started.
     * @throws StunException ILLEGAL_STATE if the stack had not been started.
     */
    void checkStarted()
        throws StunException
    {
        if(!netAccessManager.isRunning())
            throw new StunException(
                        StunException.ILLEGAL_STATE,
                        "The stack needs to be started, for "
                        +"the requested method to work.");
    }

    /**
     * Returns the currently active instance of NetAccessManager.
     * @return the currently active instance of NetAccessManager.
     */
    NetAccessManager getNetAccessManager()
    {
        return netAccessManager;
    }
}
