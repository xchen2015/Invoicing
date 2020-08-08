package com.pinfly.purchasecharge.component.bean.auditlog;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class LogEventName
{
    private static final Map <String, String> eventMap = new HashMap <String, String> ();

    private String defaultEventName;
    private String i18nEventNameCode;
    private Locale locale;

    private LogEventName (String defaultEventName, String i18nEventNameCode, Locale locale)
    {
        this.defaultEventName = defaultEventName;
        this.i18nEventNameCode = i18nEventNameCode;
        this.locale = locale;
    }

    public static String createEventName (String defaultEventName, String i18nEventNameCode, Locale locale)
    {
        LogEventName event = null;
        if (eventMap.containsKey (defaultEventName))
        {
            event = new LogEventName (defaultEventName, eventMap.get (defaultEventName), locale);
        }
        else
        {
            event = new LogEventName (defaultEventName, i18nEventNameCode, locale);
            eventMap.put (defaultEventName, i18nEventNameCode);
        }
        return event.getEventName ();
    }

    public String getEventName ()
    {
        String message = this.defaultEventName;
        try
        {
            message = getMessage (this.locale, this.i18nEventNameCode);
        }
        catch (Exception e)
        {
        }
        return message;
    }

    /**
     * Returns the localized text of a key.
     */
    private static String getMessage (Locale loc, String key, Object... pars)
    {
        ResourceBundle res = ResourceBundle.getBundle (LogEventName.class.getName (), loc);
        String msg = res.getString (key);
        return new MessageFormat (msg, loc).format (pars);
    }

}
