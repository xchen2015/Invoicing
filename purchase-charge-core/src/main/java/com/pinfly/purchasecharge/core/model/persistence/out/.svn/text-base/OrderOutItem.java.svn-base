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
import com.pinfly.purchasecharge.core.model.persistence.goods.Goods;
import com.pinfly.purchasecharge.core.model.persistence.goods.GoodsDepository;

/**
 * 出库单明细
 */
@Entity
@Table (name = "pc_orderOut_item")
public class OrderOutItem extends BaseModel
{
    private static final long serialVersionUID = 1L;

    private long id;
    private float unitPrice;
    private long amount;
    private float sum;
    private String comment;

    private Goods goods;
    private GoodsDepository depository;
    private OrderOut orderOut;

    public OrderOutItem ()
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

    @ManyToOne (cascade =
    { CascadeType.REFRESH }, optional = false)
    @JoinColumn (name = "depository_id")
    public GoodsDepository getDepository ()
    {
        return depository;
    }

    public void setDepository (GoodsDepository depository)
    {
        this.depository = depository;
    }

    @Column (nullable = false)
    public float getUnitPrice ()
    {
        return unitPrice;
    }

    public void setUnitPrice (float unitPrice)
    {
        this.unitPrice = unitPrice;
    }

    @Column (nullable = false)
    public long getAmount ()
    {
        return amount;
    }

    public void setAmount (long amount)
    {
        this.amount = amount;
    }

    @Column (nullable = false)
    public float getSum ()
    {
        return sum;
    }

    public void setSum (float sum)
    {
        this.sum = sum;
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

    @ManyToOne (fetch = FetchType.LAZY, cascade =
    { CascadeType.REFRESH }, optional = false)
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
