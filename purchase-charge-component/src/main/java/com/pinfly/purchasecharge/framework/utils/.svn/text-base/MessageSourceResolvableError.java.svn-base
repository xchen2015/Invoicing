package com.pinfly.purchasecharge.framework.utils;

import java.io.Serializable;
import org.springframework.context.MessageSourceResolvable;

/**
 * Class that extends RuntimeException and implements MessageSourceResolvable.
 * This class can be used as is or as a base class for exceptions that do not
 * need to be declared or caught and use the standard jsp page for displaying
 * the error to the user.
 * <p>
 * Note that although the name of this class indicates it is an error, it does
 * not extend the java Error class.
 * 
 * @see MessageSourceResolvableException
 * @see MessageSourceResolvable
 */
public class MessageSourceResolvableError extends RuntimeException implements MessageSourceResolvable, Serializable
{
    private static final long serialVersionUID = 1L;
    private final String[] m_msgCode;
    private final Object[] m_arguments;

    /**
     * Create an instance with the specified msgCode. The msgCode must resolve
     * to a message using the default message source.
     * 
     * @param msgCode the msgCode that identifies the message
     */
    public MessageSourceResolvableError (String msgCode)
    {
        this (msgCode, new Object[0]);
    }

    /**
     * Create an instance with the specified msgCode and arguments. The msgCode
     * must resolve to a message using the default message source. If the
     * message has argument placeholders, the passed in arguments will be used.
     * 
     * @param msgCode the msgCode that identifies the message
     * @param args any arguments that are to be substituted into the message
     */
    public MessageSourceResolvableError (String msgCode, Object[] args)
    {
        super (msgCode);
        m_msgCode = new String[]
        { msgCode };
        m_arguments = args;
    }

    /**
     * Create an instance with the specified msgCode and cause. The msgCode must
     * resolve to a message using the default message source.
     * 
     * @param msgCode the msgCode that identifies the message
     * @param cause the exception that caused this exception
     */
    public MessageSourceResolvableError (String msgCode, Throwable cause)
    {
        this (msgCode, new Object[0], cause);
    }

    /**
     * Create an instance with the specified msgCode, arguments and cause. The
     * msgCode must resolve to a message using the default message source. If
     * the message has argument placeholders, the passed in arguments will be
     * used.
     * 
     * @param msgCode the msgCode that identifies the message
     * @param args any arguments that are to be substituted into the message
     * @param cause the exception that caused this exception
     */
    public MessageSourceResolvableError (String msgCode, Object[] args, Throwable cause)
    {
        this (msgCode, args);
        initCause (cause);
    }

    //
    // MessageSourceResolvable methods
    //

    /** Get the message code */
    public String[] getCodes ()
    {
        return m_msgCode;
    }

    /** Get the message arguments */
    public Object[] getArguments ()
    {
        return m_arguments;
    }

    /**
     * Return a default message to be used if the message code cannot be
     * resolved. The default message identifies the exception class and the
     * message code.
     */
    public String getDefaultMessage ()
    {
        return "Exception type: " + this.getClass ().getName () + " with message key of "
               + String.valueOf (m_msgCode[0]);
    }

}
