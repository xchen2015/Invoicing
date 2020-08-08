package com.pinfly.purchasecharge.dal.usersecurity;

import com.pinfly.purchasecharge.core.model.persistence.usersecurity.Role;
import com.pinfly.purchasecharge.dal.common.MyGenericDao;

public interface RoleDao extends MyGenericDao <Role, Long>
{
    public Role findByName (String name);

}
