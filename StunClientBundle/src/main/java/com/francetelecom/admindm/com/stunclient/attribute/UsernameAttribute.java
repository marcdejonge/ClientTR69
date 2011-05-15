/**
 * 
 */
package com.francetelecom.admindm.com.stunclient.attribute;

import net.java.stun4j.StunException;
import net.java.stun4j.attribute.Attribute;

/**
 * A USERNAME attribute is added in a STUN BindingRequest if the
 * IGD.ManagementServer.STUNUsername is set.
 * @author mpcy8647 
 */
public final class UsernameAttribute extends Attribute {

	/** attribute type.*/
	private static final char ATTRIBUTE_TYPE = 0x0006;
	
	/** name.*/
	private static final String NAME = "USERNAME";
	
	/** username attribute value.*/
	private final String username;

	/**
	 * Create a new UsernameAttribute object.
	 * @param arg0
	 * @param username
	 *            username
	 * @return UsernameAttribute
	 */
	public static UsernameAttribute createUsernameAttribute(char arg0,
			String username) {
		return new UsernameAttribute(arg0, username);
	}

	/**
	 * @see net.java.stun4j.attribute.Attribute#decodeAttributeBody(byte[],
	 *      char, char)
	 */
	public void decodeAttributeBody(byte[] arg0, char arg1, char arg2)
			throws StunException {
	    throw new StunException("Not implemented!!");
	}

	/**
     * Encode.
     * @return the byte[]
     * @see net.java.stun4j.attribute.Attribute#encode()
     */
	public byte[] encode() {
		byte binValue[] = new byte[HEADER_LENGTH + getDataLength()];

        //Type
        binValue[0] = (byte)(ATTRIBUTE_TYPE>>8);
        binValue[1] = (byte)(ATTRIBUTE_TYPE&0x00FF);
        //Length
        binValue[2] = (byte)(getDataLength()>>8);
        binValue[3] = (byte)(getDataLength()&0x00FF);
        //Data
        byte[] encodingValue = username.getBytes();
        for(int i = 0; i < getDataLength(); i++) {
        	if (i < username.length()) {
        		binValue[i+4] = encodingValue[i];
        	} else {
        		binValue[i+4] = 0x20;
        	}
        }

        return binValue;
	}

	/**
	 * Equals.
	 * 
	 * @param arg0 the arg0
	 * 
	 * @return always return false
	 * 
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
		if ((username.length() % 4) != 0) {
			int entireDiv = (username.length() / 4);
			return (char) ((entireDiv + 1) * 4);
		} else {
			return (char) username.length();
		}
		
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
	 * Initialize a UsernameAttribute.
	 * @param arg0
	 * @param username
	 *            username
	 */
	protected UsernameAttribute(char arg0, String username) {
		super(arg0);
        setAttributeType(Attribute.USERNAME);
		this.username = username;
	}

}
