package com.pinfly.purchasecharge.core.model.persistence.in;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.pinfly.purchasecharge.core.model.BaseModel;
import com.pinfly.purchasecharge.core.model.persistence.PaymentAccount;
import com.pinfly.purchasecharge.core.model.persistence.PaymentWay;

/**
 * 供应商付款单细项
 */
@Entity
@Table (name = "pc_provider_payment_record")
public class ProviderPaymentRecord extends BaseModel
{
    private static final long serialVersionUID = 1L;

    private long id;
    private float paid;
    private String comment;

    private PaymentAccount paymentAccount;
    private PaymentWay paymentWay;
    private OrderIn orderIn;
    
    private ProviderPayment payment;

    public ProviderPaymentRecord ()
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
    public float getPaid ()
    {
        return paid;
    }

    public void setPaid (float paid)
    {
        this.paid = paid;
    }

    @ManyToOne (cascade =
    { CascadeType.REFRESH })
    @JoinColumn (name = "paymentAccount_id")
    public PaymentAccount getPaymentAccount ()
    {
        return paymentAccount;
    }

    public void setPaymentAccount (PaymentAccount paymentAccount)
    {
        this.paymentAccount = paymentAccount;
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

    @ManyToOne
    @JoinColumn (name = "paymentWay_id")
    public PaymentWay getPaymentWay ()
    {
        return paymentWay;
    }

    public void setPaymentWay (PaymentWay paymentWay)
    {
        this.paymentWay = paymentWay;
    }

    @ManyToOne (fetch = FetchType.LAZY, cascade =
    { CascadeType.REFRESH }, optional = false)
    @JoinColumn (name = "provider_payment_id")
    public ProviderPayment getPayment ()
    {
        return payment;
    }

    public void setPayment (ProviderPayment payment)
    {
        this.payment = payment;
    }

    @ManyToOne (fetch = FetchType.LAZY, cascade =
    { CascadeType.REFRESH })
    @JoinColumn (name = "orderIn_id")
    public OrderIn getOrderIn ()
    {
        return orderIn;
    }

    public void setOrderIn (OrderIn orderIn)
    {
        this.orderIn = orderIn;
    }

}
