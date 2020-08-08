package com.pinfly.purchasecharge.core.model;

import java.io.Serializable;

public class LoginUser implements Serializable
{
    private static final long serialVersionUID = 1L;

    private long uid;
    private String userId;
    private String fullName;
    private boolean admin = false;

    public long getUid ()
    {
        return uid;
    }

    public void setUid (long uid)
    {
        this.uid = uid;
    }

    public String getUserId ()
    {
        return userId;
    }

    public void setUserId (String userId)
    {
        this.userId = userId;
    }

    public boolean isAdmin ()
    {
        return admin;
    }

    public void setAdmin (boolean admin)
    {
        this.admin = admin;
    }

    public String getFullName ()
    {
        return fullName;
    }

    public void setFullName (String fullName)
    {
        this.fullName = fullName;
    }
}
