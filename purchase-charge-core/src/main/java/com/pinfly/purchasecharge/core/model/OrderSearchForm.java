package com.pinfly.purchasecharge.core.model;

public class OrderSearchForm extends SearchForm
{
    private static final long serialVersionUID = 1L;

    private long customerId;
    private long orderId;
    private String orderBid;
    private long userCreatedBy;

    public long getCustomerId ()
    {
        return customerId;
    }

    public void setCustomerId (long customerId)
    {
        this.customerId = customerId;
    }

    public long getOrderId ()
    {
        return orderId;
    }

    public void setOrderId (long orderId)
    {
        this.orderId = orderId;
    }

    public String getOrderBid ()
    {
        return orderBid;
    }

    public void setOrderBid (String orderBid)
    {
        this.orderBid = orderBid;
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
