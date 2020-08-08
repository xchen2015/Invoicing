package com.pinfly.purchasecharge.framework.utils;

import java.io.IOException;
import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.pinfly.purchasecharge.framework.session.FrameworkSessionInternal;

/**
 * Implementation class for the <code>namespace</code> tag in the Fusion Web
 * Framework tag library.
 * <p>
 * The tag takes no arguments are returns a string that contains the encodes the
 * current portlet and patient id information from the FrameworkSession. This
 * can later be used to set the FrameworkSession to the same portlet and patient
 * id.
 * 
 * @see PortletNamespace
 */
public class Namespace extends TagSupport
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger (Namespace.class);

    public int doStartTag () throws JspException
    {
        ServletRequest request = pageContext.getRequest ();
        FrameworkSessionInternal frameworkSession = (FrameworkSessionInternal) request.getAttribute (RequestNames.FRAMEWORK_SESSION);
        if (frameworkSession == null)
        {
            throw new JspException ("No FrameworkSession found in request.");
        }

        String namespace = frameworkSession.getNamespace ();
        logger.debug (namespace);
        JspWriter writer = pageContext.getOut ();
        try
        {
            writer.print (namespace);
        }
        catch (IOException ioe)
        {
            throw new JspException ("Unable to write namespace", ioe);
        }

        return SKIP_BODY;
    }

}
