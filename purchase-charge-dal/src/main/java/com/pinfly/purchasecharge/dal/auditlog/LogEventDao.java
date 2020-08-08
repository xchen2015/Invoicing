package com.pinfly.purchasecharge.dal.auditlog;

import com.pinfly.purchasecharge.core.model.persistence.auditlog.LogEvent;
import com.pinfly.purchasecharge.dal.common.MyGenericDao;

public interface LogEventDao extends MyGenericDao <LogEvent, Long>
{
    public LogEvent findByName (String name);

}
