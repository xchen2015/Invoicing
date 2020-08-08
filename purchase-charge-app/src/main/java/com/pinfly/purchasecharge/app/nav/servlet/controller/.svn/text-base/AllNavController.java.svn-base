package com.pinfly.purchasecharge.app.nav.servlet.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

import com.pinfly.purchasecharge.app.nav.Navigation;
import com.pinfly.purchasecharge.app.nav.NavigationService;

public class AllNavController extends NavController
{
    private static final Logger logger = Logger.getLogger (AllNavController.class);
    private NavigationService subNavigationService;
    private NavigationService helpNavigationService;

    @Override
    protected ModelAndView handleRequestInternal (HttpServletRequest request, HttpServletResponse response)
                                                                                                           throws Exception
    {
        logger.debug ("ServletPath:" + request.getServletPath ());

        String activePath = normalizePath (request.getServletPath ());
        logger.debug ("ActivePath:" + activePath);

        // Lookup the navigation configuration based on the path info
        Navigation subNavigation = subNavigationService.getNavigation (activePath);
        logger.debug ("SubNavigation:" + subNavigation);
        Navigation helpNavigation = helpNavigationService.getNavigation (activePath);
        logger.debug ("HelpNavigation:" + helpNavigation);

        // Get ModelAndView from top navigation
        // Add additional sub navigation for menu
        ModelAndView mav = super.handleRequestInternal (request, response);
        mav.addObject ("subNavigation", subNavigation);
        mav.addObject ("helpNavigation", helpNavigation);

        return mav;
    }

    public NavigationService getSubNavigationService ()
    {
        return subNavigationService;
    }

    public void setSubNavigationService (NavigationService subNavigationService)
    {
        this.subNavigationService = subNavigationService;
    }

    public NavigationService getHelpNavigationService ()
    {
        return helpNavigationService;
    }

    public void setHelpNavigationService (NavigationService helpNavigationService)
    {
        this.helpNavigationService = helpNavigationService;
    }
}
