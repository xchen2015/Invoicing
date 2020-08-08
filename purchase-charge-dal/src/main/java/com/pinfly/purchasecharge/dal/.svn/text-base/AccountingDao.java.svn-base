package com.pinfly.purchasecharge.dal;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pinfly.purchasecharge.core.model.AccountingSearchForm;
import com.pinfly.purchasecharge.core.model.persistence.Accounting;
import com.pinfly.purchasecharge.dal.common.MyGenericDao;

public interface AccountingDao extends MyGenericDao <Accounting, Long>
{
    public List <Accounting> findByPayAccountId (long payAccountId);

    public List <Accounting> findByType (long typeId);

    public List <Accounting> findByUserCreated (long userCreatedBy);

    public List <Accounting> findByTypeAndTimeRange (long typeId, Date start, Date end);

    public List <Accounting> findByTimeRange (Timestamp start, Timestamp end);

    public Page <Accounting> findPageAccounting (AccountingSearchForm searchForm, Pageable pageable);

    public float countAccounting (AccountingSearchForm searchForm);

}
