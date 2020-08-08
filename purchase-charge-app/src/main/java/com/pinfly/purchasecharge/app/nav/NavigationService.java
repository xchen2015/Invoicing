package com.pinfly.purchasecharge.app.nav;

/**
 * Interface for accessing application navigation configuration for a given
 * application path.
 */
public interface NavigationService
{

    /**
     * Get the Navigation state for the given navigation path.
     * 
     * @param activePath The active path within the application navigation as a
     *            String.
     * @return A Navigation instance configured with the navigation state for
     *         the given navigation path.
     */
    public Navigation getNavigation (String activePath);

}
