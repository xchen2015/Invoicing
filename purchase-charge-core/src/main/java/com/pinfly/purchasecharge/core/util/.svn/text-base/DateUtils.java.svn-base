package com.pinfly.purchasecharge.core.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class DateUtils
{
    private static final Logger logger = Logger.getLogger (DateUtils.class);

    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String DATE_TIME_NO_SECOND_PATTERN = "yyyy-MM-dd HH:mm";
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME_MILLISECOND_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";

    public static String date2String (Date date, String sformat)
    {
        String dateStr = null;
        if (null != date)
        {
            try
            {
                DateFormat sdf = new SimpleDateFormat (sformat);
                dateStr = sdf.format (date);
            }
            catch (Exception e)
            {
                logger.error (e.getMessage ());
            }
        }
        return dateStr;
    }

    public static Date string2Date (String dateString, String sformat)
    {
        DateFormat sdf = new SimpleDateFormat (sformat);
        Date date = null;
        if (!StringUtils.isBlank (dateString))
        {
            try
            {
                date = sdf.parse (dateString);
            }
            catch (ParseException e)
            {
                logger.error (e.getMessage ());
            }
        }
        return date;
    }

    /**
     * 得到几天前的时间
     * 
     * @param d
     * @param day
     * @return
     */
    public static Date getDateBefore (Date d, int days)
    {
        Calendar now = Calendar.getInstance ();
        now.setTime (d);
        now.set (Calendar.DATE, now.get (Calendar.DATE) - days);
        return now.getTime ();
    }

    /**
     * 得到几小时前的时间
     * 
     * @param d
     * @param hours
     * @return
     */
    public static Date getHourBefore (Date d, int hours)
    {
        Calendar now = Calendar.getInstance ();
        now.setTime (d);
        now.set (Calendar.HOUR_OF_DAY, now.get (Calendar.HOUR_OF_DAY) - hours);
        return now.getTime ();
    }

    /**
     * 得到几天后的时间
     * 
     * @param d
     * @param day
     * @return
     */
    public static Date getDateAfter (Date d, int days)
    {
        Calendar now = Calendar.getInstance ();
        now.setTime (d);
        now.set (Calendar.DATE, now.get (Calendar.DATE) + days);
        return now.getTime ();
    }

    public static long getInterval (Date from, Date end)
    {
        if (null != from && null != end && end.after (from))
        {
            long fromTime = from.getTime ();
            long endTime = end.getTime ();
            long interval = (endTime - fromTime) / 1000;
            return interval;
        }
        else if (null != from && null == end)
        {
            end = new Date ();
            if (end.after (from))
            {
                return getInterval (from, end);
            }
        }
        return 0;
    }

    public static Date[] getDateRange (int year, int month)
    {
        Date[] dateRange = new Date[2];
        if (month == 0 || month == 2 || month == 4 || month == 6 || month == 7 || month == 9 || month == 11)
        {
            dateRange[0] = DateUtils.string2Date (year + "-" + (month + 1) + "-" + "01 00:00:00",
                                                  DateUtils.DATE_TIME_PATTERN);
            dateRange[1] = DateUtils.string2Date (year + "-" + (month + 1) + "-" + "31 23:59:59",
                                                  DateUtils.DATE_TIME_PATTERN);
        }
        else if (month == 3 || month == 5 || month == 8 || month == 10)
        {
            dateRange[0] = DateUtils.string2Date (year + "-" + (month + 1) + "-" + "01 00:00:00",
                                                  DateUtils.DATE_TIME_PATTERN);
            dateRange[1] = DateUtils.string2Date (year + "-" + (month + 1) + "-" + "30 23:59:59",
                                                  DateUtils.DATE_TIME_PATTERN);
        }
        else
        {
            if (((GregorianCalendar) Calendar.getInstance ()).isLeapYear (year))
            {
                dateRange[0] = DateUtils.string2Date (year + "-" + (month + 1) + "-" + "01 00:00:00",
                                                      DateUtils.DATE_TIME_PATTERN);
                dateRange[1] = DateUtils.string2Date (year + "-" + (month + 1) + "-" + "29 23:59:59",
                                                      DateUtils.DATE_TIME_PATTERN);
            }
            else
            {
                dateRange[0] = DateUtils.string2Date (year + "-" + (month + 1) + "-" + "01 00:00:00",
                                                      DateUtils.DATE_TIME_PATTERN);
                dateRange[1] = DateUtils.string2Date (year + "-" + (month + 1) + "-" + "28 23:59:59",
                                                      DateUtils.DATE_TIME_PATTERN);
            }
        }
        return dateRange;
    }
    
    public static Timestamp date2Timestamp (Date date) 
    {
        if(null != date) 
        {
            return new Timestamp (date.getTime ());
        }
        return new Timestamp (System.currentTimeMillis ());
    }
}
