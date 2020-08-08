package com.pinfly.purchasecharge.component.bean;

import org.hibernate.validator.constraints.NotBlank;

public class CustomerLevelBean extends BaseBean
{
    private static final long serialVersionUID = 7118174668290265090L;
    @NotBlank
    private String name;
    private boolean enabled = true;
    private int order;

    private float saleMoney;
    private float profitMoney;

    private int paymentDays;
    private float maxDebt;
    private float priceRate;

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public int getPaymentDays ()
    {
        return paymentDays;
    }

    public void setPaymentDays (int paymentDays)
    {
        this.paymentDays = paymentDays;
    }

    public float getMaxDebt ()
    {
        return maxDebt;
    }

    public void setMaxDebt (float maxDebt)
    {
        this.maxDebt = maxDebt;
    }

    public float getPriceRate ()
    {
        return priceRate;
    }

    public void setPriceRate (float priceRate)
    {
        this.priceRate = priceRate;
    }

    public float getSaleMoney ()
    {
        return saleMoney;
    }

    public void setSaleMoney (float saleMoney)
    {
        this.saleMoney = saleMoney;
    }

    public boolean isEnabled ()
    {
        return enabled;
    }

    public void setEnabled (boolean enabled)
    {
        this.enabled = enabled;
    }

    public int getOrder ()
    {
        return order;
    }

    public void setOrder (int order)
    {
        this.order = order;
    }

    public float getProfitMoney ()
    {
        return profitMoney;
    }

    public void setProfitMoney (float profitMoney)
    {
        this.profitMoney = profitMoney;
    }
}
