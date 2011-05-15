package com.francetelecom.admindm.com.stunclient.attribute;
import net.java.stun4j.StunException;
import junit.framework.TestCase;
public class BindingChangeAttributeTest extends TestCase {
    
    /** The ENCODE d_ bindin g_ chang e_ attribute. */
    private static byte[] ENCODED_BINDING_CHANGE_ATTRIBUTE;
    
    static {
        ENCODED_BINDING_CHANGE_ATTRIBUTE = new byte[] { (byte) 11000000, (byte)2 , 0, 0};
    }
    
    /**
     * Instantiates a new binding change attribute test.
     */
    public BindingChangeAttributeTest() {
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
    /**
     * Test create binding change attribute.
     */
    public final void testCreateBindingChangeAttribute() {
        BindingChangeAttribute bindingChangeAttribute =
                BindingChangeAttribute.createBindingChangeAttribute('a');
        
        // test getName method
        assertTrue(bindingChangeAttribute.getName().equals("BINDING-CHANGE"));
        
        // test getDataLength method
        assertTrue(bindingChangeAttribute.getDataLength() == 0);
        
        // test equal method (always return false)
        assertFalse(bindingChangeAttribute.equals(null));
        
        // test encoding
        byte[] encoded = bindingChangeAttribute.encode();
        for(int i = 0 ; i < encoded.length; i++) {
            assertTrue(encoded[i] == ENCODED_BINDING_CHANGE_ATTRIBUTE[i]);
        }
        
        // test decoding
        boolean exception = false;
        try {
            bindingChangeAttribute.decodeAttributeBody(ENCODED_BINDING_CHANGE_ATTRIBUTE, '0', '0');
            fail();
        } catch (StunException e) {
            exception = true;
        }
        assertTrue(exception);
    }
}
