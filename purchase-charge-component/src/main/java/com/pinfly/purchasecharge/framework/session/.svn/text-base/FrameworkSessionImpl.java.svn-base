package com.pinfly.purchasecharge.framework.session;

import java.io.Serializable;
import java.util.*;

import org.apache.log4j.Logger;

/**
 * Implementation class for <code>FrameworkSession</code>. The class also
 * implements the <code>FrameworkSessionInternal</code> interface. Methods on
 * this interface are intended for use by the framework itself and should not be
 * used by applications.
 * 
 */
public class FrameworkSessionImpl implements FrameworkSessionInternal
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger (FrameworkSessionImpl.class);

    private static final String PREFIX = "pinflyfwf__";

    private static final InheritableThreadLocal <String> s_namespace = new InheritableThreadLocal <String> ();

    private final Map <String, Serializable> m_attributes = Collections.synchronizedMap (new HashMap <String, Serializable> ());

    public void setAttribute (String name, Serializable value)
    {
        setAttribute (name, value, APPLICATION_SCOPE);
    }

    public void setAttribute (String name, Serializable value, SessionScope scope)
    {
        String key = buildKey (name, scope);
        m_attributes.put (key, value);
    }

    public Serializable getAttribute (String name)
    {
        return getAttribute (name, APPLICATION_SCOPE);
    }

    public Serializable getAttribute (String name, SessionScope scope)
    {
        String key = buildKey (name, scope);
        Serializable value = (Serializable) m_attributes.get (key);

        return value;
    }

    public void removeAttribute (String name)
    {
        removeAttribute (name, APPLICATION_SCOPE);
    }

    public void removeAttribute (String name, SessionScope scope)
    {
        String key = buildKey (name, scope);
        m_attributes.remove (key);
    }

    public boolean containsAttribute (String name)
    {
        return containsAttribute (name, APPLICATION_SCOPE);
    }

    public boolean containsAttribute (String name, SessionScope scope)
    {
        String key = buildKey (name, scope);
        return m_attributes.containsKey (key);
    }

    String buildKey (String name, SessionScope scope)
    {
        String key;
        if (scope == APPLICATION_SCOPE)
        {
            key = name;
        }
        else if (scope == PORTLET_SCOPE)
        {
            String namespace = (String) s_namespace.get ();
            if (namespace == null)
            {
                throw new IllegalStateException (
                                                 "Request to store/get attribute at portlet scope but no portlet namespace is available");
            }
            key = namespace + '_' + name;
        }
        else
        {
            throw new IllegalArgumentException ("Unknown session scope: " + scope);
        }

        return key;
    }

    public void setNamespace (String namespace)
    {
        if (namespace == null || namespace.length () == 0)
        {
            logger.warn ("Requested namespace is null; no current namespace will be available");
            return;
        }

        if (namespace.startsWith (PREFIX))
        {
            // Carefx namespace
            int delim = namespace.lastIndexOf ('_');
            if (delim == -1)
            {
                logger.warn ("Couldn't find patient identifier in framework namespace " + namespace);
            }
            else
            {
                String ns = namespace.substring (PREFIX.length (), delim);
                s_namespace.set (ns);
            }
        }
        else
        {
            // Just a portlet namespace
            s_namespace.set (namespace);
        }
    }

    public String getNamespace ()
    {
        String ns = (String) s_namespace.get ();
        if (ns == null)
        {
            ns = "";
        }
        return ns;
    }

    public void clearNamespace ()
    {
        s_namespace.set (null);
    }

}
