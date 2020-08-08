package com.pinfly.purchasecharge.core.model.persistence;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.pinfly.purchasecharge.core.model.BaseModel;

/**
 * 记账
 */
@Entity
@Table (name = "pc_accounting")
public class Accounting extends BaseModel
{
    private static final long serialVersionUID = 1L;

    private long id;
    private float money;
    private AccountingType type;
    private PaymentAccount paymentAccount;
    /**
     * 对象，如供应商或客户
     */
    private String customerId;
    /**
     * 来源，如入库单或出库单
     */
    private String orderId;
    private String comment;

    private Timestamp created;
    private Timestamp updated;
    private long userCreatedBy;
    private long userUpdatedBy;

    public Accounting ()
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

    @Column (nullable = false)
    public float getMoney ()
    {
        return money;
    }

    public void setMoney (float money)
    {
        this.money = money;
    }

    @ManyToOne (optional = false)
    @JoinColumn (name = "accounting_type_id", nullable = false)
    public AccountingType getType ()
    {
        return type;
    }

    public void setType (AccountingType type)
    {
        this.type = type;
    }

    @Column (nullable = false)
    @Temporal (value = TemporalType.TIMESTAMP)
    public Timestamp getCreated ()
    {
        return created;
    }

    public void setCreated (Timestamp created)
    {
        this.created = created;
    }

    @Temporal (value = TemporalType.TIMESTAMP)
    public Timestamp getUpdated ()
    {
        return updated;
    }

    public void setUpdated (Timestamp updated)
    {
        this.updated = updated;
    }

    @ManyToOne (cascade =
    { CascadeType.REFRESH }, optional = false)
    @JoinColumn (name = "paymentAccount_id")
    public PaymentAccount getPaymentAccount ()
    {
        return paymentAccount;
    }

    public void setPaymentAccount (PaymentAccount paymentAccount)
    {
        this.paymentAccount = paymentAccount;
    }

    @Column (nullable = false)
    public long getUserCreatedBy ()
    {
        return userCreatedBy;
    }

    public void setUserCreatedBy (long userCreatedBy)
    {
        this.userCreatedBy = userCreatedBy;
    }

    @Column (nullable = false)
    public long getUserUpdatedBy ()
    {
        return userUpdatedBy;
    }

    public void setUserUpdatedBy (long userUpdatedBy)
    {
        this.userUpdatedBy = userUpdatedBy;
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

    public String getCustomerId ()
    {
        return customerId;
    }

    public void setCustomerId (String customerId)
    {
        this.customerId = customerId;
    }

    public String getOrderId ()
    {
        return orderId;
    }

    public void setOrderId (String orderId)
    {
        this.orderId = orderId;
    }
}
