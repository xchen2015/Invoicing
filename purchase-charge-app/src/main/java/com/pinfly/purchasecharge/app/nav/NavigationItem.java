package com.pinfly.purchasecharge.app.nav;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class NavigationItem implements Serializable, Cloneable
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger (NavigationItem.class);

    private String m_path;
    private String m_titleCode;
    private NavigationItemType m_type;
    private String m_template;
    private boolean m_active;
    private Map <String, List <Widget>> m_regionWidgetMap;

    public String getPath ()
    {
        return m_path;
    }

    public void setPath (String path)
    {
        this.m_path = path;
    }

    public String getTitleCode ()
    {
        return m_titleCode;
    }

    public void setTitleCode (String titleCode)
    {
        this.m_titleCode = titleCode;
    }

    public NavigationItemType getType ()
    {
        return m_type;
    }

    public void setType (NavigationItemType type)
    {
        this.m_type = type;
    }

    public String getTemplate ()
    {
        return m_template;
    }

    public void setTemplate (String template)
    {
        this.m_template = template;
    }

    public boolean isHidden ()
    {
        return NavigationItemType.HIDDEN == getType ();
    }

    public boolean isActive ()
    {
        return m_active;
    }

    public void setActive (boolean active)
    {
        this.m_active = active;
    }

    public Map <String, List <Widget>> getRegionWidgetMap ()
    {
        return m_regionWidgetMap;
    }

    public void setRegionWidgetMap (Map <String, List <Widget>> regionWidgetMap)
    {
        this.m_regionWidgetMap = regionWidgetMap;
    }

    @Override
    public Object clone ()
    {
        NavigationItem newItem = null;
        try
        {
            newItem = (NavigationItem) super.clone ();
            // The only thing we need to make a copy of is the region map
            if (m_regionWidgetMap != null)
            {
                Map <String, List <Widget>> newMap = new HashMap <String, List <Widget>> (m_regionWidgetMap.size ());
                for (Map.Entry <String, List <Widget>> entry : m_regionWidgetMap.entrySet ())
                {
                    // We cheat a little bit here by not actually copying the
                    // Widget
                    List <Widget> newList = new ArrayList <Widget> (entry.getValue ());
                    newMap.put (entry.getKey (), newList);
                }
                m_regionWidgetMap = newMap;
            }
        }
        catch (CloneNotSupportedException e)
        {
            // Can't happen - we do support clone
            LOGGER.warn ("Impossible exception thrown!", e);
        }

        return newItem;
    }

    @Override
    public String toString ()
    {
        StringBuilder builder = new StringBuilder ();
        builder.append ("NavigationItem [path=");
        builder.append (m_path);
        builder.append (", title=");
        builder.append (m_titleCode);
        builder.append (", type=");
        builder.append (m_type);
        builder.append (", template=");
        builder.append (m_template);
        builder.append (", active=");
        builder.append (m_active);
        builder.append ("]");
        return builder.toString ();
    }

    public enum NavigationItemType
    {
        PAGE, LABEL, URL, HIDDEN;
    }

}
