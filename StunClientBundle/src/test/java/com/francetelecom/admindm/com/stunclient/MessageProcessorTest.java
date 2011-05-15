package com.francetelecom.admindm.com.stunclient;
import com.francetelecom.admindm.com.stunclient.impl.MessageProcessor;
import com.francetelecom.admindm.com.stunclient.impl.MessageQueue;
import com.francetelecom.admindm.com.stunclient.impl.Processor;
import net.java.stun4j.StunAddress;
import net.java.stun4j.StunException;
import net.java.stun4j.message.MessageFactory;
import net.java.stun4j.message.Response;
import net.java.stun4j.stack.TransactionID;
import junit.framework.TestCase;
/**
 * The Class MessageProcessorTest.
 */
public class MessageProcessorTest extends TestCase {
    /** The processor1. */
    private MyProcessor processor1;
    /** The processor2. */
    private MyProcessor processor2;
    /** The message processor. */
    private MessageProcessor messageProcessor;
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
     * Test add processor. Creates a response, adds a processor then adds the
     * response to the queue and eventually, checks if the processor has been
     * invoked and if the response is good.
     */
    public final void testAddProcessor() {
        messageProcessor.start();
        // create response
        Response response = createResponse();
        // add a processor
        messageProcessor.addProcessor(new String(response.getTransactionID()),
                processor1);
        // add the response to the queue
        messageQueue.addResponse(response);
        sleep1s();
        // check if the processor 1 was invoked
        assertTrue(processor1.isInvoked());
        assertTrue(processor1.getResponse().equals(response));
        messageProcessor.stop();
    }
    /**
     * Test add processor. Creates a response, adds 2 processors then adds the
     * response to the queue and eventually, checks if the processors have
     * been invoked and if the response is good.
     */
    public final void testAdd2Processors() {
        messageProcessor.start();
        // create response
        Response response = createResponse();
        // add 2 processors
        messageProcessor.addProcessor(new String(response.getTransactionID()),
                processor1);
        messageProcessor.addProcessor(new String(response.getTransactionID()),
                processor2);
        // add the response to the queue
        messageQueue.addResponse(response);
        sleep1s();
        // check if the processor 1 was invoked
        assertTrue(processor1.isInvoked());
        assertTrue(processor1.getResponse().equals(response));
        // check if the processor 2 was invoked
        assertTrue(processor2.isInvoked());
        assertTrue(processor2.getResponse().equals(response));
    }
    /**
     * Test add1 non invokable processor. Creates a response, then add a
     * Processor with a TransactionID different from the one of the Response
     * object and check if the processor has been invoked.
     */
    public final void testAdd1NonInvokableProcessor() {
        messageProcessor.start();
        // create response
        Response response = createResponse();
        // add a processor
        messageProcessor.addProcessor(new String("non invokable!!"),
                processor1);
        // add the response to the queue
        messageQueue.addResponse(response);
        sleep1s();
        // check if the processor 1 wasn't invoked
        assertTrue(!processor1.isInvoked());
        assertTrue(processor1.getResponse() == null);
    }
    /**
     * Test add1 processor that should not be invoked. Creates a response, add
     * it to the MessageQueue. Then adds a Processor to this particular
     * Response and check if the processor has been invoked.
     * 
     */
    public final void testAdd1ProcessorThatShouldNotBeInvoked() {
        messageProcessor.start();
        // create response
        Response response = createResponse();
        // add the response to the queue
        messageQueue.addResponse(response);
        sleep1s();
        // add a processor
        messageProcessor.addProcessor(new String("non invokable!!"),
                processor1);
        sleep1s();
        // check if the processor 1 wasn't invoked
        assertTrue(!processor1.isInvoked());
        assertTrue(processor1.getResponse() == null);
    }
    /**
     * Test remove processor. Creates a Response, adds a Processor then
     * removes it. Adds the Response to the queue then check if the processor
     * has been invoked.
     */
    public final void testRemoveProcessor() {
        messageProcessor.start();
        // create response
        Response response = createResponse();
        // add a processor
        messageProcessor.addProcessor(new String(response.getTransactionID()),
                processor1);
        // remove the processors for this response
        messageProcessor.removeProcessor(new String(response
                .getTransactionID()));
        // add the response to the queue
        messageQueue.addResponse(response);
        sleep1s();
        // check if the processor 1 was invoked
        assertTrue(!processor1.isInvoked());
        assertTrue(processor1.getResponse() == null);
    }
    /**
     * Test remove processors. Creates a Response, adds 2 Processors then
     * removes them. Adds the Response to the queue then check if the
     * processors have been invoked.
     */
    public final void testRemoveProcessors() {
        messageProcessor.start();
        // create response
        Response response = createResponse();
        // add processors
        messageProcessor.addProcessor(new String(response.getTransactionID()),
                processor1);
        messageProcessor.addProcessor(new String(response.getTransactionID()),
                processor2);
        // remove the processors for this response
        messageProcessor.removeProcessor(new String(response
                .getTransactionID()));
        // add the response to the queue
        messageQueue.addResponse(response);
        sleep1s();
        // check if the processor 1 was invoked
        assertTrue(!processor1.isInvoked());
        assertTrue(processor1.getResponse() == null);
        // check if the processor 2 was invoked
        assertTrue(!processor2.isInvoked());
        assertTrue(processor2.getResponse() == null);
    }
    /**
     * Test remove processors with wrong transaction id. Creates a response,
     * adds 2 processors then removes processors using a wrong transactionId
     * argument and check if the processors have been invoked.
     */
    public final void testRemoveProcessorsWithWrongTransactionId() {
        messageProcessor.start();
        // create response
        Response response = createResponse();
        // add processors
        messageProcessor.addProcessor(new String(response.getTransactionID()),
                processor1);
        messageProcessor.addProcessor(new String(response.getTransactionID()),
                processor2);
        // remove the processors for this response
        messageProcessor.removeProcessor("wrong");
        // add the response to the queue
        messageQueue.addResponse(response);
        sleep1s();
        // check if the processor 1 was invoked
        assertTrue(processor1.isInvoked());
        assertTrue(processor1.getResponse().equals(response));
        // check if the processor 2 was invoked
        assertTrue(processor2.isInvoked());
        assertTrue(processor2.getResponse().equals(response));
    }
    /**
     * Test stop. Creates a Response, add 2 processors then stops the
     * MessageProcessor. The processors aren't invoked.
     */
    public void testStop() {
        messageProcessor.start();
        // create response
        Response response = createResponse();
        // add processors
        messageProcessor.addProcessor(new String(response.getTransactionID()),
                processor1);
        messageProcessor.addProcessor(new String(response.getTransactionID()),
                processor2);
        // stop the processor
        messageProcessor.stop();
        // add the response to the queue
        messageQueue.addResponse(response);
        sleep1s();
        // check if the processor 1 was invoked
        assertTrue(!processor1.isInvoked());
        assertTrue(processor1.getResponse() == null);
        // check if the processor 2 was invoked
        assertTrue(!processor2.isInvoked());
        assertTrue(processor2.getResponse() == null);
    }
    /*
     * (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        messageQueue = new MessageQueue();
        messageProcessor = new MessageProcessor(messageQueue);
        processor1 = new MyProcessor();
        processor2 = new MyProcessor();
    }
    /*
     * (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        messageProcessor.stop();
    }
    /**
     * Creates the response.
     * 
     * @return the response
     */
    private static Response createResponse() {
        Response response = null;
        try {
            response =
                    MessageFactory.createBindingResponse(mappedAddress,
                            sourceAddress, changedAddress);
            response.setTransactionID(TransactionID.createTransactionID()
                    .getTransactionID());
        } catch (StunException e) {
            e.printStackTrace();
        }
        return response;
    }
    /**
     * Sleep1s.
     */
    private final static void sleep1s() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
     * The Class MyProcessor.
     */
    private class MyProcessor implements Processor {
        /** The invoked. */
        private boolean invoked = false;
        /** The response. */
        private Response response = null;
        /**
         * Process.
         * 
         * @param response the response
         * 
         * @see com.francetelecom.admindm.com.stunclient.impl.Processor#process(net.java.stun4j.message.Response)
         */
        public void process(Response response) {
            invoked = true;
            this.response = response;
        }
        /**
         * Checks if is invoked.
         * 
         * @return true, if is invoked
         */
        public boolean isInvoked() {
            return invoked;
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
