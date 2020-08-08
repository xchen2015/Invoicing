package com.pinfly.purchasecharge.core.model.persistence.out;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.pinfly.purchasecharge.core.model.BaseModel;
import com.pinfly.purchasecharge.core.model.persistence.DeliveryCompany;

/**
 * 物流信息
 */
@Entity
@Table (name = "pc_order_delivery")
public class OrderDelivery extends BaseModel
{
    private static final long serialVersionUID = 1L;

    private long id;
    private DeliveryCompany company;
    private String number;
    private int amount;
    private float fee;
    private float paid;
    private Date dateCreated;
    private long userCreatedBy;

    private OrderOut orderOut;

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    public long getId ()
    {
        return id;
    }

    public void setId (long id)
    {
        this.id = id;
    }

    @Column (nullable = false)
    @Temporal (value = TemporalType.TIMESTAMP)
    public Date getDateCreated ()
    {
        return dateCreated;
    }

    public void setDateCreated (Date dateCreated)
    {
        this.dateCreated = dateCreated;
    }

    @ManyToOne (cascade =
    { CascadeType.REFRESH }, optional = false)
    @JoinColumn (name = "deliveryCompany_id")
    public DeliveryCompany getCompany ()
    {
        return company;
    }

    public void setCompany (DeliveryCompany company)
    {
        this.company = company;
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

    public long getUserCreatedBy ()
    {
        return userCreatedBy;
    }

    public void setUserCreatedBy (long userCreatedBy)
    {
        this.userCreatedBy = userCreatedBy;
    }

    @OneToOne (cascade =
    { CascadeType.REFRESH })
    @JoinColumn (name = "orderOut_id")
    public OrderOut getOrderOut ()
    {
        return orderOut;
    }

    public void setOrderOut (OrderOut orderOut)
    {
        this.orderOut = orderOut;
    }
}
