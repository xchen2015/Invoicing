package com.pinfly.purchasecharge.core.model.persistence;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.pinfly.purchasecharge.core.model.BaseModel;
import com.pinfly.purchasecharge.core.model.PaymentAccountMode;

/**
 * 支付账号
 */
@Entity
@Table (name = "pc_payment_account")
public class PaymentAccount extends BaseModel
{
    private static final long serialVersionUID = 1L;

    private long id;
    private String name;
    private String accountId;
    private float remainMoney;
    private PaymentAccountMode accountMode;

    private List <PaymentTransfer> accountTransfers;

    public PaymentAccount ()
    {
    }

    public PaymentAccount (String name)
    {
        this.name = name;
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
    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    @Column (nullable = false, unique = true)
    public String getAccountId ()
    {
        return accountId;
    }

    public void setAccountId (String accountId)
    {
        this.accountId = accountId;
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
    @Enumerated (EnumType.STRING)
    public PaymentAccountMode getAccountMode ()
    {
        return accountMode;
    }

    public void setAccountMode (PaymentAccountMode accountMode)
    {
        this.accountMode = accountMode;
    }

    @OneToMany (mappedBy = "targetAccount", cascade =
    { CascadeType.ALL })
    public List <PaymentTransfer> getAccountTransfers ()
    {
        return accountTransfers;
    }

    public void setAccountTransfers (List <PaymentTransfer> accountTransfers)
    {
        this.accountTransfers = accountTransfers;
    }

}
