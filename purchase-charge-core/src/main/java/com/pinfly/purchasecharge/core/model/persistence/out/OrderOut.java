package com.pinfly.purchasecharge.core.model.persistence.out;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.pinfly.purchasecharge.core.model.BaseModel;
import com.pinfly.purchasecharge.core.model.OrderStatusCode;
import com.pinfly.purchasecharge.core.model.OrderTypeCode;
import com.pinfly.purchasecharge.core.model.persistence.Project;

/**
 * 出库单
 */
@Entity
@Table (name = "pc_order_out")
public class OrderOut extends BaseModel
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
    private float discount;
    /**
     * 应收款
     */
    private float receivable;
    /**
     * 实收额
     */
    private float paidMoney;
    /**
     * 利润
     */
    private float profit;

    /**
     * 操作员
     */
    private long userCreatedBy;
    private long userUpdatedBy;
    private Timestamp dateCreated;
    private Timestamp lastUpdated;

    private String comment;

    private OrderStatusCode statusCode = OrderStatusCode.NEW;
    private OrderTypeCode typeCode = OrderTypeCode.OUT;

    private Customer customer;
    private List <OrderOutItem> orderItems;
    private OrderDelivery orderDelivery;
    private OrderReceipt orderReceipt;
    private Project project;

    public OrderOut ()
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

    public float getProfit ()
    {
        return profit;
    }

    public void setProfit (float profit)
    {
        this.profit = profit;
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
    @JoinColumn (name = "customer_id")
    public Customer getCustomer ()
    {
        return customer;
    }

    public void setCustomer (Customer customer)
    {
        this.customer = customer;
    }

    @OneToMany (mappedBy = "orderOut", cascade =
    { CascadeType.ALL })
    public List <OrderOutItem> getOrderItems ()
    {
        return orderItems;
    }

    public void setOrderItems (List <OrderOutItem> orderItems)
    {
        this.orderItems = orderItems;
        if (null != orderItems)
        {
            for (OrderOutItem orderItem : orderItems)
            {
                orderItem.setOrderOut (this);
            }
        }
    }

    @OneToOne (mappedBy = "orderOut", cascade =
    { CascadeType.ALL })
    public OrderDelivery getOrderDelivery ()
    {
        return orderDelivery;
    }

    public void setOrderDelivery (OrderDelivery orderDelivery)
    {
        this.orderDelivery = orderDelivery;
    }

    @OneToOne (mappedBy = "orderOut", cascade =
    { CascadeType.ALL })
    public OrderReceipt getOrderReceipt ()
    {
        return orderReceipt;
    }

    public void setOrderReceipt (OrderReceipt orderReceipt)
    {
        this.orderReceipt = orderReceipt;
    }

    @ManyToOne
    @JoinColumn (name = "project_id")
    public Project getProject ()
    {
        return project;
    }

    public void setProject (Project project)
    {
        this.project = project;
    }

}
