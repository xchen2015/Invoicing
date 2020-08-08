package com.pinfly.purchasecharge.core.model.persistence.goods;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.collections.CollectionUtils;

import com.pinfly.purchasecharge.core.model.BaseModel;

/**
 * 货物
 */
@Entity
@Table (name = "pc_goods")
public class Goods extends BaseModel
{
    private static final long serialVersionUID = 1L;

    private long id;
    private String name;
    private String shortCode;

    private String specificationModel;
    private String barCode;
    private String comment;

    /**
     * 成本价
     */
    private float averagePrice;
    /**
     * 预计进货价
     */
    private float importPrice;
    /**
     * 零售参考价
     */
    private float retailPrice;
    /**
     * 批发参考价
     */
    private float tradePrice;
    /**
     * 最低库存
     */
    private long minStock;
    /**
     * 最高库存
     */
    private long maxStock;

    private long userUpdatedBy;
    private Date lastUpdated;

    private GoodsUnit unit;
    private GoodsType type;
    private GoodsDepository preferedDepository;
    private List <GoodsPicture> pictures;
    /**
     * 库存
     */
    private List <GoodsStorage> storages;

    public Goods ()
    {
    }

    public Goods (String name)
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

    @Column (nullable = false)
    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getSpecificationModel ()
    {
        return specificationModel;
    }

    public void setSpecificationModel (String specificationModel)
    {
        this.specificationModel = specificationModel;
    }

    public String getBarCode ()
    {
        return barCode;
    }

    public void setBarCode (String barCode)
    {
        this.barCode = barCode;
    }

    @Column (length = 2048)
    public String getComment ()
    {
        return comment;
    }

    public void setComment (String comment)
    {
        this.comment = comment;
    }

    @ManyToOne (cascade =
    { CascadeType.MERGE, CascadeType.REFRESH })
    @JoinColumn (name = "unit_id")
    public GoodsUnit getUnit ()
    {
        return unit;
    }

    public void setUnit (GoodsUnit unit)
    {
        this.unit = unit;
    }

    @ManyToOne (cascade =
    { CascadeType.MERGE, CascadeType.REFRESH })
    @JoinColumn (name = "type_id")
    public GoodsType getType ()
    {
        return type;
    }

    public void setType (GoodsType type)
    {
        this.type = type;
    }

    @Column (unique = true)
    public String getShortCode ()
    {
        return shortCode;
    }

    public void setShortCode (String shortCode)
    {
        this.shortCode = shortCode;
    }

    public float getAveragePrice ()
    {
        return averagePrice;
    }

    public void setAveragePrice (float averagePrice)
    {
        this.averagePrice = averagePrice;
    }

    public float getImportPrice ()
    {
        return importPrice;
    }

    public void setImportPrice (float importPrice)
    {
        this.importPrice = importPrice;
    }

    public float getRetailPrice ()
    {
        return retailPrice;
    }

    public void setRetailPrice (float retailPrice)
    {
        this.retailPrice = retailPrice;
    }

    public float getTradePrice ()
    {
        return tradePrice;
    }

    public void setTradePrice (float tradePrice)
    {
        this.tradePrice = tradePrice;
    }

    @ManyToOne (cascade =
    { CascadeType.MERGE, CascadeType.REFRESH })
    @JoinColumn (name = "preferedDepository_id")
    public GoodsDepository getPreferedDepository ()
    {
        return preferedDepository;
    }

    public void setPreferedDepository (GoodsDepository preferedDepository)
    {
        this.preferedDepository = preferedDepository;
    }

    @OneToMany (fetch = FetchType.EAGER, mappedBy = "goods", cascade = CascadeType.ALL, orphanRemoval = true)
    public List <GoodsStorage> getStorages ()
    {
        return storages;
    }

    public void setStorages (List <GoodsStorage> storages)
    {
        this.storages = storages;
        if (CollectionUtils.isNotEmpty (storages))
        {
            for (GoodsStorage storage : storages)
            {
                storage.setGoods (this);
            }
        }
    }

    @Temporal (value = TemporalType.TIMESTAMP)
    public Date getLastUpdated ()
    {
        return lastUpdated;
    }

    public void setLastUpdated (Date lastUpdated)
    {
        this.lastUpdated = lastUpdated;
    }

    @OneToMany (mappedBy = "goods")
    public List <GoodsPicture> getPictures ()
    {
        return pictures;
    }

    public void setPictures (List <GoodsPicture> pictures)
    {
        this.pictures = pictures;
    }

    public long getUserUpdatedBy ()
    {
        return userUpdatedBy;
    }

    public void setUserUpdatedBy (long userUpdatedBy)
    {
        this.userUpdatedBy = userUpdatedBy;
    }

    public long getMinStock ()
    {
        return minStock;
    }

    public void setMinStock (long minStock)
    {
        this.minStock = minStock;
    }

    public long getMaxStock ()
    {
        return maxStock;
    }

    public void setMaxStock (long maxStock)
    {
        this.maxStock = maxStock;
    }
}
