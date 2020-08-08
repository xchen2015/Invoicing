package com.pinfly.purchasecharge.dal.usersecurity;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pinfly.purchasecharge.core.model.RoleAuthority;
import com.pinfly.purchasecharge.core.model.UserRole;
import com.pinfly.purchasecharge.core.model.persistence.usersecurity.User;
import com.pinfly.purchasecharge.dal.common.MyGenericDao;

public interface UserDao extends MyGenericDao <User, Long>
{
    public User findByUserId (String userId);

    public int updatePassword (String userId, String password);

    public long getUniqueIdByUserId (String userId);

    public List <User> findByName (String name);

    Page <User> findByFuzzy (Pageable pageable, String searchKey);

    List <UserRole> findUserRoleByUserId (String userId);

    List <RoleAuthority> findRoleAuthorityByRole (String role);

}
