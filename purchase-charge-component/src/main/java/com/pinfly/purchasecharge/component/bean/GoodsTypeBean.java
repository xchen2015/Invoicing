package com.pinfly.purchasecharge.component.bean;

import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

public class GoodsTypeBean extends BaseBean
{
    private static final long serialVersionUID = 7118174668290265090L;

    @NotBlank
    private String text;
    private String state = "closed";
    private String attributes;
    private String parentId;

    private List <GoodsTypeBean> children;
    private long goodsAmount;

    public String getText ()
    {
        return text;
    }

    public void setText (String text)
    {
        this.text = text;
    }

    public String getState ()
    {
        return state;
    }

    public void setState (String state)
    {
        this.state = state;
    }

    public String getAttributes ()
    {
        return attributes;
    }

    public void setAttributes (String attributes)
    {
        this.attributes = attributes;
    }

    public String getParentId ()
    {
        return parentId;
    }

    public void setParentId (String parentId)
    {
        this.parentId = parentId;
    }

    public List <GoodsTypeBean> getChildren ()
    {
        return children;
    }

    public void setChildren (List <GoodsTypeBean> children)
    {
        this.children = children;
    }

    public long getGoodsAmount ()
    {
        return goodsAmount;
    }

    public void setGoodsAmount (long goodsAmount)
    {
        this.goodsAmount = goodsAmount;
    }
}
