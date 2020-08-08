package com.pinfly.purchasecharge.dal.impl.auditlog;

import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.pinfly.purchasecharge.core.model.persistence.auditlog.LogEvent;
import com.pinfly.purchasecharge.dal.auditlog.LogEventDao;
import com.pinfly.purchasecharge.dal.common.MyGenericDaoImpl;

public class LogEventDaoImpl extends MyGenericDaoImpl <LogEvent, Long> implements LogEventDao
{
    private static final Logger logger = Logger.getLogger (LogEventDaoImpl.class);

    public LogEventDaoImpl ()
    {
        super (LogEvent.class);
    }

    @Override
    public LogEvent findByName (String name)
    {
        logger.debug (name);
        String sql = "select et from LogEvent et where et.name=?1";
        Query query = getEntityManager ().createQuery (sql, LogEvent.class);
        query.setParameter (1, name);
        return CollectionUtils.isNotEmpty (query.getResultList ()) ? (LogEvent) query.getResultList ().get (0) : null;
    }
}
