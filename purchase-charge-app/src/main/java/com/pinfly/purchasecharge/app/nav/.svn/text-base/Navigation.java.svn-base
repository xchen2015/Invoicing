package com.pinfly.purchasecharge.app.nav;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

import com.pinfly.purchasecharge.app.nav.NavigationItem.NavigationItemType;
import com.pinfly.purchasecharge.app.security.Utils;
import com.pinfly.purchasecharge.core.model.BaseModel;

/**
 * Class to contain the navigation configuration and track the active page and
 * navigation items at each navigation path.
 */
public class Navigation extends BaseModel
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger (Navigation.class);

    private List <NavigationItem> m_configuration;
    private String m_activePath;
    private NavigationItem m_activePageItem;

    private Map <String, NavigationItem> m_itemMap = new HashMap <String, NavigationItem> ();
    private Map <String, List <NavigationItem>> m_levelMap = new HashMap <String, List <NavigationItem>> ();
    private List <NavigationItem> m_allItems = new ArrayList <NavigationItem> ();

    /**
     * Constructor for a Navigation state instance that clones the given
     * configuration and marks the current active state based on the activePath.
     * 
     * @param configuration A List of NavigationItems that determines the
     *            structure of the navigation.
     * @param activePath The active LABEL or PAGE path as a String.
     */
    public Navigation (List <NavigationItem> configuration, String activePath, ServletContext servletContext)
    {
        m_configuration = configuration;
        m_activePath = activePath;

        Authentication user = SecurityContextHolder.getContext ().getAuthentication ();
        LOGGER.debug ("User: " + String.valueOf (user));

        List <String> levelLabels = new ArrayList <String> ();
        levelLabels.add ("/");

        List <NavigationItem> activeItems = new ArrayList <NavigationItem> ();

        // Create a mapping of the path to a NavigationItem
        for (NavigationItem configItem : m_configuration)
        {
            String itemPath = configItem.getPath ();
            boolean authorized = false;
            try
            {
                if (servletContext != null)
                {
                    authorized = Utils.getPrivilegeEvaluator (servletContext).isAllowed (activePath, itemPath + ".do",
                                                                                         "GET", user);
                }
                else
                {
                    LOGGER.warn ("ServletContext is null and cannot validate url '" + itemPath + "' for user '" + user
                                 + "'. So not authorizing user to access page.");
                }
            }
            catch (IOException e)
            {
                LOGGER.warn ("Error while getting WebPrivilegeEvaluator to validation navigation items for user :"
                             + user, e);
                authorized = false;
            }
            if (!authorized)
            {
                continue;
            }

            // String itemTemplate = configItem.getTemplate ();
            // NavigationItemType itemType = configItem.getType ();
            // String itemTitle = configItem.getTitleCode ();
            // Map <String, List <Widget>> regionWidgetMap =
            // configItem.getRegionWidgetMap ();
            //
            // NavigationItem item = new NavigationItem ();
            // item.setPath (itemPath);
            // item.setTemplate (itemTemplate);
            // item.setType (itemType);
            // item.setTitleCode (itemTitle);
            // item.setRegionWidgetMap (regionWidgetMap);

            NavigationItem item = (NavigationItem) configItem.clone ();
            if (item.getType () == NavigationItemType.LABEL)
            {
                levelLabels.add (itemPath);
            }

            m_allItems.add (item);
            m_itemMap.put (itemPath, item);
        }

        // Create a mapping of label path to a list of NavigationItems at
        // each level starting with the root level.
        for (String levelLabel : levelLabels)
        {
            String levelPrefix = (levelLabel.equals ("/")) ? "/" : levelLabel + "/";
            int level = StringUtils.countOccurrencesOf (levelPrefix, "/");
            List <NavigationItem> subItems = m_levelMap.get (levelLabel);
            if (subItems == null)
            {
                subItems = new ArrayList <NavigationItem> ();
                m_levelMap.put (levelLabel, subItems);
            }
            if (activePath.startsWith (levelLabel))
            {
                if (m_itemMap.get (levelLabel) != null)
                {
                    activeItems.add (m_itemMap.get (levelLabel));
                }
            }
            for (NavigationItem item : m_allItems)
            {
                String path = item.getPath ();
                if (path.startsWith (levelPrefix) && (StringUtils.countOccurrencesOf (path, "/") == level))
                {
                    subItems.add (item);
                }

            }
        }
        LOGGER.debug ("LevelMap: " + m_levelMap.toString ());

        // If the activePath points to a page item,
        // set it as the active page. If the activePath points
        // to a label, get the first item from the
        // levelMap and set it as the active page.
        NavigationItem activePage = null;
        NavigationItem activeItem = m_itemMap.get (activePath);
        if (activeItem != null && activeItem.getType () == NavigationItemType.LABEL)
        {
            // Find the label in the level map and set the first
            // item in the list as the firstPage.
            List <NavigationItem> subItems = m_levelMap.get (activePath);
            if (subItems != null && subItems.size () > 0)
            {
                activePage = subItems.get (0);
            }
        }
        else
        {
            activePage = activeItem;
        }
        if (activePage != null)
        {
            activePage.setActive (true);
            m_activePageItem = activePage;
        }
        LOGGER.debug ("ActivePage: " + activePage);

        // Set the active items
        for (NavigationItem item : activeItems)
        {
            item.setActive (true);
        }
        LOGGER.debug ("ActiveItems: " + activeItems);
    }

    /**
     * Get a List of NavigationItems for the given navigation path.
     * 
     * @param path A navigation path within the configured navigation structure.
     * @return A List of NavigationItems at the given navigation path.
     */
    public List <NavigationItem> getNavigationItems (String path)
    {
        LOGGER.debug ("Requested NavigationItems at Path: " + String.valueOf (path));
        List <NavigationItem> items = new ArrayList <NavigationItem> ();

        List <NavigationItem> subItems = m_levelMap.get (path);
        if (subItems != null)
        {
            items.addAll (subItems);
        }

        LOGGER.debug ("Returning " + (items != null ? items.size () : "no") + " items");
        return items;
    }

    /**
     * Get an ordered List of widgets for the given region name.
     * 
     * @param regionName The region name to lookup widgets for as a String.
     * @return A List of Widget instances that are assigned to the given
     *         regionName.
     */
    public List <Widget> getWidgetsForRegion (String regionName)
    {
        LOGGER.debug ("RegionName: " + String.valueOf (regionName));
        List <Widget> widgets = new ArrayList <Widget> ();

        if (m_activePageItem != null && m_activePageItem.getRegionWidgetMap () != null)
        {
            List <Widget> widgetsForRegion = m_activePageItem.getRegionWidgetMap ().get (regionName);
            if (widgetsForRegion != null)
            {
                widgets.addAll (widgetsForRegion);
            }
            else
            {
                LOGGER.warn ("no widgets configured for regionName " + regionName + " on page " + m_activePageItem);
            }
        }

        LOGGER.debug ("Returning " + (widgets != null ? widgets.size () : "no") + " widgets");
        return widgets;
    }

    /**
     * Get an unordered set of Widgets for the currently active PAGE.
     * 
     * @return A Set of Widget instances for the currently active PAGE.
     */
    public Set <Widget> getActivePageWidgets ()
    {
        Set <Widget> allWidgets = new HashSet <Widget> ();

        if (m_activePageItem != null && m_activePageItem.getRegionWidgetMap () != null)
        {
            for (String regionName : m_activePageItem.getRegionWidgetMap ().keySet ())
            {
                allWidgets.addAll (m_activePageItem.getRegionWidgetMap ().get (regionName));
            }
        }

        LOGGER.debug ("Returning " + (allWidgets != null ? allWidgets.size () : "no") + " widgets");
        return allWidgets;
    }

    /**
     * Get the NavigationItem for the currently active PAGE.
     * 
     * @return A NavigationItem that is active, or null if not valid.
     */
    public NavigationItem getActivePageItem ()
    {
        return m_activePageItem;
    }

    /**
     * Get the page template for the currently active page.
     * 
     * @return The page template name as a String.
     */
    public String getActivePageTemplate ()
    {
        String template = null;
        if (m_activePageItem != null)
        {
            template = m_activePageItem.getTemplate ();
        }

        LOGGER.debug ("ActivePageTemplate: " + String.valueOf (template));
        return template;
    }

    /**
     * Get the path for the currently active page.
     * 
     * @return A navigation path as a String.
     */
    public String getActivePagePath ()
    {
        String path = null;

        if (m_activePageItem != null)
        {
            path = m_activePageItem.getPath ();
        }
        LOGGER.debug ("ActivePagePath: " + String.valueOf (path));
        return path;
    }

    /**
     * Is the currently active page a top level page.
     * 
     * @return true if the currently active page is a top level page (i.e. has
     *         no parent in the label/page hierarchy).
     */
    public boolean isActivePageAtTopLevel ()
    {
        // It is a top level page if there is no / in the path
        // other than the leading /.
        String activePagePath = getActivePagePath ();
        boolean topLevel = (activePagePath != null && activePagePath.indexOf ("/", 1) == -1);

        LOGGER.debug ("Path is '" + String.valueOf (activePagePath) + "' so return value is " + topLevel);
        return topLevel;
    }

    /**
     * The active path for this Navigation instance.
     * 
     * @return A String path for the active page.
     */
    public String getActivePath ()
    {
        LOGGER.debug ("ActivePath: " + String.valueOf (m_activePath));
        return m_activePath;
    }

}
