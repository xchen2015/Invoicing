package com.pinfly.purchasecharge.core.model.persistence.out;

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
 * 客户收款单明细
 */
@Entity
@Table (name = "pc_customer_payment_record")
public class CustomerPaymentRecord extends BaseModel
{
    private static final long serialVersionUID = 1L;

    private long id;
    private float paid;
    private String comment;

    private PaymentAccount paymentAccount;
    private PaymentWay paymentWay;
    private OrderOut orderOut;
    
    private CustomerPayment payment;

    public CustomerPaymentRecord ()
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
    @JoinColumn (name = "customer_payment_id")
    public CustomerPayment getPayment ()
    {
        return payment;
    }

    public void setPayment (CustomerPayment payment)
    {
        this.payment = payment;
    }

    @ManyToOne (fetch = FetchType.LAZY, cascade =
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
