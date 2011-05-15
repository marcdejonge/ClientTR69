package com.francetelecom.admindm.com.stunclient;
import java.lang.Thread.State;
import com.francetelecom.admindm.com.stunclient.impl.MessageQueue;
import junit.framework.TestCase;
import net.java.stun4j.StunAddress;
import net.java.stun4j.StunException;
import net.java.stun4j.message.MessageFactory;
import net.java.stun4j.message.Response;
/**
 * The Class MessageQueueTest.
 */
public class MessageQueueTest extends TestCase {
    /** The message queue. */
    private MessageQueue messageQueue;
    /** The mapped address. */
    private static StunAddress mappedAddress;
    /** The source address. */
    private static StunAddress sourceAddress;
    /** The changed address. */
    private static StunAddress changedAddress;
    static {
        mappedAddress = new StunAddress("localhost", 5555);
        sourceAddress = new StunAddress("localhost", 5556);
        changedAddress = new StunAddress("localhost", 5557);
    }
    /**
     * Instantiates a new message queue test.
     * 
     * @param arg0 the arg0
     */
    public MessageQueueTest(String arg0) {
        super(arg0);
    }
    /*
     * (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        messageQueue = new MessageQueue();
    }
    /*
     * (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    /**
     * Test message queue.
     */
    public final void testMessageQueue() {
    }
    /**
     * Test add response. One Response is added, and removed from the Queue.
     */
    public final void testAddOneResponse() {
        Response response = null;
        try {
            response =
                    MessageFactory.createBindingResponse(mappedAddress,
                            sourceAddress, changedAddress);
        } catch (StunException e) {
            fail("unable to create a response");
        }
        messageQueue.addResponse(response);
        // check the response
        Response responseFromTheQueue = messageQueue.removeResponse();
        if (!responseFromTheQueue.equals(response)) {
            fail("the response isn't the same!!!");
        }
    }
    /**
     * Test add two responses.
     */
    public final void testAddTwoResponses() {
        // add first response
        Response response1 = null;
        try {
            response1 =
                    MessageFactory.createBindingResponse(mappedAddress,
                            sourceAddress, changedAddress);
        } catch (StunException e) {
            fail("unable to create a response");
        }
        messageQueue.addResponse(response1);
        // add second response
        Response response2 = null;
        try {
            response2 =
                    MessageFactory.createBindingResponse(mappedAddress,
                            sourceAddress, changedAddress);
        } catch (StunException e) {
            fail("unable to create a response");
        }
        messageQueue.addResponse(response2);
        // remove first response
        if (!messageQueue.removeResponse().equals(response1)) {
            fail("the response isn't the good response");
        }
        // remove second response
        if (!messageQueue.removeResponse().equals(response2)) {
            fail("the response isn't the good response");
        }
    }
    /**
     * Test remove response. No response added.
     */
    public final void testRemoveResponse() {
        Thread t = new Thread(new RemoveThread(messageQueue));
        t.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!(t.getState().equals(State.WAITING))) {
            fail("no received message, should be waiting wheareas it is in "
                 + t.getState());
        }
    }
    
    
    /**
     * Test remove1 response with thread interrupt.
     * Try to remove a response from an empty queue and 
     * send an interrupt.
     */
    public final void testRemove1ResponseWithThreadInterrupt() {

        Thread t = new Thread(new RemoveThread(messageQueue));
        t.start();
        t.interrupt();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        
        if (!(t.getState().equals(State.TERMINATED))) {
            fail("no received message, should be terminated wheareas it is in "
                 + t.getState());
        }
    }
    
    /**
     * Test clear queue.
     * Add a response then clear the queue then try to get the response.
     */
    public final void testClearQueue() {
        // add first response
        Response response1 = null;
        try {
            response1 =
                    MessageFactory.createBindingResponse(mappedAddress,
                            sourceAddress, changedAddress);
        } catch (StunException e) {
            fail("unable to create a response");
        }
        messageQueue.addResponse(response1);
        
        // clear the queue
        messageQueue.clearQueue();
        
        // try to get the added response
        RemoveThread removeThread = new RemoveThread(messageQueue);
        Thread t = new Thread(removeThread);
        t.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        if (!(t.getState().equals(State.TERMINATED))) {
            
            fail("no received message, should be terminated wheareas it is in "
                 + t.getState());
        }
        
        assertTrue(removeThread.getResponse() == null);
        
    }
    
    /**
     * Test clear queue.
     * Try to remove a Response then clear the queue
     */
    public final void testClearQueueWithRemoveMessageBefore() {
      
        // try to get a response
        RemoveThread removeThread = new RemoveThread(messageQueue);
        Thread t = new Thread(removeThread);
        t.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // clear the queue
        messageQueue.clearQueue();
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        if (!(t.getState().equals(State.TERMINATED))) {
            
            fail("no received message, should be terminated wheareas it is in "
                 + t.getState());
        }
        
        assertTrue(removeThread.getResponse() == null);
        
        
        
    }
    
    
    /**
     * The Class RemoveThread.
     */
    private class RemoveThread implements Runnable {
        /** The message queue. */
        private final MessageQueue messageQueue;
        
        /** The response. */
        private Response response;
        /**
         * Instantiates a new removes the thread.
         * 
         * @param messageQueue the message queue
         */
        public RemoveThread(MessageQueue messageQueue) {
            this.messageQueue = messageQueue;
        }
        
        /**
         * Run.
         * 
         * @see java.lang.Runnable#run()
         */
        public void run() {
            response = messageQueue.removeResponse();
        }
        
        /**
         * Gets the response.
         * 
         * @return the response
         */
        public Response getResponse() {
            return response;
        }
    }
    
}
