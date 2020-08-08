package com.pinfly.purchasecharge.dal.impl;

import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.pinfly.purchasecharge.core.model.persistence.PaymentWay;
import com.pinfly.purchasecharge.dal.PaymentWayDao;
import com.pinfly.purchasecharge.dal.common.MyGenericDaoImpl;

public class PaymentWayDaoImpl extends MyGenericDaoImpl <PaymentWay, Long> implements PaymentWayDao
{
    private static final Logger logger = Logger.getLogger (PaymentWayDaoImpl.class);

    public PaymentWayDaoImpl ()
    {
        super (PaymentWay.class);
    }

    @Override
    public PaymentWay findByName (String name)
    {
        logger.debug (name);
        String sql = "select c from PaymentWay c where c.name=?1";
        Query query = getEntityManager ().createQuery (sql, PaymentWay.class);
        query.setParameter (1, name);
        return CollectionUtils.isNotEmpty (query.getResultList ()) ? (PaymentWay) query.getResultList ().get (0) : null;
    }

}
