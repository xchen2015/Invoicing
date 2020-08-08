package com.pinfly.purchasecharge.app.nav;

import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * An implementation of the NavigationService that takes its configuration and
 * constructs Navigation instances.
 */
public class SimpleNavigationService implements NavigationService
{
    private List <NavigationItem> m_configuration;
    /** Servlet Context object */
    @Autowired
    private ServletContext m_servletContext;

    /*
     * (non-Javadoc)
     * 
     * @see
     * net.carefx.gwt.framework.app.nav.NavigationService#getNavigation(java
     * .lang.String)
     */
    public Navigation getNavigation (String activePath)
    {
        return new Navigation (m_configuration, activePath, m_servletContext);
    }

    public void setConfiguration (List <NavigationItem> config)
    {
        this.m_configuration = config;
    }

}
