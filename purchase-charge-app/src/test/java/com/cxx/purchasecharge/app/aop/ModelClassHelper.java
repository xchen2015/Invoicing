package com.cxx.purchasecharge.app.aop;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelClassHelper
{
    /**
     * 通过反射Object，根据字段名称，获取字段值
     * 
     * @param fieldName
     * @param o
     * @return
     */
    public static Object getFieldValueByName (String fieldName, Object o)
    {
        try
        {
            String firstLetter = fieldName.substring (0, 1).toUpperCase ();
            String getter = "get" + firstLetter + fieldName.substring (1);
            Method method = o.getClass ().getMethod (getter, new Class[]
            {});
            Object value = method.invoke (o, new Object[]
            {});
            return value;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    /**
     * 通过反射Object，得到字段名称
     * 
     * @param o
     * @return
     */
    public static String[] getAllFieldName (Object o)
    {
        Field[] fields = o.getClass ().getDeclaredFields ();
        String[] fieldNames = new String[fields.length];

        for (int i = 0; i < fields.length; i++)
        {
            fieldNames[i] = fields[i].getName ();
        }
        return fieldNames;
    }

    /**
     * 通过反射Object，获取对象信息：类型、名称、值
     * 
     * @param o
     * @return
     */
    public static List <Map <String, Object>> getFieldsInfo (Object o)
    {
        Field[] fields = o.getClass ().getDeclaredFields ();
        List <Map <String, Object>> list = new ArrayList <Map <String, Object>> ();
        Map <String, Object> infoMap = null;
        for (int i = 0; i < fields.length; i++)
        {
            infoMap = new HashMap <String, Object> ();
            infoMap.put ("type", fields[i].getType ().getSimpleName ());
            infoMap.put ("name", fields[i].getName ());
            infoMap.put ("value", getFieldValueByName (fields[i].getName (), o));
            list.add (infoMap);
        }
        return list;
    }
}
