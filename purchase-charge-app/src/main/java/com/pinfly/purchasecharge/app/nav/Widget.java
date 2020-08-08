package com.pinfly.purchasecharge.app.nav;

import java.io.Serializable;

/**
 * Class to hold the widgetClass name and the uniqueName to generate the
 * placement on the layout. Optionally, if type is set, it will render the
 * Widget as a span instead of a div.
 */
public class Widget implements Serializable
{
    private static final long serialVersionUID = 1L;
    private String m_uniqueName;
    private String m_type;

    private String getViewServletPath;
    private String getJsServletPath;
    private String getCssServletPath;

    public String getGetViewServletPath ()
    {
        return getViewServletPath;
    }

    public void setGetViewServletPath (String getViewServletPath)
    {
        this.getViewServletPath = getViewServletPath;
    }

    public String getUniqueName ()
    {
        return m_uniqueName;
    }

    public void setUniqueName (String uniqueName)
    {
        this.m_uniqueName = uniqueName;
    }

    public String getType ()
    {
        return m_type;
    }

    public void setType (String type)
    {
        m_type = type;
    }

    public String getGetJsServletPath ()
    {
        return getJsServletPath;
    }

    public void setGetJsServletPath (String getJsServletPath)
    {
        this.getJsServletPath = getJsServletPath;
    }

    public String getGetCssServletPath ()
    {
        return getCssServletPath;
    }

    public void setGetCssServletPath (String getCssServletPath)
    {
        this.getCssServletPath = getCssServletPath;
    }

    @Override
    public String toString ()
    {
        StringBuilder builder = new StringBuilder ();
        builder.append ("Widget [getViewServletPath=");
        builder.append (getViewServletPath);
        builder.append (", m_uniqueName=");
        builder.append (m_uniqueName);
        builder.append (", m_type=");
        builder.append (m_type);
        builder.append ("]");
        return builder.toString ();
    }

    @Override
    public int hashCode ()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((m_uniqueName == null) ? 0 : m_uniqueName.hashCode ());
        result = prime * result + ((getViewServletPath == null) ? 0 : getViewServletPath.hashCode ());
        return result;
    }

    @Override
    public boolean equals (Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass () != obj.getClass ())
        {
            return false;
        }
        Widget other = (Widget) obj;
        if (m_uniqueName == null)
        {
            if (other.m_uniqueName != null)
            {
                return false;
            }
        }
        else if (!m_uniqueName.equals (other.m_uniqueName))
        {
            return false;
        }
        if (getViewServletPath == null)
        {
            if (other.getViewServletPath != null)
            {
                return false;
            }
        }
        else if (!getViewServletPath.equals (other.getViewServletPath))
        {
            return false;
        }
        return true;
    }

}
