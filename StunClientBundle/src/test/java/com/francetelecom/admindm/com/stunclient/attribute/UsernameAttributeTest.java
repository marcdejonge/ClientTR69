package com.francetelecom.admindm.com.stunclient.attribute;
import net.java.stun4j.StunException;
import junit.framework.TestCase;

/**
 * The Class UsernameAttributeTest.
 */
public class UsernameAttributeTest extends TestCase {
    /** The Constant USERNAME. */
    private static final String USERNAME = "greg";
    /** The ENCODE d_ attribute. */
    private static byte[] ENCODED_ATTRIBUTE =
            new byte[] { /* type */0, (byte) 6, /* length */0, (byte) 0x0 };
    /** The ENCODE d_ attribut e_ g. */
    private static byte[] ENCODED_ATTRIBUTE_G =
            new byte[] { /* type */0, (byte) 6, /* length */0, (byte) 0x4, /* data */
                    (byte) 'g', (byte) ' ', (byte) ' ', (byte) ' ' };
    /** The ENCODE d_ attribut e_ gr. */
    private static byte[] ENCODED_ATTRIBUTE_GR =
            new byte[] { /* type */0, (byte) 6, /* length */0, (byte) 0x4, /* data */
                    (byte) 'g', (byte) 'r', (byte) ' ', (byte) ' ' };
    /** The ENCODE d_ attribut e_ gre. */
    private static byte[] ENCODED_ATTRIBUTE_GRE =
            new byte[] { /* type */0, (byte) 6, /* length */0, (byte) 0x4, /* data */
                    (byte) 'g', (byte) 'r', (byte) 'e', (byte) ' ' };
    /** The ENCODE d_ attribute. */
    private static byte[] ENCODED_ATTRIBUTE_GREG =
            new byte[] { /* type */0, (byte) 6, /* length */0, (byte) 0x4, /* data */
                    (byte) 'g', (byte) 'r', (byte) 'e', (byte) 'g' };
    /** The ENCODE d_ attribute. */
    private static byte[] ENCODED_ATTRIBUTE_GREG1 =
            new byte[] { /* type */0, (byte) 6, /* length */0, (byte) 0x8, /* data */
                    (byte) 'g', (byte) 'r', (byte) 'e', (byte) 'g',
                    (byte) '1', (byte) ' ', (byte) ' ', (byte) ' ' };
    /** The ENCODE d_ attribute. */
    private static byte[] ENCODED_ATTRIBUTE_GREG12 =
            new byte[] { /* type */0, (byte) 6, /* length */0, (byte) 0x8, /* data */
                    (byte) 'g', (byte) 'r', (byte) 'e', (byte) 'g',
                    (byte) '1', (byte) '2', (byte) ' ', (byte) ' ' };
    /** The ENCODE d_ attribute. */
    private static byte[] ENCODED_ATTRIBUTE_GREG123 =
            new byte[] { /* type */0, (byte) 6, /* length */0, (byte) 0x8, /* data */
                    (byte) 'g', (byte) 'r', (byte) 'e', (byte) 'g',
                    (byte) '1', (byte) '2', (byte) '3', (byte) ' ' };
    /** The ENCODE d_ attribute. */
    private static byte[] ENCODED_ATTRIBUTE_GREG1234 =
            new byte[] { /* type */0, (byte) 6, /* length */0, (byte) 0x8, /* data */
                    (byte) 'g', (byte) 'r', (byte) 'e', (byte) 'g',
                    (byte) '1', (byte) '2', (byte) '3', (byte) '4' };
    /** The ENCODE d_ attribute. */
    private static byte[] ENCODED_ATTRIBUTE_GREG12345 =
            new byte[] { /* type */0, (byte) 6, /* length */0, (byte) 0xc, /* data */
                    (byte) 'g', (byte) 'r', (byte) 'e', (byte) 'g',
                    (byte) '1', (byte) '2', (byte) '3', (byte) '4',
                    (byte) '5', (byte) ' ', (byte) ' ', (byte) ' ' };
    /**
     * Instantiates a new username attribute test.
     * 
     * @param arg0 the arg0
     */
    public UsernameAttributeTest(String arg0) {
        super(arg0);
    }
    /**
     * Sets the up.
     * 
     * @throws Exception the exception
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }
    public final void testCreateUsernameAttribute() {
        UsernameAttribute usernameAttribute =
                UsernameAttribute.createUsernameAttribute('a', USERNAME);
        // check attribute type
        assertTrue(usernameAttribute.getAttributeType() == 0x0006);
        // check name
        assertTrue(usernameAttribute.getName().equals("USERNAME"));
        // check size
        assertTrue(((usernameAttribute.getDataLength() == 4)));
        // check equal
        assertFalse(usernameAttribute.equals(usernameAttribute));
        
        // check decode method
        boolean exception = false;
        try {
            usernameAttribute.decodeAttributeBody(new byte[] {}, 'a', 'b');
            fail();
        } catch (StunException e) {
            exception = true;
        }
        assertTrue(exception);
        // check encode method
        byte[] encoded = usernameAttribute.encode();
        assertTrue(encoded.length == ENCODED_ATTRIBUTE_GREG.length);
        for (int i = 0; i < encoded.length; i++) {
            assertTrue(encoded[i] == ENCODED_ATTRIBUTE_GREG[i]);
        }
    }
    /**
     * Test get data length. Create several UsernameAttribute with different
     * username size.
     */
    public final void testGetDataLengthAndEncode() {
        UsernameAttribute usernameAttribute = null;
        // username = ""
        usernameAttribute = UsernameAttribute.createUsernameAttribute('a', "");
        assertTrue(usernameAttribute.getDataLength() == 0);
        assertTrue(compareByteArray(usernameAttribute.encode(),
                ENCODED_ATTRIBUTE));
        // username = g
        usernameAttribute =
                UsernameAttribute.createUsernameAttribute('a', "g");
        assertTrue(usernameAttribute.getDataLength() == 4);
        assertTrue(compareByteArray(usernameAttribute.encode(),
                ENCODED_ATTRIBUTE_G));
        // username = gr
        usernameAttribute =
                UsernameAttribute.createUsernameAttribute('a', "gr");
        assertTrue(usernameAttribute.getDataLength() == 4);
        assertTrue(compareByteArray(usernameAttribute.encode(),
                ENCODED_ATTRIBUTE_GR));
        // username = gre
        usernameAttribute =
                UsernameAttribute.createUsernameAttribute('a', "gre");
        assertTrue(usernameAttribute.getDataLength() == 4);
        assertTrue(compareByteArray(usernameAttribute.encode(),
                ENCODED_ATTRIBUTE_GRE));
        // username = greg1
        usernameAttribute =
                UsernameAttribute.createUsernameAttribute('a', "greg1");
        assertTrue(usernameAttribute.getDataLength() == 8);
        assertTrue(compareByteArray(usernameAttribute.encode(),
                ENCODED_ATTRIBUTE_GREG1));
        // username = greg12
        usernameAttribute =
                UsernameAttribute.createUsernameAttribute('a', "greg12");
        assertTrue(usernameAttribute.getDataLength() == 8);
        assertTrue(compareByteArray(usernameAttribute.encode(),
                ENCODED_ATTRIBUTE_GREG12));
        // username = greg123
        usernameAttribute =
                UsernameAttribute.createUsernameAttribute('a', "greg123");
        assertTrue(usernameAttribute.getDataLength() == 8);
        assertTrue(compareByteArray(usernameAttribute.encode(),
                ENCODED_ATTRIBUTE_GREG123));
        // username = greg1234
        usernameAttribute =
                UsernameAttribute.createUsernameAttribute('a', "greg1234");
        assertTrue(usernameAttribute.getDataLength() == 8);
        assertTrue(compareByteArray(usernameAttribute.encode(),
                ENCODED_ATTRIBUTE_GREG1234));
        // username = greg12345
        usernameAttribute =
                UsernameAttribute.createUsernameAttribute('a', "greg12345");
        assertTrue(usernameAttribute.getDataLength() == 12);
        assertTrue(compareByteArray(usernameAttribute.encode(),
                ENCODED_ATTRIBUTE_GREG12345));
    }
    /**
     * Compare byte array.
     * 
     * @param array1 the array1
     * @param array2 the array2
     * 
     * @return true, if successful
     */
    private boolean compareByteArray(byte[] array1, byte[] array2) {
        if (array1.length != array2.length) {
            return false;
        } else {
            for (int i = 0; i < array1.length; i++) {
                if (array1[i] != array2[i]) {
                    return false;
                }
            }
        }
        return true;
    }
}
