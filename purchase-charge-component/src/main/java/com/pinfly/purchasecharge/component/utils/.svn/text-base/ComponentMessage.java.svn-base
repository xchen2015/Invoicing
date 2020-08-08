package com.pinfly.purchasecharge.component.utils;

import java.util.HashMap;
import java.util.Map;

public class ComponentMessage
{
    private static final Map <String, String> messageMap = new HashMap <String, String> ();

    private String defaultMessage;
    private String i18nMessageCode;

    private ComponentMessage (String defaultMessage, String i18nMessageCode)
    {
        this.defaultMessage = defaultMessage;
        this.i18nMessageCode = i18nMessageCode;
    }

    public String getDefaultMessage ()
    {
        return defaultMessage;
    }

    public String getI18nMessageCode ()
    {
        return i18nMessageCode;
    }

    public static ComponentMessage createMessage (String defaultMessage, String i18nMessageCode)
    {
        ComponentMessage message = null;
        if (messageMap.containsKey (defaultMessage))
        {
            message = new ComponentMessage (defaultMessage, messageMap.get (defaultMessage));
        }
        else
        {
            message = new ComponentMessage (defaultMessage, i18nMessageCode);
            messageMap.put (defaultMessage, i18nMessageCode);
        }
        return message;
    }
}
