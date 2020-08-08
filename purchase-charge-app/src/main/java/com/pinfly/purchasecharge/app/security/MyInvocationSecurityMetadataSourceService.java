package com.pinfly.purchasecharge.app.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.RequestMatcher;

import com.pinfly.purchasecharge.component.bean.ComponentContext;
import com.pinfly.purchasecharge.core.config.PurchaseChargeProperties;
import com.pinfly.purchasecharge.core.model.persistence.usersecurity.Authority;
import com.pinfly.purchasecharge.core.model.persistence.usersecurity.Role;
import com.pinfly.purchasecharge.core.model.persistence.usersecurity.User;

/**
 * 最核心的地方，就是提供某个资源对应的权限定义，即getAttributes方法返回的结果。 此类在初始化时，应该取到所有资源及其对应角色的定义。
 * 
 */
public class MyInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource,
                                                      InitializingBean
{
    private static final Logger LOGGER = Logger.getLogger (MyInvocationSecurityMetadataSourceService.class);
    private ComponentContext componentContext;
    private boolean initialized;
    private SecurityAuthorityList securityAuthorityList;
    private Map <RequestMatcher, Collection <ConfigAttribute>> resourceMap = new HashMap <RequestMatcher, Collection <ConfigAttribute>> ();

    @Override
    public Collection <ConfigAttribute> getAllConfigAttributes ()
    {
        LOGGER.debug ("getAllConfigAttributes executed");
        Set <ConfigAttribute> allAttributes = new HashSet <ConfigAttribute> ();
        for (Map.Entry <RequestMatcher, Collection <ConfigAttribute>> entry : resourceMap.entrySet ())
        {
            allAttributes.addAll (entry.getValue ());
        }
        return allAttributes;
    }

    // 根据URL，找到相关的权限配置。
    @Override
    public Collection <ConfigAttribute> getAttributes (Object object) throws IllegalArgumentException
    {
        resourceMap = componentContext.getSecurityResourceMap (false);
        // object 是一个URL，被用户请求的url
        final HttpServletRequest request = ((FilterInvocation) object).getRequest ();
        String requestUrl = ((FilterInvocation) object).getRequestUrl ();
        int firstQuestionMarkIndex = requestUrl.indexOf ("?");
        if (firstQuestionMarkIndex > 0)
        {
            requestUrl = requestUrl.substring (0, firstQuestionMarkIndex);
        }
        LOGGER.debug ("Request url: " + requestUrl);

        if (requestUrl.endsWith (".html") || requestUrl.endsWith (".do")
            || (requestUrl.startsWith ("/pdf") && -1 == requestUrl.lastIndexOf (".")))
        {
            Iterator <RequestMatcher> ite = resourceMap.keySet ().iterator ();
            while (ite.hasNext ())
            {
                RequestMatcher requestMatcher = ite.next ();
                if (requestMatcher.matches (request))
                {
                    return resourceMap.get (requestMatcher);
                }
            }
        }

        return null;
    }

    @Override
    public boolean supports (Class <?> arg0)
    {
        return true;
    }

    public void setComponentContext (ComponentContext componentContext)
    {
        this.componentContext = componentContext;
    }

    public void setInitialized (boolean initialized)
    {
        this.initialized = initialized;
    }

    public void setSecurityAuthorityList (SecurityAuthorityList securityAuthorityList)
    {
        this.securityAuthorityList = securityAuthorityList;
    }

    @Override
    public void afterPropertiesSet () throws Exception
    {
        resourceMap = componentContext.getSecurityResourceMap (false);
        initUserRoleAuthority ();
    }

    private void cleanInitialAuthority ()
    {
        List <Authority> authorities = componentContext.getQueryService ().getAllAuthority ();
        componentContext.getPersistenceService ().deleteAuthority (authorities);
    }

    private void initUserRoleAuthority ()
    {
        if (this.initialized)
        {
            cleanInitialAuthority ();

            try
            {
                List <Authority> dbAuthorities = new ArrayList <Authority> ();
                if (null != this.securityAuthorityList)
                {
                    LOGGER.info ("application securityAuthorityList size: "
                                 + this.securityAuthorityList.getSecurityAuthorities ().size ());
                    storeAuthorities (this.securityAuthorityList.getSecurityAuthorities (), null);
                    dbAuthorities = componentContext.getQueryService ().getAllAuthority ();

                    if (CollectionUtils.isNotEmpty (dbAuthorities))
                    {
                        String adminRole = PurchaseChargeProperties.getDefaultRole ();
                        List <Role> dbRoles = new ArrayList <Role> ();
                        Role dbRole = componentContext.getQueryService ().getRole (adminRole);
                        if (null == dbRole)
                        {
                            dbRole = new Role (adminRole);
                            dbRole.setAuthorities (dbAuthorities);
                            dbRole.setEnabled (true);
                            dbRoles.add (dbRole);
                            dbRoles = componentContext.getPersistenceService ().addRoles (dbRoles);
                        }
                        else
                        {
                            dbRole.setAuthorities (dbAuthorities);
                            dbRoles.add (dbRole);
                            dbRoles = componentContext.getPersistenceService ().addRoles (dbRoles);
                        }

                        if (CollectionUtils.isNotEmpty (dbRoles))
                        {
                            List <User> users = new ArrayList <User> ();
                            String adminUser = PurchaseChargeProperties.getDefaultUser ();
                            User defaultUser = componentContext.getQueryService ().getUser (adminUser);
                            if (null == defaultUser)
                            {
                                String adminPwd = PurchaseChargeProperties.getDefaultPassword ();
                                User user = new User (adminUser, adminPwd);
                                user.setRoles (dbRoles);
                                user.setEnabled (true);
                                user.setAdmin (true);
                                user.setFirstName (adminUser);
                                user.setLastName (adminUser);
                                users.add (user);
                                componentContext.getPersistenceService ().addUsers (users);
                            }
                            else
                            {
                                defaultUser.setRoles (dbRoles);
                                users.add (defaultUser);
                                componentContext.getPersistenceService ().addUsers (users);
                            }
                        }
                    }
                }
            }
            catch (Exception e)
            {
                LOGGER.warn ("initUserRoleAuthority initialized failed", e);
            }
        }
    }

    private void storeAuthorities (List <SecurityAuthority> securityAuthorities,
                                   SecurityAuthority parentSecurityAuthority)
    {
        List <Authority> authorities;
        List <Authority> topAuthorities;
        Authority authority = null;
        for (SecurityAuthority securityAuthority : securityAuthorities)
        {
            if (securityAuthority.isEnabled ()
                && null == componentContext.getQueryService ().getAuthority (securityAuthority.getName ()))
            {
                authority = new Authority (securityAuthority.getName ());
                authority.setUrl (securityAuthority.getUrl ());
                authority.setEnabled (true);
                if (null != parentSecurityAuthority)
                {
                    Authority parentAuthority = componentContext.getQueryService ()
                                                                .getAuthority (parentSecurityAuthority.getName ());
                    if (null == parentAuthority)
                    {
                        topAuthorities = new ArrayList <Authority> ();
                        topAuthorities.add (authority);
                        componentContext.getPersistenceService ().addAuthorities (topAuthorities);
                    }
                    else
                    {
                        authority.setParent (parentAuthority);
                        authorities = new ArrayList <Authority> ();
                        authorities.add (authority);
                        componentContext.getPersistenceService ().addAuthorities (authorities);
                    }
                }
                else
                {
                    topAuthorities = new ArrayList <Authority> ();
                    topAuthorities.add (authority);
                    componentContext.getPersistenceService ().addAuthorities (topAuthorities);
                }
                if (CollectionUtils.isNotEmpty (securityAuthority.getAuthorities ()))
                {
                    storeAuthorities (securityAuthority.getAuthorities (), securityAuthority);
                }
            }
        }
    }

}
