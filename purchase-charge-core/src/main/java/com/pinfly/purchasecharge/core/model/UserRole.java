package com.pinfly.purchasecharge.core.model;

public class UserRole extends BaseModel
{
    private static final long serialVersionUID = 1L;

    private String user;
    private String role;

    public String getUser ()
    {
        return user;
    }

    public void setUser (String user)
    {
        this.user = user;
    }

    public String getRole ()
    {
        return role;
    }

    public void setRole (String role)
    {
        this.role = role;
    }

}
