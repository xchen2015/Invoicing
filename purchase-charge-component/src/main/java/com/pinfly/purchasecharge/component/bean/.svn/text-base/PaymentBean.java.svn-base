package com.pinfly.purchasecharge.component.bean;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.util.CollectionUtils;

import com.pinfly.purchasecharge.core.model.PaymentTypeCode;

public class PaymentBean extends BaseBean
{
    private static final long serialVersionUID = 1L;

    private String bid;
    @NotNull
    private PaymentTypeCode typeCode;
    private String receiptId;
    private String payDate;
    private float paid;
    private float addUnPaid;
    private float remainUnPaid;
    private String userCreated;
    private String comment;

    @NotNull
    private CustomerBean customerBean;
    private List <PaymentRecordBean> paymentRecordBeans;

    private String paymentRecordList;

    public String getBid ()
    {
        return bid;
    }

    public void setBid (String bid)
    {
        this.bid = bid;
    }

    public float getPaid ()
    {
        return paid;
    }

    public void setPaid (float paid)
    {
        this.paid = paid;
    }

    public float getRemainUnPaid ()
    {
        return remainUnPaid;
    }

    public void setRemainUnPaid (float remainUnPaid)
    {
        this.remainUnPaid = remainUnPaid;
    }

    public String getUserCreated ()
    {
        return userCreated;
    }

    public void setUserCreated (String userCreated)
    {
        this.userCreated = userCreated;
    }

    public String getPayDate ()
    {
        return payDate;
    }

    public void setPayDate (String payDate)
    {
        this.payDate = payDate;
    }

    public String getComment ()
    {
        return comment;
    }

    public void setComment (String comment)
    {
        this.comment = comment;
    }

    public PaymentTypeCode getTypeCode ()
    {
        return typeCode;
    }

    public void setTypeCode (PaymentTypeCode typeCode)
    {
        this.typeCode = typeCode;
    }

    public float getAddUnPaid ()
    {
        return addUnPaid;
    }

    public void setAddUnPaid (float addUnPaid)
    {
        this.addUnPaid = addUnPaid;
    }

    public String getReceiptId ()
    {
        return receiptId;
    }

    public void setReceiptId (String receiptId)
    {
        this.receiptId = receiptId;
    }

    public CustomerBean getCustomerBean ()
    {
        return customerBean;
    }

    public void setCustomerBean (CustomerBean customerBean)
    {
        this.customerBean = customerBean;
    }

    public List <PaymentRecordBean> getPaymentRecordBeans ()
    {
        return paymentRecordBeans;
    }

    public void setPaymentRecordBeans (List <PaymentRecordBean> paymentRecordBeans)
    {
        this.paymentRecordBeans = paymentRecordBeans;
        float paid = 0;
        if (!CollectionUtils.isEmpty (paymentRecordBeans))
        {
            for (PaymentRecordBean paymentRecord : paymentRecordBeans)
            {
                paid += paymentRecord.getPaid ();
            }
        }
        this.setPaid (paid);
    }

    public String getPaymentRecordList ()
    {
        return paymentRecordList;
    }

    public void setPaymentRecordList (String paymentRecordList)
    {
        this.paymentRecordList = paymentRecordList;
    }

}
