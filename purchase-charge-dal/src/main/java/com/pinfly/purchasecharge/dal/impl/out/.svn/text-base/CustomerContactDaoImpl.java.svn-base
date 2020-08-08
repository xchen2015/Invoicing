package com.pinfly.purchasecharge.dal.impl.out;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.pinfly.purchasecharge.core.model.persistence.out.CustomerContact;
import com.pinfly.purchasecharge.dal.common.MyGenericDaoImpl;
import com.pinfly.purchasecharge.dal.out.CustomerContactDao;

public class CustomerContactDaoImpl extends MyGenericDaoImpl <CustomerContact, Long> implements CustomerContactDao
{
    private static final Logger logger = Logger.getLogger (CustomerContactDaoImpl.class);

    public CustomerContactDaoImpl ()
    {
        super (CustomerContact.class);
    }

    @Override
    public CustomerContact findByName (String name)
    {
        String sql = "select c from CustomerContact c where c.name=?1";
        Query query = getEntityManager ().createQuery (sql, CustomerContact.class);
        query.setParameter (1, name);
        return CollectionUtils.isNotEmpty (query.getResultList ()) ? (CustomerContact) query.getResultList ().get (0)
                                                                  : null;
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <CustomerContact> findByCustomer (long customerId)
    {
        String sql = "select c from CustomerContact c where c.customer.id=?1";
        Query query = getEntityManager ().createQuery (sql, CustomerContact.class);
        query.setParameter (1, customerId);
        return query.getResultList ();
    }

    @Override
    public List <CustomerContact> findByFuzzyName (String keyWord)
    {
        logger.debug ("Find contact by fuzzy name: " + keyWord);
        String searchKey = (null == keyWord ? "" : keyWord.trim ());
        String sql = "select * from pc_contact c where c.name like ?1 order by convert(c.name USING gbk) COLLATE gbk_chinese_ci asc";
        // String sql =
        // "select c from CustomerContact c where c.name like ?1 order by c.name";
        Query query = getEntityManager ().createNativeQuery (sql, CustomerContact.class);
        query.setParameter (1, searchKey + "%");
        @SuppressWarnings ("unchecked")
        List <CustomerContact> contactList = query.getResultList ();
        return contactList;
    }

}
