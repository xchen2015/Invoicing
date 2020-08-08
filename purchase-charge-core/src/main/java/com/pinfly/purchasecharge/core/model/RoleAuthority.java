package com.pinfly.purchasecharge.core.model;

public class RoleAuthority extends BaseModel
{
    private static final long serialVersionUID = 1L;

    private String role;
    private String authorityName;
    private String authorityValue;

    public String getRole ()
    {
        return role;
    }

    public void setRole (String role)
    {
        this.role = role;
    }

    public String getAuthorityName ()
    {
        return authorityName;
    }

    public void setAuthorityName (String authorityName)
    {
        this.authorityName = authorityName;
    }

    public String getAuthorityValue ()
    {
        return authorityValue;
    }

    public void setAuthorityValue (String authorityValue)
    {
        this.authorityValue = authorityValue;
    }

}
