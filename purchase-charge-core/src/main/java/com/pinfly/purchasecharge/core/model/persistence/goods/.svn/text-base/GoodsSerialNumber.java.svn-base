package com.pinfly.purchasecharge.core.model.persistence.goods;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.pinfly.purchasecharge.core.model.BaseModel;
import com.pinfly.purchasecharge.core.model.persistence.in.OrderInItem;
import com.pinfly.purchasecharge.core.model.persistence.out.OrderOutItem;

/**
 * 货物序列号
 */
@Entity
@Table (name = "pc_goods_serial")
public class GoodsSerialNumber extends BaseModel
{
    private static final long serialVersionUID = 1L;

    private long id;
    private String serialNumber;
    private Goods goods;

    private OrderInItem orderInItem;
    private OrderOutItem orderOutItem;

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
    public String getSerialNumber ()
    {
        return serialNumber;
    }

    public void setSerialNumber (String serialNumber)
    {
        this.serialNumber = serialNumber;
    }

    @ManyToOne (cascade =
    { CascadeType.REFRESH })
    @JoinColumn (name = "goods_id")
    public Goods getGoods ()
    {
        return goods;
    }

    public void setGoods (Goods goods)
    {
        this.goods = goods;
    }

    @ManyToOne (cascade =
    { CascadeType.REFRESH })
    @JoinColumn (name = "orderInItem_id")
    public OrderInItem getOrderInItem ()
    {
        return orderInItem;
    }

    public void setOrderInItem (OrderInItem orderInItem)
    {
        this.orderInItem = orderInItem;
    }

    @ManyToOne (cascade =
    { CascadeType.REFRESH })
    @JoinColumn (name = "orderOutItem_id")
    public OrderOutItem getOrderOutItem ()
    {
        return orderOutItem;
    }

    public void setOrderOutItem (OrderOutItem orderOutItem)
    {
        this.orderOutItem = orderOutItem;
    }

}
