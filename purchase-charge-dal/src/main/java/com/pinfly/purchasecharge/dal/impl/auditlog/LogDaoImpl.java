package com.pinfly.purchasecharge.dal.impl.auditlog;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.pinfly.purchasecharge.core.model.LogSearchForm;
import com.pinfly.purchasecharge.core.model.persistence.auditlog.Log;
import com.pinfly.purchasecharge.core.util.DateUtils;
import com.pinfly.purchasecharge.dal.auditlog.LogDao;
import com.pinfly.purchasecharge.dal.common.GenericDaoUtils;
import com.pinfly.purchasecharge.dal.common.MyGenericDaoImpl;

public class LogDaoImpl extends MyGenericDaoImpl <Log, Long> implements LogDao
{
    private static final Logger logger = Logger.getLogger (LogDaoImpl.class);

    public LogDaoImpl ()
    {
        super (Log.class);
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <Log> findOldLog (Timestamp date)
    {
        String sql = "select o from Log o where o.dateCreate < ?1";
        Query query = getEntityManager ().createQuery (sql, Log.class);
        query.setParameter (1, date);
        return query.getResultList ();
    }

    @Override
    public Page <Log> findBySearchForm (Pageable pageable, LogSearchForm searchForm)
    {
        String pageableLog = "offSet:" + pageable.getOffset () + ",pageNumber:" + pageable.getPageNumber ()
                             + ",pageSize:" + pageable.getPageSize () + ",sort:" + pageable.getSort ();
        logger.debug ("Find paging log by fuzzy, pageable: (" + pageableLog + "), searchForm: " + searchForm);
        Page <Log> logPage;
        String selectCountSql = "SELECT count(o) ";
        String fromSql = "FROM Log o ";
        String conditionSql = "";
        if (null != searchForm)
        {
            if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                && 0 != searchForm.getLogEventId () && 0 != searchForm.getUserCreate ())
            {
                conditionSql += "where o.userCreate = ?1 and o.event.id = ?2 and o.dateCreate between ?3 and ?4 ";
            }
            else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                     && 0 != searchForm.getLogEventId ())
            {
                conditionSql += "where o.event.id = ?1 and o.dateCreate between ?2 and ?3 ";
            }
            else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                     && 0 != searchForm.getUserCreate ())
            {
                conditionSql += "where o.userCreate = ?1 and o.dateCreate between ?2 and ?3 ";
            }
            else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
            {
                conditionSql += "where o.dateCreate between ?1 and ?2 ";
            }
            else if (0 != searchForm.getLogEventId () && 0 != searchForm.getUserCreate ())
            {
                conditionSql += "where o.userCreate = ?1 and o.event.id = ?2 ";
            }
            else if (0 != searchForm.getLogEventId ())
            {
                conditionSql += "where o.event.id = ?1 ";
            }
            else if (0 != searchForm.getUserCreate ())
            {
                conditionSql += "where o.userCreate = ?1 ";
            }
        }

        Query countQuery = getEntityManager ().createQuery (selectCountSql + fromSql + conditionSql);
        if (null != searchForm)
        {
            if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                && 0 != searchForm.getLogEventId () && 0 != searchForm.getUserCreate ())
            {
                countQuery.setParameter (1, searchForm.getUserCreate ());
                countQuery.setParameter (2, searchForm.getLogEventId ());
                countQuery.setParameter (3, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                countQuery.setParameter (4, DateUtils.date2Timestamp (searchForm.getEndDate ()));
            }
            else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                     && 0 != searchForm.getLogEventId ())
            {
                countQuery.setParameter (1, searchForm.getLogEventId ());
                countQuery.setParameter (2, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                countQuery.setParameter (3, DateUtils.date2Timestamp (searchForm.getEndDate ()));
            }
            else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                     && 0 != searchForm.getUserCreate ())
            {
                countQuery.setParameter (1, searchForm.getUserCreate ());
                countQuery.setParameter (2, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                countQuery.setParameter (3, DateUtils.date2Timestamp (searchForm.getEndDate ()));
            }
            else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
            {
                countQuery.setParameter (1, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                countQuery.setParameter (2, DateUtils.date2Timestamp (searchForm.getEndDate ()));
            }
            else if (0 != searchForm.getLogEventId () && 0 != searchForm.getUserCreate ())
            {
                countQuery.setParameter (1, searchForm.getUserCreate ());
                countQuery.setParameter (2, searchForm.getLogEventId ());
            }
            else if (0 != searchForm.getLogEventId ())
            {
                countQuery.setParameter (1, searchForm.getLogEventId ());
            }
            else if (0 != searchForm.getUserCreate ())
            {
                countQuery.setParameter (1, searchForm.getUserCreate ());
            }
        }
        int total = Integer.parseInt (countQuery.getResultList ().get (0).toString ());

        String querySql = "SELECT o ";
        querySql += fromSql;
        querySql += conditionSql;
        String orderBySql = " ORDER BY ";
        querySql += orderBySql;
        String sortSqlStr = GenericDaoUtils.parseSort (Log.class, pageable, "o");
        if (!"".endsWith (sortSqlStr))
        {
            querySql += sortSqlStr;
        }
        else
        {
            querySql += "o.dateCreate desc";
        }
        Query query = getEntityManager ().createQuery (querySql);
        if (null != searchForm)
        {
            if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                && 0 != searchForm.getLogEventId () && 0 != searchForm.getUserCreate ())
            {
                query.setParameter (1, searchForm.getUserCreate ());
                query.setParameter (2, searchForm.getLogEventId ());
                query.setParameter (3, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                query.setParameter (4, DateUtils.date2Timestamp (searchForm.getEndDate ()));
            }
            else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                     && 0 != searchForm.getLogEventId ())
            {
                query.setParameter (1, searchForm.getLogEventId ());
                query.setParameter (2, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                query.setParameter (3, DateUtils.date2Timestamp (searchForm.getEndDate ()));
            }
            else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                     && 0 != searchForm.getUserCreate ())
            {
                query.setParameter (1, searchForm.getUserCreate ());
                query.setParameter (2, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                query.setParameter (3, DateUtils.date2Timestamp (searchForm.getEndDate ()));
            }
            else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
            {
                query.setParameter (1, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                query.setParameter (2, DateUtils.date2Timestamp (searchForm.getEndDate ()));
            }
            else if (0 != searchForm.getLogEventId () && 0 != searchForm.getUserCreate ())
            {
                query.setParameter (1, searchForm.getUserCreate ());
                query.setParameter (2, searchForm.getLogEventId ());
            }
            else if (0 != searchForm.getLogEventId ())
            {
                query.setParameter (1, searchForm.getLogEventId ());
            }
            else if (0 != searchForm.getUserCreate ())
            {
                query.setParameter (1, searchForm.getUserCreate ());
            }
        }

        query.setFirstResult (pageable.getOffset ());
        query.setMaxResults (pageable.getPageSize ());

        @SuppressWarnings ("unchecked")
        List <Log> logList = query.getResultList ();
        logPage = new PageImpl <Log> (logList, pageable, total);
        return logPage;
    }

}
