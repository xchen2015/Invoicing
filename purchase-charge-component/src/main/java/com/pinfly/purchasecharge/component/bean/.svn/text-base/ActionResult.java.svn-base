package com.pinfly.purchasecharge.component.bean;

import java.io.Serializable;

public class ActionResult implements Serializable
{
    private static final long serialVersionUID = 1L;

    private ActionResultStatus status;
    private String message = "";

    protected ActionResult ()
    {
    }

    public ActionResultStatus getStatus ()
    {
        return status;
    }

    public void setStatus (ActionResultStatus status)
    {
        this.status = status;
    }

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public static ActionResultBuilder withStatus (ActionResultStatus status)
    {
        createActionResult ().withStatus (status);
        return builder;
    }

    public static ActionResultBuilder withMessage (String message)
    {
        createActionResult ().withMessage (message);
        return builder;
    }

    public static ActionResultBuilder ok ()
    {
        return withStatus (ActionResultStatus.OK);
    }

    public static ActionResultBuilder serverError ()
    {
        return withStatus (ActionResultStatus.INTERNAL_SERVER_ERROR);
    }

    public static ActionResultBuilder badRequest ()
    {
        return withStatus (ActionResultStatus.BAD_REQUEST);
    }

    public static ActionResultBuilder unauthorized ()
    {
        return withStatus (ActionResultStatus.UNAUTHORIZED);
    }
    
    public static ActionResultBuilder forbidden ()
    {
        return withStatus (ActionResultStatus.FORBIDDEN);
    }

    public static ActionResultBuilder notAcceptable ()
    {
        return withStatus (ActionResultStatus.NOT_ACCEPTABLE);
    }

    public int getStatusCode ()
    {
        return status.getStatusCode ();
    }

    private static ActionResultBuilder builder;

    public static ActionResultBuilder createActionResult ()
    {
        builder = ActionResultBuilder.newInstance ();
        return builder;
    }

    public static class ActionResultBuilder
    {
        private ActionResult actionResult;

        protected ActionResultBuilder ()
        {
            actionResult = new ActionResult ();
        }

        public ActionResultBuilder withStatus (ActionResultStatus status)
        {
            actionResult.setStatus (status);
            return builder;
        }

        public ActionResultBuilder withMessage (String message)
        {
            actionResult.setMessage (message);
            return builder;
        }

        protected static ActionResultBuilder newInstance ()
        {
            return new ActionResultBuilder ();
        }

        public ActionResult build ()
        {
            return actionResult;
        }
    }

    public enum ActionResultStatus
    {
        OK (200, "OK"), INTERNAL_SERVER_ERROR (500, "Internal Server Error"), BAD_REQUEST (400, "Bad Request"), NOT_ACCEPTABLE (
                                                                                                                                406,
                                                                                                                                "Not Acceptable"), UNAUTHORIZED (
                                                                                                                                                                 401,
                                                                                                                                                                 "Unauthorized"), FORBIDDEN (
                                                                                                                                                                                             403,
                                                                                                                                                                                             "Forbidden");

        private final int code;
        private final String reason;

        ActionResultStatus (final int statusCode, final String reasonPhrase)
        {
            this.code = statusCode;
            this.reason = reasonPhrase;
        }

        public int getStatusCode ()
        {
            return code;
        }

        public String getReasonPhrase ()
        {
            return toString ();
        }

        @Override
        public String toString ()
        {
            return reason;
        }

        /**
         * Convert a numerical status code into the corresponding Status
         * 
         * @param statusCode the numerical status code
         * @return the matching Status or null is no matching Status is defined
         */
        public static ActionResultStatus fromStatusCode (final int statusCode)
        {
            for (ActionResultStatus s : ActionResultStatus.values ())
            {
                if (s.code == statusCode)
                {
                    return s;
                }
            }
            return null;
        }
    }

}
