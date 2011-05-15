package com.francetelecom.admindm.com.stunclient.attribute;
import net.java.stun4j.StunException;
import net.java.stun4j.attribute.Attribute;
/**
 * The Class ConnectionRequestBindingAttribute.
 * @author mpcy8647
 */
public final class ConnectionRequestBindingAttribute extends Attribute {
    /** The Constant ATTRIBUTE_TYPE. */
    private static final char ATTRIBUTE_TYPE = 0xc001;
    /** The Constant NAME. */
    private static final String NAME = "CONNECTION-REQUEST-BINDING";
    /** The Constant VALUE. */
    private static final byte[] VALUE = {0x64, 0x73, 0x6c, 0x66, 0x6f, 0x72,
            0x75, 0x6d, 0x2e, 0x6f, 0x72, 0x67, 0x2f, 0x54, 0x52, 0x2d, 0x31,
            0x31, 0x31, 0x20 };
    /** LENGTH. */
    private static final char DATA_LENGTH = 20;
    /**
     * Creates the connection request binding attribute.
     * @param arg0 the arg0
     * @return the connection request binding attribute
     */
    public static ConnectionRequestBindingAttribute 
        createConnectionRequestBindingAttribute(char arg0) {
        return new ConnectionRequestBindingAttribute(arg0);
    }
    /**
     * Instantiates a new connection request binding attribute.
     * @param arg0 the arg0
     */
    protected ConnectionRequestBindingAttribute(char arg0) {
        super(arg0);
    }
    /**
     * Decode attribute body.
     * @param arg0 the arg0
     * @param arg1 the arg1
     * @param arg2 the arg2
     * @throws StunException the stun exception
     */
    public void decodeAttributeBody(byte[] arg0, char arg1, char arg2)
            throws StunException {
        throw new StunException("Not implemented");
    }
    /**
     * Encode.
     * @return the byte[]
     * @see net.java.stun4j.attribute.Attribute#encode()
     */
    public byte[] encode() {
        byte[] binValue = new byte[HEADER_LENGTH + DATA_LENGTH];
        // Type
        binValue[0] = (byte) (ATTRIBUTE_TYPE >> 8);
        binValue[1] = (byte) (ATTRIBUTE_TYPE & 0x00FF);
        // Length
        binValue[2] = (byte) (getDataLength() >> 8);
        binValue[3] = (byte) (getDataLength() & 0x00FF);
        // Data
        for (int i = 0; i < DATA_LENGTH; i++) {
            binValue[i + 4] = VALUE[i];
        }
        return binValue;
    }
    /**
     * Gets the data length.
     * @return the data length
     * @see net.java.stun4j.attribute.Attribute#getDataLength()
     */
    public char getDataLength() {
        return DATA_LENGTH;
    }
    /**
     * Gets the name.
     * @return the name
     * @see net.java.stun4j.attribute.Attribute#getName()
     */
    public String getName() {
        return NAME;
    }
    /**
     * Equals.
     * @param arg0 the arg0
     * @return always false
     * @see net.java.stun4j.attribute.Attribute#equals(java.lang.Object)
     */
    public boolean equals(Object arg0) {
        return false;
    }
}
