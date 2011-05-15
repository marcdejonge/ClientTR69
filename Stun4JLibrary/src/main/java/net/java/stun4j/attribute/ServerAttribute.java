/*
 * Stun4j, the OpenSource Java Solution for NAT and Firewall Traversal.
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package net.java.stun4j.attribute;

import net.java.stun4j.StunException;
import java.util.*;

/**
 *    The server attribute contains a textual description of the software
 * being used by the server, including manufacturer and version number.
 * The attribute has no impact on operation of the protocol, and serves
 * only as a tool for diagnostic and debugging purposes.
 * The value of SERVER is variable length.  Its length MUST be a
 * multiple of 4 (measured in bytes) in order to guarantee alignment of
 * attributes on word boundaries.
 *
 * <p>Organization Network Research Team, Louis Pasteur University</p>
 * <p>@author Emil Ivov</p>
 */
public class ServerAttribute
    extends Attribute
{
    private byte[] server = null;

    protected ServerAttribute()
    {
        super(SERVER);
    }

    /**
     * Copies the value of the server attribute from the specified
     * attributeValue.
     *
     * @param attributeValue a binary array containing this attribute's
     *   field values and NOT containing the attribute header.
     * @param offset the position where attribute values begin (most often
     *   offset is equal to the index of the first byte after length)
     * @param length the length of the binary array.
     * @throws StunException if attrubteValue contains invalid data.
     */
    public void decodeAttributeBody(byte[] attributeValue, char offset, char length) throws
        StunException
    {
        server = new byte[length];
        System.arraycopy(attributeValue, offset, server, 0, length);
    }

    /**
     * Returns a binary representation of this attribute.
     *
     * @return a binary representation of this attribute.
     */
    public byte[] encode()
    {
        char type = getAttributeType();
        byte binValue[] = new byte[HEADER_LENGTH + getDataLength()];

        //Type
        binValue[0] = (byte)(type>>8);
        binValue[1] = (byte)(type&0x00FF);

        //Length
        binValue[2] = (byte)(getDataLength()>>8);
        binValue[3] = (byte)(getDataLength()&0x00FF);

        //server
        System.arraycopy(server, 0, binValue, 4, getDataLength());

        return binValue;
    }

    /**
     * Returns the length of this attribute's body.
     *
     * @return the length of this attribute's value.
     */
    public char getDataLength()
    {
        return (char)server.length;
    }

    /**
     * Returns the human readable name of this attribute.
     *
     * @return this attribute's name.
     */
    public String getName()
    {
        return "SERVER";
    }

    /**
     * Returns a (cloned) byte array containg the data value of the server
     * attribute.
     * @return the binary array containing the server.
     */
    public byte[] getServer()
    {
        if (server == null)
            return null;

        byte[] copy = new byte[server.length];
        System.arraycopy(server, 0, copy, 0, server.length);
        return server;
    }

    /**
     * Copies the specified binary array into the the data value of the server
     * attribute.
     *
     * @param server the binary array containing the server.
     */
    public void setServer(byte[] server)
    {
        if (server == null)
            this.server = null;

        this.server = new byte[server.length];
        System.arraycopy(server, 0, this.server, 0, server.length);
    }

    /**
     * Compares two STUN Attributes. Two attributes are considered equal when they
     * have the same type length and value.
     *
     * @param obj the object to compare this attribute with.
     * @return true if the attributes are equal and false otherwise.
     */

    public boolean equals(Object obj)
    {
        if (! (obj instanceof ServerAttribute)
            || obj == null)
            return false;

        if (obj == this)
            return true;

        ServerAttribute att = (ServerAttribute) obj;
        if (att.getAttributeType() != getAttributeType()
            || att.getDataLength() != getDataLength()
            || !Arrays.equals( att.server, server))
            return false;

        return true;
    }

}
