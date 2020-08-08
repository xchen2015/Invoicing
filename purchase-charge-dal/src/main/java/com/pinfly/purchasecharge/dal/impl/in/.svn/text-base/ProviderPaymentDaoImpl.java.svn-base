package com.pinfly.purchasecharge.dal.impl.in;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.pinfly.purchasecharge.core.model.PaymentSearchForm;
import com.pinfly.purchasecharge.core.model.PaymentTypeCode;
import com.pinfly.purchasecharge.core.model.persistence.in.ProviderPayment;
import com.pinfly.purchasecharge.core.model.persistence.in.ProviderPaymentRecord;
import com.pinfly.purchasecharge.core.util.DateUtils;
import com.pinfly.purchasecharge.dal.common.GenericDaoUtils;
import com.pinfly.purchasecharge.dal.common.MyGenericDaoImpl;
import com.pinfly.purchasecharge.dal.in.ProviderPaymentDao;

public class ProviderPaymentDaoImpl extends MyGenericDaoImpl <ProviderPayment, Long> implements ProviderPaymentDao
{

    public ProviderPaymentDaoImpl ()
    {
        super (ProviderPayment.class);
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <ProviderPayment> findByProviderId (long providerId)
    {
        String sql = "select p from ProviderPayment p where p.provider.id=?1 order by p.paidDate desc";
        Query query = getEntityManager ().createQuery (sql, ProviderPayment.class);
        query.setParameter (1, providerId);
        return query.getResultList ();
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <ProviderPayment> findByAdvance (long providerId, Timestamp start, Timestamp end)
    {
        String sql = "select p from ProviderPayment p where p.provider.id=?1 and p.paidDate between ?2 and ?3 order by p.paidDate desc";
        Query query = getEntityManager ().createQuery (sql, ProviderPayment.class);
        query.setParameter (1, providerId);
        query.setParameter (2, start);
        query.setParameter (3, end);
        return query.getResultList ();
    }

    @Override
    @SuppressWarnings ("unchecked")
    public Page <ProviderPayment> findPagePayment (PaymentSearchForm searchForm, Pageable pageable)
    {
        String selectCountSql = "SELECT count(p) ";
        String fromSql = "FROM ProviderPayment p where ";
        String conditionSql = "";
        if (null != searchForm)
        {
            if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                && 0 != searchForm.getCustomerId () && null != searchForm.getTypeCode ())
            {
                conditionSql += "p.paidDate between ?1 and ?2 and p.provider.id = ?3 and p.typeCode = ?4 ";
            }
            else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                     && 0 != searchForm.getCustomerId ())
            {
                conditionSql += "p.paidDate between ?1 and ?2 and p.provider.id = ?3 ";
            }
            else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                     && null != searchForm.getTypeCode ())
            {
                conditionSql += "p.paidDate between ?1 and ?2 and p.typeCode = ?3 ";
            }
            else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
            {
                conditionSql += "p.paidDate between ?1 and ?2 ";
            }
            else if (0 != searchForm.getCustomerId () && null != searchForm.getTypeCode ())
            {
                conditionSql += "p.provider.id = ?1 and p.typeCode = ?2 ";
            }
            else if (0 != searchForm.getCustomerId ())
            {
                conditionSql += "p.provider.id = ?1 ";
            }
            else if (null != searchForm.getTypeCode ())
            {
                conditionSql += "p.typeCode = ?1 ";
            }
        }
        Query countQuery = getEntityManager ().createQuery (selectCountSql + fromSql + conditionSql);
        if (null != searchForm)
        {
            if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                && 0 != searchForm.getCustomerId () && null != searchForm.getTypeCode ())
            {
                countQuery.setParameter (1, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                countQuery.setParameter (2, DateUtils.date2Timestamp (searchForm.getEndDate ()));
                countQuery.setParameter (3, searchForm.getCustomerId ());
                countQuery.setParameter (4, searchForm.getTypeCode ());
            }
            else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                     && 0 != searchForm.getCustomerId ())
            {
                countQuery.setParameter (1, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                countQuery.setParameter (2, DateUtils.date2Timestamp (searchForm.getEndDate ()));
                countQuery.setParameter (3, searchForm.getCustomerId ());
            }
            else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                     && null != searchForm.getTypeCode ())
            {
                countQuery.setParameter (1, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                countQuery.setParameter (2, DateUtils.date2Timestamp (searchForm.getEndDate ()));
                countQuery.setParameter (3, searchForm.getTypeCode ());
            }
            else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
            {
                countQuery.setParameter (1, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                countQuery.setParameter (2, DateUtils.date2Timestamp (searchForm.getEndDate ()));
            }
            else if (0 != searchForm.getCustomerId () && null != searchForm.getTypeCode ())
            {
                countQuery.setParameter (1, searchForm.getCustomerId ());
                countQuery.setParameter (2, searchForm.getTypeCode ());
            }
            else if (0 != searchForm.getCustomerId ())
            {
                countQuery.setParameter (1, searchForm.getCustomerId ());
            }
            else if (null != searchForm.getTypeCode ())
            {
                countQuery.setParameter (1, searchForm.getTypeCode ());
            }
        }
        int total = Integer.parseInt (countQuery.getResultList ().get (0).toString ());

        String querySql = "SELECT p ";
        querySql += fromSql;
        querySql += conditionSql;
        String orderBySql = " ORDER BY ";
        querySql += orderBySql;

        String sortSqlStr = "p.paidDate desc";
        try
        {
            sortSqlStr = GenericDaoUtils.parseSort (ProviderPayment.class, pageable, "p");
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
                && 0 != searchForm.getCustomerId () && null != searchForm.getTypeCode ())
            {
                query.setParameter (1, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                query.setParameter (2, DateUtils.date2Timestamp (searchForm.getEndDate ()));
                query.setParameter (3, searchForm.getCustomerId ());
                query.setParameter (4, searchForm.getTypeCode ());
            }
            else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                     && 0 != searchForm.getCustomerId ())
            {
                query.setParameter (1, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                query.setParameter (2, DateUtils.date2Timestamp (searchForm.getEndDate ()));
                query.setParameter (3, searchForm.getCustomerId ());
            }
            else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                     && null != searchForm.getTypeCode ())
            {
                query.setParameter (1, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                query.setParameter (2, DateUtils.date2Timestamp (searchForm.getEndDate ()));
                query.setParameter (3, searchForm.getTypeCode ());
            }
            else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
            {
                query.setParameter (1, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                query.setParameter (2, DateUtils.date2Timestamp (searchForm.getEndDate ()));
            }
            else if (0 != searchForm.getCustomerId () && null != searchForm.getTypeCode ())
            {
                query.setParameter (1, searchForm.getCustomerId ());
                query.setParameter (2, searchForm.getTypeCode ());
            }
            else if (null != searchForm.getTypeCode ())
            {
                query.setParameter (1, searchForm.getTypeCode ());
            }
            else if (0 != searchForm.getCustomerId ())
            {
                query.setParameter (1, searchForm.getCustomerId ());
            }
        }
        query.setFirstResult (pageable.getOffset ());
        query.setMaxResults (pageable.getPageSize ());

        List <ProviderPayment> payments = query.getResultList ();
        // lazy load provider of payment
        for (ProviderPayment payment : payments)
        {
            payment.getProvider ();
        }
        Page <ProviderPayment> paymentPage = new PageImpl <ProviderPayment> (payments, pageable, total);
        return paymentPage;
    }
    
    @Override
    @SuppressWarnings ("unchecked")
    public float countPaid (PaymentSearchForm searchForm)
    {
        String selectCountSql = "SELECT p ";
        String fromSql = "FROM ProviderPayment p ";
        String conditionSql = "";
        if (null != searchForm)
        {
            if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                    && 0 != searchForm.getCustomerId ())
            {
                conditionSql += "where p.paidDate between ?1 and ?2 and p.provider.id = ?3 ";
            }
            else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
            {
                conditionSql += "where p.paidDate between ?1 and ?2 ";
            }
            else if (0 != searchForm.getCustomerId ())
            {
                conditionSql += "where p.provider.id = ?1 ";
            }
        }
        
        String sql = selectCountSql + fromSql + conditionSql;
        Query query = getEntityManager ().createQuery (sql);
        if (null != searchForm)
        {
            if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                    && 0 != searchForm.getCustomerId ())
            {
                query.setParameter (1, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                query.setParameter (2, DateUtils.date2Timestamp (searchForm.getEndDate ()));
                query.setParameter (3, searchForm.getCustomerId ());
            }
            else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
            {
                query.setParameter (1, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                query.setParameter (2, DateUtils.date2Timestamp (searchForm.getEndDate ()));
            }
            else if (0 != searchForm.getCustomerId ())
            {
                query.setParameter (1, searchForm.getCustomerId ());
            }
        }
        
        float sumPaid = 0;
        List <ProviderPayment> payments = query.getResultList ();
        for (ProviderPayment payment : payments)
        {
            List <ProviderPaymentRecord> paymentRecords = payment.getPaymentRecords ();
            for (ProviderPaymentRecord paymentRecord : paymentRecords)
            {
                sumPaid += paymentRecord.getPaid ();
            }
        }
        return sumPaid;
    }

    @Override
    public float countNewUnPaid (PaymentSearchForm searchForm)
    {
        String selectCountSql = "SELECT sum(p.addUnPaid) ";
        String fromSql = "FROM ProviderPayment p ";
        String conditionSql = "";
        if (null != searchForm)
        {
            if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                && 0 != searchForm.getCustomerId ())
            {
                conditionSql += "where p.paidDate between ?1 and ?2 and p.provider.id = ?3 ";
            }
            else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
            {
                conditionSql += "where p.paidDate between ?1 and ?2 ";
            }
            else if (0 != searchForm.getCustomerId ())
            {
                conditionSql += "where p.provider.id = ?1 ";
            }
        }

        String sql = selectCountSql + fromSql + conditionSql;
        Query query = getEntityManager ().createQuery (sql);
        if (null != searchForm)
        {
            if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                && 0 != searchForm.getCustomerId ())
            {
                query.setParameter (1, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                query.setParameter (2, DateUtils.date2Timestamp (searchForm.getEndDate ()));
                query.setParameter (3, searchForm.getCustomerId ());
            }
            else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
            {
                query.setParameter (1, DateUtils.date2Timestamp (searchForm.getStartDate ()));
                query.setParameter (2, DateUtils.date2Timestamp (searchForm.getEndDate ()));
            }
            else if (0 != searchForm.getCustomerId ())
            {
                query.setParameter (1, searchForm.getCustomerId ());
            }
        }

        float sumUnPaid = 0;
        if (CollectionUtils.isNotEmpty (query.getResultList ()))
        {
            sumUnPaid = Float.parseFloat (query.getResultList ().get (0).toString ());
        }
        return sumUnPaid;
    }

    @Override
    public ProviderPayment findLast (long providerId)
    {
        String sql = "select p from ProviderPayment p where p.paidDate = (select max(ppr.payment.paidDate) from ProviderPaymentRecord ppr where ppr.payment.provider.id = ?1 and ppr.paid > 0)";
        Query query = getEntityManager ().createQuery (sql, ProviderPayment.class);
        query.setParameter (1, providerId);
        return CollectionUtils.isNotEmpty (query.getResultList ()) ? (ProviderPayment) query.getResultList ().get (0)
                                                                  : null;
    }
    
    @Override
    public ProviderPayment findByBid (String bid)
    {
        String sql = "select p from ProviderPayment p where p.bid = ?1";
        Query query = getEntityManager ().createQuery (sql, ProviderPayment.class);
        query.setParameter (1, bid);
        return CollectionUtils.isNotEmpty (query.getResultList ()) ? (ProviderPayment) query.getResultList ().get (0)
                                                                   : null;
    }

    @Override
    public ProviderPayment findByReceiptIdAndType (String receiptId, PaymentTypeCode typeCode)
    {
        if(StringUtils.isNotBlank (receiptId) && null != typeCode) 
        {
            String sql = "select p from ProviderPayment p where p.receiptId = ?1 and p.typeCode = ?2";
            Query query = getEntityManager ().createQuery (sql, ProviderPayment.class);
            query.setParameter (1, receiptId);
            query.setParameter (2, typeCode);
            return CollectionUtils.isNotEmpty (query.getResultList ()) ? (ProviderPayment) query.getResultList ().get (0)
                                                                      : null;
        }
        return null;
    }

}
