package com.pinfly.purchasecharge.app.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

/**
 * Default login failure handler that audits the user login attempt. Subclasses
 * should override onAuthenticationFailure(HttpServletRequest,
 * HttpServletResponse, AuthenticationException) and call
 * super.onAuthenticationFailure(HttpServletRequest, HttpServletResponse,
 * AuthenticationException).
 * 
 * @see AuthenticationFailureHandler
 */
public class BaseAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler
{
    private static final Logger logger = Logger.getLogger (BaseAuthenticationFailureHandler.class);

    @Override
    public void onAuthenticationFailure (HttpServletRequest request, HttpServletResponse response,
                                         AuthenticationException exception) throws IOException, ServletException
    {
        try
        {
            logger.debug ("Authentication Failed: " + exception.getMessage ());
        }
        catch (Exception badaudit)
        {
            logger.warn ("Failed to audit the failed login event", badaudit);
        }

        super.onAuthenticationFailure (request, response, exception);
    }

}
