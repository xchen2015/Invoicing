package com.pinfly.purchasecharge.dal.impl.in;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.pinfly.purchasecharge.core.model.persistence.in.ProviderContact;
import com.pinfly.purchasecharge.dal.common.MyGenericDaoImpl;
import com.pinfly.purchasecharge.dal.in.ProviderContactDao;

public class ProviderContactDaoImpl extends MyGenericDaoImpl <ProviderContact, Long> implements ProviderContactDao
{
    private static final Logger logger = Logger.getLogger (ProviderContactDaoImpl.class);

    public ProviderContactDaoImpl ()
    {
        super (ProviderContact.class);
    }

    @Override
    public ProviderContact findByName (String name)
    {
        String sql = "select c from ProviderContact c where c.name=?1";
        Query query = getEntityManager ().createQuery (sql, ProviderContact.class);
        query.setParameter (1, name);
        return CollectionUtils.isNotEmpty (query.getResultList ()) ? (ProviderContact) query.getResultList ().get (0)
                                                                  : null;
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <ProviderContact> findByProvider (long providerId)
    {
        String sql = "select c from ProviderContact c where c.provider.id=?1";
        Query query = getEntityManager ().createQuery (sql, ProviderContact.class);
        query.setParameter (1, providerId);
        return query.getResultList ();
    }

    @Override
    public List <ProviderContact> findByFuzzyName (String keyWord)
    {
        logger.debug ("Find contact by fuzzy name: " + keyWord);
        String searchKey = (null == keyWord ? "" : keyWord.trim ());
        String sql = "select * from pc_contact c where c.name like ?1 order by convert(c.name USING gbk) COLLATE gbk_chinese_ci asc";
        // String sql =
        // "select c from ProviderContact c where c.name like ?1 order by c.name";
        Query query = getEntityManager ().createNativeQuery (sql, ProviderContact.class);
        query.setParameter (1, searchKey + "%");
        @SuppressWarnings ("unchecked")
        List <ProviderContact> contactList = query.getResultList ();
        return contactList;
    }

}
