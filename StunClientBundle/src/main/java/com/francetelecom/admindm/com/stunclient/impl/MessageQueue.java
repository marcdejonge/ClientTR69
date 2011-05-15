/**
 * 
 */
package com.francetelecom.admindm.com.stunclient.impl;
import java.util.ArrayList;
import java.util.List;
import net.java.stun4j.message.Response;
import com.francetelecom.admindm.api.Log;
/**
 * <p>
 * This class represents a Queue of Stun message which are going to be
 * treated.
 * </p>
 * 
 * @author mpcy8647
 * 
 */
public class MessageQueue {
    /** list of stun message */
    private List receivedMessageList;
    /**
     * Instantiates a new message queue.
     */
    public MessageQueue() {
        receivedMessageList = new ArrayList();
    }
    /**
     * <p>
     * Add a new Response Stun messages.
     * </p>
     * <p>
     * The adding is synchronized on the messageList.
     * </p>
     * <p>
     * Moreover, when a response is added, we check if the response wasn't be
     * expected. If so, the STUNMessageSender object corresponding to the
     * request of this response is deleted.
     * </p>
     * 
     * @param response
     */
    public synchronized void addResponse(Response response) {
        // add the message to the received message
        receivedMessageList.add(response);
        Log.debug("receive STUN response ");
        // notify all the waiting thread
        notifyAll();
    }
    /**
     * <p>
     * Remove the first Response of the list.
     * </p>
     * <p>
     * A call to this method is blocked while the message queue is empty.
     * </p>
     * 
     * @return
     */
    public synchronized Response removeResponse() {
        try {
            waitWhileEmpty();
        } catch (InterruptedException e) {
            return null;
        }
        if (receivedMessageList != null) {
            return (Response) receivedMessageList.remove(0);
        } else {
            return null;
        }
    }
    /**
     * <p>
     * Clean the queue of STUN messages.
     * </p>
     * <p>
     * This operation includes the cleaning of the list of incoming STUN
     * messages as well as the map of expected messages.
     * </p>
     */
    public void clearQueue() {
        // clear the list
        receivedMessageList.clear();
        receivedMessageList = null;
        
        // notify all 
        synchronized (this) {
            notifyAll();
        }
    }
    /**
     * <p>
     * Wait until a new message arrived.
     * </p>
     * @throws InterruptedException
     * 
     */
    private synchronized void waitWhileEmpty() throws InterruptedException {
        if (receivedMessageList != null) {
            while (true) {
                if ((receivedMessageList != null) && (receivedMessageList.isEmpty())) {
                    wait();
                } else {
                    return; 
                }
            }
        }
    }
}
