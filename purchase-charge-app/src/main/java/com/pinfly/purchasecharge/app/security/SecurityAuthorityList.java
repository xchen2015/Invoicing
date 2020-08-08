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
@XmlRootElement (name = "SecurityAuthorities")
@XmlType (propOrder =
{ "securityAuthorities" })
public class SecurityAuthorityList implements Serializable
{
    private static final long serialVersionUID = 1L;

    private List <SecurityAuthority> securityAuthorities;

    public List <SecurityAuthority> getSecurityAuthorities ()
    {
        return securityAuthorities;
    }

    public void setSecurityAuthorities (List <SecurityAuthority> securityAuthorities)
    {
        this.securityAuthorities = securityAuthorities;
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
