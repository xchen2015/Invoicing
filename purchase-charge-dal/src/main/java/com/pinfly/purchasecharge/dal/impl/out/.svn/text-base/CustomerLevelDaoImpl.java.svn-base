package com.pinfly.purchasecharge.dal.impl.out;

import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.pinfly.purchasecharge.core.model.persistence.out.CustomerLevel;
import com.pinfly.purchasecharge.dal.common.MyGenericDaoImpl;
import com.pinfly.purchasecharge.dal.out.CustomerLevelDao;

public class CustomerLevelDaoImpl extends MyGenericDaoImpl <CustomerLevel, Long> implements CustomerLevelDao
{
    private static final Logger logger = Logger.getLogger (CustomerLevelDaoImpl.class);

    public CustomerLevelDaoImpl ()
    {
        super (CustomerLevel.class);
    }

    @Override
    @SuppressWarnings ("unchecked")
    public Iterable <CustomerLevel> findAll ()
    {
        String sql = "select c from CustomerLevel c order by c.order asc";
        Query query = getEntityManager ().createQuery (sql, CustomerLevel.class);
        return query.getResultList ();
    }

    @Override
    public CustomerLevel findByName (String name)
    {
        logger.debug (name);
        String sql = "select c from CustomerLevel c where c.name=?1";
        Query query = getEntityManager ().createQuery (sql, CustomerLevel.class);
        query.setParameter (1, name);
        return CollectionUtils.isNotEmpty (query.getResultList ()) ? (CustomerLevel) query.getResultList ().get (0)
                                                                  : null;
    }

    @Override
    public CustomerLevel findByOrder (int order)
    {
        String sql = "select c from CustomerLevel c where c.order=?1";
        Query query = getEntityManager ().createQuery (sql, CustomerLevel.class);
        query.setParameter (1, order);
        return CollectionUtils.isNotEmpty (query.getResultList ()) ? (CustomerLevel) query.getResultList ().get (0)
                                                                  : null;
    }
}
