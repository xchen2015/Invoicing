package com.pinfly.purchasecharge.dal.impl.out;

import java.util.List;

import javax.persistence.Query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pinfly.purchasecharge.core.model.PaymentSearchForm;
import com.pinfly.purchasecharge.core.model.persistence.out.CustomerPaymentRecord;
import com.pinfly.purchasecharge.dal.common.GenericDaoUtils;
import com.pinfly.purchasecharge.dal.common.MyGenericDaoImpl;
import com.pinfly.purchasecharge.dal.out.CustomerPaymentRecordDao;

public class CustomerPaymentRecordDaoImpl extends MyGenericDaoImpl <CustomerPaymentRecord, Long> implements
                                                                                                CustomerPaymentRecordDao
{
    public CustomerPaymentRecordDaoImpl ()
    {
        super (CustomerPaymentRecord.class);
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <CustomerPaymentRecord> findByPaymentWay (long paymentWayId)
    {
        String querySql = "SELECT ppr FROM CustomerPaymentRecord ppr WHERE ppr.paymentWay.id = ?1";
        Query query = getEntityManager ().createQuery (querySql, CustomerPaymentRecord.class);
        query.setParameter (1, paymentWayId);
        return query.getResultList ();
    }

    @Override
    @Transactional (propagation = Propagation.NOT_SUPPORTED)
    public Page <CustomerPaymentRecord> findPagePaymentRecord (Pageable pageable, PaymentSearchForm searchForm)
    {
        if (null != pageable && null != searchForm)
        {
            String selectCountSql = "SELECT count(p) ";
            String fromSql = "FROM CustomerPaymentRecord p where ";
            String conditionSql = "";

            Query countQuery = null;
            if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                && 0 != searchForm.getCustomerId () && 0 != searchForm.getPaymentAccount ())
            {
                conditionSql += "p.payment.paidDate between ?1 and ?2 and p.payment.customer.id = ?3 and p.paymentAccount.id = ?4 ";
                countQuery = getEntityManager ().createQuery (selectCountSql + fromSql + conditionSql);
                countQuery.setParameter (1, searchForm.getStartDate ());
                countQuery.setParameter (2, searchForm.getEndDate ());
                countQuery.setParameter (3, searchForm.getCustomerId ());
                countQuery.setParameter (4, searchForm.getPaymentAccount ());
            }
            else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                     && 0 != searchForm.getPaymentAccount ())
            {
                conditionSql += "p.payment.paidDate between ?1 and ?2 and p.paymentAccount.id = ?3 ";
                countQuery = getEntityManager ().createQuery (selectCountSql + fromSql + conditionSql);
                countQuery.setParameter (1, searchForm.getStartDate ());
                countQuery.setParameter (2, searchForm.getEndDate ());
                countQuery.setParameter (3, searchForm.getPaymentAccount ());
            }
            else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                     && 0 != searchForm.getCustomerId ())
            {
                conditionSql += "p.payment.paidDate between ?1 and ?2 and p.payment.customer.id = ?3 ";
                countQuery = getEntityManager ().createQuery (selectCountSql + fromSql + conditionSql);
                countQuery.setParameter (1, searchForm.getStartDate ());
                countQuery.setParameter (2, searchForm.getEndDate ());
                countQuery.setParameter (3, searchForm.getCustomerId ());
            }
            else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
            {
                conditionSql += "p.payment.paidDate between ?1 and ?2 ";
                countQuery = getEntityManager ().createQuery (selectCountSql + fromSql + conditionSql);
                countQuery.setParameter (1, searchForm.getStartDate ());
                countQuery.setParameter (2, searchForm.getEndDate ());
            }
            else if (0 != searchForm.getCustomerId () && 0 != searchForm.getPaymentAccount ())
            {
                conditionSql += "p.payment.customer.id = ?1 and p.paymentAccount.id = ?2 ";
                countQuery = getEntityManager ().createQuery (selectCountSql + fromSql + conditionSql);
                countQuery.setParameter (1, searchForm.getCustomerId ());
                countQuery.setParameter (2, searchForm.getPaymentAccount ());
            }
            else if (0 != searchForm.getCustomerId ())
            {
                conditionSql += "p.payment.customer.id = ?1 ";
                countQuery = getEntityManager ().createQuery (selectCountSql + fromSql + conditionSql);
                countQuery.setParameter (1, searchForm.getCustomerId ());
            }
            else if (0 != searchForm.getPaymentAccount ())
            {
                conditionSql += "p.paymentAccount.id = ?1 ";
                countQuery = getEntityManager ().createQuery (selectCountSql + fromSql + conditionSql);
                countQuery.setParameter (1, searchForm.getPaymentAccount ());
            }
            int total = 0;
            if (null != countQuery)
            {
                total = Integer.parseInt (countQuery.getResultList ().get (0).toString ());
            }

            String querySql = "SELECT p ";
            querySql += fromSql;
            querySql += conditionSql;
            String orderBySql = " ORDER BY ";
            querySql += orderBySql;

            String sortSqlStr = "p.payment.paidDate desc";
            try
            {
                sortSqlStr = GenericDaoUtils.parseSort (CustomerPaymentRecord.class, pageable, "p");
            }
            catch (Exception e)
            {
                // TODO: handle exception
            }
            querySql += sortSqlStr;

            Query dataQuery = getEntityManager ().createQuery (querySql);
            if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                && 0 != searchForm.getCustomerId () && 0 != searchForm.getPaymentAccount ())
            {
                dataQuery.setParameter (1, searchForm.getStartDate ());
                dataQuery.setParameter (2, searchForm.getEndDate ());
                dataQuery.setParameter (3, searchForm.getCustomerId ());
                dataQuery.setParameter (4, searchForm.getPaymentAccount ());
            }
            else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                     && 0 != searchForm.getPaymentAccount ())
            {
                dataQuery.setParameter (1, searchForm.getStartDate ());
                dataQuery.setParameter (2, searchForm.getEndDate ());
                dataQuery.setParameter (3, searchForm.getPaymentAccount ());
            }
            else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ()
                     && 0 != searchForm.getCustomerId ())
            {
                dataQuery.setParameter (1, searchForm.getStartDate ());
                dataQuery.setParameter (2, searchForm.getEndDate ());
                dataQuery.setParameter (3, searchForm.getCustomerId ());
            }
            else if (null != searchForm.getStartDate () && null != searchForm.getEndDate ())
            {
                dataQuery.setParameter (1, searchForm.getStartDate ());
                dataQuery.setParameter (2, searchForm.getEndDate ());
            }
            else if (0 != searchForm.getCustomerId () && 0 != searchForm.getPaymentAccount ())
            {
                dataQuery.setParameter (1, searchForm.getCustomerId ());
                dataQuery.setParameter (2, searchForm.getPaymentAccount ());
            }
            else if (0 != searchForm.getCustomerId ())
            {
                dataQuery.setParameter (1, searchForm.getCustomerId ());
            }
            else if (0 != searchForm.getPaymentAccount ())
            {
                dataQuery.setParameter (1, searchForm.getPaymentAccount ());
            }

            dataQuery.setFirstResult (pageable.getOffset ());
            dataQuery.setMaxResults (pageable.getPageSize ());

            @SuppressWarnings ("unchecked")
            List <CustomerPaymentRecord> paymentRecords = dataQuery.getResultList ();
            for (CustomerPaymentRecord paymentRecord : paymentRecords)
            {
                paymentRecord.getPayment ();
                paymentRecord.getPayment ().getCustomer ();
            }
            Page <CustomerPaymentRecord> paymentRecordPage = new PageImpl <CustomerPaymentRecord> (paymentRecords,
                                                                                                   pageable, total);
            return paymentRecordPage;
        }
        return null;
    }

}
