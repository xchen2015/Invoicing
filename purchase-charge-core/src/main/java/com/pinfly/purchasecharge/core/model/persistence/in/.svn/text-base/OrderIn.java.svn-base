package com.pinfly.purchasecharge.core.model.persistence.in;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.pinfly.purchasecharge.core.model.BaseModel;
import com.pinfly.purchasecharge.core.model.OrderStatusCode;
import com.pinfly.purchasecharge.core.model.OrderTypeCode;

/**
 * 入库单
 */
@Entity
@Table (name = "pc_order_in")
public class OrderIn extends BaseModel
{
    private static final long serialVersionUID = 1L;

    private long id;
    private String bid;

    /**
     * 交易额
     */
    private float dealMoney;
    /**
     * 折扣率
     */
    private float discount = 1;
    /**
     * 应收款
     */
    private float receivable;
    /**
     * 实收额
     */
    private float paidMoney;

    /**
     * 操作员
     */
    private long userCreatedBy;
    private long userUpdatedBy;
    private Timestamp dateCreated;
    private Timestamp lastUpdated;

    private String comment;

    private OrderStatusCode statusCode = OrderStatusCode.NEW;
    private OrderTypeCode typeCode = OrderTypeCode.IN;

    private Provider provider;
    private List <OrderInItem> orderItems;

    public OrderIn ()
    {
    }

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

    @Column (nullable = false, unique = true)
    public String getBid ()
    {
        return bid;
    }

    public void setBid (String bid)
    {
        this.bid = bid;
    }

    public float getDealMoney ()
    {
        return dealMoney;
    }

    public void setDealMoney (float dealMoney)
    {
        this.dealMoney = dealMoney;
    }

    public float getPaidMoney ()
    {
        return paidMoney;
    }

    public void setPaidMoney (float paidMoney)
    {
        this.paidMoney = paidMoney;
    }

    @Column (length = 1024)
    public String getComment ()
    {
        return comment;
    }

    public void setComment (String comment)
    {
        this.comment = comment;
    }

    @Temporal (value = TemporalType.TIMESTAMP)
    public Timestamp getLastUpdated ()
    {
        return lastUpdated;
    }

    public void setLastUpdated (Timestamp lastUpdated)
    {
        this.lastUpdated = lastUpdated;
    }

    @Column (nullable = false)
    @Temporal (value = TemporalType.TIMESTAMP)
    public Timestamp getDateCreated ()
    {
        return dateCreated;
    }

    public void setDateCreated (Timestamp dateCreated)
    {
        this.dateCreated = dateCreated;
    }

    @Column (nullable = false)
    @Enumerated (EnumType.STRING)
    public OrderStatusCode getStatusCode ()
    {
        return statusCode;
    }

    public void setStatusCode (OrderStatusCode statusCode)
    {
        this.statusCode = statusCode;
    }

    @Column (nullable = false)
    @Enumerated (EnumType.STRING)
    public OrderTypeCode getTypeCode ()
    {
        return typeCode;
    }

    public void setTypeCode (OrderTypeCode typeCode)
    {
        this.typeCode = typeCode;
    }

    public float getDiscount ()
    {
        return discount;
    }

    public void setDiscount (float discount)
    {
        this.discount = discount;
    }

    public float getReceivable ()
    {
        return receivable;
    }

    public void setReceivable (float receivable)
    {
        this.receivable = receivable;
    }

    public long getUserCreatedBy ()
    {
        return userCreatedBy;
    }

    public void setUserCreatedBy (long userCreatedBy)
    {
        this.userCreatedBy = userCreatedBy;
    }

    public long getUserUpdatedBy ()
    {
        return userUpdatedBy;
    }

    public void setUserUpdatedBy (long userUpdatedBy)
    {
        this.userUpdatedBy = userUpdatedBy;
    }

    @ManyToOne
    @JoinColumn (name = "provider_id")
    public Provider getProvider ()
    {
        return provider;
    }

    public void setProvider (Provider provider)
    {
        this.provider = provider;
    }

    @OneToMany (mappedBy = "orderIn", cascade =
    { CascadeType.ALL })
    public List <OrderInItem> getOrderItems ()
    {
        return orderItems;
    }

    public void setOrderItems (List <OrderInItem> orderItems)
    {
        this.orderItems = orderItems;
        if (null != orderItems)
        {
            for (OrderInItem orderItem : orderItems)
            {
                orderItem.setOrderIn (this);
            }
        }
    }

}
