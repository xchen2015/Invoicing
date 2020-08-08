package com.pinfly.purchasecharge.dal.impl.out;

import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.pinfly.purchasecharge.core.model.persistence.out.CustomerType;
import com.pinfly.purchasecharge.dal.common.MyGenericDaoImpl;
import com.pinfly.purchasecharge.dal.out.CustomerTypeDao;

public class CustomerTypeDaoImpl extends MyGenericDaoImpl <CustomerType, Long> implements CustomerTypeDao
{
    private static final Logger logger = Logger.getLogger (CustomerTypeDaoImpl.class);

    public CustomerTypeDaoImpl ()
    {
        super (CustomerType.class);
    }

    @Override
    public CustomerType findByName (String name)
    {
        logger.debug (name);
        String sql = "select c from CustomerType c where c.name=?1";
        Query query = getEntityManager ().createQuery (sql, CustomerType.class);
        query.setParameter (1, name);
        return CollectionUtils.isNotEmpty (query.getResultList ()) ? (CustomerType) query.getResultList ().get (0)
                                                                  : null;
    }
}
