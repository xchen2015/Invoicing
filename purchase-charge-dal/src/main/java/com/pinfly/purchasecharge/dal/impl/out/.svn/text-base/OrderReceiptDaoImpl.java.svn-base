package com.pinfly.purchasecharge.dal.impl.out;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.pinfly.purchasecharge.core.model.persistence.out.OrderReceipt;
import com.pinfly.purchasecharge.dal.common.MyGenericDaoImpl;
import com.pinfly.purchasecharge.dal.out.OrderReceiptDao;

public class OrderReceiptDaoImpl extends MyGenericDaoImpl <OrderReceipt, Long> implements OrderReceiptDao
{
    @SuppressWarnings ("unused")
    private static final Logger logger = Logger.getLogger (OrderReceiptDaoImpl.class);

    public OrderReceiptDaoImpl ()
    {
        super (OrderReceipt.class);
    }

    @Override
    public OrderReceipt findByOrder (long orderId)
    {
        String sql = "select ort from OrderReceipt ort where ort.orderOut.id = ?1";
        Query query = getEntityManager ().createQuery (sql, OrderReceipt.class);
        query.setParameter (1, orderId);
        return CollectionUtils.isNotEmpty (query.getResultList ()) ? (OrderReceipt) query.getResultList ().get (0)
                                                                  : null;
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <OrderReceipt> findBy (long customerId, Date start, Date end)
    {
        if (0 != customerId)
        {
            String sql = "select ort from OrderReceipt ort where ort.orderOut.customer.id = ?1";
            Query query = getEntityManager ().createQuery (sql, OrderReceipt.class);
            query.setParameter (1, customerId);
            return query.getResultList ();
        }
        else if (null != start && null != end)
        {
            String sql = "select ort from OrderReceipt ort where ort.dateCreated between ?1 and ?2";
            Query query = getEntityManager ().createQuery (sql, OrderReceipt.class);
            query.setParameter (1, start);
            query.setParameter (2, end);
            return query.getResultList ();
        }
        else if (0 != customerId && null != start && null != end)
        {
            String sql = "select ort from OrderReceipt ort where ort.orderOut.customer.id = ?1 and ort.dateCreated between ?2 and ?3";
            Query query = getEntityManager ().createQuery (sql, OrderReceipt.class);
            query.setParameter (1, customerId);
            query.setParameter (2, start);
            query.setParameter (3, end);
            return query.getResultList ();
        }
        return null;
    }
}
