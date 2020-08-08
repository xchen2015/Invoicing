package com.pinfly.purchasecharge.app.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import com.pinfly.purchasecharge.component.bean.ComponentContext;
import com.pinfly.purchasecharge.component.bean.auditlog.LogEventName;

/**
 * Default logout success handler that audits the user logout event. Subclasses
 * should override onLogoutSuccess(HttpServletRequest, HttpServletResponse,
 * Authentication) and call super.onLogoutSuccess(HttpServletRequest,
 * HttpServletResponse, Authentication).
 * 
 */
public class BaseLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler
{
    private static final Logger logger = Logger.getLogger (BaseLogoutSuccessHandler.class);
    private ComponentContext componentContext;

    @Override
    public void onLogoutSuccess (HttpServletRequest request, HttpServletResponse response, Authentication authentication)
                                                                                                                         throws IOException,
                                                                                                                         ServletException
    {
        if (null != authentication)
        {
            User user = (User) authentication.getPrincipal ();
            String userId = user.getUsername ();
            componentContext.setLoginUserToSession (request, null);
            logger.debug ("User: " + userId + " Logout completed");

            try
            {
                com.pinfly.purchasecharge.core.model.persistence.usersecurity.User pcUser = componentContext.getQueryService ()
                                                                                                            .getUser (userId);
                componentContext.getLogService ().log (LogEventName.createEventName ("Logout", "LogEvent.Logout",
                                                                                     request.getLocale ()),
                                                       pcUser.getId (), "");
            }
            catch (Exception e)
            {
            }
        }
        super.onLogoutSuccess (request, response, authentication);
    }

    public void setComponentContext (ComponentContext componentContext)
    {
        this.componentContext = componentContext;
    }

}
