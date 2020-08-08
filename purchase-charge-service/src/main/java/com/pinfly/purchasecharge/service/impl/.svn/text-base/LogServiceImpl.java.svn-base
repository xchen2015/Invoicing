package com.pinfly.purchasecharge.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pinfly.purchasecharge.core.model.LogSearchForm;
import com.pinfly.purchasecharge.core.model.persistence.auditlog.Log;
import com.pinfly.purchasecharge.core.model.persistence.auditlog.LogEvent;
import com.pinfly.purchasecharge.dal.auditlog.LogDao;
import com.pinfly.purchasecharge.dal.auditlog.LogEventDao;
import com.pinfly.purchasecharge.service.LogService;
import com.pinfly.purchasecharge.service.exception.PcServiceException;
import com.pinfly.purchasecharge.service.utils.DataCache;

public class LogServiceImpl implements LogService
{
    private LogEventDao logEventDao;
    private LogDao logDao;

    @Override
    public LogEvent saveLogEvent (LogEvent logEvent) throws PcServiceException
    {
        if(null != logEvent) 
        {
            logEvent = logEventDao.save (logEvent);
            DataCache.refreshDataToCache (LogEvent.class.getName (), logEventDao);
            return logEvent;
        }
        return null;
    }

    @Override
    public List <LogEvent> saveLogEvent (List <LogEvent> logEvents) throws PcServiceException
    {
        if(CollectionUtils.isNotEmpty (logEvents)) 
        {
            logEvents = (List <LogEvent>) logEventDao.save (logEvents);
            DataCache.refreshDataToCache (LogEvent.class.getName (), logEventDao);
            return logEvents;
        }
        return null;
    }

    @Override
    @SuppressWarnings ("unchecked")
    public LogEvent getLogEvent (long eventId)
    {
        String key = LogEvent.class.getName ();
        Object data = DataCache.getDataFromCache (key);
        if(null != data) 
        {
            List <LogEvent> logEvents = (List <LogEvent>) data;
            for(LogEvent logEvent : logEvents) 
            {
                if(eventId == logEvent.getId ()) 
                {
                    return logEvent;
                }
            }
        }
        return logEventDao.findOne (eventId);
    }

    @Override
    @SuppressWarnings ("unchecked")
    public LogEvent getLogEvent (String name)
    {
        if(StringUtils.isNotBlank (name)) 
        {
            String key = LogEvent.class.getName ();
            Object data = DataCache.getDataFromCache (key);
            if(null != data) 
            {
                List <LogEvent> logEvents = (List <LogEvent>) data;
                for(LogEvent logEvent : logEvents) 
                {
                    if(name.equals (logEvent.getName ())) 
                    {
                        return logEvent;
                    }
                }
            }
            return logEventDao.findByName (name);
        }
        return null;
    }

    @Override
    public void deleteLogEvent (long logEventId) throws PcServiceException
    {
        logEventDao.delete (logEventId);
        DataCache.refreshDataToCache (LogEvent.class.getName (), logEventDao);
    }

    @Override
    public void deleteLogEvent (String name) throws PcServiceException
    {
        LogEvent logEvent = logEventDao.findByName (name);
        if (null != logEvent)
        {
            logEventDao.delete (logEvent);
            DataCache.refreshDataToCache (LogEvent.class.getName (), logEventDao);
        }
    }

    @Override
    public void deleteLogEvent (List <LogEvent> logEvents) throws PcServiceException
    {
        logEventDao.delete (logEvents);
        DataCache.refreshDataToCache (LogEvent.class.getName (), logEventDao);
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <LogEvent> getAll ()
    {
        String key = LogEvent.class.getName ();
        Object data = DataCache.getDataFromCache (key);
        List <LogEvent> logEvents = new ArrayList <LogEvent> ();
        if(null == data) 
        {
            logEvents = (List <LogEvent>) logEventDao.findAll ();
            DataCache.putDataToCache (key, logEvents);
        }
        else 
        {
            logEvents = (List <LogEvent>) data;
        }
        return logEvents;
    }

    @Override
    public void log (Log log) throws PcServiceException
    {
        if (null != log && null != log.getEvent () && StringUtils.isNotBlank (log.getEvent ().getName ())
            && 0 != log.getUserCreate ())
        {
            if (null == log.getDateCreate ())
            {
                log.setDateCreate (new Timestamp (System.currentTimeMillis ()));
            }

            LogEvent logEvent = getLogEvent (log.getEvent ().getName ());
            if (null != logEvent)
            {
                if (logEvent.isEnabled ())
                {
                    log.setEvent (logEvent);
                    logDao.save (log);
                }
            }
            else
            {
                logEvent = saveLogEvent (log.getEvent ());
                log.setEvent (logEvent);
                log (log);
            }
        }
    }

    @Override
    public void log (String logEventName, long userCreate, String comment) throws PcServiceException
    {
        log (new Log (new LogEvent (logEventName), userCreate, comment));
    }

    @Override
    public void log (LogEvent logEvent, long userCreate, String comment) throws PcServiceException
    {
        log (new Log (logEvent, userCreate, comment));
    }

    @Override
    public Page <Log> getPagedLog (Pageable pageable, LogSearchForm searchForm)
    {
        return logDao.findBySearchForm (pageable, searchForm);
    }

    @Override
    public List <Log> getOldLog (Date date)
    {
        return logDao.findOldLog (new Timestamp (date.getTime ()));
    }

    @Override
    public void deleteLog (List <Log> logs) throws PcServiceException
    {
        logDao.delete (logs);
    }

    public void setLogEventDao (LogEventDao logEventDao)
    {
        this.logEventDao = logEventDao;
    }

    public void setLogDao (LogDao logDao)
    {
        this.logDao = logDao;
    }

}
