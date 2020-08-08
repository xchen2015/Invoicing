package com.pinfly.purchasecharge.app.security;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.pinfly.common.crypto.CryptoException;
import com.pinfly.common.util.EncryptUtil;
import com.pinfly.purchasecharge.component.bean.ComponentContext;
import com.pinfly.purchasecharge.core.model.UserRole;

/**
 * 该类的主要作用是为Spring Security提供一个经过用户认证后的UserDetails。
 * 该UserDetails包括用户名、密码、是否可用、是否过期等信息。
 * 
 * @author xiang
 */
public class MyUserDetailsService implements UserDetailsService
{
    private static final Logger LOGGER = Logger.getLogger (MyUserDetailsService.class);
    private ComponentContext componentContext;
    private UserCache userCache;

    @Override
    public UserDetails loadUserByUsername (String username) throws UsernameNotFoundException, DataAccessException
    {
        List <GrantedAuthority> auths = new ArrayList <GrantedAuthority> ();

        // 得到用户的权限
        List <UserRole> userAuthorities = componentContext.getQueryService ().getUserRoleByUserId (username);
        if (CollectionUtils.isNotEmpty (userAuthorities))
        {
            for (UserRole userAuthority : userAuthorities)
            {
                SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority (userAuthority.getRole ());
                auths.add (simpleGrantedAuthority);
            }
        }

        // 取得用户的密码
        String password = null;
        com.pinfly.purchasecharge.core.model.persistence.usersecurity.User pcUser = componentContext.getQueryService ()
                                                                                                    .getUser (username);
        LOGGER.info ("UserId: " + pcUser.getUserId () + ", Enable: " + pcUser.isEnabled ());
        if (null != pcUser)
        {
            password = pcUser.getPwd ();
            try
            {
                password = EncryptUtil.decryptWithCryptTool (password);
            }
            catch (CryptoException e)
            {
                LOGGER.warn (e.getMessage (), e);
            }
        }

        return new User (username, password, pcUser.isEnabled (), true, true, true, auths);
    }

    public void setComponentContext (ComponentContext componentContext)
    {
        this.componentContext = componentContext;
    }

    public void setUserCache (UserCache userCache)
    {
        this.userCache = userCache;
    }

}
