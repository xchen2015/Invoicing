package com.pinfly.purchasecharge.framework.session;

/**
 * Interface that is used by the framework to manage the FrameworkSession. These
 * methods are public as an implementation side effect and should not be used by
 * applications.
 */
public interface FrameworkSessionInternal extends FrameworkSession
{
    /**
     * Set the current FrameworkSession namespace. The namespace must contain
     * portlet information. It may also contain patient information. If so, then
     * the current patient id will be set in the FrameworkSession; if not, then
     * there will be no current patient id.
     * <p>
     * Typically, the value for namespace will have come from a previous call to
     * getNamespace() or getPortletNamespace() and those methods in turn are
     * typically accessed via the jsp tags namespace and portletNamespace,
     * respectively.
     * 
     * @param namespace The namespace to set.
     */
    public void setNamespace (String namespace);

    /**
     * Get the current value of the namespace. This returns a value that can be
     * used to establish the state of the FrameworkSession via a subsequent call
     * to setNamespace. The call always returns information about the current
     * portlet. If there is a current patient id, then it also includes
     * information specifying which id is current.
     * 
     * @return The current namespace.
     */
    public String getNamespace ();

    /**
     * Clear any namespace information in the FrameworkSession. After this
     * method is called it is not possible to access the FrameworkSession using
     * portlet scope or the current patient id until the namespace is rest with
     * a call to setNamespace.
     */
    public void clearNamespace ();

}
