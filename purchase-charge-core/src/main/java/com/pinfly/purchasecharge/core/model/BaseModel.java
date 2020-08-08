package com.pinfly.purchasecharge.core.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class BaseModel implements Serializable
{
    private static final long serialVersionUID = 1L;

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
