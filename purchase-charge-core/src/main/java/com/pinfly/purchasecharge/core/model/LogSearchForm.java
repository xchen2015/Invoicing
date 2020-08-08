package com.pinfly.purchasecharge.core.model;

public class LogSearchForm extends SearchForm
{
    private static final long serialVersionUID = 1L;

    private long userCreate;
    private long logEventId;

    public long getUserCreate ()
    {
        return userCreate;
    }

    public void setUserCreate (long userCreate)
    {
        this.userCreate = userCreate;
    }

    public long getLogEventId ()
    {
        return logEventId;
    }

    public void setLogEventId (long logEventId)
    {
        this.logEventId = logEventId;
    }

}
