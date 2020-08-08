package com.pinfly.purchasecharge.component.bean;

import java.util.List;

public class RegionBean extends BaseBean
{
    private static final long serialVersionUID = 7118174668290265090L;
    private String id;
    private String text;
    private String parentId;

    private List <RegionBean> children;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getText ()
    {
        return text;
    }

    public void setText (String text)
    {
        this.text = text;
    }

    public String getParentId ()
    {
        return parentId;
    }

    public void setParentId (String parentId)
    {
        this.parentId = parentId;
    }

    public List <RegionBean> getChildren ()
    {
        return children;
    }

    public void setChildren (List <RegionBean> children)
    {
        this.children = children;
    }
}
