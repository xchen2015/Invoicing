package com.pinfly.purchasecharge.core.model;

public class OrderItemSearchForm extends SearchForm
{
    private static final long serialVersionUID = 1L;

    private long customerId;
    private long goodsId;
    private long userCreatedBy;

    public long getCustomerId ()
    {
        return customerId;
    }

    public void setCustomerId (long customerId)
    {
        this.customerId = customerId;
    }

    public long getGoodsId ()
    {
        return goodsId;
    }

    public void setGoodsId (long goodsId)
    {
        this.goodsId = goodsId;
    }

    public long getUserCreatedBy ()
    {
        return userCreatedBy;
    }

    public void setUserCreatedBy (long userCreatedBy)
    {
        this.userCreatedBy = userCreatedBy;
    }
}
