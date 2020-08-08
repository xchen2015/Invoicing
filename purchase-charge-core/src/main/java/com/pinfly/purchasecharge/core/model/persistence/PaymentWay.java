package com.pinfly.purchasecharge.core.model.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.pinfly.purchasecharge.core.model.BaseModel;

/**
 * 支付方式
 */
@Entity
@Table (name = "pc_payment_way")
public class PaymentWay extends BaseModel
{
    private static final long serialVersionUID = 1L;

    private long id;
    private String name;

    public PaymentWay ()
    {
    }

    public PaymentWay (String name)
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

}
