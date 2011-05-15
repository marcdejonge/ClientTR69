/*
 * Stun4j, the OpenSource Java Solution for NAT and Firewall Traversal.
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package net.java.stun4j.message;

import net.java.stun4j.attribute.*;
import net.java.stun4j.*;
import net.java.stun4j.StunException;
import java.util.List;
import java.util.logging.*;

/**
 * This class  provides factory methods to allow an application to create
 * STUN Messages from a particular implementation.
 *
 * <p>Organisation: Louis Pasteur University, Strasbourg, France.</p>
 *                   <p>Network Research Team (http://www-r2.u-strasbg.fr)</p></p>
 * @author Emil Ivov
 * @version 0.1
 */

public class MessageFactory
{
    private static final Logger logger =
        Logger.getLogger(MessageFactory.class.getName());
    /**
     * Creates a default binding request. The request contains a ChangeReqeust
     * attribute with zero change ip and change port flags.
     * @return a default binding request.
     */
    public static Request createBindingRequest()
    {
        Request bindingRequest = new Request();
        try
        {
            bindingRequest.setMessageType(Message.BINDING_REQUEST);
        }
        catch (StunException ex)
        {
            //there should be no exc here since we're the creators.
            logger.log(Level.FINE, "Failed to set message type.", ex);
        }

        //add a change request attribute
        ChangeRequestAttribute attribute
                             = AttributeFactory.createChangeRequestAttribute();

        try
        {
            bindingRequest.addAttribute(attribute);
        }
        catch (StunException ex)
        {
            //shouldn't happen
            throw new RuntimeException("Failed to add a change request attribute to a binding request!");
        }

        return bindingRequest;
    }

    /**
     * Creates a BindingResponse assigning the specified values to mandatory
     * headers.
     *
     * @param mappedAddress     the address to assign the mappedAddressAttribute
     * @param sourceAddress     the address to assign the sourceAddressAttribute
     * @param changedAddress    the address to assign the changedAddressAttribute
     * @return a BindingResponse assigning the specified values to mandatory
     *         headers.
     * @throws StunException ILLEGAL_ARGUMENT
     */
    public static Response createBindingResponse(StunAddress mappedAddress,
                                                 StunAddress sourceAddress,
                                                 StunAddress changedAddress)
        throws StunException
    {
        Response bindingResponse = new Response();
        bindingResponse.setMessageType(Message.BINDING_RESPONSE);

        //mapped address
        MappedAddressAttribute mappedAddressAttribute =
            AttributeFactory.createMappedAddressAttribute(mappedAddress);

        //source address
        SourceAddressAttribute sourceAddressAttribute =
            AttributeFactory.createSourceAddressAttribute(sourceAddress);

        //changed address
        ChangedAddressAttribute changedAddressAttribute =
            AttributeFactory.createChangedAddressAttribute(changedAddress);

        try
        {
            bindingResponse.addAttribute(mappedAddressAttribute);
            bindingResponse.addAttribute(sourceAddressAttribute);
            bindingResponse.addAttribute(changedAddressAttribute);
        }
        catch (StunException ex)
        {
            throw new StunException(StunException.INTERNAL_ERROR,
                "Failed to add a mandatory attribute to the binding response.");
        }

        return bindingResponse;
    }


    /**
     * Creates a binding error response according to the specified error code
     * and unknown attributes.
     *
     * @param errorCode the error code to encapsulate in this message
     * @param reasonPhrase a human readable description of the error
     * @param unknownAttributes a char[] array containing the ids of one or more
     * attributes that had not been recognized.
     * @throws StunException INVALID_ARGUMENTS if one or more of the given
     * parameters had an invalid value.
     *
     * @return a binding error response message containing an error code and a
     * UNKNOWN-ATTRIBUTES header
     */
    private static Response createBindingErrorResponse(char errorCode,
                                                     String reasonPhrase,
                                                     char[] unknownAttributes)
        throws StunException
    {
        Response bindingErrorResponse = new Response();
        bindingErrorResponse.setMessageType(Message.BINDING_ERROR_RESPONSE);

        //init attributes
        UnknownAttributesAttribute unknownAttributesAttribute = null;
        ErrorCodeAttribute errorCodeAttribute =
              AttributeFactory.createErrorCodeAttribute(errorCode,reasonPhrase);

        bindingErrorResponse.addAttribute(errorCodeAttribute);

        if(unknownAttributes != null)
        {
            unknownAttributesAttribute = AttributeFactory.
                             createUnknownAttributesAttribute();
            for (int i = 0; i < unknownAttributes.length; i++)
            {
                unknownAttributesAttribute.addAttributeID(unknownAttributes[i]);
            }
            bindingErrorResponse.addAttribute(unknownAttributesAttribute);
        }

        return bindingErrorResponse;
    }

    /**
     * Creates a binding error response with UNKNOWN_ATTRIBUTES error code
     * and the specified unknown attributes.
     *
     * @param unknownAttributes a char[] array containing the ids of one or more
     *  attributes that had not been recognized.
     * @throws StunException INVALID_ARGUMENTS if one or more of the given
     * parameters had an invalid value.
     * @return a binding error response message containing an error code and a
     * UNKNOWN-ATTRIBUTES header
     */
    public static Response createBindingErrorResponseUnknownAttributes(
                                                     char[] unknownAttributes)
        throws StunException
    {
        return createBindingErrorResponse(ErrorCodeAttribute.UNKNOWN_ATTRIBUTE,
                                          null,
                                          unknownAttributes);
    }

    /**
     * Creates a binding error response with UNKNOWN_ATTRIBUTES error code
     * and the specified unknown attributes and reason phrase.
     *
     * @param reasonPhrase a short description of the error.
     * @param unknownAttributes a char[] array containing the ids of one or more
     *  attributes that had not been recognized.
     * @throws StunException INVALID_ARGUMENTS if one or more of the given
     * parameters had an invalid value.
     * @return a binding error response message containing an error code and a
     * UNKNOWN-ATTRIBUTES header
     */
    public static Response createBindingErrorResponseUnknownAttributes(
                                                     String reasonPhrase,
                                                     char[] unknownAttributes)
        throws StunException
    {
        return createBindingErrorResponse(ErrorCodeAttribute.UNKNOWN_ATTRIBUTE,
                                          reasonPhrase,
                                          unknownAttributes);
    }


    /**
     * Creates a binding error response with an ERROR-CODE attribute.
     *
     * @param errorCode the error code to encapsulate in this message
     * @param reasonPhrase a human readable description of the error.
     * @throws StunException INVALID_ARGUMENTS if one or more of the given
     * parameters had an invalid value.
     *
     * @return a binding error response message containing an error code and a
     * UNKNOWN-ATTRIBUTES header
     */
    public static Response createBindingErrorResponse(char errorCode,
                                                     String reasonPhrase)
        throws StunException
    {
        return createBindingErrorResponse(errorCode, reasonPhrase, null);
    }

    /**
    * Creates a binding error response according to the specified error code.
    *
    * @param errorCode the error code to encapsulate in this message
    * attributes that had not been recognized.
    * @throws StunException INVALID_ARGUMENTS if one or more of the given
    * parameters had an invalid value.
    *
    * @return a binding error response message containing an error code and a
    * UNKNOWN-ATTRIBUTES header
    */
    public static Response createBindingErrorResponse(char errorCode) throws
        StunException
    {
        return createBindingErrorResponse(errorCode, null, null);
    }

//======================== NOT CURRENTLY SUPPORTED =============================
    public static Request createShareSecretRequest()
    {
        throw new UnsupportedOperationException(
            "Shared Secret Support is not currently impolemented");
    }

    public static Response createSharedSecretResponse()
    {
        throw new UnsupportedOperationException(
            "Shared Secret Support is not currently impolemented");
    }

    public static Response createSharedSecretErrorResponse()
    {
        throw new UnsupportedOperationException(
            "Shared Secret Support is not currently impolemented");
    }

}
