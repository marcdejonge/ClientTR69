/** Stun4j, the OpenSource Java Solution for NAT and Firewall Traversal.
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package net.java.stun4j.attribute;

import net.java.stun4j.StunException;
import net.java.stun4j.StunAddress;

/**
 *
 * This class  provides factory methods to allow an application to create
 * STUN Attributes from a particular implementation.
 *
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Organisation: Louis Pasteur University, Strasbourg, France</p>
 * <p>Network Research Team (http://www-r2.u-strasbg.fr)</p></p>
 * @author Emil Ivov
 * @version 0.1
 */
public class AttributeFactory
{
    private AttributeFactory()
    {
    }

//------------------------------------ CHANGE REQUEST --------------------------

    /**
     * Creates a ChangeRequestAttribute with "false" values for the changeIP and
     * changePort flags.
     * @return the newly created ChangeRequestAttribute.
     */
    public static ChangeRequestAttribute
                    createChangeRequestAttribute()
    {
        return createChangeRequestAttribute(false, false);
    };

    /**
     * Creates a ChangeRequestAttribute with the specified flag values.
     * @param changeIP   the value of the changeIP flag.
     * @param changePort the value of the changePort flag.
     * @return the newly created ChangeRequestAttribute.
     */
    public static ChangeRequestAttribute
                    createChangeRequestAttribute(boolean changeIP,
                                                 boolean changePort)
    {
        ChangeRequestAttribute attribute = new ChangeRequestAttribute();

        attribute.setChangeIpFlag(changeIP);
        attribute.setChangePortFlag(changePort);

        return attribute;
    };

//------------------------------------ CHANGED ADDRESS -------------------------

    /**
     * Creates a changedAddressAttribute of the specified type and with the
     * specified address and port
     * @param address the address value of the address attribute
     * @return the newly created address attribute.
     */
    public static ChangedAddressAttribute
                                  createChangedAddressAttribute(StunAddress address)
    {
        ChangedAddressAttribute attribute= new ChangedAddressAttribute();

        attribute.setAddress(address);

        return attribute;

    };

//------------------------------------ ERROR CODE ------------------------------
    /**
     * Creates an ErrorCodeAttribute with the specified error class and number
     * and a default reason phrase.
     * @param errorClass a valid error class.
     * @param errorNumber a valid error number.
     * @return the newly created attribute.
     * @throws StunException if the error class or number have invalid values
     * according to rfc3489.
     */
    public static ErrorCodeAttribute createErrorCodeAttribute(
                        byte errorClass,
                        byte errorNumber
                        )
        throws StunException
    {
        return createErrorCodeAttribute(errorClass, errorNumber, null);
    };

    /**
     * Creates an ErrorCodeAttribute with the specified error class, number and
     * reason phrase.
     * @param errorClass a valid error class.
     * @param errorNumber a valid error number.
     * @param reasonPhrase a human readable reason phrase. A null reason phrase
     *                     would be replaced (if possible) by a default one
     *                     as defined byte the rfc3489.
     * @return the newly created attribute.
     * @throws StunException if the error class or number have invalid values
     * according to rfc3489.
     */
    public static ErrorCodeAttribute createErrorCodeAttribute(
                        byte errorClass,
                        byte errorNumber,
                        String reasonPhrase
                        )
        throws StunException
    {
        ErrorCodeAttribute attribute = new ErrorCodeAttribute();

        attribute.setErrorClass(errorClass);
        attribute.setErrorNumber(errorNumber);

        attribute.setReasonPhrase(reasonPhrase==null?
            ErrorCodeAttribute.getDefaultReasonPhrase(attribute.getErrorCode())
            :reasonPhrase);

        return attribute;
    };



    /**
     * Creates an ErrorCodeAttribute with the specified error code and a default
     * reason phrase.
     * @param errorCode a valid error code.
     * @return the newly created attribute.
     * @throws StunException if errorCode is not a valid error code as defined
     * by rfc3489
     */
    public static ErrorCodeAttribute createErrorCodeAttribute(char errorCode)
        throws StunException
    {
        return createErrorCodeAttribute(errorCode, null);
    };

    /**
     * Creates an ErrorCodeAttribute with the specified error code and reason
     * phrase.
     * @param errorCode a valid error code.
     * @param reasonPhrase a human readable reason phrase. A null reason phrase
     *                     would be replaced (if possible) by a default one
     *                     as defined byte the rfc3489.

     * @return the newly created attribute.
     * @throws StunException if errorCode is not a valid error code as defined
     * by rfc3489
     */
    public static ErrorCodeAttribute createErrorCodeAttribute(
                                        char errorCode,
                                        String reasonPhrase)
        throws StunException
    {
        ErrorCodeAttribute attribute = new ErrorCodeAttribute();

        attribute.setErrorCode(errorCode);
        attribute.setReasonPhrase(reasonPhrase==null?
            ErrorCodeAttribute.getDefaultReasonPhrase(attribute.getErrorCode())
            :reasonPhrase);


        return attribute;
    };

//------------------------------------ MAPPED ADDRESS --------------------------

    /**
     * Creates a MappedAddressAttribute of the specified type and with the
     * specified address and port
     * @param address the address value of the address attribute
     * @return the newly created address attribute.
     */
    public static MappedAddressAttribute createMappedAddressAttribute(
                                                StunAddress address)
    {
        MappedAddressAttribute attribute = new MappedAddressAttribute();

        attribute.setAddress(address);

        return attribute;

    };

//------------------------------------ REFLECTED FROM --------------------------

    /**
     * Creates a ReflectedFromAddressAttribute of the specified type and with
     * the specified address and port
     * @param address the address value of the address attribute
     * @return the newly created address attribute.
     */
    public static ReflectedFromAttribute createReflectedFromAttribute(
                                                StunAddress address)
    {
        ReflectedFromAttribute attribute = new ReflectedFromAttribute();

        attribute.setAddress(address);

        return attribute;

    };

//------------------------------------ RESPONSE ADRESS -------------------------
    /**
     * Creates a ResponseFromAddressAttribute of the specified type and with
     * the specified address and port
     * @param address the address value of the address attribute
     * @return the newly created address attribute.
     */
    public static ResponseAddressAttribute createResponseAddressAttribute(
                                                    StunAddress address)
    {
        ResponseAddressAttribute attribute = new ResponseAddressAttribute();

        attribute.setAddress(address);

        return attribute;

    };

//------------------------------------ SOURCE ADDRESS --------------------------
    /**
     * Creates a SourceFromAddressAttribute of the specified type and with
     * the specified address and port
     * @param address the address value of the address attribute
     * @return the newly created address attribute.
     */
    public static SourceAddressAttribute createSourceAddressAttribute(
                                               StunAddress address)
    {
        SourceAddressAttribute attribute = new SourceAddressAttribute();

        attribute.setAddress(address);

        return attribute;

    };

//------------------------------------ UNKNOWN ATTRIBUTES ----------------------
    /**
     * Creates an empty UnknownAttributesAttribute.
     * @return the newly created UnknownAttributesAttribute
     */
    public static UnknownAttributesAttribute createUnknownAttributesAttribute()
    {
        UnknownAttributesAttribute attribute = new UnknownAttributesAttribute();

        return attribute;
    };

}
