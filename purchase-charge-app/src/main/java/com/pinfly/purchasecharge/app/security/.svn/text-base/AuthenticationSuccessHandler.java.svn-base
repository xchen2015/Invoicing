package com.pinfly.purchasecharge.app.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.pinfly.purchasecharge.component.bean.ComponentContext;
import com.pinfly.purchasecharge.component.bean.auditlog.LogEventName;
import com.pinfly.purchasecharge.core.model.LoginUser;

/**
 * Default authentication success handler that sets the CCOW user subject and
 * audits the user login.
 */
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler
{
    private static final Logger logger = Logger.getLogger (AuthenticationSuccessHandler.class);
    private ComponentContext componentContext;

    @Override
    public void onAuthenticationSuccess (HttpServletRequest request, HttpServletResponse response,
                                         Authentication authentication) throws IOException, ServletException
    {
        try
        {
            User user = (User) authentication.getPrincipal ();
            String userId = user.getUsername ();
            LoginUser loginUser = new LoginUser ();
            com.pinfly.purchasecharge.core.model.persistence.usersecurity.User pcUser = componentContext.getQueryService ()
                                                                                                        .getUser (userId);
            loginUser.setUid (pcUser.getId ());
            loginUser.setUserId (user.getUsername ());
            loginUser.setAdmin (pcUser.isAdmin ());
            fillUser (loginUser);
            componentContext.setLoginUserToSession (request, loginUser);
            logger.debug ("User: " + userId + " Login completed");

            try
            {
                componentContext.getLogService ().log (LogEventName.createEventName ("Login", "LogEvent.Login",
                                                                                     request.getLocale ()),
                                                       pcUser.getId (), "");
            }
            catch (Exception e)
            {
            }
        }
        catch (Exception e)
        {
            request.getSession ().invalidate ();
            String failureUrl = Utils.buildLogonFailureUrl (request);
            logger.debug ("Invalidated session. Redirecting to " + failureUrl);
            this.getRedirectStrategy ().sendRedirect (request, response, failureUrl);
        }

        super.onAuthenticationSuccess (request, response, authentication);
    }

    private void fillUser (LoginUser loginUser)
    {
        if (null != loginUser && StringUtils.isNotBlank (loginUser.getUserId ()))
        {
            com.pinfly.purchasecharge.core.model.persistence.usersecurity.User user = componentContext.getQueryService ()
                                                                                                      .getUser (loginUser.getUserId ());
            if (null != user)
            {
                loginUser.setFullName (user.getLastName () + user.getFirstName ());
            }
        }
    }

    public ComponentContext getComponentContext ()
    {
        return componentContext;
    }

    public void setComponentContext (ComponentContext componentContext)
    {
        this.componentContext = componentContext;
    }

}
