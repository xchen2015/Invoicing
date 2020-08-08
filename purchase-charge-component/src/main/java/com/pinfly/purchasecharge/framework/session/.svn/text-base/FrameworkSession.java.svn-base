package com.pinfly.purchasecharge.framework.session;

import java.io.Serializable;

/**
 * The <code>FrameworkSession</code> interface provides a way for applications
 * to store user and patient specific information across multiple requests. The
 * FrameworkSession is similar to the standard PortletSession but extends the
 * concept in some of important ways.
 */
public interface FrameworkSession extends Serializable
{
    /** Scope value indicating that the item is stored at application scope. */
    public static final SessionScope APPLICATION_SCOPE = SessionScope.APPLICATION_SCOPE;
    /** Scope value indicating that the item is stored at portlet scope. */
    public static final SessionScope PORTLET_SCOPE = SessionScope.PORTLET_SCOPE;

    /**
     * Add a non-patient specific item to the session at application scope. This
     * is a convenience routine that is equivalent to <br>
     * <code>setAttribute (name, value, APPLICATION_SCOPE)</code>
     * 
     * @param name The name to associate with the attribute.
     * @param value The attribute to put in the session.
     */

    public void setAttribute (String name, Serializable value);

    /**
     * Add a non-patient specific item to the session at the specified scope.
     * 
     * @param name The name to associate with the attribute.
     * @param value The attribute to put in the session.
     * @param scope The scope of the attribute.
     * @throws IllegalStateException if the scope is PORTLET_SCOPE but the
     *             current portlet scope cannot be determined. This generally
     *             means that there was no namespace parameter in the request
     */
    public void setAttribute (String name, Serializable value, SessionScope scope) throws IllegalStateException;

    /**
     * Get a non-patient specific item from the session at application scope.
     * This is a convenience routine that is equivalent to <br>
     * <code>getAttribute (name, APPLICATION_SCOPE)</code>
     * 
     * @param name The name of the attribute to retrieve
     * @return The attribute associated with the name, or null if there is no
     *         attributed associated with the name
     */
    public Serializable getAttribute (String name);

    /**
     * Get a non-patient specific item from the session at the specified scope.
     * 
     * @param name The name of the attribute to retrieve
     * @param scope The scope of the attribute
     * @return The attribute associated with the name at the specified scope, or
     *         null if there is no attribute at that name and scope.
     * @throws IllegalStateException if the scope is PORTLET_SCOPE but the
     *             current portlet scope cannot be determined. This generally
     *             means that there was no namespace parameter in the request
     */
    public Serializable getAttribute (String name, SessionScope scope) throws IllegalStateException;

    /**
     * Does the session contain a non-patient specific item with the specified
     * name at application scope. This is a convenience routine that is
     * equivalent to <br>
     * <code>containsKey (name, APPLICATION_SCOPE)</code>
     * 
     * @param name The name of the item to look for.
     * @return <code>true</code> if the item exists in the FrameworkSession at
     *         application scope.
     */
    public boolean containsAttribute (String name);

    /**
     * Does the session contain a non-patient specific item with the specified
     * name at the specified scope.
     * 
     * @param name The name of the item to look for.
     * @param scope The scope of the item.
     * @return <code>true</code> if the item exists in the FrameworkSession at
     *         the specified scope.
     * @throws IllegalStateException if the scope is PORTLET_SCOPE but the
     *             current portlet scope cannot be determined. This generally
     *             means that there was no namespace parameter in the request
     */
    public boolean containsAttribute (String name, SessionScope scope) throws IllegalStateException;

    /**
     * Remove a non-patient specific attribute from the session at application
     * scope. This is a convenience routine equivalent to <br>
     * <code>removeAttribute (name, APPLICATION_SCOPE)</code>
     * 
     * @param name The name of the attribute to remove from the session.
     */
    public void removeAttribute (String name);

    /**
     * Remove a non-patient specific attribute from the session at the specified
     * scope.
     * 
     * @param name The name of the attribute to remove from the session.
     * @param scope The scope of the attribute.
     * @throws IllegalStateException if the scope is PORTLET_SCOPE but the
     *             current portlet scope cannot be determined. This generally
     *             means that there was no namespace parameter in the request
     */
    public void removeAttribute (String name, SessionScope scope) throws IllegalStateException;

}
