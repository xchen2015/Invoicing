package com.pinfly.purchasecharge.dal.impl.usersecurity;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.pinfly.purchasecharge.core.model.RoleAuthority;
import com.pinfly.purchasecharge.core.model.UserRole;
import com.pinfly.purchasecharge.core.model.persistence.usersecurity.User;
import com.pinfly.purchasecharge.dal.common.GenericDaoUtils;
import com.pinfly.purchasecharge.dal.common.MyGenericDaoImpl;
import com.pinfly.purchasecharge.dal.usersecurity.UserDao;

public class UserDaoImpl extends MyGenericDaoImpl <User, Long> implements UserDao
{
    private static final Logger logger = Logger.getLogger (UserDaoImpl.class);

    public UserDaoImpl ()
    {
        super (User.class);
    }

    @Override
    public User findByUserId (String userId)
    {
        String sql = "select u from User u where u.userId=?1";
        Query query = getEntityManager ().createQuery (sql, User.class);
        query.setParameter (1, userId);
        return CollectionUtils.isNotEmpty (query.getResultList ()) ? (User) query.getResultList ().get (0) : null;
    }

    @Override
    @Transactional
    public int updatePassword (String userId, String password)
    {
        String sql = "update User u set u.pwd = ?2 where u.userId = ?1";
        Query query = getEntityManager ().createQuery (sql);
        query.setParameter (1, userId);
        query.setParameter (2, password);
        return query.executeUpdate ();
    }

    @Override
    public long getUniqueIdByUserId (String userId)
    {
        String sql = "select u.id from User u where u.userId=?1";
        Query query = getEntityManager ().createQuery (sql);
        query.setParameter (1, userId);
        return CollectionUtils.isNotEmpty (query.getResultList ()) ? Long.valueOf (query.getResultList ().get (0)
                                                                                        .toString ()) : 0;
    }

    @Override
    public List <User> findByName (String name)
    {
        logger.debug ("Find user by name: " + name);
        String key = (null == name ? "" : name.trim ());
        // String sql =
        // "select u from User u where u.userId like ?1 or u.firstName like ?2 or u.lastName like ?3 order by u.userId";
        String sql = "select * from pc_user u where u.userId like ?1 or u.firstName like ?2 or u.lastName like ?3 order by convert(u.userId USING gbk) COLLATE gbk_chinese_ci asc";
        Query query = getEntityManager ().createNativeQuery (sql, User.class);
        query.setParameter (1, key + "%");
        query.setParameter (2, key + "%");
        query.setParameter (3, key + "%");
        @SuppressWarnings ("unchecked")
        List <User> users = query.getResultList ();
        return users;
    }

    @Override
    public Page <User> findByFuzzy (Pageable pageable, String searchKey)
    {
        String pageableLog = "offSet:" + pageable.getOffset () + ",pageNumber:" + pageable.getPageNumber ()
                             + ",pageSize:" + pageable.getPageSize () + ",sort:" + pageable.getSort ();
        logger.debug ("Find paging user by fuzzy, pageable: (" + pageableLog + "), searchkey: " + searchKey);
        String keyWord = (null == searchKey ? "" : searchKey.trim ());
        String selectCountSql = "SELECT count(u) ";
        String fromSql = "FROM User u where " + "u.userId like ?1 or " + "u.firstName like ?2 or "
                         + "u.lastName like ?3 or " + "u.mobilePhone like ?4 or " + "u.email like ?5";
        Query countQuery = getEntityManager ().createQuery (selectCountSql + fromSql);
        countQuery.setParameter (1, keyWord + "%");
        countQuery.setParameter (2, keyWord + "%");
        countQuery.setParameter (3, keyWord + "%");
        countQuery.setParameter (4, keyWord + "%");
        countQuery.setParameter (5, keyWord + "%");
        int total = Integer.parseInt (countQuery.getResultList ().get (0).toString ());

        String querySql = "SELECT u ";
        querySql += fromSql;
        String orderBySql = " ORDER BY ";
        querySql += orderBySql;

        String sortSqlStr = "u.userId asc";
        try
        {
            sortSqlStr = GenericDaoUtils.parseSort (User.class, pageable, "u");
        }
        catch (Exception e)
        {
            // TODO: handle exception
        }
        querySql += sortSqlStr;

        Query query = getEntityManager ().createQuery (querySql);
        query.setParameter (1, keyWord + "%");
        query.setParameter (2, keyWord + "%");
        query.setParameter (3, keyWord + "%");
        query.setParameter (4, keyWord + "%");
        query.setParameter (5, keyWord + "%");

        query.setFirstResult (pageable.getOffset ());
        query.setMaxResults (pageable.getPageSize ());

        @SuppressWarnings ("unchecked")
        List <User> userLists = query.getResultList ();
        Page <User> userPage = new PageImpl <User> (userLists, pageable, total);
        return userPage;
    }

    @Override
    public List <UserRole> findUserRoleByUserId (String userId)
    {
        logger.debug ("Find UserAuthority by userId: " + userId);
        if (StringUtils.isNotBlank (userId))
        {
            String sql = "select u.userId as user, r.name as role from pc_role r, pc_user u, pc_user_role ur where u.id=ur.user_id and r.id=ur.role_id and u.userId=?1";
            Query query = getEntityManager ().createNativeQuery (sql, UserRole.class);
            query.setParameter (1, userId);
            @SuppressWarnings ("unchecked")
            List <UserRole> userAuthorities = query.getResultList ();
            return userAuthorities;
        }
        return null;
    }

    @Override
    public List <RoleAuthority> findRoleAuthorityByRole (String role)
    {
        logger.debug ("Find RoleAuthority by role: " + role);
        if (StringUtils.isNotBlank (role))
        {
            String sql = "select r.name as role, a.name as authorityName, a.url as authorityValue from pc_authority a, pc_role r, pc_role_authority ar where a.id=ar.authority_id and r.id=ar.role_id and r.name=?1";
            Query query = getEntityManager ().createNativeQuery (sql, RoleAuthority.class);
            query.setParameter (1, role);
            @SuppressWarnings ("unchecked")
            List <RoleAuthority> roleAuthorities = query.getResultList ();
            return roleAuthorities;
        }
        return null;
    }

}
