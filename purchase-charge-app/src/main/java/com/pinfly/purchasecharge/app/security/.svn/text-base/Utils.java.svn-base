package com.pinfly.purchasecharge.app.security;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.security.web.PortResolver;
import org.springframework.security.web.PortResolverImpl;
import org.springframework.security.web.access.WebInvocationPrivilegeEvaluator;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Common utilities for security classes.
 */
public class Utils
{
    @SuppressWarnings ("unused")
    private static final Logger logger = Logger.getLogger (Utils.class);

    /**
     * Return the default URL the client used to make the request.
     * 
     * @param request The HTTP request.
     * @return The full URL for the request, or an empty String if request is
     *         null.
     */
    public static String getDefaultURL (HttpServletRequest request)
    {
        return (request != null ? UrlUtils.buildFullRequestUrl (request) : "");
    }

    /**
     * Return the default URL the client used to make the request with optional
     * uri and query string.
     * 
     * @param request The HTTP request.
     * @param uri The URI path portion of the URL to return or null.
     * @param queryString The query string portion of the URL to return or null.
     * @return The full URL for the request, or an empty String if request is
     *         null.
     */
    public static String getDefaultURL (HttpServletRequest request, String uri, String queryString)
    {
        PortResolver portResolver = new PortResolverImpl ();
        return (request != null ? UrlUtils.buildFullRequestUrl (request.getScheme (), request.getServerName (),
                                                                portResolver.getServerPort (request), uri, queryString)
                               : "");
    }

    /**
     * To get WebInvocationPrivilegeEvaluator based on servlet context
     * 
     * @param context
     * @return {@link WebInvocationPrivilegeEvaluator}
     * @throws IOException
     */
    public static WebInvocationPrivilegeEvaluator getPrivilegeEvaluator (ServletContext context) throws IOException
    {
        ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext (context);
        Map <String, WebInvocationPrivilegeEvaluator> wipes = ctx.getBeansOfType (WebInvocationPrivilegeEvaluator.class);

        if (wipes.size () == 0)
        {
            throw new IOException (
                                   "No visible WebInvocationPrivilegeEvaluator instance could be found in the application "
                                           + "context. There must be at least one in order to support the use of URL access checks in 'authorize' tags.");
        }

        return (WebInvocationPrivilegeEvaluator) wipes.values ().toArray ()[0];
    }

    public static String buildLogonFailureUrl (HttpServletRequest request)
    {
        StringBuilder buff = new StringBuilder ().append (request.getContextPath ()).append ('/').append ("login.do");

        return Utils.getDefaultURL (request, buff.toString (), "authfailed=true");
    }

}
