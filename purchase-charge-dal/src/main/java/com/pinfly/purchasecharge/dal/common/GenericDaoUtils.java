package com.pinfly.purchasecharge.dal.common;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import com.pinfly.purchasecharge.core.model.persistence.goods.Goods;
import com.pinfly.purchasecharge.dal.exception.PCDalException;

/**
 * General Utilities class with rules for primary keys and query names.
 * 
 * @author Xiangxiao Chen
 */
@SuppressWarnings ("all")
public class GenericDaoUtils
{
    private static final Logger logger = Logger.getLogger (GenericDaoUtils.class);

    /**
     * Checkstyle rule: utility classes should not have public constructor
     */
    private GenericDaoUtils ()
    {
    }

    /**
     * Get primary key field name from object. Looks for "id", "Id" and
     * "version".
     * 
     * @param o the object to examine
     * @return the fieldName
     */
    protected static String getPrimaryKeyFieldName (Object o)
    {
        String fieldName = null;
        Field[] fieldlist = o.getClass ().getDeclaredFields ();
        for (Field fld : fieldlist)
        {
            javax.persistence.Id id = fld.getAnnotation (javax.persistence.Id.class);
            if (null != id)
            {
                fieldName = fld.getName ();
                break;
            }
        }
        Method[] methodList = o.getClass ().getDeclaredMethods ();
        for (Method method : methodList)
        {
            javax.persistence.Id id = method.getAnnotation (javax.persistence.Id.class);
            if (null != id)
            {
                fieldName = method.getName ().substring (3);
                fieldName = Character.toLowerCase (fieldName.charAt (0)) + fieldName.substring (1);
                break;
            }
        }
        logger.info ("The result of getPrimaryKeyFieldName from " + o.getClass ().getSimpleName () + " : " + fieldName);
        return fieldName;
    }

    /**
     * Get the object type of the primary key
     * 
     * @param o the object to examine
     * @return the class type
     */
    protected static Class getPrimaryKeyFieldType (Object o)
    {
        Field[] fieldlist = o.getClass ().getDeclaredFields ();
        Class fieldType = null;
        for (Field fld : fieldlist)
        {
            javax.persistence.Id id = fld.getAnnotation (javax.persistence.Id.class);
            if (null != id)
            {
                fieldType = fld.getType ();
                break;
            }
        }
        Method[] methodList = o.getClass ().getDeclaredMethods ();
        for (Method method : methodList)
        {
            javax.persistence.Id id = method.getAnnotation (javax.persistence.Id.class);
            if (null != id)
            {
                fieldType = method.getReturnType ();
                break;
            }
        }
        return fieldType;
    }

    /**
     * Get the value of the primary key using reflection.
     * 
     * @param o the object to examine
     * @return the value as an Object
     */
    public static Object getPrimaryKey (Object o)
    {
        // Use reflection to find the first property that has the name "id" or
        // "Id"
        String fieldName = getPrimaryKeyFieldName (o);
        String getterMethod = "get" + Character.toUpperCase (fieldName.charAt (0)) + fieldName.substring (1);

        try
        {
            Method getMethod = o.getClass ().getMethod (getterMethod, (Class[]) null);
            return getMethod.invoke (o, (Object[]) null);
        }
        catch (Exception e)
        {
            logger.error ("Could not invoke method '" + getterMethod + "' on " + o.getClass ().getSimpleName ());
        }
        return null;
    }

    /**
     * Sets the primary key's value
     * 
     * @param o the object to examine
     * @param clazz the class type of the primary key
     * @param value the value of the new primary key
     */
    protected static void setPrimaryKey (Object o, Class clazz, Object value)
    {
        String fieldName = getPrimaryKeyFieldName (o);
        String setMethodName = "set" + Character.toUpperCase (fieldName.charAt (0)) + fieldName.substring (1);

        try
        {
            Method setMethod = o.getClass ().getMethod (setMethodName, clazz);
            if (value != null)
            {
                setMethod.invoke (o, value);
            }
        }
        catch (Exception e)
        {
            logger.error (MessageFormat.format ("Could not set ''{0}.{1} with value {2}", o.getClass ()
                                                                                           .getSimpleName (),
                                                fieldName, value));
        }
    }

    public static String parseSort (Class clazz, Pageable pageable, String alias)
    {
        String sortSqlStr = "";
        if (null != clazz && null != pageable && StringUtils.isNotBlank (alias))
        {
            Sort sort = pageable.getSort ();
            if (null != sort)
            {
                Iterator <Order> ordersIterator = sort.iterator ();
                while (ordersIterator.hasNext ())
                {
                    Order order = ordersIterator.next ();
                    boolean found = false;
                    String orderProperty = order.getProperty ();
                    if (findProperty (clazz, orderProperty))
                    {
                        sortSqlStr += (alias + "." + order.getProperty () + " " + order.getDirection () + ",");
                        found = true;
                        break;
                    }
                    if (!found)
                    {
                        logger.error ("Don't find the specified sort property, property=" + orderProperty);
                        throw new PCDalException ("Don't find the specified sort property, property=" + orderProperty);
                    }
                }
            }
        }

        return sortSqlStr.substring (0, sortSqlStr.lastIndexOf (","));
    }

    private static boolean findProperty (Class clazz, String property)
    {
        if (null != property && !"".equals (property.trim ()))
        {
            if (property.indexOf (".") > 0)
            {
                String firstProperty = property.substring (0, property.indexOf ("."));
                String leftProperty = property.substring (property.indexOf (".") + 1);
                Method[] methods = clazz.getMethods ();
                for (Method method : methods)
                {
                    String methodName = method.getName ();
                    if (methodName.startsWith ("get"))
                    {
                        String fieldName = methodName.substring (3);
                        fieldName = Character.toLowerCase (fieldName.charAt (0)) + fieldName.substring (1);
                        if (fieldName.equals (firstProperty))
                        {
                            Class subClass = method.getReturnType ();
                            return findProperty (subClass, leftProperty);
                        }
                    }
                    else if (methodName.startsWith ("is"))
                    {
                        String fieldName = methodName.substring (2);
                        fieldName = Character.toLowerCase (fieldName.charAt (0)) + fieldName.substring (1);
                        if (fieldName.equals (firstProperty))
                        {
                            Class subClass = method.getReturnType ();
                            return findProperty (subClass, leftProperty);
                        }
                    }
                }
            }
            else
            {
                Method[] methods = clazz.getMethods ();
                for (Method method : methods)
                {
                    String methodName = method.getName ();
                    if (methodName.startsWith ("get"))
                    {
                        String fieldName = methodName.substring (3);
                        fieldName = Character.toLowerCase (fieldName.charAt (0)) + fieldName.substring (1);
                        if (fieldName.equals (property))
                        {
                            return true;
                        }
                    }
                    else if (methodName.startsWith ("is"))
                    {
                        String fieldName = methodName.substring (2);
                        fieldName = Character.toLowerCase (fieldName.charAt (0)) + fieldName.substring (1);
                        if (fieldName.equals (property))
                        {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static void main (String[] args)
    {
        int page = 0;
        int size = 10;
        Pageable pageable = new PageRequest (page, size, new Sort ("available"));
        System.out.println (parseSort (Goods.class, pageable, "g"));
    }

}
