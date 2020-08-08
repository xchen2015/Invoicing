package com.pinfly.purchasecharge.core.model.persistence.goods;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.pinfly.purchasecharge.core.model.BaseModel;

/**
 * 货物单位
 */
@Entity
@Table (name = "pc_goods_unit")
public class GoodsUnit extends BaseModel
{
    private static final long serialVersionUID = 1L;

    private long id;
    private String name;

    public GoodsUnit ()
    {
    }

    public GoodsUnit (long id)
    {
        this.id = id;
    }

    public GoodsUnit (String name)
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
