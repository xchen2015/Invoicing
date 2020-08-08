package com.pinfly.purchasecharge.dal.impl;

import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.pinfly.purchasecharge.core.model.persistence.PaymentAccount;
import com.pinfly.purchasecharge.dal.PaymentAccountDao;
import com.pinfly.purchasecharge.dal.common.MyGenericDaoImpl;

public class PaymentAccountDaoImpl extends MyGenericDaoImpl <PaymentAccount, Long> implements PaymentAccountDao
{
    @SuppressWarnings ("unused")
    private static final Logger logger = Logger.getLogger (PaymentAccountDaoImpl.class);

    public PaymentAccountDaoImpl ()
    {
        super (PaymentAccount.class);
    }

    @Override
    @SuppressWarnings ("unchecked")
    public Iterable <PaymentAccount> findAll ()
    {
        String sql = "select c from PaymentAccount c order by c.remainMoney desc";
        Query query = getEntityManager ().createQuery (sql, PaymentAccount.class);
        return query.getResultList ();
    }

    @Override
    public PaymentAccount findByName (String name)
    {
        String sql = "select c from PaymentAccount c where c.name=?1";
        Query query = getEntityManager ().createQuery (sql, PaymentAccount.class);
        query.setParameter (1, name);
        return CollectionUtils.isNotEmpty (query.getResultList ()) ? (PaymentAccount) query.getResultList ().get (0)
                                                                  : null;
    }

    @Override
    public PaymentAccount findByAccount (String account)
    {
        String sql = "select c from PaymentAccount c where c.accountId=?1";
        Query query = getEntityManager ().createQuery (sql, PaymentAccount.class);
        query.setParameter (1, account);
        return CollectionUtils.isNotEmpty (query.getResultList ()) ? (PaymentAccount) query.getResultList ().get (0)
                                                                  : null;
    }
}
