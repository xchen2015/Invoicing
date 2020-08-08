package com.pinfly.purchasecharge.component.bean.auditlog;

import org.hibernate.validator.constraints.NotBlank;

import com.pinfly.purchasecharge.component.bean.BaseBean;

public class LogEventBean extends BaseBean
{
    private static final long serialVersionUID = 7118174668290265090L;

    @NotBlank
    private String name;
    private boolean enabled = true;

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public boolean isEnabled ()
    {
        return enabled;
    }

    public void setEnabled (boolean enabled)
    {
        this.enabled = enabled;
    }

}
