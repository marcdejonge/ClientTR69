/*
 * Stun4j, the OpenSource Java Solution for NAT and Firewall Traversal.
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package net.java.stun4j.attribute;

import net.java.stun4j.StunException;

/**
 * After the header are 0 or more attributes.  Each attribute is TLV
 * encoded, with a 16 bit type, 16 bit length, and variable value:
 *
 *     0                   1                   2                   3       <br/>
 *     0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1     <br/>
 *    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+    <br/>
 *    |         Type                  |            Length             |    <br/>
 *    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+    <br/>
 *    |                             Value                             .... <br/>
 *    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+    <br/>
 *<br/>
 *    The following types are defined:<br/>
 *<br/>
 *    0x0001: MAPPED-ADDRESS                                               <br/>
 *    0x0002: RESPONSE-ADDRESS                                             <br/>
 *    0x0003: CHANGE-REQUEST                                               <br/>
 *    0x0004: SOURCE-ADDRESS                                               <br/>
 *    0x0005: CHANGED-ADDRESS                                              <br/>
 *    0x0006: USERNAME                                                     <br/>
 *    0x0007: PASSWORD                                                     <br/>
 *    0x0008: MESSAGE-INTEGRITY                                            <br/>
 *    0x0009: ERROR-CODE                                                   <br/>
 *    0x000a: UNKNOWN-ATTRIBUTES                                           <br/>
 *    0x000b: REFLECTED-FROM                                               <br/>
 *                                                                         <br/>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Organisation: <p> Organisation: Louis Pasteur University, Strasbourg, France</p>
 * 					<p> Network Research Team (http://www-r2.u-strasbg.fr)</p></p>
 * @author Emil Ivov
 * @version 0.1
 */
public abstract class Attribute
{
    public static final char MAPPED_ADDRESS = 0x0001;
    public static final char RESPONSE_ADDRESS = 0x0002;
    public static final char CHANGE_REQUEST = 0x0003;
    public static final char SOURCE_ADDRESS = 0x0004;
    public static final char CHANGED_ADDRESS = 0x0005;
    public static final char USERNAME = 0x0006;
    public static final char PASSWORD = 0x0007;
    public static final char MESSAGE_INTEGRITY = 0x0008;
    public static final char ERROR_CODE = 0x0009;
    public static final char UNKNOWN_ATTRIBUTES = 0x000a;
    public static final char REFLECTED_FROM = 0x000b;
    public static final char XOR_MAPPED_ADDRESS = 0x8020;
    public static final char XOR_ONLY = 0x0021;
    public static final char SERVER = 0x8022;
    public static final char UNKNOWN_OPTIONAL_ATTRIBUTE = 0x8000;

    /**
     * The type of the attribute.
     */
    protected char attributeType = 0;

    /**
     * The size of an atribute header in bytes = len(TYPE) + len(LENGTH) = 4
     */
    public static final char HEADER_LENGTH = 4;

    /**
     * Creates an empty STUN message attribute.
     *
     * @param attributeType the type of the attribute.
     */
    protected Attribute(char attributeType)
    {
        setAttributeType(attributeType);
    }

    /**
     * Returns the length of this attribute's body.
     * @return the length of this attribute's value.
     */
    public abstract char getDataLength();

    /**
     * Returns the human readable name of this attribute. Attribute names do
     * not really matter from the protocol point of view. They are only used
     * for debugging and readability.
     * @return this attribute's name.
     */
    public abstract String getName();

    /**
     * Returns the attribute's type.
     * @return the type of this attribute.
     */
    public char getAttributeType()
    {
        return attributeType;
    }

    /**
     * Sets the attribute's type.
     * @param type the new type of this attribute
     */
    protected void setAttributeType(char type)
    {
        this.attributeType = type;
    }

   /**
    * Compares two STUN Attributes. Two attributes are considered equal when they
    * have the same type length and value.
    *
    * @param obj the object to compare this attribute with.
    * @return true if the attributes are equal and false otherwise.
    */

    public abstract boolean equals(Object obj);

    /**
     * Returns a binary representation of this attribute.
     * @return a binary representation of this attribute.
     */
    public abstract byte[] encode();

    /**
     * Sets this attribute's fields according to attributeValue array.
     *
     * @param attributeValue a binary array containing this attribute's field
     *                       values and NOT containing the attribute header.
     * @param offset the position where attribute values begin (most often
     * 				 offset is equal to the index of the first byte after
     * 				 length)
     * @param length the length of the binary array.
     * @throws StunException if attrubteValue contains invalid data.
     */
    public abstract void decodeAttributeBody(byte[] attributeValue, char offset, char length)
        throws StunException;

}
