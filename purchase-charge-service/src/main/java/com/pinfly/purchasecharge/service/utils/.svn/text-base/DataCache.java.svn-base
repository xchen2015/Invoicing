package com.pinfly.purchasecharge.service.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.pinfly.purchasecharge.dal.common.MyGenericDao;

public class DataCache
{
    private static final Logger logger = Logger.getLogger (DataCache.class);
    private static Map <String, Object> dataCacheMap = new HashMap <String, Object> ();
    
    public static void putDataToCache (String key, Object data)
    {
        dataCacheMap.put (key, data);
    }
    
    @SuppressWarnings ("rawtypes")
    public static void refreshDataToCache (String key, MyGenericDao dao)
    {
        putDataToCache (key, dao.findAll ());
    }

    public static Object getDataFromCache (String key)
    {
        return dataCacheMap.get (key);
    }
    
    public static void printData (String key) 
    {
        System.out.println (getDataFromCache (key));
    }
    
    public static void logData (String key) 
    {
        logger.info (getDataFromCache (key));
    }

}
