package com.pinfly.purchasecharge.component.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class GenericPagingResult <T extends Object> implements Serializable
{
    private static final long serialVersionUID = 1L;

    private List <T> rows;
    private long total;
    private List <Map <String, String>> footer;

    public List <T> getRows ()
    {
        return rows;
    }

    public void setRows (List <T> rows)
    {
        this.rows = rows;
    }

    public long getTotal ()
    {
        return total;
    }

    public void setTotal (long total)
    {
        this.total = total;
    }

    public List <Map <String, String>> getFooter ()
    {
        return footer;
    }

    public void setFooter (List <Map <String, String>> footer)
    {
        this.footer = footer;
    }

}
