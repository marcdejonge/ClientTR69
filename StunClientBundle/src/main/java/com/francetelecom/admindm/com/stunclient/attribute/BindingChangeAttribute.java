/**
 * 
 */
package com.francetelecom.admindm.com.stunclient.attribute;
import net.java.stun4j.StunException;
import net.java.stun4j.attribute.Attribute;
/**
 * The Class BindingChangeAttribute.
 * @author mpcy8647
 */
public final class BindingChangeAttribute extends Attribute {
    /** attribute type. */
    private static final char ATTRIBUTE_TYPE = 0xc002;
    /** name. */
    private static final String NAME = "BINDING-CHANGE";
    /** LENGTH. */
    private static final char DATA_LENGTH = 0;
    /**
     * Creates the binding change attribute.
     * @param arg0 the arg0
     * @return the binding change attribute
     */
    public static BindingChangeAttribute createBindingChangeAttribute(char arg0) {
        return new BindingChangeAttribute(arg0);
    }
    /**
     * Instantiates a new binding change attribute.
     * @param arg0 the arg0
     */
    protected BindingChangeAttribute(char arg0) {
        super(arg0);
    }
    /**
     * Decode attribute body.
     * @param arg0 the arg0
     * @param arg1 the arg1
     * @param arg2 the arg2
     * @throws StunException the stun exception
     * @see net.java.stun4j.attribute.Attribute#decodeAttributeBody(byte[],
     *      char, char)
     */
    public void decodeAttributeBody(byte[] arg0, char arg1, char arg2)
            throws StunException {
        throw new StunException("Not implemented !!");
    }
    /**
     * Encode.
     * @return the byte[]
     * @see net.java.stun4j.attribute.Attribute#encode()
     */
    public byte[] encode() {
        byte binValue[] = new byte[HEADER_LENGTH + DATA_LENGTH];
        // Type
        binValue[0] = (byte) (ATTRIBUTE_TYPE >> 8);
        binValue[1] = (byte) (ATTRIBUTE_TYPE & 0x00FF);
        // Length
        binValue[2] = (byte) (getDataLength() >> 8);
        binValue[3] = (byte) (getDataLength() & 0x00FF);
        return binValue;
    }
    /**
     * Equals. THIS METHOD ISN'T IMPLEMENTED.
     * @param arg0 the arg0
     * @return always return false
     * @see net.java.stun4j.attribute.Attribute#equals(java.lang.Object)
     */
    public boolean equals(Object arg0) {
        return false;
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
}
