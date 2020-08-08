package com.pinfly.purchasecharge.dal.exception;

public class PCDalException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    public PCDalException ()
    {
        super ();
    }

    public PCDalException (String message)
    {
        super (message);
    }

    public PCDalException (Throwable cause)
    {
        super (cause);
    }

    public PCDalException (String message, Throwable cause)
    {
        super (message, cause);
    }
}
