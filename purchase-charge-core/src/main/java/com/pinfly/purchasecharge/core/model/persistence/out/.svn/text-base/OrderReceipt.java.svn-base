package com.pinfly.purchasecharge.core.model.persistence.out;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.pinfly.purchasecharge.core.model.BaseModel;
import com.pinfly.purchasecharge.core.model.ReceiptTypeCode;

/**
 * 开票信息
 */
@Entity
@Table (name = "pc_order_receipt")
public class OrderReceipt extends BaseModel
{
    private static final long serialVersionUID = 1L;

    private long id;
    private String title;
    private ReceiptTypeCode type;
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
    @Enumerated (EnumType.STRING)
    public ReceiptTypeCode getType ()
    {
        return type;
    }

    public void setType (ReceiptTypeCode type)
    {
        this.type = type;
    }

    @Column (nullable = false)
    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
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

    @Temporal (value = TemporalType.TIMESTAMP)
    public Date getDateCreated ()
    {
        return dateCreated;
    }

    public void setDateCreated (Date dateCreated)
    {
        this.dateCreated = dateCreated;
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
    { CascadeType.PERSIST, CascadeType.REFRESH })
    @JoinColumn (unique = true, name = "orderOut_id")
    public OrderOut getOrderOut ()
    {
        return orderOut;
    }

    public void setOrderOut (OrderOut orderOut)
    {
        this.orderOut = orderOut;
    }

}
