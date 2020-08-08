package com.pinfly.purchasecharge.app.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.filter.GenericFilterBean;

public class MyConcurrentSessionFilter extends GenericFilterBean implements MessageSourceAware
{
    private SessionRegistry sessionRegistry;
    private String expiredUrl;
    private LogoutHandler[] handlers = new LogoutHandler[]
    { new SecurityContextLogoutHandler () };
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy ();
    private MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor ();

    public MyConcurrentSessionFilter ()
    {
    }

    public MyConcurrentSessionFilter (SessionRegistry sessionRegistry)
    {
        this (sessionRegistry, null);
    }

    public MyConcurrentSessionFilter (SessionRegistry sessionRegistry, String expiredUrl)
    {
        this.sessionRegistry = sessionRegistry;
        this.expiredUrl = expiredUrl;
    }

    @Override
    public void setMessageSource (MessageSource messageSource)
    {
        this.messages = new MessageSourceAccessor (messageSource);
    }

    @Override
    public void doFilter (ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
                                                                                     ServletException
    {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        HttpSession session = request.getSession (false);

        if (session != null)
        {
            SessionInformation info = sessionRegistry.getSessionInformation (session.getId ());

            if (info != null)
            {
                if (info.isExpired ())
                {
                    // Expired - abort processing
                    doLogout (request, response);

                    if (expiredUrl != null)
                    {
                        redirectStrategy.sendRedirect (request, response, expiredUrl);

                        return;
                    }
                    else
                    {
                        String loginUrl = request.getContextPath () + "/login.do";
                        String html = "<h3>" + messages.getMessage ("concurrencyLogin.info") + " <a href='" + loginUrl
                                      + "'>" + messages.getMessage ("concurrencyLogin.switchUser") + "</a></h3>";
                        response.setContentType ("text/html;charset=utf-8");
                        response.getWriter ().print (html);
                        response.flushBuffer ();
                    }

                    return;
                }
                else
                {
                    // Non-expired - update last request date/time
                    sessionRegistry.refreshLastRequest (info.getSessionId ());
                }
            }
        }

        chain.doFilter (request, response);
    }

    private void doLogout (HttpServletRequest request, HttpServletResponse response)
    {
        Authentication auth = SecurityContextHolder.getContext ().getAuthentication ();

        for (LogoutHandler handler : handlers)
        {
            handler.logout (request, response, auth);
        }
    }

    public void setSessionRegistry (SessionRegistry sessionRegistry)
    {
        this.sessionRegistry = sessionRegistry;
    }

    public void setExpiredUrl (String expiredUrl)
    {
        this.expiredUrl = expiredUrl;
    }

    public void setRedirectStrategy (RedirectStrategy redirectStrategy)
    {
        this.redirectStrategy = redirectStrategy;
    }

    public void setLogoutHandlers (LogoutHandler[] handlers)
    {
        this.handlers = handlers;
    }

}
