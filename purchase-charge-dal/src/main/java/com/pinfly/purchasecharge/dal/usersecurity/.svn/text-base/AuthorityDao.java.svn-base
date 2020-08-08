package com.pinfly.purchasecharge.dal.usersecurity;

import java.util.List;

import com.pinfly.purchasecharge.core.model.persistence.usersecurity.Authority;
import com.pinfly.purchasecharge.dal.common.MyGenericDao;

public interface AuthorityDao extends MyGenericDao <Authority, Long>
{
    public Authority findByName (String name);

    public boolean isSystem (String name);

    public List <Authority> findByParent (Long parentId);

}
