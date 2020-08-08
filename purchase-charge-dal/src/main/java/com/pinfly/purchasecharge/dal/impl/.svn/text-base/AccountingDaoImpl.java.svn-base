package com.pinfly.purchasecharge.dal.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.pinfly.purchasecharge.core.model.AccountingSearchForm;
import com.pinfly.purchasecharge.core.model.persistence.Accounting;
import com.pinfly.purchasecharge.core.util.DateUtils;
import com.pinfly.purchasecharge.dal.AccountingDao;
import com.pinfly.purchasecharge.dal.common.GenericDaoUtils;
import com.pinfly.purchasecharge.dal.common.MyGenericDaoImpl;

public class AccountingDaoImpl extends MyGenericDaoImpl <Accounting, Long> implements AccountingDao
{
    public AccountingDaoImpl ()
    {
        super (Accounting.class);
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <Accounting> findByPayAccountId (long payAccountId)
    {
        String sql = "select a from Accounting a where a.payAccountId=?1";
        Query query = getEntityManager ().createQuery (sql, Accounting.class);
        query.setParameter (1, payAccountId);
        return query.getResultList ();
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <Accounting> findByType (long typeId)
    {
        String sql = "select a from Accounting a where a.type.id=?1";
        Query query = getEntityManager ().createQuery (sql, Accounting.class);
        query.setParameter (1, typeId);
        return query.getResultList ();
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <Accounting> findByUserCreated (long userCreatedBy)
    {
        String sql = "select a from Accounting a where a.userCreatedBy=?1";
        Query query = getEntityManager ().createQuery (sql, Accounting.class);
        query.setParameter (1, userCreatedBy);
        return query.getResultList ();
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <Accounting> findByTypeAndTimeRange (long typeId, Date start, Date end)
    {
        String sql = "select a from Accounting a where a.type.id=?1 and a.created between ?2 and ?3 order by a.created desc";
        Query query = getEntityManager ().createQuery (sql, Accounting.class);
        query.setParameter (1, typeId);
        query.setParameter (2, start);
        query.setParameter (3, end);
        return query.getResultList ();
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <Accounting> findByTimeRange (Timestamp start, Timestamp end)
    {
        String sql = "select a from Accounting a where a.created between ?1 and ?2 order by a.created desc";
        Query query = getEntityManager ().createQuery (sql, Accounting.class);
        query.setParameter (1, start);
        query.setParameter (2, end);
        return query.getResultList ();
    }

    @Override
    public Page <Accounting> findPageAccounting (AccountingSearchForm searchForm, Pageable pageable)
    {
        String selectCountSql = "SELECT count(e) ";
        String fromSql = "FROM Accounting e where 1=1 ";
        String conditionSql = "";
        if (null != searchForm)
        {
            if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                && 0 != searchForm.getAccountingTypeId ())
            {
                conditionSql += "and e.type.id=?1 and e.created between ?2 and ?3 ";
            }
            else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                     && null != searchForm.getAccountingModeCode ())
            {
                conditionSql += "and e.type.accountingMode=?1 and e.created between ?2 and ?3 ";
            }
            else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
            {
                conditionSql += "and e.created between ?1 and ?2 ";
            }
            else if (0 != searchForm.getAccountingTypeId ())
            {
                conditionSql += "and e.type.id=?1 ";
            }
            else if (null != searchForm.getAccountingModeCode ())
            {
                conditionSql += "and e.type.accountingMode=?1 ";
            }
        }
        Query countQuery = getEntityManager ().createQuery (selectCountSql + fromSql + conditionSql);
        if (null != searchForm)
        {
            if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                && 0 != searchForm.getAccountingTypeId ())
            {
                countQuery.setParameter (1, searchForm.getAccountingTypeId ());
                countQuery.setParameter (2, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                countQuery.setParameter (3, DateUtils.date2Timestamp (searchForm.getEndDate ()));
            }
            else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                     && null != searchForm.getAccountingModeCode ())
            {
                countQuery.setParameter (1, searchForm.getAccountingModeCode ());
                countQuery.setParameter (2, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                countQuery.setParameter (3, DateUtils.date2Timestamp (searchForm.getEndDate ()));
            }
            else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
            {
                countQuery.setParameter (1, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                countQuery.setParameter (2, DateUtils.date2Timestamp (searchForm.getEndDate ()));
            }
            else if (0 != searchForm.getAccountingTypeId ())
            {
                countQuery.setParameter (1, searchForm.getAccountingTypeId ());
            }
            else if (null != searchForm.getAccountingModeCode ())
            {
                countQuery.setParameter (1, searchForm.getAccountingModeCode ());
            }
        }
        int total = Integer.parseInt (countQuery.getResultList ().get (0).toString ());

        String querySql = "SELECT e ";
        querySql += fromSql;
        querySql += conditionSql;
        String orderBySql = " ORDER BY ";
        querySql += orderBySql;

        String sortSqlStr = "e.created desc";
        try
        {
            sortSqlStr = GenericDaoUtils.parseSort (Accounting.class, pageable, "e");
        }
        catch (Exception e)
        {
            // TODO: handle exception
        }
        querySql += sortSqlStr;

        Query query = getEntityManager ().createQuery (querySql);
        if (null != searchForm)
        {
            if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                && 0 != searchForm.getAccountingTypeId ())
            {
                query.setParameter (1, searchForm.getAccountingTypeId ());
                query.setParameter (2, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                query.setParameter (3, DateUtils.date2Timestamp (searchForm.getEndDate ()));
            }
            else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                     && null != searchForm.getAccountingModeCode ())
            {
                query.setParameter (1, searchForm.getAccountingModeCode ());
                query.setParameter (2, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                query.setParameter (3, DateUtils.date2Timestamp (searchForm.getEndDate ()));
            }
            else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
            {
                query.setParameter (1, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                query.setParameter (2, DateUtils.date2Timestamp (searchForm.getEndDate ()));
            }
            else if (0 != searchForm.getAccountingTypeId ())
            {
                query.setParameter (1, searchForm.getAccountingTypeId ());
            }
            else if (null != searchForm.getAccountingModeCode ())
            {
                query.setParameter (1, searchForm.getAccountingModeCode ());
            }
        }
        query.setFirstResult (pageable.getOffset ());
        query.setMaxResults (pageable.getPageSize ());

        @SuppressWarnings ("unchecked")
        List <Accounting> expenses = query.getResultList ();
        Page <Accounting> paymentPage = new PageImpl <Accounting> (expenses, pageable, total);
        return paymentPage;
    }

    @Override
    public float countAccounting (AccountingSearchForm searchForm)
    {
        String selectCountSql = "SELECT sum(e.money) ";
        String fromSql = "FROM Accounting e ";
        String conditionSql = "";
        if (null != searchForm)
        {
            if (null != searchForm.getAccountingModeCode () && null != searchForm.getStartDate ()
                && null != searchForm.getEndDate () && 0 != searchForm.getAccountingTypeId ())
            {
                conditionSql += "where e.type.accountingMode=?1 and e.type.id=?2 and e.created between ?3 and ?4 ";
            }
            else if (null != searchForm.getAccountingModeCode () && null != searchForm.getStartDate ()
                     && null != searchForm.getEndDate ())
            {
                conditionSql += "where e.type.accountingMode=?1 and e.created between ?2 and ?3 ";
            }
            else if (null != searchForm.getAccountingModeCode () && 0 != searchForm.getAccountingTypeId ())
            {
                conditionSql += "where e.type.accountingMode=?1 and e.type.id=?2 ";
            }
            else if (null == searchForm.getAccountingModeCode () && null != searchForm.getStartDate ()
                     && null != searchForm.getEndDate () && 0 != searchForm.getAccountingTypeId ())
            {
                conditionSql += "where e.type.id=?1 and e.created between ?2 and ?3 ";
            }
            else if (null == searchForm.getAccountingModeCode () && null != searchForm.getStartDate ()
                     && null != searchForm.getEndDate ())
            {
                conditionSql += "where e.created between ?1 and ?2 ";
            }
            else if (null == searchForm.getAccountingModeCode () && 0 != searchForm.getAccountingTypeId ())
            {
                conditionSql += "where e.type.id=?1 ";
            }
        }

        String countSql = selectCountSql + fromSql + conditionSql;
        Query countQuery = getEntityManager ().createQuery (countSql);
        if (null != searchForm)
        {
            if (null != searchForm.getAccountingModeCode () && null != searchForm.getStartDate ()
                && null != searchForm.getEndDate () && 0 != searchForm.getAccountingTypeId ())
            {
                countQuery.setParameter (1, searchForm.getAccountingModeCode ());
                countQuery.setParameter (2, searchForm.getAccountingTypeId ());
                countQuery.setParameter (3, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                countQuery.setParameter (4, DateUtils.date2Timestamp (searchForm.getEndDate ()));
            }
            else if (null != searchForm.getAccountingModeCode () && null != searchForm.getStartDate ()
                     && null != searchForm.getEndDate ())
            {
                countQuery.setParameter (1, searchForm.getAccountingModeCode ());
                countQuery.setParameter (2, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                countQuery.setParameter (3, DateUtils.date2Timestamp (searchForm.getEndDate ()));
            }
            else if (null != searchForm.getAccountingModeCode () && 0 != searchForm.getAccountingTypeId ())
            {
                countQuery.setParameter (1, searchForm.getAccountingModeCode ());
                countQuery.setParameter (2, searchForm.getAccountingTypeId ());
            }
            else if (null == searchForm.getAccountingModeCode () && null != searchForm.getStartDate ()
                     && null != searchForm.getEndDate () && 0 != searchForm.getAccountingTypeId ())
            {
                countQuery.setParameter (1, searchForm.getAccountingTypeId ());
                countQuery.setParameter (2, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                countQuery.setParameter (3, DateUtils.date2Timestamp (searchForm.getEndDate ()));
            }
            else if (null == searchForm.getAccountingModeCode () && null != searchForm.getStartDate ()
                     && null != searchForm.getEndDate ())
            {
                countQuery.setParameter (1, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                countQuery.setParameter (2, DateUtils.date2Timestamp (searchForm.getEndDate ()));
            }
            else if (null == searchForm.getAccountingModeCode () && 0 != searchForm.getAccountingTypeId ())
            {
                countQuery.setParameter (1, searchForm.getAccountingTypeId ());
            }
        }

        float sumMoney = Float.parseFloat (countQuery.getResultList ().get (0).toString ());
        return sumMoney;
    }

}
