package com.pinfly.purchasecharge.dal.impl;

import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.pinfly.purchasecharge.core.model.persistence.DeliveryCompany;
import com.pinfly.purchasecharge.dal.DeliveryCompanyDao;
import com.pinfly.purchasecharge.dal.common.MyGenericDaoImpl;

public class DeliveryCompanyDaoImpl extends MyGenericDaoImpl <DeliveryCompany, Long> implements DeliveryCompanyDao
{
    @SuppressWarnings ("unused")
    private static final Logger logger = Logger.getLogger (DeliveryCompanyDaoImpl.class);

    public DeliveryCompanyDaoImpl ()
    {
        super (DeliveryCompany.class);
    }

    @Override
    public DeliveryCompany findByName (String name)
    {
        String sql = "select dc from DeliveryCompany dc where dc.name = ?1";
        Query query = getEntityManager ().createQuery (sql, DeliveryCompany.class);
        query.setParameter (1, name);
        return CollectionUtils.isNotEmpty (query.getResultList ()) ? (DeliveryCompany) query.getResultList ().get (0)
                                                                  : null;
    }
}
