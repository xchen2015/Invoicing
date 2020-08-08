package com.pinfly.purchasecharge.dal.impl.usersecurity;

import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.pinfly.purchasecharge.core.model.persistence.usersecurity.Role;
import com.pinfly.purchasecharge.dal.common.MyGenericDaoImpl;
import com.pinfly.purchasecharge.dal.usersecurity.RoleDao;

public class RoleDaoImpl extends MyGenericDaoImpl <Role, Long> implements RoleDao
{
    @SuppressWarnings ("unused")
    private static final Logger logger = Logger.getLogger (RoleDaoImpl.class);

    public RoleDaoImpl ()
    {
        super (Role.class);
    }

    @Override
    public Role findByName (String name)
    {
        String sql = "select a from Role a where a.name=?1";
        Query query = getEntityManager ().createQuery (sql, Role.class);
        query.setParameter (1, name);
        return CollectionUtils.isNotEmpty (query.getResultList ()) ? (Role) query.getResultList ().get (0) : null;
    }

}
