/*
 * Stun4j, the OpenSource Java Solution for NAT and Firewall Traversal.
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package net.java.stun4j.attribute;

import net.java.stun4j.StunException;
import net.java.stun4j.message.*;

/**
 * The class provides utilities for decoding a binary stream into an Attribute
 * class.
 *
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Organisation: Louis Pasteur University, Strasbourg, France</p>
 *                   <p>Network Research Team (http://www-r2.u-strasbg.fr)</p></p>
 * @author Emil Ivov
 * @version 0.1
 */

public class AttributeDecoder
{
    /**
     * Decodes the specified binary array and returns the corresponding
     * attribute object.
     * @param bytes the binary array that should be decoded.
     * @param offset the index where the message starts.
     * @param length the number of bytes that the message is long.
     * @return An object representing the attribute encoded in bytes or null if
     *         the attribute was not recognized.
     * @throws StunException if bytes does is not a valid STUN attribute.
     */
    public static Attribute decode(byte bytes[],
                                   char offset,
                                   char length)
        throws StunException
    {
        if(bytes == null || bytes.length < Attribute.HEADER_LENGTH)
            throw new StunException(StunException.ILLEGAL_ARGUMENT,
                                    "Could not decode the specified binary array.");

        //Discover attribute type
        char attributeType   = (char)((bytes[offset]<<8)|bytes[offset + 1]);
        char attributeLength = (char)((bytes[offset + 2]<<8)|bytes[offset + 3]);

        if(attributeLength > bytes.length - offset )
            throw new StunException(StunException.ILLEGAL_ARGUMENT,
                                    "The indicated attribute length ("+attributeLength+") "
                                    +"does not match the length of the passed binary array");

        Attribute decodedAttribute = null;

        switch(attributeType)
        {
            case Attribute.CHANGE_REQUEST:
                decodedAttribute = new ChangeRequestAttribute(); break;
            case Attribute.CHANGED_ADDRESS:
                decodedAttribute = new ChangedAddressAttribute(); break;
            case Attribute.MAPPED_ADDRESS:
                decodedAttribute = new MappedAddressAttribute(); break;
            case Attribute.ERROR_CODE:
                decodedAttribute = new ErrorCodeAttribute(); break;
            case Attribute.MESSAGE_INTEGRITY:
                throw new UnsupportedOperationException(
                    "The MESSAGE-INTEGRITY Attribute is not yet implemented.");
            case Attribute.PASSWORD:
                throw new UnsupportedOperationException(
                    "The PASSWORD Attribute is not yet implemented.");
            case Attribute.REFLECTED_FROM:
                decodedAttribute = new ReflectedFromAttribute(); break;
            case Attribute.RESPONSE_ADDRESS:
                decodedAttribute = new ResponseAddressAttribute(); break;
            case Attribute.SOURCE_ADDRESS:
                decodedAttribute = new SourceAddressAttribute(); break;
            case Attribute.UNKNOWN_ATTRIBUTES:
                decodedAttribute = new UnknownAttributesAttribute(); break;
            case Attribute.XOR_MAPPED_ADDRESS:
                decodedAttribute = new XorMappedAddressAttribute(); break;
            case Attribute.XOR_ONLY:
                decodedAttribute = new XorOnlyAttribute(); break;
            case Attribute.SERVER:
                decodedAttribute = new ServerAttribute(); break;
            case Attribute.USERNAME:
                throw new UnsupportedOperationException(
                    "The USERNAME Attribute is not yet implemented.");

            //According to rfc3489 we should silently ignore unknown attributes.
            default: decodedAttribute = new OptionalAttribute(Attribute.UNKNOWN_OPTIONAL_ATTRIBUTE);
                break;
        }

        decodedAttribute.setAttributeType(attributeType);

        decodedAttribute.decodeAttributeBody(bytes, (char)(Attribute.HEADER_LENGTH + offset), attributeLength);

        return decodedAttribute;
    }

}
