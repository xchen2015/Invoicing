package com.pinfly.purchasecharge.dal.impl.in;

import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.pinfly.purchasecharge.core.model.persistence.in.ProviderType;
import com.pinfly.purchasecharge.dal.common.MyGenericDaoImpl;
import com.pinfly.purchasecharge.dal.in.ProviderTypeDao;

public class ProviderTypeDaoImpl extends MyGenericDaoImpl <ProviderType, Long> implements ProviderTypeDao
{
    @SuppressWarnings ("unused")
    private static final Logger logger = Logger.getLogger (ProviderTypeDaoImpl.class);

    public ProviderTypeDaoImpl ()
    {
        super (ProviderType.class);
    }

    @Override
    public ProviderType findByName (String name)
    {
        String sql = "select c from ProviderType c where c.name=?1";
        Query query = getEntityManager ().createQuery (sql, ProviderType.class);
        query.setParameter (1, name);
        return CollectionUtils.isNotEmpty (query.getResultList ()) ? (ProviderType) query.getResultList ().get (0)
                                                                  : null;
    }
}
