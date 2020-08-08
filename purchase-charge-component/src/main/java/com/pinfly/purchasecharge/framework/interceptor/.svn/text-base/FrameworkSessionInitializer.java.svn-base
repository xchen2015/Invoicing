package com.pinfly.purchasecharge.framework.interceptor;

import java.io.Serializable;
import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.pinfly.purchasecharge.framework.session.FrameworkSessionInternal;
import com.pinfly.purchasecharge.framework.session.FrameworkSessionItem;
import com.pinfly.purchasecharge.framework.utils.RequestNames;

public class FrameworkSessionInitializer extends HandlerInterceptorAdapter implements Serializable
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger (FrameworkSessionInitializer.class);

    @Autowired
    // this is a session scoped proxy
    private FrameworkSessionInternal frameworkSession;

    @Override
    public boolean preHandle (HttpServletRequest request, HttpServletResponse response, Object handler)
    {
        logger.debug ("Processing interceptor: " + this.toString () + " for request " + request.getRequestURL ());

        request.setAttribute (RequestNames.FRAMEWORK_SESSION, frameworkSession);

        String portalUserId = FrameworkSessionItem.PORTAL_USER_ID.get (frameworkSession);
        if (portalUserId == null)
        {
            Principal principal = request.getUserPrincipal ();
            if (principal != null)
            {
                portalUserId = principal.getName ();
            }
            else
            {
                portalUserId = "UNKNOWN";
            }
            if (logger.isDebugEnabled ())
            {
                logger.debug ("Set portal user to " + portalUserId);
            }
            FrameworkSessionItem.PORTAL_USER_ID.set (frameworkSession, portalUserId);
        }
        return true;
    }

    @Override
    public void afterCompletion (HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
    {
    }

}
