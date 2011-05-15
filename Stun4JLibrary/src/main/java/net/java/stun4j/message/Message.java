/*
 * Stun4j, the OpenSource Java Solution for NAT and Firewall Traversal.
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package net.java.stun4j.message;

import net.java.stun4j.attribute.Attribute;
import java.util.*;
import net.java.stun4j.StunException;
import net.java.stun4j.attribute.ChangeRequestAttribute;
import net.java.stun4j.attribute.AttributeDecoder;

/**
 * This class represents a STUN message. STUN messages are TLV (type-length-value)
 * encoded using big endian (network ordered) binary.  All STUN messages start
 * with a STUN header, followed by a STUN payload.  The payload is a series of
 * STUN attributes, the set of which depends on the message type.  The STUN
 * header contains a STUN message type, transaction ID, and length.
 *
 * <p>Organisation: Louis Pasteur University, Strasbourg, France</p>
 * 					<p>Network Research Team (http://www-r2.u-strasbg.fr)</p>
 * @author Emil Ivov
 * @version 0.1
 */

public abstract class Message
{
    public static final char BINDING_REQUEST               = 0x0001;
    public static final char BINDING_RESPONSE              = 0x0101;
    public static final char BINDING_ERROR_RESPONSE        = 0x0111;
    public static final char SHARED_SECRET_REQUEST         = 0x0002;
    public static final char SHARED_SECRET_RESPONSE        = 0x0102;
    public static final char SHARED_SECRET_ERROR_RESPONSE  = 0x0112;

    //Message fields
    /**
     * The length of Stun Message Headers in byres
     * = len(Type) + len(DataLength) + len(Transaction ID).
     */
    public static final byte HEADER_LENGTH = 20;

    /**
     * Indicates the type of the message. The message type can be Binding Request,
     * Binding Response, Binding Error Response, Shared Secret Request, Shared
     * Secret Response, or Shared Secret Error Response.
     */
    protected char messageType = 0x0000;

    /**
     * The transaction ID is used to correlate requests and responses.
     */
    protected byte[] transactionID = null;

    /**
     * The length of the transaction id (in bytes).
     */
    public static final byte TRANSACTION_ID_LENGTH = 16;


    /**
     * The list of attributes contained by the message. We are using a hastable
     * rather than a uni-dimensional list, in order to facilitate attribute
     * search (even though it introduces some redundancies). Order is important
     * so we'll be using a LinkedHashMap
     */
    //not sure this is the best solution but I'm trying to keep entry order
    protected LinkedHashMap attributes = new LinkedHashMap();

    /**
     * Desribes which attributes are present in which messages.  An
     * M indicates that inclusion of the attribute in the message is
     * mandatory, O means its optional, C means it's conditional based on
     * some other aspect of the message, and N/A means that the attribute is
     * not applicable to that message type.
     *
     *
     *                                         Binding  Shared  Shared  Shared        <br/>
     *                       Binding  Binding  Error    Secret  Secret  Secret        <br/>
     *   Att.                Req.     Resp.    Resp.    Req.    Resp.   Error         <br/>
     *                                                                  Resp.         <br/>
     *   _____________________________________________________________________        <br/>
     *   MAPPED-ADDRESS      N/A      M        N/A      N/A     N/A     N/A           <br/>
     *   RESPONSE-ADDRESS    O        N/A      N/A      N/A     N/A     N/A           <br/>
     *   CHANGE-REQUEST      O        N/A      N/A      N/A     N/A     N/A           <br/>
     *   SOURCE-ADDRESS      N/A      M        N/A      N/A     N/A     N/A           <br/>
     *   CHANGED-ADDRESS     N/A      M        N/A      N/A     N/A     N/A           <br/>
     *   USERNAME            O        N/A      N/A      N/A     M       N/A           <br/>
     *   PASSWORD            N/A      N/A      N/A      N/A     M       N/A           <br/>
     *   MESSAGE-INTEGRITY   O        O        N/A      N/A     N/A     N/A           <br/>
     *   ERROR-CODE          N/A      N/A      M        N/A     N/A     M             <br/>
     *   UNKNOWN-ATTRIBUTES  N/A      N/A      C        N/A     N/A     C             <br/>
     *   REFLECTED-FROM      N/A      C        N/A      N/A     N/A     N/A           <br/>
     *   XOR-MAPPED-ADDRESS  N/A      M        N/A      N/A     N/A     N/A
     *   XOR-ONLY            O        N/A      N/A      N/A     N/A     N/A
     *   SERVER              N/A      O        O        N/A     O       O
     *
     */
    public static final byte N_A = 0;
    public static final byte C   = 1;
    public static final byte O   = 2;
    public static final byte M   = 3;

    //Message indices
    protected static final byte BINDING_REQUEST_PRESENTITY_INDEX              = 0;
    protected static final byte BINDING_RESPONSE_PRESENTITY_INDEX             = 1;
    protected static final byte BINDING_ERROR_RESPONSE_PRESENTITY_INDEX       = 2;
    protected static final byte SHARED_SECRET_REQUEST_PRESENTITY_INDEX        = 3;
    protected static final byte SHARED_SECRET_RESPONSE_PRESENTITY_INDEX       = 4;
    protected static final byte SHARED_SECRET_ERROR_RESPONSE_PRESENTITY_INDEX = 5;

    //Attribute indices
    protected static final byte MAPPED_ADDRESS_PRESENTITY_INDEX               =  0;
    protected static final byte RESPONSE_ADDRESS_PRESENTITY_INDEX             =  1;
    protected static final byte CHANGE_REQUEST_PRESENTITY_INDEX               =  2;
    protected static final byte SOURCE_ADDRESS_PRESENTITY_INDEX               =  3;
    protected static final byte CHANGED_ADDRESS_PRESENTITY_INDEX              =  4;
    protected static final byte USERNAME_PRESENTITY_INDEX                     =  5;
    protected static final byte PASSWORD_PRESENTITY_INDEX                     =  6;
    protected static final byte MESSAGE_INTEGRITY_PRESENTITY_INDEX            =  7;
    protected static final byte ERROR_CODE_PRESENTITY_INDEX                   =  8;
    protected static final byte UNKNOWN_ATTRIBUTES_PRESENTITY_INDEX           =  9;
    protected static final byte REFLECTED_FROM_PRESENTITY_INDEX               = 10;
    protected static final byte XOR_MAPPED_ADDRESS_PRESENTITY_INDEX           = 11;
    protected static final byte XOR_ONLY_PRESENTITY_INDEX                     = 12;
    protected static final byte SERVER_PRESENTITY_INDEX                       = 13;
    protected static final byte UNKNOWN_OPTIONAL_ATTRIBUTES_PRESENTITY_INDEX  = 14;


    protected final static byte attributePresentities[][] = new byte[][]{
    //                                            Binding   Shared   Shared   Shared
    //                        Binding   Binding   Error     Secret   Secret   Secret
    //  Att.                  Req.      Resp.     Resp.     Req.     Resp.    Error
    //                                                                        Resp.
    //  _______________________________________________________________________
      /*MAPPED-ADDRESS*/    { N_A,      M,        N_A,      N_A,     N_A,     N_A},
      /*RESPONSE-ADDRESS*/  { O,        N_A,      N_A,      N_A,     N_A,     N_A},
      /*CHANGE-REQUEST*/    { O,        N_A,      N_A,      N_A,     N_A,     N_A},
      /*SOURCE-ADDRESS*/    { N_A,      M,        N_A,      N_A,     N_A,     N_A},
      /*CHANGED-ADDRESS*/   { N_A,      M,        N_A,      N_A,     N_A,     N_A},
      /*USERNAME*/          { O,        N_A,      N_A,      N_A,     M,       N_A},
      /*PASSWORD*/          { N_A,      N_A,      N_A,      N_A,     M,       N_A},
      /*MESSAGE-INTEGRITY*/ { O,        O,        N_A,      N_A,     N_A,     N_A},
      /*ERROR-CODE*/        { N_A,      N_A,      M,        N_A,     N_A,     M},
      /*UNKNOWN-ATTRIBUTES*/{ N_A,      N_A,      C,        N_A,     N_A,     C},
      /*REFLECTED-FROM*/    { N_A,      C,        N_A,      N_A,     N_A,     N_A},
      /*XOR-MAPPED-ADDRESS*/{ N_A,      M,        N_A,      N_A,     N_A,     N_A},
      /*XOR-ONLY*/          { O,        N_A,      N_A,      N_A,     N_A,     N_A},
      /*SERVER*/            { N_A,      O,        O,        N_A,     O,       O},
      /*UNKNOWN_OPTIONAL*/  { O,        O,        O,        O,       O,       O}};




    /**
     * Creates an empty STUN Mesage.
     */
    protected Message()
    {
    }

    /**
     * Returns the length of this message's body.
     * @return the length of the data in this message.
     */
    public char getDataLength()
    {
        char length = 0;
        Attribute att = null;

        Iterator iter = attributes.entrySet().iterator();
        while (iter.hasNext()) {
            att = (Attribute)((Map.Entry)iter.next()).getValue();
            length += att.getDataLength() + Attribute.HEADER_LENGTH;
        }

        return length;
    }

    /**
     * Adds the specified attribute to this message. If an attribute with that
     * name was already added, it would be replaced.
     * @param attribute the attribute to add to this message.
     * @throws StunException if the message cannot contain
     * such an attribute.
     */
    public void addAttribute(Attribute attribute)
        throws StunException
    {
        Character attributeType = new Character(attribute.getAttributeType());

        if (getAttributePresentity(attributeType.charValue()) == N_A)
            throw new StunException(StunException.ILLEGAL_ARGUMENT,
                                    "The attribute "
                                    + attribute.getName()
                                    + " is not allowed in a "
                                    + getName());

        attributes.put(attributeType, attribute);
    }

    /**
     * Returns the attribute with the specified type or null if no such
     * attribute exists.
     *
     * @param attributeType the type of the attribute
     * @return the attribute with the specified type or null if no such attribute
     * exists
     */
    public Attribute getAttribute(char attributeType)
    {
        return (Attribute)attributes.get(new Character(attributeType));
    }

    /*
     * Returns an enumeration containing all message attributes.
     * @return an enumeration containing all message attributes..
     */
    /*
    public Iterator getAttributes()
    {
        return attributes.entrySet().iterator();
    }
    */

    /**
     * Removes the specified attribute.
     * @param attributeType the attribute to remove.
     */
    public void removeAttribute(char attributeType)
    {
        attributes.remove(new Character(attributeType));
    }

    /**
     * Returns the number of attributes, currently contained by the message.
     * @return the number of attributes, currently contained by the message.
     */
    public int getAttributeCount()
    {
        return  attributes.size();
    }



    /**
     * Sets this message's type to be messageType. Method is package access
     * as it should not permit changing the type of message once it has been
     * initialized (could provoke attribute discrepancies). Called by
     * messageFactory.
     * @param messageType the message type.
     * @throws StunException ILLEGAL_ARGUMENT if message type is not valid in
     * the current context (e.g. when trying to set a Response type to a Request
     * and vice versa)
     */
    protected void setMessageType(char messageType)
        throws StunException
    {
        this.messageType = messageType;
    }

    /**
     * The message type of this message.
     * @return the message type of the message.
     */
    public char getMessageType()
    {
        return messageType;
    }

    /**
     * Copies the specified tranID and sets it as this message's transactionID.
     * @param tranID the transaction id to set in this message.
     * @throws StunException ILLEGAL_ARGUMENT if the transaction id is not valid.
     */
    public void setTransactionID(byte[] tranID)
        throws StunException
    {
        if(tranID == null
           || tranID.length != TRANSACTION_ID_LENGTH)
            throw new StunException(StunException.ILLEGAL_ARGUMENT,
                                    "Invalid transaction id");

        this.transactionID = new byte[TRANSACTION_ID_LENGTH];
        System.arraycopy(tranID, 0,
                         this.transactionID, 0, TRANSACTION_ID_LENGTH);
    }

    /**
     * Returns a reference to this message's transaction id.
     * @return a reference to this message's transaction id.
     */
    public byte[] getTransactionID()
    {
        return this.transactionID;
    }

    /**
     * Returns whether an attribute could be present in this message.
     * @param attributeType the id of the attribute to check .
     * @return Message.N_A - for not applicable <br/>
     *         Message.C   - for case depending <br/>
     *         Message.N_A - for not applicable <br/>
     */
    protected byte getAttributePresentity(char attributeType)
    {
        byte msgIndex = -1;
        byte attributeIndex = -1;

        switch (messageType)
        {
            case BINDING_REQUEST:              msgIndex = BINDING_REQUEST_PRESENTITY_INDEX; break;
            case BINDING_RESPONSE:             msgIndex = BINDING_RESPONSE_PRESENTITY_INDEX; break;
            case BINDING_ERROR_RESPONSE:       msgIndex = BINDING_ERROR_RESPONSE_PRESENTITY_INDEX; break;
            case SHARED_SECRET_REQUEST:        msgIndex = SHARED_SECRET_REQUEST_PRESENTITY_INDEX; break;
            case SHARED_SECRET_RESPONSE:       msgIndex = SHARED_SECRET_RESPONSE_PRESENTITY_INDEX; break;
            case SHARED_SECRET_ERROR_RESPONSE: msgIndex = SHARED_SECRET_ERROR_RESPONSE_PRESENTITY_INDEX; break;
        }

        switch (attributeType)
        {
            case Attribute.MAPPED_ADDRESS:     attributeIndex = MAPPED_ADDRESS_PRESENTITY_INDEX; break;
            case Attribute.RESPONSE_ADDRESS:   attributeIndex = RESPONSE_ADDRESS_PRESENTITY_INDEX; break;
            case Attribute.CHANGE_REQUEST:     attributeIndex = CHANGE_REQUEST_PRESENTITY_INDEX; break;
            case Attribute.SOURCE_ADDRESS:     attributeIndex = SOURCE_ADDRESS_PRESENTITY_INDEX; break;
            case Attribute.CHANGED_ADDRESS:    attributeIndex = CHANGED_ADDRESS_PRESENTITY_INDEX; break;
            case Attribute.USERNAME:           attributeIndex = USERNAME_PRESENTITY_INDEX; break;
            case Attribute.PASSWORD:           attributeIndex = PASSWORD_PRESENTITY_INDEX; break;
            case Attribute.MESSAGE_INTEGRITY:  attributeIndex = MESSAGE_INTEGRITY_PRESENTITY_INDEX; break;
            case Attribute.ERROR_CODE:         attributeIndex = ERROR_CODE_PRESENTITY_INDEX; break;
            case Attribute.UNKNOWN_ATTRIBUTES: attributeIndex = UNKNOWN_ATTRIBUTES_PRESENTITY_INDEX; break;
            case Attribute.REFLECTED_FROM:     attributeIndex = REFLECTED_FROM_PRESENTITY_INDEX; break;
            case Attribute.XOR_MAPPED_ADDRESS: attributeIndex = XOR_MAPPED_ADDRESS_PRESENTITY_INDEX; break;
            case Attribute.XOR_ONLY:           attributeIndex = XOR_ONLY_PRESENTITY_INDEX; break;
            case Attribute.SERVER:             attributeIndex = SERVER_PRESENTITY_INDEX; break;
            default: attributeIndex = UNKNOWN_OPTIONAL_ATTRIBUTES_PRESENTITY_INDEX; break;
        }









        return attributePresentities[ attributeIndex ][ msgIndex ];
    }

    /**
     * Returns the human readable name of this message. Message names do
     * not really matter from the protocol point of view. They are only used
     * for debugging and readability.
     * @return this message's name.
     */
    public String getName()
    {
        switch (messageType)
        {
            case BINDING_REQUEST:              return "BINDING-REQUEST";
            case BINDING_RESPONSE:             return "BINDING-RESPONSE";
            case BINDING_ERROR_RESPONSE:       return "BINDING-ERROR-RESPONSE";
            case SHARED_SECRET_REQUEST:        return "SHARED-SECRET-REQUEST";
            case SHARED_SECRET_RESPONSE:       return "SHARED-SECRET-RESPONSE";
            case SHARED_SECRET_ERROR_RESPONSE: return "SHARED-SECRET-ERROR-RESPONSE";
        }

        return "UNKNOWN-MESSAGE";
    }

    /**
     * Compares two STUN Messages. Messages are considered equal when their
     * type, length, and all their attributes are equal.
     *
     * @param obj the object to compare this message with.
     * @return true if the messages are equal and false otherwise.
     */
    public boolean equals(Object obj)
    {
        if(!(obj instanceof Message)
           || obj == null)
            return false;

        if(obj == this)
            return true;

        Message msg = (Message) obj;
        if( msg.getMessageType()   != getMessageType())
            return false;
        if(msg.getDataLength() != getDataLength())
            return false;

        //compare attributes
        Iterator iter = attributes.entrySet().iterator();
        while (iter.hasNext()) {
            Attribute localAtt = (Attribute)((Map.Entry)iter.next()).getValue();

            if(!localAtt.equals(msg.getAttribute(localAtt.getAttributeType())))
                return false;
        }

        return true;
    }


    /**
     * Returns a binary representation of this message.
     * @return a binary representation of this message.
     * @throws StunException if the message does not have all required
     * attributes.
     */
    public byte[] encode()
        throws StunException
    {
        //make sure we have everything necessary to encode a proper message
        validateAttributePresentity();
        char dataLength = getDataLength();
        byte binMsg[] = new byte[HEADER_LENGTH + dataLength];
        int offset    = 0;

        binMsg[offset++] = (byte)(getMessageType()>>8);
        binMsg[offset++] = (byte)(getMessageType()&0xFF);

        binMsg[offset++] = (byte)(dataLength >> 8);
        binMsg[offset++] = (byte)(dataLength & 0xFF);

        System.arraycopy(getTransactionID(), 0, binMsg, offset, TRANSACTION_ID_LENGTH);
        offset+=TRANSACTION_ID_LENGTH;

        Iterator iter = attributes.entrySet().iterator();
        while (iter.hasNext()) {

            Attribute attribute = (Attribute)((Map.Entry)iter.next()).getValue();

            byte[] attBinValue = attribute.encode();
            System.arraycopy(attBinValue, 0, binMsg, offset, attBinValue.length);
            offset += attBinValue.length;
        }

        return binMsg;
    }

    /**
     * Constructs a message from its binary representation.
     * @param binMessage the binary array that contains the encoded message
     * @param offset the index where the message starts.
     * @param arrayLen the length of the message
     * @return a Message object constructed from the binMessage array
     * @throws StunException ILLEGAL_ARGUMENT if one or more of the arguments
     * have invalid values.
     */
    public static Message decode(byte binMessage[], char offset, char arrayLen)
        throws StunException
    {
        arrayLen = (char)Math.min(binMessage.length, arrayLen);

        if(binMessage == null || arrayLen - offset < Message.HEADER_LENGTH)
            throw new StunException(StunException.ILLEGAL_ARGUMENT,
                                    "The given binary array is not a valid StunMessage");

        char messageType = (char)((binMessage[offset++]<<8) | (binMessage[offset++]&0xFF));
        Message message;
        if (Message.isResponseType(messageType))
        {
            message = new Response();
        }
        else
        {
            message = new Request();
        }
        message.setMessageType(messageType);

        int length = (char)((binMessage[offset++]<<8) | (binMessage[offset++]&0xFF));

        if(arrayLen - offset - TRANSACTION_ID_LENGTH < length)
            throw new StunException(StunException.ILLEGAL_ARGUMENT,
                                    "The given binary array does not seem to "
                                    +"contain a whole StunMessage");

        byte tranID[] = new byte[TRANSACTION_ID_LENGTH];
        System.arraycopy(binMessage, offset, tranID, 0, TRANSACTION_ID_LENGTH);
        message.setTransactionID(tranID);
        offset+=TRANSACTION_ID_LENGTH;

        while(offset - Message.HEADER_LENGTH< length)
        {
            Attribute att = AttributeDecoder.decode(binMessage,
                                                    (char)offset,
                                                    (char)(length - offset));
            message.addAttribute(att);
            offset += att.getDataLength() + Attribute.HEADER_LENGTH;
        }

        return message;

    }

    /**
     * Verify that the message has all obligatory attributes and throw an
     * exception if this is not the case.
     *
     * @return true if the message has all obligatory attributes, false
     * otherwise.
     * @throws StunException (ILLEGAL_STATE)if the message does not have all
     * required attributes.
     */
    protected void validateAttributePresentity()
        throws StunException
    {
        for(char i = Attribute.MAPPED_ADDRESS; i < Attribute.REFLECTED_FROM; i++)
            if(getAttributePresentity(i) == M && getAttribute(i) == null)
                throw new StunException(StunException.ILLEGAL_STATE,
                                        "A mandatory attribute (type="
                                        +(int)i
                                        + ") is missing!");

    }

    /**
     * Determines whether type could be the type of a STUN Response (as opposed
     * to STUN Request).
     * @param type the type to test.
     * @return true if type is a valid response type.
     */
    public static boolean isResponseType(char type)
    {
        return (((type >> 8) & 1) != 0);
    }

    /**
     * Determines whether type could be the type of a STUN Request (as opposed
     * to STUN Response).
     * @param type the type to test.
     * @return true if type is a valid request type.
     */
    public static boolean isRequestType(char type)
    {
        return !isResponseType(type);
    }

    public String toString()
    {
        return getName()+"("+getMessageType()
            +")[attrib.count=" + getAttributeCount()
            +" len=" + this.getDataLength()
            +" tranID=" + this.getTransactionID() + "]";
    }

}
