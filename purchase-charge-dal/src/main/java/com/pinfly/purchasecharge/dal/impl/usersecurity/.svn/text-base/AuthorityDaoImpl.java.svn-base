package com.pinfly.purchasecharge.dal.impl.usersecurity;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.pinfly.purchasecharge.core.model.persistence.usersecurity.Authority;
import com.pinfly.purchasecharge.dal.common.MyGenericDaoImpl;
import com.pinfly.purchasecharge.dal.usersecurity.AuthorityDao;

public class AuthorityDaoImpl extends MyGenericDaoImpl <Authority, Long> implements AuthorityDao
{
    @SuppressWarnings ("unused")
    private static final Logger logger = Logger.getLogger (AuthorityDaoImpl.class);

    public AuthorityDaoImpl ()
    {
        super (Authority.class);
    }

    @Override
    public Authority findByName (String name)
    {
        String sql = "select a from Authority a where a.name=?1";
        Query query = getEntityManager ().createQuery (sql, Authority.class);
        query.setParameter (1, name);
        return CollectionUtils.isNotEmpty (query.getResultList ()) ? (Authority) query.getResultList ().get (0) : null;
    }

    @Override
    public boolean isSystem (String name)
    {
        String sql = "select a.system from Authority a where a.name=?1";
        Query query = getEntityManager ().createQuery (sql);
        query.setParameter (1, name);
        return CollectionUtils.isNotEmpty (query.getResultList ()) ? Boolean.valueOf (query.getResultList ().get (0)
                                                                                           .toString ()) : false;
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List <Authority> findByParent (Long parentId)
    {
        String sql = "select a from Authority a where a.parent.id=?1";
        Query query = getEntityManager ().createQuery (sql, Authority.class);
        query.setParameter (1, parentId);
        return query.getResultList ();
    }
}
