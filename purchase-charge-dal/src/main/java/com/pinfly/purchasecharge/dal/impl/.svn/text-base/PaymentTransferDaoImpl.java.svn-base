package com.pinfly.purchasecharge.dal.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.pinfly.purchasecharge.core.model.PaymentTransferSearchForm;
import com.pinfly.purchasecharge.core.model.persistence.PaymentTransfer;
import com.pinfly.purchasecharge.core.util.DateUtils;
import com.pinfly.purchasecharge.dal.PaymentTransferDao;
import com.pinfly.purchasecharge.dal.common.MyGenericDaoImpl;

public class PaymentTransferDaoImpl extends MyGenericDaoImpl <PaymentTransfer, Long> implements PaymentTransferDao
{

    public PaymentTransferDaoImpl ()
    {
        super (PaymentTransfer.class);
    }

    @Override
    @SuppressWarnings ("unchecked")
    public Page <PaymentTransfer> findBySearchForm (Pageable pageable, PaymentTransferSearchForm searchForm)
    {
        int total = 0;
        List <PaymentTransfer> content = new ArrayList <PaymentTransfer> ();
        if (0 != searchForm.getTargetAccount () && null != searchForm.getStartDate ()
                && null != searchForm.getEndDate () && null != searchForm.getTransferTypeCode ())
        {
            String countSql = "select count(pat) from PaymentTransfer pat where pat.targetAccount.id = ?1 and pat.transferTypeCode = ?2 and pat.dateCreated between ?3 and ?4";
            Query countQuery = getEntityManager ().createQuery (countSql);
            countQuery.setParameter (1, searchForm.getTargetAccount ());
            countQuery.setParameter (2, searchForm.getTransferTypeCode ());
            countQuery.setParameter (3, DateUtils.date2Timestamp (searchForm.getStartDate ()));
            countQuery.setParameter (4, DateUtils.date2Timestamp (searchForm.getEndDate ()));
            total = Integer.parseInt (countQuery.getResultList ().get (0).toString ());

            String querySql = "select pat from PaymentTransfer pat where pat.targetAccount.id = ?1 and pat.transferTypeCode = ?2 and pat.dateCreated between ?3 and ?4 order by pat.dateCreated desc";
            Query query = getEntityManager ().createQuery (querySql, PaymentTransfer.class);
            query.setParameter (1, searchForm.getTargetAccount ());
            query.setParameter (2, searchForm.getTransferTypeCode ());
            query.setParameter (3, DateUtils.date2Timestamp (searchForm.getStartDate ()));
            query.setParameter (4, DateUtils.date2Timestamp (searchForm.getEndDate ()));

            query.setFirstResult (pageable.getOffset ());
            query.setMaxResults (pageable.getPageSize ());
            content = query.getResultList ();
        }
        else if (0 != searchForm.getTargetAccount () && null != searchForm.getStartDate ()
            && null != searchForm.getEndDate ())
        {
            String countSql = "select count(pat) from PaymentTransfer pat where pat.targetAccount.id = ?1 and pat.dateCreated between ?2 and ?3";
            Query countQuery = getEntityManager ().createQuery (countSql);
            countQuery.setParameter (1, searchForm.getTargetAccount ());
            countQuery.setParameter (2, DateUtils.date2Timestamp (searchForm.getStartDate ()));
            countQuery.setParameter (3, DateUtils.date2Timestamp (searchForm.getEndDate ()));
            total = Integer.parseInt (countQuery.getResultList ().get (0).toString ());

            String querySql = "select pat from PaymentTransfer pat where pat.targetAccount.id = ?1 and pat.dateCreated between ?2 and ?3 order by pat.dateCreated desc";
            Query query = getEntityManager ().createQuery (querySql, PaymentTransfer.class);
            query.setParameter (1, searchForm.getTargetAccount ());
            query.setParameter (2, DateUtils.date2Timestamp (searchForm.getStartDate ()));
            query.setParameter (3, DateUtils.date2Timestamp (searchForm.getEndDate ()));

            query.setFirstResult (pageable.getOffset ());
            query.setMaxResults (pageable.getPageSize ());
            content = query.getResultList ();
        }
        
        Page <PaymentTransfer> transferPage = new PageImpl <PaymentTransfer> (content, pageable, total);
        return transferPage;
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <PaymentTransfer> findByAccount (long accountId)
    {
        if (0 != accountId)
        {
            String sql = "select pat from PaymentTransfer pat where pat.targetAccount.id = ?1 order by pat.dateCreated desc";
            Query query = getEntityManager ().createQuery (sql, PaymentTransfer.class);
            query.setParameter (1, accountId);
            return query.getResultList ();
        }
        else
        {
            String sql = "select pat from PaymentTransfer pat order by dateCreated desc";
            Query query = getEntityManager ().createQuery (sql, PaymentTransfer.class);
            return query.getResultList ();
        }
    }

    @Override
    public float countInMoney (PaymentTransferSearchForm searchForm)
    {
        float sumInMoney = 0;
        if (0 != searchForm.getTargetAccount () && null != searchForm.getStartDate ()
                && null != searchForm.getEndDate () && null != searchForm.getTransferTypeCode ())
        {
            String countSql = "select sum(pat.inMoney) from PaymentTransfer pat where pat.targetAccount.id = ?1 and pat.transferTypeCode = ?2 and pat.dateCreated between ?3 and ?4";
            Query countQuery = getEntityManager ().createQuery (countSql);
            countQuery.setParameter (1, searchForm.getTargetAccount ());
            countQuery.setParameter (2, searchForm.getTransferTypeCode ());
            countQuery.setParameter (3, DateUtils.date2Timestamp (searchForm.getStartDate ()));
            countQuery.setParameter (4, DateUtils.date2Timestamp (searchForm.getEndDate ()));
            sumInMoney = Float.parseFloat (countQuery.getResultList ().get (0).toString ());
        }
        else if (0 != searchForm.getTargetAccount () && null != searchForm.getStartDate ()
            && null != searchForm.getEndDate ())
        {
            String countSql = "select sum(pat.inMoney) from PaymentTransfer pat where pat.targetAccount.id = ?1 and pat.dateCreated between ?2 and ?3";
            Query countQuery = getEntityManager ().createQuery (countSql);
            countQuery.setParameter (1, searchForm.getTargetAccount ());
            countQuery.setParameter (2, DateUtils.date2Timestamp (searchForm.getStartDate ()));
            countQuery.setParameter (3, DateUtils.date2Timestamp (searchForm.getEndDate ()));
            sumInMoney = Float.parseFloat (countQuery.getResultList ().get (0).toString ());
        }
        return sumInMoney;
    }

    @Override
    public float countOutMoney (PaymentTransferSearchForm searchForm)
    {
        float sumOutMoney = 0;
        if (0 != searchForm.getTargetAccount () && null != searchForm.getStartDate ()
                && null != searchForm.getEndDate () && null != searchForm.getTransferTypeCode ())
        {
            String countSql = "select sum(pat.outMoney) from PaymentTransfer pat where pat.targetAccount.id = ?1 and pat.transferTypeCode = ?2 and pat.dateCreated between ?3 and ?4";
            Query countQuery = getEntityManager ().createQuery (countSql);
            countQuery.setParameter (1, searchForm.getTargetAccount ());
            countQuery.setParameter (2, searchForm.getTransferTypeCode ());
            countQuery.setParameter (3, DateUtils.date2Timestamp (searchForm.getStartDate ()));
            countQuery.setParameter (4, DateUtils.date2Timestamp (searchForm.getEndDate ()));
            sumOutMoney = Float.parseFloat (countQuery.getResultList ().get (0).toString ());
        }
        else if (0 != searchForm.getTargetAccount () && null != searchForm.getStartDate ()
            && null != searchForm.getEndDate ())
        {
            String countSql = "select sum(pat.outMoney) from PaymentTransfer pat where pat.targetAccount.id = ?1 and pat.dateCreated between ?2 and ?3";
            Query countQuery = getEntityManager ().createQuery (countSql);
            countQuery.setParameter (1, searchForm.getTargetAccount ());
            countQuery.setParameter (2, DateUtils.date2Timestamp (searchForm.getStartDate ()));
            countQuery.setParameter (3, DateUtils.date2Timestamp (searchForm.getEndDate ()));
            sumOutMoney = Float.parseFloat (countQuery.getResultList ().get (0).toString ());
        }
        return sumOutMoney;
    }

}
