/*
 * Stun4j, the OpenSource Java Solution for NAT and Firewall Traversal.
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package net.java.stun4j.stack;

import net.java.stun4j.StunException;
import net.java.stun4j.NetAccessPointDescriptor;
import net.java.stun4j.message.Message;

import java.util.Hashtable;
import java.io.*;
import java.util.Vector;
import java.util.*;
import net.java.stun4j.*;
import java.net.DatagramSocket;
import java.util.logging.*;

/**
 * Manages NetAccessPoints and MessageProcessor pooling. This class serves as a
 * layer that masks network primitives and provides equivalent STUN abstractions.
 * Instances that operate with the NetAccessManager are only supposed to
 * understand STUN talk and shouldn't be aware of datagrams sockets, and etc.
 *
 * <p>Organisation: Louis Pasteur University, Strasbourg, France</p>
 *                   <p>Network Research Team (http://www-r2.u-strasbg.fr)</p></p>
 * @author Emil Ivov
 * @version 0.1
 */

class NetAccessManager
    implements ErrorHandler
{
    private static final Logger logger =
        Logger.getLogger(NetAccessManager.class.getName());
    /**
     * All access points currently in use. The table maps NetAccessPointDescriptor-s
     * to NetAccessPoint-s
     */
    private Hashtable    netAccessPoints   = new Hashtable();

    /**
     * A synchronized FIFO where incoming messages are stocked for processing.
     */
    private MessageQueue messageQueue      = new MessageQueue();

    /**
     * A thread pool of message processors.
     */
    private Vector       messageProcessors = new Vector();

    /**
     * The instance that should be notified whan an incoming message has been
     * processed and ready for delivery
     */
    private MessageEventHandler messageEventHandler = null;

    /**
     * Indicates whether the access manager has been started.
     */
    private boolean isRunning = false;

    /**
     * The size of the thread pool to start with.
     */
    private int initialThreadPoolSize = StunStack.DEFAULT_THREAD_POOL_SIZE;

    /**
     * Constructs a NetAccessManager.
     */
    NetAccessManager()
    {
    }



    /**
     * Initializes the message processors pool and sets the status of the manager
     * to running.
     */
    synchronized void start()
    {
        if(isRunning)
            return;

        this.isRunning = true;
        this.initThreadPool();
    }

    /**
     * Stops and deletes all message processors and access points.
     */
    void shutDown()
    {
        if (!isRunning)
            return;

        //stop access points
        logger.info("removing " + netAccessPoints.size() + " access points.");
        Enumeration keys = netAccessPoints.keys();
        while (keys.hasMoreElements())
        {
            NetAccessPointDescriptor apd = (NetAccessPointDescriptor) keys.
                nextElement();
            removeNetAccessPoint(apd);
            logger.info(".");
        }
        logger.info("removed all access points");

        //stop and empty thread pool
        while (!messageProcessors.isEmpty())
        {
            MessageProcessor mp = (MessageProcessor) messageProcessors.remove(0);
            mp.stop();
        }

        //removed a call to messageQueue.notifyAll(). it was useless since it
        //was only releasing a single message processor which then immediately
        //got stuck in a wait since the message queue was empty.(Bug Report
        //originally received from Pascal Maugeri who reported zombie threads
        //still running after a stack shutdown).

        isRunning = false;
    }

    /**
     * Determines whether the NetAccessManager has been started.
     * @return true if this NetAccessManager has been started, and false
     * otherwise.
     */
    boolean isRunning()
    {
        return isRunning;
    }


    /**
     * Sets the instance to notify for incoming message events.
     * @param evtHandler the entity that will handle incoming messages.
     */
    void setEventHandler(MessageEventHandler evtHandler)
    {
        messageEventHandler = evtHandler;
    }

//------------------------ error handling -------------------------------------
    /**
     * A civilized way of not caring!
     * @param message a description of the error
     * @param error   the error that has occurred
     */
    public void handleError(String message, Throwable error)
    {
        /**
         * apart from logging, i am not sure what else we could do here.
         */
        logger.log( Level.WARNING, "The following error occurred", error);
    }

    /**
     * Clears the faulty thread and tries to repair the damage and instanciate
     * a replacement.
     *
     * @param callingThread the thread where the error occurred.
     * @param message       A description of the error
     * @param error         The error itself
     */
    public void handleFatalError(Runnable callingThread, String message, Throwable error)
    {
        if (callingThread instanceof NetAccessPoint)
        {
            NetAccessPoint ap = (NetAccessPoint)callingThread;

            //make sure socket is closed
            removeNetAccessPoint(ap.getDescriptor());

            try
            {
                logger.log( Level.WARNING, "An access point has unexpectedly "
                    +"stopped. AP:" + ap.toString(), error);
                installNetAccessPoint(ap.getDescriptor());
            }
            catch (StunException ex)
            {
                //make sure nothing's left and notify user
                removeNetAccessPoint(ap.getDescriptor());
                logger.log(Level.WARNING, "Failed to relaunch accesspoint:" + ap,
                           ex);
            }
        }
        else if( callingThread instanceof MessageProcessor )
        {
            MessageProcessor mp = (MessageProcessor)callingThread;
            logger.log( Level.WARNING, "A message processor has unexpectedly "
                    +"stopped. AP:" + mp.toString(), error);

            //make sure the guy's dead.
            mp.stop();
            messageProcessors.remove(mp);

            mp = new MessageProcessor(messageQueue, messageEventHandler, this);
            mp.start();
            logger.fine("A message processor has been relaunched because "
                        +"of an error.");
        }
    }

    /**
     * Creates and starts a new access point according to the given descriptor.
     * If the specified access point has already been installed the method
     * has no effect.
     *
     * @param apDescriptor   a description of the access point to create.
     * @throws StunException if we fail to create or start the accesspoint.
     */
    void installNetAccessPoint(NetAccessPointDescriptor apDescriptor)
        throws StunException
    {
        if(netAccessPoints.containsKey(apDescriptor))
            return;

        NetAccessPoint ap = new NetAccessPoint(apDescriptor, messageQueue, this);
        netAccessPoints.put(apDescriptor, ap);

        try
        {
            ap.start();
        }
        catch (IOException ex)
        {
            logger.log(Level.WARNING, "The NAPD("+ap+") failed to bind ", ex);
            throw new StunException(
                      StunException.NETWORK_ERROR,
                      "An IOException occurred while starting access point: "
                      +apDescriptor.toString() ,
                      ex);
        }
    }

    /**
     * Creates and starts a new access point based on the specified socket.
     * If the specified access point has already been installed the method
     * has no effect.
     *
     * @param  socket   the socket that the access point should use.
     * @return an access point descriptor to allow further management of the
     * newly created access point.
     * @throws StunException if we fail to create or start the accesspoint.
     */
    NetAccessPointDescriptor installNetAccessPoint(DatagramSocket socket)
        throws StunException
    {

        //no null check - let it through a null pointer exception
        StunAddress address = new StunAddress(socket.getLocalAddress(), socket.getLocalPort());
        NetAccessPointDescriptor apDescriptor = new NetAccessPointDescriptor(address);

        if(netAccessPoints.containsKey(apDescriptor))
            return apDescriptor;

        NetAccessPoint ap = new NetAccessPoint(apDescriptor, messageQueue, this);
        //call the useExternalSocket method to avoid closing the socket when
        //removing the accesspoint. Bug Report - Dave Stuart - SipQuest
        ap.useExternalSocket(socket);
        netAccessPoints.put(apDescriptor, ap);

        try
        {
            ap.start();
        }
        catch (IOException ex)
        {
            throw new StunException(
                      StunException.NETWORK_ERROR,
                      "An IOException occurred while starting the access point",
                      ex);
        }

        return apDescriptor;
    }


    /**
     * Stops and deletes the specified access point.
     * @param apDescriptor the access  point to remove
     */
    void removeNetAccessPoint(NetAccessPointDescriptor apDescriptor)
    {
        NetAccessPoint ap = (NetAccessPoint)netAccessPoints.remove(apDescriptor);

        if(ap != null)
            ap.stop();
    }

    //---------------thread pool implementation --------------------------------
    /**
     * Adjusts the number of concurrently running MessageProcessors.
     * If the number is smaller or bigger than the number of threads
     * currentlyrunning, then message processors are created/deleted so that their
     * count matches the new value.
     *
     * @param threadPoolSize the number of MessageProcessors that should be running concurrently
     * @throws StunException INVALID_ARGUMENT if threadPoolSize is not a valid size.
     */
    void setThreadPoolSize(int threadPoolSize)
        throws StunException
    {
        if(threadPoolSize < 1)
            throw new StunException(StunException.ILLEGAL_ARGUMENT,
                                    threadPoolSize + " is not a legal thread pool size value.");

        //if we are not running just record the size so that we could init later.
        if(!isRunning)
        {
            initialThreadPoolSize = threadPoolSize;
            return;
        }

        if(messageProcessors.size() < threadPoolSize)
        {
            //create additional processors
            fillUpThreadPool(threadPoolSize);
        }
        else
        {
            //delete extra processors
            shrinkThreadPool(threadPoolSize);
        }
    }

    /**
     * @throws StunException INVALID_ARGUMENT if threadPoolSize is not a valid size.
     */
    private void initThreadPool()
    {
            //create additional processors
            fillUpThreadPool(initialThreadPoolSize);
    }


    /**
     * Starts all message processors
     *
     * @param newSize the new thread poolsize
     */
    private void fillUpThreadPool(int newSize)
    {
        //make sure we don't resize more than once
        messageProcessors.ensureCapacity(newSize);

        for (int i = messageProcessors.size(); i < newSize; i++)
        {
            MessageProcessor mp = new MessageProcessor(messageQueue,
                                                       messageEventHandler,
                                                       this);
            messageProcessors.add(mp);

            mp.start();
        }

    }

    /**
     * Starts all message processors
     *
     * @param newSize the new thread poolsize
     */
    private void shrinkThreadPool(int newSize)
    {
        while(messageProcessors.size() > newSize)
        {
            MessageProcessor mp = (MessageProcessor)messageProcessors.remove(0);
            mp.stop();
        }
    }

    //--------------- SENDING MESSAGES -----------------------------------------
    /**
     * Sends the specified stun message through the specified access point.
     * @param stunMessage the message to send
     * @param apDescriptor the access point to use to send the message
     * @param address the destination of the message.
     * @throws StunException if message encoding fails, ILLEGAL_ARGUMENT if the
     * apDescriptor references an access point that had not been installed,
     * NETWORK_ERROR if an error occurs while sending message bytes through the
     * network socket.
     */
    void sendMessage(Message                  stunMessage,
                     NetAccessPointDescriptor apDescriptor,
                     StunAddress                  address)
        throws StunException
    {
        byte[] bytes = stunMessage.encode();
        NetAccessPoint ap = (NetAccessPoint)netAccessPoints.get(apDescriptor);

        if(ap == null)
            throw new StunException(
                          StunException.ILLEGAL_ARGUMENT,
                          "The specified access point had not been installed.");

        try
        {
            ap.sendMessage(bytes, address);
        }
        catch (Exception ex)
        {
            throw new StunException(StunException.NETWORK_ERROR,
                        "An Exception occurred while sending message bytes "
                        +"through a network socket!",
                        ex);
        }
    }

}
