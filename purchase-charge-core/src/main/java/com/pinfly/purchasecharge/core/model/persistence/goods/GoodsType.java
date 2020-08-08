package com.pinfly.purchasecharge.core.model.persistence.goods;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.pinfly.purchasecharge.core.model.BaseModel;

/**
 * 货物类型
 */
@Entity
@Table (name = "pc_goods_type")
public class GoodsType extends BaseModel
{
    private static final long serialVersionUID = 1L;

    private long id;
    private String name;

    /**
     * parent type
     */
    private GoodsType parent;
    private List <Goods> goodsList;

    public GoodsType ()
    {
    }

    public GoodsType (long id)
    {
        this.id = id;
    }

    public GoodsType (String name)
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

    @ManyToOne
    @JoinColumn (name = "parent_id")
    public GoodsType getParent ()
    {
        return parent;
    }

    public void setParent (GoodsType goodsType)
    {
        this.parent = goodsType;
    }

    @OneToMany (mappedBy = "type")
    public List <Goods> getGoodsList ()
    {
        return goodsList;
    }

    public void setGoodsList (List <Goods> goodsList)
    {
        this.goodsList = goodsList;
    }
}
