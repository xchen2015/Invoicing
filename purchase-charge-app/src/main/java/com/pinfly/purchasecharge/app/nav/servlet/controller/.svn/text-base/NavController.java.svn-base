package com.pinfly.purchasecharge.app.nav.servlet.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.pinfly.purchasecharge.app.nav.Navigation;
import com.pinfly.purchasecharge.app.nav.NavigationService;

/**
 *
 */
public class NavController extends AbstractController
{
    private static final Logger logger = Logger.getLogger (NavController.class);
    private NavigationService navigationService;
    private int maxNavigationLevels = 3;

    @Override
    protected ModelAndView handleRequestInternal (HttpServletRequest request, HttpServletResponse response)
                                                                                                           throws Exception
    {
        // Get the path info part of the request
        // TODO: Not sure how Spring gets the name without the .do
        // This gives /dashboard.do
        logger.debug ("ServletPath:" + request.getServletPath ());

        String activePath = normalizePath (request.getServletPath ());
        logger.debug ("ActivePath:" + activePath);

        // Lookup the navigation configuration based on the path info
        Navigation nav = navigationService.getNavigation (activePath);
        logger.debug ("Navigation:" + nav);

        // Set the layout to use in the view and set the body attribute to
        // include
        ModelAndView mav = new ModelAndView ("template/" + nav.getActivePageTemplate ());
        mav.addObject ("navigation", nav);
        mav.addObject ("maxNavigationLevels", maxNavigationLevels);

        return mav;
    }

    /**
     * Strip of any .* extension.
     * 
     * @param path The path to normalize.
     * @return The given path with any extension removed or the original path.
     */
    protected String normalizePath (String path)
    {
        String result = path;
        if (path != null)
        {
            int idx = path.lastIndexOf (".");
            if (idx != -1)
            {
                result = path.substring (0, idx);
            }
        }

        return result;
    }

    public void setNavigationService (NavigationService navService)
    {
        this.navigationService = navService;
    }

    public void setMaxNavigationLevels (int maxNavigationLevels)
    {
        this.maxNavigationLevels = maxNavigationLevels;
    }

}
