package com.pinfly.purchasecharge.framework.utils;

/**
 * Class that defines names of request attributes. Since the Spring framework
 * copies items from the model into the request, these names are also used for
 * entries in the model part of the ModelAndView.
 * <p>
 * These names define request attributes (i.e. values retrieved using
 * <code>request.getAttribute(name)</code>). For names of request parameters
 * (i.e. values retrieved using <code>request.getParameter(name)</code) see
 * {@link ParameterNames}.
 */
public class RequestNames
{
    public static final String NAMESPACE = "namespace";

    /**
     * Attribute that contains the current FrameworkSession. Note that this
     * attribute is set by the Framework's DispatchServlet and
     * DispatcherPortlet. If the request is not processed by the Framework's
     * dispatcher, then this attribute will not be available.
     */
    public static final String FRAMEWORK_SESSION = "frameworkSession";

    /**
     * Attribute containing the exception that caused an error page to be
     * displayed. Note that this value cannot be changed, as it must match the
     * value that is used internally by Spring.
     */
    public static final String EXCEPTION = "exception";

    /**
     * Header that has the widget class name.
     */
    public static final String WIDGET_CLASS = "X-CFX-WidgetClass";

    /**
     * Header that has the widget unique name.
     */
    public static final String UNIQUE_NAME = "X-CFX-UniqueName";

    protected RequestNames ()
    {
    }

}
