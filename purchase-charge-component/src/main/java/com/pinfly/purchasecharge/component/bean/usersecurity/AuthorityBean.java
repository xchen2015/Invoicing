package com.pinfly.purchasecharge.component.bean.usersecurity;

import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

import com.pinfly.purchasecharge.component.bean.BaseBean;

public class AuthorityBean extends BaseBean
{
    private static final long serialVersionUID = 1L;

    @NotBlank
    private String name;
    private String url;
    /**
     * only for combotree display
     */
    private String text;
    private boolean enabled = false;
    private List <AuthorityBean> children;
    private String state = "open";

    private List <RoleBean> roleBeans;

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
        this.text = name;
    }

    public String getUrl ()
    {
        return url;
    }

    public void setUrl (String url)
    {
        this.url = url;
    }

    public String getText ()
    {
        return text;
    }

    public List <AuthorityBean> getChildren ()
    {
        return children;
    }

    public void setChildren (List <AuthorityBean> children)
    {
        this.children = children;
    }

    public String getState ()
    {
        return state;
    }

    public void setState (String state)
    {
        this.state = state;
    }

    public boolean isEnabled ()
    {
        return enabled;
    }

    public void setEnabled (boolean enabled)
    {
        this.enabled = enabled;
    }

    public List <RoleBean> getRoleBeans ()
    {
        return roleBeans;
    }

    public void setRoleBeans (List <RoleBean> roleBeans)
    {
        this.roleBeans = roleBeans;
    }
}
