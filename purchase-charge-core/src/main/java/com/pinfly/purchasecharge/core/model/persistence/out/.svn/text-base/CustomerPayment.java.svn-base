package com.pinfly.purchasecharge.core.model.persistence.out;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.collections.CollectionUtils;

import com.pinfly.purchasecharge.core.model.BaseModel;
import com.pinfly.purchasecharge.core.model.PaymentTypeCode;

/**
 * 客户收款单
 */
@Entity
@Table (name = "pc_customer_payment")
public class CustomerPayment extends BaseModel
{
    private static final long serialVersionUID = 1L;

    private long id;
    private String bid;
    private PaymentTypeCode typeCode;
    private String receiptId;
    private Timestamp paidDate;
    private float addUnPaid;
    private float remainUnPaid;
    private long userCreatedBy;
    private List <CustomerPaymentRecord> paymentRecords;
    private String comment;

    private Customer customer;

    public CustomerPayment ()
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

    @Column (nullable = false)
    @Temporal (value = TemporalType.TIMESTAMP)
    public Timestamp getPaidDate ()
    {
        return paidDate;
    }

    public void setPaidDate (Timestamp paidDate)
    {
        this.paidDate = paidDate;
    }

    public long getUserCreatedBy ()
    {
        return userCreatedBy;
    }

    public void setUserCreatedBy (long userCreatedBy)
    {
        this.userCreatedBy = userCreatedBy;
    }

    public String getReceiptId ()
    {
        return receiptId;
    }

    public void setReceiptId (String receiptId)
    {
        this.receiptId = receiptId;
    }

    public float getAddUnPaid ()
    {
        return addUnPaid;
    }

    public void setAddUnPaid (float addUnPaid)
    {
        this.addUnPaid = addUnPaid;
    }

    public float getRemainUnPaid ()
    {
        return remainUnPaid;
    }

    public void setRemainUnPaid (float remainUnPaid)
    {
        this.remainUnPaid = remainUnPaid;
    }

    @Column (nullable = false)
    @Enumerated (EnumType.STRING)
    public PaymentTypeCode getTypeCode ()
    {
        return typeCode;
    }

    public void setTypeCode (PaymentTypeCode typeCode)
    {
        this.typeCode = typeCode;
    }

    @OneToMany (mappedBy = "payment", fetch = FetchType.EAGER, cascade =
    { CascadeType.ALL })
    public List <CustomerPaymentRecord> getPaymentRecords ()
    {
        return paymentRecords;
    }

    public void setPaymentRecords (List <CustomerPaymentRecord> paymentRecords)
    {
        this.paymentRecords = paymentRecords;
        if (CollectionUtils.isNotEmpty (paymentRecords))
        {
            for (CustomerPaymentRecord paymentRecord : paymentRecords)
            {
                paymentRecord.setPayment (this);
            }
        }
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

    @ManyToOne (fetch = FetchType.LAZY, cascade =
    { CascadeType.REFRESH }, optional = false)
    @JoinColumn (name = "customer_id")
    public Customer getCustomer ()
    {
        return customer;
    }

    public void setCustomer (Customer customer)
    {
        this.customer = customer;
    }

}
