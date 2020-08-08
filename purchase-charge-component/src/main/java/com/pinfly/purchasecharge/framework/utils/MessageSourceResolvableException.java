package com.pinfly.purchasecharge.framework.utils;

import org.springframework.context.MessageSourceResolvable;

/**
 * Class that extends Exception and implements MessageSourceResolvable. This
 * class can be used as is or as a base class for exceptions thatneed to be
 * declared and caught and use the standard jsp page for displaying the error to
 * the user.
 * 
 * @see MessageSourceResolvable
 */
public class MessageSourceResolvableException extends Exception implements MessageSourceResolvable
{
    private static final long serialVersionUID = 1L;

    private final String[] m_msgCode;
    private final Object[] m_arguments;

    /**
     * Create a instance with the specified msgCode. The msgCode must resolve to
     * a message using the default message source.
     * 
     * @param msgCode the msgCode that identifies the message
     */
    public MessageSourceResolvableException (String msgCode)
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
    public MessageSourceResolvableException (String msgCode, Object[] args)
    {
        super (msgCode);
        m_msgCode = new String[]
        { msgCode };
        m_arguments = args;
    }

    /**
     * Create a instance with the specified msgCode and cause. The msgCode must
     * resolve to a message using the default message source.
     * 
     * @param msgCode the msgCode that identifies the message
     * @param cause the exception that caused this exception
     */
    public MessageSourceResolvableException (String msgCode, Throwable cause)
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
    public MessageSourceResolvableException (String msgCode, Object[] args, Throwable cause)
    {
        this (msgCode, args);
        initCause (cause);
    }

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
