package com.pinfly.purchasecharge.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pinfly.purchasecharge.core.model.LogSearchForm;
import com.pinfly.purchasecharge.core.model.persistence.auditlog.Log;
import com.pinfly.purchasecharge.core.model.persistence.auditlog.LogEvent;
import com.pinfly.purchasecharge.service.exception.PcServiceException;

public interface LogService
{
    LogEvent saveLogEvent (LogEvent logEvent) throws PcServiceException;

    List <LogEvent> saveLogEvent (List <LogEvent> logEvents) throws PcServiceException;

    LogEvent getLogEvent (long eventId);

    LogEvent getLogEvent (String name);

    void deleteLogEvent (long logEventId) throws PcServiceException;

    void deleteLogEvent (String name) throws PcServiceException;

    void deleteLogEvent (List <LogEvent> logEvents) throws PcServiceException;

    List <LogEvent> getAll ();

    void log (Log log) throws PcServiceException;

    void log (String logEventName, long userCreate, String comment) throws PcServiceException;

    void log (LogEvent logEvent, long userCreate, String comment) throws PcServiceException;

    Page <Log> getPagedLog (Pageable pageable, LogSearchForm searchForm);

    /**
     * 得到指定时间之前的操作日志
     * 
     * @param date
     * @return
     */
    List <Log> getOldLog (Date date);

    void deleteLog (List <Log> logs) throws PcServiceException;
}
