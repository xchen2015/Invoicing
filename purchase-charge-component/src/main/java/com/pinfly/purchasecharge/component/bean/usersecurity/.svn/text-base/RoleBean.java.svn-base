package com.pinfly.purchasecharge.component.bean.usersecurity;

import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

import com.pinfly.purchasecharge.component.bean.BaseBean;

public class RoleBean extends BaseBean
{
    private static final long serialVersionUID = 1L;

    @NotBlank
    private String name;
    /**
     * only for combotree display
     */
    private String text;
    private boolean enabled = false;

    private List <UserBean> userBeans;
    private List <AuthorityBean> authorityBeans;

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
        this.text = name;
    }

    public String getText ()
    {
        return text;
    }

    public boolean isEnabled ()
    {
        return enabled;
    }

    public void setEnabled (boolean enabled)
    {
        this.enabled = enabled;
    }

    public List <UserBean> getUserBeans ()
    {
        return userBeans;
    }

    public void setUserBeans (List <UserBean> userBeans)
    {
        this.userBeans = userBeans;
    }

    public List <AuthorityBean> getAuthorityBeans ()
    {
        return authorityBeans;
    }

    public void setAuthorityBeans (List <AuthorityBean> authorityBeans)
    {
        this.authorityBeans = authorityBeans;
    }
}
