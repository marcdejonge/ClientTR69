/**
 * 
 */
package com.francetelecom.admindm.com.stunclient.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.java.stun4j.message.Response;
/**
 * <p>
 * Allows the registering of {@link Processor} for STUN response message.
 * </p>
 * <p>
 * When a new message arrives, this class is going to check if
 * {@link Processor}s have been registered for the received message. If so,
 * the {@link Processor#process(Response)} method is called for all the
 * registered {@link Processor}s.
 * </p>
 * @author mpcy8647
 * 
 */
public class MessageProcessor implements Runnable {
    /** is thread running ? */
    private boolean running = false;
    /** The thread. */
    private Thread thread = null;
    /** message queue to process */
    private final MessageQueue messageQueue;
    /** list of registered listeners<String(TransactionId), List of Processor> */
    private final Map processors;
    /**
     * 
     * @param msgQueue
     * @param stunClient
     */
    public MessageProcessor(MessageQueue msgQueue) {
        this.messageQueue = msgQueue;
        this.processors = new HashMap();
    }
    /**
     * <p>
     * Run.
     * </p>
     * <p>
     * The following actions are performed:
     * <ul>
     * <li>Removes from the {@link MessageQueue} an incoming message
     * (blocking call).</li>
     * <li>Based on the TransactionId of the STUN message, gets the list of
     * {@link Processor}s.</li>
     * <li>For each {@link Processor}, invoke the
     * {@link Processor#process(Response)} method.</li>
     * </ul>
     * 
     * @see java.lang.Runnable#run()
     */
    public void run() {
        while (running) {
            // the call to MessageQueue.removeResponse is a blocked while no
            // message to process is available
            Response response = messageQueue.removeResponse();
            // get the List of processor associated to the response
            if (response != null) {
                List processorList =
                        (List) processors.remove(new String(response
                                .getTransactionID()));
                if (processorList != null) {
                    for (Iterator it = processorList.iterator(); it.hasNext();) {
                        Processor processor = (Processor) it.next();
                        processor.process(response);
                    }
                }
            }
        }
    }
    /**
     * <p>
     * Stop the thread.
     * </p>
     * 
     */
    public void stop() {
        running = false;
        if (thread != null) {
            thread.interrupt();
        }
    }
    
    public void start() {
        running = true;
        thread = new Thread(this);
        thread.start();
    }
    
    /**
     * <p>
     * Add a new processor for the response identified by the transactionId
     * parameter.
     * </p>
     * 
     * @param transactionId transactionId of the response
     * @param processor processor to add to the list
     */
    public void addProcessor(String transactionId, Processor processor) {
        List processorList;
        if (processors.containsKey(transactionId)) {
            // a list already exists
            processorList = (List) processors.get(transactionId);
        } else {
            // create a new list
            processorList = new ArrayList();
            processors.put(transactionId, processorList);
        }
        // add the processor to the list
        processorList.add(processor);
    }
    /**
     * <p>
     * Remove the processors registered for a response identified by the
     * transactionId parameter.
     * </p>
     * 
     * @param transactionId transactionId of the response
     */
    public void removeProcessor(String transactionId) {
        processors.remove(transactionId);
    }
}
