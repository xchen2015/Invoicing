package com.pinfly.purchasecharge.framework.interceptor;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.pinfly.purchasecharge.framework.utils.MessageSourceResolvableException;

public class SessionValidationInterceptor extends HandlerInterceptorAdapter implements InitializingBean
{
    private static final Logger logger = Logger.getLogger (SessionValidationInterceptor.class.getName ());

    private String m_errorPage;
    private static final String DEFAULT_ERROR_PAGE = "/timeout.jsp";

    /**
     * Set the path to the jsp page that will be included in the response if
     * there is no patient. An instance of MessageSourceResolvableException that
     * describes the error will be placed in the exception request attribute
     * prior to invoking this page
     */
    public void setErrorPage (String errorPage)
    {
        m_errorPage = errorPage;
    }

    /**
     * Called by the framework after all the properties have been set.
     * <p>
     * This method is public as an implemenation side effect. It should not be
     * called except by the framework.
     */
    public void afterPropertiesSet ()
    {
        if (m_errorPage == null)
        {
            logger.warn ("No error page defined.  Using default of " + DEFAULT_ERROR_PAGE);
            m_errorPage = DEFAULT_ERROR_PAGE;
        }
    }

    public boolean preHandle (HttpServletRequest request, HttpServletResponse response, Object handler)
                                                                                                       throws Exception
    {
        boolean ok = true;
        try
        {
            HttpSession session = request.getSession (false);
            if (session == null)
            {
                throw new MessageSourceResolvableException ("framework.sessionTimeout");
            }
        }
        catch (MessageSourceResolvableException e)
        {
            RequestDispatcher rd = request.getRequestDispatcher (m_errorPage);
            // request.setAttribute (RequestNames.EXCEPTION, e);
            rd.include (request, response);
            ok = false;
        }

        return ok;
    }

}
