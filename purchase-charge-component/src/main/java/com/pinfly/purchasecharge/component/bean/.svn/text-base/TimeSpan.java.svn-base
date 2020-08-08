package com.pinfly.purchasecharge.component.bean;

import java.io.Serializable;

public class TimeSpan implements Serializable
{
    private static final long serialVersionUID = 1L;

    public static final TimeSpan CUSTOMIZE = new TimeSpan ("CUSTOMIZE", "TimeFrame.customize");
    public static final TimeSpan TODAY = new TimeSpan ("TODAY", "TimeFrame.today");
    public static final TimeSpan RECENT_THREE_DAYS = new TimeSpan ("RECENT_THREE_DAYS", "TimeFrame.recent_three_days");
    public static final TimeSpan RECENT_SEVEN_DAYS = new TimeSpan ("RECENT_SEVEN_DAYS", "TimeFrame.recent_seven_days");
    public static final TimeSpan RECENT_FIFTEEN_DAYS = new TimeSpan ("RECENT_FIFTEEN_DAYS",
                                                                     "TimeFrame.recent_fifteen_days");
    public static final TimeSpan RECENT_THIRTY_DAYS = new TimeSpan ("RECENT_THIRTY_DAYS",
                                                                    "TimeFrame.recent_thirty_days");
    public static final TimeSpan CURRENT_MONTH = new TimeSpan ("CURRENT_MONTH", "TimeFrame.current_month");

    private String shortCode;
    private String messageCode;

    public TimeSpan (String shortCode, String messageCode)
    {
        this.shortCode = shortCode;
        this.messageCode = messageCode;
    }

    public String getShortCode ()
    {
        return shortCode;
    }

    public void setShortCode (String shortCode)
    {
        this.shortCode = shortCode;
    }

    public String getMessageCode ()
    {
        return messageCode;
    }

    public void setMessageCode (String messageCode)
    {
        this.messageCode = messageCode;
    }

}
