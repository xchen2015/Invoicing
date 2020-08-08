package com.pinfly.purchasecharge.dal.impl.out;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.pinfly.purchasecharge.core.model.persistence.out.OrderDelivery;
import com.pinfly.purchasecharge.dal.common.MyGenericDaoImpl;
import com.pinfly.purchasecharge.dal.out.OrderDeliveryDao;

public class OrderDeliveryDaoImpl extends MyGenericDaoImpl <OrderDelivery, Long> implements OrderDeliveryDao
{
    @SuppressWarnings ("unused")
    private static final Logger logger = Logger.getLogger (OrderDeliveryDaoImpl.class);

    public OrderDeliveryDaoImpl ()
    {
        super (OrderDelivery.class);
    }

    @Override
    public OrderDelivery findByOrder (long orderId)
    {
        String sql = "select ody from OrderDelivery ody where ody.orderOut.id = ?1";
        Query query = getEntityManager ().createQuery (sql, OrderDelivery.class);
        query.setParameter (1, orderId);
        return CollectionUtils.isNotEmpty (query.getResultList ()) ? (OrderDelivery) query.getResultList ().get (0)
                                                                  : null;
    }

    @Override
    public OrderDelivery findByNumber (String number)
    {
        String sql = "select ody from OrderDelivery ody where ody.number = ?1";
        Query query = getEntityManager ().createQuery (sql, OrderDelivery.class);
        query.setParameter (1, number);
        return CollectionUtils.isNotEmpty (query.getResultList ()) ? (OrderDelivery) query.getResultList ().get (0)
                                                                  : null;
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <OrderDelivery> findByCompany (long companyId)
    {
        String sql = "select ody from OrderDelivery ody where ody.company.id = ?1";
        Query query = getEntityManager ().createQuery (sql, OrderDelivery.class);
        query.setParameter (1, companyId);
        return query.getResultList ();
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <OrderDelivery> findBy (long customerId, Date start, Date end)
    {
        if (0 != customerId)
        {
            String sql = "select ody from OrderDelivery ody where ody.orderOut.customer.id = ?1";
            Query query = getEntityManager ().createQuery (sql, OrderDelivery.class);
            query.setParameter (1, customerId);
            return query.getResultList ();
        }
        else if (null != start && null != end)
        {
            String sql = "select ody from OrderDelivery ody where ody.dateCreated between ?1 and ?2";
            Query query = getEntityManager ().createQuery (sql, OrderDelivery.class);
            query.setParameter (1, start);
            query.setParameter (2, end);
            return query.getResultList ();
        }
        else if (0 != customerId && null != start && null != end)
        {
            String sql = "select ody from OrderDelivery ody where ody.orderOut.customer.id = ?1 and ody.dateCreated between ?2 and ?3";
            Query query = getEntityManager ().createQuery (sql, OrderDelivery.class);
            query.setParameter (1, customerId);
            query.setParameter (2, start);
            query.setParameter (3, end);
            return query.getResultList ();
        }
        return null;
    }
}
