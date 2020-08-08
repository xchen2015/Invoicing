package com.pinfly.purchasecharge.app.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import com.pinfly.purchasecharge.component.bean.ActionResult;
import com.pinfly.purchasecharge.component.bean.ComponentContext;
import com.pinfly.purchasecharge.component.utils.AjaxUtils;
import com.pinfly.purchasecharge.core.model.LoginUser;

/**
 * 该过滤器的主要作用就是通过spring著名的IoC生成securityMetadataSource。
 * securityMetadataSource相当于本包中自定义的MyInvocationSecurityMetadataSourceService。
 * 该MyInvocationSecurityMetadataSourceService的作用提从数据库提取权限和资源，装配到HashMap中，
 * 供Spring Security使用，用于权限校验。
 * 
 * @author xiang
 * 
 */
public class MyFilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter
{
    private static final String FILTER_APPLIED = "__spring_security_filterSecurityInterceptor_filterApplied";
    private FilterInvocationSecurityMetadataSource securityMetadataSource;
    private boolean observeOncePerRequest = true;
    private ComponentContext componentContext;

    public void doFilter (ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
                                                                                              ServletException
    {
        FilterInvocation fi = new FilterInvocation (request, response, chain);
        if ((fi.getRequest () != null) && (fi.getRequest ().getAttribute (FILTER_APPLIED) != null)
            && observeOncePerRequest)
        {
            // filter already applied to this request and user wants us to
            // observe
            // once-per-request handling, so don't re-do security checking
            fi.getChain ().doFilter (fi.getRequest (), fi.getResponse ());
        }
        else
        {
            // first time this request being called, so perform security
            // checking
            if (fi.getRequest () != null)
            {
                fi.getRequest ().setAttribute (FILTER_APPLIED, Boolean.TRUE);
            }

            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            InterceptorStatusToken token = null;
            try
            {
                token = super.beforeInvocation (fi);
            }
            catch (AccessDeniedException accessDeniedException)
            {
                LoginUser loginUser = componentContext.getLoginUser (httpServletRequest);
                if (null != loginUser)
                {
                    ActionResult ar = ActionResult.unauthorized ()
                                                  .withMessage (messages.getMessage ("accessDeny.info")).build ();
                    response.setContentType ("text/html;charset=utf-8");
                    response.getWriter ().print (AjaxUtils.getJsonObject (ar));
                }
                throw accessDeniedException;
            }

            try
            {
                fi.getChain ().doFilter (fi.getRequest (), fi.getResponse ());
            }
            finally
            {
                super.finallyInvocation (token);
            }

            super.afterInvocation (token, null);
        }
    }

    @SuppressWarnings ("unused")
    private boolean isAnonymousUser (Authentication authentication)
    {
        // "anonymousUser", AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS")
        if (null != authentication)
        {
            if ("anonymousUser".equalsIgnoreCase (authentication.getPrincipal ().toString ()))
            {
                if (AuthorityUtils.createAuthorityList ("ROLE_ANONYMOUS")
                                  .containsAll (authentication.getAuthorities ()))
                {
                    return true;
                }
            }
        }
        return false;
    }

    public FilterInvocationSecurityMetadataSource getSecurityMetadataSource ()
    {
        return this.securityMetadataSource;
    }

    public Class <? extends Object> getSecureObjectClass ()
    {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource ()
    {
        return this.securityMetadataSource;
    }

    public void setSecurityMetadataSource (FilterInvocationSecurityMetadataSource securityMetadataSource)
    {
        this.securityMetadataSource = securityMetadataSource;
    }

    public void setComponentContext (ComponentContext componentContext)
    {
        this.componentContext = componentContext;
    }

    public void destroy ()
    {

    }

    public void init (FilterConfig filterconfig) throws ServletException
    {

    }

}
