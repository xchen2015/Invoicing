package com.pinfly.purchasecharge.app.security;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

/**
 * 
 */
public class PreAuthFilter extends AbstractPreAuthenticatedProcessingFilter
{
    private static final Logger logger = Logger.getLogger (PreAuthFilter.class);

    @Override
    protected Object getPreAuthenticatedCredentials (HttpServletRequest request)
    {
        return "DUMMY_NOT_NULL_VALUE";
    }

    @Override
    protected Object getPreAuthenticatedPrincipal (HttpServletRequest request)
    {
        String userId = null;
        try
        {

            if (request.getUserPrincipal () != null)
            {
                logger.info ("preauth tried for session:" + request.getSession ().getId ());
                return null;

            }

            Cookie[] cookies = request.getCookies ();
            // String userEndpoint = null;

            if (cookies != null)
            {
                for (int c = 0; c < cookies.length; c++)
                {
                    String cookieName = cookies[c].getName ();
                    logger.info ("cookieName: " + cookieName);

                }
            }

        }
        catch (Exception e)
        {
            logger.warn ("Error connecting to user enpdoint", e);
        }

        if (userId == null)
        {
            logger.debug ("No user id in session.");
        }

        logger.debug ("Returning no user");
        return null;
    }

}
