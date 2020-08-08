package com.pinfly.purchasecharge.core.model.persistence.goods;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.pinfly.purchasecharge.core.model.BaseModel;

/**
 * 货物库存
 */
@Entity
@Table (name = "pc_goods_storage")
public class GoodsStorage extends BaseModel
{
    private static final long serialVersionUID = 1L;

    private long id;
    private GoodsDepository depository;
    private long amount;
    private float price;
    private float worth;

    private Goods goods;

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
    @JoinColumn (name = "depository_id")
    public GoodsDepository getDepository ()
    {
        return depository;
    }

    public void setDepository (GoodsDepository depository)
    {
        this.depository = depository;
    }

    @ManyToOne (cascade =
    { CascadeType.REFRESH }, optional = false)
    @JoinColumn (name = "goods_id")
    public Goods getGoods ()
    {
        return goods;
    }

    public void setGoods (Goods goods)
    {
        this.goods = goods;
    }

    public long getAmount ()
    {
        return amount;
    }

    public void setAmount (long amount)
    {
        this.amount = amount;
    }

    public float getPrice ()
    {
        return price;
    }

    public void setPrice (float price)
    {
        this.price = price;
    }

    public float getWorth ()
    {
        return worth;
    }

    public void setWorth (float worth)
    {
        this.worth = worth;
    }
}
