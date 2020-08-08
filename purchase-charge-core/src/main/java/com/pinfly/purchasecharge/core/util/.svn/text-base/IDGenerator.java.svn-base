package com.pinfly.purchasecharge.core.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 *
 */
public class IDGenerator
{
    private static Long lastValue = Long.parseLong ("0");
    private static Long lastTimeValue = new Date ().getTime ();
    private static SimpleDateFormat timeFormat = new SimpleDateFormat ("yyMMddHHmmss");

    static
    {
        lastValue = Long.parseLong (timeFormat.format (lastTimeValue) + "00001");
    }

    public synchronized static long getIdentityID ()
    {
        synchronized (IDGenerator.class)
        {
            synchronized (lastValue)
            {
                synchronized (lastTimeValue)
                {
                    Date curDate = new Date ();
                    long curTimeValue = curDate.getTime ();

                    if (curTimeValue - lastTimeValue < 1000)
                    {
                        return ++lastValue;
                    }
                    else
                    {
                        lastTimeValue = curTimeValue;
                        lastValue = Long.parseLong (timeFormat.format (lastTimeValue) + "00001");
                        return lastValue;
                    }
                }
            }
        }
    }

    public static String getUUID ()
    {
        return UUID.randomUUID ().toString ();
    }

}
