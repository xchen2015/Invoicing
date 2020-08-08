package com.pinfly.purchasecharge.component.bean;

import org.hibernate.validator.constraints.NotBlank;

import com.pinfly.purchasecharge.core.model.PaymentAccountMode;

/**
 * 
 * @author xiang
 * 
 */
public class PaymentAccountBean extends BaseBean
{
    private static final long serialVersionUID = 7118174668290265090L;

    @NotBlank
    private String name;
    @NotBlank
    private String accountId;
    private float remainMoney;
    private PaymentAccountMode accountMode = PaymentAccountMode.DEPOSIT;

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

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

    public PaymentAccountMode getAccountMode ()
    {
        return accountMode;
    }

    public void setAccountMode (PaymentAccountMode accountMode)
    {
        this.accountMode = accountMode;
    }

}
