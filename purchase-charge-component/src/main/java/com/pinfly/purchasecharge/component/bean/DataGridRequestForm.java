package com.pinfly.purchasecharge.component.bean;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class DataGridRequestForm implements Serializable
{
    private static final long serialVersionUID = 1L;

    private int page;

    private int rows;

    private String sort;

    private String order;

    private String searchKey;

    public int getPage ()
    {
        return page;
    }

    public void setPage (int page)
    {
        this.page = page;
    }

    public int getRows ()
    {
        return rows;
    }

    public void setRows (int rows)
    {
        this.rows = rows;
    }

    public String getSort ()
    {
        return sort;
    }

    public void setSort (String sort)
    {
        this.sort = sort;
    }

    public String getOrder ()
    {
        return order;
    }

    public void setOrder (String order)
    {
        this.order = order;
    }

    public String getSearchKey ()
    {
        return searchKey;
    }

    public void setSearchKey (String searchKey)
    {
        this.searchKey = searchKey;
    }

    @Override
    public String toString ()
    {
        return ToStringBuilder.reflectionToString (this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
