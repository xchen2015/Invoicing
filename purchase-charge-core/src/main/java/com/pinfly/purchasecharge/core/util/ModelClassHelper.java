package com.pinfly.purchasecharge.core.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

public class ModelClassHelper
{
    private static Logger logger = Logger.getLogger (ModelClassHelper.class);

    public static final String FIELD_NAME = "fieldName";
    public static final String FIELD_TYPE = "fieldType";
    public static final String FIELD_VALUE = "fieldValue";

    /**
     * 通过反射Object，根据field名称和对象，获取field值
     * 
     * @param fieldName
     * @param o
     * @return
     */
    @SuppressWarnings ("all")
    public static Object getFieldValue (String fieldName, Object o)
    {
        try
        {
            if (StringUtils.isNotBlank (fieldName) && null != o)
            {
                PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor (o.getClass (), fieldName);
                if (null != propertyDescriptor)
                {
                    Method readMethod = propertyDescriptor.getReadMethod ();
                    Object value = readMethod.invoke (o, new Object[]
                    {});
                    return value;
                }
            }
        }
        catch (Exception e)
        {
            logger.warn (e.getMessage ());
        }
        return null;
    }

    /**
     * 通过反射Object，得到field名称
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
     * 通过反射Object，获取field信息：类型、名称、值
     * 
     * @param o
     * @return
     */
    public static List <Map <String, Object>> getFieldsInfo (Object o)
    {
        List <Map <String, Object>> list = new ArrayList <Map <String, Object>> ();
        if (null != o)
        {
            Field[] fields = o.getClass ().getDeclaredFields ();
            Map <String, Object> infoMap = null;
            for (int i = 0; i < fields.length; i++)
            {
                infoMap = new HashMap <String, Object> ();
                infoMap.put (FIELD_NAME, fields[i].getName ());
                infoMap.put (FIELD_TYPE, fields[i].getType ());
                infoMap.put (FIELD_VALUE, getFieldValue (fields[i].getName (), o));
                list.add (infoMap);
            }
        }
        return list;
    }

}
