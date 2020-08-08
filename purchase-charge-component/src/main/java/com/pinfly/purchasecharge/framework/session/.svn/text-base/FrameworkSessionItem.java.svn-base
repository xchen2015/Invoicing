package com.pinfly.purchasecharge.framework.session;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import com.pinfly.purchasecharge.framework.utils.RequestNames;

public class FrameworkSessionItem <T>
{
    /**
     * FrameworkSessionItem containing the user's portal user id.
     * <p>
     * Scope: Application
     */
    public static final FrameworkSessionItem <String> PORTAL_USER_ID = new FrameworkSessionItem <String> (
                                                                                                          "com.pinfly.framework.portalUserId",
                                                                                                          SessionScope.APPLICATION_SCOPE);

    protected final String name;
    protected final SessionScope scope;

    /**
     * Create a FrameworkSessionItem with the specified name and scope.
     * 
     * @param name The name under which the item will be stored in the
     *            FrameworkSession
     * @param scope The scope at with the item will be stored in the
     *            FrameworkSession
     */
    protected FrameworkSessionItem (String name, SessionScope scope)
    {
        this.name = name;
        this.scope = scope;
    }

    /**
     * Get the name associated with this FrameworkSessionItem
     * 
     * @return The attribute name for this item.
     */
    public String getName ()
    {
        return name;
    }

    /**
     * Get the scope associated with this FrameworkSessionItem
     * 
     * @return The scope for this item.
     */
    public SessionScope getScope ()
    {
        return scope;
    }

    /**
     * Get the FrameworkSession from a servlet request.
     * 
     * @param request The request from which to retrieve the FrameworkSession
     * @return The FrameworkSession from the request
     * @throws NullPointerException if request is null
     * @throws IllegalArgumentException if there is no FrameworkSession
     *             associated with the request
     */
    protected FrameworkSession getSession (HttpServletRequest request)
    {
        if (request == null)
        {
            throw new NullPointerException ("request cannot be null");
        }
        FrameworkSession sess = (FrameworkSession) request.getAttribute (RequestNames.FRAMEWORK_SESSION);
        if (sess == null)
        {
            throw new IllegalStateException ("No FrameworkSession available in request");
        }
        return sess;
    }

    /**
     * Checks to see if a FrameworkSession reference is null.
     * 
     * @param sess The reference to check
     * @throws NullPointerException if sess is null
     */
    protected void validateSession (FrameworkSession sess)
    {
        if (sess == null)
        {
            throw new NullPointerException ("session cannot be null");
        }
    }

    /**
     * Get the value of this item from the FrameworkSession.
     * 
     * @param request An HttpServletRequest that contains a FrameworkSession
     *            attribute
     * @return The value of this item in the FrameworkSession. Returns null if
     *         this item is not present in the FrameworkSession.
     * @throws NullPointerException if request is null
     * @throws InvalidArgumentException if the request does not contain a
     *             FrameworkSession
     */
    public T get (HttpServletRequest request)
    {
        return get (getSession (request));
    }

    /**
     * Get the value of this item from the FrameworkSession.
     * 
     * @param sess The FrameworkSession from which to retrieve the value
     * @return The value of this item in the FrameworkSession. Returns null if
     *         this item is not present in the FrameworkSession.
     * @throws NullPointerException if sess is null
     */
    public T get (FrameworkSession sess)
    {
        validateSession (sess);
        @SuppressWarnings ("unchecked")
        T value = (T) sess.getAttribute (name, scope);

        return value;
    }

    /**
     * Set the value of this item in the FrameworkSession.
     * 
     * @param request An HttpServletRequest that contains a FrameworkSession
     *            attribute
     * @param value The value to set in the FrameworkSession
     * @throws NullPointerException if request is null
     * @throws InvalidArgumentException if the request does not contain a
     *             FrameworkSession
     */
    public void set (HttpServletRequest request, T value)
    {
        set (getSession (request), value);
    }

    /**
     * Set the value of this item in the FrameworkSession.
     * 
     * @param sess The FrameworkSession into which to set the value
     * @param value The value to set in the FrameworkSession
     * @throws NullPointerException if sess is null
     */
    public void set (FrameworkSession sess, T value)
    {
        validateSession (sess);
        sess.setAttribute (name, (Serializable) value, scope);
    }

    /**
     * Remove this item from the FrameworkSession.
     * 
     * @param request An HttpServletRequest that contains a FrameworkSession
     *            attribute from which this item should be removed.
     * @throws NullPointerException if request is null
     * @throws InvalidArgumentException if the request does not contain a
     *             FrameworkSession
     */
    public void remove (HttpServletRequest request)
    {
        remove (getSession (request));
    }

    /**
     * Remove this item from the FrameworkSession.
     * 
     * @param sess The FrameworkSession from which to remove the value
     * @throws NullPointerException if sess is null
     */
    public void remove (FrameworkSession sess)
    {
        validateSession (sess);
        sess.removeAttribute (name, scope);
    }

    public String toString ()
    {
        return "FrameworkSessionNonPatientItem with name '" + getName () + "' and scope " + getScope ();
    }

}
