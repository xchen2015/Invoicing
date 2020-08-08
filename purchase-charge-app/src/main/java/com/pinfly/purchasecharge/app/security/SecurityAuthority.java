package com.pinfly.purchasecharge.app.security;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@XmlAccessorType (XmlAccessType.FIELD)
@XmlRootElement (name = "SecurityAuthority")
@XmlType (propOrder =
{ "name", "url", "enabled", "authorities" })
public class SecurityAuthority implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String name;
    private String url;
    private boolean enabled = true;
    private List <SecurityAuthority> authorities;

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getUrl ()
    {
        return url;
    }

    public void setUrl (String url)
    {
        this.url = url;
    }

    public boolean isEnabled ()
    {
        return enabled;
    }

    public void setEnabled (boolean enabled)
    {
        this.enabled = enabled;
    }

    public List <SecurityAuthority> getAuthorities ()
    {
        return authorities;
    }

    public void setAuthorities (List <SecurityAuthority> authorities)
    {
        this.authorities = authorities;
    }

    @Override
    public String toString ()
    {
        return ToStringBuilder.reflectionToString (this, ToStringStyle.MULTI_LINE_STYLE);
    }

    @Override
    public boolean equals (Object o)
    {
        return EqualsBuilder.reflectionEquals (this, o);
    }

    @Override
    public int hashCode ()
    {
        return HashCodeBuilder.reflectionHashCode (this);
    }

}
