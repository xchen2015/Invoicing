package com.pinfly.purchasecharge.framework.session;

import java.io.InvalidObjectException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class used to specify the scope at which attributes can be stored in the
 * <code>FrameworkSession</code>.
 */
public class SessionScope implements Serializable
{
    private static final long serialVersionUID = 1L;

    // This class implements the type safe enum pattern.
    private final static List <SessionScope> s_scopes = new ArrayList <SessionScope> ();

    final static SessionScope APPLICATION_SCOPE = new SessionScope ("Application");
    final static SessionScope PORTLET_SCOPE = new SessionScope ("Portlet");

    private final String m_desc;
    private final int m_index;

    private SessionScope (String desc)
    {
        m_desc = desc;
        m_index = s_scopes.size ();
        s_scopes.add (this);
    }

    public String toString ()
    {
        return "SessionScope: " + m_desc;
    }

    // On deserialization, use one of the instances that
    // was instantiated above, not the deserialized instance
    public Object readResolve () throws InvalidObjectException
    {
        if (m_index < 0 || m_index >= s_scopes.size ())
        {
            throw new InvalidObjectException ("Requested index " + m_index + " is out of valid range: 0 to "
                                              + (s_scopes.size () - 1));
        }

        return s_scopes.get (m_index);
    }

}
