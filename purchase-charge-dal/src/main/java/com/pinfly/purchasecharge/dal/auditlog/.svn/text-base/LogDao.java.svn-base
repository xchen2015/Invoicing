package com.pinfly.purchasecharge.dal.auditlog;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pinfly.purchasecharge.core.model.LogSearchForm;
import com.pinfly.purchasecharge.core.model.persistence.auditlog.Log;
import com.pinfly.purchasecharge.dal.common.MyGenericDao;

public interface LogDao extends MyGenericDao <Log, Long>
{
    public List <Log> findOldLog (Timestamp date);

    public Page <Log> findBySearchForm (Pageable pageable, LogSearchForm searchForm);
}
