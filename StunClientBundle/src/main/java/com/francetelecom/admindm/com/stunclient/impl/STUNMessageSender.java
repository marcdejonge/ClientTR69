/**
 * 
 */
package com.francetelecom.admindm.com.stunclient.impl;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import net.java.stun4j.message.Response;
import com.francetelecom.admindm.api.Log;
/**
 * <p>
 * This class sends STUN Request Binding messages with reliability.
 * </p>
 * <p>
 * The STUN message will be transmit immediately from the beginning of the
 * execution thread (t0) and will be retransmit t0 + 100ms , t0 + 300 ms, t0 +
 * 700ms, t0 + 1500 ms, t0 + 3100 ms, t0 + 4700 ms, t0 + 6300 ms and t0 + 7900
 * ms if the response isn't received.
 * </p>
 * <p>
 * After these message retransmissions, the message is discarded.
 * </p>
 * 
 * @author mpcy8647
 * 
 */
public class STUNMessageSender implements Runnable, Processor {
    /** time to wait before the next sending of the packet */
    private int durationTime = 0;
    /** datagram packet to send */
    private final DatagramPacket packet;
    /** datagram socket */
    private final DatagramSocket socket;
    /** true if the response has been received */
    private boolean isReceived = false;
    /** current execution thread */
    private Thread currentThread = null;
    /**
     * <p>
     * Initialize packet and socket attribute.
     * </p>
     * 
     * @param packet packet to send
     * @param socket socket to use
     */
    public STUNMessageSender(DatagramPacket packet, DatagramSocket socket) {
        this.packet = packet;
        this.socket = socket;
    }
    /**
     * <p>
     * Start the sending message thread.
     * </p>
     */
    public void start() {
        currentThread = new Thread(this);
        currentThread.start();
    }
    /**
     * <p>
     * Thread runtime
     * </p>
     * <p>
     * While the response hasn't been received, the packet is retransmitted.
     * The duration between two packet sending is multiplied by 2.
     * </p>
     */
    public void run() {
        int count = 0;
        while (!isReceived) {
            try {
                Thread.sleep(durationTime);
            } catch (InterruptedException e) {
                Log.error("Sleep interrupted in STUNMessageSender");
                if (isReceived) {
                    return;
                }
            }
            try {
                socket.send(packet);
            } catch (IOException e1) {
                Log.error("unable to send STUN BindingRequest message");
            }
            if (durationTime == 0) {
                durationTime = 100;
            } else if (durationTime < 1600) {
                durationTime = durationTime * 2;
            } else if (count <= 9) {
                count++;
            } else {
                // all the attempt are failed
                // the sending has failed
                // consider the server has received
                Log.error("the expected Response hasn't been received.");
                isReceived = true;
            }
        }
    }
    /**
     * <p>
     * Stop the packet resending.
     * </p>
     * <p>
     * The BindingResponse has been received.
     * </p>
     */
    public void process(Response response) {
        Log.debug("process with STUNMessageSender - stop retransmission !");
        isReceived = true;
        currentThread.interrupt();
    }
}
