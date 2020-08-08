package com.pinfly.purchasecharge.component.bean;

public class OrderDeliveryBean extends BaseBean
{
    private static final long serialVersionUID = 1L;

    private DeliveryCompanyBean deliveryCompanyBean;
    private String number;
    private int amount;
    private float fee;
    private float paid;
    private String dateCreated;
    private String userCreated;

    private OrderBean orderBean;

    public DeliveryCompanyBean getDeliveryCompanyBean ()
    {
        return deliveryCompanyBean;
    }

    public void setDeliveryCompanyBean (DeliveryCompanyBean deliveryCompanyBean)
    {
        this.deliveryCompanyBean = deliveryCompanyBean;
    }

    public String getNumber ()
    {
        return number;
    }

    public void setNumber (String number)
    {
        this.number = number;
    }

    public int getAmount ()
    {
        return amount;
    }

    public void setAmount (int amount)
    {
        this.amount = amount;
    }

    public float getFee ()
    {
        return fee;
    }

    public void setFee (float fee)
    {
        this.fee = fee;
    }

    public float getPaid ()
    {
        return paid;
    }

    public void setPaid (float paid)
    {
        this.paid = paid;
    }

    public String getDateCreated ()
    {
        return dateCreated;
    }

    public void setDateCreated (String dateCreated)
    {
        this.dateCreated = dateCreated;
    }

    public String getUserCreated ()
    {
        return userCreated;
    }

    public void setUserCreated (String userCreated)
    {
        this.userCreated = userCreated;
    }

    public OrderBean getOrderBean ()
    {
        return orderBean;
    }

    public void setOrderBean (OrderBean orderBean)
    {
        this.orderBean = orderBean;
    }
}
