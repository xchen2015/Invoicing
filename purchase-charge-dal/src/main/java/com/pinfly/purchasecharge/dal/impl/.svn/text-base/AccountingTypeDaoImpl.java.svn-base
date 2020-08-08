package com.pinfly.purchasecharge.dal.impl;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.pinfly.purchasecharge.core.model.AccountingModeCode;
import com.pinfly.purchasecharge.core.model.persistence.AccountingType;
import com.pinfly.purchasecharge.dal.AccountingTypeDao;
import com.pinfly.purchasecharge.dal.common.MyGenericDaoImpl;

public class AccountingTypeDaoImpl extends MyGenericDaoImpl <AccountingType, Long> implements AccountingTypeDao
{
    @SuppressWarnings ("unused")
    private static final Logger LOGGER = Logger.getLogger (AccountingTypeDaoImpl.class.getName ());

    public AccountingTypeDaoImpl ()
    {
        super (AccountingType.class);
    }

    @Override
    public AccountingType findByName (String name)
    {
        String sql = "select a from AccountingType a where a.name=?1";
        Query query = getEntityManager ().createQuery (sql, AccountingType.class);
        query.setParameter (1, name);
        return CollectionUtils.isNotEmpty (query.getResultList ()) ? (AccountingType) query.getResultList ().get (0)
                                                                  : null;
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <AccountingType> findByMode (AccountingModeCode mode)
    {
        String sql = "select a from AccountingType a where a.accountingMode=?1";
        Query query = getEntityManager ().createQuery (sql, AccountingType.class);
        query.setParameter (1, mode);
        return query.getResultList ();
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <AccountingType> findAll ()
    {
        String sql = "select a from AccountingType a order by a.accountingMode";
        Query query = getEntityManager ().createQuery (sql, AccountingType.class);
        return query.getResultList ();
    }
}
