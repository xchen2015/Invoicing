package com.pinfly.purchasecharge.core.model.persistence.out;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.pinfly.purchasecharge.core.model.BaseModel;

/**
 * 客户等级
 */
@Entity
@Table (name = "pc_customer_level")
public class CustomerLevel extends BaseModel
{
    private static final long serialVersionUID = 1L;

    private long id;
    private String name;
    private boolean enabled = true;
    private int order;

    /**
     * 等级条件一：销售额
     */
    private float saleMoney;
    /**
     * 等级条件二：利润额
     */
    private float profitMoney;

    /**
     * 优惠一：账期天数
     */
    private int paymentDays;
    /**
     * 优惠二：最多允许欠账
     */
    private float maxDebt;
    /**
     * 优惠三：价格折扣率
     */
    private float priceRate;

    public CustomerLevel ()
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

    @Column (name = "level_order", nullable = false, unique = true)
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
