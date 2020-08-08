package com.pinfly.purchasecharge.component.bean.usersecurity;

import java.util.List;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.pinfly.purchasecharge.component.bean.BaseBean;
import com.pinfly.purchasecharge.core.config.PurchaseChargeProperties;

public class UserBean extends BaseBean
{
    private static final long serialVersionUID = 1L;

    @NotBlank
    private String userId;
    private String pwd = PurchaseChargeProperties.getDefaultPassword ();
    private boolean enabled = false;
    private boolean admin = false;

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    private String mobilePhone;
    @Email
    private String email;
    private String netCommunityId;
    private String bod;
    private String birthAddress;
    private String comments;

    private List <RoleBean> roleBeans;

    public String getUserId ()
    {
        return userId;
    }

    public void setUserId (String userId)
    {
        this.userId = userId;
    }

    public String getPwd ()
    {
        return pwd;
    }

    public void setPwd (String pwd)
    {
        this.pwd = pwd;
    }

    public String getFirstName ()
    {
        return firstName;
    }

    public void setFirstName (String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName ()
    {
        return lastName;
    }

    public void setLastName (String lastName)
    {
        this.lastName = lastName;
    }

    public boolean isEnabled ()
    {
        return enabled;
    }

    public void setEnabled (boolean enabled)
    {
        this.enabled = enabled;
    }

    public boolean isAdmin ()
    {
        return admin;
    }

    public void setAdmin (boolean admin)
    {
        this.admin = admin;
    }

    public String getComments ()
    {
        return comments;
    }

    public void setComments (String comments)
    {
        this.comments = comments;
    }

    public String getBod ()
    {
        return bod;
    }

    public void setBod (String bod)
    {
        this.bod = bod;
    }

    public String getBirthAddress ()
    {
        return birthAddress;
    }

    public void setBirthAddress (String birthAddress)
    {
        this.birthAddress = birthAddress;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public String getMobilePhone ()
    {
        return mobilePhone;
    }

    public void setMobilePhone (String mobilePhone)
    {
        this.mobilePhone = mobilePhone;
    }

    public String getNetCommunityId ()
    {
        return netCommunityId;
    }

    public void setNetCommunityId (String netCommunityId)
    {
        this.netCommunityId = netCommunityId;
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
