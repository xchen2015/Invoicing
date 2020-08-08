package com.cxx.purchasecharge.core.model.persistence;

import java.util.Calendar;
import java.util.Date;

import junit.framework.Assert;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import com.pinfly.purchasecharge.core.util.DateUtils;

public class BaseTest
{
    public static void main (String[] args)
    {
        // String regex = "[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}";
        String regex = "[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+";
        // String email = "xchen04@harris.com";
        // String email = "messagingUser005@jamesdev.carefx.local";
        String email = "yuanlin@test.carefx.local; messagingUser001@jamesdev.carefx.local; messagingUser003@jamesdev.carefx.local";
        // String email = "xchen04";
        // System.out.println (email.matches (regex));
        System.out.println (validateEmailAddress (email));

        Date d = new Date ();
        System.out.println (DateUtils.date2String (d, DateUtils.DATE_TIME_PATTERN));
        Date date = new Date (d.getTime () + (30 * 24 * 3600 * 1000));
        System.out.println (DateUtils.date2String (date, DateUtils.DATE_PATTERN));

        Calendar calendar = Calendar.getInstance ();
        calendar.setTimeInMillis (d.getTime ());
        calendar.set (Calendar.DATE, calendar.get (Calendar.DATE) + 30);
        System.out.println (DateUtils.date2String (calendar.getTime (), DateUtils.DATE_PATTERN));

        Date from = DateUtils.string2Date ("2014-03-31 23:59:00", DateUtils.DATE_TIME_PATTERN);
        Date end = DateUtils.string2Date ("2014-03-31 23:59:59", DateUtils.DATE_TIME_PATTERN);
        System.out.println (DateUtils.getInterval (from, end));

        from = DateUtils.string2Date ("2014-03-31 23:00:00", DateUtils.DATE_TIME_PATTERN);
        end = DateUtils.string2Date ("2014-03-31 23:59:59", DateUtils.DATE_TIME_PATTERN);
        System.out.println (DateUtils.getInterval (from, end));

        from = DateUtils.string2Date ("2014-03-31 22:00:00", DateUtils.DATE_TIME_PATTERN);
        end = DateUtils.string2Date ("2014-03-31 23:59:59", DateUtils.DATE_TIME_PATTERN);
        System.out.println (DateUtils.getInterval (from, end));

        from = DateUtils.string2Date ("2014-03-30 23:00:00", DateUtils.DATE_TIME_PATTERN);
        end = DateUtils.string2Date ("2014-03-31 23:59:59", DateUtils.DATE_TIME_PATTERN);
        System.out.println (DateUtils.getInterval (from, end));

        from = DateUtils.string2Date ("2014-01-31 23:00:00", DateUtils.DATE_TIME_PATTERN);
        end = DateUtils.string2Date ("2014-03-31 23:59:59", DateUtils.DATE_TIME_PATTERN);
        System.out.println (DateUtils.getInterval (from, end));

        from = DateUtils.string2Date ("2012-01-31 23:00:00", DateUtils.DATE_TIME_PATTERN);
        end = DateUtils.string2Date ("2014-03-31 23:59:59", DateUtils.DATE_TIME_PATTERN);
        System.out.println (DateUtils.getInterval (from, end));
    }

    public static boolean validateEmailAddress (String emails)
    {
        boolean flag = true;
        if (emails != null && emails.trim ().length () > 0)
        {
            // String regex = "[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}";
            String regex = "[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+";
            String[] emailArr = emails.split (";");
            for (String email : emailArr)
            {
                if (email != null && email.trim ().length () > 0)
                {
                    if (!email.trim ().matches (regex))
                    {
                        flag = false;
                        break;
                    }
                }
            }
        }
        return flag;
    }

    @Test
    public void testPwd ()
    {
        // Between 6-16 characters. Must include at least two alphanumeric
        // characters. Special characters @#$*/\ are not allowed
        // String regex = "((^[A-Za-z0-9]+_*[A-Za-z0-9]+$)[^ -@#$*/\\\\])*";
        String regex = "[A-Za-z0-9]+[A-Za-z0-9_]*[A-Za-z0-9]+";
        // String regex2 = "[^ @#$*/\\\\]{6,16}";
        // String str = "te324564\\";
        // String str = "te32_4564";
        // String str = "te324564";
        // String str = "te32_4564te32_4564";
        // String str = "te324564-";
        // String str = "te324564$";
        // String str = "te324564_";
        // String str = "te324564 ";
        // String str = "te324564/";
        String str = "te//324564";
        Assert.assertTrue (str.matches (regex));
        // Assert.assertTrue (str.matches (regex2));
    }

    @Test
    public void testStringUtilsIsEmpty ()
    {
        String str = " ";
        Assert.assertTrue (StringUtils.isEmpty (str));
        Assert.assertTrue (StringUtils.isBlank (str));
    }

    @Test
    public void testValiteInteger ()
    {
        String regex = "-*[0-9]*";
        String str = "3";
        Assert.assertTrue (str.matches (regex));

        String s = "05";
        System.out.println (Integer.parseInt (s));
    }

    @Test
    public void testIndexOf ()
    {
        String s = "ss1(ss2)";
        int index = s.indexOf ("(");
        int lastIndex = s.lastIndexOf (")");
        Assert.assertEquals ("ss1", s.substring (0, index));
        Assert.assertEquals ("ss2", s.substring (index + 1, lastIndex));
    }
}
