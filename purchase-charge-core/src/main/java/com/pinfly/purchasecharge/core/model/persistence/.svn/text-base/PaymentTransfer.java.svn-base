package com.pinfly.purchasecharge.core.model.persistence;

import java.sql.Timestamp;

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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.pinfly.purchasecharge.core.model.BaseModel;
import com.pinfly.purchasecharge.core.model.PaymentTransferTypeCode;

/**
 * 内部转账
 */
@Entity
@Table (name = "pc_payment_transfer")
public class PaymentTransfer extends BaseModel
{
    private static final long serialVersionUID = 1L;

    private long id;
    private PaymentAccount targetAccount;
    private PaymentTransferTypeCode transferTypeCode;
    private String source;
    private float inMoney;
    private float outMoney;
    private float remainMoney;
    private Timestamp dateCreated;
    private long userCreatedBy;
    private String comment;

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

    @ManyToOne (cascade =
    { CascadeType.REFRESH })
    @JoinColumn (name = "target_account_id")
    public PaymentAccount getTargetAccount ()
    {
        return targetAccount;
    }

    public void setTargetAccount (PaymentAccount targetAccount)
    {
        this.targetAccount = targetAccount;
    }

    @Column (nullable = false)
    @Enumerated (EnumType.STRING)
    public PaymentTransferTypeCode getTransferTypeCode ()
    {
        return transferTypeCode;
    }

    public void setTransferTypeCode (PaymentTransferTypeCode transferTypeCode)
    {
        this.transferTypeCode = transferTypeCode;
    }

    public String getSource ()
    {
        return source;
    }

    public void setSource (String source)
    {
        this.source = source;
    }

    public float getInMoney ()
    {
        return inMoney;
    }

    public void setInMoney (float inMoney)
    {
        this.inMoney = inMoney;
    }

    public float getOutMoney ()
    {
        return outMoney;
    }

    public void setOutMoney (float outMoney)
    {
        this.outMoney = outMoney;
    }

    public float getRemainMoney ()
    {
        return remainMoney;
    }

    public void setRemainMoney (float remainMoney)
    {
        this.remainMoney = remainMoney;
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

    @Column (length = 1024)
    public String getComment ()
    {
        return comment;
    }

    public void setComment (String comment)
    {
        this.comment = comment;
    }

    public long getUserCreatedBy ()
    {
        return userCreatedBy;
    }

    public void setUserCreatedBy (long userCreatedBy)
    {
        this.userCreatedBy = userCreatedBy;
    }

}
