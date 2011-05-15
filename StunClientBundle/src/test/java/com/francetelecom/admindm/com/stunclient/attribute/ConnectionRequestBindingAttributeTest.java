/**
 * 
 */
package com.francetelecom.admindm.com.stunclient.attribute;
import net.java.stun4j.StunException;
import junit.framework.TestCase;
/**
 * @author mpcy8647
 * 
 */
public class ConnectionRequestBindingAttributeTest extends TestCase {
    /** The ENCODE d_ connectio n_ reques t_ bindin g_ attribute. */
    private static byte[] ENCODED_CONNECTION_REQUEST_BINDING_ATTRIBUTE =
            new byte[] { /* type */(byte) 11000000, (byte) 00000001, /* length */
            (byte) 00000000, (byte) 0x14, /* data */(byte) 0x64,
                    (byte) 0x73, (byte) 0x6c, (byte) 0x66, (byte) 0x6f,
                    (byte) 0x72, (byte) 0x75, (byte) 0x6d, (byte) 0x2e, (byte) 0x6f,
                    (byte) 0x72, (byte) 0x67, (byte) 0x2f, (byte) 0x54,
                    (byte) 0x52, (byte) 0x2d, (byte) 0x31, (byte) 0x31,
                    (byte) 0x31, (byte) 0x20 };
    /**
     * Instantiates a new connection request binding attribute test.
     */
    public ConnectionRequestBindingAttributeTest() {
    }
    /**
     * Test create attribute.
     */
    public final void testCreateAttribute() {
        ConnectionRequestBindingAttribute attribute =
                ConnectionRequestBindingAttribute
                        .createConnectionRequestBindingAttribute('a');
        // test the getName method
        assertTrue(attribute.getName().equals("CONNECTION-REQUEST-BINDING"));
        // test the getDataLength method
        assertTrue(attribute.getDataLength() == 20);
        // test the decode method
        boolean exception = false;
        try {
            attribute.decodeAttributeBody(new byte[] {}, 'a', 'b');
            fail();
        } catch (StunException e) {
            exception = true;
        }
        assertTrue(exception);
        //test equal
        assertFalse(attribute.equals(attribute));
        
        // test encode
        byte[] encoded = attribute.encode();
        assertTrue(encoded.length == attribute.getDataLength() + 4);
        // test attribute type headers
        for(int i = 0; i < encoded.length; i++) {
            assertTrue(encoded[i] == ENCODED_CONNECTION_REQUEST_BINDING_ATTRIBUTE[i]);
        }
    }
}
